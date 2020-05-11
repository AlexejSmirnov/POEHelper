package com.resdev.poehelper.view.fragment

interface MainFragment {
    fun setFilter(filter: String)
    fun notifyCurrencyChanged()
    fun notifyLeagueChanged()
    fun paintRecycler()
}