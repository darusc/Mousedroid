package com.darusc.mousedroid.fragments

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.darusc.mousedroid.R
import com.darusc.mousedroid.databinding.FragmentMainBinding
import com.darusc.mousedroid.viewmodels.ConnectionViewModel
import kotlinx.coroutines.launch

class Main : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var loadingPopup: PopupWindow

    private val viewModel: ConnectionViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        loadingPopup = PopupWindow (
            layoutInflater.inflate(R.layout.loading_fragment, null),
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.MATCH_PARENT,
        )

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.P)
    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnConnectUSB.setOnClickListener  {
            viewModel.connectInUSBMode(requireContext())
        }

        binding.btnConnectWIFI.setOnClickListener {
            findNavController().navigate(R.id.action_main_to_devicelist)
        }

        binding.btnConnectBT.setOnClickListener {
            viewModel.connectInBluetoothMode(requireContext())
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.state.collect {
                        when(it) {
                            is ConnectionViewModel.State.Connecting -> {
                                if(!loadingPopup.isShowing) {
                                    loadingPopup.showAtLocation(binding.root, Gravity.CENTER, 0, 0)
                                }
                            }
                            is ConnectionViewModel.State.Idle -> loadingPopup.dismiss()
                            is ConnectionViewModel.State.Connected -> loadingPopup.dismiss()
                        }
                    }
                }

                launch {
                    viewModel.events.collect {
                        when(it) {
                            is ConnectionViewModel.Event.NavigateToInput -> findNavController().navigate(R.id.action_main_to_touchpad)
                            is ConnectionViewModel.Event.NavigateToMain -> { }
                            is ConnectionViewModel.Event.ConnectionDisconnected -> showPopupDialog(R.layout.connection_disconnected_fragment)
                            is ConnectionViewModel.Event.ConnectionFailed -> showPopupDialog(R.layout.connection_failed_fragment)
                            else -> { }
                        }
                    }
                }
            }
        }
    }
}