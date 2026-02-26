package com.darusc.mousedroid.layouts

/**
 * HID keycodes.
 * https://github.com/NicoHood/HID/blob/master/src/KeyboardLayouts/ImprovedKeylayouts.h
 * and https://www.usb.org/sites/default/files/documents/hut1_12v2.pdf
 */
object Keycode {

    /**
     * HID modifiers. Each modifiers consists of 1 byte
     * Bit 0 - CTRL
     * Bit 1 - SHIFT
     * Bit 2 - ALT
     * Bit 3 - GUI (WIN)
     * Bit 4 - RIGHT CTRL
     * Bit 5 - RIGHT SHIFT
     * Bit 6 - RIGHT ALT
     * Bit 7 - RIGHT GUI
     *
     * Left shifted by 8 bits to allow packing
     */

    const val MOD_NONE          = 0x00.toByte()
    const val MOD_LEFT_CTRL     = 0x01.toByte()
    const val MOD_LEFT_SHIFT    = 0x02.toByte()
    const val MOD_LEFT_ALT      = 0x04.toByte()
    const val MOD_LEFT_GUI      = 0x08.toByte() // (Windows Key)
    const val MOD_RIGHT_CTRL    = 0x10.toByte()
    const val MOD_RIGHT_SHIFT   = 0x20.toByte()
    const val MOD_RIGHT_ALT     = 0x40.toByte()
    const val MOD_RIGHT_GUI     = 0x80.toByte()

    /**
     * HID usage ids
     */

    const val KEY_RESERVED: Byte         = 0x00.toByte()
    const val KEY_ERROR_ROLLOVER: Byte   = 0x01.toByte()
    const val KEY_POST_FAIL: Byte        = 0x02.toByte()
    const val KEY_ERROR_UNDEFINED: Byte  = 0x03.toByte()

    // Letters (a-z)
    const val KEY_A: Byte = 0x04.toByte()
    const val KEY_B: Byte = 0x05.toByte()
    const val KEY_C: Byte = 0x06.toByte()
    const val KEY_D: Byte = 0x07.toByte()
    const val KEY_E: Byte = 0x08.toByte()
    const val KEY_F: Byte = 0x09.toByte()
    const val KEY_G: Byte = 0x0A.toByte()
    const val KEY_H: Byte = 0x0B.toByte()
    const val KEY_I: Byte = 0x0C.toByte()
    const val KEY_J: Byte = 0x0D.toByte()
    const val KEY_K: Byte = 0x0E.toByte()
    const val KEY_L: Byte = 0x0F.toByte()
    const val KEY_M: Byte = 0x10.toByte()
    const val KEY_N: Byte = 0x11.toByte()
    const val KEY_O: Byte = 0x12.toByte()
    const val KEY_P: Byte = 0x13.toByte()
    const val KEY_Q: Byte = 0x14.toByte()
    const val KEY_R: Byte = 0x15.toByte()
    const val KEY_S: Byte = 0x16.toByte()
    const val KEY_T: Byte = 0x17.toByte()
    const val KEY_U: Byte = 0x18.toByte()
    const val KEY_V: Byte = 0x19.toByte()
    const val KEY_W: Byte = 0x1A.toByte()
    const val KEY_X: Byte = 0x1B.toByte()
    const val KEY_Y: Byte = 0x1C.toByte()
    const val KEY_Z: Byte = 0x1D.toByte()

    // Numbers (1-9, 0)
    const val KEY_1: Byte = 0x1E.toByte()
    const val KEY_2: Byte = 0x1F.toByte()
    const val KEY_3: Byte = 0x20.toByte()
    const val KEY_4: Byte = 0x21.toByte()
    const val KEY_5: Byte = 0x22.toByte()
    const val KEY_6: Byte = 0x23.toByte()
    const val KEY_7: Byte = 0x24.toByte()
    const val KEY_8: Byte = 0x25.toByte()
    const val KEY_9: Byte = 0x26.toByte()
    const val KEY_0: Byte = 0x27.toByte()

    // Standard Control Keys
    const val KEY_ENTER: Byte        = 0x28.toByte()
    const val KEY_ESC: Byte          = 0x29.toByte()
    const val KEY_BACKSPACE: Byte    = 0x2A.toByte()
    const val KEY_TAB: Byte          = 0x2B.toByte()
    const val KEY_SPACE: Byte        = 0x2C.toByte()
    const val KEY_MINUS: Byte        = 0x2D.toByte() // - and _
    const val KEY_EQUAL: Byte        = 0x2E.toByte() // = and +
    const val KEY_LEFT_BRACE: Byte   = 0x2F.toByte() // [ and {
    const val KEY_RIGHT_BRACE: Byte  = 0x30.toByte() // ] and }
    const val KEY_BACKSLASH: Byte    = 0x31.toByte() // \ and |
    const val KEY_NON_US_NUM: Byte   = 0x32.toByte() // Non-US # and ~
    const val KEY_SEMICOLON: Byte    = 0x33.toByte() // ; and :
    const val KEY_QUOTE: Byte        = 0x34.toByte() // ' and "
    const val KEY_GRAVE: Byte        = 0x35.toByte() // ` and ~
    const val KEY_COMMA: Byte        = 0x36.toByte() // , and <
    const val KEY_DOT: Byte          = 0x37.toByte() // . and >
    const val KEY_SLASH: Byte        = 0x38.toByte() // / and ?
    const val KEY_CAPS_LOCK: Byte    = 0x39.toByte()

