package com.resdev.poehelper.model.poemarket
import com.google.gson.annotations.SerializedName

//the class is responsible for generating a item exchange request
data class PoeMarketItemRequest(
    @SerializedName("query")
    val query: Query,
    @SerializedName("sort")
    val sort: Sort = Sort("asc")
)

data class Query(
    @SerializedName("filters")
    val filters: Filters,
    @SerializedName("stats")
    val stats: List<Stat> = listOf(Stat(listOf(),"and")),
    @SerializedName("status")
    val status: Statusx = Statusx("online"),
    @SerializedName("term")
    val type: String
)

data class Sort(
    @SerializedName("price")
    val price: String
)

data class Filters(
    @SerializedName("map_filters")
    val mapFilters: MapFilters,
    @SerializedName("misc_filters")
    val miscFilters: MiscFilters,
    @SerializedName("req_filters")
    val reqFilters: ReqFilters,
    @SerializedName("socket_filters")
    val socketFilters: SocketFilters
)

data class Stat(
    @SerializedName("filters")
    val filters: List<Any>,
    @SerializedName("type")
    val type: String
)

data class Status(
    @SerializedName("option")
    val option: String
)

data class MapFilters(
    @SerializedName("filters")
    val filters: FiltersX
)

data class MiscFilters(
    @SerializedName("filters")
    val filters: FiltersXX
)

data class ReqFilters(
    @SerializedName("filters")
    val filters: FiltersXXX
)

data class SocketFilters(
    @SerializedName("filters")
    val filters: FiltersXXXX
)

data class FiltersX(
    @SerializedName("map_tier")
    val mapTier: MapTier
)

data class MapTier(
    @SerializedName("min")
    val min: Int
)

data class FiltersXX(
    @SerializedName("corrupted")
    val corrupted: Corrupted,
    @SerializedName("crusader_item")
    val crusaderItem: CrusaderItem,
    @SerializedName("elder_item")
    val elderItem: ElderItem,
    @SerializedName("gem_level")
    val gemLevel: GemLevel,
    @SerializedName("hunter_item")
    val hunterItem: HunterItem,
    @SerializedName("quality")
    val quality: Quality,
    @SerializedName("redeemer_item")
    val redeemerItem: RedeemerItem,
    @SerializedName("shaper_item")
    val shaperItem: ShaperItem,
    @SerializedName("warlord_item")
    val warlordItem: WarlordItem
)

data class Corrupted(
    @SerializedName("option")
    val option: String = "false"
)

data class CrusaderItem(
    @SerializedName("option")
    val option: String = "false"
)

data class ElderItem(
    @SerializedName("option")
    val option: String = "false"
)

data class GemLevel(
    @SerializedName("min")
    val min: Int = 0
)

data class HunterItem(
    @SerializedName("option")
    val option: String = "false"
)

data class Quality(
    @SerializedName("min")
    val min: Int = 0
)

data class RedeemerItem(
    @SerializedName("option")
    val option: String = "false"
)

data class ShaperItem(
    @SerializedName("option")
    val option: String = "false"
)

data class WarlordItem(
    @SerializedName("option")
    val option: String = "false"
)

data class FiltersXXX(
    @SerializedName("lvl")
    val lvl: Lvl
)

data class Lvl(
    @SerializedName("min")
    val min: Int = 0
)

data class FiltersXXXX(
    @SerializedName("links")
    val links: Links
)

data class Links(
    @SerializedName("min")
    val min: Int = 0
)