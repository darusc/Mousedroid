package com.darusc.mousedroid.layouts

class KeyboardLayoutUS: KeyboardLayout(
    NAME,
    intArrayOf(
        // --- Control Codes (0-31) ---
        Keycode.KEY_RESERVED,    // 00 NUL
        Keycode.KEY_RESERVED,    // 01 SOH
        Keycode.KEY_RESERVED,    // 02 STX
        Keycode.KEY_RESERVED,    // 03 ETX
        Keycode.KEY_RESERVED,    // 04 EOT
        Keycode.KEY_RESERVED,    // 05 ENQ
        Keycode.KEY_RESERVED,    // 06 ACK
        Keycode.KEY_RESERVED,    // 07 BEL
        Keycode.KEY_BACKSPACE,   // 08 BS  (Backspace)
        Keycode.KEY_TAB,         // 09 TAB (Tab)
        Keycode.KEY_ENTER,       // 10 LF  (Enter)
        Keycode.KEY_RESERVED,    // 11 VT
        Keycode.KEY_RESERVED,    // 12 FF
        Keycode.KEY_RESERVED,    // 13 CR
        Keycode.KEY_RESERVED,    // 14 SO
        Keycode.KEY_RESERVED,    // 15 SI
        Keycode.KEY_RESERVED,    // 16 DLE
        Keycode.KEY_RESERVED,    // 17 DC1
        Keycode.KEY_RESERVED,    // 18 DC2
        Keycode.KEY_RESERVED,    // 19 DC3
        Keycode.KEY_RESERVED,    // 20 DC4
        Keycode.KEY_RESERVED,    // 21 NAK
        Keycode.KEY_RESERVED,    // 22 SYN
        Keycode.KEY_RESERVED,    // 23 ETB
        Keycode.KEY_RESERVED,    // 24 CAN
        Keycode.KEY_RESERVED,    // 25 EM
        Keycode.KEY_RESERVED,    // 26 SUB
        Keycode.KEY_ESC,         // 27 ESC (Escape)
        Keycode.KEY_RESERVED,    // 28 FS
        Keycode.KEY_RESERVED,    // 29 GS
        Keycode.KEY_RESERVED,    // 30 RS
        Keycode.KEY_RESERVED,    // 31 US

        // --- Symbols & Numbers (32-47) ---
        Keycode.KEY_SPACE,                  // 32 ' '
        Keycode.KEY_1 or Keycode.MOD_LEFT_SHIFT,    // 33 '!'
        Keycode.KEY_QUOTE or Keycode.MOD_LEFT_SHIFT,// 34 '"'
        Keycode.KEY_3 or Keycode.MOD_LEFT_SHIFT,    // 35 '#'
        Keycode.KEY_4 or Keycode.MOD_LEFT_SHIFT,    // 36 '$'
        Keycode.KEY_5 or Keycode.MOD_LEFT_SHIFT,    // 37 '%'
        Keycode.KEY_7 or Keycode.MOD_LEFT_SHIFT,    // 38 '&'
        Keycode.KEY_QUOTE,                  // 39 '''
        Keycode.KEY_9 or Keycode.MOD_LEFT_SHIFT,    // 40 '('
        Keycode.KEY_0 or Keycode.MOD_LEFT_SHIFT,    // 41 ')'
        Keycode.KEY_8 or Keycode.MOD_LEFT_SHIFT,    // 42 '*'
        Keycode.KEY_EQUAL or Keycode.MOD_LEFT_SHIFT,// 43 '+'
        Keycode.KEY_COMMA,                  // 44 ','
        Keycode.KEY_MINUS,                  // 45 '-'
        Keycode.KEY_DOT,                    // 46 '.'
        Keycode.KEY_SLASH,                  // 47 '/'

        // --- Digits (48-57) ---
        Keycode.KEY_0,  // 48 '0'
        Keycode.KEY_1,  // 49 '1'
        Keycode.KEY_2,  // 50 '2'
        Keycode.KEY_3,  // 51 '3'
        Keycode.KEY_4,  // 52 '4'
        Keycode.KEY_5,  // 53 '5'
        Keycode.KEY_6,  // 54 '6'
        Keycode.KEY_7,  // 55 '7'
        Keycode.KEY_8,  // 56 '8'
        Keycode.KEY_9,  // 57 '9'

        // --- Symbols (58-64) ---
        Keycode.KEY_SEMICOLON or Keycode.MOD_LEFT_SHIFT, // 58 ':'
        Keycode.KEY_SEMICOLON,                   // 59 ';'
        Keycode.KEY_COMMA or Keycode.MOD_LEFT_SHIFT,     // 60 '<'
        Keycode.KEY_EQUAL,                       // 61 '='
        Keycode.KEY_DOT or Keycode.MOD_LEFT_SHIFT,       // 62 '>'
        Keycode.KEY_SLASH or Keycode.MOD_LEFT_SHIFT,     // 63 '?'
        Keycode.KEY_2 or Keycode.MOD_LEFT_SHIFT,         // 64 '@'

        // --- Uppercase Letters (65-90) ---
        Keycode.KEY_A or Keycode.MOD_LEFT_SHIFT, // 65 'A'
        Keycode.KEY_B or Keycode.MOD_LEFT_SHIFT, // 66 'B'
        Keycode.KEY_C or Keycode.MOD_LEFT_SHIFT, // 67 'C'
        Keycode.KEY_D or Keycode.MOD_LEFT_SHIFT, // 68 'D'
        Keycode.KEY_E or Keycode.MOD_LEFT_SHIFT, // 69 'E'
        Keycode.KEY_F or Keycode.MOD_LEFT_SHIFT, // 70 'F'
        Keycode.KEY_G or Keycode.MOD_LEFT_SHIFT, // 71 'G'
        Keycode.KEY_H or Keycode.MOD_LEFT_SHIFT, // 72 'H'
        Keycode.KEY_I or Keycode.MOD_LEFT_SHIFT, // 73 'I'
        Keycode.KEY_J or Keycode.MOD_LEFT_SHIFT, // 74 'J'
        Keycode.KEY_K or Keycode.MOD_LEFT_SHIFT, // 75 'K'
        Keycode.KEY_L or Keycode.MOD_LEFT_SHIFT, // 76 'L'
        Keycode.KEY_M or Keycode.MOD_LEFT_SHIFT, // 77 'M'
        Keycode.KEY_N or Keycode.MOD_LEFT_SHIFT, // 78 'N'
        Keycode.KEY_O or Keycode.MOD_LEFT_SHIFT, // 79 'O'
        Keycode.KEY_P or Keycode.MOD_LEFT_SHIFT, // 80 'P'
        Keycode.KEY_Q or Keycode.MOD_LEFT_SHIFT, // 81 'Q'
        Keycode.KEY_R or Keycode.MOD_LEFT_SHIFT, // 82 'R'
        Keycode.KEY_S or Keycode.MOD_LEFT_SHIFT, // 83 'S'
        Keycode.KEY_T or Keycode.MOD_LEFT_SHIFT, // 84 'T'
        Keycode.KEY_U or Keycode.MOD_LEFT_SHIFT, // 85 'U'
        Keycode.KEY_V or Keycode.MOD_LEFT_SHIFT, // 86 'V'
        Keycode.KEY_W or Keycode.MOD_LEFT_SHIFT, // 87 'W'
        Keycode.KEY_X or Keycode.MOD_LEFT_SHIFT, // 88 'X'
        Keycode.KEY_Y or Keycode.MOD_LEFT_SHIFT, // 89 'Y'
        Keycode.KEY_Z or Keycode.MOD_LEFT_SHIFT, // 90 'Z'

        // --- Symbols (91-96) ---
        Keycode.KEY_LEFT_BRACE,                   // 91 '['
        Keycode.KEY_BACKSLASH,                    // 92 '\'
        Keycode.KEY_RIGHT_BRACE,                  // 93 ']'
        Keycode.KEY_6 or Keycode.MOD_LEFT_SHIFT,          // 94 '^'
        Keycode.KEY_MINUS or Keycode.MOD_LEFT_SHIFT,      // 95 '_'
        Keycode.KEY_GRAVE,                        // 96 '`'

        // --- Lowercase Letters (97-122) ---
        Keycode.KEY_A, // 97 'a'
        Keycode.KEY_B, // 98 'b'
        Keycode.KEY_C, // 99 'c'
        Keycode.KEY_D, // 100 'd'
        Keycode.KEY_E, // 101 'e'
        Keycode.KEY_F, // 102 'f'
        Keycode.KEY_G, // 103 'g'
        Keycode.KEY_H, // 104 'h'
        Keycode.KEY_I, // 105 'i'
        Keycode.KEY_J, // 106 'j'
        Keycode.KEY_K, // 107 'k'
        Keycode.KEY_L, // 108 'l'
        Keycode.KEY_M, // 109 'm'
        Keycode.KEY_N, // 110 'n'
        Keycode.KEY_O, // 111 'o'
        Keycode.KEY_P, // 112 'p'
        Keycode.KEY_Q, // 113 'q'
        Keycode.KEY_R, // 114 'r'
        Keycode.KEY_S, // 115 's'
        Keycode.KEY_T, // 116 't'
        Keycode.KEY_U, // 117 'u'
        Keycode.KEY_V, // 118 'v'
        Keycode.KEY_W, // 119 'w'
        Keycode.KEY_X, // 120 'x'
        Keycode.KEY_Y, // 121 'y'
        Keycode.KEY_Z, // 122 'z'

        // --- Symbols (123-126) ---
        Keycode.KEY_LEFT_BRACE or Keycode.MOD_LEFT_SHIFT,  // 123 '{'
        Keycode.KEY_BACKSLASH or Keycode.MOD_LEFT_SHIFT,   // 124 '|'
        Keycode.KEY_RIGHT_BRACE or Keycode.MOD_LEFT_SHIFT, // 125 '}'
        Keycode.KEY_GRAVE or Keycode.MOD_LEFT_SHIFT,       // 126 '~'

        // --- DEL (127) ---
        Keycode.KEY_BACKSPACE,                      // 127 DEL

        Keycode.KEY_RESERVED,                       // 128 - Unused
        Keycode.KEY_RESERVED,                       // 129 - Unused
        Keycode.KEY_RESERVED,                       // 130 - Unused
        Keycode.KEY_RESERVED,                       // 131 - Unused
        Keycode.KEY_RESERVED,                       // 132 - Unused
        Keycode.KEY_RESERVED,                       // 133 - Unused
        Keycode.KEY_RESERVED,                       // 134 - Unused
        Keycode.KEY_RESERVED,                       // 135 - Unused
        Keycode.KEY_RESERVED,                       // 136 - Unused
        Keycode.KEY_RESERVED,                       // 137 - Unused
        Keycode.KEY_RESERVED,                       // 138 - Unused
        Keycode.KEY_RESERVED,                       // 139 - Unused
        Keycode.KEY_RESERVED,                       // 140 - Unused
        Keycode.KEY_RESERVED,                       // 141 - Unused
        Keycode.KEY_RESERVED,                       // 142 - Unused
        Keycode.KEY_RESERVED,                       // 143 - Unused
        Keycode.KEY_RESERVED,                       // 144 - Unused
        Keycode.KEY_RESERVED,                       // 145 - Unused
        Keycode.KEY_RESERVED,                       // 146 - Unused
        Keycode.KEY_RESERVED,                       // 147 - Unused
        Keycode.KEY_RESERVED,                       // 148 - Unused
        Keycode.KEY_RESERVED,                       // 149 - Unused
        Keycode.KEY_RESERVED,                       // 150 - Unused
        Keycode.KEY_RESERVED,                       // 151 - Unused
        Keycode.KEY_RESERVED,                       // 152 - Unused
        Keycode.KEY_RESERVED,                       // 153 - Unused
        Keycode.KEY_RESERVED,                       // 154 - Unused
        Keycode.KEY_RESERVED,                       // 155 - Unused
        Keycode.KEY_RESERVED,                       // 156 - Unused
        Keycode.KEY_RESERVED,                       // 157 - Unused
        Keycode.KEY_RESERVED,                       // 158 - Unused
        Keycode.KEY_RESERVED,                       // 159 - Unused
        Keycode.KEY_RESERVED,                       // 160 - Non-breaking Space
        Keycode.KEY_RESERVED,                       // 161 - Inverted Exclamation Mark
        Keycode.KEY_RESERVED,                       // 162 - Cent
        Keycode.KEY_RESERVED,                       // 163 - British Pound Sign
        Keycode.KEY_RESERVED,                       // 164 - Euro Sign
        Keycode.KEY_RESERVED,                       // 165 - Yen
        Keycode.KEY_RESERVED,                       // 166 - Capital 's' Inverted Circumflex
        Keycode.KEY_RESERVED,                       // 167 - Section Sign
        Keycode.KEY_RESERVED,                       // 168 - 's' Inverted Circumflex
        Keycode.KEY_RESERVED,                       // 169 - Copyright Sign
        Keycode.KEY_RESERVED,                       // 170 - Superscript 'a'
        Keycode.KEY_RESERVED,                       // 171 - Open Guillemet
        Keycode.KEY_RESERVED,                       // 172 - Logic Negation
        Keycode.KEY_RESERVED,                       // 173 - Soft Hypen
        Keycode.KEY_RESERVED,                       // 174 - Registered Trademark
        Keycode.KEY_RESERVED,                       // 175 - Macron
        Keycode.KEY_RESERVED,                       // 176 - Degree Symbol
        Keycode.KEY_RESERVED,                       // 177 - Plus-Minus
        Keycode.KEY_RESERVED,                       // 178 - Superscript '2'
        Keycode.KEY_RESERVED,                       // 179 - Superscript '3'
        Keycode.KEY_RESERVED,                       // 180 - Capital 'z' Inverted Circumflex
        Keycode.KEY_RESERVED,                       // 181 - Micro Symbol
        Keycode.KEY_RESERVED,                       // 182 - Paragraph Mark
        Keycode.KEY_RESERVED,                       // 183 - Interpunct
        Keycode.KEY_RESERVED,                       // 184 - 'z' Inverted Circumflex
        Keycode.KEY_RESERVED,                       // 185 - Superscript '1'
        Keycode.KEY_RESERVED,                       // 186 - Ordinal Indicator
        Keycode.KEY_RESERVED,                       // 187 - Closed Guillemet
        Keycode.KEY_RESERVED,                       // 188 - Capital 'oe'
        Keycode.KEY_RESERVED,                       // 189 - 'oe'
        Keycode.KEY_RESERVED,                       // 190 - Capital 'y' Umlaut
        Keycode.KEY_RESERVED,                       // 191 - Inverted Question Mark
        Keycode.KEY_RESERVED,                       // 192 - Capital 'a' Grave
        Keycode.KEY_RESERVED,                       // 193 - Capital 'a' Acute
        Keycode.KEY_RESERVED,                       // 194 - Capital 'a' Circumflex
        Keycode.KEY_RESERVED,                       // 195 - Capital 'a' Tilde
        Keycode.KEY_RESERVED,                       // 196 - Capital 'a' Umlaut
        Keycode.KEY_RESERVED,                       // 197 - Capital 'a' Circle
        Keycode.KEY_RESERVED,                       // 198 - Capital 'ae'
        Keycode.KEY_RESERVED,                       // 199 - Capital 'c' Cedilla
        Keycode.KEY_RESERVED,                       // 200 - Capital 'e' Grave
        Keycode.KEY_RESERVED,                       // 201 - Capital 'e' Acute
        Keycode.KEY_RESERVED,                       // 202 - Capital 'e' Circumflex
        Keycode.KEY_RESERVED,                       // 203 - Capital 'e' Umlaut
        Keycode.KEY_RESERVED,                       // 204 - Capital 'i' Grave
        Keycode.KEY_RESERVED,                       // 205 - Capital 'i' Acute
        Keycode.KEY_RESERVED,                       // 206 - Capital 'i' Circumflex
        Keycode.KEY_RESERVED,                       // 207 - Capital 'i' Umlaut
        Keycode.KEY_RESERVED,                       // 208 - Capital Eth
        Keycode.KEY_RESERVED,                       // 207 - Capital 'n' Tilde
        Keycode.KEY_RESERVED,                       // 210 - Capital 'o' Grave
        Keycode.KEY_RESERVED,                       // 211 - Capital 'o' Acute
        Keycode.KEY_RESERVED,                       // 212 - Capital 'o' Circumflex
        Keycode.KEY_RESERVED,                       // 213 - Capital 'o' Tilde
        Keycode.KEY_RESERVED,                       // 214 - Capital 'o' Umlaut
        Keycode.KEY_RESERVED,                       // 215 - Multiplication Sign
        Keycode.KEY_RESERVED,                       // 216 - Capital 'o' Barred
        Keycode.KEY_RESERVED,                       // 217 - Capital 'u' Grave
        Keycode.KEY_RESERVED,                       // 218 - Capital 'u' Acute
        Keycode.KEY_RESERVED,                       // 219 - Capital 'u' Circumflex
        Keycode.KEY_RESERVED,                       // 220 - Capital 'u' Umlaut
        Keycode.KEY_RESERVED,                       // 221 - Capital 'y' Acute
        Keycode.KEY_RESERVED,                       // 222 - Capital Thorn
        Keycode.KEY_RESERVED,                       // 223 - Eszett
        Keycode.KEY_RESERVED,                       // 224 - 'a' Grave
        Keycode.KEY_RESERVED,                       // 225 - 'a' Acute
        Keycode.KEY_RESERVED,                       // 226 - 'a' Circumflex
        Keycode.KEY_RESERVED,                       // 227 - 'a' Tilde
        Keycode.KEY_RESERVED,                       // 228 - 'a' Umlaut
        Keycode.KEY_RESERVED,                       // 229 - 'a' Circle
        Keycode.KEY_RESERVED,                       // 230 - 'ae'
        Keycode.KEY_RESERVED,                       // 231 - 'c' Cedilla
        Keycode.KEY_RESERVED,                       // 232 - 'e' Grave
        Keycode.KEY_RESERVED,                       // 233 - 'e' Acute
        Keycode.KEY_RESERVED,                       // 234 - 'e' Circumflex
        Keycode.KEY_RESERVED,                       // 235 - 'e' Umlaut
        Keycode.KEY_RESERVED,                       // 236 - 'i' Grave
        Keycode.KEY_RESERVED,                       // 237 - 'i' Acute
        Keycode.KEY_RESERVED,                       // 238 - 'i' Circumflex
        Keycode.KEY_RESERVED,                       // 239 - 'i' Umlaut
        Keycode.KEY_RESERVED,                       // 240 - Eth
        Keycode.KEY_RESERVED,                       // 241 - 'n' Tilde
        Keycode.KEY_RESERVED,                       // 242 - 'o' Grave
        Keycode.KEY_RESERVED,                       // 243 - 'o' Acute
        Keycode.KEY_RESERVED,                       // 244 - 'o' Circumflex
        Keycode.KEY_RESERVED,                       // 245 - 'o' Tilde
        Keycode.KEY_RESERVED,                       // 246 - 'o' Umlaut
        Keycode.KEY_RESERVED,                       // 247 - Multiplication Sign
        Keycode.KEY_RESERVED,                       // 248 - 'o' Barred
        Keycode.KEY_RESERVED,                       // 249 - 'u' Grave
        Keycode.KEY_RESERVED,                       // 250 - 'u' Acute
        Keycode.KEY_RESERVED,                       // 251 - 'u' Circumflex
        Keycode.KEY_RESERVED,                       // 252 - 'u' Umlaut
        Keycode.KEY_RESERVED,                       // 253 - 'y' Acute
        Keycode.KEY_RESERVED,                       // 254 - Thorn
        Keycode.KEY_RESERVED,                       // 255 - 'y' Umlaut
    )
) {
    companion object {
        const val NAME = "English (US)"
    }
}