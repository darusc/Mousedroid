package com.darusc.mousedroid.networking

import android.util.Log
import com.darusc.mousedroid.mkinput.InputEvent
import com.darusc.mousedroid.networking.bluetooth.HIDReport
import com.darusc.mousedroid.networking.bluetooth.MouseReport

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

private fun getMouseButtonHIDCode(button: InputEvent.MouseButton): Byte {
    return when(button) {
        InputEvent.MouseButton.NONE -> 0
        InputEvent.MouseButton.LEFT -> 1
        InputEvent.MouseButton.RIGHT -> 2
        InputEvent.MouseButton.MIDDLE -> 4
    }
}

/**
 * Translate the input event to 1 or more bluetooth HID reports
 * (e.g mouse click requires 2 reports -> one for pressing the button and one for releasing)
 */
fun InputEvent.toHIDReport(): Array<HIDReport> {
    Log.d("Mousedroid", this::class.java.toString())
    return when (this) {
        is InputEvent.MouseMove -> {
            val report = MouseReport(getMouseButtonHIDCode(this.button), (-this.dx).toByte(), (-this.dy).toByte(), 0)
            arrayOf(report)
        }
        is InputEvent.MouseClick -> {
            val r1 = MouseReport(getMouseButtonHIDCode(this.button), 0, 0, 0)
            val r2 = MouseReport(0, 0, 0, 0)
            arrayOf(r1, r2)
        }
        is InputEvent.MouseDragState -> {
            val state = if(this.isDown) getMouseButtonHIDCode(this.button) else 0
            val report = MouseReport(state, 0, 0, 0)
            arrayOf(report)
        }
        is InputEvent.MouseScroll -> {
            val report = if(this.dy != 0) {
                // Vertical scrolling -> mouse wheel rotation
                MouseReport(0, 0, 0, (this.dy * 0.05).toInt().toByte())
            } else {
                // Horizontal scrolling -> CTRL + mouse wheel rotation
                TODO()
            }
            arrayOf(report)
        }
        is InputEvent.KeyPress -> {
            TODO()
        }
        is InputEvent.Zoom -> {
            TODO()
        }
    }
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