package com.resdev.poehelper

import android.graphics.Color
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.resdev.poehelper.model.pojo.CurrencyDetail
import com.resdev.poehelper.model.pojo.ItemLine
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import com.resdev.poehelper.model.room.ItemEntity
import kotlinx.android.synthetic.main.activity_main.*
import java.net.InetAddress

object Util {

    fun getFromMap(field : String, map : HashMap<String, String>) : String{
        return map[field] ?: field
    }
    fun getFromMapForEssence(field : String, map : HashMap<String, String>) : String{
        var parts = field.split(": ")
        var ret = ""
        for (i in parts){
            ret+=(map[i] ?: i)+": "
        }
        return ret.substring(0, ret.length-2)
    }
    fun isInternetAvailable(): Boolean {
        return try {
            val ipAddr: InetAddress = InetAddress.getByName("google.com")
            !ipAddr.equals("")
        } catch (e: Exception) {
            false
        }
    }



    fun getLineGraphSeries(list:List<Double?>) : LineGraphSeries<DataPoint> {

        var newList :ArrayList<DataPoint> = ArrayList()
        for (i in list.indices){
            if (list[i] != null){
                newList.add(DataPoint(i.toDouble(), list[i]!!))
            }
        }
        return LineGraphSeries(newList.toTypedArray())

    }

    fun generatePoeTradeItemTradeUrl(line : ItemLine):String{
        var base = "https://poe.trade/search?"
        base+="league=${Config.league}"
        base+="&name=${line.name}"
        base+="&link_min=${line.links}"
        base+="&level_min=${if (line.baseType?.contains("Essence") == true) 0 else line.mapTier}"
        base+="&corrupted =${if (line.corrupted) 1 else 0}"
        base+="&rlevel_min=${line.levelRequired}"
        base+="&q_min=${line.gemQuality}"
        return base
    }

    fun generatePoeTradeItemTradeUrl(line: ItemEntity): String{
        var base = "https://poe.trade/search?"
        base+="league=${Config.league}"
        base+="&name=${line.name}"
        base+="&link_min=${line.links}"
        base+="&level_min=${if (line.baseType?.contains("Essence") == true) 0 else line.mapTier}"
        base+="&corrupted =${if (line.corrupted) 1 else 0}"
        base+="&rlevel_min=${line.levelRequired}"
        base+="&q_min=${line.gemQuality}"
        return base
    }

    fun generatePoeMarketTradeUrl(): String{
        return when(Config.language){
            "en"->"https://www.pathofexile.com/trade/search/"+Config.league
            "ko"->"https://poe.game.daum.net/trade/search/"+Config.league
            "ge"->"https://de.pathofexile.com/trade/search/"+Config.league
            else->"https://${Config.language}.pathofexile.com/trade/search/"+Config.league
        }
    }

    fun generatePoeMarketExchangeUrl(): String{
        return when(Config.language){
            "en"->"https://www.pathofexile.com/trade/exchange/"+Config.league
            "ko"->"https://poe.game.daum.net/trade/exchange/"+Config.league
            "ge"->"https://de.pathofexile.com/trade/exchange/"+Config.league
            else->"https://${Config.language}.pathofexile.com/trade/exchange/"+Config.league
        }
    }

    fun generateCurrencyTradeUrl(detail : CurrencyDetail):String{
        if (detail.poeTradeId==-1){
            return "https://poe.trade/search?league=${Config.league}&name=${detail.name}"
        }
        var base = "https://currency.poe.trade/search?league=${Config.league}&online=x&stock=&"
        base += if (detail.poeTradeId!=CurrentValue.currencyDetail.poeTradeId) "have=${detail.poeTradeId}" else "have=4"
        base+="&want=${CurrentValue.currencyDetail.poeTradeId}"
        println(base)
        return base
    }

    fun isColorLight(color: String): Boolean{
       return Integer.parseInt(color.substring(2, 4), 16)+
                Integer.parseInt(color.substring(4, 6), 16)+
                Integer.parseInt(color.substring(6, 8), 16) > 530
    }


    fun seriesConfig(series: LineGraphSeries<DataPoint>, view: View){
        series.thickness = 10
        series.color=view.resources.getColor(R.color.graphs_line)
        series.backgroundColor = view.resources.getColor(R.color.graphs_under_line)
        series.isDrawBackground = true
    }

    fun showInternetConnectionError(view: View){
        var snackbar = Snackbar.make(view, "Connection error!", Snackbar.LENGTH_LONG)
        snackbar.view.setBackgroundColor(view.context.getColor(R.color.lightGray))
        snackbar.setActionTextColor(Color.BLACK)
        snackbar.show()
    }
}