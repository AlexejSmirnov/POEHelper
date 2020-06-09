package com.resdev.poehelper

import org.junit.Test

class ConfigTest{
    @Test
    fun isBlackDark(){
        assert(!Util.isColorLight("00000000"))
    }

    @Test
    fun isWhiteDark(){
        print((-0x16e19d).toString())
        assert(Util.isColorLight("FFFFFFFF"))
    }


}