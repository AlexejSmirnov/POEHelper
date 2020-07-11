package com.resdev.poehelper.view.pop_up_window

import android.view.View
import com.resdev.poehelper.R
import com.resdev.poehelper.model.room.ItemEntity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_stats_info_window.view.*

object PopupItemInfoWindowSetuper {
    fun chooseWindow(item: ItemEntity, popupView: View):Boolean{
        when(item.itemType){
            "DeliriumOrb", "Incubator", "Scarab", "Fossil", "Resonator", "Vial", "Seed" -> {
                setupNormalItem(
                    item,
                    popupView
                )
                return true
            }
            "Essence"->{
                setupEssenceItem(
                    item,
                    popupView
                )
                return true
            }
            "Watchstone", "UniqueMap", "UniqueArmour","UniqueWeapon","UniqueJewel","UniqueFlask","UniqueAccessory" -> {
                setupUniqueItem(
                    item,
                    popupView
                )
                return true
            }
            "Prophecy" -> {
                setupProphecy(
                    item,
                    popupView
                )
                return true

            }
            else->{
                return false
            }
        }
    }

    fun setupNormalItem(item: ItemEntity, view: View){
        view.item_title.setBackgroundResource(R.drawable.normal_title_background)
        view.item_title.setTextColor(view?.resources?.getColor(R.color.normal_item)?:0)
        view.item_title.text = item.translatedName ?: item.name
        if (item.implicitModifiers.isNotEmpty()){
            for (i in item.implicitModifiers){
                view.item_prefix.text = view.item_prefix.text.toString()+ (i.translated?:i.text)+"\n"
            }
            if (view.item_prefix.text.isNotEmpty()){
                view.item_prefix.text = view.item_prefix.text.toString().substring(0, view.item_prefix.text.length-1)
                view.item_prefix.visibility= View.VISIBLE
                view.separate_string_1.visibility= View.VISIBLE
                view.separate_string_1.setImageDrawable(view.resources.getDrawable(R.drawable.normal_string))
            }

        }
        if (item.explicitModifiers.isNotEmpty()){
            for (i in item.explicitModifiers){
                view.item_suffix.text = view.item_suffix.text.toString()+(i.translated?:i.text)+"\n"
            }
            if (view.item_suffix.text.isNotEmpty()){view.item_suffix.text = view.item_suffix.text.toString().substring(0, view.item_suffix.text.length-1)}
            view.item_suffix.visibility= View.VISIBLE
            view.separate_string_2.visibility= View.VISIBLE
            view.separate_string_2.setImageDrawable(view.resources.getDrawable(R.drawable.normal_string))
        }
        if (item.flavourText?.isNotEmpty() == true){
            view.item_description.text = (item.translatedFlavourText?:item.flavourText)
            view.item_description.visibility = View.VISIBLE
            view.separate_string_3.visibility = View.VISIBLE
            view.separate_string_3.setImageDrawable(view.resources.getDrawable(R.drawable.normal_string))
        }
        Picasso.get().load(item.icon).into(view.item_image)
    }

