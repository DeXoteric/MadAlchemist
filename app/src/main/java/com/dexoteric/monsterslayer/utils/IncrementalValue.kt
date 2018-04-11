package com.dexoteric.monsterslayer.utils

import kotlin.math.pow

fun getNewValue(baseCost: Int, value: Int, multiplier: Double): Int {


    val newValue = baseCost.toDouble() * multiplier.pow((value - 1).toDouble())

    return newValue.toInt()
}