package com.resdev.poehelper.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.resdev.poehelper.model.Config
import com.resdev.poehelper.model.CurrentValue
import com.resdev.poehelper.model.Converter
import com.resdev.poehelper.model.pojo.CurrenciesModel
import com.resdev.poehelper.model.pojo.ItemsModel
import com.resdev.poehelper.model.retrofit.PoeLeagueLoading
import com.resdev.poehelper.model.retrofit.PoeNinjaLoading
import com.resdev.poehelper.model.room.ApplicationDatabase
import com.resdev.poehelper.model.room.ItemEntity
import com.resdev.poehelper.view.MyApplication
import kotlinx.coroutines.*


object Repository {
    var database = ApplicationDatabase.getInstance(MyApplication.getApplicationContext())
    private var currenciesModel: MutableLiveData<CurrenciesModel> = MutableLiveData()
    private var itemsModel: MutableLiveData<ItemsModel> = MutableLiveData()
    private var bookmarkItems: MutableLiveData<List<ItemEntity>> = MutableLiveData()
    //CurrentValue object observe this field
    private var currentExchangeRate: MutableLiveData<CurrenciesModel> = MutableLiveData()
    private var lastCurrency: String? = null
    private var lastItem:String? = null
    init {
        uninterruptableLoading()
        bookmarksLoader()
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

    fun getBookmarks():LiveData<List<ItemEntity>>{
        return bookmarkItems
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

    //this method loads items/currencies every minute
    //tries to load ExchangeRate (which observed by CurrentValue) before his actual work
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
                var a = PoeLeagueLoading.loadLeagues().getEditedLeagues()
                leagues.addAll(a)
                Thread.sleep(1000)
            }

        }
    }

    fun updateBookmarksAsync(){
        GlobalScope.launch{
            updateBookmarksItems()
        }

    }
    //this method loads itemModel of every all types that are in the database
    //after it go through each loaded item and find out if its id is in the database
    fun updateBookmarksItems(){
        var itemsTypes = database.entityDao.getTypes()
        while (bookmarkItems.value==null){}
        var idMap = bookmarkItems.value!!.map { it.id }
        for (i in itemsTypes){
            val items = PoeNinjaLoading.loadItems(Config.league, i)
            items.bindModel()
            for (j in items.lines){
                val id = idMap.indexOf(j.id)
                if (id!=-1){
                    database.entityDao.updateItem(Converter.fromRetrofitItemToRoomEntity(j, i))
                }
            }
        }
        bookmarkItems.postValue(database.entityDao.getItems())

    }

    //method update values in database every minute
    fun bookmarksLoader(){
        GlobalScope.launch {
            bookmarkItems.postValue(database.entityDao.getItems())
            while (true){
                updateBookmarksItems()
                delay(60000)
            }
        }
    }

    fun addItem(item: ItemEntity){
        GlobalScope.async {
            database.entityDao.insertItem(item)
        }
    }

    fun removeEntity(item: ItemEntity){
        GlobalScope.async {
            database.entityDao.removeItem(item)
            updateBookmarksItems()
        }
    }



}