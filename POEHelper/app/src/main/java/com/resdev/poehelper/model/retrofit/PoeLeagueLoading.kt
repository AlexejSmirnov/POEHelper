package com.resdev.poehelper.model.retrofit

import com.resdev.poehelper.model.pojo.LeaguesModel
import javax.inject.Inject


class PoeLeagueLoading @Inject constructor(){
    @Inject
    lateinit var poeLeagueClient: PoeLeagueApi

    fun loadLeagues(): LeaguesModel {
        return try{
            poeLeagueClient
                .getItem()
                ?.execute()
                ?.body()!!
        } catch (e: Exception){
            LeaguesModel.defaultModel
        }

    }
}
