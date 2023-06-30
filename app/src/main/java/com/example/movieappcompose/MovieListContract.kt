package com.example.movieappcompose

import androidx.paging.PagingData
import com.example.movieappcompose.mvi.UiEffect
import com.example.movieappcompose.mvi.UiEvent
import com.example.movieappcompose.mvi.UiState
import com.example.movieappcompose.shared.data.models.ListItem
import com.example.movieappcompose.shared.data.models.Movie

class  MovieListContract {

    sealed interface Event: UiEvent {
        class OnMovieClicked(val id: Long): Event
        class OnIconButtonClicked(val data: Movie, val remove: Boolean = false): Event
        object ShowMovieList: Event
    }

    data class State(
        val movieListState: MovieListState
    ) : UiState

    sealed class Effect: UiEffect {
        object Empty: Effect()
        class NavigateToDetails(val id: Long? = 0): Effect()
        class OnIconButtonClick(val movie: Movie? = null, val isFavouriteList: Boolean? = false): Effect()
    }

    sealed class MovieListState {
        object Empty: MovieListState()
        object Loading: MovieListState()
        data class Error(val message: String? = null): MovieListState()
        data class Success(val movieList: List<Movie>? = null, val pagingData: PagingData<ListItem>? = null): MovieListState()
    }

}