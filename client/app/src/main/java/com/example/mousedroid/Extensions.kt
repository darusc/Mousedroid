package com.example.mousedroid

import android.content.Context
import android.view.WindowManager
import android.widget.PopupWindow

// https://stackoverflow.com/questions/35874001/dim-the-background-using-popupwindow-in-android
fun PopupWindow.dim(amount: Float) {
    val container = contentView.rootView
    val context = contentView.context
    val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val p = container.layoutParams as WindowManager.LayoutParams
    p.flags = p.flags or WindowManager.LayoutParams.FLAG_DIM_BEHIND
    p.dimAmount = amount
    wm.updateViewLayout(container, p)
}