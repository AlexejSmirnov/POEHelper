package com.resdev.poehelper

import com.resdev.poehelper.utils.ChartsUtil
import com.resdev.poehelper.utils.ColorsUtil
import com.resdev.poehelper.utils.Util
import org.junit.Test

class ConfigTest{
    @Test
    fun isBlackDark(){
        assert(!ColorsUtil.isColorLight("00000000"))
    }

    @Test
    fun isWhiteDark(){
        assert(ColorsUtil.isColorLight("FFFFFFFF"))
    }

    @Test
    fun getCal(){
        print(ChartsUtil.getDaysSet())
    }


}