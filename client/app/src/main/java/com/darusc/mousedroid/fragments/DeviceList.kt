package com.darusc.mousedroid.fragments

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.PopupWindow
import androidx.annotation.RequiresPermission
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.darusc.mousedroid.adapters.DeviceAdapter
import com.darusc.mousedroid.R
import com.darusc.mousedroid.getDeviceDetails
import com.darusc.mousedroid.networking.ConnectionManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.darusc.mousedroid.dim

class DeviceList : Fragment() {

    private val connectionManager = ConnectionManager.getInstance()

    private val deviceList: ArrayList<Pair<String, String>> = arrayListOf()
    private lateinit var deviceAdapter: DeviceAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_device_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().getSharedPreferences("devices", Context.MODE_PRIVATE)?.all?.let {
            for((key, value) in it) {
                deviceList.add(Pair(key, value as String))
            }
        }

        deviceAdapter = DeviceAdapter(requireContext(), deviceList, object : DeviceAdapter.OnItemClickListener {
            override fun onItemLongClick(position: Int) {
                showDeleteDialog(position)
            }

            @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
            override fun onItemClick(address: String) {
                val details = getDeviceDetails(requireContext(), ConnectionManager.Mode.WIFI)
                connectionManager.connect(address, 6969, details)
            }
        })

        view.findViewById<RecyclerView>(R.id.recyclerView).apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = deviceAdapter
        }

        view.findViewById<MaterialButton>(R.id.btnBack).setOnClickListener { parentFragmentManager.popBackStack() }
        view.findViewById<MaterialButton>(R.id.btnAddDevice).setOnClickListener { showAddDeviceDialog() }
    }

    private fun removeFromPreferences(key: String) {
        requireActivity()
            .getSharedPreferences("devices", Context.MODE_PRIVATE)
            .edit()
            .apply {
                remove(key)
                apply()
            }
    }

    private fun addToPreferences(key: String, value: String) {
        requireActivity()
            .getSharedPreferences("devices", Context.MODE_PRIVATE)
            .edit()
            .apply {
                putString(key, value)
                apply()
            }
    }

    private fun showDeleteDialog(position: Int){
        val pView = layoutInflater.inflate(R.layout.device_delete_fragment, null)
        val popup = PopupWindow(
            pView,
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            true
        )

        pView.findViewById<MaterialButton>(R.id.deviceDeleteConfirm).setOnClickListener {
            removeFromPreferences(deviceList[position].first)

            deviceList.removeAt(position)
            deviceAdapter.notifyDataSetChanged()

            popup.dismiss()
        }

        pView.findViewById<MaterialButton>(R.id.cancelDelete).setOnClickListener {
            popup.dismiss()
        }

        popup.showAtLocation(pView, Gravity.CENTER, 0, 0)
        popup.dim(0.6f)
    }

    private fun showAddDeviceDialog() {
        val pView = layoutInflater.inflate(R.layout.device_add_fragment, null)
        val popup = PopupWindow(
            pView,
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            true
        )

        val address: TextInputEditText = pView.findViewById(R.id.textAddress)
        val name: TextInputEditText = pView.findViewById(R.id.textName)
        val deviceAddBtn: MaterialButton = pView.findViewById(R.id.deviceAddConfirm)

        address.addTextChangedListener {
            val ip = it?.toString()
            deviceAddBtn.isEnabled =
                ip?.matches(Regex("^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\$")) ?: false
        }

        deviceAddBtn.setOnClickListener {
            deviceList.add(name.text.toString() to address.text.toString())
            deviceAdapter.notifyItemInserted(deviceList.size - 1)

            addToPreferences(name.text.toString(), address.text.toString())

            popup.dismiss()
        }

        pView.findViewById<MaterialButton>(R.id.cancelAdd).setOnClickListener {
            popup.dismiss()
        }

        // !!!
        popup.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        popup.showAtLocation(pView, Gravity.BOTTOM, 0, 0)
        popup.dim(0.6f)
    }
}