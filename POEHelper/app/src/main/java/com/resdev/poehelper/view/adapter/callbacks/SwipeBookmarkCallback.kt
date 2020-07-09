package com.resdev.poehelper.view.adapter.callbacks

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.resdev.poehelper.model.Config
import com.resdev.poehelper.R
import com.resdev.poehelper.utils.Util
import com.resdev.poehelper.model.retrofit.PoeMarket
import com.resdev.poehelper.repository.Repository
import com.resdev.poehelper.utils.ColorsUtil.isColorLight
import com.resdev.poehelper.utils.URLUtils.generatePoeMarketTradeUrl
import com.resdev.poehelper.view.adapter.BookmarkAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.abs

class SwipeBookmarkCallback() : ItemTouchHelper.SimpleCallback(0,
    ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        if (direction == ItemTouchHelper.RIGHT){
            makeRemoveAction(viewHolder)
        }
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
        recyclerView.setOnTouchListener { _, event ->
            swipeBack = (event.action == MotionEvent.ACTION_CANCEL || event.action == MotionEvent.ACTION_UP)&&dX<0
            if (willActionBeTriggered(dX, recyclerView.width) and swipeBack and (dX<0)) {//
                makeOpenAction(viewHolder)
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
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
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
                paint.color = Color.GRAY
            }

            paint.textSize = 40F
            paint.textAlign = Paint.Align.CENTER
            setTouchListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            var height = itemView.top + itemView.height / 2
            if (dX>0 && dX<itemView.right){
                c.drawText("Remove from", 150F, (height-(paint.textSize/2)-1), paint)
                c.drawText("bookmarks", 150F, (height+(paint.textSize/2)+1), paint)
            }
            else if (dX<0){
                c.drawText("Check on", (itemView.right - 150).toFloat(), (height-(paint.textSize/2)-1), paint)
                c.drawText("pathofexile", (itemView.right - 150).toFloat(), (height+(paint.textSize/2)+1), paint)
            }

        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }


    private fun willActionBeTriggered(dX: Float, viewWidth: Int): Boolean {
        return abs(dX) >= viewWidth / 3.5
    }

    private fun makeOpenAction(viewHolder: RecyclerView.ViewHolder){
        GlobalScope.launch {
            var holder= viewHolder as BookmarkAdapter.BookmarkItemViewHolder
            var link = PoeMarket.sendItemRequest(
                Config.league,
                (holder.item))
            if (link==null){
                withContext(Dispatchers.Main){
                    Util.showInternetConnectionError(viewHolder.itemView)
                }
                return@launch
            }
            withContext(Dispatchers.Main){
                Util.openBrowserWindowByUrl(viewHolder.itemView.context, generatePoeMarketTradeUrl()+"/${link?.id}")
            }

        }
    }

    private fun makeRemoveAction(viewHolder: RecyclerView.ViewHolder){
        var holder= viewHolder as BookmarkAdapter.BookmarkItemViewHolder
        Repository.removeEntity(holder.item)
        var itemName = holder.item.translatedName ?: holder.item.name
        var snackbar = Snackbar.make(viewHolder.itemView, "$itemName is removed", Snackbar.LENGTH_LONG)
        snackbar.setActionTextColor(Color.BLACK)
        snackbar.view.setBackgroundColor(viewHolder.itemView.context.getColor(R.color.lightGray))
        snackbar.show()
    }


}