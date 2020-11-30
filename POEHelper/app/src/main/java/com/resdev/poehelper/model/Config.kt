package com.resdev.poehelper.model

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import com.resdev.poehelper.MyApplication


object Config {
    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getApplicationContext())
    private val _league = MutableLiveData("Delirium")
    private val _currency = MutableLiveData("Chaos Orb")
    private val _language = MutableLiveData("en")
    private val _color = MutableLiveData(-0x16e19d)

    fun saveConfigs(){
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("League", _league.value)
        editor.putString("Currency", _currency.value)
        editor.putString("Language", _language.value)
        editor.putInt("Color", _color.value ?: 0)
        editor.apply()
    }

    fun loadConfig(){
        _league.postValue(sharedPreferences.getString("League", "Standard"))
        _currency.postValue(sharedPreferences.getString("Currency", "Chaos Orb"))
        _language.postValue(sharedPreferences.getString("Language", "en"))
        _color.postValue(sharedPreferences.getInt("Color", -0x000001))
    }

    fun getObservableLeague(): LiveData<String> = _league
    fun getObservableCurrency(): LiveData<String> = _currency
    fun getObservableLanguage(): LiveData<String> = _language
    fun getObservableColor(): LiveData<Int> = _color

    fun getColor() = _color.value ?: -0x16e19d
    fun getCurrency() = _currency.value ?: "Chaos Orb"
    fun getLanguage() = _language.value ?: "en"
    fun getLeague() = _league.value ?: "Standard"


    fun setColor(color: Int) {_color.value = color}
    fun setCurrency(currency: String) = _currency.postValue(currency)
    fun setLanguage(language: String) = _language.postValue(language)
    fun setLeague(league: String) = _league.postValue(league)
}