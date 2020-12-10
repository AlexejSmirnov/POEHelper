package com.resdev.poehelper


import com.resdev.poehelper.di.ApplicationComponent
import com.resdev.poehelper.di.DaggerApplicationComponent
import com.resdev.poehelper.di.bookmark.BookmarkViewModelModule
import com.resdev.poehelper.di.currency.CurrencyViewModelModule
import com.resdev.poehelper.di.item.ItemsViewModelModule
import com.resdev.poehelper.model.Config
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
    @Inject lateinit var config: Config

    override fun onCreate() {
        super.onCreate()

    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        component = DaggerApplicationComponent.builder().application(this).build()
        component.inject(this)
        application = this
        return component
    }

    companion object{
        private lateinit var component: ApplicationComponent
        private lateinit var application: MyApplication

        fun getApplicationContext() = application.applicationContext
        fun getPreloadingRepository() = application.preloadingRepository
        fun getItemRepository() = application.itemRepository
        fun getCurrencyRepository() = application.currencyRepository
        fun getCurrentValue() = application.currentValue
        fun getConfig() = application.config

        fun getBookmarkSubComponent() = component.bookmarkSubComponent(BookmarkViewModelModule())
        fun getNewItemSubComponent(type: String) = component.itemsSubComponent(
            ItemsViewModelModule(type)
        )
        fun getNewCurrencySubComponent(type: String) = component.currencySubComponent(
            CurrencyViewModelModule(type)
        )
    }
}