package com.example.mousedroid

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.BatteryManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import com.example.mousedroid.networking.ConnectionManager
import com.example.mousedroid.networking.ConnectionManager.Mode
import com.google.android.material.button.MaterialButton
import org.w3c.dom.Text

class MainActivity : AppCompatActivity(), ConnectionManager.ConnectionStateCallback {

    private val TAG = "Mousedroid"

    private var connectionManager = ConnectionManager.getInstance(this)
    private lateinit var inputActivityIntent: Intent

    private var deviceDetails: String = ""

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        Log.d(TAG, "PERMISSION RESULT")
        if(requestCode == 100) {
            if(!grantResults.contains(PackageManager.PERMISSION_DENIED)) {
                init((baseContext.getSystemService(BLUETOOTH_SERVICE) as BluetoothManager).adapter.name)
            }
            else {
                init("PERMISSION NOT ALLOWED")
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun init(deviceName: String) {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL

        val connectionMode = getConnectionMode()
//        TcpClient.init(manufacturer, deviceName, model, connectionMode)

        deviceDetails = "$manufacturer/$deviceName/$model/$connectionMode"

        when(connectionMode) {
            Mode.USB -> connectionManager.connect(6969, deviceDetails)
            Mode.WIFI -> resultLauncher.launch(Intent(this, SelectDeviceActivity::class.java))
        }

        findViewById<MaterialButton>(R.id.retryButton).setOnClickListener {
            if(connectionMode == Mode.USB){
                finish()
                startActivity(intent)
            }
            else{
                findViewById<ConstraintLayout>(R.id.errorPanel).visibility = View.GONE
                findViewById<RelativeLayout>(R.id.loadingPanel).visibility = View.VISIBLE
                resultLauncher.launch(Intent(this, SelectDeviceActivity::class.java))
            }
        }

        findViewById<MaterialButton>(R.id.selectAnotherDeviceButton).setOnClickListener {
            findViewById<ConstraintLayout>(R.id.errorPanel).visibility = View.VISIBLE
            findViewById<RelativeLayout>(R.id.loadingPanel).visibility = View.GONE
            resultLauncher.launch(Intent(this, SelectDeviceActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S_V2) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "REQUESTING PERMISSION")
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.BLUETOOTH_CONNECT), 100)
            }
            else {
                init((baseContext.getSystemService(BLUETOOTH_SERVICE) as BluetoothManager).adapter.name)
            }
        } else {
            init(Settings.Secure.getString(contentResolver, "bluetooth_name"))
        }

        //val deviceName = Settings.Global.getString(application.contentResolver, Settings.Global.DEVICE_NAME)
    }

    override fun onResume() {
        super.onResume()
        connectionManager = ConnectionManager.getInstance(this)
    }

    override fun onConnectionSuccessful(connectionMode: Mode) {
        inputActivityIntent = Intent(this, InputActivity::class.java)
        inputActivtyResultLauncher.launch(inputActivityIntent)
    }

    override fun onConnectionFailed(connectionMode: Mode) {
        runOnUiThread {
            findViewById<ConstraintLayout>(R.id.errorPanel).visibility = View.VISIBLE
            findViewById<RelativeLayout>(R.id.loadingPanel).visibility = View.GONE

            if(connectionMode == Mode.USB) {
                findViewById<MaterialButton>(R.id.selectAnotherDeviceButton).visibility = View.GONE
                findViewById<TextView>(R.id.errorDetail).text = getString(R.string.connectionFailedUSB)
            }
            else{
                findViewById<TextView>(R.id.errorName).text = getString(R.string.connectionFailed)
                findViewById<TextView>(R.id.errorDetail).text = getString(R.string.connectionFailedWIFI)
            }
        }
    }

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if(result.resultCode == Activity.RESULT_OK){
            val ip = result.data?.getStringExtra("result").toString()
            connectionManager.connect(ip, 6969, deviceDetails)
        }
    }

    // Handle connection disconnect event trough result from the input activity
    // as it is not safe to perform UI operations on the suspended main activity
    private var inputActivtyResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if(result.resultCode == Activity.RESULT_OK) {
            findViewById<ConstraintLayout>(R.id.errorPanel).visibility = View.VISIBLE
            findViewById<RelativeLayout>(R.id.loadingPanel).visibility = View.GONE
            findViewById<TextView>(R.id.errorName).text = getString(R.string.connectionLost)
            findViewById<TextView>(R.id.errorDetail).text = ""
        }
    }

    private fun getConnectionMode(): Mode {
        val ifilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        val batteryStatus = baseContext.registerReceiver(null, ifilter)

        val isPluggedIn = batteryStatus?.getIntExtra(
            BatteryManager.EXTRA_PLUGGED,
            -1
        ) == BatteryManager.BATTERY_PLUGGED_USB

        return if(isPluggedIn) Mode.USB else Mode.WIFI
    }
}