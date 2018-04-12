package com.dexoteric.monsterslayer.utils

import android.content.Context
import com.dexoteric.monsterslayer.R
import java.util.*

// funkcja generująca losową nazwę boss'a
fun getRandomBossName(context: Context) :String {

    // inicjalizacja Array
//    val arrayBossName: Array<String> = context.resources.getStringArray(R.array.boss_name)
//    val arrayBossPrefix: Array<String> = context.resources.getStringArray(R.array.boss_prefix)
//    val arrayBossSuffix: Array<String> = context.resources.getStringArray(R.array.boss_suffix)
    val arrayBossNames: Array<String> = context.resources.getStringArray(R.array.boss_names)

//    val randomPrefix = Random().nextInt(arrayBossPrefix.size)
//    val prefix = arrayBossPrefix[randomPrefix]
//
//    val randomName = Random().nextInt(arrayBossName.size)
//    val name = arrayBossName[randomName]
//
//    val randomSuffix = Random().nextInt(arrayBossSuffix.size)
//    val suffix = arrayBossSuffix[randomSuffix]

    val randomName = Random().nextInt(arrayBossNames.size)

//    return "$prefix $name $suffix"
    return arrayBossNames[randomName]
}