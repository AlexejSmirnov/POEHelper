package com.resdev.poehelper.view.datawrappers

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.resdev.poehelper.CurrentValue
import com.resdev.poehelper.R
import com.resdev.poehelper.Util.roundPercentages
import com.resdev.poehelper.model.pojo.ItemLine
import com.squareup.picasso.Picasso

class ItemLineUIWrapper(val itemLine: ItemLine, val context: Context) : ItemUiInterface{
    private val translations = itemLine.itemsModel.language.translations

    override fun getName(): String{
        return translations[itemLine.name] ?: itemLine.name
    }

    override fun getChaosValue():String{
        if (itemLine.chaosValue == null){
            return context.resources.getString(R.string.no_data)
        }
        return "1.0 "+context.resources.getString( R.string.string_for) +
                " %.2f".format(itemLine.chaosValue/(CurrentValue.line.chaosEquivalent?:1.0))
    }

    override fun getPercentage(): String{
        return roundPercentages(itemLine.sparkline.totalChange)
    }

    override fun getTier(): String {
        return "tier "+itemLine.mapTier
    }

    override fun getGemLvl(): String {
        return "lvl "+itemLine.gemLevel
    }

    override fun hasGemLvl(): Boolean {
        return itemLine.gemLevel!=0
    }

    override fun hasTier(): Boolean {
        return itemLine.mapTier!=0
    }

    override fun getQuality(): String {
        return "+"+itemLine.gemQuality+"%"
    }

    override fun hasQuality(): Boolean {
        return itemLine.gemQuality!=0
    }

    override fun anyApply(): Boolean {
        return hasGemLvl() || hasQuality() || hasTier()
    }

    override fun getCorrupted():Boolean{
        return itemLine.corrupted
    }

    override fun getPercentageColor(): Int{
        return if ((itemLine.sparkline.totalChange ?: 0.0)>=0.0) context.getColor(R.color.green) else context.getColor(R.color.red)
    }
    override fun getUrl():String{
        if (itemLine.icon==null) return ""
        else if (itemLine.icon.endsWith(".png")){
            return itemLine.icon
        }
        else if (itemLine.variant!=null){
            return (itemLine.icon+("&${itemLine.variant?.toLowerCase()}=1"))
        }
        return itemLine.icon


    }

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