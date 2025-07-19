package com.example.mousedroid

import android.content.Context
import android.os.Looper
import android.view.*
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.ScaleGestureDetectorCompat
import com.example.mousedroid.networking.ConnectionManager
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.delay
import kotlin.math.abs
import kotlin.math.ln

/**
 * Class that handles simples gestures (tap, long press, scroll) using
 * a GestureDetectorCompat
 */
class GestureHandler(context: Context): View.OnTouchListener {

    private val TAG = "Mousedroid"
    private val EV_DELAY_MILLIS: Long = 150
    private val SCROLL_TRESHOLD = 2.0f

    private data class State(
        var scrolling: Boolean,
        var lastScrolled: Long,
        var doublePress: Boolean,
        var lastDoublePress: Long,
        var dragging: Boolean,
    )
    private val state = State(false, 0, false, 0, false)

    private val connectionManager = ConnectionManager.getInstance()

    /**
     * Detector used for detecting scaling (pinch to zoom)
     */
    private val scaleDetector: ScaleGestureDetector = ScaleGestureDetector(context, object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            val scale = (ln(detector.scaleFactor) * 500).toInt().coerceIn(-128, 127).toByte()
            connectionManager.sendBytes(byteArrayOf(Input.ZOOM, scale), true)

            return true
        }
    })

    /**
     * Detector used for gestures like: tap, double tap, move, drag and scroll
     */
    private val gestureDetector: GestureDetectorCompat = GestureDetectorCompat(context, object : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapUp(e: MotionEvent): Boolean {
            // Post a runnable that sends a click event after a set delay
            // onDoubleTap will cancel it when called
            singleTapRunnable = Runnable {
                connectionManager.sendBytes(byteArrayOf(Input.LCLICK), true)
            }
            handler.postDelayed(singleTapRunnable!!, EV_DELAY_MILLIS)
            return super.onSingleTapUp(e)
        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
            // Cancel the click event from the single tap
            singleTapRunnable?.let { handler.removeCallbacks(it) }

            state.doublePress = true
            state.lastDoublePress = System.currentTimeMillis()

            // Post a runnable that will send 2 click events after a set time
            // If before that a move event is detected by the onTouch, it will be canceled
            doubleTapRunnable = Runnable {
                connectionManager.sendBytes(byteArrayOf(Input.LCLICK), true)
                connectionManager.sendBytes(byteArrayOf(Input.LCLICK), true)
                state.doublePress = false
                state.dragging = false
            }
            handler.postDelayed(doubleTapRunnable!!, EV_DELAY_MILLIS)
            return false
        }

        override fun onScroll(
            e1: MotionEvent,
            e2: MotionEvent,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            if((e1.pointerCount == 2 || e2.pointerCount == 2) || System.currentTimeMillis() - state.lastScrolled < EV_DELAY_MILLIS) {
                if(abs(distanceX) < SCROLL_TRESHOLD && abs(distanceY) < SCROLL_TRESHOLD) {
                    return super.onScroll(e1, e2, distanceX, distanceY)
                }

                // If at least one event has 2 pointers and the time before last scroll is less than 500ms
                // we continue to scroll
                state.scrolling = true
                state.lastScrolled = System.currentTimeMillis()

                val type: Byte
                val delta: Float
                if(abs(distanceY) > abs(distanceX)) {
                    // Vertical scrolling
                    type = Input.SCROLL
                    delta = -distanceY
                } else {
                    // Horizontal scrolling
                    type = Input.SCROLL_H
                    delta = -distanceX
                }

                connectionManager.sendBytes(byteArrayOf(type, delta.toInt().coerceIn(-128, 127).toByte()), true)
            }
            else {
                // We consider to be just moving if there is 1 pointer in both events
                connectionManager.sendBytes(
                    byteArrayOf(
                        Input.MOVE,
                        distanceX.toInt().coerceIn(-128, 127).toByte(),
                        distanceY.toInt().coerceIn(-128, 127).toByte()),
                    true)
            }

            return super.onScroll(e1, e2, distanceX, distanceY)
        }
    })



    private val handler = android.os.Handler(Looper.getMainLooper())
    private var singleTapRunnable: Runnable? = null
    private var doubleTapRunnable: Runnable? = null

    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {

        if(p1?.actionMasked == MotionEvent.ACTION_POINTER_UP && p1.pointerCount == 2) {
            if(state.scrolling) {
                // Cancel scrolling when pointer is lifted up
                state.scrolling = false
                return false
            } else if(System.currentTimeMillis() - state.lastScrolled > 500) {
                // Right click is detected when there are 2 pointers and 1 starts to lift
                // Can't be detected in onSingleTapUp because the 2 pointers are not lifted at the same time
                // so we don't get that event with 2 pointers but rather 2 different events with 1 pointer
                connectionManager.sendBytes(byteArrayOf(Input.RCLICK), true)
            }
        }

        if(p1?.action == MotionEvent.ACTION_UP && state.dragging) {
            // Cancel dragging
            state.scrolling = false
            state.doublePress = false
            connectionManager.sendBytes(byteArrayOf(Input.UP), true)
        }

        if(p1?.let { gestureDetector.onTouchEvent(it) } == true) {
            return true
        }

        if(p1?.let { scaleDetector.onTouchEvent(it) } == true) {
            return true
        }

        when(p1?.action) {
            MotionEvent.ACTION_MOVE -> {
                if(state.doublePress && System.currentTimeMillis() - state.lastDoublePress < EV_DELAY_MILLIS) {
                    // If a move event is triggered right after a double press initiate dragging
                    // Can't be detected in onTouch because it seem like the detector classified that event
                    // as a double tap and won't detect a new scroll event right after

                    // Cancel the double tap and continue with dragging
                    doubleTapRunnable?.let { handler.removeCallbacks(it) }
                    state.doublePress = false
                    state.dragging = true

                    // Send a mouse down event to initiate dragging
                    connectionManager.sendBytes(byteArrayOf(Input.DOWN), true)

                    val cancelEvent = MotionEvent.obtain(p1)
                    cancelEvent.action = MotionEvent.ACTION_CANCEL
                    gestureDetector.onTouchEvent(cancelEvent)
                }
            }
        }

        return true
    }
}
