package com.dexoteric.monsterslayer.utils

import kotlin.math.pow



fun setInventoryCost (baseCost: Int, level: Int, tier: Int) : Int {

    val multiplier = 1.15

    val newValue = baseCost.toDouble() * (multiplier.pow((level - 1).toDouble())) +
            baseCost.toDouble() * (multiplier.pow((tier - 1).toDouble()))
    return newValue.toInt()

}