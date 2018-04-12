package com.dexoteric.monsterslayer.utils

import java.text.DecimalFormat

fun format(number: Int): String {

    val numberE = DecimalFormat("###.###E+0")
    val numberN = DecimalFormat("###,###")

    return if (number >= 1000000) {
        numberE.format(number)
    } else {
        numberN.format(number)
    }

}