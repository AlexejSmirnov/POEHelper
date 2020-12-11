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
import com.resdev.poehelper.MyApplication
import com.resdev.poehelper.R
import com.resdev.poehelper.databinding.CurrencyViewHolderBinding
import com.resdev.poehelper.model.pojo.CurrencyDetail
import com.resdev.poehelper.model.pojo.CurrencyLine
import com.resdev.poehelper.utils.getFromMap
import com.resdev.poehelper.view.datawrappers.CurrencyLineUiWrapper
import com.resdev.poehelper.view.pop_up_window.setupWindow
import com.resdev.poehelper.view.pop_up_window.showCurrencyWindow
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_view_holder.view.*


class CurrenciesAdapter :
    ListAdapter<CurrencyLine, CurrenciesAdapter.CurrencyViewHolder>(DIFF_CALLBACK) {
    companion object{
        val currentValue = MyApplication.getCurrentValue()
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<CurrencyLine> =
            object : DiffUtil.ItemCallback<CurrencyLine>() {
                override fun areItemsTheSame(oldItem: CurrencyLine, newItem: CurrencyLine): Boolean {
                   return oldItem.currencyTypeName == newItem.currencyTypeName

                }

                override fun areContentsTheSame(oldItem: CurrencyLine, newItem: CurrencyLine): Boolean {
                  return oldItem.chaosEquivalent == newItem.chaosEquivalent
                          && oldItem.getTranslatedName() == newItem.getTranslatedName()

                }
            }
    }

    var popupWindow: PopupWindow? = null

    override fun getItemViewType(position: Int) = R.layout.currency_view_holder

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        var item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener{onClickShowPopupWindow(item, holder.itemView) }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<CurrencyViewHolderBinding>(layoutInflater, R.layout.currency_view_holder, parent, false);
        return CurrencyViewHolder(binding)
    }



    class CurrencyViewHolder(private val binding: CurrencyViewHolderBinding) : RecyclerView.ViewHolder(binding.root){
        lateinit var line : CurrencyDetail

        init {
            setViewDefaults()
        }
        fun bind(currencyLine: CurrencyLine) {
            line  = currencyLine.model.getDetails(currencyLine.currencyTypeName)!!
            binding.currency = CurrencyLineUiWrapper(currencyLine, binding.root.context)
            binding.executePendingBindings()
        }
        fun setViewDefaults(){
                    itemView.currency_name.text = getFromMap(currentValue.getDetails().name, currentValue.getData().language.translations)
                    Picasso.get().load(currentValue.getDetails().icon).into(itemView.currency_view)

        }

    }
    fun onClickShowPopupWindow(currencyLine: CurrencyLine, view: View?) {
        if (currencyLine.currencyTypeName== currentValue.getLine().currencyTypeName){
            return
        }
        val popupView: View = LayoutInflater.from(view!!.context).inflate(R.layout.currency_info_window, null)
        val width = LinearLayout.LayoutParams.WRAP_CONTENT
        val height = LinearLayout.LayoutParams.WRAP_CONTENT
        val focusable = true
        val popupWindow = PopupWindow(popupView, width, height, focusable)
        setupWindow(currencyLine, popupView)
        this.popupWindow = popupWindow
        popupWindow.showCurrencyWindow(view)

    }

    fun closeWindow(){
        popupWindow?.dismiss()
    }







}