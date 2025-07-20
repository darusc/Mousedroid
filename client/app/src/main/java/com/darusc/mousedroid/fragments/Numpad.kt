package com.darusc.mousedroid.fragments

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.darusc.mousedroid.mkinput.Input
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

    companion object {
        fun onNumpadButtonClickListener(view: View) {
            ConnectionManager.getInstance().sendBytes(byteArrayOf(Input.KEYPRESS, view.tag.toString().toInt().toByte()), true)
        }
    }
}