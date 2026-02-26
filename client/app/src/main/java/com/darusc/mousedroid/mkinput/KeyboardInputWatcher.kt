package com.darusc.mousedroid.mkinput

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class KeyboardInputWatcher(
    private val editText: EditText,
    private val sendInputCallback: (CharArray) -> Unit
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
                sendInputCallback(charArrayOf(127.toChar())) // DEL
                if(start == 1 && lengthAfter == 0){
                    reset()
                }
            }
            else {
                val c = if(text.isNotEmpty()) text[text.length - 1] else null
                if(c == '\n'){
                    sendInputCallback(charArrayOf(10.toChar())) // \n
                    reset()
                }
                else {
                    if(lengthAfter == lengthBefore + 1) {
                        sendInputCallback(charArrayOf(text.last()))
                    }
                    else {
                        val newText = text.subSequence(start, start + (lengthAfter - lengthBefore))
                        val chars = newText.toString().toCharArray()
                        sendInputCallback(chars)
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