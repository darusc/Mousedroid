package com.darusc.mousedroid.viewmodels

import android.view.View
import com.darusc.mousedroid.R
import com.darusc.mousedroid.layouts.Keycode
import com.darusc.mousedroid.mkinput.InputEvent
import com.darusc.mousedroid.networking.ConnectionManager

class NumpadViewModel: BaseViewModel<NumpadViewModel.State, NumpadViewModel.Event>(State.Idle) {

    sealed class State : BaseViewModel.State() {
        object Idle : State()
    }

    sealed class Event : BaseViewModel.Event()

    private val connectionManager = ConnectionManager.getInstance()

    fun onButtonClick(view: View) {
        when(view.id) {
            R.id.num0 -> connectionManager.send(InputEvent.NumpadKeyPress(Keycode.KEYPAD_0))
            R.id.num1 -> connectionManager.send(InputEvent.NumpadKeyPress(Keycode.KEYPAD_1))
            R.id.num2 -> connectionManager.send(InputEvent.NumpadKeyPress(Keycode.KEYPAD_2))
            R.id.num3 -> connectionManager.send(InputEvent.NumpadKeyPress(Keycode.KEYPAD_3))
            R.id.num4 -> connectionManager.send(InputEvent.NumpadKeyPress(Keycode.KEYPAD_4))
            R.id.num5 -> connectionManager.send(InputEvent.NumpadKeyPress(Keycode.KEYPAD_5))
            R.id.num6 -> connectionManager.send(InputEvent.NumpadKeyPress(Keycode.KEYPAD_6))
            R.id.num7 -> connectionManager.send(InputEvent.NumpadKeyPress(Keycode.KEYPAD_7))
            R.id.num8 -> connectionManager.send(InputEvent.NumpadKeyPress(Keycode.KEYPAD_8))
            R.id.num9 -> connectionManager.send(InputEvent.NumpadKeyPress(Keycode.KEYPAD_9))
            R.id.numdiv -> connectionManager.send(InputEvent.NumpadKeyPress(Keycode.KEYPAD_DIVIDE))
            R.id.nummultiply -> connectionManager.send(InputEvent.NumpadKeyPress(Keycode.KEYPAD_MULTIPLY))
            R.id.numminus -> connectionManager.send(InputEvent.NumpadKeyPress(Keycode.KEYPAD_SUBTRACT))
            R.id.numplus -> connectionManager.send(InputEvent.NumpadKeyPress(Keycode.KEYPAD_ADD))
            R.id.numenter -> connectionManager.send(InputEvent.NumpadKeyPress(Keycode.KEYPAD_ENTER))
            R.id.numdot -> connectionManager.send(InputEvent.NumpadKeyPress(Keycode.KEYPAD_DOT))
        }
    }

    fun onButtonLongClick(view: View): Boolean {
        when(view.id) {
            R.id.numdot -> connectionManager.send(InputEvent.NumpadKeyPress(Keycode.KEY_BACKSPACE))
        }
        return true
    }
}