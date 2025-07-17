package com.example.mousedroid

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mousedroid.networking.ConnectionManager

class InputActivity: AppCompatActivity(), ConnectionManager.ConnectionStateCallback {

    private lateinit var softinputView: EditText
    private val connectionManager = ConnectionManager.getInstance(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input)

        softinputView = findViewById(R.id.softinput_view)
        val kiw = KeyboardInputWatcher(softinputView)
        softinputView.addTextChangedListener(kiw)

        findViewById<CheckBox>(R.id.openKeyboard).apply {
            setOnCheckedChangeListener { compoundButton: CompoundButton, checked: Boolean ->
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                if(checked){
                    softinputView.requestFocus()
                    imm.showSoftInput(softinputView, InputMethodManager.SHOW_FORCED)
                }
                else{
                    imm.hideSoftInputFromWindow(windowToken, 0)
                }
            }
        }

        findViewById<CheckBox>(R.id.openNumpad).setOnCheckedChangeListener { compoundButton: CompoundButton, checked: Boolean ->
            if (checked) {
                changeActiveInputFragment(NumpadFragment())
            }
            else {
                changeActiveInputFragment(TouchpadFragment(baseContext))
            }
        }

        changeActiveInputFragment(TouchpadFragment(baseContext))
    }

    override fun onDisconnected() {
        setResult(Activity.RESULT_OK, Intent())
        finish();
    }

    private fun changeActiveInputFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
    }

    fun numpadButtonClickListener(view: View) = NumpadFragment.onNumpadButtonClickListener(view)
}