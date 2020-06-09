package com.resdev.poehelper.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.resdev.poehelper.model.pojo.ItemLine
import com.resdev.poehelper.model.pojo.ItemsModel
import com.resdev.poehelper.model.room.ItemEntity
import com.resdev.poehelper.repository.Repository

class BookmarksViewModel(application: Application) : AndroidViewModel(application){
    private val repository = Repository
    private var _itemsData : LiveData<List<ItemEntity>> = repository.getBookmarks()
    private  var itemsData: MutableLiveData<List<ItemEntity>> = MutableLiveData()
    private var filter = ""
    init {
        _itemsData.observeForever {
            filterData(it)
        }

    }

    private fun filterData(itemsModel: List<ItemEntity>){
        var data = ArrayList<ItemEntity>()
        for (i in itemsModel){
            if ((i.translatedName?:i.name).toLowerCase().contains(filter.toLowerCase())){
                data.add(i)
            }
        }
        itemsData.postValue(data)
    }

    fun setFiler(filter: String){
        this.filter = filter
        filterData(_itemsData.value!!)
    }

    fun loadItems(){
        repository.updateBookmarksAsync()

    }

    fun getItems(): LiveData<List<ItemEntity>> {
        return itemsData
    }




}