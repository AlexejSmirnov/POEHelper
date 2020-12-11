package com.resdev.poehelper

import android.content.Context
import com.resdev.poehelper.di.DaggerTestComponent
import com.resdev.poehelper.model.retrofit.PoeLeagueLoading
import com.resdev.poehelper.utils.*
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito.mock
import javax.inject.Inject

class UtilTest {
    lateinit var leagues: Array<String>
    @Inject lateinit var poeLeagueLoading: PoeLeagueLoading
    val languages = arrayListOf("en", "ru", "ko", "ge", "br")
    init {
        runBlocking {
            DaggerTestComponent.create().inject(this@UtilTest)
            leagues = poeLeagueLoading.loadLeagues().getEditedLeagues() ?: throw Exception()
        }
    }

    @Test fun arrayWithFirstNullTest(){
        val array = listOf(null, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0)
        val dataSet = getGraphDataset(array, 106.0, true, mock(Context::class.java))
        assert(dataSet != null)
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