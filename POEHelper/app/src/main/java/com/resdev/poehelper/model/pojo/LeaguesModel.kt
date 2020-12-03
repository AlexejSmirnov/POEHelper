package com.resdev.poehelper.model.pojo


import com.google.gson.annotations.SerializedName

//this class contains all leagues.
class LeaguesModel : ArrayList<LeaguesModel.LeaguesModelItem>(){

    data class LeaguesModelItem(
        @SerializedName("id")
        val id: String
    )
    //method filter all solo leagues and left only default and last leagues
    fun getEditedLeagues():Array<String>?{
        var newLeague = ""
        for (item in this){
            if (!item.id.contains("SSF") and !item.id.contains("Hardcore") and !item.id.contains("Standard") and !item.id.contains("(")){
                newLeague=item.id

            }
        }
        if(this.isEmpty()){return null}
        if (newLeague=="") return arrayOf("Standard" , "Hardcore")
        return arrayOf(newLeague, "Hardcore $newLeague",  "Standard" , "Hardcore")

    }

    companion object{
        val defaultModel = LeaguesModel()
    }
}