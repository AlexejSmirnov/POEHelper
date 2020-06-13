package com.resdev.poehelper

import com.resdev.poehelper.model.Converter
import com.resdev.poehelper.model.poemarket.RequestBuilder
import com.resdev.poehelper.model.retrofit.PoeMarket
import com.resdev.poehelper.model.retrofit.PoeNinjaLoading
import com.resdev.poehelper.model.room.ItemEntity
import org.junit.Test

class PoeMarketTest{
    init {
        val model = PoeNinjaLoading.loadItems("Standard", "Scarab")
        model.bindModel()
        PoeMarket.rebuildRetrofit()
    }


    @Test fun defaultUsage(){
        //var response = PoeMarket.sendRequest("Standard", itemEntity)
        //assert(response.total>0)
    }

    @Test fun allTypesGenerating(){
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
        var fullUrl = Util.generatePoeMarketExchangeUrl()+"/${link?.id}"
        println(fullUrl + " "+itemEntity.itemType)
    }


    @Test fun generateCurrency(){
        var list = PoeNinjaLoading.loadCurrencies("Standard", "Currency")
        var link = PoeMarket.sendCurrencyRequest("Standard", list.lines[0].detailsId, list.lines[2].detailsId)
        var fullUrl = Util.generatePoeMarketExchangeUrl()+"/${link?.id}"
        println(fullUrl)
    }
}