package com.example.poehelper.Adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.poehelper.CurrentValue
import com.example.poehelper.Models.CurrencyDetail
import com.example.poehelper.Models.CurrencyLine
import com.example.poehelper.R
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.GridLabelRenderer
import com.squareup.picasso.Picasso

object PopupCurrencyWindowSetuper {

    fun setupWindow(currencyLine: CurrencyLine, currencyDetail: CurrencyDetail, view: View){
        view.findViewById<TextView>(R.id.currency_buy_percents).text =
            (currencyLine.receiveSparkLine?.totalChange?.toString() ?: "0")+"%"
        if ((currencyLine.receiveSparkLine?.totalChange ?: 0.0)>=0.0){
            view.findViewById<TextView>(R.id.currency_buy_percents).setTextColor(view.resources.getColor(R.color.green))
        }
        else{
            view.findViewById<TextView>(R.id.currency_buy_percents).setTextColor(view.resources.getColor(R.color.red))
        }

        view.findViewById<TextView>(R.id.currency_sell_percentes).text =
            (currencyLine.paySparkLine?.totalChange?.toString() ?: "0")+"%"
        if ((currencyLine.paySparkLine?.totalChange ?: 0.0)>=0.0){
            view.findViewById<TextView>(R.id.currency_sell_percentes).setTextColor(view.resources.getColor(R.color.green))
        }
        else{
            view.findViewById<TextView>(R.id.currency_sell_percentes).setTextColor(view.resources.getColor(R.color.red))
        }

        Picasso.get().load(currencyDetail?.icon).into(view.findViewById<ImageView>(R.id.showing_currency_image_1))
        Picasso.get().load(currencyDetail?.icon).into(view.findViewById<ImageView>(R.id.showing_currency_image_2))
        Picasso.get().load(CurrentValue.currencyDetail?.icon).into(view.findViewById<ImageView>(R.id.exchanging_currency_image_1))
        Picasso.get().load(CurrentValue.currencyDetail?.icon).into(view.findViewById<ImageView>(R.id.exchanging_currency_image_2))

        val buyingGraph = view.findViewById<GraphView>(R.id.buying_graph)
        buyingGraph.addSeries(getLineGraphSeries(currencyLine.receiveSparkLine!!.data))
        buyingGraph.gridLabelRenderer.isVerticalLabelsVisible = false
        buyingGraph.gridLabelRenderer.isHorizontalLabelsVisible = false
        buyingGraph.gridLabelRenderer.gridStyle = GridLabelRenderer.GridStyle.NONE


        val sellingGraph = view.findViewById<GraphView>(R.id.selling_graph)
        sellingGraph.addSeries(getLineGraphSeries(currencyLine.paySparkLine!!.data))
        sellingGraph.gridLabelRenderer.isVerticalLabelsVisible = false
        sellingGraph.gridLabelRenderer.isHorizontalLabelsVisible = false
        sellingGraph.gridLabelRenderer.gridStyle = GridLabelRenderer.GridStyle.NONE

        view.findViewById<TextView>(R.id.currency_buy_title).text = buyFormatValue(currencyLine.receive?.value)
        view.findViewById<TextView>(R.id.currency_sell_title).text = sellFormatValue(currencyLine.pay?.value)


    }
    private fun buyFormatValue(value:Double?):String{

        return when (value) {
            null -> {
                "no data"
            }
            else -> {
                val actualValue = value/CurrentValue.line.chaosEquivalent!!
                if (actualValue>=1){
                    return "1.0 for " + "%.1f".format(actualValue)
                }
                else{
                    "%.1f".format(1.0/actualValue)+" for 1.0"
                }
            }
        }
    }
    private fun sellFormatValue(value:Double?):String{

        return when (value) {
            null -> {
                "no data"
            }
            else -> {
                val actualValue = value*CurrentValue.line.chaosEquivalent!!
                if (actualValue>=1){
                    return "%.1f".format(actualValue)+" for 1.0"
                }
                else{
                    return "1.0 for " + "%.1f".format(1.0/actualValue)

                }
            }
        }
    }
//    private fun sellFormatValue(value:Double?):String{
//        return when (value) {
//            null -> {
//                "no data"
//            }
//            else -> {
//                var actualValue = value / CurrentValue.line.chaosEquivalent!!
//                return if (actualValue>=1){
//                    "1.0 for "+ "%.1f".format(actualValue)
//                } else{
//                    "%.1f".format(1/actualValue)+" for 1.0"
//                }
//            }
//        }
//    }




}