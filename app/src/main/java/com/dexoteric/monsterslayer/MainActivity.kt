package com.dexoteric.monsterslayer

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import com.dexoteric.monsterslayer.utils.getSmallCapsString
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    // inicjalizacja zmiennych
    var prestige = 1000000
    private var gold = 0.00
    var exp = 100
    private var gems = 0
    var amuletTier = 10
    var amuletLevel = 100
    var helmetTier = 10
    var helmetLevel = 100
    var cloakTier = 10
    var cloakLevel = 100
    var weaponTier = 10
    var weaponLevel = 100
    var chestplateTier = 10
    var chestplateLevel = 100
    var shieldTier = 10
    var shieldLevel = 100
    var glovesTier = 10
    var glovesLevel = 100
    var leggingsTier = 10
    var leggingsLevel = 100
    var beltTier = 10
    var beltLevel = 100
    var ringTier = 10
    var ringLevel = 100
    var bootsTier = 10
    var bootsLevel = 100
    var goldLooted = 1
    private var progressMonsterSlaying = 0
    private var gemChanceToDrop = 5 // %



    private val handler = Handler()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        onWindowFocusChanged(true)



        // inicjalizacja Array
        val arrayBossNames: Array<String> = resources.getStringArray(R.array.boss_name)
        val arrayBossPrefix: Array<String> = resources.getStringArray(R.array.boss_prefix)
        val arrayBossSuffix: Array<String> = resources.getStringArray(R.array.boss_suffix)

        // inicjalizacja Button
        val buttonRebirth: ImageButton = findViewById(R.id.btn_rebirth)
        val buttonOptions: ImageButton = findViewById(R.id.btn_options)
        val buttonSlay: Button = findViewById(R.id.btn_slay)
        buttonSlay.text = getSmallCapsString("Click to SLAY!")

        // inicjalizacja TextView
        val textPrestige: TextView = findViewById(R.id.text_prestige)
        textPrestige.text = getSmallCapsString("Highest level boss killed")
        val textPrestigeValue: TextView = findViewById(R.id.text_prestige_value)
        textPrestigeValue.text = prestige.toString()

        val textGold: TextView = findViewById(R.id.text_gold)
        textGold.text = getSmallCapsString("Gold")
        val textGoldValue: TextView = findViewById(R.id.text_gold_value)
        textGoldValue.text = gold.toString()

        val textExp: TextView = findViewById(R.id.text_exp)
        textExp.text = getSmallCapsString("Exp")
        val textExpValue: TextView = findViewById(R.id.text_exp_value)
        textExpValue.text = exp.toString()

        val textGems: TextView = findViewById(R.id.text_gems)
        textGems.text = getSmallCapsString("Gems")
        val textGemsValue: TextView = findViewById(R.id.text_gems_value)
        textGemsValue.text = gems.toString()

        val textInventory: TextView = findViewById(R.id.text_inventory)
        textInventory.text = getSmallCapsString("Inventory")

        val textAmuletLevel: TextView = findViewById(R.id.text_amulet_level)
        textAmuletLevel.text = amuletLevel.toString()
        val textAmuletTier: TextView = findViewById(R.id.text_amulet_tier)
        textAmuletTier.text = amuletTier.toString()

        val textHelmetLevel: TextView = findViewById(R.id.text_helmet_level)
        textHelmetLevel.text = helmetLevel.toString()
        val textHelmetTier: TextView = findViewById(R.id.text_helmet_tier)
        textHelmetTier.text = helmetTier.toString()

        val textCloakLevel: TextView = findViewById(R.id.text_cloak_level)
        textCloakLevel.text = cloakLevel.toString()
        val textCloakTier: TextView = findViewById(R.id.text_cloak_tier)
        textCloakTier.text = cloakTier.toString()

        val textWeaponLevel: TextView = findViewById(R.id.text_weapon_level)
        textWeaponLevel.text = weaponLevel.toString()
        val textWeaponTier: TextView = findViewById(R.id.text_weapon_tier)
        textWeaponTier.text = weaponTier.toString()

        val textChestplateLevel: TextView = findViewById(R.id.text_chestplate_level)
        textChestplateLevel.text = chestplateLevel.toString()
        val textChestplateTier: TextView = findViewById(R.id.text_chestplate_tier)
        textChestplateTier.text = chestplateTier.toString()

        val textShieldLevel: TextView = findViewById(R.id.text_shield_level)
        textShieldLevel.text = shieldLevel.toString()
        val textShieldTier: TextView = findViewById(R.id.text_shield_tier)
        textShieldTier.text = shieldTier.toString()

        val textGlovesLevel: TextView = findViewById(R.id.text_gloves_level)
        textGlovesLevel.text = glovesLevel.toString()
        val textGlovesTier: TextView = findViewById(R.id.text_gloves_tier)
        textGlovesTier.text = glovesTier.toString()

        val textLeggingsLevel: TextView = findViewById(R.id.text_leggings_level)
        textLeggingsLevel.text = leggingsLevel.toString()
        val textLeggingsTier: TextView = findViewById(R.id.text_leggings_tier)
        textLeggingsTier.text = leggingsTier.toString()

        val textBeltLevel: TextView = findViewById(R.id.text_belt_level)
        textBeltLevel.text = beltLevel.toString()
        val textBeltTier: TextView = findViewById(R.id.text_belt_tier)
        textBeltTier.text = beltTier.toString()

        val textRingLevel: TextView = findViewById(R.id.text_ring_level)
        textRingLevel.text = ringLevel.toString()
        val textRingTier: TextView = findViewById(R.id.text_ring_tier)
        textRingTier.text = ringTier.toString()

        val textBootsLevel: TextView = findViewById(R.id.text_boots_level)
        textBootsLevel.text = bootsLevel.toString()
        val textBootsTier: TextView = findViewById(R.id.text_boots_tier)
        textBootsTier.text = bootsTier.toString()

        val textHeroStats: TextView = findViewById(R.id.text_hero_stats)
        textHeroStats.text = getSmallCapsString("Hero Stats")

        val textMonsterSlaying: TextView = findViewById(R.id.text_monster_slaying)
        textMonsterSlaying.text = getSmallCapsString("Monster Slaying")


        monsterSlaying()

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

    // funkcja monster slaying progress bar
    fun monsterSlaying() {

        fun randomGemDropChance() :Boolean {
            val r = Random()
            val randomNumber: Int = r.nextInt(1000 + 1)
            return randomNumber <= gemChanceToDrop * 10
        }

        // inicjalizacja ProgressBar
        val progressBarMonsterSlaying: ProgressBar = findViewById(R.id.progress_monster_slaying)

        // inicjalizacja TextView
        val textGoldLooted: TextView = findViewById(R.id.text_gold_looted)

        Thread(Runnable {
            while (progressMonsterSlaying < 1200) {
                progressMonsterSlaying++
                Thread.sleep(1)
                handler.post { progressBarMonsterSlaying.progress = progressMonsterSlaying }
            }
            handler.post {
                if (randomGemDropChance()) {
                    textGoldLooted.text = getSmallCapsString("$goldLooted gold looted! Gem looted!")
                    gems++
                    text_gems_value.text = gems.toString()
                } else {
                    textGoldLooted.text = getSmallCapsString("$goldLooted gold looted!")
                }
                    gold += goldLooted
                    text_gold_value.text = gold.toString()
                    progressMonsterSlaying = 0
                    progressBarMonsterSlaying.progress = progressMonsterSlaying

                monsterSlaying()
            }
        }).start()
    }

}
