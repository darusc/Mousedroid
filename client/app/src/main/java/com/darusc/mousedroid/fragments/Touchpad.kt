package com.darusc.mousedroid.fragments

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.darusc.mousedroid.mkinput.GestureHandler
import com.darusc.mousedroid.R

class Touchpad() : Fragment() {

    private val TAG = "Mousedroid"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR

        val view = inflater.inflate(R.layout.fragment_touchpad, container, false)
        view.setOnTouchListener(GestureHandler(requireContext()))

        return view
    }
}
