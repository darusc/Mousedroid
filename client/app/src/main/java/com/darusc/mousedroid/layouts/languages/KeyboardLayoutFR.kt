package com.darusc.mousedroid.layouts.languages

import com.darusc.mousedroid.layouts.KeyboardLayout
import com.darusc.mousedroid.layouts.Keycode

class KeyboardLayoutFR : KeyboardLayout() {

    companion object {
        const val NAME = "French"
    }

    override val name = NAME

    override val charMap: Map<Char, List<Key>> = mapOf(
        // --- Control Characters ---
        '\u007F' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_BACKSPACE)),
        '\b' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_BACKSPACE)),
        '\n' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_ENTER)),
        '\r' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_ENTER)),
        '\t' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_TAB)),
        '\u001B' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_ESC)),

        // --- Space ---
        ' ' to Key(Keycode.MOD_NONE, Keycode.KEY_SPACE),

        // --- Number Row (Symbols first, Numbers with Shift) ---
        '&' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_1)),
        '1' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_1)),
        'é' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_2)),
        '2' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_2)),
        '~' to listOf(Key(Keycode.MOD_RIGHT_ALT, Keycode.KEY_2)),
        '"' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_3)),
        '3' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_3)),
        '#' to listOf(Key(Keycode.MOD_RIGHT_ALT, Keycode.KEY_3)),
        '\'' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_4)),
        '4' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_4)),
        '{' to listOf(Key(Keycode.MOD_RIGHT_ALT, Keycode.KEY_4)),
        '(' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_5)),
        '5' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_5)),
        '[' to listOf(Key(Keycode.MOD_RIGHT_ALT, Keycode.KEY_5)),
        '-' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_6)),
        '6' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_6)),
        '|' to listOf(Key(Keycode.MOD_RIGHT_ALT, Keycode.KEY_6)),
        'è' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_7)),
        '7' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_7)),
        '`' to listOf(Key(Keycode.MOD_RIGHT_ALT, Keycode.KEY_7)),
        '_' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_8)),
        '8' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_8)),
        '\\' to listOf(Key(Keycode.MOD_RIGHT_ALT, Keycode.KEY_8)),
        'ç' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_9)),
        '9' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_9)),
        '^' to listOf(Key(Keycode.MOD_RIGHT_ALT, Keycode.KEY_9)),
        'à' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_0)),
        '0' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_0)),
        '@' to listOf(Key(Keycode.MOD_RIGHT_ALT, Keycode.KEY_0)),

        // --- AZERTY Letters (Lower) ---
        'a' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_Q)), // AZERTY swap
        'b' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_B)),
        'c' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_C)),
        'd' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_D)),
        'e' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_E)),
        '€' to listOf(Key(Keycode.MOD_RIGHT_ALT, Keycode.KEY_E)),
        'f' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_F)),
        'g' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_G)),
        'h' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_H)),
        'i' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_I)),
        'j' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_J)),
        'k' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_K)),
        'l' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_L)),
        'm' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_SEMICOLON)), // M moved
        'n' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_N)),
        'o' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_O)),
        'p' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_P)),
        'q' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_A)), // AZERTY swap
        'r' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_R)),
        's' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_S)),
        't' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_T)),
        'u' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_U)),
        'v' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_V)),
        'w' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_Z)), // AZERTY swap
        'x' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_X)),
        'y' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_Y)),
        'z' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_W)), // AZERTY swap

        // --- AZERTY Letters (Upper) ---
        'A' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_Q)),
        'Q' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_A)),
        'W' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_Z)),
        'Z' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_W)),
        'M' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_SEMICOLON)),
        // ... all other uppercase mapping following the shifted physical key ...
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
        'N' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_N)),
        'O' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_O)),
        'P' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_P)),
        'R' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_R)),
        'S' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_S)),
        'T' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_T)),
        'U' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_U)),
        'V' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_V)),
        'X' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_X)),
        'Y' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_Y)),

        // --- Symbols & French Specifics ---
        ')' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_MINUS)),
        '°' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_MINUS)),
        ']' to listOf(Key(Keycode.MOD_RIGHT_ALT, Keycode.KEY_MINUS)),
        '=' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_EQUAL)),
        '+' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_EQUAL)),
        '}' to listOf(Key(Keycode.MOD_RIGHT_ALT, Keycode.KEY_EQUAL)),
        'ù' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_QUOTE)),
        '%' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_QUOTE)),
        '*' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_BACKSLASH)),
        'µ' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_BACKSLASH)),
        ',' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_M)),
        '?' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_M)),
        ';' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_COMMA)),
        '.' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_COMMA)),
        ':' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_DOT)),
        '/' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_DOT)),
        '!' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_SLASH)),
        '§' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_SLASH)),
        '<' to listOf(Key(Keycode.MOD_NONE, Keycode.KEY_NON_US)),
        '>' to listOf(Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_NON_US)),

        // --- French Dead Key Characters (Circumflex ^) ---
        'â' to listOf(
            Key(Keycode.MOD_NONE, Keycode.KEY_LEFT_BRACE),
            Key(Keycode.MOD_NONE, Keycode.KEY_A)
        ),
        'ê' to listOf(
            Key(Keycode.MOD_NONE, Keycode.KEY_LEFT_BRACE),
            Key(Keycode.MOD_NONE, Keycode.KEY_E)
        ),
        'î' to listOf(
            Key(Keycode.MOD_NONE, Keycode.KEY_LEFT_BRACE),
            Key(Keycode.MOD_NONE, Keycode.KEY_I)
        ),
        'ô' to listOf(
            Key(Keycode.MOD_NONE, Keycode.KEY_LEFT_BRACE),
            Key(Keycode.MOD_NONE, Keycode.KEY_O)
        ),
        'û' to listOf(
            Key(Keycode.MOD_NONE, Keycode.KEY_LEFT_BRACE),
            Key(Keycode.MOD_NONE, Keycode.KEY_U)
        ),

        'Â' to listOf(
            Key(Keycode.MOD_NONE, Keycode.KEY_LEFT_BRACE),
            Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_Q)
        ),
        'Ê' to listOf(
            Key(Keycode.MOD_NONE, Keycode.KEY_LEFT_BRACE),
            Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_E)
        ),
        'Î' to listOf(
            Key(Keycode.MOD_NONE, Keycode.KEY_LEFT_BRACE),
            Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_I)
        ),
        'Ô' to listOf(
            Key(Keycode.MOD_NONE, Keycode.KEY_LEFT_BRACE),
            Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_O)
        ),
        'Û' to listOf(
            Key(Keycode.MOD_NONE, Keycode.KEY_LEFT_BRACE),
            Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_U)
        ),

        // --- French Dead Key Characters (Trema/Umlaut ¨) ---
        'ä' to listOf(
            Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_LEFT_BRACE),
            Key(Keycode.MOD_NONE, Keycode.KEY_A)
        ),
        'ë' to listOf(
            Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_LEFT_BRACE),
            Key(Keycode.MOD_NONE, Keycode.KEY_E)
        ),
        'ï' to listOf(
            Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_LEFT_BRACE),
            Key(Keycode.MOD_NONE, Keycode.KEY_I)
        ),
        'ö' to listOf(
            Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_LEFT_BRACE),
            Key(Keycode.MOD_NONE, Keycode.KEY_O)
        ),
        'ü' to listOf(
            Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_LEFT_BRACE),
            Key(Keycode.MOD_NONE, Keycode.KEY_U)
        ),
        'ÿ' to listOf(
            Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_LEFT_BRACE),
            Key(Keycode.MOD_NONE, Keycode.KEY_Y)
        ),

        'Ä' to listOf(
            Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_LEFT_BRACE),
            Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_Q)
        ),
        'Ë' to listOf(
            Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_LEFT_BRACE),
            Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_E)
        ),
        'Ï' to listOf(
            Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_LEFT_BRACE),
            Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_I)
        ),
        'Ö' to listOf(
            Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_LEFT_BRACE),
            Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_O)
        ),
        'Ü' to listOf(
            Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_LEFT_BRACE),
            Key(Keycode.MOD_LEFT_SHIFT, Keycode.KEY_U)
        )
    ) as Map<Char, List<Key>>
}