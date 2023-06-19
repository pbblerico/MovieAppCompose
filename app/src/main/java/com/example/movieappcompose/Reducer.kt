package com.example.movieappcompose

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.lang.Thread.State

abstract class Reducer<S: UiState, E: UiEvent>(initialVal: S) {
    private val _state: MutableStateFlow<S> = MutableStateFlow<S>(initialVal)
    val state: StateFlow<S>
        get() = _state

    abstract fun reduce(oldState: S, event: E)

    fun sendEvent(event: E) {
        reduce(_state.value, event)
    }

    fun setState(newState: S) {

    }
}

interface UiState

interface  UiEvent