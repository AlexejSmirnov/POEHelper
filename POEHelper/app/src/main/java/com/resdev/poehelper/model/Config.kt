package com.resdev.poehelper.model

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.resdev.poehelper.view.MyApplication


object Config {
    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getApplicationContext())

    var league = "Delirium"
    var currency = "Chaos Orb"
    var color = -0x16e19d
    var language = "en"

    fun saveConfigs(){
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("League", league)
        editor.putString("Currency", currency)
        editor.putString("Language", language)
        editor.putInt("Color", color)
        editor.apply()
    }

    fun loadConfig(){
        league = sharedPreferences.getString("League", "Standard")!!
        currency = sharedPreferences.getString("Currency", "Chaos Orb")!!
        language = sharedPreferences.getString("Language", "en")!!
        color = sharedPreferences.getInt("Color", -0x000001)
    }


}