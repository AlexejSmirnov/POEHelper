package com.resdev.poehelper


import com.resdev.poehelper.model.retrofit.PoeMarket
import com.resdev.poehelper.model.retrofit.PoeNinjaLoading
import com.resdev.poehelper.model.room.ItemEntity
import com.resdev.poehelper.utils.fromRetrofitItemToRoomEntity
import com.resdev.poehelper.utils.generatePoeMarketExchangeUrl
import com.resdev.poehelper.utils.generatePoeMarketTradeUrl
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test

class PoeMarketTest{
    var league = "Standard"
    var language = "en"

    var types = listOf(
        "DeliriumOrb",
        "Scarab",
        "DivinationCard",
        "SkillGem",
        "BaseType",
        "HelmetEnchant",
        "Map",
        "UniqueJewel",
        "Prophecy",
        "UniqueMap",
        "Beast",
        "Vial")



    @Test fun allTypesGenerating() =
        runBlocking {
            for (i in types){
                launch {
                    val model = PoeNinjaLoading.loadItems(league, i, language)
                    model.bindModel()
                    val itemEntity = fromRetrofitItemToRoomEntity(model.lines[0], i)
                    linkGenerating(itemEntity)
                }
            }
        }





    fun linkGenerating(itemEntity: ItemEntity){
        val link = PoeMarket.sendItemRequest((itemEntity), league)
        val fullUrl = generatePoeMarketTradeUrl(league) +"/${link?.id}"
        println(fullUrl + " "+itemEntity.itemType)
    }


    @Test fun generateCurrency(){
        runBlocking {
            val list = PoeNinjaLoading.loadCurrencies(league, "Currency", language)
            val link = PoeMarket.sendCurrencyRequest(list.lines[0].detailsId, list.lines[2].detailsId, league)
            val fullUrl = generatePoeMarketExchangeUrl(league)+"/${link?.id}"
            println("$fullUrl Currency")
        }
    }
}