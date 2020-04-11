package com.resdev.poehelper.Models
import com.google.gson.annotations.SerializedName


data class ItemsModel(
    @SerializedName("lines")
    var lines: List<ItemLine>,
    @SerializedName("language")
    var language: Language

)

data class ItemLine(
    @SerializedName("name")
    val name: String,
    @SerializedName("baseType")
    val baseType: String?,
    @SerializedName("chaosValue")
    val chaosValue: Double?,
    @SerializedName("icon")
    val icon: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("flavourText")
    val flavourText: String,
    @SerializedName("explicitModifiers")
    val explicitModifiers: List<ExplicitModifier>,
    @SerializedName("implicitModifiers")
    val implicitModifiers: List<ImplicitModifier>,
    @SerializedName("prophecyText")
    val prophecyText: Any?,
    @SerializedName("links")
    val links:Int,
    @SerializedName("corrupted")
    val corrupted:Boolean,
    @SerializedName("gemLevel")
    val gemLevel:Int,
    @SerializedName("gemQuality")
    val gemQuality:Int,
    @SerializedName("mapTier")
    val mapTier:Int,
    @SerializedName("levelRequired")
    val levelRequired:Int,
    @SerializedName("variant")
    val variant:String,
    @SerializedName("lowConfidenceSparkline")
    val sparkline: Sparkline

)

data class ExplicitModifier(
    @SerializedName("optional")
    val optional: Boolean,
    @SerializedName("text")
    val text: String
)

data class ImplicitModifier(
    @SerializedName("optional")
    val optional: Boolean,
    @SerializedName("text")
    val text: String
)
data class Sparkline(
    @SerializedName("data")
    val `data`: List<Double>,
    @SerializedName("totalChange")
    val totalChange: Double
)

data class Language(
    @SerializedName("name")
    val name: String,
    @SerializedName("translations")
    val translations: HashMap<String, String>
)
