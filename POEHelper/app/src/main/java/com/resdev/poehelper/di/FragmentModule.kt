package com.resdev.poehelper.di

import com.resdev.poehelper.view.fragment.BookmarkFragment
import com.resdev.poehelper.view.fragment.CurrencyFragment
import com.resdev.poehelper.view.fragment.ItemFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule{
    @ContributesAndroidInjector
    abstract fun injectBookmarkFragment(): BookmarkFragment
    @ContributesAndroidInjector
    abstract fun injectItemFragment(): ItemFragment
    @ContributesAndroidInjector
    abstract fun injectCurrencyFragment(): CurrencyFragment
}