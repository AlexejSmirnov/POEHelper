package com.resdev.poehelper


import com.resdev.poehelper.Models.LeaguesModel
import retrofit2.Call
import retrofit2.http.GET

interface PoeApi {
    @GET("/leagues")
    fun getItem(): Call<LeaguesModel?>?
}