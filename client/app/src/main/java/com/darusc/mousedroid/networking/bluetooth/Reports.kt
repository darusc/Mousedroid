package com.darusc.mousedroid.networking.bluetooth

/**
 * Base HID Report class
 */
open class HIDReport(val bytes: ByteArray, val id: Int)

/**
 * Mouse HID report
 * @param button Code of the pressed button
 * @param dx Movement on X axis
 * @param dy Movement on Y axis
 * @param dw Mouse wheel movement
 */
class MouseReport(
    button: Byte,
    dx: Byte,
    dy: Byte,
    dw: Byte
): HIDReport(
    byteArrayOf(button, dx, dy, dw),
    REPORT_ID_MOUSE
)

/**
 * Keyboard HID report
 * @param modifier Bitmask for modifier keys (CTRL, ALT, ...)
 * @param keys HID usage ID of up to 6 keys pressed at once
 */
class KeyboardReport(
    modifier: Byte,
    vararg keys: Byte
): HIDReport(
    byteArrayOf(modifier, 0x00, *keys.copyOf(6)),
    REPORT_ID_KEYBOARD
)