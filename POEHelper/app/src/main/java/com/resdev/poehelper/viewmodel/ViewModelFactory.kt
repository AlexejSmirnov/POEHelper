package com.resdev.poehelper.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.resdev.poehelper.model.Config
import com.resdev.poehelper.repository.CurrencyRepository
import com.resdev.poehelper.repository.ItemRepository
import javax.inject.Inject


class CurrencyViewModelFactory @Inject constructor(private val mApplication: Application, private val config: Config, private val currencyRepository: CurrencyRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CurrencyViewModel(mApplication,currencyRepository, config ) as T
    }
}


class ItemViewModelFactory @Inject constructor(private val mApplication: Application, private val config: Config, private val itemRepository: ItemRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ItemViewModel(mApplication, itemRepository, config) as T
    }
}

class BookmarkViewModelFactory(private val mApplication: Application, private val config: Config, private val itemRepository: ItemRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BookmarksViewModel(mApplication, itemRepository, config) as T
    }
}