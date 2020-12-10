package com.resdev.poehelper.di.item

import android.app.Application
import com.resdev.poehelper.di.currency.CurrencyScope
import com.resdev.poehelper.viewmodel.BookmarkViewModelFactory
import com.resdev.poehelper.viewmodel.BookmarksViewModel
import com.resdev.poehelper.viewmodel.ItemViewModel
import com.resdev.poehelper.viewmodel.ItemViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ItemsViewModelModule(val type: String) {
    @ItemsScope
    @Provides
    fun itemsViewModel(application: Application): ItemViewModel{
        return ItemViewModelFactory(application, type).create(ItemViewModel::class.java)
    }

    @CurrencyScope
    @Provides
    fun type() = type
}