package com.resdev.poehelper.utils

import com.resdev.poehelper.model.Config

//utils that helps to generate urls for market for different languages
object URLUtils {
    fun generatePoeMarketTradeUrl(): String{
        return when(Config.getLanguage()){
            "en"->"https://www.pathofexile.com/trade/search/"+ Config.getLeague()
            "ko"->"https://poe.game.daum.net/trade/search/"+ Config.getLeague()
            "ge"->"https://de.pathofexile.com/trade/search/"+ Config.getLeague()
            else->"https://${Config.getLanguage()}.pathofexile.com/trade/search/"+ Config.getLeague()
        }
    }

    fun generatePoeMarketExchangeUrl(): String{
        return when(Config.getLanguage()){
            "en"->"https://www.pathofexile.com/trade/exchange/"+ Config.getLeague()
            "ko"->"https://poe.game.daum.net/trade/exchange/"+ Config.getLeague()
            "ge"->"https://de.pathofexile.com/trade/exchange/"+ Config.getLeague()
            else->"https://${Config.getLanguage()}.pathofexile.com/trade/exchange/"+ Config.getLeague()
        }
    }
}