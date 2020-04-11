package com.resdev.poehelper

import android.view.View
import com.resdev.poehelper.Models.CurrencyDetail
import com.resdev.poehelper.Models.ItemLine
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import java.net.InetAddress

object Util {

    fun getFromMap(field : String, map : HashMap<String, String>) : String{
        return map[field] ?: field
    }
    fun getFromMapForEssence(field : String, map : HashMap<String, String>) : String{
        var parts = field.split(": ")
        var one = map[parts[0]] ?: parts[0]
        var two = map[parts[1]] ?: parts[1]
        return "$one: $two"
    }
    fun isInternetAvailable(): Boolean {
        return try {
            val ipAddr: InetAddress = InetAddress.getByName("google.com")
            !ipAddr.equals("")
        } catch (e: Exception) {
            false
        }
    }

    fun getLineGraphSeries(list:List<Double>) : LineGraphSeries<DataPoint> {

        var newList :ArrayList<DataPoint> = ArrayList()
        for (i in list.indices){
            if (list[i] != null){
                newList.add(DataPoint(i.toDouble(), list[i]))
            }
        }
        return LineGraphSeries(newList.toTypedArray())

    }

    fun generateItemTradeUrl(line : ItemLine):String{
        var base = "https://poe.trade/search?"
        base+="league=${Config.league}"
        base+="&name=${line.name}"
        base+="&link_min=${line.links}"
        base+="&level_min=${line.mapTier}"
        base+="&corrupted =${if (line.corrupted) 1 else 0}"
        base+="&rlevel_min=${line.levelRequired}"
        base+="&q_min=${line.gemQuality}"
        println(base)
        return base
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


    fun seriesConfig(series: LineGraphSeries<DataPoint>, view: View){
        series.thickness = 10
        series.color=view.resources.getColor(R.color.graphs_line)
        series.backgroundColor = view.resources.getColor(R.color.graphs_under_line)
        series.isDrawBackground = true
    }
}