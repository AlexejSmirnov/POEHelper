package com.resdev.poehelper

import android.app.Application
import android.content.Context
import com.resdev.poehelper.di.DaggerApplicationComponent
import com.resdev.poehelper.model.Config
import com.resdev.poehelper.model.retrofit.PoeLeagueLoading
import com.resdev.poehelper.model.retrofit.PoeMarket
import com.resdev.poehelper.model.retrofit.PoeNinjaApi
import com.resdev.poehelper.model.retrofit.PoeNinjaLoading
import com.resdev.poehelper.repository.PreloadingRepository

open class MyApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        CONTEXT = applicationContext
        val component = DaggerApplicationComponent.create()
        poeLeagueLoading = component.providesPoeLeagueClient()
        poeNinjaLoading = component.providesPoeNinjaClient()
        preloadingRepository = component.providePreloadingRepository()
    }



    companion object{
        private lateinit var CONTEXT: Context
        private lateinit var poeLeagueLoading: PoeLeagueLoading
        private lateinit var poeNinjaLoading: PoeNinjaLoading
        private lateinit var preloadingRepository: PreloadingRepository

        fun getApplicationContext() = CONTEXT
        fun getPoeLeagueLoading() = poeLeagueLoading
        fun getPoeNinjaLoading() = poeNinjaLoading
        fun getPreloadingRepository() = preloadingRepository
    }
}