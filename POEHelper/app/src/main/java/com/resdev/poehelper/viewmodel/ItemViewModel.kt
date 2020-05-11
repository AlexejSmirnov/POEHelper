package com.resdev.poehelper.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.resdev.poehelper.model.pojo.ItemLine
import com.resdev.poehelper.model.pojo.ItemsModel
import com.resdev.poehelper.repository.Repository

class ItemViewModel(val type: String, application: Application) : AndroidViewModel(application){
    private val repository = Repository
    private var _itemsData : LiveData<ItemsModel> = repository.getItems()
    private  var itemsData: MutableLiveData<ItemsModel> = MutableLiveData()
    private var filter = ""
    init {
        _itemsData.observeForever {
            filterData(it)
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
    fun setLastItem(){
        repository.setLastItem(type)
    }
    fun setFiler(filter: String){
        this.filter = filter
        filterData(_itemsData.value!!)
    }

     fun loadItems(){
        repository.loadItems()

    }

    fun getItems():LiveData<ItemsModel>{
        return itemsData
    }




}
