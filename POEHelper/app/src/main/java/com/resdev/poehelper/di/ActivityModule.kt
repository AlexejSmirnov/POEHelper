package com.resdev.poehelper.di

import com.resdev.poehelper.view.activity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule{
    @ContributesAndroidInjector
    abstract fun contributeProductListActivity(): MainActivity
}