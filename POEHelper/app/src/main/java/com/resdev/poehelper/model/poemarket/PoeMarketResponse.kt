package com.resdev.poehelper.model.poemarket
import com.google.gson.annotations.SerializedName


data class PoeMarketResponse(
    @SerializedName("complexity")
    val complexity: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("result")
    val result: List<String>,
    @SerializedName("total")
    val total: Int
)