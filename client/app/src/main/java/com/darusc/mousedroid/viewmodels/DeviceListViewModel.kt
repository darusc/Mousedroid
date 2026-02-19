package com.darusc.mousedroid.viewmodels

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DeviceListViewModel(
    private val sharedPreferences: SharedPreferences
): BaseViewModel<DeviceListViewModel.State, DeviceListViewModel.Event>(State(emptyList())) {

    sealed class Event: BaseViewModel.Event()
    data class State(val devices: List<Pair<String, String>>): BaseViewModel.State()

    class Factory(private val sharedPreferences: SharedPreferences): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(DeviceListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DeviceListViewModel(sharedPreferences) as T
            }
            throw IllegalArgumentException("Unknown viewmodel class")
        }
    }

    init {
        updateState()
    }

    fun add(name: String, address: String) {
        sharedPreferences.edit { putString(name, address) }
        updateState()
    }

    fun remove(name: String) {
        sharedPreferences.edit { remove(name) }
        updateState()
    }

    private fun updateState() {
        val devices = mutableListOf<Pair<String, String>>()
        sharedPreferences.all.let {
            for((name, address) in it) {
                devices.add(Pair(name, address as String))
            }
        }
        setState(State(devices))
    }
}