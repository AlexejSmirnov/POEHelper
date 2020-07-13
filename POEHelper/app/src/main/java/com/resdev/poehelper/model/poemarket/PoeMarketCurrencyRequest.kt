package com.resdev.poehelper.model.poemarket
import com.google.gson.annotations.SerializedName

//the class is responsible for generating a currency exchange request
data class PoeMarketCurrencyRequest(
    @SerializedName("exchange")
    val exchange: Exchange
)

data class Exchange(
    @SerializedName("have")
    val have: List<String>,
    @SerializedName("status")
    val statusx: Statusx,
    @SerializedName("want")
    val want: List<String>
)

data class Statusx(
    @SerializedName("option")
    val option: String
)