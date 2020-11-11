package com.resdev.poehelper.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.resdev.poehelper.model.CurrentValue
import com.resdev.poehelper.R
import com.resdev.poehelper.utils.Util.getFromMap
import com.resdev.poehelper.databinding.ItemViewHolderBinding
import com.resdev.poehelper.model.Converter
import com.resdev.poehelper.model.pojo.ItemLine
import com.resdev.poehelper.view.datawrappers.ItemLineUIWrapper
import com.resdev.poehelper.view.pop_up_window.PopupItemInfoWindowSetuper
import com.resdev.poehelper.view.pop_up_window.PopupItemWindowSetuper
import com.resdev.poehelper.view.pop_up_window.showCurrencyWindow
import com.resdev.poehelper.view.pop_up_window.showItemWindow
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_view_holder.view.*
import kotlinx.coroutines.*


class ItemsAdapter(private val itemType: String) :
    ListAdapter<ItemLine, ItemsAdapter.ItemViewHolder>(DIFF_CALLBACK) {

    companion object{
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<ItemLine> =
            object : DiffUtil.ItemCallback<ItemLine>() {
                override fun areItemsTheSame(oldItem: ItemLine, newItem: ItemLine): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: ItemLine, newItem: ItemLine): Boolean {
                    return oldItem.chaosValue == newItem.chaosValue && oldItem.getTranslatedName() == newItem.getTranslatedName()
                }
            }
    }

    var popupWindow: PopupWindow? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemViewHolderBinding>(layoutInflater, R.layout.item_view_holder, parent, false);
        return ItemViewHolder(binding, itemType)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener{onClickShowPopupWindow(item, holder.itemView) }
        holder.itemView.setOnLongClickListener{onLongClickShowPopupWindow(item, holder.itemView) }
    }

    class ItemViewHolder(binding: ItemViewHolderBinding, val itemType: String) : RecyclerView.ViewHolder(binding.root){
        lateinit var item : ItemLine
        var binding: ItemViewHolderBinding = binding
        init {
            setViewDefaults()
        }

        fun bind(itemLine: ItemLine) {
            item = itemLine
            binding.item = ItemLineUIWrapper(itemLine, binding.root.context)
            binding.executePendingBindings()
        }

        fun setViewDefaults(){
             itemView.currency_name.text = getFromMap(CurrentValue.getDetails().name, CurrentValue.getData().language.translations)
                    Picasso.get().load(CurrentValue.getDetails().icon).into(itemView.currency_view)
        }


    }

    fun onLongClickShowPopupWindow(item: ItemLine, view: View?):Boolean {
        val popupView: View = LayoutInflater.from(view!!.context).inflate(R.layout.item_stats_info_window, null)
        val width = LinearLayout.LayoutParams.WRAP_CONTENT
        val height = LinearLayout.LayoutParams.WRAP_CONTENT
        val focusable = true
        val popupWindow = PopupWindow(popupView, width, height, focusable)

        if (!PopupItemInfoWindowSetuper.chooseWindow(Converter.fromRetrofitItemToRoomEntity(item, itemType), popupView)){
            return true
        }
        this.popupWindow = popupWindow
        popupWindow.showItemWindow(view)
        return true
    }

    fun onClickShowPopupWindow(item: ItemLine, view: View?):Boolean{
        val popupView: View = LayoutInflater.from(view!!.context).inflate(R.layout.item_info_window, null)
        val width = LinearLayout.LayoutParams.WRAP_CONTENT
        val height = LinearLayout.LayoutParams.WRAP_CONTENT
        val focusable = true
        val popupWindow = PopupWindow(popupView, width, height, focusable)

        PopupItemWindowSetuper.setupWindow(Converter.fromRetrofitItemToRoomEntity(item, itemType), popupView)
        this.popupWindow = popupWindow
        popupWindow.showCurrencyWindow(view)
        return true
    }


    fun closeWindow(){
        popupWindow?.dismiss()
    }

}