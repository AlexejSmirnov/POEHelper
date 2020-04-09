package com.example.poehelper

import com.example.poehelper.Models.CurrenciesModel
import com.example.poehelper.Models.ItemsModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface PoeNinjaApi {

    @GET("/api/data/itemoverview")
    fun getItem(
        @Query("league") leagueName: String?,
        @Query("type") type: String
    ): Call<ItemsModel?>?


    @GET("/api/data/currencyoverview")
    fun getCurrency(
        @Query("league") leagueName: String?,
        @Query("type") type: String
    ): Call<CurrenciesModel?>?
}