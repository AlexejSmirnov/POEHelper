package com.resdev.poehelper.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.resdev.poehelper.model.Config
import com.resdev.poehelper.utils.fromRetrofitItemToRoomEntity
import com.resdev.poehelper.model.room.ItemEntity
import com.resdev.poehelper.repository.ItemRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BookmarksViewModel(application: Application) : AndroidViewModel(application){
    val repository = ItemRepository
    private  var _itemsData: MutableLiveData<List<ItemEntity>> = MutableLiveData()
    private  var itemsData: MutableLiveData<List<ItemEntity>> = MutableLiveData()
    private var filter = ""
    private var job: Job? = null
    init {
        launchUpdating()
        _itemsData.observeForever {
            filterData(it)
        }
        Config.getObservableLeague().observeForever{
            restartLaunchUpdating()
        }
        Config.getObservableCurrency().observeForever{
            restartLaunchUpdating()
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
        _itemsData.value?.let {
            filterData(it)
        }

    }

    fun loadItems(){
        viewModelScope.launch(IO) { updateBookmarksItems() }
    }

    fun getItems(): LiveData<List<ItemEntity>> {
        return itemsData
    }


    fun launchUpdating(){
        job = viewModelScope.launch(IO) {
            while (true){
                updateBookmarksItems()
                _itemsData.postValue(repository.getItemsFromDatabase())
                delay(60000)
            }
        }
    }

    fun restartLaunchUpdating(){
        job?.cancel()
        launchUpdating()
    }

    suspend fun updateBookmarksItems(){
        var itemsTypes = repository.getTypes()
        _itemsData.value?.let { value ->
            val idMap = value.map { it.id }
            for (i in itemsTypes){
                val items = ItemRepository.getItem(i)
                items.bindModel()
                for (j in items.lines){
                    val id = idMap.indexOf(j.id)
                    if (id!=-1){
                        ItemRepository.updateItem(
                            fromRetrofitItemToRoomEntity(
                                j,
                                i
                            )
                        )
                    }
                }
            }
        }

    }
}