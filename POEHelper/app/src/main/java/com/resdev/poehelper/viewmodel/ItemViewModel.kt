package com.resdev.poehelper.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.resdev.poehelper.MyApplication
import com.resdev.poehelper.model.Config
import com.resdev.poehelper.model.pojo.CurrenciesModel
import com.resdev.poehelper.model.pojo.ItemsModel
import com.resdev.poehelper.repository.ItemRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class ItemViewModel (application: Application, val repository: ItemRepository, val config: Config) : AndroidViewModel(application){

    private var _itemsData : MutableLiveData<ItemsModel> = MutableLiveData()
    private  var itemsData: MutableLiveData<ItemsModel> = MutableLiveData()
    private var filter = ""
    private var type: String = ""
    private var job: Job? = null
    private val filterObserver = Observer<ItemsModel> { filterData(it) }
    private val configObserver = Observer<String> { restartUpdatingOfListOfItems() }

    private fun setObservers(){
        _itemsData.observeForever(filterObserver)
        config.getObservableLeague().observeForever(configObserver)
        config.getObservableCurrency().observeForever(configObserver)
    }

    fun removeObservers(){
        _itemsData.removeObserver(filterObserver)
        config.getObservableLeague().removeObserver(configObserver)
        config.getObservableCurrency().removeObserver(configObserver)
    }

    fun initializeObserving(type: String){
        if(type==this.type){
            return
        }
        if(this.type.isEmpty()){setObservers()}
        this.type = type
        restartUpdatingOfListOfItems()

    }

    private fun filterData(itemsModel: ItemsModel){
        var data = itemsModel.lines
            .filter { (itemsModel.language.translations[it.name]?:it.name).toLowerCase().contains(filter.toLowerCase()) }
        val copy = itemsModel.copy()
        copy.lines = data.toList()
        itemsData.postValue(copy)
    }

    fun setFiler(filter: String){
        this.filter = filter
        filterData(_itemsData.value!!)
    }

    fun restartUpdatingOfListOfItems(){
        job?.cancel()
        updateListOfItems()
    }

    fun updateListOfItems(){
        viewModelScope.launch(IO) {
            while (true){
                _itemsData.postValue(repository.getItem(type))
                delay(60_000)
            }
        }
    }

    fun getItems():LiveData<ItemsModel>{
        return itemsData
    }

    override fun onCleared() {
        super.onCleared()
        removeObservers()
    }
}
