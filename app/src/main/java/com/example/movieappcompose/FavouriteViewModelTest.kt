package com.example.movieappcompose

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappcompose.movieList.repository.MovieRepository
import com.example.movieappcompose.mvi.UiEffect
import com.example.movieappcompose.mvi.UiEvent
import com.example.movieappcompose.mvi.UiState
import com.example.movieappcompose.utils.Result
import kotlinx.coroutines.Dispatchers
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
    private val _uiState: MutableStateFlow<MovieListContract.State> = MutableStateFlow(MovieListContract.State(MovieListContract.MovieListState.Empty))
    val uiState = _uiState.asStateFlow()

    private val _event: MutableSharedFlow<MovieListContract.Event> = MutableSharedFlow()
    val event = _event.asSharedFlow()

    private val _effect: Channel<MovieListContract.Effect> = Channel()
    val effect = _effect.receiveAsFlow()

    fun subscribeEvents() {
        viewModelScope.launch {
            event.collectLatest {event ->
                handleEvent(event)
            }
        }
    }

    private fun handleEvent(event: UiEvent) {
        when(event) {
            is MovieListContract.Event.ShowMovieList -> {
                getFavouriteList()
            }
            is MovieListContract.Event.OnMovieClicked -> {
                setEffect { MovieListContract.Effect.NavigateToDetails(event.id) }
            }
            is MovieListContract.Event.OnIconButtonClicked -> {
                event.data.let { movie ->
                    setEffect { MovieListContract.Effect.OnIconButtonClick(movie, event.remove) }
                }
            }
            else -> Unit
        }
    }

    fun removeFromFavourite(id: String) {
        viewModelScope.launch(Dispatchers.Main) {
            repository.removeFromFavourite(id) {}
        }
    }

    private fun getFavouriteList() {
        setState { copy(movieListState = MovieListContract.MovieListState.Loading) }
        viewModelScope.launch {
            repository.getFavouritesList().collect {
                when(it) {
                    is Result.Success -> {
                        it.data?.let {movies ->
                            setState { copy(movieListState = MovieListContract.MovieListState.Success(
                                movieList = movies)
                            ) }
                        }
                    }
                    else -> {
                        setState { copy(movieListState = MovieListContract.MovieListState.Empty) }
                        Log.d("asd", it.toString())
                    }
                }
            }
        }
    }

    fun setEvent(event: MovieListContract.Event) {
        val newEvent = event
        viewModelScope.launch { _event.emit(newEvent) }
    }

    fun setState(reduce: MovieListContract.State.() -> MovieListContract.State) {
        val newState = uiState.value.reduce()
        _uiState.value = newState
    }

    fun setEffect(builder: () -> MovieListContract.Effect) {
        viewModelScope.launch { _effect.send(builder()) }
    }
}