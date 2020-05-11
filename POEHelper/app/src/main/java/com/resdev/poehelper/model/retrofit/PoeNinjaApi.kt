package com.resdev.poehelper.model.retrofit

import com.resdev.poehelper.model.pojo.CurrenciesModel
import com.resdev.poehelper.model.pojo.ItemsModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface PoeNinjaApi {

    @GET("/api/data/itemoverview")
    fun getItem(
        @Query("league") leagueName: String?,
        @Query("type") type: String,
        @Query("language") language: String
    ): Call<ItemsModel?>?


    @GET("/api/data/currencyoverview")
    fun getCurrency(
        @Query("league") leagueName: String?,
        @Query("type") type: String,
        @Query("language") language: String
    ): Call<CurrenciesModel?>?
}