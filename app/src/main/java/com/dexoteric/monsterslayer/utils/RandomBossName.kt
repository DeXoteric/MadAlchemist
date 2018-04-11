package com.dexoteric.monsterslayer.utils

import android.content.Context
import com.dexoteric.monsterslayer.R
import java.util.*

// funkcja generująca losową nazwę boss'a
fun getRandomBossName(context: Context) :String {

    // inicjalizacja Array
    val arrayBossNames: Array<String> = context.resources.getStringArray(R.array.boss_name)
    val arrayBossPrefix: Array<String> = context.resources.getStringArray(R.array.boss_prefix)
    val arrayBossSuffix: Array<String> = context.resources.getStringArray(R.array.boss_suffix)

    val randomPrefix = Random().nextInt(arrayBossPrefix.size)
    val prefix = arrayBossPrefix[randomPrefix]

    val randomName = Random().nextInt(arrayBossNames.size)
    val name = arrayBossNames[randomName]

    val randomSuffix = Random().nextInt(arrayBossSuffix.size)
    val suffix = arrayBossSuffix[randomSuffix]

    return "$prefix $name $suffix"
}