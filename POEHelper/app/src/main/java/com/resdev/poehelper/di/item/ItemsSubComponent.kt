package com.resdev.poehelper.di.item

import com.resdev.poehelper.di.bookmark.BookmarkViewModelModule
import com.resdev.poehelper.view.fragment.ItemFragment
import com.resdev.poehelper.viewmodel.ItemViewModel
import dagger.Subcomponent

@ItemsScope
@Subcomponent(modules = [ItemsViewModelModule::class])
interface ItemsSubComponent{
    fun getItemViewModel(): ItemViewModel
    fun inject(fragment: ItemFragment)
}