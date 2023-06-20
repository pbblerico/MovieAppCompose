package com.example.movieappcompose.movieList.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movieappcompose.MovieListEvent
import com.example.movieappcompose.UiEvent
import com.example.movieappcompose.shared.data.models.ListItem
import com.example.movieappcompose.shared.data.models.Movie
import com.example.movieappcompose.movieList.repository.MovieRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MovieViewModelMVI(
    private val movieRepository: MovieRepository
): ViewModel() {
    private val movieEventChannel = Channel<MovieListEvent> ()
    val movieEventFlow = movieEventChannel.receiveAsFlow()

    val movieMultipleListPaging: StateFlow<PagingData<ListItem>> =
        Pager(
            PagingConfig(pageSize = 1)
        ) {
            movieRepository.getMoviePagingSource()
        }.flow.cachedIn(viewModelScope).stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    private fun sendEvent(event: UiEvent) {
        reduce(event)
    }

    private fun reduce(event: UiEvent) {
        viewModelScope.launch {
            when (event) {
                is MovieListEvent.ShowMovieList -> {
                    TODO()
                }
                is MovieListEvent.OnMovieClicked -> {
                    movieEventChannel.send(event)
                }
                is MovieListEvent.OnIconButtonClicked -> {
                    if (event.remove) {

                    } else {

                    }
                }
            }
        }
    }

    fun getMovieDetails(id: Long) {
        sendEvent(MovieListEvent.OnMovieClicked(id))
    }

    fun addToFavourite(movie: Movie) {
        sendEvent(MovieListEvent.OnIconButtonClicked(movie))
    }

    fun getMovieList() {
        sendEvent(MovieListEvent.ShowMovieList(TODO()))
    }

    fun removeFromFavourite(movie: Movie) {
        sendEvent(MovieListEvent.OnIconButtonClicked(movie, true))
    }
}