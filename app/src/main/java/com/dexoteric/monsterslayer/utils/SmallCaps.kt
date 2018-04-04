package com.dexoteric.monsterslayer.utils

import android.text.Spannable
import android.text.style.RelativeSizeSpan
import android.text.SpannableString


/**
 * Produce a formatted SpannableString object from a given String
 * input, with all lowercase characters converted to smallcap
 * characters. Uses only standard A-Z characters, so works with
 * any font.
 *
 * @param input  The input string, e.g. "Small Caps"
 * @return       A formatted SpannableString, e.g. "Sᴍᴀʟʟ Cᴀᴘs"
 */
fun getSmallCapsString(input: String): SpannableString {
    // values needed to record start/end points of blocks of lowercase letters
    val chars = input.toCharArray()
    var currentBlock = 0
    val blockStarts = IntArray(chars.size)
    val blockEnds = IntArray(chars.size)
    var blockOpen = false

    // record where blocks of lowercase letters start/end
    for (i in chars.indices) {
        val c = chars[i]
        if (c in 'a'..'z') {
            if (!blockOpen) {
                blockOpen = true
                blockStarts[currentBlock] = i
            }
            // replace with uppercase letters
            chars[i] = (c - 'a' + '\u0041'.toInt()).toChar()
        } else {
            if (blockOpen) {
                blockOpen = false
                blockEnds[currentBlock] = i
                ++currentBlock
            }
        }
    }

    // add the string end, in case the last character is a lowercase letter
    blockEnds[currentBlock] = chars.size

    // shrink the blocks found above
    val output = SpannableString(String(chars))
    for (i in 0 until Math.min(blockStarts.size, blockEnds.size)) {
        output.setSpan(RelativeSizeSpan(0.8f), blockStarts[i], blockEnds[i], Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
    }

    return output
}