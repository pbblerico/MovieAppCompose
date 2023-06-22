package com.example.movieappcompose

import androidx.paging.PagingData
import com.example.movieappcompose.shared.data.models.ListItem
import com.example.movieappcompose.shared.data.models.Movie

class MovieListContract {

    sealed class Event: UiEvent {
        object Initial: Event()
        class OnMovieClicked(val id: Long): Event()
        class OnIconButtonClicked(val data: Movie, val remove: Boolean = false): Event()
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
        data class Success(val pagingList: PagingData<ListItem>): MovieListState()
    }

}