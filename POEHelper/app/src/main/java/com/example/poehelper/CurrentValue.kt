package com.example.poehelper

import com.example.poehelper.Models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
                line = CurrencyLine("Chaos Orb", 1.0, Pay(1.0), Receive(1.0))
            }
            if (i.currencyTypeName == currencyName){
                line = i
            }
        }
    }
    fun isInitialized():Boolean{
        return ::data.isInitialized
    }

    fun getArray():Array<CharSequence>{
        var array = ArrayList<CharSequence>()
        array.add("Chaos Orb")
        for (i in data.lines){
            array.add(i.currencyTypeName)
        }
        array.sortBy { it.toString() }
        return Array(array.size){i->array[i]}
    }

    fun loadData(){
        val data = PoeLoading.loadCurrencies(Config.league, "Currency")
        getActualData(Config.currency, data)
    }

}