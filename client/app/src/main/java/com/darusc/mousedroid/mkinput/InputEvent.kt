package com.darusc.mousedroid.mkinput

sealed class InputEvent {

    data class MouseMove(val dx: Int, val dy: Int) : InputEvent()
    data class MouseScroll(val dx: Int, val dy: Int) : InputEvent() // Covers Vertical (dy) and Horizontal (dx)
    data class MouseClick(val button: MouseButton) : InputEvent()
    data class MouseDragState(val button: MouseButton, val isDown: Boolean) : InputEvent() // For DOWN/UP dragging
    data class Zoom(val scale: Int) : InputEvent()

    data class KeyPress(val activeBytes: ByteArray) : InputEvent()

    enum class MouseButton {
        LEFT,
        RIGHT,
        MIDDLE
    }
}