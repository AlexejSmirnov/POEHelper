package com.resdev.poehelper.di.bookmark

import android.app.Application
import com.resdev.poehelper.viewmodel.BookmarkViewModelFactory
import com.resdev.poehelper.viewmodel.BookmarksViewModel
import dagger.Module
import dagger.Provides

@Module
class BookmarkViewModelModule {
    @BookmarkScope
    @Provides
    fun bookmarkViewModel(application: Application): BookmarksViewModel{
        return BookmarkViewModelFactory(application).create(BookmarksViewModel::class.java)
    }
}