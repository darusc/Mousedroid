package com.darusc.mousedroid.mkinput

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class KeyboardInputWatcher(
    private val editText: EditText,
    private val sendInputCallback: (ByteArray) -> Unit
): TextWatcher {

    private val TAG = "Mousedroid"

    private var ignoreChange = false

    init {
        reset()
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

    override fun onTextChanged(text: CharSequence, start: Int, lengthBefore: Int, lengthAfter: Int) {
        if(!ignoreChange) {
            if(lengthAfter < lengthBefore){
                sendInputCallback(byteArrayOf(127)) // DEL
                if(start == 1 && lengthAfter == 0){
                    reset()
                }
            }
            else {
                val c = if(text.isNotEmpty()) text[text.length - 1] else null
                if(c == '\n'){
                    sendInputCallback(byteArrayOf(10)) // \n
                    reset()
                }
                else {
                    if(lengthAfter == lengthBefore + 1) {
                        sendInputCallback(byteArrayOf(text.last().code.toByte()))
                    }
                    else {
                        val newText = text.substring(start, start + (lengthAfter - lengthBefore))
                        val bytes = newText.toByteArray(Charsets.UTF_8)
                        sendInputCallback(bytes)
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