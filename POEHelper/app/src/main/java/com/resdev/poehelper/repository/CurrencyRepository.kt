package com.resdev.poehelper.repository

import com.resdev.poehelper.model.Config
import com.resdev.poehelper.model.retrofit.PoeLeagueLoading
import com.resdev.poehelper.model.retrofit.PoeNinjaLoading
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async

object CurrencyRepository{
    suspend fun getCurrency(currencyName: String) = PoeNinjaLoading.loadCurrencies(Config.getLeague(), currencyName)
}