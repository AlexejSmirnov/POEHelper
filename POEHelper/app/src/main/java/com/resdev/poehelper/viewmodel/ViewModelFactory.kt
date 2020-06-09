package com.resdev.poehelper.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class CurrencyViewModelFactory(private val mApplication: Application, private val mType: String) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CurrencyViewModel(mType, mApplication) as T
    }
}


class ItemViewModelFactory(private val mApplication: Application, private val mType: String) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ItemViewModel(mType, mApplication) as T
    }
}

class BookmarkViewModelFactory(private val mApplication: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BookmarksViewModel(mApplication) as T
    }
}