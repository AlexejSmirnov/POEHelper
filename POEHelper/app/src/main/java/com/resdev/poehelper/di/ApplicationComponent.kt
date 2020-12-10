package com.resdev.poehelper.di

import com.resdev.poehelper.model.retrofit.*
import com.resdev.poehelper.repository.PreloadingRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RetrofitApiModule::class])
interface ApplicationComponent {
    fun providesPoeNinjaClient(): PoeNinjaLoading
    fun providesPoeLeagueClient(): PoeLeagueLoading
    fun providePreloadingRepository(): PreloadingRepository
}