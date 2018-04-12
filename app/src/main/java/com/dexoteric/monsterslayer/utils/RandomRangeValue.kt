package com.dexoteric.monsterslayer.utils

import java.util.*


fun getRandomRangeValue(baseValue: Int, percent: Double) : Int{

    val minmax = baseValue * (percent / 100)


    val newMinValue = baseValue - minmax.toInt()
    val newMaxValue = baseValue + minmax.toInt()

    val arrayMinMaxValues = ArrayList<Int>()

    for (i in newMinValue..newMaxValue) {
        arrayMinMaxValues.add(i)
    }

    val randomMinMaxValue = Random().nextInt(arrayMinMaxValues.size)

    return arrayMinMaxValues[randomMinMaxValue]
}