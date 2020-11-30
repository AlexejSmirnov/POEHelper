package com.resdev.poehelper.model
import android.util.Log
import com.resdev.poehelper.model.pojo.*
import com.resdev.poehelper.repository.CurrencyRepository
import com.resdev.poehelper.utils.getFromMap
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Default

//this class is responsible for storing all currencies exchange rate and for providing the exchange rate for the current currency
object CurrentValue {
    private lateinit var currencyDetail: CurrencyDetail
    private lateinit var line: CurrencyLine
    private lateinit var data: CurrenciesModel
    init {
        setupObservers()
    }

    fun getActualData(){
        var a = data
        for (i in data.currencyDetails){
            if (Config.getCurrency() == i.name){
                currencyDetail = i
            }
        }
        for (i in data.lines){
            if (Config.getCurrency() == "Chaos Orb"){
                line = CurrencyLine(
                    "Chaos Orb",
                    1.0,
                    Pay(1.0),
                    Receive(1.0),
                    null,
                    null,
                    "chaos"
                )
                return
            }
            if (i.currencyTypeName == Config.getCurrency()){
                line = i
                return
            }
        }
    }

    fun getArray():Array<Array<String>>?{
        if (!isCurrentDataIsReady()){
            return null
        }
        var array = ArrayList<CharSequence>()
        array.add("Chaos Orb")
        for (i in data.lines){
            array.add(i.currencyTypeName)
        }
        array.sortBy { it.toString() }
        var defaultArr = Array(array.size){i->array[i].toString()}
        var translated = Array(array.size){i-> getFromMap(array[i].toString(), data.language.translations)}
        return arrayOf(defaultArr, translated)
    }

    fun getLine(): CurrencyLine{
        return line
    }

    fun getDetails():CurrencyDetail {
        return currencyDetail
    }

    fun getData():CurrenciesModel{
        return data
    }

     fun isCurrentDataIsReady():Boolean{
       return ::data.isInitialized && ::line.isInitialized && ::currencyDetail.isInitialized
    }

    var job: Job? = null
    fun loadCurrencyAsync(){
        job?.cancel()
        job = CoroutineScope(Default).launch {
            var result = CurrenciesModel.emptyModel
            while (result == CurrenciesModel.emptyModel){
                result = CurrencyRepository.getCurrency("Currency")
                delay(1000)
            }
            data = result
            getActualData()
        }
    }

    fun setupObservers(){
        Config.getObservableCurrency().observeForever{
            loadCurrencyAsync()
         }
        Config.getObservableLeague().observeForever {
            loadCurrencyAsync()
        }
        Config.getObservableLanguage().observeForever {
            loadCurrencyAsync()
        }
    }
}