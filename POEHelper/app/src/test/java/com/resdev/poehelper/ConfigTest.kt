package com.resdev.poehelper

import com.resdev.poehelper.utils.Util
import org.junit.Test

class ConfigTest{
    @Test
    fun isBlackDark(){
        assert(!Util.isColorLight("00000000"))
    }

    @Test
    fun isWhiteDark(){
        assert(Util.isColorLight("FFFFFFFF"))
    }

    @Test
    fun getCal(){
        print(Util.getDaysSet())
    }


}