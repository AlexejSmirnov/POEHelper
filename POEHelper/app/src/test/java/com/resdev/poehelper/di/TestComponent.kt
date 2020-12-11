package com.resdev.poehelper.di

import com.resdev.poehelper.PoeMarketTest
import com.resdev.poehelper.RepositoryUnitTest
import com.resdev.poehelper.RoomTest
import com.resdev.poehelper.UtilTest
import com.resdev.poehelper.model.Config
import com.resdev.poehelper.model.retrofit.PoeLeagueLoading
import com.resdev.poehelper.model.retrofit.PoeMarket
import com.resdev.poehelper.model.retrofit.PoeNinjaLoading
import com.resdev.poehelper.repository.CurrencyRepository
import com.resdev.poehelper.repository.ItemRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RetrofitApiModule::class, TestModule::class])
interface TestComponent {
    fun provideItemRepository(): ItemRepository
    fun provideCurrencyRepository(): CurrencyRepository
    fun provideConfig(): Config
    fun providePoeMarket(): PoeMarket
    fun providePoeNinjaLoading(): PoeNinjaLoading
    fun providePoeLeagueLoading(): PoeLeagueLoading

    fun inject(poeMarketTest: PoeMarketTest)
    fun inject(repositoryUnitTest: RepositoryUnitTest)
    fun inject(roomTest: RoomTest)
    fun inject(utilTest: UtilTest)
}