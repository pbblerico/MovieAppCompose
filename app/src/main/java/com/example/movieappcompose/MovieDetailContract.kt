package com.example.movieappcompose

import com.example.movieappcompose.mvi.UiEvent
import com.example.movieappcompose.shared.data.models.Movie

class MovieDetailContract {
    sealed interface Event: UiEvent {
        object ShowMovieDetails: Event
    }

    data class State(
        val loading: Boolean,
        val data: Movie?
    )
}