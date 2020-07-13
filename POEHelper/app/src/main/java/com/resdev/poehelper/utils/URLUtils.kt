package com.resdev.poehelper.utils

import com.resdev.poehelper.model.Config

//utils that helps to generate urls for market for different languages
object URLUtils {
    fun generatePoeMarketTradeUrl(): String{
        return when(Config.language){
            "en"->"https://www.pathofexile.com/trade/search/"+ Config.league
            "ko"->"https://poe.game.daum.net/trade/search/"+ Config.league
            "ge"->"https://de.pathofexile.com/trade/search/"+ Config.league
            else->"https://${Config.language}.pathofexile.com/trade/search/"+ Config.league
        }
    }

    fun generatePoeMarketExchangeUrl(): String{
        return when(Config.language){
            "en"->"https://www.pathofexile.com/trade/exchange/"+ Config.league
            "ko"->"https://poe.game.daum.net/trade/exchange/"+ Config.league
            "ge"->"https://de.pathofexile.com/trade/exchange/"+ Config.league
            else->"https://${Config.language}.pathofexile.com/trade/exchange/"+ Config.league
        }
    }
}