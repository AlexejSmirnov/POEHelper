package com.resdev.poehelper.di.bookmark

import com.resdev.poehelper.view.fragment.BookmarkFragment
import com.resdev.poehelper.viewmodel.BookmarksViewModel
import dagger.Subcomponent

@BookmarkScope
@Subcomponent(modules = [BookmarkViewModelModule::class])
interface BookmarkSubComponent{
    fun getBookmarkViewModel(): BookmarksViewModel
    fun inject(fragment: BookmarkFragment)
}