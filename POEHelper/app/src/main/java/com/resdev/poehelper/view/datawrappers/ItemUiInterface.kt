package com.resdev.poehelper.view.datawrappers

import com.resdev.poehelper.CurrentValue
import com.resdev.poehelper.R

interface ItemUiInterface {

    fun getName(): String

    fun getChaosValue():String

    fun getPercentage(): String

    fun getQuality():String

    fun getTier():String
    fun getGemLvl():String
    fun hasGemLvl():Boolean
    fun hasTier():Boolean
    fun hasQuality(): Boolean
    fun anyApply():Boolean

    fun getCorrupted():Boolean

    fun getPercentageColor():Int

    fun getUrl():String
}