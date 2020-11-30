package com.resdev.poehelper.utils

import com.resdev.poehelper.model.Config

//utils that helps to generate urls for market for different languages

fun generatePoeMarketTradeUrl(league: String = Config.getLeague(), language: String = Config.getLanguage()): String{
    return when(language){
        "en"-> "https://www.pathofexile.com/trade/search/$league"
        "ko"-> "https://poe.game.daum.net/trade/search/$league"
        "ge"-> "https://de.pathofexile.com/trade/search/$league"
        else->"https://${language}.pathofexile.com/trade/search/"+ league
    }
}

fun generatePoeMarketExchangeUrl(league: String = Config.getLeague(), language: String = Config.getLanguage()): String{
    return when(language){
        "en"-> "https://www.pathofexile.com/trade/exchange/$league"
        "ko"-> "https://poe.game.daum.net/trade/exchange/$league"
        "ge"-> "https://de.pathofexile.com/trade/exchange/$league"
        else->"https://${language}.pathofexile.com/trade/exchange/"+ league
    }
}
