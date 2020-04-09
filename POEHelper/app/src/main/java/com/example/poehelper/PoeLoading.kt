package com.example.poehelper

import com.example.poehelper.Models.CurrenciesModel
import com.example.poehelper.Models.ItemsModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PoeLoading {
    private  var retrofit : Retrofit = Retrofit.Builder()
        .baseUrl("https://poe.ninja")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val poeNinjaClient = retrofit.create(PoeNinjaApi::class.java)


    fun loadItems(leagueName: String, itemName:String):ItemsModel{
        return poeNinjaClient
            .getItem(leagueName, itemName)
            ?.execute()
            ?.body()!!
    }


    fun loadCurrencies(leagueName: String, itemName:String):CurrenciesModel{
        return poeNinjaClient
            .getCurrency(leagueName, itemName)
            ?.execute()
            ?.body()!!

    }
}

