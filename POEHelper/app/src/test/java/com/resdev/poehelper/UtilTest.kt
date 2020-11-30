package com.resdev.poehelper

import android.content.Context
import com.resdev.poehelper.utils.getGraphDataset
import org.junit.Test
import org.mockito.Mockito.mock


class UtilTest {
    @Test fun arrayWithFirstNullTest(){
        var array = listOf(null, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0)
        var dataset = getGraphDataset(array, 106.0, true, mock(Context::class.java))
        assert(dataset != null)
    }
}