package com.resdev.poehelper.di.currency

import com.resdev.poehelper.view.fragment.CurrencyFragment
import com.resdev.poehelper.viewmodel.CurrencyViewModel
import dagger.Subcomponent

@CurrencyScope
@Subcomponent(modules = [CurrencyViewModelModule::class])
interface CurrencySubComponent{
    fun getCurrencyViewModel():CurrencyViewModel
    fun inject(fragment: CurrencyFragment)
}