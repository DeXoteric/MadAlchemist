package com.dexoteric.monsterslayer.utils


fun setInventoryStat(level: Int, tier: Int, multiplier: Int): Int {
    val calculation = (tier - 1) * 100 * multiplier + level * multiplier
    return  calculation + (calculation * (tier - 1) * 25) / 100
}



