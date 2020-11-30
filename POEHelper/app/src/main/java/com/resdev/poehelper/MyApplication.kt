package com.resdev.poehelper

import android.app.Application
import android.content.Context
import com.resdev.poehelper.model.Config
import com.resdev.poehelper.model.retrofit.PoeMarket
import com.resdev.poehelper.repository.PreloadingRepository

open class MyApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        CONTEXT = applicationContext
        PreloadingRepository
        PoeMarket.setLanguageObservable(Config.getObservableLanguage())
    }



    companion object{
        private lateinit var CONTEXT: Context
        fun getApplicationContext() =
            CONTEXT
    }
}