package com.resdev.poehelper.model.poemarket

import com.resdev.poehelper.model.room.ItemEntity

//This class has methods for generating requests for links to go to the official site
object RequestBuilder {
    fun generateItemLink(item:ItemEntity):PoeMarketItemRequest{
        return generateItemLinkByParams(
            item.translatedName?:item.name, item.links, item.corrupted, item.gemLevel, item.gemQuality, item.mapTier, item.levelRequired, item.variant)
    }

    private fun generateItemLinkByParams(name: String,
                                         links: Int? = null,
                                         corrupted :Boolean = false,
                                         gemLevel:Int? = null,
                                         gemQuality:Int? = null,
                                         mapTier:Int? = null,
                                         levelRequired:Int? = null,
                                         variant:String? = null) : PoeMarketItemRequest{
        return PoeMarketItemRequest(query = Query(
            type = name, filters = Filters(
                mapFilters = MapFilters(FiltersX(MapTier( 0))),
                socketFilters = SocketFilters(FiltersXXXX(Links(links?:0 ))),
                reqFilters = ReqFilters(FiltersXXX(Lvl( 0))),
                miscFilters = MiscFilters(
                    FiltersXX(
                    corrupted = Corrupted(corrupted.toString()),
                    gemLevel = GemLevel(gemLevel?:0),
                    quality = Quality(gemQuality?:0),
                    elderItem = ElderItem((variant=="Elder").toString()),
                    shaperItem = ShaperItem((variant=="Shaper").toString()),
                    crusaderItem = CrusaderItem((variant=="Crusader").toString()),
                    hunterItem = HunterItem((variant=="Hunter").toString()),
                    redeemerItem = RedeemerItem((variant=="Redeemer").toString()),
                    warlordItem = WarlordItem((variant=="Warlord").toString())
                ))
            )
        ))
    }

    fun generateCurrencyLink(want: String, have: String):PoeMarketCurrencyRequest{
        return PoeMarketCurrencyRequest(Exchange(
            statusx = Statusx("online"),
            want = listOf(want),
            have = listOf(have)))
    }


}