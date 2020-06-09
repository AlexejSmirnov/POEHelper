package com.resdev.poehelper.view.datawrappers

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.resdev.poehelper.CurrentValue
import com.resdev.poehelper.R
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
        return roundDouble(item.sparkline.totalChange)
    }

    override fun roundDouble(value: Double): String {
        if (value>=10000){
            return ((value/1000).toInt().toString())+"k%"
        }
        return "$value%"
    }

    override fun getCorrupted(): Boolean {
        return item.corrupted
    }

    override fun getPercentageColor(): Int {
        return if ((item.sparkline.totalChange ?: 0.0)>=0.0) context.getColor(R.color.green) else context.getColor(R.color.red)
    }

    override fun getUrl(): String {
        if (item.icon==null) return ""
        else if (item.icon!!.endsWith(".png")){
            return item.icon!!
        }
        else if (item.variant!=null){
            return (item.icon+("&${item.variant?.toLowerCase()}=1"))
        }
        return item.icon!!
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