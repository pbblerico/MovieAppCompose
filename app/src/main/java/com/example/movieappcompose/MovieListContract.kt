package com.example.movieappcompose

import androidx.paging.PagingData
import com.example.movieappcompose.shared.data.models.ListItem
import com.example.movieappcompose.shared.data.models.Movie

class MovieListContract {

    sealed class Event(val movieId: Long? = 0): UiEvent {
        object ShowMovieList: Event()
        class OnMovieClicked(val id: Long): Event(movieId = id)
        class OnIconButtonClicked(val data: Movie, val remove: Boolean = false): Event()
    }

    data class State(
        val movieListState: MovieListState
    ) : UiState

    sealed class Effect: UiEffect {
        object NavigateToDetails: Effect()
    }

    sealed class MovieListState {
        object Empty: MovieListState()
        object Loading: MovieListState()
        data class Error(val message: String? = null): MovieListState()
        data class Success(val pagingList: PagingData<ListItem>): MovieListState()
    }

}