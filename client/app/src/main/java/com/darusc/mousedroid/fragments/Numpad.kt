package com.darusc.mousedroid.fragments

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.darusc.mousedroid.mkinput.InputEvent
import com.darusc.mousedroid.R
import com.darusc.mousedroid.networking.ConnectionManager

class Numpad : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        return inflater.inflate(R.layout.fragment_numpad, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNumpadButtons(view as ViewGroup)
    }

    private fun setupNumpadButtons(viewGroup: ViewGroup) {
        for (i in 0 until viewGroup.childCount) {
            val child = viewGroup.getChildAt(i)

            if (child is ViewGroup) {
                // If it's a container (like your ConstraintLayout), look inside it
                setupNumpadButtons(child)
            } else if (child.tag != null) {
                // If the view has a tag, it's one of our buttons!
                child.setOnClickListener {
                    val keyCode = it.tag.toString().toByte()
                    sendKeyPress(keyCode)
                }
            }
        }
    }

    private fun sendKeyPress(keyCode: Byte) {
        ConnectionManager.getInstance().send(
            InputEvent.KeyPress(byteArrayOf(keyCode)),
            true
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Reset orientation when leaving Numpad
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }
}