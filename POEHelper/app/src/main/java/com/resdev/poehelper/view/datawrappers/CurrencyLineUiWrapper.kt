package com.resdev.poehelper.view.datawrappers

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.resdev.poehelper.model.CurrentValue
import com.resdev.poehelper.R
import com.resdev.poehelper.model.pojo.CurrencyLine
import com.squareup.picasso.Picasso

class CurrencyLineUiWrapper(val currencyLine: CurrencyLine, val context: Context) {
    var translations = currencyLine.model.language.translations
    fun getName(): String{
        if (currencyLine.currencyTypeName == CurrentValue.line.currencyTypeName){
            return  translations["Chaos Orb"] ?: "Chaos Orb"
        }
        return translations[currencyLine.currencyTypeName] ?: currencyLine.currencyTypeName
    }


    fun getPayValue(): String{
        if (currencyLine.currencyTypeName == CurrentValue.line.currencyTypeName){
            return "${formatValue(currencyLine.receive?.value)} " + context.resources.getString(R.string.sell)
        }
        return " ${formatBuyValueRatio(currencyLine.pay?.value)} "+context.resources.getString(R.string.sell)
    }

    fun getReceiveValue():String{
        if (currencyLine.currencyTypeName == CurrentValue.line.currencyTypeName){
            return context.resources.getString(R.string.buy)+" ${formatValue(currencyLine.pay?.value)}"
        }
        return context.resources.getString(R.string.buy)+" ${formatSellValueRatio(currencyLine.receive?.value)}"
    }

    fun getUrl():String{
        return currencyLine.model.getDetails(currencyLine.currencyTypeName)!!.icon
    }
    companion object{
        @BindingAdapter( "curr_url")
        @JvmStatic
        fun loadCurrencyImage(imageView: ImageView,  url: String){
            if (url == CurrentValue.currencyDetail.icon){

                Picasso.get().load("https://web.poecdn.com/image/Art/2DItems/Currency/CurrencyRerollRare.png?scale=1&w=1&h=1").into(imageView)
            }
            else{
                Picasso.get().load(url).into(imageView)
            }

        }
    }



    private fun formatBuyValueRatio(value:Double?):String{
        if (value == null){
            return context.resources.getString(R.string.no_data)
        }
        return "%.2f".format(value* (CurrentValue.line.chaosEquivalent?:1.0))
    }
    private fun formatSellValueRatio(value:Double?):String{
        if (value == null){
            return context.resources.getString(R.string.no_data)
        }
        return "%.2f".format(value/ (CurrentValue.line.chaosEquivalent?:1.0))
    }

    private fun formatValue(value:Double?):String{
        if (value == null){
            return context.resources.getString(R.string.no_data)
        }
        return "%.2f".format(value)
    }
}