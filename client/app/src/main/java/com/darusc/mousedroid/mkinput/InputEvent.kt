package com.darusc.mousedroid.mkinput

sealed class InputEvent {

    enum class MouseButton {
        LEFT,
        RIGHT,
        MIDDLE,
        NONE
    }

    enum class MediaAction {
        PREVIOUS,
        PLAY_PAUSE,
        NEXT,
        FORWARD,
        REPLAY,
        VOLUME_DOWN,
        VOLUME_UP,
        VOLUME_MUTE
    }

    enum class NumpadKey {
        NUM0,
        NUM1,
        NUM2,
        NUM3,
        NUM4,
        NUM5,
        NUM6,
        NUM7,
        NUM8,
        NUM9,
        NUM_ENTER,
        NUM_PLUS,
        NUM_MINUS,
        NUM_MULTIPLY,
        NUM_DIV,
        NUM_DEL,
        NUM_DOT
    }

    data class MouseMove(val dx: Int, val dy: Int, val button: MouseButton = MouseButton.NONE) : InputEvent()
    data class MouseScroll(val dx: Int, val dy: Int) : InputEvent() // Covers Vertical (dy) and Horizontal (dx)
    data class MouseClick(val button: MouseButton) : InputEvent()
    data class MouseDragState(val button: MouseButton, val isDown: Boolean) : InputEvent() // For DOWN/UP dragging
    data class Zoom(val scale: Int) : InputEvent()

    data class KeyPress(val modifier: Byte, val code: Byte) : InputEvent()
    data class NumpadKeyPress(val key: NumpadKey): InputEvent()

    data class MediaEvent(val action: MediaAction): InputEvent()

    data class BatteryEvent(val percentage: Int): InputEvent()
}