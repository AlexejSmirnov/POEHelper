package com.resdev.poehelper.repository


import com.resdev.poehelper.MyApplication
import com.resdev.poehelper.model.retrofit.PoeLeagueLoading
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import javax.inject.Inject

class PreloadingRepository @Inject constructor(private val poeLeagueLoading: PoeLeagueLoading) {
    var league = CoroutineScope(Dispatchers.Default).async {
        var value = emptyArray<String>()
        while (value.isEmpty()){
            val a = poeLeagueLoading
            value = poeLeagueLoading.loadLeagues().getEditedLeagues() ?: emptyArray()
            delay(1000)
        }
        value
    }
}