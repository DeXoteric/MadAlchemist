package com.dexoteric.monsterslayer.utils

import kotlin.math.pow

fun getNewValue(baseValue: Int, value: Int, multiplier: Double): Int {


    val newValue = baseValue.toDouble() * (multiplier.pow((value - 1).toDouble()))
    return newValue.toInt()
}

fun getMinValue (baseValue: Int, percent: Double) : Int{

    val minmax = baseValue * (percent / 100)

    return baseValue - minmax.toInt()

}

fun getMaxValue (baseValue: Int, percent: Double) : Int{

    val minmax = baseValue * (percent / 100)

    return baseValue + minmax.toInt()

}