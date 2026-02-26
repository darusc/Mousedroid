package com.darusc.mousedroid.fragments

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothClass.Device
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.PopupWindow
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.darusc.mousedroid.adapters.DeviceAdapter
import com.darusc.mousedroid.R
import com.darusc.mousedroid.databinding.FragmentDeviceListBinding
import com.darusc.mousedroid.getDeviceDetails
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.darusc.mousedroid.networking.Connection
import com.darusc.mousedroid.networking.bluetooth.BluetoothAdapterWrapper
import com.darusc.mousedroid.viewmodels.ConnectionViewModel
import com.darusc.mousedroid.viewmodels.DeviceListViewModel
import kotlinx.coroutines.launch

class DeviceList : Fragment() {

    private lateinit var binding: FragmentDeviceListBinding
    private lateinit var loadingPopup: PopupWindow

    private lateinit var deviceAdapter: DeviceAdapter

    private val connectionMode: Connection.Mode
        get() = arguments?.getSerializable("CONNECTION_MODE") as Connection.Mode

    private val connectionViewModel: ConnectionViewModel by activityViewModels()
    private val deviceListViewModel: DeviceListViewModel by activityViewModels {
        if(connectionMode == Connection.Mode.BLUETOOTH) {
            val devices = BluetoothAdapterWrapper.getInstance()?.pairedDevices ?: emptySet()
            DeviceListViewModel.Factory(devices)
        } else {
            val preferences = requireActivity().getSharedPreferences("devices", Context.MODE_PRIVATE)
            DeviceListViewModel.Factory(preferences)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_device_list, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        loadingPopup = PopupWindow (
            layoutInflater.inflate(R.layout.loading_fragment, null),
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.MATCH_PARENT,
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        deviceAdapter = DeviceAdapter(arrayListOf(), object : DeviceAdapter.OnItemClickListener {
            override fun onItemLongClick(position: Int) {
                if (connectionMode == Connection.Mode.WIFI) {
                    // Delete is done only when the fragment was created to display wifi devices
                    val name = deviceAdapter.devices[position].first
                    showDeleteDialog(name)
                }
            }

            @RequiresApi(Build.VERSION_CODES.P)
            @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
            override fun onItemClick(name: String, address: String) {
                deviceListViewModel.onDeviceClick(requireContext(), name, address)
            }
        })
        binding.recyclerView.adapter = deviceAdapter

        binding.btnBack.setOnClickListener { parentFragmentManager.popBackStack() }

        // Make the add device button visible only if fragment
        // was created to display wifi devices otherwise keep it hidden
        if(connectionMode == Connection.Mode.WIFI) {
            binding.btnAddDevice.setOnClickListener { showAddDeviceDialog() }
            binding.btnAddDevice.visibility = View.VISIBLE
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    deviceListViewModel.state.collect {
                        deviceAdapter.devices.clear()
                        deviceAdapter.devices.addAll(it.devices)
                        deviceAdapter.notifyDataSetChanged()
                    }
                }

                launch {
                    connectionViewModel.state.collect {
                        when(it) {
                            is ConnectionViewModel.State.Connecting -> {
                                if (!loadingPopup.isShowing) {
                                    loadingPopup.contentView.findViewById<TextView>(R.id.loadingMessage).text = it.message
                                    loadingPopup.showAtLocation(binding.root, Gravity.CENTER, 0, 0)
                                }
                            }
                            is ConnectionViewModel.State.Idle -> loadingPopup.dismiss()
                            is ConnectionViewModel.State.Connected -> loadingPopup.dismiss()
                        }
                    }
                }

                launch {
                    connectionViewModel.events.collect {
                        when(it) {
                            is ConnectionViewModel.Event.NavigateToInput -> findNavController().navigate(R.id.action_devicelist_to_touchpad)
                            is ConnectionViewModel.Event.NavigateToMain -> findNavController().popBackStack(R.id.mainFragment, false)
                            is ConnectionViewModel.Event.ConnectionDisconnected -> showPopupDialog(R.layout.connection_disconnected_fragment)
                            is ConnectionViewModel.Event.ConnectionFailed -> showPopupDialog(R.layout.connection_failed_fragment)
                            else -> { }
                        }
                    }
                }
            }
        }
    }

    private fun showDeleteDialog(name: String){
        val pView = layoutInflater.inflate(R.layout.device_delete_fragment, null)
        val popup = PopupWindow(
            pView,
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            true
        )

        pView.findViewById<MaterialButton>(R.id.deviceDeleteConfirm).setOnClickListener {
            deviceListViewModel.remove(name)
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
            deviceListViewModel.add(name.text.toString(), address.text.toString())
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