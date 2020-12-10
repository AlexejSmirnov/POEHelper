package com.resdev.poehelper.repository

import com.resdev.poehelper.MyApplication
import com.resdev.poehelper.model.Config
import com.resdev.poehelper.model.retrofit.PoeNinjaLoading

object CurrencyRepository{

    suspend fun getCurrency(currencyName: String) = MyApplication.getPoeNinjaLoading().loadCurrencies(Config.getLeague(), currencyName, Config.getLanguage())
}