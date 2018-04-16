package com.dexoteric.monsterslayer

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*
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

        // inicjalizacja inventory layouts
        val layoutAmulet: LinearLayout = findViewById(R.id.layout_amulet)
        layoutAmulet.setOnClickListener(clickListener)
        val layoutHelmet: LinearLayout = findViewById(R.id.layout_helmet)
        layoutHelmet.setOnClickListener(clickListener)
        val layoutCloak: LinearLayout = findViewById(R.id.layout_cloak)
        layoutCloak.setOnClickListener(clickListener)
        val layoutWeapon: LinearLayout = findViewById(R.id.layout_weapon)
        layoutWeapon.setOnClickListener(clickListener)
        val layoutChestplate: LinearLayout = findViewById(R.id.layout_chestplate)
        layoutChestplate.setOnClickListener(clickListener)
        val layoutShield: LinearLayout = findViewById(R.id.layout_shield)
        layoutShield.setOnClickListener(clickListener)
        val layoutGloves: LinearLayout = findViewById(R.id.layout_gloves)
        layoutGloves.setOnClickListener(clickListener)
        val layoutLeggings: LinearLayout = findViewById(R.id.layout_leggings)
        layoutLeggings.setOnClickListener(clickListener)
        val layoutBelt: LinearLayout = findViewById(R.id.layout_belt)
        layoutBelt.setOnClickListener(clickListener)
        val layoutRing: LinearLayout = findViewById(R.id.layout_ring)
        layoutRing.setOnClickListener(clickListener)
        val layoutBoots: LinearLayout = findViewById(R.id.layout_boots)
        layoutBoots.setOnClickListener(clickListener)


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
            R.id.layout_amulet -> {
                if (amuletCost > gold) {
                    Toast.makeText(this, "Amulet Cost: ${format(amuletCost)}", Toast.LENGTH_SHORT).show()
                } else {
                    if (amuletLevel == 100) {
                        amuletTier++
                        amuletLevel = 1
                        text_amulet_tier.text = amuletTier.toString()
                        text_amulet_level.text = amuletLevel.toString()
                        amuletAttackPower = setInventoryStat(amuletLevel, amuletTier, STAT_MULTIPLIER_LARGE)
                        amuletDefense = setInventoryStat(amuletLevel, amuletTier, STAT_MULTIPLIER_SMALL)

                    } else {
                        amuletLevel++
                        text_amulet_level.text = amuletLevel.toString()
                        amuletAttackPower = setInventoryStat(amuletLevel, amuletTier, STAT_MULTIPLIER_LARGE)
                        amuletDefense = setInventoryStat(amuletLevel, amuletTier, STAT_MULTIPLIER_SMALL)

                    }
                    gold -= amuletCost
                    text_gold_value.text = format(gold)
                }
                refreshHeroStats(text_stats)
                amuletCost = setInventoryCost(INVENTORY_BASE_COST, amuletLevel, amuletTier)
            }
            R.id.layout_helmet -> {
                if (helmetCost > gold) {

                } else {
                    if (helmetLevel == 100) {
                        helmetTier++
                        helmetLevel = 1
                        text_helmet_tier.text = helmetTier.toString()
                        text_helmet_level.text = helmetLevel.toString()
                        helmetAttackPower = setInventoryStat(helmetLevel, helmetTier, STAT_MULTIPLIER_SMALL)
                        helmetDefense = setInventoryStat(helmetLevel, helmetTier, STAT_MULTIPLIER_LARGE)

                    } else {
                        helmetLevel++
                        text_helmet_level.text = helmetLevel.toString()
                        helmetAttackPower = setInventoryStat(helmetLevel, helmetTier, STAT_MULTIPLIER_SMALL)
                        helmetDefense = setInventoryStat(helmetLevel, helmetTier, STAT_MULTIPLIER_LARGE)

                    }
                    gold -= helmetCost
                    text_gold_value.text = format(gold)
                }
                refreshHeroStats(text_stats)
                helmetCost = setInventoryCost(INVENTORY_BASE_COST, helmetLevel, helmetTier)
            }
            R.id.layout_cloak -> {
                if (cloakCost > gold) {

                } else {
                    if (cloakLevel == 100) {
                        cloakTier++
                        cloakLevel = 1
                        text_cloak_tier.text = cloakTier.toString()
                        text_cloak_level.text = cloakLevel.toString()
                        cloakAttackPower = setInventoryStat(cloakLevel, cloakTier, STAT_MULTIPLIER_NORMAL)
                        cloakDefense = setInventoryStat(cloakLevel, cloakTier, STAT_MULTIPLIER_NORMAL)

                    } else {
                        cloakLevel++
                        text_cloak_level.text = cloakLevel.toString()
                        cloakAttackPower = setInventoryStat(cloakLevel, cloakTier, STAT_MULTIPLIER_NORMAL)
                        cloakDefense = setInventoryStat(cloakLevel, cloakTier, STAT_MULTIPLIER_NORMAL)

                    }
                    gold -= cloakCost
                    text_gold_value.text = format(gold)
                }
                refreshHeroStats(text_stats)
                cloakCost = setInventoryCost(INVENTORY_BASE_COST, cloakLevel, cloakTier)
            }
            R.id.layout_weapon -> {
                if (weaponCost > gold) {

                } else {
                    if (weaponLevel == 100) {
                        weaponTier++
                        weaponLevel = 1
                        text_weapon_tier.text = weaponTier.toString()
                        text_weapon_level.text = weaponLevel.toString()
                        weaponAttackPower = setInventoryStat(weaponLevel, weaponTier, STAT_MULTIPLIER_LARGER)
                        weaponDefense = setInventoryStat(weaponLevel, weaponTier, STAT_MULTIPLIER_SMALLER)

                    } else {
                        weaponLevel++
                        text_weapon_level.text = weaponLevel.toString()
                        weaponAttackPower = setInventoryStat(weaponLevel, weaponTier, STAT_MULTIPLIER_LARGER)
                        weaponDefense = setInventoryStat(weaponLevel, weaponTier, STAT_MULTIPLIER_SMALLER)


                    }
                    gold -= weaponCost
                    text_gold_value.text = format(gold)
                }
                refreshHeroStats(text_stats)
                weaponCost = setInventoryCost(INVENTORY_BASE_COST, weaponLevel, weaponTier)
            }
            R.id.layout_chestplate -> {
                if (chestplateCost > gold) {

                } else {
                    if (chestplateLevel == 100) {
                        chestplateTier++
                        chestplateLevel = 1
                        text_chestplate_tier.text = chestplateTier.toString()
                        text_chestplate_level.text = chestplateLevel.toString()
                        chestplateAttackPower = setInventoryStat(chestplateLevel, chestplateTier, STAT_MULTIPLIER_SMALLER)
                        chestplateDefense = setInventoryStat(chestplateLevel, chestplateTier, STAT_MULTIPLIER_LARGER)

                    } else {
                        chestplateLevel++
                        text_chestplate_level.text = chestplateLevel.toString()
                        chestplateAttackPower = setInventoryStat(chestplateLevel, chestplateTier, STAT_MULTIPLIER_SMALLER)
                        chestplateDefense = setInventoryStat(chestplateLevel, chestplateTier, STAT_MULTIPLIER_LARGER)

                    }
                    gold -= chestplateCost
                    text_gold_value.text = format(gold)
                }
                refreshHeroStats(text_stats)
                chestplateCost = setInventoryCost(INVENTORY_BASE_COST, chestplateLevel, chestplateTier)
            }
            R.id.layout_shield -> {
                if (shieldCost > gold) {

                } else {
                    if (shieldLevel == 100) {
                        shieldTier++
                        shieldLevel = 1
                        text_shield_tier.text = shieldTier.toString()
                        text_shield_level.text = shieldLevel.toString()
                        shieldAttackPower = setInventoryStat(shieldLevel, shieldTier, STAT_MULTIPLIER_SMALLER)
                        shieldDefense = setInventoryStat(shieldLevel, shieldTier, STAT_MULTIPLIER_LARGER)

                    } else {
                        shieldLevel++
                        text_shield_level.text = shieldLevel.toString()
                        shieldAttackPower = setInventoryStat(shieldLevel, shieldTier, STAT_MULTIPLIER_SMALLER)
                        shieldDefense = setInventoryStat(shieldLevel, shieldTier, STAT_MULTIPLIER_LARGER)

                    }
                    gold -= shieldCost
                    text_gold_value.text = format(gold)
                }
                refreshHeroStats(text_stats)
                shieldCost = setInventoryCost(INVENTORY_BASE_COST, shieldLevel, shieldTier)
            }
            R.id.layout_gloves -> {
                if (glovesCost > gold) {

                } else {
                    if (glovesLevel == 100) {
                        glovesTier++
                        glovesLevel = 1
                        text_gloves_tier.text = glovesTier.toString()
                        text_gloves_level.text = glovesLevel.toString()
                        glovesAttackPower = setInventoryStat(glovesLevel, glovesTier, STAT_MULTIPLIER_LARGE)
                        glovesDefense = setInventoryStat(glovesLevel, glovesTier, STAT_MULTIPLIER_SMALL)

                    } else {
                        glovesLevel++
                        text_gloves_level.text = glovesLevel.toString()
                        glovesAttackPower = setInventoryStat(glovesLevel, glovesTier, STAT_MULTIPLIER_LARGE)
                        glovesDefense = setInventoryStat(glovesLevel, glovesTier, STAT_MULTIPLIER_SMALL)

                    }
                    gold -= glovesCost
                    text_gold_value.text = format(gold)
                }
                refreshHeroStats(text_stats)
                glovesCost = setInventoryCost(INVENTORY_BASE_COST, glovesLevel, glovesTier)
            }
            R.id.layout_leggings -> {
                if (leggingsCost > gold) {

                } else {
                    if (leggingsLevel == 100) {
                        leggingsTier++
                        leggingsLevel = 1
                        text_leggings_tier.text = leggingsTier.toString()
                        text_leggings_level.text = leggingsLevel.toString()
                        leggingsAttackPower = setInventoryStat(leggingsLevel, leggingsTier, STAT_MULTIPLIER_SMALL)
                        leggingsDefense = setInventoryStat(leggingsLevel, leggingsTier, STAT_MULTIPLIER_LARGE)

                    } else {
                        leggingsLevel++
                        text_leggings_level.text = leggingsLevel.toString()
                        leggingsAttackPower = setInventoryStat(leggingsLevel, leggingsTier, STAT_MULTIPLIER_SMALL)
                        leggingsDefense = setInventoryStat(leggingsLevel, leggingsTier, STAT_MULTIPLIER_LARGE)

                    }
                    gold -= leggingsCost
                    text_gold_value.text = format(gold)
                }
                refreshHeroStats(text_stats)
                leggingsCost = setInventoryCost(INVENTORY_BASE_COST, leggingsLevel, leggingsTier)
            }
            R.id.layout_belt -> {
                if (beltCost > gold) {

                } else {
                    if (beltLevel == 100) {
                        beltTier++
                        beltLevel = 1
                        text_belt_tier.text = beltTier.toString()
                        text_belt_level.text = beltLevel.toString()
                        beltAttackPower = setInventoryStat(beltLevel, beltTier, STAT_MULTIPLIER_LARGE)
                        beltDefense = setInventoryStat(beltLevel, beltTier, STAT_MULTIPLIER_SMALL)

                    } else {
                        beltLevel++
                        text_belt_level.text = beltLevel.toString()
                        beltAttackPower = setInventoryStat(beltLevel, beltTier, STAT_MULTIPLIER_LARGE)
                        beltDefense = setInventoryStat(beltLevel, beltTier, STAT_MULTIPLIER_SMALL)

                    }
                    gold -= beltCost
                    text_gold_value.text = format(gold)
                }
                refreshHeroStats(text_stats)
                beltCost = setInventoryCost(INVENTORY_BASE_COST, beltLevel, beltTier)
            }
            R.id.layout_ring -> {
                if (ringCost > gold) {

                } else {
                    if (ringLevel == 100) {
                        ringTier++
                        ringLevel = 1
                        text_ring_tier.text = ringTier.toString()
                        text_ring_level.text = ringLevel.toString()
                        ringAttackPower = setInventoryStat(ringLevel, ringTier, STAT_MULTIPLIER_LARGER)
                        ringDefense = setInventoryStat(ringLevel, ringTier, STAT_MULTIPLIER_SMALLER)

                    } else {
                        ringLevel++
                        text_ring_level.text = ringLevel.toString()
                        ringAttackPower = setInventoryStat(ringLevel, ringTier, STAT_MULTIPLIER_LARGER)
                        ringDefense = setInventoryStat(ringLevel, ringTier, STAT_MULTIPLIER_SMALLER)

                    }
                    gold -= ringCost
                    text_gold_value.text = format(gold)
                }
                refreshHeroStats(text_stats)
                ringCost = setInventoryCost(INVENTORY_BASE_COST, ringLevel, ringTier)
            }
            R.id.layout_boots -> {
                if (bootsCost > gold) {

                } else {
                    if (bootsLevel == 100) {
                        bootsTier++
                        bootsLevel = 1
                        text_boots_tier.text = bootsTier.toString()
                        text_boots_level.text = bootsLevel.toString()
                        bootsAttackPower = setInventoryStat(bootsLevel, bootsTier, STAT_MULTIPLIER_SMALL)
                        bootsDefense = setInventoryStat(bootsLevel, bootsTier, STAT_MULTIPLIER_LARGE)

                    } else {
                        bootsLevel++
                        text_boots_level.text = bootsLevel.toString()
                        bootsAttackPower = setInventoryStat(bootsLevel, bootsTier, STAT_MULTIPLIER_SMALL)
                        bootsDefense = setInventoryStat(bootsLevel, bootsTier, STAT_MULTIPLIER_LARGE)

                    }
                    gold -= bootsCost
                    text_gold_value.text = format(gold)
                }
                refreshHeroStats(text_stats)
                bootsCost = setInventoryCost(INVENTORY_BASE_COST, bootsLevel, bootsTier)
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
