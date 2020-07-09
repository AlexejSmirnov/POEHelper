package com.resdev.poehelper.utils

import android.graphics.Color
import androidx.annotation.ColorInt

object ColorsUtil {
    fun isColorLight(color: String): Boolean{
        return Integer.parseInt(color.substring(2, 4), 16)+
                Integer.parseInt(color.substring(4, 6), 16)+
                Integer.parseInt(color.substring(6, 8), 16) > 530
    }

    @ColorInt
    fun getDarkenColor(@ColorInt color: Int): Int {
        return Color.HSVToColor(FloatArray(3).apply {
            Color.colorToHSV(color, this)
            this[2] *= 0.9f
        })
    }
}