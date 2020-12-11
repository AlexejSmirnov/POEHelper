package com.resdev.poehelper.utils

import android.view.View
import com.resdev.poehelper.MyApplication
import com.resdev.poehelper.model.pojo.CurrencyDetail
import com.resdev.poehelper.model.room.ItemEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun openItemUrl(itemEntity: ItemEntity, view: View){
    GlobalScope.launch {
        val link = MyApplication.getPoeMarket().sendItemRequest(
            itemEntity, MyApplication.getConfig().getLeague())
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
        val link = MyApplication.getPoeMarket().sendCurrencyRequest(
            currencyDetail.tradeId, MyApplication.getCurrentValue().getDetails().tradeId, MyApplication.getConfig().getLeague())
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