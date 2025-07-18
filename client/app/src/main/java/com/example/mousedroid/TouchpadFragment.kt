package com.example.mousedroid

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.mousedroid.networking.ConnectionManager

class TouchpadFragment(context: Context) : Fragment(), View.OnTouchListener {

    private val connectionManager = ConnectionManager.getInstance()
    private val gestureHandler: GestureHandler = GestureHandler(context)

    private val TAG = "Mousedroid"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR

        val view = inflater.inflate(R.layout.touchpad_fragment, container, false)
        view.setOnTouchListener(this)

        gestureHandler.bindView(view.findViewById(R.id.touchpadFragment))

        return view
    }

    override fun onTouch(p0: View?, p1: MotionEvent): Boolean {

        if(p1.actionMasked == MotionEvent.ACTION_POINTER_UP && p1.pointerCount == 2){
            // If 2 fingers were lifted up
            if(!gestureHandler.scrolled && System.currentTimeMillis() - gestureHandler.lastScrolled > 500){
                // and we didn't scroll before that means a right click is issued
                connectionManager.sendBytes(byteArrayOf(Input.RCLICK), true)
            }
            else{
                // otherwise scrolling is stopped
                gestureHandler.scrolled = false
            }
            return false
        }

        if(gestureHandler.detector.onTouchEvent(p1)) {
            return true
        }

        when(p1.action) {
            MotionEvent.ACTION_MOVE -> {
                // A move event is detected after a long press gesture
                // a dragging gesture is issued
                if(gestureHandler.isLongPressed) {
                    gestureHandler.isLongPressed = false
                    gestureHandler.isDragging = true

                    val cancelEvent = MotionEvent.obtain(p1)
                    cancelEvent.action = MotionEvent.ACTION_CANCEL
                    gestureHandler.detector.onTouchEvent(cancelEvent)
                }
            }
            MotionEvent.ACTION_UP -> {
                // Cancel dragging and reset long pressed state when pointers are lifted up
                if(gestureHandler.isLongPressed || gestureHandler.isDragging){
                    gestureHandler.isDragging = false
                    gestureHandler.isLongPressed = false
                    connectionManager.sendBytes(byteArrayOf(Input.UP), true)
                }
            }
        }

        return true
    }
}
