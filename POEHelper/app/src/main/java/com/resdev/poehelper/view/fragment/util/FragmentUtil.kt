package com.resdev.poehelper.view.fragment.util

import com.resdev.poehelper.R


fun fromCodeToType(code: Int):String {
    return when (code) {
        R.id.nav_currency -> "Currency"
        R.id.nav_fragment -> "Fragment"
        R.id.nav_delirium_orb -> "DeliriumOrb"
        R.id.nav_watchstone -> "Watchstone"
//        R.id.nav_seed -> "Seed"
        R.id.nav_oil -> "Oil"
        R.id.nav_incubator -> "Incubator"
        R.id.nav_scarab -> "Scarab"
        R.id.nav_fossil -> "Fossil"
        R.id.nav_essence -> "Essence"
        R.id.nav_resonator -> "Resonator"
        R.id.nav_divination_card -> "DivinationCard"
        R.id.nav_skill_gem -> "SkillGem"
        R.id.nav_base_type -> "BaseType"
        R.id.nav_helmet_enchant -> "HelmetEnchant"
        R.id.nav_map -> "Map"
        R.id.nav_unique_armour -> "UniqueArmour"
        R.id.nav_unique_flask -> "UniqueFlask"
        R.id.nav_unique_weapon -> "UniqueWeapon"
        R.id.nav_unique_accessory -> "UniqueAccessory"
        R.id.nav_unique_jewel -> "UniqueJewel"
        R.id.nav_prophecy -> "Prophecy"
        R.id.nav_unique_map -> "UniqueMap"
        R.id.nav_beast -> "Beast"
        R.id.nav_vial -> "Vial"
        else -> ""
    }

}