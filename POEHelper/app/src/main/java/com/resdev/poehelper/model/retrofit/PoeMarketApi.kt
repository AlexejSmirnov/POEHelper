package com.resdev.poehelper.model.retrofit

import com.resdev.poehelper.model.poemarket.PoeMarketCurrencyRequest
import com.resdev.poehelper.model.poemarket.PoeMarketItemRequest
import com.resdev.poehelper.model.poemarket.PoeMarketResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path


interface PoeMarketApi {
    @POST("api/trade/search/{league}")
    fun sendItemRequest(
        @Path("league") league: String,
        @Body request: PoeMarketItemRequest): Call<PoeMarketResponse>

    @POST("api/trade/exchange/{league}")
    fun sendCurrencyRequest(
        @Path("league") league: String,
        @Body request: PoeMarketCurrencyRequest): Call<PoeMarketResponse>

}