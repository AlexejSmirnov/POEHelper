package com.example.poehelper.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.poehelper.CurrentValue
import com.example.poehelper.Models.CurrenciesModel
import com.example.poehelper.Models.CurrencyDetail
import com.example.poehelper.Models.CurrencyLine
import com.example.poehelper.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.currency_view_holder.view.*
import kotlinx.android.synthetic.main.item_view_holder.view.currency_name
import kotlinx.android.synthetic.main.item_view_holder.view.currency_view

class CurrenciesAdapter(private var currencies: CurrenciesModel) :
    RecyclerView.Adapter<CurrenciesAdapter.ItemViewHolder>(), MyAdapterInterface {
    var dataCopy = currencies.copy()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val value = LayoutInflater.from(parent.context)
            .inflate(R.layout.currency_view_holder, parent, false)
        return ItemViewHolder(value)
    }

    override fun getItemCount(): Int {
        return dataCopy.lines.size
    }
    val formatVal = "%.6f"

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        var currencyLine:CurrencyLine = dataCopy.lines[position]
        var currencyDetail:CurrencyDetail? = dataCopy.getDetails(currencyLine.currencyTypeName)


        if (currencyLine.currencyTypeName == CurrentValue.line.currencyTypeName){
            holder.itemView.this_currency_name.text = "Chaos Orb"
            holder.itemView.pay_text.text = "${formatValue(currencyLine.receive?.value)} " + holder.itemView.resources.getString(R.string.sell)
            holder.itemView.recieve_text.text = holder.itemView.resources.getString(R.string.buy)+" ${formatValue(currencyLine.pay?.value)}"
            Picasso.get().load("https://web.poecdn.com/image/Art/2DItems/Currency/CurrencyRerollRare.png?scale=1&w=1&h=1").into(holder.itemView.this_currency_view)
        }
        else{
            holder.itemView.this_currency_name.text = currencyLine.currencyTypeName
            holder.itemView.pay_text.text =  " ${formatWithValueRatio(currencyLine.pay?.value)} "+holder.itemView.resources.getString(R.string.sell)
            holder.itemView.recieve_text.text = holder.itemView.resources.getString(R.string.buy)+" ${formatWithValueRatio(currencyLine.receive?.value)}"
            Picasso.get().load(currencyDetail?.icon).into(holder.itemView.this_currency_view)
        }

    }
    override fun filter(text: String) {
        var text = text
        if (text.isEmpty()) {
            dataCopy = currencies.copy()
        } else {
            text = text.toLowerCase()

            var elements = ArrayList<CurrencyLine>()
            for (item in currencies.lines) {
                if (item.currencyTypeName.toLowerCase().contains(text)) {
                    elements.add(item)
                }
                else if (item.currencyTypeName == CurrentValue.line.currencyTypeName && "chaos orb".contains(text)) {
                    elements.add(item)
                }

            }
            dataCopy.lines = List<CurrencyLine>(elements.size){i -> elements.get(i)}
        }
        notifyDataSetChanged()
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        init {
            itemView.currency_name.text = CurrentValue.currencyDetail.name
            Picasso.get().load(CurrentValue.currencyDetail.icon).into(itemView.currency_view)
        }

    }

    fun formatWithValueRatio(value:Double?):String{
        if (value == null){
            return "no data"
        }
        return "%.2f".format(value/CurrentValue.line.chaosEquivalent!!)
    }

    fun formatValue(value:Double?):String{
        if (value == null){
            return "no data"
        }
        return "%.2f".format(value)
    }


}