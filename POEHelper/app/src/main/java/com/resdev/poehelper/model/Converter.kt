package com.resdev.poehelper.model

import android.util.Log
import com.resdev.poehelper.model.pojo.ItemLine
import com.resdev.poehelper.model.room.ExplicitModifier
import com.resdev.poehelper.model.room.ImplicitModifier
import com.resdev.poehelper.model.room.ItemEntity
import com.resdev.poehelper.model.room.Sparkline

object Converter {
    fun fromRetrofitItemToRoomEntity(item: ItemLine, type: String): ItemEntity{
        val translations = item.itemsModel.language.translations
        var sparktext = item.sparkline.data.joinToString {"$it "}
        var newItem = ItemEntity(
            item.id,
            item.name,
            translations[item.name],
            item.baseType,
            translations[item.baseType],
            item.chaosValue,
            item.icon,
            item.flavourText,
            translations[item.flavourText],
            item.prophecyText,
            translations[item.prophecyText],
            item.links,
            item.corrupted,
            item.itemClass,
            item.gemLevel,
            item.gemQuality,
            item.mapTier,
            item.levelRequired,
            item.variant,
            type
        )
        newItem.sparkline = Sparkline(item.id, sparktext.substring(0, sparktext.lastIndex-1), item.sparkline.totalChange)
        newItem.implicitModifiers = item.implicitModifiers.map {ImplicitModifier(item.id, it.text, translations [it.text])}
        newItem.explicitModifiers = item.explicitModifiers.map {ExplicitModifier(item.id, it.text, translations[it.text])}
        return newItem
    }
}