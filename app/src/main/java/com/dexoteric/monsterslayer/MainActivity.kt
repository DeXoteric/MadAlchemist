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


var MULTIPLIER_1_07 = 1.07
var MULTIPLIER_1_15 = 1.15
var STAT_MULTIPLIER_LARGER = 120
var STAT_MULTIPLIER_LARGE = 100
var STAT_MULTIPLIER_NORMAL = 80
var STAT_MULTIPLIER_SMALL = 60
var STAT_MULTIPLIER_SMALLER = 40
var SLAYING_BASE_GOLD = 10
var BOSS_BASE_ATTACK_POWER = 1000
var BOSS_BASE_DEFENSE = 1000
var HERO_BASE_ATTACK_POWER = 1000
var HERO_BASE_DEFENSE = 1000
var SLAYING_TIMER = 1200
var BOSS_KILL_TIMER = 2000
var INVENTORY_BASE_COST = 10


class MainActivity : AppCompatActivity() {

    // inicjalizacja zmiennych
    private var prestige = 0
    private var gold = 0
    private var exp = 0
    private var gems = 0
    private var bossLevel = 1
    private var heroLevel = 1

    private var bossAttackPower = 900
    private var bossDefense = 900
    private var heroAttackPower = 0
    private var heroDefense = 0

    private var amuletTier = 1
    private var amuletLevel = 1
    private var amuletCost = setInventoryCost(INVENTORY_BASE_COST, amuletLevel, amuletTier)
    private var amuletAttackPower = setInventoryStat(amuletLevel, amuletTier, STAT_MULTIPLIER_LARGE)
    private var amuletDefense = setInventoryStat(amuletLevel, amuletTier, STAT_MULTIPLIER_SMALL)
    private var helmetTier = 1
    private var helmetLevel = 1
    private var helmetCost = setInventoryCost(INVENTORY_BASE_COST, helmetLevel, helmetTier)
    private var helmetAttackPower = setInventoryStat(helmetLevel, helmetTier, STAT_MULTIPLIER_SMALL)
    private var helmetDefense = setInventoryStat(helmetLevel, helmetTier, STAT_MULTIPLIER_LARGE)
    private var cloakTier = 1
    private var cloakLevel = 1
    private var cloakCost = setInventoryCost(INVENTORY_BASE_COST, cloakLevel, cloakTier)
    private var cloakAttackPower = setInventoryStat(cloakLevel, cloakTier, STAT_MULTIPLIER_NORMAL)
    private var cloakDefense = setInventoryStat(cloakLevel, cloakTier, STAT_MULTIPLIER_NORMAL)
    private var weaponTier = 1
    private var weaponLevel = 1
    private var weaponCost = setInventoryCost(INVENTORY_BASE_COST, weaponLevel, weaponTier)
    private var weaponAttackPower = setInventoryStat(weaponLevel, weaponTier, STAT_MULTIPLIER_LARGER)
    private var weaponDefense = setInventoryStat(weaponLevel, weaponTier, STAT_MULTIPLIER_SMALLER)
    private var chestplateTier = 1
    private var chestplateLevel = 1
    private var chestplateCost = setInventoryCost(INVENTORY_BASE_COST, chestplateLevel, chestplateTier)
    private var chestplateAttackPower = setInventoryStat(chestplateLevel, chestplateTier, STAT_MULTIPLIER_SMALLER)
    private var chestplateDefense = setInventoryStat(chestplateLevel, chestplateTier, STAT_MULTIPLIER_LARGER)
    private var shieldTier = 1
    private var shieldLevel = 1
    private var shieldCost = setInventoryCost(INVENTORY_BASE_COST, shieldLevel, shieldTier)
    private var shieldAttackPower = setInventoryStat(shieldLevel, shieldTier, STAT_MULTIPLIER_SMALLER)
    private var shieldDefense = setInventoryStat(shieldLevel, shieldTier, STAT_MULTIPLIER_LARGER)
    private var glovesTier = 1
    private var glovesLevel = 1
    private var glovesCost = setInventoryCost(INVENTORY_BASE_COST, glovesLevel, glovesTier)
    private var glovesAttackPower = setInventoryStat(glovesLevel, glovesTier, STAT_MULTIPLIER_LARGE)
    private var glovesDefense = setInventoryStat(glovesLevel, glovesTier, STAT_MULTIPLIER_SMALL)
    private var leggingsTier = 1
    private var leggingsLevel = 1
    private var leggingsCost = setInventoryCost(INVENTORY_BASE_COST, leggingsLevel, leggingsTier)
    private var leggingsAttackPower = setInventoryStat(leggingsLevel, leggingsTier, STAT_MULTIPLIER_SMALL)
    private var leggingsDefense = setInventoryStat(leggingsLevel, leggingsTier, STAT_MULTIPLIER_LARGE)
    private var beltTier = 1
    private var beltLevel = 1
    private var beltCost = setInventoryCost(INVENTORY_BASE_COST, beltLevel, beltTier)
    private var beltAttackPower = setInventoryStat(beltLevel, beltTier, STAT_MULTIPLIER_LARGE)
    private var beltDefense = setInventoryStat(beltLevel, beltTier, STAT_MULTIPLIER_SMALL)
    private var ringTier = 1
    private var ringLevel = 1
    private var ringCost = setInventoryCost(INVENTORY_BASE_COST, ringLevel, ringTier)
    private var ringAttackPower = setInventoryStat(ringLevel, ringTier, STAT_MULTIPLIER_LARGER)
    private var ringDefense = setInventoryStat(ringLevel, ringTier, STAT_MULTIPLIER_SMALLER)
    private var bootsTier = 1
    private var bootsLevel = 1
    private var bootsCost = setInventoryCost(INVENTORY_BASE_COST, bootsLevel, bootsTier)
    private var bootsAttackPower = setInventoryStat(bootsLevel, bootsTier, STAT_MULTIPLIER_SMALL)
    private var bootsDefense = setInventoryStat(bootsLevel, bootsTier, STAT_MULTIPLIER_LARGE)


