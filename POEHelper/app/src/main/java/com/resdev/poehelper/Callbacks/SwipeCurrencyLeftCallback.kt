package com.resdev.poehelper.Callbacks

import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.net.Uri
import android.view.MotionEvent
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_SWIPE
import androidx.recyclerview.widget.RecyclerView
import com.resdev.poehelper.Adapter.CurrenciesAdapter
import com.resdev.poehelper.Config
import com.resdev.poehelper.Util


class SwipeCurrencyLeftCallback() : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

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
                paint.setColor(Config.color)
            }
            else{
                paint.setColor(Color.GRAY)
            }

            paint.setTextSize(40F)
            paint.setTextAlign(Paint.Align.CENTER)
            var text = "Check on\nCheck on"
            setTouchListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            var height = itemView.getTop() + itemView.getHeight() / 2
            c.drawText("Check on", (itemView.getRight() - 200).toFloat(), (height-20).toFloat(), paint)
            c.drawText("poe.trade", (itemView.getRight() - 200).toFloat(), (height+20).toFloat(), paint)
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    private fun willActionBeTriggered(dX: Float, viewWidth: Int): Boolean {
        return Math.abs(dX) >= viewWidth / 3.5
    }

    fun makeAction(viewHolder: RecyclerView.ViewHolder){
        val i = Intent(Intent.ACTION_VIEW)
        var holder= viewHolder as CurrenciesAdapter.CurrencyViewHolder
        i.data = Uri.parse(Util.generateCurrencyTradeUrl(holder.line))
        holder.itemView.context.startActivity(i)
    }


}