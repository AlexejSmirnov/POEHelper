package com.resdev.poehelper.view.datawrappers

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.resdev.poehelper.model.CurrentValue
import com.resdev.poehelper.R
import com.resdev.poehelper.utils.Util.roundPercentages
import com.resdev.poehelper.model.room.ItemEntity
import com.squareup.picasso.Picasso

class ItemEntityUiWrapper(val item: ItemEntity, val context: Context) : ItemUiInterface {

    override fun getName(): String {
        return item.translatedName?:item.name
    }

    override fun getChaosValue(): String {
        if (item.chaosValue == null){
            return context.resources.getString(R.string.no_data)
        }
        return "1.0 "+context.resources.getString( R.string.string_for) +
                " %.2f".format(item.chaosValue!! /(CurrentValue.line.chaosEquivalent?:1.0))
    }

    override fun getPercentage(): String {
        return roundPercentages(item.sparkline.totalChange)
    }

    override fun getQuality(): String {
        return "+"+item.gemQuality.toString()+"%"
    }

    override fun getTier(): String {
        return "tier "+item.mapTier
    }

    override fun getGemLvl(): String {
        return "lvl "+item.gemLevel
    }

    override fun hasGemLvl(): Boolean {
        return item.gemLevel!=0
    }

    override fun hasTier(): Boolean {
        return item.mapTier!=0
    }

    override fun hasQuality(): Boolean {
        return item.gemQuality!=0
    }
    override fun anyApply(): Boolean {
        return hasGemLvl() || hasQuality() || hasTier()
    }
    override fun getCorrupted(): Boolean {
        return item.corrupted
    }

    override fun getPercentageColor(): Int {
        return if ((item.sparkline.totalChange ?: 0.0)>=0.0) context.getColor(R.color.green) else context.getColor(R.color.red)
    }

    override fun getUrl(): String {
        return when {
            item.icon==null -> ""
            item.icon!!.endsWith(".png") -> {
                item.icon!!
            }
            item.variant!=null -> {
                (item.icon+("&${item.variant?.toLowerCase()}=1"))
            }
            else -> item.icon!!
        }
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