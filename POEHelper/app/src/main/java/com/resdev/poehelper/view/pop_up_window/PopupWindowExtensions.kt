package com.resdev.poehelper.view.pop_up_window

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow

fun PopupWindow.dimBehind() {
    val container = contentView.rootView
    val context = contentView.context
    val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val p = container.layoutParams as WindowManager.LayoutParams
    p.flags = p.flags or WindowManager.LayoutParams.FLAG_DIM_BEHIND
    p.dimAmount = 0.5f
    wm.updateViewLayout(container, p)
}

fun PopupWindow.showCurrencyWindow(view: View) {
    this.showAtLocation(view, Gravity.CENTER, 0, 0)
    this.dimBehind()
    this.contentView.setOnClickListener{
        this.dismiss()
        true
    }
}
fun PopupWindow.showItemWindow(view: View) {
    this.showAtLocation(view, Gravity.CENTER, 0, 0)
    this.contentView.setOnLongClickListener{
        this.dismiss()
        true
    }
}

