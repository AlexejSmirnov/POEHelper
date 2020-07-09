package com.resdev.poehelper

import com.resdev.poehelper.model.retrofit.PoeLeagueLoading
import org.junit.Test

class LeagueTest {
    @Test
    fun leaguesLoading(){
        PoeLeagueLoading
        var leagues = PoeLeagueLoading.loadLeagues()
        for (i in leagues.getEditedLeagues()){
            println(i)
        }
    }
}