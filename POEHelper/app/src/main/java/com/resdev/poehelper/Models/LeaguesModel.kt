package com.resdev.poehelper.Models


import com.google.gson.annotations.SerializedName

class LeaguesModel : ArrayList<LeaguesModel.LeaguesModelItem>(){

    data class LeaguesModelItem(

        @SerializedName("id")
        val id: String

    )

    fun getEditedLeagues():Array<String>{
        var newLeague = ""
        for (item in this){
            if (!item.id.contains("SSF") and !item.id.contains("Hardcore") and !item.id.contains("Standard")){
                newLeague=item.id

            }
        }
        return arrayOf(newLeague, "Hardcore $newLeague",  "Standard" , "Hardcore")

    }
}