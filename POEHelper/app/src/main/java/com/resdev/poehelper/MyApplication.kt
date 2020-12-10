package com.resdev.poehelper


import com.resdev.poehelper.di.DaggerApplicationComponent
import com.resdev.poehelper.model.CurrentValue
import com.resdev.poehelper.repository.CurrencyRepository
import com.resdev.poehelper.repository.ItemRepository
import com.resdev.poehelper.repository.PreloadingRepository
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import javax.inject.Inject

open class MyApplication : DaggerApplication(){
    @Inject lateinit var preloadingRepository: PreloadingRepository
    @Inject lateinit var itemRepository: ItemRepository
    @Inject lateinit var currencyRepository: CurrencyRepository
    @Inject lateinit var currentValue: CurrentValue

    override fun onCreate() {
        super.onCreate()

    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val component = DaggerApplicationComponent.builder().application(this).build()
        component.inject(this)
        application = this
        return component
    }

    companion object{
        private lateinit var application: MyApplication

        fun getApplicationContext() = application.applicationContext
        fun getPreloadingRepository() = application.preloadingRepository
        fun getItemRepository() = application.itemRepository
        fun getCurrencyRepository() = application.currencyRepository
        fun getCurrentValue() = application.currentValue
    }
}