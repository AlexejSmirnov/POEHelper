package com.resdev.poehelper.di

import android.app.Application
import com.resdev.poehelper.MyApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Singleton
    @Provides
    fun getContext(application: Application) = application.applicationContext

}