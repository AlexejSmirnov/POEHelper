package com.resdev.poehelper.di

import android.app.Application
import android.content.Context
import com.resdev.poehelper.MyApplication
import com.resdev.poehelper.model.room.ApplicationDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {
    @Singleton
    @Provides
    fun getDatabase(context: Context): ApplicationDatabase{
        return ApplicationDatabase.getInstance(context)
    }



}