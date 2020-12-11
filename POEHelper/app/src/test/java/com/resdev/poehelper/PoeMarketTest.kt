package com.resdev.poehelper


import androidx.test.runner.AndroidJUnit4
import com.resdev.poehelper.di.DaggerTestComponent
import com.resdev.poehelper.model.Config
import com.resdev.poehelper.model.retrofit.PoeMarket
import com.resdev.poehelper.model.retrofit.PoeNinjaLoading
import com.resdev.poehelper.model.room.ItemEntity
import com.resdev.poehelper.utils.fromRetrofitItemToRoomEntity
import com.resdev.poehelper.utils.generatePoeMarketExchangeUrl
import com.resdev.poehelper.utils.generatePoeMarketTradeUrl
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class PoeMarketTest{
    @Inject lateinit var poeMarket: PoeMarket
    @Inject lateinit var poeNinjaLoading: PoeNinjaLoading
    @Inject lateinit var config: Config
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
    init {
        DaggerTestComponent.create().inject(this)
    }

    fun linkGenerating(itemEntity: ItemEntity){
        val link = poeMarket.sendItemRequest((itemEntity), config.getLeague())
        val fullUrl = generatePoeMarketTradeUrl(config.getLeague(), config.getLanguage()) +"/${link?.id}"
        println(fullUrl + " "+itemEntity.itemType)
    }


    @Test fun allTypesGenerating() =
        runBlocking {
            for (i in types){
                launch {
                    val model = poeNinjaLoading.loadItems(config.getLeague(), i, config.getLanguage())
                    model.bindModel()
                    val itemEntity = fromRetrofitItemToRoomEntity(model.lines[0], i)
                    linkGenerating(itemEntity)
                }
            }
        }

    @Test fun generateCurrency(){
        runBlocking {
            val list = poeNinjaLoading.loadCurrencies(config.getLeague(), "Currency", config.getLanguage())
            val link = poeMarket.sendCurrencyRequest(list.lines[0].detailsId, list.lines[2].detailsId, config.getLeague())
            val fullUrl = generatePoeMarketExchangeUrl(config.getLeague(), config.getLanguage())+"/${link?.id}"
            println("$fullUrl Currency")
        }
    }
}