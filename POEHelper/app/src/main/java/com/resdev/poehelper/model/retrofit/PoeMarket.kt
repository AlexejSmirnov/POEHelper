package com.resdev.poehelper.model.retrofit

import com.resdev.poehelper.Config
import com.resdev.poehelper.model.poemarket.PoeMarketResponse
import com.resdev.poehelper.model.poemarket.RequestBuilder
import com.resdev.poehelper.model.room.ItemEntity
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

object PoeMarket {
    private lateinit var retrofit : Retrofit

    private lateinit var poeMarketClient : PoeMarketApi


    fun sendItemRequest(leagueName: String, itemsEntity: ItemEntity): PoeMarketResponse? {
        val item = RequestBuilder.generateItemLink(itemsEntity)
        return try{
            poeMarketClient.sendItemRequest(Config.league, item)
                ?.execute()
                ?.body()!!
        } catch (e: Exception){
            null
        }



    }

    fun sendCurrencyRequest(leagueName: String, want: String, have: String): PoeMarketResponse? {
        val item = RequestBuilder.generateCurrencyLink(want, have)
        return try{ poeMarketClient.sendCurrencyRequest(Config.league, item)
            ?.execute()
            ?.body()!!
        } catch (e: Exception){
            null
        }
    }

    fun rebuildRetrofit(){
        var url = when(Config.language){
            "en"-> "https://www.pathofexile.com"
            "ko"-> "https://poe.game.daum.net"
            "ge"-> "https://de.pathofexile.com"
            else-> "https://${Config.language}.pathofexile.com"
        }
        retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        poeMarketClient = retrofit.create(PoeMarketApi::class.java)
    }
}