package com.darusc.mousedroid.networking

import android.util.Log
import com.darusc.mousedroid.layouts.KeyboardLayout
import com.darusc.mousedroid.layouts.Keycode
import com.darusc.mousedroid.mkinput.InputEvent
import com.darusc.mousedroid.networking.bluetooth.HIDReport
import com.darusc.mousedroid.networking.bluetooth.KeyboardReport
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
    return when (button) {
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
fun InputEvent.toHIDReport(layout: KeyboardLayout): Array<HIDReport> {
    Log.d("Mousedroid", this::class.java.toString())
    return when (this) {
        is InputEvent.MouseMove -> {
            val report = MouseReport(
                getMouseButtonHIDCode(this.button),
                (-this.dx).toByte(),
                (-this.dy).toByte(),
                0
            )
            arrayOf(report)
        }

        is InputEvent.MouseClick -> {
            val r1 = MouseReport(getMouseButtonHIDCode(this.button), 0, 0, 0)
            val r2 = MouseReport(0, 0, 0, 0)
            arrayOf(r1, r2)
        }

        is InputEvent.MouseDragState -> {
            val state = if (this.isDown) getMouseButtonHIDCode(this.button) else 0
            val report = MouseReport(state, 0, 0, 0)
            arrayOf(report)
        }

        is InputEvent.MouseScroll -> {
            val reports = arrayListOf<HIDReport>()

            val vScroll = (this.dy / 10).coerceIn(-127, 127).toByte()
            val hScroll = (this.dx / 10).coerceIn(-127, 127).toByte()

            if (vScroll.toInt() != 0) {
                reports.add(MouseReport(0, 0, 0, vScroll))
            } else if (hScroll.toInt() != 0) {
                reports.add(KeyboardReport((Keycode.MOD_LEFT_SHIFT ushr 8).toByte(), 0)) // Hold Shift
                reports.add(MouseReport(0, 0, 0, hScroll))              // Scroll
                reports.add(KeyboardReport(0, 0))                       // Release Shift
            }

            reports.toTypedArray()
        }

        is InputEvent.KeyPress -> {
            val reports = arrayListOf<HIDReport>()
            // Extract all characters from the typed string
            // and map them using the the selected layout
            val text = String(this.activeBytes, Charsets.UTF_8)
            for (char in text) {
                val mapping = layout.getMapping(char)
                if (mapping != null) {
                    reports.add(KeyboardReport(mapping.modifier, mapping.code)) // KEY pressed
                    reports.add(KeyboardReport(0, 0))           // KEY released
                }
            }
            reports.toTypedArray()
        }

        is InputEvent.Zoom -> {
            // CTRL + mouse wheel rotation
            val zoom = this.scale.coerceIn(-127, 127).toByte()
            if (zoom.toInt() != 0) {
                arrayOf(
                    KeyboardReport((Keycode.MOD_LEFT_CTRL ushr 8).toByte()),   // Press CTRL
                    MouseReport(0, 0, 0, zoom),                 // Scroll
                    KeyboardReport(0)                                 // Release CTRL
                )
            } else {
                emptyArray()
            }
        }
    }
}

/**
 * Translate the input event to raw socket bytes
 */
fun InputEvent.toSocketReport(): ByteArray {
    return when (this) {
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
            val code =
                if (this.button == InputEvent.MouseButton.LEFT) RawSocketEvents.LCLICK else RawSocketEvents.RCLICK
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