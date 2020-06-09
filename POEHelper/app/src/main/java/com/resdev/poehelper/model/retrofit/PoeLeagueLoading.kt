package com.resdev.poehelper.model.retrofit

import com.resdev.poehelper.model.pojo.LeaguesModel
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.util.concurrent.TimeUnit


object PoeLeagueLoading {
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