    fun setupEssenceItem(item: ItemEntity, view: View){
        view.item_title.setBackgroundResource(R.drawable.normal_title_background)
        view.item_title.setTextColor(view?.resources?.getColor(R.color.normal_item)?:0)
        view.item_title.text = item.translatedName ?: item.name
        view.item_prefix.text = item.explicitModifiers[0].translated ?: item.explicitModifiers[0].text
        view.item_prefix.visibility= View.VISIBLE
        view.separate_string_1.visibility= View.VISIBLE
        view.separate_string_1.setImageDrawable(view.resources.getDrawable(R.drawable.normal_string))

        for (i in 2 until item.explicitModifiers.size){
            view.item_suffix.text = view.item_suffix.text.toString()+(item.explicitModifiers[i].translated ?: item.explicitModifiers[i].text)+"\n"
        }
        if (view.item_suffix.text.isNotEmpty()){
            view.item_suffix.text = view.item_suffix.text.toString().substring(0, view.item_suffix.text.length-1)
            view.item_suffix.visibility= View.VISIBLE
            view.separate_string_2.visibility= View.VISIBLE
            view.separate_string_2.setImageDrawable(view.resources.getDrawable(R.drawable.normal_string))
        }



        if (item.flavourText?.isNotEmpty() == true){
            view.item_description.text  = (item.translatedFlavourText?:item.flavourText)
            view.item_description.visibility = View.VISIBLE
            view.separate_string_3.visibility = View.VISIBLE
            view.separate_string_3.setImageDrawable(view.resources.getDrawable(R.drawable.normal_string))
        }
        Picasso.get().load(item.icon).into(view.item_image)
    }
    fun setupUniqueItem(item: ItemEntity, view: View){
        view.item_title.setBackgroundResource(R.drawable.unique_title_background)
        view.item_title.setTextColor(view?.resources?.getColor(R.color.unique_item)?:0)
        var links = if (item.links>0) {
            ", ${item.links}L"
        }
        else{
            ""
        }
        var type = (item.translatedType ?: item.baseType) ?: ""
        if (type.isNotEmpty()) {type = "\n$type"}
        view.item_title.text = (item.translatedName ?: item.name)+type+links
        if (item.implicitModifiers.isNotEmpty()){
            for (i in item.implicitModifiers){
                view.item_prefix.text = view.item_prefix.text.toString()+(i.translated?:i.text)+"\n"
            }
            if (view.item_prefix.text.isNotEmpty()){
                view.item_prefix.text = view.item_prefix.text.toString().substring(0, view.item_prefix.text.length-1)
                view.item_prefix.visibility= View.VISIBLE
                view.separate_string_1.visibility= View.VISIBLE
                view.separate_string_1.setImageDrawable(view.resources.getDrawable(R.drawable.unique_string))
            }

        }
        if (item.explicitModifiers.isNotEmpty()){
            for (i in item.explicitModifiers){
                view.item_suffix.text = view.item_suffix.text.toString()+(i.translated?:i.text)+"\n"
            }
            if (view.item_suffix.text.isNotEmpty()){
                view.item_suffix.text = view.item_suffix.text.toString().substring(0, view.item_suffix.text.length-1)
                view.item_suffix.visibility= View.VISIBLE
                view.separate_string_2.visibility= View.VISIBLE
                view.separate_string_2.setImageDrawable(view.resources.getDrawable(R.drawable.unique_string))
            }

        }
        if (item.flavourText?.isNotEmpty() == true){
            view.item_description.text = (item.translatedFlavourText?:item.flavourText)!!.replace("<default>", "")
            view.item_description.visibility = View.VISIBLE
            view.separate_string_3.visibility = View.VISIBLE
            view.separate_string_3.setImageDrawable(view.resources.getDrawable(R.drawable.unique_string))
        }
        Picasso.get().load(item.icon).into(view.item_image)
    }
    fun setupProphecy(item: ItemEntity, view: View){
        view.item_title.setBackgroundResource(R.drawable.prophecy_title_background)
        view.item_title.setTextColor(view?.resources?.getColor(R.color.prophecy_item)?:0)
        view.item_title.text = (item.translatedName ?: item.name)
        if (item.flavourText?.isNotEmpty() == true){
            view.item_description.text = item.translatedFlavourText?:item.flavourText
            view.item_description.visibility = View.VISIBLE
            view.separate_string_3.visibility = View.VISIBLE
            view.separate_string_3.setImageDrawable(view.resources.getDrawable(R.drawable.prophecy_string))
        }
        if (item.prophecyText!=null){
            view.item_prophecy.text =item.translatedProphecyText?:item.prophecyText
            view.item_prophecy.visibility = View.VISIBLE
            view.separate_string_4.visibility = View.VISIBLE
            view.separate_string_4.setImageDrawable(view.resources.getDrawable(R.drawable.prophecy_string))
        }
        Picasso.get().load(item.icon).into(view.item_image)
    }
}