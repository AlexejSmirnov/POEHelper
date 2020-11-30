package com.resdev.poehelper.model.retrofit

import androidx.lifecycle.LiveData
import com.resdev.poehelper.model.poemarket.PoeMarketResponse
import com.resdev.poehelper.model.poemarket.RequestBuilder
import com.resdev.poehelper.model.room.ItemEntity
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//PoeMarketApi retrofit
object PoeMarket {
    private lateinit var retrofit : Retrofit
    private lateinit var poeMarketClient : PoeMarketApi
    init {
        rebuildRetrofit("en")
    }

    fun setLanguageObservable(data: LiveData<String>){
        data.observeForever{
            rebuildRetrofit(it)
        }
    }

    fun sendItemRequest(itemsEntity: ItemEntity, league: String): PoeMarketResponse? {
        val item = RequestBuilder.generateItemLink(itemsEntity)
        return try{
            poeMarketClient.sendItemRequest(league, item)
                .execute()
                .body()
        } catch (e: Exception){
            e.printStackTrace()
            null
        }
    }

    fun sendCurrencyRequest(want: String, have: String, league: String): PoeMarketResponse? {
        val item = RequestBuilder.generateCurrencyLink(want, have)
        return try{ poeMarketClient.sendCurrencyRequest(league, item)
            .execute()
            .body()
        } catch (e: Exception){
            e.printStackTrace()
            null
        }
    }

    fun rebuildRetrofit(language: String){
        var url = when(language){
            "en"-> "https://www.pathofexile.com"
            "ko"-> "https://poe.game.daum.net"
            "ge"-> "https://de.pathofexile.com"
            else-> "https://${language}.pathofexile.com"
        }
        retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        poeMarketClient = retrofit.create(PoeMarketApi::class.java)
    }
}