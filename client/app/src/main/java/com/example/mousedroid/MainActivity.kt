package com.example.mousedroid

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.widget.PopupWindow
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.mousedroid.fragments.Main
import com.example.mousedroid.networking.ConnectionManager
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity(), ConnectionManager.ConnectionStateCallback {

    private val TAG = "Mousedroid"

    private var connectionManager = ConnectionManager.getInstance(this)

    private lateinit var inputActivityIntent: Intent
    // Handle connection disconnect event trough result from the input activity
    // as it is not safe to perform UI operations on the suspended main activity
    private var inputActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if(result.resultCode == Activity.RESULT_OK) {
            showPopupMessage(R.layout.connection_disconnected_fragment)
        }
    }

    private lateinit var loadingPopup: PopupWindow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        changeActiveFragment(Main())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S_V2) {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.BLUETOOTH_CONNECT), 1000)
            }
        }

        loadingPopup = PopupWindow (
            layoutInflater.inflate(R.layout.loading_fragment, null),
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.MATCH_PARENT,
        )
    }

    override fun onResume() {
        super.onResume()
        connectionManager = ConnectionManager.getInstance(this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 1000) {
            if(grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                AlertDialog.Builder(this)
                    .setTitle("Bluetooth Permission Required")
                    .setMessage("Please enable bluetooth permission in settings and restart the app.")
                    .setPositiveButton("Go to settings") { _, _ ->
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            data = Uri.fromParts("package", packageName, null)
                        }
                        startActivity(intent)
                    }
                    .setNegativeButton("Cancel", null)
                    .show()
            }
        }
    }

    override fun onConnectionInitiated() {
        loadingPopup.showAtLocation(findViewById(android.R.id.content), Gravity.CENTER, 0, 0)
    }

    override fun onConnectionSuccessful(connectionMode: ConnectionManager.Mode) {
        runOnUiThread {
            loadingPopup.dismiss()
        }
        inputActivityIntent = Intent(this, InputActivity::class.java)
        inputActivityResultLauncher.launch(inputActivityIntent)
    }

    override fun onConnectionFailed(connectionMode: ConnectionManager.Mode) {
        runOnUiThread {
            loadingPopup.dismiss()
            showPopupMessage(R.layout.connection_failed_fragment)
        }
    }

    private fun changeActiveFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
    }

    private fun showPopupMessage(@LayoutRes fragmentId: Int) {
        val pView = layoutInflater.inflate(fragmentId, null)
        val popup = PopupWindow(
            pView,
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            true
        )

        pView.findViewById<MaterialButton>(R.id.btnOK).setOnClickListener {
            popup.dismiss()
        }

        popup.showAtLocation(pView, Gravity.CENTER, 0, 0)
        popup.dim(0.6f)
    }
}