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

    const val MOD_NONE          = 0x0000
    const val MOD_LEFT_CTRL     = 0x0100 // (1 << 8)
    const val MOD_LEFT_SHIFT    = 0x0200 // (1 << 9)
    const val MOD_LEFT_ALT      = 0x0400 // (1 << 10)
    const val MOD_LEFT_GUI      = 0x0800 // (1 << 11)  (Windows Key)
    const val MOD_RIGHT_CTRL    = 0x1000 // (1 << 12)
    const val MOD_RIGHT_SHIFT   = 0x2000 // (1 << 13)
    const val MOD_RIGHT_ALT     = 0x4000 // (1 << 14)  (AltGr)
    const val MOD_RIGHT_GUI     = 0x8000 // (1 << 15)

    /**
     * HID usage ids
     */

    const val KEY_RESERVED: Int         = 0x00
    const val KEY_ERROR_ROLLOVER: Int   = 0x01
    const val KEY_POST_FAIL: Int        = 0x02
    const val KEY_ERROR_UNDEFINED: Int  = 0x03

    // Letters (a-z)
    const val KEY_A: Int = 0x04
    const val KEY_B: Int = 0x05
    const val KEY_C: Int = 0x06
    const val KEY_D: Int = 0x07
    const val KEY_E: Int = 0x08
    const val KEY_F: Int = 0x09
    const val KEY_G: Int = 0x0A
    const val KEY_H: Int = 0x0B
    const val KEY_I: Int = 0x0C
    const val KEY_J: Int = 0x0D
    const val KEY_K: Int = 0x0E
    const val KEY_L: Int = 0x0F
    const val KEY_M: Int = 0x10
    const val KEY_N: Int = 0x11
    const val KEY_O: Int = 0x12
    const val KEY_P: Int = 0x13
    const val KEY_Q: Int = 0x14
    const val KEY_R: Int = 0x15
    const val KEY_S: Int = 0x16
    const val KEY_T: Int = 0x17
    const val KEY_U: Int = 0x18
    const val KEY_V: Int = 0x19
    const val KEY_W: Int = 0x1A
    const val KEY_X: Int = 0x1B
    const val KEY_Y: Int = 0x1C
    const val KEY_Z: Int = 0x1D

    // Numbers (1-9, 0)
    const val KEY_1: Int = 0x1E
    const val KEY_2: Int = 0x1F
    const val KEY_3: Int = 0x20
    const val KEY_4: Int = 0x21
    const val KEY_5: Int = 0x22
    const val KEY_6: Int = 0x23
    const val KEY_7: Int = 0x24
    const val KEY_8: Int = 0x25
    const val KEY_9: Int = 0x26
    const val KEY_0: Int = 0x27

    // Standard Control Keys
    const val KEY_ENTER: Int        = 0x28
    const val KEY_ESC: Int          = 0x29
    const val KEY_BACKSPACE: Int    = 0x2A
    const val KEY_TAB: Int          = 0x2B
    const val KEY_SPACE: Int        = 0x2C
    const val KEY_MINUS: Int        = 0x2D // - and _
    const val KEY_EQUAL: Int        = 0x2E // = and +
    const val KEY_LEFT_BRACE: Int   = 0x2F // [ and {
    const val KEY_RIGHT_BRACE: Int  = 0x30 // ] and }
    const val KEY_BACKSLASH: Int    = 0x31 // \ and |
    const val KEY_NON_US_NUM: Int   = 0x32 // Non-US # and ~
    const val KEY_SEMICOLON: Int    = 0x33 // ; and :
    const val KEY_QUOTE: Int        = 0x34 // ' and "
    const val KEY_GRAVE: Int        = 0x35 // ` and ~
    const val KEY_COMMA: Int        = 0x36 // , and <
    const val KEY_DOT: Int          = 0x37 // . and >
    const val KEY_SLASH: Int        = 0x38 // / and ?
    const val KEY_CAPS_LOCK: Int    = 0x39

    // Function Keys
    const val KEY_F1: Int  = 0x3A
    const val KEY_F2: Int  = 0x3B
    const val KEY_F3: Int  = 0x3C
    const val KEY_F4: Int  = 0x3D
    const val KEY_F5: Int  = 0x3E
    const val KEY_F6: Int  = 0x3F
    const val KEY_F7: Int  = 0x40
    const val KEY_F8: Int  = 0x41
    const val KEY_F9: Int  = 0x42
    const val KEY_F10: Int = 0x43
    const val KEY_F11: Int = 0x44
    const val KEY_F12: Int = 0x45

    // Navigation & Editing
    const val KEY_PRINT_SCREEN: Int = 0x46
    const val KEY_SCROLL_LOCK: Int  = 0x47
    const val KEY_PAUSE: Int        = 0x48
    const val KEY_INSERT: Int       = 0x49
    const val KEY_HOME: Int         = 0x4A
    const val KEY_PAGE_UP: Int      = 0x4B
    const val KEY_DELETE: Int       = 0x4C
    const val KEY_END: Int          = 0x4D
    const val KEY_PAGE_DOWN: Int    = 0x4E
    const val KEY_RIGHT: Int        = 0x4F
    const val KEY_LEFT: Int         = 0x50
    const val KEY_DOWN: Int         = 0x51
    const val KEY_UP: Int           = 0x52

    // Keypad (Numpad)
    const val KEY_NUM_LOCK: Int     = 0x53
    const val KEYPAD_DIVIDE: Int    = 0x54
    const val KEYPAD_MULTIPLY: Int  = 0x55
    const val KEYPAD_SUBTRACT: Int  = 0x56
    const val KEYPAD_ADD: Int       = 0x57
    const val KEYPAD_ENTER: Int     = 0x58
    const val KEYPAD_1: Int = 0x59
    const val KEYPAD_2: Int = 0x5A
    const val KEYPAD_3: Int = 0x5B
    const val KEYPAD_4: Int = 0x5C
    const val KEYPAD_5: Int = 0x5D
    const val KEYPAD_6: Int = 0x5E
    const val KEYPAD_7: Int = 0x5F
    const val KEYPAD_8: Int = 0x60
    const val KEYPAD_9: Int = 0x61
    const val KEYPAD_0: Int = 0x62
    const val KEYPAD_DOT: Int = 0x63

    // Misc
    const val KEY_NON_US: Int       = 0x64
    const val KEY_APPLICATION: Int  = 0x65
    const val KEY_POWER: Int        = 0x66
    const val KEY_PAD_EQUALS: Int   = 0x67

    // F13 - F24
    const val KEY_F13: Int = 0x68
    const val KEY_F14: Int = 0x69
    const val KEY_F15: Int = 0x6A
    const val KEY_F16: Int = 0x6B
    const val KEY_F17: Int = 0x6C
    const val KEY_F18: Int = 0x6D
    const val KEY_F19: Int = 0x6E
    const val KEY_F20: Int = 0x6F
    const val KEY_F21: Int = 0x70
    const val KEY_F22: Int = 0x71
    const val KEY_F23: Int = 0x72
    const val KEY_F24: Int = 0x73

    // Multimedia / Extended
    const val KEY_EXECUTE: Int      = 0x74
    const val KEY_HELP: Int         = 0x75
    const val KEY_MENU: Int         = 0x76
    const val KEY_SELECT: Int       = 0x77
    const val KEY_STOP: Int         = 0x78
    const val KEY_AGAIN: Int        = 0x79
    const val KEY_UNDO: Int         = 0x7A
    const val KEY_CUT: Int          = 0x7B
    const val KEY_COPY: Int         = 0x7C
    const val KEY_PASTE: Int        = 0x7D
    const val KEY_FIND: Int         = 0x7E
    const val KEY_MUTE: Int         = 0x7F
    const val KEY_VOLUME_UP: Int    = 0x80
    const val KEY_VOLUME_DOWN: Int  = 0x81

    // Modifier HID Usages (If used as keys themselves)
    const val KEY_LEFT_CTRL: Int    = 0xE0
    const val KEY_LEFT_SHIFT: Int   = 0xE1
    const val KEY_LEFT_ALT: Int     = 0xE2
    const val KEY_LEFT_GUI: Int     = 0xE3
    const val KEY_RIGHT_CTRL: Int   = 0xE4
    const val KEY_RIGHT_SHIFT: Int  = 0xE5
    const val KEY_RIGHT_ALT: Int    = 0xE6
    const val KEY_RIGHT_GUI: Int    = 0xE7
}