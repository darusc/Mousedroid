package com.darusc.mousedroid.layouts.languages

import com.darusc.mousedroid.layouts.KeyboardLayout
import com.darusc.mousedroid.layouts.Keycode

class KeyboardLayoutUS : KeyboardLayout() {

    companion object {
        const val NAME = "English (US)"
    }

    override val name = NAME

    override val charMap = mapOf(
        // --- Control Characters ---
        '\u007F' to Key(Keycode.MOD_NONE, Keycode.KEY_BACKSPACE), // DEL (127)
        '\b'     to Key(Keycode.MOD_NONE, Keycode.KEY_BACKSPACE), // Backspace (8)
        '\n'     to Key(Keycode.MOD_NONE, Keycode.KEY_ENTER),     // Enter (10)
        '\r'     to Key(Keycode.MOD_NONE, Keycode.KEY_ENTER),     // CR (13)
        '\t'     to Key(Keycode.MOD_NONE, Keycode.KEY_TAB),       // Tab (9)
        '\u001B' to Key(Keycode.MOD_NONE, Keycode.KEY_ESC),       // Esc (27)

        // --- Space ---
        ' ' to Key(Keycode.MOD_NONE, Keycode.KEY_SPACE),

        // --- Numbers and their Shifted Symbols ---
        '1' to Key(Keycode.MOD_NONE, Keycode.KEY_1),
        '!' to Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_1),
        '2' to Key(Keycode.MOD_NONE, Keycode.KEY_2),
        '@' to Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_2),
        '3' to Key(Keycode.MOD_NONE, Keycode.KEY_3),
        '#' to Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_3),
        '4' to Key(Keycode.MOD_NONE, Keycode.KEY_4),
        '$' to Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_4),
        '5' to Key(Keycode.MOD_NONE, Keycode.KEY_5),
        '%' to Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_5),
        '6' to Key(Keycode.MOD_NONE, Keycode.KEY_6),
        '^' to Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_6),
        '7' to Key(Keycode.MOD_NONE, Keycode.KEY_7),
        '&' to Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_7),
        '8' to Key(Keycode.MOD_NONE, Keycode.KEY_8),
        '*' to Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_8),
        '9' to Key(Keycode.MOD_NONE, Keycode.KEY_9),
        '(' to Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_9),
        '0' to Key(Keycode.MOD_NONE, Keycode.KEY_0),
        ')' to Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_0),

        // --- Lowercase Letters ---
        'a' to Key(Keycode.MOD_NONE, Keycode.KEY_A),
        'b' to Key(Keycode.MOD_NONE, Keycode.KEY_B),
        'c' to Key(Keycode.MOD_NONE, Keycode.KEY_C),
        'd' to Key(Keycode.MOD_NONE, Keycode.KEY_D),
        'e' to Key(Keycode.MOD_NONE, Keycode.KEY_E),
        'f' to Key(Keycode.MOD_NONE, Keycode.KEY_F),
        'g' to Key(Keycode.MOD_NONE, Keycode.KEY_G),
        'h' to Key(Keycode.MOD_NONE, Keycode.KEY_H),
        'i' to Key(Keycode.MOD_NONE, Keycode.KEY_I),
        'j' to Key(Keycode.MOD_NONE, Keycode.KEY_J),
        'k' to Key(Keycode.MOD_NONE, Keycode.KEY_K),
        'l' to Key(Keycode.MOD_NONE, Keycode.KEY_L),
        'm' to Key(Keycode.MOD_NONE, Keycode.KEY_M),
        'n' to Key(Keycode.MOD_NONE, Keycode.KEY_N),
        'o' to Key(Keycode.MOD_NONE, Keycode.KEY_O),
        'p' to Key(Keycode.MOD_NONE, Keycode.KEY_P),
        'q' to Key(Keycode.MOD_NONE, Keycode.KEY_Q),
        'r' to Key(Keycode.MOD_NONE, Keycode.KEY_R),
        's' to Key(Keycode.MOD_NONE, Keycode.KEY_S),
        't' to Key(Keycode.MOD_NONE, Keycode.KEY_T),
        'u' to Key(Keycode.MOD_NONE, Keycode.KEY_U),
        'v' to Key(Keycode.MOD_NONE, Keycode.KEY_V),
        'w' to Key(Keycode.MOD_NONE, Keycode.KEY_W),
        'x' to Key(Keycode.MOD_NONE, Keycode.KEY_X),
        'y' to Key(Keycode.MOD_NONE, Keycode.KEY_Y),
        'z' to Key(Keycode.MOD_NONE, Keycode.KEY_Z),

        // --- Uppercase Letters ---
        'A' to Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_A),
        'B' to Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_B),
        'C' to Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_C),
        'D' to Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_D),
        'E' to Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_E),
        'F' to Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_F),
        'G' to Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_G),
        'H' to Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_H),
        'I' to Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_I),
        'J' to Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_J),
        'K' to Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_K),
        'L' to Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_L),
        'M' to Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_M),
        'N' to Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_N),
        'O' to Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_O),
        'P' to Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_P),
        'Q' to Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_Q),
        'R' to Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_R),
        'S' to Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_S),
        'T' to Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_T),
        'U' to Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_U),
        'V' to Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_V),
        'W' to Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_W),
        'X' to Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_X),
        'Y' to Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_Y),
        'Z' to Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_Z),

        // --- Symbols ---
        '-' to Key(Keycode.MOD_NONE, Keycode.KEY_MINUS),
        '_' to Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_MINUS),
        '=' to Key(Keycode.MOD_NONE, Keycode.KEY_EQUAL),
        '+' to Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_EQUAL),
        '[' to Key(Keycode.MOD_NONE, Keycode.KEY_LEFT_BRACE),
        '{' to Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_LEFT_BRACE),
        ']' to Key(Keycode.MOD_NONE, Keycode.KEY_RIGHT_BRACE),
        '}' to Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_RIGHT_BRACE),
        '\\' to Key(Keycode.MOD_NONE, Keycode.KEY_BACKSLASH),
        '|' to Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_BACKSLASH),
        ';' to Key(Keycode.MOD_NONE, Keycode.KEY_SEMICOLON),
        ':' to Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_SEMICOLON),
        '\'' to Key(Keycode.MOD_NONE, Keycode.KEY_QUOTE),
        '"' to Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_QUOTE),
        '`' to Key(Keycode.MOD_NONE, Keycode.KEY_GRAVE),
        '~' to Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_GRAVE),
        ',' to Key(Keycode.MOD_NONE, Keycode.KEY_COMMA),
        '<' to Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_COMMA),
        '.' to Key(Keycode.MOD_NONE, Keycode.KEY_DOT),
        '>' to Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_DOT),
        '/' to Key(Keycode.MOD_NONE, Keycode.KEY_SLASH),
        '?' to Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_SLASH)
    )
}