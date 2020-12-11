package com.resdev.poehelper.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.resdev.poehelper.model.Config
import com.resdev.poehelper.model.pojo.CurrenciesModel
import com.resdev.poehelper.repository.CurrencyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CurrencyViewModel (application: Application, val repository: CurrencyRepository, val config: Config) : AndroidViewModel(application) {
    private var _currenciesData : MutableLiveData<CurrenciesModel> = MutableLiveData()
    private var currenciesData: MutableLiveData<CurrenciesModel> = MutableLiveData()
    private var filter = ""
    private var type = ""
    private var job: Job? = null
    private val filterObserver = Observer<CurrenciesModel> { filterData(it)
    }
    private val configObserver = Observer<String> { restartUpdatingOfListOfValue() }

    private fun setObservers(){
        _currenciesData.observeForever(filterObserver)
        config.getObservableLeague().observeForever(configObserver)
        config.getObservableCurrency().observeForever(configObserver)
    }

    fun removeObservers(){
        _currenciesData.removeObserver(filterObserver)
        config.getObservableLeague().removeObserver(configObserver)
        config.getObservableCurrency().removeObserver(configObserver)
    }

    fun initializeObserving(type: String){
        if(type==this.type){
            return
        }
        if(this.type.isEmpty()){setObservers()}
        this.type = type
        restartUpdatingOfListOfValue()
    }

    private fun filterData(currenciesModel: CurrenciesModel){
        val data = currenciesModel.lines
            .filter {(currenciesModel.language.translations[it.currencyTypeName]?:it.currencyTypeName).toLowerCase().contains(filter.toLowerCase())}
        val copy = currenciesModel.copy()
        copy.lines = data.toList()
        currenciesData.postValue(copy)
    }

    fun setFiler(filter: String){
        this.filter = filter
        filterData(_currenciesData.value!!)
    }

    fun restartUpdatingOfListOfValue(){
        job?.cancel()
        updateListOfValues()
    }

    fun updateListOfValues(){
        job = viewModelScope.launch(Dispatchers.IO) {
            while (true){
                _currenciesData.postValue(repository.getCurrency(type))
                delay(60_000)
            }
        }
    }

    fun getItems():LiveData<CurrenciesModel>{
        return currenciesData
    }

    override fun onCleared() {
        super.onCleared()
        removeObservers()
    }
}
