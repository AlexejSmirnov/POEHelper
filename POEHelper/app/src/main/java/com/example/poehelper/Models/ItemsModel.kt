package com.example.poehelper.Models
import com.google.gson.annotations.SerializedName


data class ItemsModel(
    @SerializedName("lines")
    var lines: List<ItemLine>
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
    val links:Int

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
