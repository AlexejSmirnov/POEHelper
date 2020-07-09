package com.resdev.poehelper.view.adapter.callbacks

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_SWIPE
import androidx.recyclerview.widget.RecyclerView
import com.resdev.poehelper.view.adapter.CurrenciesAdapter
import com.resdev.poehelper.model.Config
import com.resdev.poehelper.model.CurrentValue
import com.resdev.poehelper.utils.Util
import com.resdev.poehelper.model.retrofit.PoeMarket
import com.resdev.poehelper.utils.ColorsUtil.isColorLight
import com.resdev.poehelper.utils.URLUtils.generatePoeMarketExchangeUrl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.abs


class SwipeCurrencyCallback() : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
    }


    var swipeBack = false
    override fun convertToAbsoluteDirection(flags: Int, layoutDirection: Int): Int {
        if (swipeBack) {
            swipeBack = false
            return 0
        }
        return super.convertToAbsoluteDirection(flags, layoutDirection)
    }

    private fun setTouchListener(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float, dY: Float,
        actionState: Int, isCurrentlyActive: Boolean
    ) {
        recyclerView.setOnTouchListener { v, event ->
            swipeBack = event.action == MotionEvent.ACTION_CANCEL || event.action == MotionEvent.ACTION_UP
            if (willActionBeTriggered(dX, recyclerView.width) and swipeBack) {//
                makeAction(viewHolder)
            }
            false
        }
    }



    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(
            c!!, recyclerView!!, viewHolder, dX,
            dY, actionState, isCurrentlyActive
        )
        val itemView = viewHolder.itemView
        if (actionState == ACTION_STATE_SWIPE) {
            val paint = Paint()
            if (willActionBeTriggered(dX, recyclerView.width)){
                if (!isColorLight(Integer.toHexString(Config.color))){
                    paint.color = Config.color
                }
                else{
                    paint.color = Color.BLACK
                }

            }
            else{
                paint.setColor(Color.GRAY)
            }

            paint.textSize = 40F
            paint.textAlign = Paint.Align.CENTER
            setTouchListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            var height = itemView.getTop() + itemView.getHeight() / 2
            c.drawText("Check on", (itemView.getRight() - 150).toFloat(), (height-20).toFloat(), paint)
            c.drawText("pathofexile", (itemView.getRight() - 150).toFloat(), (height+20).toFloat(), paint)
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    private fun willActionBeTriggered(dX: Float, viewWidth: Int): Boolean {
        return abs(dX) >= viewWidth / 3.5
    }

    fun makeAction(viewHolder: RecyclerView.ViewHolder){
        GlobalScope.launch {
            var holder= viewHolder as CurrenciesAdapter.CurrencyViewHolder
            var link = PoeMarket.sendCurrencyRequest(
                Config.league,
                holder.line.tradeId?:"", CurrentValue.currencyDetail.tradeId?:"")
            if (link==null){
                withContext(Dispatchers.Main){
                    Util.showInternetConnectionError(viewHolder.itemView)
                }
                return@launch
            }
            withContext(Dispatchers.Main){
                Util.openBrowserWindowByUrl(viewHolder.itemView.context, generatePoeMarketExchangeUrl()+"/${link?.id}")
            }

        }
    }


}