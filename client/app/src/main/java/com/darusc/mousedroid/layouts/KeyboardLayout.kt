package com.darusc.mousedroid.layouts

/**
 * Base class for a KeyboardLayout
 * @param asciimap Mapping of each ascii character to its corresponding keycode and modifier
 * (packed as an Int. First byte -> the modifier, second byte -> the keycode)
 */
abstract class KeyboardLayout(
    private val asciimap: IntArray
) {

    data class Key(
        val code: Byte,
        val modifier: Byte
    )

    /**
     * Get the keycode and modifier required for a given ascii character
     */
    fun getMapping(c: Char): Key? {
        val code = c.code
        if(code < 0 || code >= asciimap.size) {
            return null
        }

        val packed = asciimap[code]
        if(packed == Keycode.KEY_RESERVED) {
            return null
        }

        return Key(
            code = (packed and 0xFF).toByte(),  // Key code is in the second byte
            modifier = (packed ushr 8).toByte() // The modifier is the first byte
        )
    }
}