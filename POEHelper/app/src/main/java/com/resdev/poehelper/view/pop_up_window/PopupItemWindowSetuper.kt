package com.resdev.poehelper.view.pop_up_window

import android.view.View
import com.resdev.poehelper.model.pojo.ItemLine
import com.resdev.poehelper.R
import com.resdev.poehelper.Util
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_info_window.view.*

object PopupItemWindowSetuper {
    fun chooseWindow(item: ItemLine, itemType:String, popupView: View, translations: HashMap<String, String>):Boolean{
        when(itemType){
            "DeliriumOrb", "Incubator", "Scarab", "Fossil", "Resonator", "Vial" -> {
                setupNormalItem(
                    item,
                    popupView,
                    translations
                )
                return true
            }
            "Essence"->{
                setupEssenceItem(
                    item,
                    popupView,
                    translations
                )
                return true
            }
            "Watchstone", "Unique Map", "UniqueArmour","UniqueWeapon","UniqueJewel","UniqueFlask","UniqueAccessory" -> {
                setupUniqueItem(
                    item,
                    popupView,
                    translations
                )
                return true
            }
            "Prophecy" -> {
                setupProphecy(
                    item,
                    popupView,
                    translations
                )
                return true

            }
            else->{
                return false
            }
        }
    }
    fun setupNormalItem(item: ItemLine, view: View, translations: HashMap<String, String>){
        view.item_title.setBackgroundResource(R.drawable.normal_title_background)
        view.item_title.setTextColor(view?.resources?.getColor(R.color.normal_item)?:0)
        view.item_title.text = Util.getFromMap(item.name, translations)
        if (item.implicitModifiers.isNotEmpty()){
            for (i in item.implicitModifiers){
                view.item_prefix.text = view.item_prefix.text.toString()+Util.getFromMap(i.text, translations)+"\n"
            }
            view.item_prefix.text = view.item_prefix.text.toString().substring(0, view.item_prefix.text.length-1)
            view.item_prefix.visibility= View.VISIBLE
            view.separate_string_1.visibility= View.VISIBLE
            view.separate_string_1.setImageDrawable(view.resources.getDrawable(R.drawable.normal_string))
        }
        if (item.explicitModifiers.isNotEmpty()){
            for (i in item.explicitModifiers){
                view.item_suffix.text = view.item_suffix.text.toString()+Util.getFromMap(i.text, translations)+"\n"
            }
            view.item_suffix.text = view.item_suffix.text.toString().substring(0, view.item_suffix.text.length-1)

            view.item_suffix.visibility= View.VISIBLE
            view.separate_string_2.visibility= View.VISIBLE
            view.separate_string_2.setImageDrawable(view.resources.getDrawable(R.drawable.normal_string))
        }
        if (item.flavourText.isNotEmpty()){
            view.item_description.text = Util.getFromMap(item.flavourText, translations)
            view.item_description.visibility = View.VISIBLE
            view.separate_string_3.visibility = View.VISIBLE
            view.separate_string_3.setImageDrawable(view.resources.getDrawable(R.drawable.normal_string))
        }
        Picasso.get().load(item.icon).into(view.item_image)
    }


