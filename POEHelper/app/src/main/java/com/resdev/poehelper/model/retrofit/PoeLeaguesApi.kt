package com.resdev.poehelper.model.retrofit


import com.resdev.poehelper.model.pojo.LeaguesModel
import retrofit2.Call
import retrofit2.http.GET

interface PoeLeaguesApi {
    @GET("/leagues")
    fun getItem(): Call<LeaguesModel?>?
}