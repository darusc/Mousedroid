package com.example.mousedroid

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mousedroid.networking.Connection
import com.example.mousedroid.networking.ConnectionManager

class NumpadFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        return inflater.inflate(R.layout.numpad_fragment, container, false)
    }

    companion object {
        fun onNumpadButtonClickListener(view: View) {
            ConnectionManager.getInstance().sendBytes(byteArrayOf(Input.KEYPRESS, view.tag.toString().toInt().toByte()), true)
        }
    }
}