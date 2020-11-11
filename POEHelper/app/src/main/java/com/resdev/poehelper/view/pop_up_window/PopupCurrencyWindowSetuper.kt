package com.resdev.poehelper.view.pop_up_window

import android.content.res.Resources
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.LineData
import com.resdev.poehelper.model.CurrentValue
import com.resdev.poehelper.model.pojo.CurrencyLine
import com.resdev.poehelper.R
import com.resdev.poehelper.utils.ChartsUtil.getGraphDataset
import com.resdev.poehelper.utils.ChartsUtil.setupGraph
import com.resdev.poehelper.utils.Util
import com.squareup.picasso.Picasso

object PopupCurrencyWindowSetuper {

    fun setupWindow(currencyLine: CurrencyLine,  view: View){

        var currencyDetail = currencyLine.model.getDetails(currencyLine.currencyTypeName)
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
        Picasso.get().load(CurrentValue.getDetails().icon).into(view.findViewById<ImageView>(R.id.exchanging_currency_image_1))
        Picasso.get().load(CurrentValue.getDetails().icon).into(view.findViewById<ImageView>(R.id.exchanging_currency_image_2))



        val buyingGraph = view.findViewById<LineChart>(R.id.buying_graph)
        setupGraph(buyingGraph)
        var receiveValue =  (currencyLine.receive?.value ?: 1.0)/ CurrentValue.getLine().chaosEquivalent!!
        buyingGraph.data = LineData(
            getGraphDataset(currencyLine.receiveSparkLine!!.data,
            if (receiveValue>=1.0) {
                receiveValue
            }
            else {
                (1.0/receiveValue)
            },
            true, view.context))


        val sellingGraph  =view.findViewById<LineChart>(R.id.selling_graph)
        setupGraph(sellingGraph)
        var payValue = (currencyLine.pay?.value ?: 1.0)* CurrentValue.getLine().chaosEquivalent!!
        sellingGraph.data = LineData(
            getGraphDataset(currencyLine.paySparkLine!!.data, if (payValue >=1.0) {
                    payValue
                }
                else {
                    (1.0/payValue)
                },  false, view.context)
        )
        view.findViewById<TextView>(R.id.currency_buy_title).text =
            buyFormatValue(
                currencyLine.receive?.value,
                view.resources
            )
        view.findViewById<TextView>(R.id.currency_sell_title).text =
            sellFormatValue(
                currencyLine.pay?.value,
                view.resources
            )


    }
    private fun buyFormatValue(value:Double?, resources : Resources):String{

        return when (value) {
            null -> {
                resources.getString(R.string.no_data)
            }
            else -> {
                val actualValue = value/ CurrentValue.getLine().chaosEquivalent!!
                if (actualValue>=1){
                    return "1.0 ${resources.getString(R.string.string_for)} " + "%.1f".format(actualValue)
                }
                else{
                    "%.1f".format(1.0/actualValue)+" ${resources.getString(R.string.string_for)} 1.0"
                }
            }
        }
    }
    private fun sellFormatValue(value:Double?, resources : Resources):String{

        return when (value) {
            null -> {
                resources.getString(R.string.no_data)
            }
            else -> {
                val actualValue = value* CurrentValue.getLine().chaosEquivalent!!
                if (actualValue>=1){
                    return "%.1f".format(actualValue)+" ${resources.getString(R.string.string_for)} 1.0"
                }
                else{
                    return "1.0 ${resources.getString(R.string.string_for)} " + "%.1f".format(1.0/actualValue)

                }
            }
        }
    }





}