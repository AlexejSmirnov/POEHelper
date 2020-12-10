package com.resdev.poehelper.di

import android.app.Application
import android.content.Context
import com.resdev.poehelper.MyApplication
import com.resdev.poehelper.model.Config
import com.resdev.poehelper.model.CurrentValue
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
    RoomModule::class,
    AppModule::class])
interface ApplicationComponent : AndroidInjector<MyApplication> {

    fun provideItemRepository(): ItemRepository
    fun provideCurrencyRepository(): CurrencyRepository
    fun providePreloadingRepository(): PreloadingRepository
    fun provideApplicationContext(): Context
    fun provideCurrentValue(): CurrentValue
    fun provideConfig(): Config


    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): ApplicationComponent
    }
}