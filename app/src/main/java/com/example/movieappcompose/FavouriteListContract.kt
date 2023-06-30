package com.example.movieappcompose

import com.example.movieappcompose.shared.data.models.Movie

class FavouriteListContract {

    sealed interface FavouriteListState: UiState {
        object Loading : FavouriteListState
        data class Success(val data: List<Movie>?) : FavouriteListState
        data class Error(val message: String?) : FavouriteListState
        object Idle: FavouriteListState
    }
}