package com.resdev.poehelper.repository


import com.resdev.poehelper.model.retrofit.PoeLeagueLoading
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

object PreloadingRepository {
    var league = CoroutineScope(Dispatchers.Default).async {
        var value = emptyArray<String>()
        while (value.isEmpty()){
            value = PoeLeagueLoading.loadLeagues().getEditedLeagues() ?: emptyArray()
            delay(1000)
        }
        value
    }
}