package com.example.poehelper.Adapter

import android.provider.ContactsContract
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries

fun getLineGraphSeries(list:List<Double>) : LineGraphSeries <DataPoint>{

    var newList :ArrayList<DataPoint> = ArrayList()
    for (i in list.indices){
        if (list[i] != null){
            newList.add(DataPoint(i.toDouble(), list[i]))
        }
    }
    return LineGraphSeries(newList.toTypedArray())

}