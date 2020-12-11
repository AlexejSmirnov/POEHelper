package com.resdev.poehelper.di

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.resdev.poehelper.model.Config
import com.resdev.poehelper.model.room.ApplicationDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TestModule{
    @Singleton
    @Provides
    fun provideDatabase(): ApplicationDatabase{
        val context = ApplicationProvider.getApplicationContext<Context>()
        return Room.inMemoryDatabaseBuilder(
            context, ApplicationDatabase::class.java).build()
    }

    @Singleton
    @Provides
    fun provideConfig(): Config{
        return Config()
    }
}