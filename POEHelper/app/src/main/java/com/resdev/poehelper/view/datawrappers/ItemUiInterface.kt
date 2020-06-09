package com.resdev.poehelper.view.datawrappers

import com.resdev.poehelper.CurrentValue
import com.resdev.poehelper.R

interface ItemUiInterface {

    fun getName(): String

    fun getChaosValue():String

    fun getPercentage(): String

    fun roundDouble(value:Double):String

    fun getCorrupted():Boolean

    fun getPercentageColor():Int

    fun getUrl():String
}