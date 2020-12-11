package com.resdev.poehelper.di

import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.resdev.poehelper.R
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class ActivityDependenciesModule {
    @Singleton
    @Provides
    @Named("Closed")
    fun getClosedDrawable(context: Context): Drawable{
        return ContextCompat.getDrawable(context, R.drawable.ic_star_border_white_24dp)!!
    }

    @Singleton
    @Provides
    @Named("Opened")
    fun getClosedOpened(context: Context): Drawable{
        return ContextCompat.getDrawable(context, R.drawable.ic_star_white_24dp)!!
    }

    @Singleton
    @Provides
    fun sharedPreferences(context: Context): SharedPreferences{
        return context.getSharedPreferences("mysettings", Context.MODE_PRIVATE)
    }
}