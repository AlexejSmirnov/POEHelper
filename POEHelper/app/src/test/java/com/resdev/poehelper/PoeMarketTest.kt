package com.resdev.poehelper

import com.resdev.poehelper.model.Config
import com.resdev.poehelper.model.Converter
import com.resdev.poehelper.model.retrofit.PoeMarket
import com.resdev.poehelper.model.retrofit.PoeNinjaLoading
import com.resdev.poehelper.model.room.ItemEntity
import com.resdev.poehelper.utils.URLUtils
import com.resdev.poehelper.utils.Util
import org.junit.Test

class PoeMarketTest{
    var types = listOf(
        "DeliriumOrb",
        "Watchstone",
        "Oil",
        "Incubator",
        "Scarab",
        "Fossil",
        "Essence",
        "Resonator",
        "DivinationCard",
        "SkillGem",
        "BaseType",
        "HelmetEnchant",
        "Map",
        "UniqueArmour",
        "UniqueFlask",
        "UniqueWeapon",
        "UniqueAccessory",
        "UniqueJewel",
        "Prophecy",
        "UniqueMap",
        "Beast",
        "Vial")
    init {
        val model = PoeNinjaLoading.loadItems("Standard", "Scarab")
        model.bindModel()
        PoeMarket.rebuildRetrofit()
    }


    @Test fun allTypesGenerating(){
        for (i in types){
            if (i.contains("")){
                val model = PoeNinjaLoading.loadItems("Standard", i)
                model.bindModel()
                val itemEntity = Converter.fromRetrofitItemToRoomEntity(model.lines[0], i)
                linkGenerating(itemEntity)
            }

        }
    }



    fun linkGenerating(itemEntity: ItemEntity){
        var link = PoeMarket.sendItemRequest(
            Config.league,
            (itemEntity))
        var fullUrl = URLUtils.generatePoeMarketExchangeUrl()+"/${link?.id}"
        println(fullUrl + " "+itemEntity.itemType)
    }


    @Test fun generateCurrency(){
        var list = PoeNinjaLoading.loadCurrencies("Standard", "Currency")
        var link = PoeMarket.sendCurrencyRequest("Standard", list.lines[0].detailsId, list.lines[2].detailsId)
        var fullUrl = URLUtils.generatePoeMarketExchangeUrl()+"/${link?.id}"
        println(fullUrl)
    }
}