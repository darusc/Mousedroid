package com.darusc.mousedroid.viewmodels

import com.darusc.mousedroid.layouts.KeyboardLayout
import com.darusc.mousedroid.layouts.KeyboardLayoutUS
import com.darusc.mousedroid.mkinput.InputEvent
import com.darusc.mousedroid.networking.ConnectionManager

class KeyboardViewModel : BaseViewModel<KeyboardViewModel.State, KeyboardViewModel.Event>(State()) {

    sealed class Event : BaseViewModel.Event()
    class State : BaseViewModel.State()

    private val connectionManager = ConnectionManager.getInstance()

    private val layoutMap: Map<String, Class<out KeyboardLayout>> = mapOf(
        KeyboardLayoutUS.NAME to KeyboardLayoutUS::class.java
    )

    val layouts: Set<String>
        get() = layoutMap.keys

    var activeKeyboardLayout: KeyboardLayout = KeyboardLayoutUS()

    fun setKeyboardLayout(layout: String): Boolean {
        val layoutClass = layoutMap[layout]

        if (layoutClass != null) {
            try {
                activeKeyboardLayout = layoutClass.getDeclaredConstructor().newInstance()
                return true
            } catch (e: Exception) {
                return false
            }
        }

        return false
    }

    fun handleKeypress(bytes: ByteArray) {
        val text = String(bytes, Charsets.UTF_8)

        for (char in text) {
            val mapping = activeKeyboardLayout.getMapping(char)
            if (mapping != null) {
                connectionManager.send(InputEvent.KeyPress(mapping.modifier, mapping.code))
            }
        }
    }
}