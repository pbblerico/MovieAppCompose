package com.example.movieappcompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappcompose.movieList.repository.MovieRepository
import com.example.movieappcompose.mvi.UiEffect
import com.example.movieappcompose.mvi.UiEvent
import com.example.movieappcompose.mvi.UiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class FavouriteViewModelTest(
    private val repository: MovieRepository
): ViewModel() {
    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(MovieListContract.State(MovieListContract.MovieListState.Empty))
    val uiState = _uiState.asStateFlow()

    private val _event: MutableSharedFlow<UiEvent> = MutableSharedFlow()
    val event = _event.asSharedFlow()

    private val _effect: Channel<UiEffect> = Channel()
    val effect = _effect.receiveAsFlow()

    init {
        subscribeEvents()
    }

    private fun subscribeEvents() {
        viewModelScope.launch {
            event.collectLatest {event ->
                handleEvent(event)
            }
        }
    }

    fun handleEvent(event: UiEvent) {
        when(event) {
            is MovieListContract.Event.ShowMovieList -> {

            }
            is MovieListContract.Event.OnMovieClicked -> {
//                setEffect { MovieListContract.Effect.NavigateToDetails(event.id) }
            }
            is MovieListContract.Event.OnIconButtonClicked -> {
//                event.data.let { movie ->
//                    setEffect { MovieListContract.Effect.OnIconButtonClick(movie, event.remove) }
////                    getFavouriteList()
//                }
            }
            else -> Unit
        }
    }

    fun setEvent(event: UiEvent) {
        val newEvent = event
        viewModelScope.launch { _event.emit(newEvent) }
    }

    fun setState(newState: UiState) {

    }

    fun setEffect() {

    }
}