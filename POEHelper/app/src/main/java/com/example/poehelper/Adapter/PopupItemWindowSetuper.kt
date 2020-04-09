package com.example.poehelper.Adapter

import android.view.View
import com.example.poehelper.Models.ItemLine
import com.example.poehelper.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_info_window.view.*

object PopupItemWindowSetuper {
    fun chooseWindow(item:ItemLine, itemType:String, popupView: View ):Boolean{
        when(itemType){
            "Delirium Orb", "Oil", "Incubator", "Scarab", "Fossil", "Resonator", "Vial" -> {
                setupNormalItem(item, popupView)
                return true
            }
            "Essence"->{
                setupEssenceItem(item, popupView)
                return true
            }
            "Watchstone", "Unique Map", "Unique Armour","Unique Weapon","Unique Jewel","Unique Flask","Unique Accessory" -> {
                setupUniqueItem(item, popupView)
                return true
            }
            "Prophecy" -> {
                setupProphecy(item, popupView)
                return true

            }
            else->{
                return false
            }
        }
    }
    fun setupNormalItem(item: ItemLine, view: View){
        view.item_title.setBackgroundResource(R.drawable.normal_title_background)
        view.item_title.setTextColor(view?.resources?.getColor(R.color.normal_item)?:0)
        view.item_title.setText(item.name)
        if (item.implicitModifiers.isNotEmpty()){
            for (i in item.implicitModifiers){
                view.item_prefix.text = view.item_prefix.text.toString()+i.text+"\n"
            }
            view.item_prefix.text = view.item_prefix.text.toString().substring(0, view.item_prefix.text.length-1)
            view.item_prefix.visibility= View.VISIBLE
            view.separate_string_1.visibility= View.VISIBLE
            view.separate_string_1.setImageDrawable(view.resources.getDrawable(R.drawable.normal_string))
        }
        if (item.explicitModifiers.isNotEmpty()){
            for (i in item.explicitModifiers){
                view.item_suffix.text = view.item_suffix.text.toString()+i.text+"\n"
            }
            view.item_suffix.text = view.item_suffix.text.toString().substring(0, view.item_suffix.text.length-1)

            view.item_suffix.visibility= View.VISIBLE
            view.separate_string_2.visibility= View.VISIBLE
            view.separate_string_2.setImageDrawable(view.resources.getDrawable(R.drawable.normal_string))
        }
        if (item.flavourText.isNotEmpty()){
            view.item_description.text = item.flavourText
            view.item_description.visibility = View.VISIBLE
            view.separate_string_3.visibility = View.VISIBLE
            view.separate_string_3.setImageDrawable(view.resources.getDrawable(R.drawable.normal_string))
        }
        Picasso.get().load(item.icon).into(view.item_image)
    }
    fun setupEssenceItem(item: ItemLine, view: View){
        view.item_title.setBackgroundResource(R.drawable.normal_title_background)
        view.item_title.setTextColor(view?.resources?.getColor(R.color.normal_item)?:0)
        view.item_title.setText(item.name)
        view.item_prefix.text = item.explicitModifiers[0].text
        view.item_prefix.visibility= View.VISIBLE
        view.separate_string_1.visibility= View.VISIBLE
        view.separate_string_1.setImageDrawable(view.resources.getDrawable(R.drawable.normal_string))


        for (i in 2 until item.explicitModifiers.size){
            view.item_suffix.text = view.item_suffix.text.toString()+item.explicitModifiers[i].text+"\n"
        }
        view.item_suffix.text = view.item_suffix.text.toString().substring(0, view.item_suffix.text.length-1)

        view.item_suffix.visibility= View.VISIBLE
        view.separate_string_2.visibility= View.VISIBLE
        view.separate_string_2.setImageDrawable(view.resources.getDrawable(R.drawable.normal_string))

        if (item.flavourText.isNotEmpty()){
            view.item_description.text = item.flavourText
            view.item_description.visibility = View.VISIBLE
            view.separate_string_3.visibility = View.VISIBLE
            view.separate_string_3.setImageDrawable(view.resources.getDrawable(R.drawable.normal_string))
        }
        Picasso.get().load(item.icon).into(view.item_image)
    }
    fun setupUniqueItem(item: ItemLine, view: View){
        view.item_title.setBackgroundResource(R.drawable.unique_title_background)
        view.item_title.setTextColor(view?.resources?.getColor(R.color.unique_item)?:0)
        var links = if (item.links>0) {
            ", ${item.links}L"
        }
        else{
            ""
        }
        var type = item.baseType ?: ""
        view.item_title.setText(item.name+"\n"+type+links)
        if (item.implicitModifiers.isNotEmpty()){
            for (i in item.implicitModifiers){
                view.item_prefix.text = view.item_prefix.text.toString()+i.text+"\n"
            }
            view.item_prefix.text = view.item_prefix.text.toString().substring(0, view.item_prefix.text.length-1)
            view.item_prefix.visibility= View.VISIBLE
            view.separate_string_1.visibility= View.VISIBLE
            view.separate_string_1.setImageDrawable(view.resources.getDrawable(R.drawable.unique_string))
        }
        if (item.explicitModifiers.isNotEmpty()){
            for (i in item.explicitModifiers){
                view.item_suffix.text = view.item_suffix.text.toString()+i.text+"\n"
            }
            view.item_suffix.text = view.item_suffix.text.toString().substring(0, view.item_suffix.text.length-1)
            view.item_suffix.visibility= View.VISIBLE
            view.separate_string_2.visibility= View.VISIBLE
            view.separate_string_2.setImageDrawable(view.resources.getDrawable(R.drawable.unique_string))
        }
        if (item.flavourText.isNotEmpty()){
            view.item_description.text = item.flavourText.replace("<default>", "")
            view.item_description.visibility = View.VISIBLE
            view.separate_string_3.visibility = View.VISIBLE
            view.separate_string_3.setImageDrawable(view.resources.getDrawable(R.drawable.unique_string))
        }
        Picasso.get().load(item.icon).into(view.item_image)
    }
    fun setupProphecy(item: ItemLine, view: View){
        view.item_title.setBackgroundResource(R.drawable.prophecy_title_background)
        view.item_title.setTextColor(view?.resources?.getColor(R.color.prophecy_item)?:0)
        view.item_title.setText(item.name+"\n"+item.baseType)
        if (item.flavourText!=null){
            view.item_description.text = item.flavourText
            view.item_description.visibility = View.VISIBLE
            view.separate_string_3.visibility = View.VISIBLE
            view.separate_string_3.setImageDrawable(view.resources.getDrawable(R.drawable.prophecy_string))
        }
        if (item.prophecyText!=null){
            view.item_prophecy.text = item.prophecyText.toString()
            view.item_prophecy.visibility = View.VISIBLE
            view.separate_string_4.visibility = View.VISIBLE
            view.separate_string_4.setImageDrawable(view.resources.getDrawable(R.drawable.prophecy_string))
        }
        Picasso.get().load(item.icon).into(view.item_image)
    }
}