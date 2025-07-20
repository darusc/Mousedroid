package com.darusc.mousedroid.mkinput

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.darusc.mousedroid.networking.ConnectionManager

class KeyboardInputWatcher(private val editText: EditText): TextWatcher {

    private val TAG = "Mousedroid"

    private val connectionManager = ConnectionManager.getInstance()

    private var ignoreChange = false

    init {
        reset()
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

    override fun onTextChanged(text: CharSequence, start: Int, lengthBefore: Int, lengthAfter: Int) {
        if(!ignoreChange) {
            if(lengthAfter < lengthBefore){
                connectionManager.sendBytes(byteArrayOf(Input.KEYPRESS, 127), true) // DEL
                if(start == 1 && lengthAfter == 0){
                    reset()
                }
            }
            else {
                val c = if(text.isNotEmpty()) text[text.length - 1] else null
                if(c == '\n'){
                    connectionManager.sendBytes(byteArrayOf(Input.KEYPRESS, 10), true) // \n
                    reset()
                }
                else {
                    if(lengthAfter == lengthBefore + 1) {
                        connectionManager.sendBytes(byteArrayOf(Input.KEYPRESS, text[text.length - 1].code.toByte()), true)
                    }
                    else {
                        val bytes = text.substring(start).map { it.code.toByte() }.toByteArray()
                        connectionManager.sendBytes(byteArrayOf(Input.KEYPRESS, *bytes), true)
                    }
                }
            }
        }
    }

    override fun afterTextChanged(p0: Editable?) { }

    private fun reset() {
        ignoreChange = true
        editText.setText("//")
        editText.setSelection(editText.length())
        ignoreChange = false
    }
}