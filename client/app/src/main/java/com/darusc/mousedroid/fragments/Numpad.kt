package com.darusc.mousedroid.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.darusc.mousedroid.R
import com.darusc.mousedroid.databinding.FragmentNumpadBinding
import com.darusc.mousedroid.viewmodels.NumpadViewModel

class Numpad : Fragment() {

    private lateinit var binding: FragmentNumpadBinding

    private val viewModel: NumpadViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_numpad, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }
}