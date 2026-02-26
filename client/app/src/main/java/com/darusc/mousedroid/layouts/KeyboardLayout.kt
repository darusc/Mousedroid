package com.darusc.mousedroid.layouts

/**
 * Base class for a KeyboardLayout
 */
abstract class KeyboardLayout {

    data class Key(
        val modifier: Byte,
        val code: Byte
    )

    abstract val name: String

    /**
     * Mapping of unicode characters to corresponding
     * keyboard keycodes and modifier
     */
    protected abstract val charMap: Map<Char, Key>

    /**
     * Get the keycode and modifier required for a given ascii character
     */
    fun getMapping(c: Char): Key? {
        return charMap[c]
    }
}