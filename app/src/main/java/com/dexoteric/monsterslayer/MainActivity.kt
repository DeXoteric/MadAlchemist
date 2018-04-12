package com.dexoteric.monsterslayer

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import com.dexoteric.monsterslayer.utils.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

var MULTIPLIER_0_10 = 0.10
var MULTIPLIER_1_07 = 1.07
var MULTIPLIER_1_15 = 1.15
var SLAYING_BASE_GOLD = 10
var BOSS_BASE_HEALTH = 1000
var BOSS_BASE_ATTACK_POWER = 1000
var BOSS_BASE_DEFENSE = 1000


class MainActivity : AppCompatActivity(){

    // inicjalizacja zmiennych
    var prestige = 1000000
    private var gold = 0.00
    private var exp = 0
    private var gems = 0
    private var bossLevel = 1
    private var bossHealth = BOSS_BASE_HEALTH
    private var bossAttackPower = BOSS_BASE_ATTACK_POWER
    private var bossDefense = BOSS_BASE_DEFENSE

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

    private var goldLooted = SLAYING_BASE_GOLD
    private var progressMonsterSlaying = 0
    private var progressKillBoss = 0
    private var gemChanceToDrop = 5 // %

    private val handler = Handler()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        onWindowFocusChanged(true)


        // inicjalizacja Button
        val buttonRebirth: ImageButton = findViewById(R.id.btn_rebirth)
        val buttonOptions: ImageButton = findViewById(R.id.btn_options)
        val buttonSlay: Button = findViewById(R.id.btn_slay)
        buttonSlay.text = getSmallCapsString("Click to SLAY!")
        buttonSlay.setOnClickListener(clickListener)
        val buttonKillBoss: Button = findViewById(R.id.btn_kill_boss)
        buttonKillBoss.text = getSmallCapsString("Fight the Boss!")
        buttonKillBoss.setOnClickListener(clickListener)

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

        val textBossName: TextView = findViewById(R.id.text_boss_name)
        textBossName.text = getSmallCapsString(getRandomBossName(this))

        val textBossLevel: TextView = findViewById(R.id.text_boss_level)
        textBossLevel.text = getSmallCapsString("Level $bossLevel")

        val textBossStats: TextView = findViewById(R.id.text_boss_stats)
        textBossStats.text = getSmallCapsString("Health: $bossHealth\n" +
                "Attack Power: $bossAttackPower\n" +
                "Defense: $bossDefense")


        monsterSlaying()
    }

    // click listener
    private val clickListener: View.OnClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.btn_kill_boss-> {
                killBoss(text_boss_name, text_boss_level, btn_kill_boss, text_boss_stats)
            }
        }
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
    private fun monsterSlaying() {

        fun randomGemDropChance(): Boolean {
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
                goldLooted = getNewValue(getRandomRangeValue(SLAYING_BASE_GOLD, 10.0), bossLevel, MULTIPLIER_1_07)
                text_gold_value.text = gold.toString()
                progressMonsterSlaying = 0
                progressBarMonsterSlaying.progress = progressMonsterSlaying

                monsterSlaying()
            }
        }).start()
    }

    // funkcja fight the boss progress bar
    private fun killBoss(textBossName :TextView, textBossLevel :TextView, buttonKillBoss: Button, textBossStats: TextView) {

        buttonKillBoss.text = getSmallCapsString("Fighting!")

        // inicjalizacja ProgressBar
        val progressBarKillBoss: ProgressBar = findViewById(R.id.progress_boss_health)

        if (buttonKillBoss.isActivated) {

        } else {
            buttonKillBoss.isActivated = true
            Thread(Runnable {
                while (progressKillBoss < 2000) {
                    progressKillBoss++
                    Thread.sleep(1)
                    handler.post { progressBarKillBoss.progress = progressKillBoss }
                }
                handler.post {
                    bossLevel++
                    exp++
                    gems++
                    bossHealth = getNewValue(getRandomRangeValue(BOSS_BASE_HEALTH, 10.00), bossLevel, MULTIPLIER_1_15)
                    bossAttackPower = getNewValue(getRandomRangeValue(BOSS_BASE_ATTACK_POWER, 10.00), bossLevel, MULTIPLIER_1_15)
                    bossDefense = getNewValue(getRandomRangeValue(BOSS_BASE_DEFENSE, 10.00),bossLevel, MULTIPLIER_1_15)
                    textBossStats.text = getSmallCapsString("Health: $bossHealth\n" +
                            "Attack Power: $bossAttackPower\n" +
                            "Defense: $bossDefense")
                    textBossLevel.text = getSmallCapsString("Level $bossLevel")
                    textBossName.text = getSmallCapsString(getRandomBossName(this))
                    text_exp_value.text = exp.toString()
                    text_gems_value.text = gems.toString()
                    progressKillBoss = 0
                    progressBarKillBoss.progress = progressKillBoss
                    buttonKillBoss.text = getSmallCapsString("Fight the Boss!")
                    buttonKillBoss.isActivated = false
                }
            }).start()
        }
    }


}
