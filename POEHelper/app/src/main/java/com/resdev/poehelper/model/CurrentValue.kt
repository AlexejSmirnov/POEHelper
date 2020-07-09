package com.resdev.poehelper.model
import com.resdev.poehelper.model.pojo.*
import com.resdev.poehelper.repository.Repository
import com.resdev.poehelper.utils.Util

object CurrentValue {
    lateinit var currencyDetail: CurrencyDetail
    lateinit var line: CurrencyLine
    lateinit var data: CurrenciesModel
    private val repository = Repository
    init {
        repository.getExchangeRate().observeForever {
            data = it
            getActualData()
        }
    }

    fun getActualData(){
        for (i in data.currencyDetails){
            if (Config.currency == i.name){
                currencyDetail = i
            }
        }
        for (i in data.lines){
            if (Config.currency == "Chaos Orb"){
                line = CurrencyLine(
                    "Chaos Orb",
                    1.0,
                    Pay(1.0),
                    Receive(1.0),
                    null,
                    null,
                    "chaos"
                )
            }
            if (i.currencyTypeName == Config.currency){
                line = i
            }
        }
    }
    fun isInitialized():Boolean{
        return this::line.isInitialized
    }


    fun getArray():Array<Array<String>>{
        var array = ArrayList<CharSequence>()
        array.add("Chaos Orb")
        for (i in data.lines){
            array.add(i.currencyTypeName)
        }
        array.sortBy { it.toString() }
        var defaultArr = Array(array.size){i->array[i].toString()}
        var translated = Array(array.size){i-> Util.getFromMap(array[i].toString(), data.language.translations)}
        return arrayOf(defaultArr, translated)
    }


}