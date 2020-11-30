package com.resdev.poehelper.model.retrofit

import com.resdev.poehelper.model.pojo.LeaguesModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object PoeLeagueLoading {
    private  var retrofit : Retrofit = Retrofit.Builder()
        .baseUrl("http://api.pathofexile.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val poeNinjaClient = retrofit.create(PoeLeagueApi::class.java)

    fun loadLeagues(): LeaguesModel {
        return try{
            poeNinjaClient
                .getItem()
                ?.execute()
                ?.body()!!
        } catch (e: Exception){
            LeaguesModel.defaultModel
        }

    }
}