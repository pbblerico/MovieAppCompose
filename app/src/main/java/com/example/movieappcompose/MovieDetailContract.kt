package com.example.movieappcompose

import com.example.movieappcompose.mvi.UiEvent
import com.example.movieappcompose.shared.data.models.Movie

class MovieDetailContract {
    sealed interface Event: UiEvent {
        object ShowMovieDetails: Event
    }

    data class State(
        val loading: Boolean,
        val data: Movie?,
        val error: String?
    ){
        companion object {
            fun success(data: Movie): State {
                return State(
                    loading = false,
                    data = data,
                    error = null
                )
            }

            fun loading(): State = State(
                loading = true,
                data = null,
                error = null
            )

            fun error(error: String?): State = State(
                loading = false,
                data = null,
                error = error ?: "Unknown error"
            )
        }
    }
}