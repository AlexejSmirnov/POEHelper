package com.resdev.poehelper.model.room

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

//Entity which contains all data about stored item
@Entity
data class ItemEntity(
    @PrimaryKey var id:Int,
    var name: String,
    var translatedName: String?,
    var baseType: String?,
    var translatedType: String?,
    var chaosValue: Double?,
    var icon: String?,
    var flavourText: String?,
    var translatedFlavourText: String?,
    var prophecyText: String?,
    var translatedProphecyText: String?,
    var links:Int,
    var corrupted:Boolean,
    var itemClass:Int,
    var gemLevel:Int,
    var gemQuality:Int,
    var mapTier:Int,
    var levelRequired:Int,
    var variant:String?,
    var itemType: String,
    @Embedded var sparkline: Sparkline
){
    @Ignore lateinit var explicitModifiers: List<ExplicitModifier>
    @Ignore lateinit var implicitModifiers: List<ImplicitModifier>
}

@Entity
data class ExplicitModifier(
    val itemId:Int,
    @PrimaryKey val text: String,
    val translated: String?
)

@Entity
data class ImplicitModifier(
    val itemId:Int,
    @PrimaryKey val text: String,
    val translated: String?
)


data class Sparkline(
    val numbers: String,
    val totalChange: Double
){
    fun getData():List<Double?>{
        return numbers.split(" , ").map {
            try{
                it.toDouble()
            }
            catch (e: NumberFormatException){
                null
            }
        }
    }
}