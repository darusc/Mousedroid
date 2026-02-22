package com.darusc.mousedroid.viewmodels

import android.content.Context
import android.view.View
import com.darusc.mousedroid.R
import com.darusc.mousedroid.mkinput.GestureHandler
import com.darusc.mousedroid.mkinput.InputEvent
import com.darusc.mousedroid.networking.ConnectionManager

class TouchpadViewModel: BaseViewModel<TouchpadViewModel.State, TouchpadViewModel.Event>(State.Idle) {

    sealed class State: BaseViewModel.State() {
        object Idle: State()
    }
    sealed class Event: BaseViewModel.Event()

    private val connectionManager = ConnectionManager.getInstance()

    fun createGestureHandler(context: Context): GestureHandler {
        return GestureHandler(context)
    }

    fun onMediaButtonClick(view: View) {
        when(view.id) {
            R.id.btnPrev -> connectionManager.send(InputEvent.MediaEvent(InputEvent.MediaAction.PREVIOUS))
            R.id.btnPlayPause -> connectionManager.send(InputEvent.MediaEvent(InputEvent.MediaAction.PLAY_PAUSE))
            R.id.btnNext -> connectionManager.send(InputEvent.MediaEvent(InputEvent.MediaAction.NEXT))
            R.id.btnForward10 -> connectionManager.send(InputEvent.MediaEvent(InputEvent.MediaAction.FORWARD))
            R.id.btnVolDown -> connectionManager.send(InputEvent.MediaEvent(InputEvent.MediaAction.VOLUME_DOWN))
            R.id.btnVolUp -> connectionManager.send(InputEvent.MediaEvent(InputEvent.MediaAction.VOLUME_UP))
            R.id.btnVolOff -> connectionManager.send(InputEvent.MediaEvent(InputEvent.MediaAction.VOLUME_MUTE))
            R.id.btnReplay10 -> connectionManager.send(InputEvent.MediaEvent(InputEvent.MediaAction.REPLAY))
        }
    }

    fun onMouseButtonClick(view: View) {
        when(view.id) {
            R.id.btnLeftClick -> connectionManager.send(InputEvent.MouseClick(InputEvent.MouseButton.LEFT))
            R.id.btnRightClick -> connectionManager.send(InputEvent.MouseClick(InputEvent.MouseButton.RIGHT))
            R.id.btnMiddleClick -> connectionManager.send(InputEvent.MouseClick(InputEvent.MouseButton.MIDDLE))
        }
    }
}