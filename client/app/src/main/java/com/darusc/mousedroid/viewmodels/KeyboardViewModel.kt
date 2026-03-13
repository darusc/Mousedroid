package com.darusc.mousedroid.viewmodels

import com.darusc.mousedroid.layouts.KeyboardLayout
import com.darusc.mousedroid.layouts.languages.KeyboardLayoutES
import com.darusc.mousedroid.layouts.languages.KeyboardLayoutFR
import com.darusc.mousedroid.layouts.languages.KeyboardLayoutRO
import com.darusc.mousedroid.layouts.languages.KeyboardLayoutUS
import com.darusc.mousedroid.mkinput.InputEvent
import com.darusc.mousedroid.networking.ConnectionManager

class KeyboardViewModel : BaseViewModel<KeyboardViewModel.State, KeyboardViewModel.Event>(State()) {

    sealed class Event : BaseViewModel.Event()
    class State : BaseViewModel.State()

    private val connectionManager = ConnectionManager.getInstance()

    private val layoutMap: Map<String, Class<out KeyboardLayout>> = mapOf(
        KeyboardLayoutUS.NAME to KeyboardLayoutUS::class.java,
        KeyboardLayoutES.NAME to KeyboardLayoutES::class.java,
        KeyboardLayoutFR.NAME to KeyboardLayoutFR::class.java,
        KeyboardLayoutRO.NAME to KeyboardLayoutRO::class.java,
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

    fun handleKeypress(chars: CharArray) {
        for (char in chars) {
            val mapping = activeKeyboardLayout.getMapping(char)
            if (mapping != null) {
                connectionManager.send(InputEvent.KeyPress(mapping))
            }
        }
    }
}