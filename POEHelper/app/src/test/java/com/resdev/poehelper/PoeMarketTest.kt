package com.resdev.poehelper


import com.resdev.poehelper.utils.fromRetrofitItemToRoomEntity
import com.resdev.poehelper.model.retrofit.PoeMarket
import com.resdev.poehelper.model.retrofit.PoeNinjaLoading
import com.resdev.poehelper.model.room.ItemEntity
import com.resdev.poehelper.utils.generatePoeMarketExchangeUrl
import kotlinx.coroutines.runBlocking
import org.junit.Test

class PoeMarketTest{
    var league = "Standard"
    var language = "en"

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



    @Test fun allTypesGenerating(){
        runBlocking {
            for (i in types){
                if (i.contains("")){
                    val model = PoeNinjaLoading.loadItems(league, i, language)
                    model.bindModel()
                    val itemEntity =
                        fromRetrofitItemToRoomEntity(
                            model.lines[0],
                            i
                        )
                    linkGenerating(itemEntity)
                }

            }
        }

    }



    fun linkGenerating(itemEntity: ItemEntity){
        var link = PoeMarket.sendItemRequest(
            (itemEntity), league)
        var fullUrl = generatePoeMarketExchangeUrl(league)+"/${link?.id}"
        println(fullUrl + " "+itemEntity.itemType)
    }


    @Test fun generateCurrency(){
        runBlocking {
            var list = PoeNinjaLoading.loadCurrencies(league, "Currency", language)
            var link = PoeMarket.sendCurrencyRequest(list.lines[0].detailsId, list.lines[2].detailsId, league)
            var fullUrl = generatePoeMarketExchangeUrl(league)+"/${link?.id}"
            println(fullUrl)
        }
    }
}