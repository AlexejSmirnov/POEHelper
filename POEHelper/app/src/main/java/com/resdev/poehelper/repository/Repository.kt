package com.resdev.poehelper.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.resdev.poehelper.Config
import com.resdev.poehelper.CurrentValue
import com.resdev.poehelper.model.pojo.CurrenciesModel
import com.resdev.poehelper.model.pojo.ItemsModel
import com.resdev.poehelper.model.retrofit.PoeLeaguesLoading
import com.resdev.poehelper.model.retrofit.PoeNinjaLoading
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext


object Repository {
    private var currenciesModel: MutableLiveData<CurrenciesModel> = MutableLiveData()
    private var itemsModel: MutableLiveData<ItemsModel> = MutableLiveData()
    private var currentExchangeRate: MutableLiveData<CurrenciesModel> = MutableLiveData()
    private var lastCurrency: String? = null
    private var lastItem:String? = null
    init {
        uninterruptableLoading()
    }
    fun loadCurrencies() {
        GlobalScope.launch {
            var value = CurrenciesModel.emptyModel
            while (value.lines.isEmpty()){
                value = PoeNinjaLoading.loadCurrencies(Config.league, lastCurrency ?: "")
                if (value.lines.isNotEmpty()){
                    currenciesModel.postValue(value)
                    break
                }
                Thread.sleep(1000)
            }


        }
    }


    fun loadItems() {
        GlobalScope.launch {
            var value = ItemsModel.emptyModel
            while (value.lines.isEmpty()){
                value = PoeNinjaLoading.loadItems(Config.league, lastItem ?: "")
                if (value.lines.isNotEmpty()){
                    itemsModel.postValue(value)
                    break
                }
                Thread.sleep(1000)
            }


        }

    }

    private fun loadExchange() {
            currentExchangeRate.postValue(PoeNinjaLoading.loadCurrencies(Config.league, "Currency"))

    }

    fun updateExchange(){
        GlobalScope.launch {
            while (true){
                val exchange = PoeNinjaLoading.loadCurrencies(Config.league, "Currency")
                if (exchange.lines.isNotEmpty()){
                    currentExchangeRate.postValue(exchange)
                    return@launch
                }
            }
        }

    }

    fun getCurrencies(): LiveData<CurrenciesModel>{
        return currenciesModel
    }

    fun getItems(): LiveData<ItemsModel>{
        return itemsModel
    }

    fun getExchangeRate(): LiveData<CurrenciesModel>{
        return currentExchangeRate
    }

    fun setLastCurrency(type: String){
        if (lastCurrency!=type){
            lastCurrency = type
            lastItem = null
            currenciesModel.postValue(CurrenciesModel.emptyModel)
        }

    }

    fun setLastItem(type: String){
        if (lastItem!=type){
            lastItem = type
            lastCurrency = null
            itemsModel.postValue(ItemsModel.emptyModel)
        }
    }


    private fun uninterruptableLoading(){
        GlobalScope.launch(newSingleThreadContext("LoadValuesThread")) {
            while(!CurrentValue.isInitialized()){
                loadExchange()
            }
            while (true){
                if (lastCurrency != null){
                    val myCurrency = PoeNinjaLoading.loadCurrencies(Config.league, lastCurrency ?: "")
                    if (myCurrency.lines.isEmpty()){
                        Thread.sleep(1000)
                        continue
                    }
                    else{
                        currenciesModel.postValue(myCurrency)
                    }
                }
                else{
                    val myItem = PoeNinjaLoading.loadItems(Config.league, lastItem ?: "")
                    if (myItem.lines.isEmpty()){
                        Thread.sleep(1000)
                        continue
                    }
                    else{
                        itemsModel.postValue(myItem)
                    }
                }
                Thread.sleep(60000)

            }
        }
    }


    fun loadLeagues(leagues: ArrayList<String>){
        GlobalScope.launch(newSingleThreadContext("LoadLeaguesThread")) {
            while (leagues.isEmpty()){
                var a = PoeLeaguesLoading.loadLeagues().getEditedLeagues()
                leagues.addAll(a)
                Thread.sleep(1000)
            }

        }
    }


}