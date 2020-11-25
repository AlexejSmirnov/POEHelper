package com.resdev.poehelper.repository


import com.resdev.poehelper.model.retrofit.PoeLeagueLoading
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

object PreloadingRepository {
    var league = CoroutineScope(Dispatchers.Default).async {
        var value = emptyArray<String>()
        while (value.isEmpty()){
            value = PoeLeagueLoading.loadLeagues().getEditedLeagues() ?: emptyArray()
        }
        value
    }
}