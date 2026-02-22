package com.darusc.mousedroid.networking

import android.util.Log
import com.darusc.mousedroid.layouts.KeyboardLayout
import com.darusc.mousedroid.layouts.Keycode
import com.darusc.mousedroid.mkinput.InputEvent
import com.darusc.mousedroid.networking.bluetooth.HIDReport
import com.darusc.mousedroid.networking.bluetooth.KeyboardReport
import com.darusc.mousedroid.networking.bluetooth.MediaReport
import com.darusc.mousedroid.networking.bluetooth.MouseReport

/**
 * HID usage IDs used in translating the events into raw bytes for the HID protocol
 * are taken from the official usb specification
 *
 * https://www.usb.org/sites/default/files/documents/hut1_12v2.pdf
 */


/**
 * Socket events for the TCP/UDP communication
 */
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
 * Transforms a media action into its corresponding bitmask.
 * The activated bit is the corresponding to the position
 * of the action in the descriptor listing
 */
private fun getMediaActionHIDBitmask(action: InputEvent.MediaAction): Short {
    return when(action) {
        InputEvent.MediaAction.FORWARD       -> 0b0000000000000001 // First in the descriptor listing
        InputEvent.MediaAction.REPLAY        -> 0b0000000000000010 // Second in the descriptor listing
        InputEvent.MediaAction.NEXT          -> 0b0000000000000100 // ...
        InputEvent.MediaAction.PREVIOUS      -> 0b0000000000001000
        InputEvent.MediaAction.PLAY_PAUSE    -> 0b0000000000010000
        InputEvent.MediaAction.VOLUME_MUTE   -> 0b0000000000100000
        InputEvent.MediaAction.VOLUME_UP     -> 0b0000000001000000
        InputEvent.MediaAction.VOLUME_DOWN   -> 0b0000000010000000
    }
}

private fun getNumpadKeyHIDUsageID(numpadKey: InputEvent.NumpadKey): Byte {
    return when(numpadKey) {
        InputEvent.NumpadKey.NUM1 -> 0x59.toByte()
        InputEvent.NumpadKey.NUM2 -> 0x5A.toByte()
        InputEvent.NumpadKey.NUM3 -> 0x5B.toByte()
        InputEvent.NumpadKey.NUM4 -> 0x5C.toByte()
        InputEvent.NumpadKey.NUM5 -> 0x5D.toByte()
        InputEvent.NumpadKey.NUM6 -> 0x5E.toByte()
        InputEvent.NumpadKey.NUM7 -> 0x5F.toByte()
        InputEvent.NumpadKey.NUM8 -> 0x60.toByte()
        InputEvent.NumpadKey.NUM9 -> 0x61.toByte()
        InputEvent.NumpadKey.NUM0 -> 0x62.toByte()
        InputEvent.NumpadKey.NUM_DOT -> 0x63.toByte()
        InputEvent.NumpadKey.NUM_ENTER -> 0x58.toByte()
        InputEvent.NumpadKey.NUM_PLUS  -> 0x57.toByte()
        InputEvent.NumpadKey.NUM_MINUS -> 0x56.toByte()
        InputEvent.NumpadKey.NUM_MULTIPLY -> 0x55.toByte()
        InputEvent.NumpadKey.NUM_DIV -> 0x54.toByte()
        InputEvent.NumpadKey.NUM_DEL -> 0x2A.toByte()
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

        is InputEvent.NumpadKeyPress -> {
            val usageid = getNumpadKeyHIDUsageID(this.key)
            arrayOf(
                KeyboardReport(0x00, usageid),
                KeyboardReport(0)
            )
        }

        is InputEvent.MediaEvent -> {
            val bitmask = getMediaActionHIDBitmask(this.action)
            arrayOf(MediaReport(bitmask), MediaReport(0))
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

        else -> byteArrayOf()
    }
}