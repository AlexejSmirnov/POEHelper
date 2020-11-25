package com.resdev.poehelper.view

import android.app.Application
import android.content.Context
import com.resdev.poehelper.repository.PreloadingRepository

class MyApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        CONTEXT = applicationContext
        PreloadingRepository
        
    }
    companion object{
        private lateinit var CONTEXT: Context
        fun getApplicationContext() = CONTEXT
    }
}