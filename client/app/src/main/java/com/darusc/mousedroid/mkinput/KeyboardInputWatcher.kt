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
                connectionManager.send(InputEvent.KeyPress(byteArrayOf(127)), true) // DEL
                if(start == 1 && lengthAfter == 0){
                    reset()
                }
            }
            else {
                val c = if(text.isNotEmpty()) text[text.length - 1] else null
                if(c == '\n'){
                    connectionManager.send(InputEvent.KeyPress(byteArrayOf(10)), true) // \n
                    reset()
                }
                else {
                    if(lengthAfter == lengthBefore + 1) {
                        connectionManager.send(InputEvent.KeyPress(byteArrayOf(text.last().code.toByte())), true)
                    }
                    else {
                        val newText = text.substring(start, start + (lengthAfter - lengthBefore))
                        val bytes = newText.toByteArray(Charsets.UTF_8)
                        connectionManager.send(InputEvent.KeyPress(bytes), true)
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