package com.resdev.poehelper.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.resdev.poehelper.model.Config
import com.resdev.poehelper.model.pojo.ItemLine
import com.resdev.poehelper.model.pojo.ItemsModel
import com.resdev.poehelper.repository.ItemRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ItemViewModel(val type: String, application: Application) : AndroidViewModel(application){
    private val repository = ItemRepository
    private var _itemsData : MutableLiveData<ItemsModel> = MutableLiveData()
    private  var itemsData: MutableLiveData<ItemsModel> = MutableLiveData()
    private var filter = ""
    private var job: Job? = null
    init {
        updateListOfItems()
        _itemsData.observeForever {
            filterData(it)
        }
        Config.getObservableLeague().observeForever{
            restartUpdatingOfListOfItems()
        }
        Config.getObservableCurrency().observeForever{
            restartUpdatingOfListOfItems()
        }

    }

    private fun filterData(itemsModel: ItemsModel){
        var data = ArrayList<ItemLine>()
        for (i in itemsModel.lines){
            if ((itemsModel.language.translations[i.name]?:i.name).toLowerCase().contains(filter.toLowerCase())){
                data.add(i)
            }
        }
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
                delay(60000)
            }
        }
    }

    fun getItems():LiveData<ItemsModel>{
        return itemsData
    }




}
