package com.resdev.poehelper.model.retrofit

import com.resdev.poehelper.model.pojo.CurrenciesModel
import com.resdev.poehelper.model.pojo.ItemsModel
import retrofit2.http.GET
import retrofit2.http.Query

//Interface contains methods to get list of items or currencies
interface PoeNinjaApi {

    @GET("/api/data/itemoverview")
    suspend fun getItem(
        @Query("league") leagueName: String?,
        @Query("type") type: String,
        @Query("language") language: String
    ): ItemsModel?


    @GET("/api/data/currencyoverview")
    suspend fun getCurrency(
        @Query("league") leagueName: String?,
        @Query("type") type: String,
        @Query("language") language: String
    ): CurrenciesModel?
}