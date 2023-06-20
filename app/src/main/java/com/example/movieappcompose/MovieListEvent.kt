package com.example.movieappcompose

import com.example.movieappcompose.shared.data.models.Movie

sealed class MovieListEvent: UiEvent {
    class ShowMovieList(val data: List<Movie>): MovieListEvent()
    class OnMovieClicked(val id: Long): MovieListEvent()
    class OnIconButtonClicked(val data: Movie, val remove: Boolean = false): MovieListEvent()
}