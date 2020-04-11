package com.resdev.poehelper

import com.resdev.poehelper.Models.CurrenciesModel
import com.resdev.poehelper.Models.ItemsModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PoeNinjaLoading {
    private  var retrofit : Retrofit = Retrofit.Builder()
        .baseUrl("https://poe.ninja")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val poeNinjaClient = retrofit.create(PoeNinjaApi::class.java)


    fun loadItems(leagueName: String, itemName:String):ItemsModel{
        return poeNinjaClient
            .getItem(leagueName, itemName, Config.language)
            ?.execute()
            ?.body()!!
    }


    fun loadCurrencies(leagueName: String, itemName:String):CurrenciesModel{
        return poeNinjaClient
            .getCurrency(leagueName, itemName,Config.language)
            ?.execute()
            ?.body()!!

    }
}

