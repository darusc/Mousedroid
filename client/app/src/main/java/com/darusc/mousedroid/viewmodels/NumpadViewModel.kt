package com.darusc.mousedroid.viewmodels

import android.view.View
import com.darusc.mousedroid.R
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
            R.id.num0 -> connectionManager.send(InputEvent.NumpadKeyPress(InputEvent.NumpadKey.NUM0))
            R.id.num1 -> connectionManager.send(InputEvent.NumpadKeyPress(InputEvent.NumpadKey.NUM1))
            R.id.num2 -> connectionManager.send(InputEvent.NumpadKeyPress(InputEvent.NumpadKey.NUM2))
            R.id.num3 -> connectionManager.send(InputEvent.NumpadKeyPress(InputEvent.NumpadKey.NUM3))
            R.id.num4 -> connectionManager.send(InputEvent.NumpadKeyPress(InputEvent.NumpadKey.NUM4))
            R.id.num5 -> connectionManager.send(InputEvent.NumpadKeyPress(InputEvent.NumpadKey.NUM5))
            R.id.num6 -> connectionManager.send(InputEvent.NumpadKeyPress(InputEvent.NumpadKey.NUM6))
            R.id.num7 -> connectionManager.send(InputEvent.NumpadKeyPress(InputEvent.NumpadKey.NUM7))
            R.id.num8 -> connectionManager.send(InputEvent.NumpadKeyPress(InputEvent.NumpadKey.NUM8))
            R.id.num9 -> connectionManager.send(InputEvent.NumpadKeyPress(InputEvent.NumpadKey.NUM9))
            R.id.numdiv -> connectionManager.send(InputEvent.NumpadKeyPress(InputEvent.NumpadKey.NUM_DIV))
            R.id.nummultiply -> connectionManager.send(InputEvent.NumpadKeyPress(InputEvent.NumpadKey.NUM_MULTIPLY))
            R.id.numminus -> connectionManager.send(InputEvent.NumpadKeyPress(InputEvent.NumpadKey.NUM_MINUS))
            R.id.numplus -> connectionManager.send(InputEvent.NumpadKeyPress(InputEvent.NumpadKey.NUM_PLUS))
            R.id.numenter -> connectionManager.send(InputEvent.NumpadKeyPress(InputEvent.NumpadKey.NUM_ENTER))
            R.id.numdot -> connectionManager.send(InputEvent.NumpadKeyPress(InputEvent.NumpadKey.NUM_DEL))
        }
    }

    fun onButtonLongClick(view: View): Boolean {
        when(view.id) {
            R.id.numdot -> connectionManager.send(InputEvent.NumpadKeyPress(InputEvent.NumpadKey.NUM_DOT))
        }
        return true
    }
}