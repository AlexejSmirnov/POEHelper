package com.resdev.poehelper.model.retrofit

import com.resdev.poehelper.model.pojo.CurrenciesModel
import com.resdev.poehelper.model.pojo.ItemsModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject


class PoeNinjaLoading @Inject constructor() {
        @Inject
        lateinit var poeNinjaClient: PoeNinjaApi


        suspend fun loadItems(leagueName: String, itemName:String, language: String): ItemsModel {
            return try{
                poeNinjaClient.getItem(leagueName, itemName, language) ?: ItemsModel.emptyModel
            } catch (e: Exception){
                ItemsModel.emptyModel
            }

        }


        suspend fun loadCurrencies(leagueName: String, itemName:String, language: String): CurrenciesModel {
            return try{
                poeNinjaClient.getCurrency(leagueName, itemName,language) ?: CurrenciesModel.emptyModel
            } catch (e: Exception){
                CurrenciesModel.emptyModel
            }
        }


}