    fun setupEssenceItem(item: ItemLine, view: View, translations: HashMap<String, String>){
        view.item_title.setBackgroundResource(R.drawable.normal_title_background)
        view.item_title.setTextColor(view?.resources?.getColor(R.color.normal_item)?:0)
        view.item_title.text = Util.getFromMap(item.name, translations)
        view.item_prefix.text = Util.getFromMap(item.explicitModifiers[0].text, translations)
        view.item_prefix.visibility= View.VISIBLE
        view.separate_string_1.visibility= View.VISIBLE
        view.separate_string_1.setImageDrawable(view.resources.getDrawable(R.drawable.normal_string))

        for (i in 2 until item.explicitModifiers.size){
            view.item_suffix.text = view.item_suffix.text.toString()+Util.getFromMapForEssence(item.explicitModifiers[i].text, translations)+"\n"
        }
        view.item_suffix.text = view.item_suffix.text.toString().substring(0, view.item_suffix.text.length-1)

        view.item_suffix.visibility= View.VISIBLE
        view.separate_string_2.visibility= View.VISIBLE
        view.separate_string_2.setImageDrawable(view.resources.getDrawable(R.drawable.normal_string))

        if (item.flavourText.isNotEmpty()){
            view.item_description.text = Util.getFromMap(item.flavourText, translations)
            view.item_description.visibility = View.VISIBLE
            view.separate_string_3.visibility = View.VISIBLE
            view.separate_string_3.setImageDrawable(view.resources.getDrawable(R.drawable.normal_string))
        }
        Picasso.get().load(item.icon).into(view.item_image)
    }
    fun setupUniqueItem(item: ItemLine, view: View, translations: HashMap<String, String>){
        view.item_title.setBackgroundResource(R.drawable.unique_title_background)
        view.item_title.setTextColor(view?.resources?.getColor(R.color.unique_item)?:0)
        var links = if (item.links>0) {
            ", ${item.links}L"
        }
        else{
            ""
        }
        var type = item.baseType ?: ""
        view.item_title.text = Util.getFromMap(item.name, translations)+"\n"+Util.getFromMap(type, translations)+links
        if (item.implicitModifiers.isNotEmpty()){
            for (i in item.implicitModifiers){
                view.item_prefix.text = view.item_prefix.text.toString()+Util.getFromMap(i.text, translations)+"\n"
            }
            view.item_prefix.text = view.item_prefix.text.toString().substring(0, view.item_prefix.text.length-1)
            view.item_prefix.visibility= View.VISIBLE
            view.separate_string_1.visibility= View.VISIBLE
            view.separate_string_1.setImageDrawable(view.resources.getDrawable(R.drawable.unique_string))
        }
        if (item.explicitModifiers.isNotEmpty()){
            for (i in item.explicitModifiers){
                view.item_suffix.text = view.item_suffix.text.toString()+Util.getFromMap(i.text, translations)+"\n"
            }
            view.item_suffix.text = view.item_suffix.text.toString().substring(0, view.item_suffix.text.length-1)
            view.item_suffix.visibility= View.VISIBLE
            view.separate_string_2.visibility= View.VISIBLE
            view.separate_string_2.setImageDrawable(view.resources.getDrawable(R.drawable.unique_string))
        }
        if (item.flavourText.isNotEmpty()){
            view.item_description.text = Util.getFromMap(item.flavourText, translations).replace("<default>", "")
            view.item_description.visibility = View.VISIBLE
            view.separate_string_3.visibility = View.VISIBLE
            view.separate_string_3.setImageDrawable(view.resources.getDrawable(R.drawable.unique_string))
        }
        Picasso.get().load(item.icon).into(view.item_image)
    }
    fun setupProphecy(item: ItemLine, view: View, translations: HashMap<String, String>){
        view.item_title.setBackgroundResource(R.drawable.prophecy_title_background)
        view.item_title.setTextColor(view?.resources?.getColor(R.color.prophecy_item)?:0)
        view.item_title.text = Util.getFromMap(item.name, translations)+"\n"+Util.getFromMap(item.baseType ?: "", translations)
        if (item.flavourText!=null){
            view.item_description.text = Util.getFromMap( item.flavourText.toString(), translations)
            view.item_description.visibility = View.VISIBLE
            view.separate_string_3.visibility = View.VISIBLE
            view.separate_string_3.setImageDrawable(view.resources.getDrawable(R.drawable.prophecy_string))
        }
        if (item.prophecyText!=null){
            view.item_prophecy.text =Util.getFromMap( item.prophecyText.toString(), translations)
            view.item_prophecy.visibility = View.VISIBLE
            view.separate_string_4.visibility = View.VISIBLE
            view.separate_string_4.setImageDrawable(view.resources.getDrawable(R.drawable.prophecy_string))
        }
        Picasso.get().load(item.icon).into(view.item_image)
    }
}