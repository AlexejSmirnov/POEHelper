package com.resdev.poehelper.utils

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.view.View
import androidx.browser.customtabs.CustomTabsIntent
import com.google.android.material.snackbar.Snackbar
import com.resdev.poehelper.MyApplication
import com.resdev.poehelper.R
import com.resdev.poehelper.model.Config


    fun getFromMap(field : String, map : HashMap<String, String>) : String{
        return map[field] ?: field
    }

    fun showSnackbar(view: View, text: String){
        var snackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG)
        snackbar.view.setBackgroundColor(view.context.getColor(R.color.lightGray))
        snackbar.setActionTextColor(Color.BLACK)
        snackbar.show()
    }

    fun roundPercentages(value: Double): String {
        if (value>=10000){
            return ((value/1000).toInt().toString())+"k%"
        }
        return "$value%"
    }

    fun openBrowserWindowByUrl(context: Context, url: String){
        val builder = CustomTabsIntent.Builder()
            .setToolbarColor(
                getDarkenColor(
                    MyApplication.getConfig().getColor()
                )
            )
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(context, Uri.parse(url))
    }



