package com.example.mousedroid

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.mousedroid.networking.ConnectionManager

class TouchpadFragment() : Fragment() {

    private val TAG = "Mousedroid"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR

        val view = inflater.inflate(R.layout.touchpad_fragment, container, false)
        view.setOnTouchListener(GestureHandler(requireContext()))

        return view
    }
}
