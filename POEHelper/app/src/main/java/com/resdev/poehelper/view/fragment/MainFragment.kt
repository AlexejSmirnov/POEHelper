package com.resdev.poehelper.view.fragment

interface MainFragment {
    //apply keyword filter in viewModel
    fun setFilter(filter: String)
    //invoke reload of data with recreating of recyclerview
    fun notifyCurrencyChanged()
    //paint recyclers EdgeEffect
    fun paintRecycler()
}