package com.resdev.poehelper.view.datawrappers

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.resdev.poehelper.CurrentValue
import com.resdev.poehelper.R
import com.resdev.poehelper.model.pojo.ItemLine
import com.squareup.picasso.Picasso

class ItemLineUIWrapper(val itemLine: ItemLine, val context: Context){
    private val translations = itemLine.itemsModel.language.translations

    fun getName(): String{
        return translations[itemLine.name] ?: itemLine.name
    }
    fun getFormattedExplicitModifiers(): String{
        var result = ""
        for (i in itemLine.explicitModifiers){
            result += translations[i.text] ?: i.text
        }
        return result
    }
    fun getFormattedImplicitModifier(): String{
        var result = ""
        for (i in itemLine.implicitModifiers){
            result += translations[i.text] ?: i.text
        }
        return result
    }

    fun getProphecyText(): String{
        return translations[itemLine.prophecyText!!] ?: itemLine.prophecyText
    }

    fun getFlavourText(): String{
        return translations[itemLine.flavourText] ?: itemLine.flavourText
    }

    fun getBaseType(): String{
        return translations[itemLine.baseType!!] ?: itemLine.baseType
    }

    fun getChaosValue():String{
        if (itemLine.chaosValue == null){
            return context.resources.getString(R.string.no_data)
        }
        return "1.0 "+context.resources.getString( R.string.string_for) +
                " %.2f".format(itemLine.chaosValue/(CurrentValue.line.chaosEquivalent?:1.0))
    }

    fun getPercentage(): String{
        return roundDouble(itemLine.sparkline.totalChange)
    }

    fun roundDouble(value:Double):String{
        if (value>=10000){
            return ((value/1000).toInt().toString())+"k%"
        }
        return "$value%"
    }

    fun getCorrupted():Boolean{
        return itemLine.corrupted
    }

    fun getPercentageColor(): Int{
        return if ((itemLine.sparkline.totalChange ?: 0.0)>=0.0) context.getColor(R.color.green) else context.getColor(R.color.red)
    }
    fun getUrl():String{
        if (itemLine.icon==null) return ""
        else if (itemLine.icon.endsWith(".png")){
            return itemLine.icon
        }
        else if (itemLine.variant!=null){
            return (itemLine.icon+("&${itemLine.variant?.toLowerCase()}=1"))
        }
        return itemLine.icon


    }

//    fun getItemModeLvL():String{
//        if (itemLine.itemClass==4)
//    }


    companion object{
        @JvmStatic
        @BindingAdapter("url")
        fun loadImage(imageView: ImageView, url: String){
            if (url.isNotEmpty()){
                Picasso.get().load(url).into(imageView)
            }

        }
    }




}