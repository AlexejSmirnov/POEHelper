package com.resdev.poehelper.model

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Config @Inject constructor() {
    private val _league = MutableLiveData("Standard")
    private val _currency = MutableLiveData("Chaos Orb")
    private val _language = MutableLiveData("en")
    private val _color = MutableLiveData(-0x16e19d)

    fun saveConfigs(sharedPreferences: SharedPreferences?){
        sharedPreferences?.let {
            val editor: SharedPreferences.Editor = it.edit()
            editor.putString("League", _league.value)
            editor.putString("Currency", _currency.value)
            editor.putString("Language", _language.value)
            editor.putInt("Color", _color.value ?: 0)
            editor.apply()
        }
    }

    fun loadConfig(sharedPreferences: SharedPreferences?){
        sharedPreferences?.let {
            _league.postValue(it.getString("League", "Standard"))
            _currency.postValue(it.getString("Currency", "Chaos Orb"))
            _language.postValue(it.getString("Language", "en"))
            _color.postValue(it.getInt("Color", -0x000001))
        }
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