    private var goldLooted = SLAYING_BASE_GOLD
    private var progressMonsterSlaying = 0
    private var progressKillBoss = 0
    private var gemChanceToDrop = 5 // %

    private val handler = Handler()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        onWindowFocusChanged(true)


        initAllViews()
        refreshHeroStats(text_stats)
        monsterSlaying()


    }

    private fun initAllViews() {

        // inicjalizacja Button
        val buttonOptions: ImageButton = findViewById(R.id.btn_options)
        buttonOptions.setOnClickListener(clickListener)
        val buttonRebirth: ImageButton = findViewById(R.id.btn_rebirth)
        buttonRebirth.setOnClickListener(clickListener)
        val buttonSummary: ImageButton = findViewById(R.id.btn_summary)
        buttonSummary.setOnClickListener(clickListener)
        val buttonTraining: ImageButton = findViewById(R.id.btn_training)
        buttonTraining.setOnClickListener(clickListener)
        val buttonPerks: ImageButton = findViewById(R.id.btn_perks)
        buttonPerks.setOnClickListener(clickListener)
        val buttonAchievements: ImageButton = findViewById(R.id.btn_achievements)
        buttonAchievements.setOnClickListener(clickListener)
        val buttonSlay: Button = findViewById(R.id.btn_slay)
        buttonSlay.text = getSmallCapsString("Click to SLAY!")
        buttonSlay.setOnClickListener(clickListener)
        val buttonKillBoss: Button = findViewById(R.id.btn_kill_boss)
        buttonKillBoss.text = getSmallCapsString("Fight the Boss!")
        buttonKillBoss.setOnClickListener(clickListener)

        // inicjalizacja inventory buttons
        val buttonAmulet: ImageButton = findViewById(R.id.btn_amulet)
        buttonAmulet.setOnClickListener(clickListener)
        val buttonHelmet: ImageButton = findViewById(R.id.btn_helmet)
        buttonHelmet.setOnClickListener(clickListener)
        val buttonCloak: ImageButton = findViewById(R.id.btn_cloak)
        buttonCloak.setOnClickListener(clickListener)
        val buttonWeapon: ImageButton = findViewById(R.id.btn_weapon)
        buttonWeapon.setOnClickListener(clickListener)
        val buttonChestplate: ImageButton = findViewById(R.id.btn_chestplate)
        buttonChestplate.setOnClickListener(clickListener)
        val buttonShield: ImageButton = findViewById(R.id.btn_shield)
        buttonShield.setOnClickListener(clickListener)
        val buttonGloves: ImageButton = findViewById(R.id.btn_gloves)
        buttonGloves.setOnClickListener(clickListener)
        val buttonLeggings: ImageButton = findViewById(R.id.btn_leggings)
        buttonLeggings.setOnClickListener(clickListener)
        val buttonBelt: ImageButton = findViewById(R.id.btn_belt)
        buttonBelt.setOnClickListener(clickListener)
        val buttonRing: ImageButton = findViewById(R.id.btn_ring)
        buttonRing.setOnClickListener(clickListener)
        val buttonBoots: ImageButton = findViewById(R.id.btn_boots)
        buttonBoots.setOnClickListener(clickListener)


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
        textAmuletLevel.text = getSmallCapsString("Tier $amuletTier : Level $amuletLevel\n" + "Price: ${format(amuletCost)}")
        val textAmuletName: TextView = findViewById(R.id.text_amulet_name)
        textAmuletName.text = getSmallCapsString("Amulet")

        val textHelmetLevel: TextView = findViewById(R.id.text_helmet_level)
        textHelmetLevel.text = getSmallCapsString("Tier $helmetTier : Level $helmetLevel\n" + "Price: ${format(helmetCost)}")
        val textHelmetName: TextView = findViewById(R.id.text_helmet_name)
        textHelmetName.text = getSmallCapsString("Helmet")

        val textCloakLevel: TextView = findViewById(R.id.text_cloak_level)
        textCloakLevel.text = getSmallCapsString("Tier $cloakTier : Level $cloakLevel\n" + "Price: ${format(cloakCost)}")
        val textCloakName: TextView = findViewById(R.id.text_cloak_name)
        textCloakName.text = getSmallCapsString("Cloak")

        val textWeaponLevel: TextView = findViewById(R.id.text_weapon_level)
        textWeaponLevel.text = getSmallCapsString("Tier $weaponTier : Level $weaponLevel\n" + "Price: ${format(weaponCost)}")
        val textWeaponName: TextView = findViewById(R.id.text_weapon_name)
        textWeaponName.text = getSmallCapsString("Weapon")

        val textChestplateLevel: TextView = findViewById(R.id.text_chestplate_level)
        textChestplateLevel.text = getSmallCapsString("Tier $chestplateTier : Level $chestplateLevel\n" + "Price: ${format(chestplateCost)}")
        val textChestplateName: TextView = findViewById(R.id.text_chestplate_name)
        textChestplateName.text = getSmallCapsString("Chestplate")

        val textShieldLevel: TextView = findViewById(R.id.text_shield_level)
        textShieldLevel.text = getSmallCapsString("Tier $shieldTier : Level $shieldLevel\n" + "Price: ${format(shieldCost)}")
        val textShieldName: TextView = findViewById(R.id.text_shield_name)
        textShieldName.text = getSmallCapsString("Shield")

        val textGlovesLevel: TextView = findViewById(R.id.text_gloves_level)
        textGlovesLevel.text = getSmallCapsString("Tier $glovesTier : Level $glovesLevel\n" + "Price: ${format(glovesCost)}")
        val textGlovesName: TextView = findViewById(R.id.text_gloves_name)
        textGlovesName.text = getSmallCapsString("Gloves")

        val textLeggingsLevel: TextView = findViewById(R.id.text_leggings_level)
        textLeggingsLevel.text = getSmallCapsString("Tier $leggingsTier : Level $leggingsLevel\n" + "Price: ${format(leggingsCost)}")
        val textLeggingsName: TextView = findViewById(R.id.text_leggings_name)
        textLeggingsName.text = getSmallCapsString("Leggings")

        val textBeltLevel: TextView = findViewById(R.id.text_belt_level)
        textBeltLevel.text = getSmallCapsString("Tier $beltTier : Level $beltLevel\n" + "Price: ${format(beltCost)}")
        val textBeltName: TextView = findViewById(R.id.text_belt_name)
        textBeltName.text = getSmallCapsString("Belt")

        val textRingLevel: TextView = findViewById(R.id.text_ring_level)
        textRingLevel.text = getSmallCapsString("Tier $ringTier : Level $ringLevel\n" + "Price: ${format(ringCost)}")
        val textRingName: TextView = findViewById(R.id.text_ring_name)
        textRingName.text = getSmallCapsString("Ring")

        val textBootsLevel: TextView = findViewById(R.id.text_boots_level)
        textBootsLevel.text = getSmallCapsString("Tier $bootsTier : Level $bootsLevel\n" + "Price: ${format(bootsCost)}")
        val textBootsName: TextView = findViewById(R.id.text_boots_name)
        textBootsName.text = getSmallCapsString("Boots")


        val textHeroStats: TextView = findViewById(R.id.text_hero_stats)
        textHeroStats.text = getSmallCapsString("Hero Stats")
        val textStats: TextView = findViewById(R.id.text_stats)
        textStats.text = getSmallCapsString("Hero Level: ${format(heroLevel)}\n" +
                "Attack Power: ${format(heroAttackPower)}\n" +
                "Defense: ${format(heroDefense)}")

        val textMonsterSlaying: TextView = findViewById(R.id.text_monster_slaying)
        textMonsterSlaying.text = getSmallCapsString("Monster Slaying")

        val textBossName: TextView = findViewById(R.id.text_boss_name)
        textBossName.text = getSmallCapsString(getRandomBossName(this))

        val textBossLevel: TextView = findViewById(R.id.text_boss_level)
        textBossLevel.text = getSmallCapsString("Level $bossLevel")

        val textBossStats: TextView = findViewById(R.id.text_boss_stats)
        textBossStats.text = getSmallCapsString("Attack Power: ${format(bossAttackPower)}\n" +
                "Defense: ${format(bossDefense)}")

        val textBossMsg: TextView = findViewById(R.id.text_boss_msg)
        textBossMsg.text = ""
    }

    // click listener
    private val clickListener: View.OnClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.btn_kill_boss -> {
                killBoss(text_boss_name, text_boss_level, btn_kill_boss, text_boss_stats, text_boss_msg)
            }
            R.id.btn_slay -> {
                gold += (goldLooted / 2)
                text_gold_value.text = format(gold)
                println("Gold from slaying: ${goldLooted / 2}")
            }
            R.id.btn_rebirth -> {

            }
            R.id.btn_summary -> {

            }
            R.id.btn_training -> {

            }
            R.id.btn_perks -> {

            }
            R.id.btn_achievements -> {

            }
            R.id.btn_amulet -> {
                if (amuletCost > gold) {

                } else {
                    if (amuletLevel == 100) {
                        amuletTier++
                        amuletLevel = 1
                        amuletAttackPower = setInventoryStat(amuletLevel, amuletTier, STAT_MULTIPLIER_LARGE)
                        amuletDefense = setInventoryStat(amuletLevel, amuletTier, STAT_MULTIPLIER_SMALL)

                    } else {
                        amuletLevel++
                        amuletAttackPower = setInventoryStat(amuletLevel, amuletTier, STAT_MULTIPLIER_LARGE)
                        amuletDefense = setInventoryStat(amuletLevel, amuletTier, STAT_MULTIPLIER_SMALL)

                    }
                    gold -= amuletCost
                    text_gold_value.text = format(gold)
                }
                refreshHeroStats(text_stats)
                amuletCost = setInventoryCost(INVENTORY_BASE_COST, amuletLevel, amuletTier)
                text_amulet_level.text = getSmallCapsString("Tier $amuletTier : Level $amuletLevel\n" + "Price: ${format(amuletCost)}")
            }
            R.id.btn_helmet -> {
                if (helmetCost > gold) {

                } else {
                    if (helmetLevel == 100) {
                        helmetTier++
                        helmetLevel = 1
                        helmetAttackPower = setInventoryStat(helmetLevel, helmetTier, STAT_MULTIPLIER_SMALL)
                        helmetDefense = setInventoryStat(helmetLevel, helmetTier, STAT_MULTIPLIER_LARGE)

                    } else {
                        helmetLevel++
                        helmetAttackPower = setInventoryStat(helmetLevel, helmetTier, STAT_MULTIPLIER_SMALL)
                        helmetDefense = setInventoryStat(helmetLevel, helmetTier, STAT_MULTIPLIER_LARGE)

                    }
                    gold -= helmetCost
                    text_gold_value.text = format(gold)
                }
                refreshHeroStats(text_stats)
                helmetCost = setInventoryCost(INVENTORY_BASE_COST, helmetLevel, helmetTier)
                text_helmet_level.text = getSmallCapsString("Tier $helmetTier : Level $helmetLevel\n" + "Price: ${format(helmetCost)}")
            }
            R.id.btn_cloak -> {
                if (cloakCost > gold) {

                } else {
                    if (cloakLevel == 100) {
                        cloakTier++
                        cloakLevel = 1
                        cloakAttackPower = setInventoryStat(cloakLevel, cloakTier, STAT_MULTIPLIER_NORMAL)
                        cloakDefense = setInventoryStat(cloakLevel, cloakTier, STAT_MULTIPLIER_NORMAL)

                    } else {
                        cloakLevel++
                        cloakAttackPower = setInventoryStat(cloakLevel, cloakTier, STAT_MULTIPLIER_NORMAL)
                        cloakDefense = setInventoryStat(cloakLevel, cloakTier, STAT_MULTIPLIER_NORMAL)

                    }
                    gold -= cloakCost
                    text_gold_value.text = format(gold)
                    text_weapon_level.text = getSmallCapsString("Tier $weaponTier : Level $weaponLevel\n" + "Price: ${format(weaponCost)}")
                }
                refreshHeroStats(text_stats)
                cloakCost = setInventoryCost(INVENTORY_BASE_COST, cloakLevel, cloakTier)
                text_cloak_level.text = getSmallCapsString("Tier $cloakTier : Level $cloakLevel\n" + "Price: ${format(cloakCost)}")
            }
            R.id.btn_weapon -> {
                if (weaponCost > gold) {

                } else {
                    if (weaponLevel == 100) {
                        weaponTier++
                        weaponLevel = 1
                        weaponAttackPower = setInventoryStat(weaponLevel, weaponTier, STAT_MULTIPLIER_LARGER)
                        weaponDefense = setInventoryStat(weaponLevel, weaponTier, STAT_MULTIPLIER_SMALLER)

                    } else {
                        weaponLevel++
                        weaponAttackPower = setInventoryStat(weaponLevel, weaponTier, STAT_MULTIPLIER_LARGER)
                        weaponDefense = setInventoryStat(weaponLevel, weaponTier, STAT_MULTIPLIER_SMALLER)


                    }
                    gold -= weaponCost
                    text_gold_value.text = format(gold)

                }
                refreshHeroStats(text_stats)
                weaponCost = setInventoryCost(INVENTORY_BASE_COST, weaponLevel, weaponTier)
                text_weapon_level.text = getSmallCapsString("Tier $weaponTier : Level $weaponLevel\n" + "Price: ${format(weaponCost)}")
            }
            R.id.btn_chestplate -> {
                if (chestplateCost > gold) {

                } else {
                    if (chestplateLevel == 100) {
                        chestplateTier++
                        chestplateLevel = 1
                        chestplateAttackPower = setInventoryStat(chestplateLevel, chestplateTier, STAT_MULTIPLIER_SMALLER)
                        chestplateDefense = setInventoryStat(chestplateLevel, chestplateTier, STAT_MULTIPLIER_LARGER)

                    } else {
                        chestplateLevel++
                        chestplateAttackPower = setInventoryStat(chestplateLevel, chestplateTier, STAT_MULTIPLIER_SMALLER)
                        chestplateDefense = setInventoryStat(chestplateLevel, chestplateTier, STAT_MULTIPLIER_LARGER)

                    }
                    gold -= chestplateCost
                    text_gold_value.text = format(gold)
                }
                refreshHeroStats(text_stats)
                chestplateCost = setInventoryCost(INVENTORY_BASE_COST, chestplateLevel, chestplateTier)
                text_chestplate_level.text = getSmallCapsString("Tier $chestplateTier : Level $chestplateLevel\n" + "Price: ${format(chestplateCost)}")
            }
            R.id.btn_shield -> {
                if (shieldCost > gold) {

                } else {
                    if (shieldLevel == 100) {
                        shieldTier++
                        shieldLevel = 1
                        shieldAttackPower = setInventoryStat(shieldLevel, shieldTier, STAT_MULTIPLIER_SMALLER)
                        shieldDefense = setInventoryStat(shieldLevel, shieldTier, STAT_MULTIPLIER_LARGER)

                    } else {
                        shieldLevel++
                        shieldAttackPower = setInventoryStat(shieldLevel, shieldTier, STAT_MULTIPLIER_SMALLER)
                        shieldDefense = setInventoryStat(shieldLevel, shieldTier, STAT_MULTIPLIER_LARGER)

                    }
                    gold -= shieldCost
                    text_gold_value.text = format(gold)

                }
                refreshHeroStats(text_stats)
                shieldCost = setInventoryCost(INVENTORY_BASE_COST, shieldLevel, shieldTier)
                text_shield_level.text = getSmallCapsString("Tier $shieldTier : Level $shieldLevel\n" + "Price: ${format(shieldCost)}")
            }
            R.id.btn_gloves -> {
                if (glovesCost > gold) {

                } else {
                    if (glovesLevel == 100) {
                        glovesTier++
                        glovesLevel = 1
                        glovesAttackPower = setInventoryStat(glovesLevel, glovesTier, STAT_MULTIPLIER_LARGE)
                        glovesDefense = setInventoryStat(glovesLevel, glovesTier, STAT_MULTIPLIER_SMALL)

                    } else {
                        glovesLevel++
                        glovesAttackPower = setInventoryStat(glovesLevel, glovesTier, STAT_MULTIPLIER_LARGE)
                        glovesDefense = setInventoryStat(glovesLevel, glovesTier, STAT_MULTIPLIER_SMALL)

                    }
                    gold -= glovesCost
                    text_gold_value.text = format(gold)
                }
                refreshHeroStats(text_stats)
                glovesCost = setInventoryCost(INVENTORY_BASE_COST, glovesLevel, glovesTier)
                text_gloves_level.text = getSmallCapsString("Tier $glovesTier : Level $glovesLevel\n" + "Price: ${format(glovesCost)}")
            }
            R.id.btn_leggings -> {
                if (leggingsCost > gold) {

                } else {
                    if (leggingsLevel == 100) {
                        leggingsTier++
                        leggingsLevel = 1
                        leggingsAttackPower = setInventoryStat(leggingsLevel, leggingsTier, STAT_MULTIPLIER_SMALL)
                        leggingsDefense = setInventoryStat(leggingsLevel, leggingsTier, STAT_MULTIPLIER_LARGE)

                    } else {
                        leggingsLevel++
                        leggingsAttackPower = setInventoryStat(leggingsLevel, leggingsTier, STAT_MULTIPLIER_SMALL)
                        leggingsDefense = setInventoryStat(leggingsLevel, leggingsTier, STAT_MULTIPLIER_LARGE)

                    }
                    gold -= leggingsCost
                    text_gold_value.text = format(gold)

                }
                refreshHeroStats(text_stats)
                leggingsCost = setInventoryCost(INVENTORY_BASE_COST, leggingsLevel, leggingsTier)
                text_leggings_level.text = getSmallCapsString("Tier $leggingsTier : Level $leggingsLevel\n" + "Price: ${format(leggingsCost)}")
            }
            R.id.btn_belt -> {
                if (beltCost > gold) {

                } else {
                    if (beltLevel == 100) {
                        beltTier++
                        beltLevel = 1
                        beltAttackPower = setInventoryStat(beltLevel, beltTier, STAT_MULTIPLIER_LARGE)
                        beltDefense = setInventoryStat(beltLevel, beltTier, STAT_MULTIPLIER_SMALL)

                    } else {
                        beltLevel++
                        beltAttackPower = setInventoryStat(beltLevel, beltTier, STAT_MULTIPLIER_LARGE)
                        beltDefense = setInventoryStat(beltLevel, beltTier, STAT_MULTIPLIER_SMALL)

                    }
                    gold -= beltCost
                    text_gold_value.text = format(gold)
                }
                refreshHeroStats(text_stats)
                beltCost = setInventoryCost(INVENTORY_BASE_COST, beltLevel, beltTier)
                text_belt_level.text = getSmallCapsString("Tier $beltTier : Level $beltLevel\n" + "Price: ${format(beltCost)}")
            }
            R.id.btn_ring -> {
                if (ringCost > gold) {

                } else {
                    if (ringLevel == 100) {
                        ringTier++
                        ringLevel = 1
                        ringAttackPower = setInventoryStat(ringLevel, ringTier, STAT_MULTIPLIER_LARGER)
                        ringDefense = setInventoryStat(ringLevel, ringTier, STAT_MULTIPLIER_SMALLER)

                    } else {
                        ringLevel++
                        ringAttackPower = setInventoryStat(ringLevel, ringTier, STAT_MULTIPLIER_LARGER)
                        ringDefense = setInventoryStat(ringLevel, ringTier, STAT_MULTIPLIER_SMALLER)

                    }
                    gold -= ringCost
                    text_gold_value.text = format(gold)
                }
                refreshHeroStats(text_stats)
                ringCost = setInventoryCost(INVENTORY_BASE_COST, ringLevel, ringTier)
                text_ring_level.text = getSmallCapsString("Tier $ringTier : Level $ringLevel\n" + "Price: ${format(ringCost)}")
            }
            R.id.btn_boots -> {
                if (bootsCost > gold) {

                } else {
                    if (bootsLevel == 100) {
                        bootsTier++
                        bootsLevel = 1
                        bootsAttackPower = setInventoryStat(bootsLevel, bootsTier, STAT_MULTIPLIER_SMALL)
                        bootsDefense = setInventoryStat(bootsLevel, bootsTier, STAT_MULTIPLIER_LARGE)

                    } else {
                        bootsLevel++
                        bootsAttackPower = setInventoryStat(bootsLevel, bootsTier, STAT_MULTIPLIER_SMALL)
                        bootsDefense = setInventoryStat(bootsLevel, bootsTier, STAT_MULTIPLIER_LARGE)

                    }
                    gold -= bootsCost
                    text_gold_value.text = format(gold)
                }
                refreshHeroStats(text_stats)
                bootsCost = setInventoryCost(INVENTORY_BASE_COST, bootsLevel, bootsTier)
                text_boots_level.text = getSmallCapsString("Tier $bootsTier : Level $bootsLevel\n" + "Price: ${format(bootsCost)}")
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

        fun randomGoldLooted(): Int {
            val minGold = getNewValue(getMinValue(SLAYING_BASE_GOLD, 10.0), bossLevel, MULTIPLIER_1_07)
            val maxGold = getNewValue(getMaxValue(SLAYING_BASE_GOLD, 10.0), bossLevel, MULTIPLIER_1_07)

            return Random().nextInt(maxGold - minGold + 1) + minGold
        }

        // inicjalizacja ProgressBar
        val progressBarMonsterSlaying: ProgressBar = findViewById(R.id.progress_monster_slaying)

        // inicjalizacja TextView
        val textGoldLooted: TextView = findViewById(R.id.text_gold_looted)

        Thread(Runnable {
            while (progressMonsterSlaying < SLAYING_TIMER) {
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
                goldLooted = randomGoldLooted()
//                goldLooted = getNewValue(getRandomRangeValue(SLAYING_BASE_GOLD, 10.0), bossLevel, MULTIPLIER_1_07)
                text_gold_value.text = format(gold)
                progressMonsterSlaying = 0
                progressBarMonsterSlaying.progress = progressMonsterSlaying

                monsterSlaying()
            }
        }).start()
    }

    // funkcja fight the boss progress bar
    private fun killBoss(textBossName: TextView, textBossLevel: TextView, buttonKillBoss: Button, textBossStats: TextView, textBossMsg: TextView) {

        fun randomAttackPower(): Int {
            val minPower = getNewValue(getMinValue(BOSS_BASE_ATTACK_POWER, 10.0), bossLevel, MULTIPLIER_1_07)
            val maxPower = getNewValue(getMaxValue(BOSS_BASE_ATTACK_POWER, 10.0), bossLevel, MULTIPLIER_1_07)

            return Random().nextInt(maxPower - minPower + 1) + minPower
        }

        fun randomDefence(): Int {
            val minDefence = getNewValue(getMinValue(BOSS_BASE_DEFENSE, 10.0), bossLevel, MULTIPLIER_1_07)
            val maxDefence = getNewValue(getMaxValue(BOSS_BASE_DEFENSE, 10.0), bossLevel, MULTIPLIER_1_07)

            return Random().nextInt(maxDefence - minDefence + 1) + minDefence
        }

        buttonKillBoss.text = getSmallCapsString("Fighting!")

        // inicjalizacja ProgressBar
        val progressBarKillBoss: ProgressBar = findViewById(R.id.progress_boss_health)

        if (buttonKillBoss.isActivated) {

        } else {
            if (heroAttackPower < bossDefense && heroDefense < bossAttackPower) {
                textBossMsg.text = getSmallCapsString("You are too weak!")
            } else if (heroAttackPower < bossDefense && heroDefense > bossAttackPower) {
                textBossMsg.text = getSmallCapsString("Boss is too tough!")
            } else if (heroAttackPower > bossDefense && heroDefense < bossAttackPower) {
                textBossMsg.text = getSmallCapsString("Boss is too strong!")
            } else {
                buttonKillBoss.isActivated = true
                textBossMsg.text = ""
                Thread(Runnable {
                    while (progressKillBoss < BOSS_KILL_TIMER) {
                        progressKillBoss++
                        Thread.sleep(1)
                        handler.post { progressBarKillBoss.progress = progressKillBoss }
                    }
                    handler.post {
                        bossLevel++
                        exp++
                        gems++
                        bossAttackPower = randomAttackPower()
                        bossDefense = randomDefence()
                        textBossStats.text = getSmallCapsString("Attack Power: ${format(bossAttackPower)}\n" +
                                "Defense: ${format(bossDefense)}")
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

    private fun refreshHeroStats(textStats: TextView) {
        heroAttackPower = HERO_BASE_ATTACK_POWER +
                amuletAttackPower + helmetAttackPower + cloakAttackPower +
                weaponAttackPower + chestplateAttackPower + shieldAttackPower +
                glovesAttackPower + leggingsAttackPower + beltAttackPower +
                ringAttackPower + bootsAttackPower
        heroDefense = HERO_BASE_DEFENSE +
                amuletDefense + helmetDefense + cloakDefense +
                weaponDefense + chestplateDefense + shieldDefense +
                glovesDefense + leggingsDefense + beltDefense +
                ringDefense + bootsDefense
        textStats.text = getSmallCapsString("Hero Level: ${format(heroLevel)}\n" +
                "Attack Power: ${format(heroAttackPower)}\n" +
                "Defense: ${format(heroDefense)}")
    }


}
