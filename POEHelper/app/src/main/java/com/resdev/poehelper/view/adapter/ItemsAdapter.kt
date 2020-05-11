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
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.GridLabelRenderer
import com.resdev.poehelper.CurrentValue
import com.resdev.poehelper.R
import com.resdev.poehelper.Util
import com.resdev.poehelper.Util.getFromMap
import com.resdev.poehelper.Util.getLineGraphSeries
import com.resdev.poehelper.databinding.ItemViewHolderBinding
import com.resdev.poehelper.model.pojo.ItemLine
import com.resdev.poehelper.view.datawrappers.ItemLineUIWrapper
import com.resdev.poehelper.view.pop_up_window.PopupItemWindowSetuper
import com.resdev.poehelper.view.pop_up_window.showItemWindow
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_view_holder.view.*


class ItemsAdapter(val itemType: String) :
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
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        val buyingGraph = holder.itemView.findViewById<GraphView>(R.id.item_graph)
        buyingGraph.removeAllSeries()
        var series =  getLineGraphSeries(item.sparkline!!.data)
        Util.seriesConfig(series, holder.itemView)
        buyingGraph.addSeries(series)

        holder.itemView.setOnClickListener{onClickShowPopupWindow(item, holder.itemView) }
    }

    class ItemViewHolder(binding: ItemViewHolderBinding) : RecyclerView.ViewHolder(binding.root){
        lateinit var item : ItemLine
        var binding: ItemViewHolderBinding = binding
        init {
            setCurrencyImage()
            val buyingGraph = itemView.findViewById<GraphView>(R.id.item_graph)
            buyingGraph.gridLabelRenderer.isVerticalLabelsVisible = false
            buyingGraph.gridLabelRenderer.isHorizontalLabelsVisible = false
            buyingGraph.gridLabelRenderer.gridStyle = GridLabelRenderer.GridStyle.NONE
        }

        fun bind(itemLine: ItemLine) {
            item = itemLine
            binding.item = ItemLineUIWrapper(itemLine, binding.root.context)
            binding.executePendingBindings()
        }

        fun setCurrencyImage(){
            itemView.currency_name.text = getFromMap(CurrentValue.currencyDetail.name, CurrentValue.data.language.translations)
            Picasso.get().load(CurrentValue.currencyDetail.icon).into(itemView.currency_view)
        }


    }

    fun onClickShowPopupWindow(item: ItemLine, view: View?) {
        val popupView: View = LayoutInflater.from(view!!.context).inflate(R.layout.item_info_window, null)
        val width = LinearLayout.LayoutParams.WRAP_CONTENT
        val height = LinearLayout.LayoutParams.WRAP_CONTENT
        val focusable = true
        val popupWindow = PopupWindow(popupView, width, height, focusable)

        if (!PopupItemWindowSetuper.chooseWindow(item, itemType, popupView, item.itemsModel.language.translations)){
            return
        }
        this.popupWindow = popupWindow
        popupWindow.showItemWindow(view)
    }


    fun closeWindow(){
        popupWindow?.dismiss()
    }

}