package com.resdev.poehelper.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.resdev.poehelper.MyApplication
import com.resdev.poehelper.model.Config
import com.resdev.poehelper.model.pojo.ItemsModel
import com.resdev.poehelper.repository.ItemRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class ItemViewModel @Inject constructor(val type: String, application: Application) : AndroidViewModel(application){
    private val repository = MyApplication.getItemRepository()
    private var _itemsData : MutableLiveData<ItemsModel> = MutableLiveData()
    private  var itemsData: MutableLiveData<ItemsModel> = MutableLiveData()
    private val config = MyApplication.getConfig()
    private var filter = ""
    private var job: Job? = null
    init {
        updateListOfItems()
        _itemsData.observeForever {
            filterData(it)
        }
        config.getObservableLeague().observeForever{
            restartUpdatingOfListOfItems()
        }
        config.getObservableCurrency().observeForever{
            restartUpdatingOfListOfItems()
        }

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

}
