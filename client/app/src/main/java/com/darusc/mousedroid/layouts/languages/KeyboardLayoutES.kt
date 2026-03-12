package com.darusc.mousedroid.layouts.languages

import com.darusc.mousedroid.layouts.KeyboardLayout
import com.darusc.mousedroid.layouts.Keycode

class KeyboardLayoutES : KeyboardLayout() {

    companion object {
        const val NAME = "Spanish"
    }

    override val name = NAME

    override val charMap = mapOf(
        // --- Control Characters ---
        '\u007F' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_BACKSPACE)),
        '\b' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_BACKSPACE)),
        '\n' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_ENTER)),
        '\r' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_ENTER)),
        '\t' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_TAB)),
        '\u001B' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_ESC)),

        // --- Space ---
        ' ' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_SPACE)),

        // --- Number Row (Numbers direct, symbols with Shift/AltGr) ---
        '1' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_1)),
        '!' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_1)),
        '|' to listOf(Key(Keycode.MOD_RIGHT_ALT, Keycode.KEY_1)),
        '2' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_2)),
        '"' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_2)),
        '@' to listOf(Key(Keycode.MOD_RIGHT_ALT, Keycode.KEY_2)),
        '3' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_3)),
        '·' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_3)),
        '#' to listOf(Key(Keycode.MOD_RIGHT_ALT, Keycode.KEY_3)),
        '4' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_4)),
        '$' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_4)),
        '~' to listOf(Key(Keycode.MOD_RIGHT_ALT, Keycode.KEY_4)),
        '5' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_5)),
        '%' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_5)),
        '6' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_6)),
        '&' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_6)),
        '7' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_7)),
        '/' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_7)),
        '8' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_8)),
        '(' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_8)),
        '9' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_9)),
        ')' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_9)),
        '0' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_0)),
        '=' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_0)),

        // --- Spanish Specific: Ñ ---
        'ñ' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_SEMICOLON)),
        'Ñ' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_SEMICOLON)),

        // --- Letters (QWERTY) ---
        'a' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_A)),
        'b' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_B)),
        'c' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_C)),
        'd' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_D)),
        'e' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_E)),
        'f' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_F)),
        'g' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_G)),
        'h' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_H)),
        'i' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_I)),
        'j' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_J)),
        'k' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_K)),
        'l' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_L)),
        'm' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_M)),
        'n' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_N)),
        'o' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_O)),
        'p' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_P)),
        'q' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_Q)),
        'r' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_R)),
        's' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_S)),
        't' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_T)),
        'u' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_U)),
        'v' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_V)),
        'w' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_W)),
        'x' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_X)),
        'y' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_Y)),
        'z' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_Z)),

        // --- Uppercase (Shifted) ---
        'A' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_A)),
        'B' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_B)),
        'C' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_C)),
        'D' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_D)),
        'E' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_E)),
        'F' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_F)),
        'G' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_G)),
        'H' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_H)),
        'I' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_I)),
        'J' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_J)),
        'K' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_K)),
        'L' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_L)),
        'M' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_M)),
        'N' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_N)),
        'O' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_O)),
        'P' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_P)),
        'Q' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_Q)),
        'R' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_R)),
        'S' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_S)),
        'T' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_T)),
        'U' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_U)),
        'V' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_V)),
        'W' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_W)),
        'X' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_X)),
        'Y' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_Y)),
        'Z' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_Z)),

        // --- Symbols & Punctuation ---
        '´' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_QUOTE)), // Acute Dead Key
        '¨' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_QUOTE)), // Diaeresis Dead Key
        '{' to listOf(Key(Keycode.MOD_RIGHT_ALT, Keycode.KEY_QUOTE)),
        '[' to listOf(Key(Keycode.MOD_RIGHT_ALT, Keycode.KEY_LEFT_BRACE)),
        '}' to listOf(Key(Keycode.MOD_RIGHT_ALT, Keycode.KEY_RIGHT_BRACE)),
        '\\' to listOf(Key(Keycode.MOD_RIGHT_ALT, Keycode.KEY_GRAVE)),
        'º' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_GRAVE)),
        'ª' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_GRAVE)),
        '?' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_MINUS)),
        '¿' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_EQUAL)),
        '¡' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_GRAVE)), // Note: Grave on ES layout is far left
        '.' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_DOT)),
        ':' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_DOT)),
        ',' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_COMMA)),
        ';' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_COMMA)),
        '-' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_SLASH)),
        '_' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_SLASH)),
        '<' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_NON_US)),
        '>' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_NON_US)),

        // --- Spanish Dead Key: Acute Accent (á, é, í, ó, ú) ---
        'á' to listOf(
            Key(Keycode.MOD_NONE, Keycode.KEY_QUOTE),
            Key(Keycode.MOD_NONE, Keycode.KEY_A)
        ),
        'é' to listOf(
            Key(Keycode.MOD_NONE, Keycode.KEY_QUOTE),
            Key(Keycode.MOD_NONE, Keycode.KEY_E)
        ),
        'í' to listOf(
            Key(Keycode.MOD_NONE, Keycode.KEY_QUOTE),
            Key(Keycode.MOD_NONE, Keycode.KEY_I)
        ),
        'ó' to listOf(
            Key(Keycode.MOD_NONE, Keycode.KEY_QUOTE),
            Key(Keycode.MOD_NONE, Keycode.KEY_O)
        ),
        'ú' to listOf(
            Key(Keycode.MOD_NONE, Keycode.KEY_QUOTE),
            Key(Keycode.MOD_NONE, Keycode.KEY_U)
        ),
        'Á' to listOf(
            Key(Keycode.MOD_NONE, Keycode.KEY_QUOTE),
            Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_A)
        ),
        'É' to listOf(
            Key(Keycode.MOD_NONE, Keycode.KEY_QUOTE),
            Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_E)
        ),
        'Í' to listOf(
            Key(Keycode.MOD_NONE, Keycode.KEY_QUOTE),
            Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_I)
        ),
        'Ó' to listOf(
            Key(Keycode.MOD_NONE, Keycode.KEY_QUOTE),
            Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_O)
        ),
        'Ú' to listOf(
            Key(Keycode.MOD_NONE, Keycode.KEY_QUOTE),
            Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_U)
        ),

        // --- Spanish Dead Key: Diaeresis (ü) ---
        'ü' to listOf(
            Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_QUOTE),
            Key(Keycode.MOD_NONE, Keycode.KEY_U)
        ),
        'Ü' to listOf(
            Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_QUOTE),
            Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_U)
        )
    )
}