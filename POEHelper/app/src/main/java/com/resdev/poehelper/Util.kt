package com.resdev.poehelper

import android.content.Context
import android.graphics.Color
import android.view.View
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

object Util {

    fun getFromMap(field : String, map : HashMap<String, String>) : String{
        return map[field] ?: field
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


    fun isColorLight(color: String): Boolean{
       return Integer.parseInt(color.substring(2, 4), 16)+
                Integer.parseInt(color.substring(4, 6), 16)+
                Integer.parseInt(color.substring(6, 8), 16) > 530
    }



    fun showInternetConnectionError(view: View){
        var snackbar = Snackbar.make(view, "Connection error!", Snackbar.LENGTH_LONG)
        snackbar.view.setBackgroundColor(view.context.getColor(R.color.lightGray))
        snackbar.setActionTextColor(Color.BLACK)
        snackbar.show()
    }

    fun getDaysSet():ArrayList<String>{
        var cal = Calendar.getInstance()
        var list = arrayListOf<String>(SimpleDateFormat("dd.MM").format(cal.time))
        for (i in 0..5){
            cal.add(Calendar.DATE, -1)
            list.add(SimpleDateFormat("dd.MM").format(cal.time))
        }
        list.reverse()
        return list
    }

    fun getGraphDataset(list:List<Double?>, currentPrice: Double, buy: Boolean, context: Context) : LineDataSet? {
        if (list.isEmpty()){
            return LineDataSet(ArrayList<Entry>(), "")
        }
        var startValue = currentPrice /(1 + ((list.last() ?: 0.0)/100))
        val newList :ArrayList<Entry> = ArrayList()
        for (i in list.indices){
            if (list[i] != null){
                newList.add(Entry(i.toFloat(), (startValue * (1+list[i]!!/100)).toFloat()))
            }
        }
        var dataset = LineDataSet(newList, "")
        dataset.valueTextColor = Color.WHITE
        dataset.setDrawFilled(true)
        if (buy){
            dataset.color = context.getColor(R.color.graphs_line)
            dataset.highLightColor = context.getColor(R.color.graphs_line)
            dataset.fillColor = context.getColor(R.color.graphs_under_line_blue)
            dataset.setCircleColor(context.getColor(R.color.graphs_line))
        }
        else{
            dataset.color = context.getColor(R.color.graphs_yellow)
            dataset.highLightColor = context.getColor(R.color.graphs_yellow)
            dataset.fillColor = context.getColor(R.color.graphs_under_line_yellow)
            dataset.setCircleColor(context.getColor(R.color.graphs_yellow))
        }
        dataset.mode = LineDataSet.Mode.CUBIC_BEZIER;
        return dataset
    }

    fun setupGraph(graph: LineChart){
        graph.setNoDataText(graph.context.resources.getString(R.string.no_data))
        graph.setNoDataTextColor(Color.WHITE)
        graph.axisLeft.xOffset = 10F
        graph.axisRight.setDrawGridLines(false)
        graph.axisLeft.setDrawGridLines(false)
        graph.xAxis.setDrawGridLines(false)
        graph.axisLeft.textColor = Color.WHITE
        graph.xAxis.textColor = Color.WHITE
        graph.xAxis.position = XAxis.XAxisPosition.BOTTOM
        graph.legend.textColor = Color.WHITE
        graph.legend.isEnabled = false
        graph.extraBottomOffset = 10F
        graph.setScaleEnabled(false)
        graph.description.isEnabled = false
        graph.xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return getDaysSet()[value.toInt()]
            }
        }
    }

    fun roundPercentages(value: Double): String {
        if (value>=10000){
            return ((value/1000).toInt().toString())+"k%"
        }
        return "$value%"
    }

}