package com.resdev.poehelper.Adapter
import android.content.Context
import android.view.*
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.recyclerview.widget.RecyclerView
import com.resdev.poehelper.CurrentValue
import com.resdev.poehelper.Models.CurrenciesModel
import com.resdev.poehelper.Models.CurrencyDetail
import com.resdev.poehelper.Models.CurrencyLine
import com.resdev.poehelper.R
import com.resdev.poehelper.Util
import com.resdev.poehelper.PopupWindows.PopupCurrencyWindowSetuper
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.currency_view_holder.view.*
import kotlinx.android.synthetic.main.item_view_holder.view.currency_name
import kotlinx.android.synthetic.main.item_view_holder.view.currency_view

class CurrenciesAdapter(private var currencies: CurrenciesModel) :
    RecyclerView.Adapter<CurrenciesAdapter.CurrencyViewHolder>(), MyAdapterInterface {
    var dataCopy = currencies.copy()
    lateinit var parent : ViewGroup
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        this.parent = parent
        val value = LayoutInflater.from(parent.context)
            .inflate(R.layout.currency_view_holder, parent, false)
        return CurrencyViewHolder(value)
    }

    override fun getItemCount(): Int {
        return dataCopy.lines.size
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        var currencyLine:CurrencyLine = dataCopy.lines[position]
        var currencyDetail:CurrencyDetail? = dataCopy.getDetails(currencyLine.currencyTypeName)
        holder.line = currencyDetail!!

        if (currencyLine.currencyTypeName == CurrentValue.line.currencyTypeName){
            holder.itemView.this_currency_name.text = Util.getFromMap("Chaos Orb", currencies.language.translations)
            holder.itemView.pay_text.text = "${formatValue(currencyLine.receive?.value)} " + holder.itemView.resources.getString(R.string.sell)
            holder.itemView.recieve_text.text = holder.itemView.resources.getString(R.string.buy)+" ${formatValue(currencyLine.pay?.value)}"
            Picasso.get().load("https://web.poecdn.com/image/Art/2DItems/Currency/CurrencyRerollRare.png?scale=1&w=1&h=1").into(holder.itemView.this_currency_view)
        }
        else{
            holder.itemView.this_currency_name.text = Util.getFromMap(currencyLine.currencyTypeName, currencies.language.translations)
            holder.itemView.pay_text.text =  " ${formatBuyValueRatio(currencyLine.pay?.value)} "+holder.itemView.resources.getString(R.string.sell)
            holder.itemView.recieve_text.text = holder.itemView.resources.getString(R.string.buy)+" ${formatSellValueRatio(currencyLine.receive?.value)}"
            Picasso.get().load(currencyDetail?.icon).into(holder.itemView.this_currency_view)
            holder.itemView.setOnClickListener{onClickShowPopupWindow(currencyLine, currencyDetail!!, this.parent) }
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
                if (Util.getFromMap(item.currencyTypeName, currencies.language.translations).toLowerCase().contains(text)) {
                    elements.add(item)
                }
                else if (item.currencyTypeName == CurrentValue.line.currencyTypeName && Util.getFromMap("Chaos Orb", currencies.language.translations).toLowerCase().contains(text)) {
                    elements.add(item)
                }

            }
            dataCopy.lines = List(elements.size){i -> elements[i] }
        }
        notifyDataSetChanged()
    }

    class CurrencyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        lateinit var line : CurrencyDetail
        init {

            itemView.currency_name.text = Util.getFromMap(CurrentValue.currencyDetail.name, CurrentValue.data.language.translations)
            Picasso.get().load(CurrentValue.currencyDetail.icon).into(itemView.currency_view)
        }

    }
    fun onClickShowPopupWindow(currencyLine: CurrencyLine, currencyDetails: CurrencyDetail, view: View?) {
        val popupView: View = LayoutInflater.from(parent.context).inflate(R.layout.currency_info_window, null)
        val width = LinearLayout.LayoutParams.WRAP_CONTENT
        val height = LinearLayout.LayoutParams.WRAP_CONTENT
        val focusable = true
        val popupWindow = PopupWindow(popupView, width, height, focusable)

        PopupCurrencyWindowSetuper.setupWindow(currencyLine, currencyDetails, popupView)

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
        popupWindow.dimBehind()
        popupView.setOnClickListener{
            popupWindow.dismiss()
            true
        }
    }

    fun formatBuyValueRatio(value:Double?):String{
        if (value == null){
            return parent.resources.getString(R.string.no_data)
        }
        return "%.2f".format(value*CurrentValue.line.chaosEquivalent!!)
    }
    fun formatSellValueRatio(value:Double?):String{
        if (value == null){
            return parent.resources.getString(R.string.no_data)
        }
        return "%.2f".format(value/CurrentValue.line.chaosEquivalent!!)
    }

    fun formatValue(value:Double?):String{
        if (value == null){
            return parent.resources.getString(R.string.no_data)
        }
        return "%.2f".format(value)
    }

    fun PopupWindow.dimBehind() {
        val container = contentView.rootView
        val context = contentView.context
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val p = container.layoutParams as WindowManager.LayoutParams
        p.flags = p.flags or WindowManager.LayoutParams.FLAG_DIM_BEHIND
        p.dimAmount = 0.5f
        wm.updateViewLayout(container, p)
    }


}