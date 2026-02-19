package com.darusc.mousedroid.fragments

import android.content.Context
import android.view.Gravity
import android.view.WindowManager
import android.widget.PopupWindow
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.darusc.mousedroid.R
import com.google.android.material.button.MaterialButton

fun Fragment.showPopupDialog(@LayoutRes fragmentId: Int) {
    val rootView = view ?: return

    val pView = layoutInflater.inflate(fragmentId, null)
    val popup = PopupWindow(
        pView,
        ConstraintLayout.LayoutParams.MATCH_PARENT,
        ConstraintLayout.LayoutParams.WRAP_CONTENT,
        true
    )

    pView.findViewById<MaterialButton>(R.id.btnOK).setOnClickListener {
        popup.dismiss()
    }

    rootView.post {
        popup.showAtLocation(rootView, Gravity.CENTER, 0, 0)
        popup.dim(0.6f)
    }
}

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