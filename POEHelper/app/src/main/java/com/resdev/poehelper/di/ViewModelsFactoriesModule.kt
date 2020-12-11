package com.resdev.poehelper.di

import android.app.Application
import com.resdev.poehelper.model.Config
import com.resdev.poehelper.repository.CurrencyRepository
import com.resdev.poehelper.repository.ItemRepository
import com.resdev.poehelper.viewmodel.BookmarkViewModelFactory
import com.resdev.poehelper.viewmodel.CurrencyViewModelFactory
import com.resdev.poehelper.viewmodel.ItemViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ViewModelsFactoriesModule {
    @Singleton
    @Provides
    fun itemsModelFactory(application: Application, itemRepository: ItemRepository, config: Config): ItemViewModelFactory{
        return ItemViewModelFactory(application, config, itemRepository)
    }

    @Singleton
    @Provides
    fun currencyModelFactory(application: Application, currencyRepository: CurrencyRepository, config: Config): CurrencyViewModelFactory{
        return CurrencyViewModelFactory(application, config, currencyRepository)
    }

    @Singleton
    @Provides
    fun bookmarkModelFactory(application: Application, itemRepository: ItemRepository, config: Config): BookmarkViewModelFactory{
        return BookmarkViewModelFactory(application, config, itemRepository)
    }
}