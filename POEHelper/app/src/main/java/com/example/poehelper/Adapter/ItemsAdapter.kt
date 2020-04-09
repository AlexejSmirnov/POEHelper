package com.example.poehelper.Adapter



import android.view.*
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import com.example.poehelper.CurrentValue
import com.example.poehelper.Models.ItemLine
import com.example.poehelper.Models.ItemsModel
import com.example.poehelper.R
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.GridLabelRenderer
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_view_holder.view.*


class ItemsAdapter(private val itemsType:String, private val items: ItemsModel) :
    RecyclerView.Adapter<ItemsAdapter.ItemViewHolder>(),MyAdapterInterface {
    var dataCopy = items.copy()

    lateinit var parent : ViewGroup
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        this.parent = parent
        val value = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view_holder, parent, false)
        return ItemViewHolder(value)
    }

    override fun getItemCount(): Int {
        return dataCopy.lines.size
    }
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataCopy.lines[position]

        holder.itemView.item_name.text = item.name
        holder.itemView.equivalent_text.text = holder.itemView.resources.getString( R.string.string_for) +" ${formatValue(item.chaosValue)}"
        Picasso.get().load(item.icon).into(holder.itemView.this_item_view)
        holder.itemView.setOnClickListener{onClickShowPopupWindow(item, this.parent) }

        val buyingGraph = holder.itemView.findViewById<GraphView>(R.id.item_graph)
        buyingGraph.removeAllSeries()
        buyingGraph.addSeries(getLineGraphSeries(item.sparkline!!.data))
        buyingGraph.gridLabelRenderer.isVerticalLabelsVisible = false
        buyingGraph.gridLabelRenderer.isHorizontalLabelsVisible = false
        buyingGraph.gridLabelRenderer.gridStyle = GridLabelRenderer.GridStyle.NONE
        holder.itemView.findViewById<TextView>(R.id.item_percents).text =
            (item.sparkline?.totalChange?.toString() ?: "0")+"%"
        if ((item.sparkline?.totalChange ?: 0.0)>=0.0){
            holder.itemView.findViewById<TextView>(R.id.item_percents).setTextColor(holder.itemView.resources.getColor(R.color.green))
        }
        else{
            holder.itemView.findViewById<TextView>(R.id.item_percents).setTextColor(holder.itemView.resources.getColor(R.color.red))
        }

    }
    override fun filter(text: String) {
        var text = text
        if (text.isEmpty()) {
            dataCopy = items.copy()
        } else {
            text = text.toLowerCase()

            var elements = ArrayList<ItemLine>()
            for (item in items.lines) {
                if (item.name.toLowerCase().contains(text) or (item.baseType ?: "").toLowerCase().contains(text)) {
                    elements.add(item)
                }
            }
            dataCopy.lines = List<ItemLine>(elements.size){ i -> elements.get(i)}
        }
        notifyDataSetChanged()
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        init {
            itemView.currency_name.text = CurrentValue.currencyDetail.name
            Picasso.get().load(CurrentValue.currencyDetail.icon).into(itemView.currency_view)
        }

    }

    fun formatValue(value:Double?):String{
        if (value == null){
            return "no data"
        }
        return "%.2f".format(value/CurrentValue.line.chaosEquivalent!!)
    }


    fun onClickShowPopupWindow(item:ItemLine, view: View?) {
        val popupView: View = LayoutInflater.from(parent.context).inflate(R.layout.item_info_window, null)
        val width = LinearLayout.LayoutParams.WRAP_CONTENT
        val height = LinearLayout.LayoutParams.WRAP_CONTENT
        val focusable = true
        val popupWindow = PopupWindow(popupView, width, height, focusable)

        if (!PopupItemWindowSetuper.chooseWindow(item, itemsType, popupView)){
            return
        }
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)

//        popupView.setOnLongClickListener(){ v, event ->
//            popupWindow.dismiss()
//            true
//        }
        popupView.setOnLongClickListener{
            popupWindow.dismiss()
            true
        }
    }





}