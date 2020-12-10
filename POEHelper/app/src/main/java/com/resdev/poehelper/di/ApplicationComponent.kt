package com.resdev.poehelper.di

import android.app.Application
import android.content.Context
import com.resdev.poehelper.MyApplication
import com.resdev.poehelper.model.room.ApplicationDatabase
import com.resdev.poehelper.repository.CurrencyRepository
import com.resdev.poehelper.repository.ItemRepository
import com.resdev.poehelper.repository.PreloadingRepository
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    RetrofitApiModule::class,
    RoomModule::class])
interface ApplicationComponent : AndroidInjector<MyApplication> {

    fun provideItemRepository(): ItemRepository
    fun provideCurrencyRepository(): CurrencyRepository
    fun providePreloadingRepository(): PreloadingRepository
    fun provideApplicationContext(): Context

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): ApplicationComponent
    }
}