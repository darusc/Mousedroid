package com.darusc.mousedroid.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * A base viewmodel class that provides an implementation of sending
 * custom events and observable state
 */
abstract class BaseViewModel<S: BaseViewModel.State, E: BaseViewModel.Event>(initialState: S): ViewModel() {

    open class Event
    open class State

    private val _events = Channel<E>(Channel.BUFFERED)
    val events: Flow<E>
        get() = _events.receiveAsFlow()

    private val _state = MutableStateFlow<S>(initialState)
    val state: StateFlow<S>
        get() = _state.asStateFlow()

    protected fun sendEvent(event: E) {
        viewModelScope.launch {
            _events.send(event)
        }
    }

    protected fun setState(state: S) {
        viewModelScope.launch {
            _state.value = state
        }
    }
}