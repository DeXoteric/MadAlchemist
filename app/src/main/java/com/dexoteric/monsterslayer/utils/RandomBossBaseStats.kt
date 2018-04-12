package com.dexoteric.monsterslayer.utils

import java.util.*

var PERCENT = 0.1 // 10%

fun getRandomBossBaseStats(baseValue: Int) : Int{

    val minmax = baseValue * PERCENT


    val newMinValue = baseValue - minmax.toInt()
    val newMaxValue = baseValue + minmax.toInt()

    val arrayMinMaxValues = ArrayList<Int>()

    for (i in newMinValue..newMaxValue) {
        arrayMinMaxValues.add(i)
    }

    val randomMinMaxValue = Random().nextInt(arrayMinMaxValues.size)

    return arrayMinMaxValues[randomMinMaxValue]
}