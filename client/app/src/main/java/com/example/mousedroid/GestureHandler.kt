package com.example.mousedroid

import android.content.Context
import android.util.Log
import android.view.*
import androidx.core.view.GestureDetectorCompat
import com.example.mousedroid.networking.Connection
import com.example.mousedroid.networking.ConnectionManager

class GestureHandler(context: Context): GestureDetector.SimpleOnGestureListener() {

    private val connectionManager = ConnectionManager.getInstance()
    val detector: GestureDetectorCompat = GestureDetectorCompat(context, this)

    private lateinit var view: View

    var scrolled = false
    var lastScrolled: Long = 0
    var isLongPressed = false
    var isMovingAfterLongPress = false

    private val TAG = "Mousedroid"

    fun bindView(_view: View){
        view = _view.findViewById(R.id.touchpadFragment)
    }

    override fun onSingleTapUp(e: MotionEvent): Boolean {
        connectionManager.sendBytes(byteArrayOf(Input.LCLICK), true)
        return super.onSingleTapUp(e)
    }

    override fun onDoubleTap(e: MotionEvent): Boolean {
        connectionManager.sendBytes(byteArrayOf(Input.LCLICK), true)
        return true
    }

    override fun onLongPress(e: MotionEvent) {
        connectionManager.sendBytes(byteArrayOf(Input.DOWN), true)
        isLongPressed = true
        view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING)
    }

    override fun onScroll(
        e1: MotionEvent,
        e2: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        if(e1.pointerCount == 1 && e2.pointerCount == 1 && System.currentTimeMillis() - lastScrolled > 500) {
            connectionManager.sendBytes(byteArrayOf(Input.MOVE, distanceX.toInt().coerceIn(-128, 127).toByte(), distanceY.toInt().coerceIn(-128, 127).toByte()), true)
            println(distanceX)
            println(distanceY)
        }
        else {
            connectionManager.sendBytes(byteArrayOf(Input.SCROLL, (-distanceY).toInt().coerceIn(-128, 127).toByte()), true)
            scrolled = true
            lastScrolled = System.currentTimeMillis()
        }

        return super.onScroll(e1, e2, distanceX, distanceY)
    }
}
