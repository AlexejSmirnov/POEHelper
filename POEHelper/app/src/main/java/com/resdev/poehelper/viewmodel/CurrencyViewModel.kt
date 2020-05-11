package com.resdev.poehelper.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.resdev.poehelper.model.pojo.*
import com.resdev.poehelper.repository.Repository

class CurrencyViewModel(val type: String, application: Application) : AndroidViewModel(application) {
    private val repository = Repository
    private var _currenciesData : LiveData<CurrenciesModel> = repository.getCurrencies()
    private var currenciesData: MutableLiveData<CurrenciesModel> = MutableLiveData()
    private var filter = ""
    init {
        _currenciesData.observeForever {
            filterData(it)
        }
    }

    private fun filterData(currenciesModel: CurrenciesModel){
        var data = ArrayList<CurrencyLine>()
        for (i in currenciesModel.lines){
            if ((currenciesModel.language.translations[i.currencyTypeName]?:i.currencyTypeName).toLowerCase().contains(filter.toLowerCase())){
                data.add(i)
            }
        }
        val copy = currenciesModel.copy()
        copy.lines = data.toList()
        currenciesData.postValue(copy)
    }

    fun setFiler(filter: String){
        this.filter = filter
        filterData(_currenciesData.value!!)
    }


    fun loadCurrencies(){
        repository.loadCurrencies()
    }

    fun setCurrency(){
        repository.setLastCurrency(type)
    }
    fun getItems():LiveData<CurrenciesModel>{
        return currenciesData
    }






}
