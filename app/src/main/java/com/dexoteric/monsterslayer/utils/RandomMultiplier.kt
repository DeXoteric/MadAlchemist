package com.dexoteric.monsterslayer.utils

import java.util.*

fun getRandomMultiplier() :Double{


    // TODO not working as I hoped
    val arrayMultipliers = doubleArrayOf( 1.13, 1.14, 1.15)

    val randomMultiplier = Random().nextInt(arrayMultipliers.size)
    return arrayMultipliers[randomMultiplier]
}