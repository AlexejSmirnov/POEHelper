package com.resdev.poehelper.model.retrofit

import com.resdev.poehelper.model.Config
import com.resdev.poehelper.model.pojo.CurrenciesModel
import com.resdev.poehelper.model.pojo.ItemsModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

object PoeNinjaLoading {
    private  var retrofit : Retrofit = Retrofit.Builder()
        .baseUrl("https://poe.ninja")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val poeNinjaClient = retrofit.create(PoeNinjaApi::class.java)


    fun loadItems(leagueName: String, itemName:String): ItemsModel {
        return try{
            poeNinjaClient
                .getItem(leagueName, itemName, Config.getLanguage())
                ?.execute()
                ?.body()!!
        } catch (e: Exception){
            ItemsModel.emptyModel
        }

    }


    fun loadCurrencies(leagueName: String, itemName:String): CurrenciesModel {
        return try{
            poeNinjaClient
                .getCurrency(leagueName, itemName,
                    Config.getLanguage()
                )
                ?.execute()
                ?.body()!!
        } catch (e: Exception){
            CurrenciesModel.emptyModel
        }


    }
}

