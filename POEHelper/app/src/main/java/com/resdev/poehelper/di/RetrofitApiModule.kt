package com.resdev.poehelper.di

import com.resdev.poehelper.model.retrofit.PoeLeagueApi
import com.resdev.poehelper.model.retrofit.PoeMarketApi
import com.resdev.poehelper.model.retrofit.PoeNinjaApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RetrofitApiModule {
    @Singleton
    @Provides
    fun poeLeagueLoadingClient(): PoeLeagueApi{
        return Retrofit.Builder()
            .baseUrl("http://api.pathofexile.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PoeLeagueApi::class.java)
    }

    @Singleton
    @Provides
    fun poeNinjaClient(): PoeNinjaApi{
        return Retrofit.Builder()
            .baseUrl("https://poe.ninja")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PoeNinjaApi::class.java)
    }


}