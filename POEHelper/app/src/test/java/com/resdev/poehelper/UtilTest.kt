package com.resdev.poehelper

import android.content.Context
import com.resdev.poehelper.model.retrofit.PoeLeagueLoading
import com.resdev.poehelper.utils.*
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito.mock


class UtilTest {
    lateinit var leagues: Array<String>
    val languages = arrayListOf("en", "ru", "ko", "ge", "br")
    init {
        runBlocking {
            leagues = PoeLeagueLoading.loadLeagues().getEditedLeagues() ?: throw Exception()
        }
    }
    @Test fun arrayWithFirstNullTest(){
        var array = listOf(null, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0)
        var dataset = getGraphDataset(array, 106.0, true, mock(Context::class.java))
        assert(dataset != null)
    }

    @Test
    fun isBlackDark(){
        assert(!isColorLight("00000000"))
    }

    @Test
    fun isWhiteDark(){
        assert(isColorLight("FFFFFFFF"))
    }

    @Test
    fun getCalendar(){
        println("Last 7 days: ${getDaysSet()}")
    }

    @Test
    fun testUrlGenerators(){
        println("Urls generating test")
        for (i in languages){
            for (j in leagues){
                println(generatePoeMarketExchangeUrl(j, i))
                println(generatePoeMarketTradeUrl(j, i))
            }
        }
    }
}