package com.example.mousedroid

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.example.mousedroid.networking.ConnectionManager

class KeyboardInputWatcher(editText: EditText): TextWatcher {

    private val connectionManager = ConnectionManager.getInstance()

    private val TAG = "Mousedroid"

    private var ignoreChange = false

    private val edittext: EditText = editText

    init {
        editText.reset()
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

    override fun onTextChanged(text: CharSequence, start: Int, lengthBefore: Int, lengthAfter: Int) {
        if(!ignoreChange){
            if(lengthAfter < lengthBefore){
                connectionManager.sendBytes(byteArrayOf(Input.KEYPRESS, 127), true) // DEL
                if(start == 1 && lengthAfter == 0){
                    edittext.reset()
                }
            }
            else {
                val c = if(text.isNotEmpty()) text[text.length - 1] else null
                if(c == '\n'){
                    connectionManager.sendBytes(byteArrayOf(Input.KEYPRESS, 10), true) // \n
                    edittext.reset()
                }
                else{
                    if(lengthAfter == lengthBefore + 1){
                        connectionManager.sendBytes(byteArrayOf(Input.KEYPRESS, text[text.length - 1].code.toByte()), true)
                    }
                    else{
                        val bytes = text.substring(start).map { it.code.toByte() }.toByteArray()
                        connectionManager.sendBytes(byteArrayOf(Input.KEYPRESS, *bytes), true)
                    }
                }
            }
        }
    }

    override fun afterTextChanged(p0: Editable?) { }

    private fun EditText.reset(){
        ignoreChange = true
        edittext.setText("//")
        edittext.setSelection(edittext.length())
        ignoreChange = false
    }
}