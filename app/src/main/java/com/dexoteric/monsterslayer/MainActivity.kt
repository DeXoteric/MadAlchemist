package com.dexoteric.monsterslayer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        onWindowFocusChanged(true)

        // inicjalizacja Array
        val arrayBossNames: Array<String> = resources.getStringArray(R.array.boss_name)
        val arrayBossPrefix: Array<String> = resources.getStringArray(R.array.boss_prefix)
        val arrayBossSuffix: Array<String> = resources.getStringArray(R.array.boss_suffix)

        // inicjalizacja TextView
        val testText :TextView = findViewById(R.id.textView)

        // przypisuje do zmiennych String z zadeklarowanych Array<String> z pozycji [x] oraz wyświetla całość w zadeklarowanym TextView
        val bossName = arrayBossNames[0]
        val bossPrefix = arrayBossPrefix[0]
        val bossSuffix = arrayBossSuffix[0]
        val boss = "$bossPrefix $bossName $bossSuffix"
        testText.text = boss
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        if (hasFocus) {
            // przywołanie funckji
            systemUI()
        }
    }

    // funkcja odpowiadająca za ustawienie flag full_screen
    private fun systemUI() {
        val flags = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        window.decorView.systemUiVisibility = (flags)
    }

}
