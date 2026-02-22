package com.darusc.mousedroid.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.darusc.mousedroid.R
import com.darusc.mousedroid.databinding.FragmentInputBinding
import com.darusc.mousedroid.viewmodels.ConnectionViewModel
import kotlinx.coroutines.launch

/**
 * Input host fragment. All navigation between input modes
 * is happening inside this fragment
 */
class Input: Fragment() {

    private lateinit var binding: FragmentInputBinding

    private val viewModel: ConnectionViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_input, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            replaceChildFragment(Touchpad())
        }

        binding.btnOpenDrawer.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        binding.navigation.setNavigationItemSelectedListener { item ->
            item.isChecked = true
            when(item.itemId) {
                R.id.mode_touchpad -> replaceChildFragment(Touchpad())
                R.id.mode_numpad -> replaceChildFragment(Numpad())
                R.id.mode_keyboard -> { }
                R.id.mode_disconnect -> {
                    item.isChecked = false
                    //viewModel.disconnect()
                    findNavController().navigateUp()
                }
                else -> { }
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.events.collect {
                        when(it) {
                            is ConnectionViewModel.Event.NavigateToInput -> { }
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

    private fun replaceChildFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}