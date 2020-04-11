package com.resdev.poehelper

import android.util.Log
import com.resdev.poehelper.Models.*

object CurrentValue {
    lateinit var currencyDetail: CurrencyDetail
    lateinit var line: CurrencyLine
    lateinit var data: CurrenciesModel
    fun getActualData(currencyName:String, data:CurrenciesModel = this.data){
        this.data = data
        for (i in data.currencyDetails){
            if (currencyName == i.name){
                currencyDetail = i
            }
        }
        for (i in data.lines){
            if (currencyName == "Chaos Orb"){
                line = CurrencyLine("Chaos Orb", 1.0, Pay(1.0), Receive(1.0), null, null)
            }
            if (i.currencyTypeName == currencyName){
                line = i
            }
        }
        Log.d("Value", "${this.line.currencyTypeName} ${this.line.chaosEquivalent}")
    }
    fun isInitialized():Boolean{
        return ::data.isInitialized
    }

    fun getArray():Array<Array<String>>{
        var array = ArrayList<CharSequence>()
        array.add("Chaos Orb")
        for (i in data.lines){
            array.add(i.currencyTypeName)
        }
        array.sortBy { it.toString() }
        var defaultArr = Array(array.size){i->array[i].toString()}
        var translated = Array(array.size){i->Util.getFromMap(array[i].toString(), data.language.translations)}
        return arrayOf(defaultArr, translated)
    }

    fun loadData(){
        val data = PoeNinjaLoading.loadCurrencies(Config.league, "Currency")
        getActualData(Config.currency, data)
    }

}