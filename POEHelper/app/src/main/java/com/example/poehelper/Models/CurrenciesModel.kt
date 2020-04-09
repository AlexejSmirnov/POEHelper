package com.example.poehelper.Models
import com.google.gson.annotations.SerializedName


data class CurrenciesModel(
    @SerializedName("currencyDetails")
    var currencyDetails: List<CurrencyDetail>,
    @SerializedName("lines")
    var lines: List<CurrencyLine>
){
    fun getDetails(name: String) : CurrencyDetail?{
        for (i in currencyDetails){
            if (name == i.name){
                return i
            }
        }
        return null
    }
}

data class CurrencyDetail(
    @SerializedName("icon")
    val icon: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)


data class CurrencyLine(
    @SerializedName("currencyTypeName")
    val currencyTypeName: String,
    @SerializedName("chaosEquivalent")
    val chaosEquivalent: Double?,
    @SerializedName("pay")
    val pay: Pay?,
    @SerializedName("receive")
    val receive: Receive?,
    @SerializedName("paySparkLine")
    val paySparkLine: PaySparkLine?,
    @SerializedName("receiveSparkLine")
    val receiveSparkLine: ReceiveSparkLine?
)


data class PaySparkLine(
    @SerializedName("data")
    val `data`: List<Double>,
    @SerializedName("totalChange")
    val totalChange: Double
)

data class ReceiveSparkLine(
    @SerializedName("data")
    val `data`: List<Double>,
    @SerializedName("totalChange")
    val totalChange: Double
)

data class Pay(
    @SerializedName("value")
    val value: Double?

)


data class Receive(
    @SerializedName("value")
    val value: Double?
)