package com.resdev.poehelper

import android.app.Application
import android.content.Context
import com.resdev.poehelper.di.DaggerApplicationComponent
import com.resdev.poehelper.model.retrofit.PoeLeagueLoading
import com.resdev.poehelper.model.retrofit.PoeNinjaLoading
import com.resdev.poehelper.model.room.ApplicationDatabase
import com.resdev.poehelper.repository.CurrencyRepository
import com.resdev.poehelper.repository.ItemRepository
import com.resdev.poehelper.repository.PreloadingRepository

open class MyApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        val applicationComponent = DaggerApplicationComponent.builder().application(this).build()
        CONTEXT = applicationComponent.provideApplicationContext()
        itemRepository = applicationComponent.provideItemRepository()
        preloadingRepository = applicationComponent.providePreloadingRepository()
        currencyRepository = applicationComponent.provideCurrencyRepository()
    }

    companion object{
     private lateinit var CONTEXT: Context
     private lateinit var preloadingRepository: PreloadingRepository
     private lateinit var itemRepository: ItemRepository
     private lateinit var currencyRepository: CurrencyRepository

        fun getApplicationContext() = CONTEXT
        fun getPreloadingRepository() = preloadingRepository
        fun getItemRepository() = itemRepository
        fun getCurrencyRepository() = currencyRepository
    }
}