    // Function Keys
    const val KEY_F1: Byte  = 0x3A.toByte()
    const val KEY_F2: Byte  = 0x3B.toByte()
    const val KEY_F3: Byte  = 0x3C.toByte()
    const val KEY_F4: Byte  = 0x3D.toByte()
    const val KEY_F5: Byte  = 0x3E.toByte()
    const val KEY_F6: Byte  = 0x3F.toByte()
    const val KEY_F7: Byte  = 0x40.toByte()
    const val KEY_F8: Byte  = 0x41.toByte()
    const val KEY_F9: Byte  = 0x42.toByte()
    const val KEY_F10: Byte = 0x43.toByte()
    const val KEY_F11: Byte = 0x44.toByte()
    const val KEY_F12: Byte = 0x45.toByte()

    // Navigation & Editing
    const val KEY_PRByte_SCREEN: Byte = 0x46.toByte()
    const val KEY_SCROLL_LOCK: Byte  = 0x47.toByte()
    const val KEY_PAUSE: Byte        = 0x48.toByte()
    const val KEY_INSERT: Byte       = 0x49.toByte()
    const val KEY_HOME: Byte         = 0x4A.toByte()
    const val KEY_PAGE_UP: Byte      = 0x4B.toByte()
    const val KEY_DELETE: Byte       = 0x4C.toByte()
    const val KEY_END: Byte          = 0x4D.toByte()
    const val KEY_PAGE_DOWN: Byte    = 0x4E.toByte()
    const val KEY_RIGHT: Byte        = 0x4F.toByte()
    const val KEY_LEFT: Byte         = 0x50.toByte()
    const val KEY_DOWN: Byte         = 0x51.toByte()
    const val KEY_UP: Byte           = 0x52.toByte()

    // Keypad (Numpad)
    const val KEY_NUM_LOCK: Byte     = 0x53.toByte()
    const val KEYPAD_DIVIDE: Byte    = 0x54.toByte()
    const val KEYPAD_MULTIPLY: Byte  = 0x55.toByte()
    const val KEYPAD_SUBTRACT: Byte  = 0x56.toByte()
    const val KEYPAD_ADD: Byte       = 0x57.toByte()
    const val KEYPAD_ENTER: Byte     = 0x58.toByte()
    const val KEYPAD_1: Byte = 0x59.toByte()
    const val KEYPAD_2: Byte = 0x5A.toByte()
    const val KEYPAD_3: Byte = 0x5B.toByte()
    const val KEYPAD_4: Byte = 0x5C.toByte()
    const val KEYPAD_5: Byte = 0x5D.toByte()
    const val KEYPAD_6: Byte = 0x5E.toByte()
    const val KEYPAD_7: Byte = 0x5F.toByte()
    const val KEYPAD_8: Byte = 0x60.toByte()
    const val KEYPAD_9: Byte = 0x61.toByte()
    const val KEYPAD_0: Byte = 0x62.toByte()
    const val KEYPAD_DOT: Byte = 0x63.toByte()

    // Misc
    const val KEY_NON_US: Byte       = 0x64.toByte()
    const val KEY_APPLICATION: Byte  = 0x65.toByte()
    const val KEY_POWER: Byte        = 0x66.toByte()
    const val KEY_PAD_EQUALS: Byte   = 0x67.toByte()

    // F13 - F24
    const val KEY_F13: Byte = 0x68.toByte()
    const val KEY_F14: Byte = 0x69.toByte()
    const val KEY_F15: Byte = 0x6A.toByte()
    const val KEY_F16: Byte = 0x6B.toByte()
    const val KEY_F17: Byte = 0x6C.toByte()
    const val KEY_F18: Byte = 0x6D.toByte()
    const val KEY_F19: Byte = 0x6E.toByte()
    const val KEY_F20: Byte = 0x6F.toByte()
    const val KEY_F21: Byte = 0x70.toByte()
    const val KEY_F22: Byte = 0x71.toByte()
    const val KEY_F23: Byte = 0x72.toByte()
    const val KEY_F24: Byte = 0x73.toByte()

    // Multimedia / Extended
    const val KEY_EXECUTE: Byte      = 0x74.toByte()
    const val KEY_HELP: Byte         = 0x75.toByte()
    const val KEY_MENU: Byte         = 0x76.toByte()
    const val KEY_SELECT: Byte       = 0x77.toByte()
    const val KEY_STOP: Byte         = 0x78.toByte()
    const val KEY_AGAIN: Byte        = 0x79.toByte()
    const val KEY_UNDO: Byte         = 0x7A.toByte()
    const val KEY_CUT: Byte          = 0x7B.toByte()
    const val KEY_COPY: Byte         = 0x7C.toByte()
    const val KEY_PASTE: Byte        = 0x7D.toByte()
    const val KEY_FIND: Byte         = 0x7E.toByte()
    const val KEY_MUTE: Byte         = 0x7F.toByte()
    const val KEY_VOLUME_UP: Byte    = 0x80.toByte()
    const val KEY_VOLUME_DOWN: Byte  = 0x81.toByte()

    // Modifier HID Usages (If used as keys themselves)
    const val KEY_LEFT_CTRL: Byte    = 0xE0.toByte()
    const val KEY_LEFT_SHIFT: Byte   = 0xE1.toByte()
    const val KEY_LEFT_ALT: Byte     = 0xE2.toByte()
    const val KEY_LEFT_GUI: Byte     = 0xE3.toByte()
    const val KEY_RIGHT_CTRL: Byte   = 0xE4.toByte()
    const val KEY_RIGHT_SHIFT: Byte  = 0xE5.toByte()
    const val KEY_RIGHT_ALT: Byte    = 0xE6.toByte()
    const val KEY_RIGHT_GUI: Byte    = 0xE7.toByte()
}