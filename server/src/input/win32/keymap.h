#ifndef KEYMAP_WIN32_H
#define KEYMAP_WIN32_H

#include "windows.h"
#include <map>
#include <stdint.h>

namespace InputManager
{
    /**
     * @brief Mapping of USB HID keycodes to Windows virtual key codes.
     */
    static const std::map<uint8_t, uint8_t> KEYMAP =
    {
        // Letters A-Z
        {0x04, 0x41},
        {0x05, 0x42},
        {0x06, 0x43},
        {0x07, 0x44},
        {0x08, 0x45},
        {0x09, 0x46},
        {0x0A, 0x47},
        {0x0B, 0x48},
        {0x0C, 0x49},
        {0x0D, 0x4A},
        {0x0E, 0x4B},
        {0x0F, 0x4C},
        {0x10, 0x4D},
        {0x11, 0x4E},
        {0x12, 0x4F},
        {0x13, 0x50},
        {0x14, 0x51},
        {0x15, 0x52},
        {0x16, 0x53},
        {0x17, 0x54},
        {0x18, 0x55},
        {0x19, 0x56},
        {0x1A, 0x57},
        {0x1B, 0x58},
        {0x1C, 0x59},
        {0x1D, 0x5A},

        // Numbers (1-9, 0)
        {0x1E, 0x31},
        {0x1F, 0x32},
        {0x20, 0x33},
        {0x21, 0x34},
        {0x22, 0x35},
        {0x23, 0x36},
        {0x24, 0x37},
        {0x25, 0x38},
        {0x26, 0x39},
        {0x27, 0x30},

        // Standard Control & Punctuation
        {0x28, VK_RETURN},
        {0x29, VK_ESCAPE},
        {0x2A, VK_BACK},
        {0x2B, VK_TAB},
        {0x2C, VK_SPACE},
        {0x2D, VK_OEM_MINUS},
        {0x2E, VK_OEM_PLUS},
        {0x2F, VK_OEM_4},
        {0x30, VK_OEM_6},
        {0x31, VK_OEM_5}, // [ ]
        {0x32, VK_OEM_2},
        {0x33, VK_OEM_1},
        {0x34, VK_OEM_7}, // # ; '
        {0x35, VK_OEM_3},
        {0x36, VK_OEM_COMMA},
        {0x37, VK_OEM_PERIOD},
        {0x38, VK_OEM_2},
        {0x39, VK_CAPITAL}, // / and Caps

        // Function Keys (F1-F12)
        {0x3A, VK_F1},
        {0x3B, VK_F2},
        {0x3C, VK_F3},
        {0x3D, VK_F4},
        {0x3E, VK_F5},
        {0x3F, VK_F6},
        {0x40, VK_F7},
        {0x41, VK_F8},
        {0x42, VK_F9},
        {0x43, VK_F10},
        {0x44, VK_F11},
        {0x45, VK_F12},

        // Navigation & Editing
        {0x46, VK_SNAPSHOT},
        {0x47, VK_SCROLL},
        {0x48, VK_PAUSE},
        {0x49, VK_INSERT},
        {0x4A, VK_HOME},
        {0x4B, VK_PRIOR},
        {0x4C, VK_DELETE},
        {0x4D, VK_END},
        {0x4E, VK_NEXT},
        {0x4F, VK_RIGHT},
        {0x50, VK_LEFT},
        {0x51, VK_DOWN},
        {0x52, VK_UP},

        // Numpad
        {0x53, VK_NUMLOCK},
        {0x54, VK_DIVIDE},
        {0x55, VK_MULTIPLY},
        {0x56, VK_SUBTRACT},
        {0x57, VK_ADD},
        {0x58, VK_RETURN},
        {0x59, VK_NUMPAD1},
        {0x5A, VK_NUMPAD2},
        {0x5B, VK_NUMPAD3},
        {0x5C, VK_NUMPAD4},
        {0x5D, VK_NUMPAD5},
        {0x5E, VK_NUMPAD6},
        {0x5F, VK_NUMPAD7},
        {0x60, VK_NUMPAD8},
        {0x61, VK_NUMPAD9},
        {0x62, VK_NUMPAD0},
        {0x63, VK_DECIMAL},

        // Misc & Extended
        {0x64, VK_OEM_102},
        {0x65, VK_APPS},
        {0x66, VK_SLEEP},
        {0x67, VK_OEM_PLUS},

        // F13-F24
        {0x68, VK_F13},
        {0x69, VK_F14},
        {0x6A, VK_F15},
        {0x6B, VK_F16},
        {0x6C, VK_F17},
        {0x6D, VK_F18},
        {0x6E, VK_F19},
        {0x6F, VK_F20},
        {0x70, VK_F21},
        {0x71, VK_F22},
        {0x72, VK_F23},
        {0x73, VK_F24},

        // Multimedia
        {0x74, VK_EXECUTE},
        {0x75, VK_HELP},
        {0x76, VK_MENU},
        {0x77, VK_SELECT},
        {0x7F, VK_VOLUME_MUTE},
        {0x80, VK_VOLUME_UP},
        {0x81, VK_VOLUME_DOWN},

        // Modifier Usages (Sent as standalone keys)
        {0xE0, VK_LCONTROL},
        {0xE1, VK_LSHIFT},
        {0xE2, VK_LMENU},
        {0xE3, VK_LWIN},
        {0xE4, VK_RCONTROL},
        {0xE5, VK_RSHIFT},
        {0xE6, VK_RMENU},
        {0xE7, VK_RWIN}
    };

    static const std::map<uint8_t, uint8_t> MEDIAMAP =
    {
        {0b00000001, VK_RIGHT},
        {0b00000010, VK_LEFT},
        {0b00000100, VK_MEDIA_NEXT_TRACK},
        {0b00001000, VK_MEDIA_PREV_TRACK},
        {0b00010000, VK_MEDIA_PLAY_PAUSE},
        {0b00100000, VK_VOLUME_MUTE},
        {0b01000000, VK_VOLUME_UP},
        {0b10000000, VK_VOLUME_DOWN},
    };

    /**
     * @brief Mapping of USB HID modifier bits to Windows virtual key codes.
     */
    static const std::map<uint8_t, uint8_t> MODMAP = 
    {
        {0x01, VK_LCONTROL}, 
        {0x02, VK_LSHIFT}, 
        {0x04, VK_LMENU},  
        {0x08, VK_LWIN},
        {0x10, VK_RCONTROL}, 
        {0x20, VK_RSHIFT}, 
        {0x40, VK_RMENU},  
        {0x80, VK_RWIN}
    };
}

#endif