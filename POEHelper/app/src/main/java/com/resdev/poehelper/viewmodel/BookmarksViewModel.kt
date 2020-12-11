package com.resdev.poehelper.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.resdev.poehelper.model.Config
import com.resdev.poehelper.model.room.ItemEntity
import com.resdev.poehelper.repository.ItemRepository
import com.resdev.poehelper.utils.fromRetrofitItemToRoomEntity
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BookmarksViewModel (application: Application, val repository: ItemRepository, val config: Config) : AndroidViewModel(application){
    private  var _itemsData: MutableLiveData<List<ItemEntity>> = MutableLiveData()
    private  var itemsData: MutableLiveData<List<ItemEntity>> = MutableLiveData()
    private var filter = ""
    private val filterObserver = Observer<List<ItemEntity>> { filterData(it) }
    private val configObserver = Observer<String> { restartLaunchUpdating() }

    private var job: Job? = null
    init {
        restartLaunchUpdating()
        setObservers()
    }

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

    private fun filterData(itemsModel: List<ItemEntity>){
        itemsData.postValue(itemsModel
            .filter {(it.translatedName?:it.name).toLowerCase().contains(filter.toLowerCase())})

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
                delay(60_000)
            }
        }
    }

    fun restartLaunchUpdating(){
        job?.cancel()
        launchUpdating()
    }

    suspend fun updateBookmarksItems(){
        val itemsTypes = repository.getTypes()
        _itemsData.value?.let { value ->
            val idMap = value.map { it.id }
            for (i in itemsTypes){
                val items = repository.getItem(i)
                items.bindModel()
                for (j in items.lines){
                    val id = idMap.indexOf(j.id)
                    if (id!=-1){
                        repository.updateItem(fromRetrofitItemToRoomEntity(j,i)
                        )
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        removeObservers()
    }
}