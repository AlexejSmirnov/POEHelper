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
                mapFilters = MapFilters(FiltersX(MapTier( zeroToNull(mapTier)))),
                socketFilters = SocketFilters(FiltersXXXX(Links(zeroToNull(links)))),
                reqFilters = ReqFilters(FiltersXXX(Lvl( zeroToNull(levelRequired)))),
                miscFilters = MiscFilters(
                    FiltersXX(
                    corrupted = Corrupted(corrupted.toString()),
                    gemLevel = GemLevel(zeroToNull(gemLevel)),
                    quality = Quality(zeroToNull(gemQuality)),
                    elderItem = ElderItem(itemTypeToBool(variant,"Elder")),
                    shaperItem = ShaperItem(itemTypeToBool(variant,"Shaper")),
                    crusaderItem = CrusaderItem(itemTypeToBool(variant,"Crusader")),
                    hunterItem = HunterItem(itemTypeToBool(variant,"Hunter")),
                    redeemerItem = RedeemerItem(itemTypeToBool(variant,"Redeemer")),
                    warlordItem = WarlordItem(itemTypeToBool(variant,"Warlord"))
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

    fun zeroToNull(value: Int?): Int?{
        if (value == 0){return null}
        return value
    }
    fun itemTypeToBool(variant: String?, title: String) : String?{
        if (variant==title){return true.toString()}
        return null
    }


}