package com.resdev.poehelper

import com.resdev.poehelper.Models.LeaguesModel
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object PoeLoading {
    var okHttpClient = OkHttpClient().newBuilder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()
    private  var retrofit : Retrofit = Retrofit.Builder()
        .baseUrl("http://api.pathofexile.com")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val poeNinjaClient = retrofit.create(PoeApi::class.java)

    fun loadLeagues(): LeaguesModel {
        return poeNinjaClient
            .getItem()
            ?.execute()
            ?.body()!!
    }
}