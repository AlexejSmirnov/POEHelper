package com.resdev.poehelper

import android.app.Application
import android.content.Context
import com.resdev.poehelper.model.retrofit.PoeMarket
import com.resdev.poehelper.repository.PreloadingRepository

class MyApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        CONTEXT = applicationContext
        PreloadingRepository
        PoeMarket
    }



    companion object{
        private lateinit var CONTEXT: Context
        fun getApplicationContext() =
            CONTEXT
    }
}