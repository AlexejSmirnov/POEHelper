package com.resdev.poehelper.model.pojo
import com.google.gson.annotations.SerializedName


data class CurrenciesModel(
    @SerializedName("currencyDetails")
    var currencyDetails: List<CurrencyDetail>,
    @SerializedName("lines")
    var lines: List<CurrencyLine>,
    @SerializedName("language")
    var language: CurrencyLanguage
){
    init {
        for (i in lines){
            i.model = this
        }
    }
    fun getDetails(name: String) : CurrencyDetail?{
        for (i in currencyDetails){
            if (name == i.name){
                return i
            }
        }
        return null
    }

    companion object {
        val emptyModel =  CurrenciesModel(listOf(), listOf(), CurrencyLanguage("en", HashMap()))
    }




}

data class CurrencyDetail(
    @SerializedName("icon")
    val icon: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("poeTradeId")
    val poeTradeId: Int,
    @SerializedName("tradeId")
    val tradeId: String

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
    val receiveSparkLine: ReceiveSparkLine?,
    @SerializedName("detailsId")
    val detailsId: String
){
    lateinit var model: CurrenciesModel

    fun getTranslatedName():String{
        return model.language.translations[currencyTypeName] ?: currencyTypeName
    }
}


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

data class CurrencyLanguage(
    @SerializedName("name")
    val name: String,
    @SerializedName("translations")
    val translations: HashMap<String, String>
)