package com.resdev.poehelper.repository

import com.resdev.poehelper.MyApplication
import com.resdev.poehelper.model.Config
import com.resdev.poehelper.model.retrofit.PoeNinjaLoading
import javax.inject.Inject

class CurrencyRepository @Inject constructor(private val poeNinjaLoading: PoeNinjaLoading, private val config: Config){

    suspend fun getCurrency(currencyName: String) = poeNinjaLoading.loadCurrencies(config.getLeague(), currencyName, config.getLanguage())
}