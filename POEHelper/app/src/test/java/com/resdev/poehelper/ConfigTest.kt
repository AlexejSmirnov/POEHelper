package com.resdev.poehelper


import com.resdev.poehelper.utils.getDaysSet
import com.resdev.poehelper.utils.isColorLight
import org.junit.Test

class ConfigTest{
    @Test
    fun isBlackDark(){
        assert(!isColorLight("00000000"))
    }

    @Test
    fun isWhiteDark(){
        assert(isColorLight("FFFFFFFF"))
    }

    @Test
    fun getCal(){
        println("Last 7 days: ${getDaysSet()}")
    }


}