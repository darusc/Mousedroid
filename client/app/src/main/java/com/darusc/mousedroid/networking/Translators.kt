package com.darusc.mousedroid.networking

import com.darusc.mousedroid.mkinput.InputEvent

private object RawSocketEvents {
    const val LCLICK: Byte = 0x01
    const val RCLICK: Byte = 0x02
    const val DOWN: Byte = 0x03
    const val UP: Byte = 0x04
    const val MOVE: Byte = 0x05
    const val SCROLL: Byte = 0x06
    const val KEYPRESS: Byte = 0x07
    const val SCROLL_H: Byte = 0x08
    const val ZOOM: Byte = 0x09
}

/**
 * Translate the input event to bluetooth HID report
 */
fun InputEvent.toHIDReport(): ByteArray {

}

/**
 * Translate the input event to raw socket bytes
 */
fun InputEvent.toSocketReport(): ByteArray {
    return when(this) {
        is InputEvent.MouseMove -> {
            byteArrayOf(RawSocketEvents.MOVE, this.dx.toByte(), this.dy.toByte())
        }
        is InputEvent.MouseScroll -> {
            if (this.dy != 0) {
                byteArrayOf(RawSocketEvents.SCROLL, (this.dy).toByte())
            } else {
                byteArrayOf(RawSocketEvents.SCROLL_H, (this.dx).toByte())
            }
        }
        is InputEvent.MouseClick -> {
            val code = if (this.button == InputEvent.MouseButton.LEFT) RawSocketEvents.LCLICK else RawSocketEvents.RCLICK
            byteArrayOf(code)
        }
        is InputEvent.MouseDragState -> {
            val code = if (this.isDown) RawSocketEvents.DOWN else RawSocketEvents.UP
            byteArrayOf(code)
        }
        is InputEvent.Zoom -> {
            byteArrayOf(RawSocketEvents.ZOOM, this.scale.toByte())
        }
        is InputEvent.KeyPress -> {
            byteArrayOf(RawSocketEvents.KEYPRESS) + this.activeBytes
        }
    }
}