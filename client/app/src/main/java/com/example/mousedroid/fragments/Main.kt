package com.example.mousedroid.fragments

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresPermission
import androidx.fragment.app.Fragment
import com.example.mousedroid.R
import com.example.mousedroid.getDeviceDetails
import com.example.mousedroid.networking.ConnectionManager
import com.google.android.material.button.MaterialButton

class Main : Fragment() {

    private val connectionManager = ConnectionManager.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<MaterialButton>(R.id.btnConnectUSB).setOnClickListener(::onButtonConnectUSBClick)
        view.findViewById<MaterialButton>(R.id.btnConnectWIFI).setOnClickListener(::onButtonConnectWIFIClick)
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    private fun onButtonConnectUSBClick(view: View) {
        val details = getDeviceDetails(requireContext(), ConnectionManager.Mode.USB)
        connectionManager.connect(6969, details)
    }

    private fun onButtonConnectWIFIClick(view: View) {
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, DeviceList())
            .addToBackStack(null)
            .commit()
    }
}