package com.resdev.poehelper.model.room

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class ItemEntity(
    @PrimaryKey val id:Int,
    val name: String,
    val translatedName: String?,
    val baseType: String?,
    val translatedType: String?,
    val chaosValue: Double?,
    val icon: String?,
    val flavourText: String,
    val translatedFlavourText: String?,
    @Relation(
        parentColumn = "id",
        entityColumn = "itemId"
    )
    val explicitModifiers: List<ExplicitModifier>,
    @Relation(
        parentColumn = "id",
        entityColumn = "itemId"
    )
    val implicitModifiers: List<ImplicitModifier>,
    val prophecyText: String?,
    val translatedProphecyText: String?,
    val links:Int,
    val corrupted:Boolean,
    val itemClass:Int,
    val gemLevel:Int,
    val gemQuality:Int,
    val mapTier:Int,
    val levelRequired:Int,
    val variant:String?,
    @Relation(
        parentColumn = "id",
        entityColumn = "itemId"
    )
    val sparkline: Sparkline,
    val itemType: String
)

@Entity
data class ExplicitModifier(
    val itemId:Int,
    val text: String,
    val translated: String
)

@Entity
data class ImplicitModifier(
    val itemId:Int,
    val text: String,
    val translated: String
)

@Entity
data class Sparkline(
    val itemId:Int,
    val numbers: String,
    val totalChange: Double
){
    fun getData():List<Double>{
        return numbers.split(" ").map { it.toDouble() }
    }
}