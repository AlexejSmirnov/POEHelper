package com.resdev.poehelper.di.currency

import android.app.Application
import com.resdev.poehelper.viewmodel.CurrencyViewModel
import com.resdev.poehelper.viewmodel.CurrencyViewModelFactory
import com.resdev.poehelper.viewmodel.ItemViewModel
import com.resdev.poehelper.viewmodel.ItemViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class CurrencyViewModelModule(val type: String) {
    @CurrencyScope
    @Provides
    fun currencyViewModel(application: Application): CurrencyViewModel{
        return CurrencyViewModelFactory(application, type).create(CurrencyViewModel::class.java)
    }

    @CurrencyScope
    @Provides
    fun type() = type
}