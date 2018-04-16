package com.dexoteric.monsterslayer.utils

import kotlin.math.pow


fun setInventoryCost (baseCost: Int, level: Int, tier: Int,multiplier:Double) : Int {

    val newValue = baseCost.toDouble() * (multiplier.pow((level - 1).toDouble())) +
            baseCost.toDouble() * (multiplier.pow((tier - 1).toDouble()))
    return newValue.toInt()

}