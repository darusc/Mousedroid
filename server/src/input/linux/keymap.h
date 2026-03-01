#ifndef KEYMAP_LINUX_H
#define KEYMAP_LINUX_H

#ifdef __unix__

#include <map>
#include <stdint.h>

namespace InputManager
{
    /**
     * @brief Mapping of USB HID keycodes to Linux Input Event codes (KEY_*).
     */
    static const std::map<uint8_t, uint16_t> KEYMAP =
    {
        // Letters A-Z
        {0x04, KEY_A},
        {0x05, KEY_B},
        {0x06, KEY_C},
        {0x07, KEY_D},
        {0x08, KEY_E},
        {0x09, KEY_F},
        {0x0A, KEY_G},
        {0x0B, KEY_H},
        {0x0C, KEY_I},
        {0x0D, KEY_J},
        {0x0E, KEY_K},
        {0x0F, KEY_L},
        {0x10, KEY_M},
        {0x11, KEY_N},
        {0x12, KEY_O},
        {0x13, KEY_P},
        {0x14, KEY_Q},
        {0x15, KEY_R},
        {0x16, KEY_S},
        {0x17, KEY_T},
        {0x18, KEY_U},
        {0x19, KEY_V},
        {0x1A, KEY_W},
        {0x1B, KEY_X},
        {0x1C, KEY_Y},
        {0x1D, KEY_Z},

        // Numbers (1-9, 0)
        {0x1E, KEY_1},
        {0x1F, KEY_2},
        {0x20, KEY_3},
        {0x21, KEY_4},
        {0x22, KEY_5},
        {0x23, KEY_6},
        {0x24, KEY_7},
        {0x25, KEY_8},
        {0x26, KEY_9},
        {0x27, KEY_0},

        // Standard Control & Punctuation
        {0x28, KEY_ENTER},
        {0x29, KEY_ESC},
        {0x2A, KEY_BACKSPACE},
        {0x2B, KEY_TAB},
        {0x2C, KEY_SPACE},
        {0x2D, KEY_MINUS},
        {0x2E, KEY_EQUAL},
        {0x2F, KEY_LEFTBRACE},
        {0x30, KEY_RIGHTBRACE},
        {0x31, KEY_BACKSLASH},
        {0x32, KEY_BACKSLASH}, // Non-US # and ~
        {0x33, KEY_SEMICOLON},
        {0x34, KEY_APOSTROPHE},
        {0x35, KEY_GRAVE},
        {0x36, KEY_COMMA},
        {0x37, KEY_DOT},
        {0x38, KEY_SLASH},
        {0x39, KEY_CAPSLOCK},

        // Function Keys (F1-F12)
        {0x3A, KEY_F1},
        {0x3B, KEY_F2},
        {0x3C, KEY_F3},
        {0x3D, KEY_F4},
        {0x3E, KEY_F5},
        {0x3F, KEY_F6},
        {0x40, KEY_F7},
        {0x41, KEY_F8},
        {0x42, KEY_F9},
        {0x43, KEY_F10},
        {0x44, KEY_F11},
        {0x45, KEY_F12},

        // Navigation & Editing
        {0x46, KEY_SYSRQ}, // Print Screen
        {0x47, KEY_SCROLLLOCK},
        {0x48, KEY_PAUSE},
        {0x49, KEY_INSERT},
        {0x4A, KEY_HOME},
        {0x4B, KEY_PAGEUP},
        {0x4C, KEY_DELETE},
        {0x4D, KEY_END},
        {0x4E, KEY_PAGEDOWN},
        {0x4F, KEY_RIGHT},
        {0x50, KEY_LEFT},
        {0x51, KEY_DOWN},
        {0x52, KEY_UP},

        // Numpad
        {0x53, KEY_NUMLOCK},
        {0x54, KEY_KPSLASH},
        {0x55, KEY_KPASTERISK},
        {0x56, KEY_KPMINUS},
        {0x57, KEY_KPPLUS},
        {0x58, KEY_KPENTER},
        {0x59, KEY_KP1},
        {0x5A, KEY_KP2},
        {0x5B, KEY_KP3},
        {0x5C, KEY_KP4},
        {0x5D, KEY_KP5},
        {0x5E, KEY_KP6},
        {0x5F, KEY_KP7},
        {0x60, KEY_KP8},
        {0x61, KEY_KP9},
        {0x62, KEY_KP0},
        {0x63, KEY_KPDOT},

        // F13-F24
        {0x68, KEY_F13},
        {0x69, KEY_F14},
        {0x6A, KEY_F15},
        {0x6B, KEY_F16},
        {0x6C, KEY_F17},
        {0x6D, KEY_F18},
        {0x6E, KEY_F19},
        {0x6F, KEY_F20},
        {0x70, KEY_F21},
        {0x71, KEY_F22},
        {0x72, KEY_F23},
        {0x73, KEY_F24},

        // Multimedia bits in KEYMAP
        {0x7F, KEY_MUTE},
        {0x80, KEY_VOLUMEDOWN},
        {0x81, KEY_VOLUMEUP},

        // Modifiers (Standalone)
        {0xE0, KEY_LEFTCTRL},
        {0xE1, KEY_LEFTSHIFT},
        {0xE2, KEY_LEFTALT},
        {0xE3, KEY_LEFTMETA}, // Windows Key
        {0xE4, KEY_RIGHTCTRL},
        {0xE5, KEY_RIGHTSHIFT},
        {0xE6, KEY_RIGHTALT},
        {0xE7, KEY_RIGHTMETA}
    };

    static const std::map<uint8_t, uint16_t> MEDIAMAP =
    {
        {0b00000001, KEY_FORWARD},
        {0b00000010, KEY_BACK},
        {0b00000100, KEY_NEXTSONG},
        {0b00001000, KEY_PREVIOUSSONG},
        {0b00010000, KEY_PLAYPAUSE},
        {0b00100000, KEY_MUTE},
        {0b01000000, KEY_VOLUMEUP},
        {0b10000000, KEY_VOLUMEDOWN},
    };

    static const std::map<uint8_t, uint16_t> MODMAP =
    {
        {0x01, KEY_LEFTCTRL},
        {0x02, KEY_LEFTSHIFT},
        {0x04, KEY_LEFTALT},
        {0x08, KEY_LEFTMETA},
        {0x10, KEY_RIGHTCTRL},
        {0x20, KEY_RIGHTSHIFT},
        {0x40, KEY_RIGHTALT},
        {0x80, KEY_RIGHTMETA}
    };
}

#endif
#endif