package com.resdev.poehelper.utils

import android.view.View
import com.resdev.poehelper.model.Config
import com.resdev.poehelper.model.CurrentValue
import com.resdev.poehelper.model.pojo.CurrencyDetail
import com.resdev.poehelper.model.retrofit.PoeMarket
import com.resdev.poehelper.model.room.ItemEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun openItemUrl(itemEntity: ItemEntity, view: View){
    GlobalScope.launch {
        var link = PoeMarket.sendItemRequest(
            itemEntity, Config.getLeague())
        if (link==null){
            withContext(Dispatchers.Main){
                showSnackbar(view, "Connection error or Rate limit exceeded")
            }
            return@launch
        }
        withContext(Dispatchers.Main){
            openBrowserWindowByUrl(view.context, generatePoeMarketTradeUrl()+"/${link.id}")
        }

    }
}

fun openCurrencyIrl(currencyDetail: CurrencyDetail, view: View){
    GlobalScope.launch {
        var link = PoeMarket.sendCurrencyRequest(
            currencyDetail.tradeId?:"", CurrentValue.getDetails().tradeId?:"", Config.getLeague())
        if (link==null){
            withContext(Dispatchers.Main){
                showSnackbar(view, "Connection error or Rate limit exceeded")
            }
            return@launch
        }
        withContext(Dispatchers.Main){
            openBrowserWindowByUrl(view.context, generatePoeMarketExchangeUrl()+"/${link.id}")
        }

    }
}