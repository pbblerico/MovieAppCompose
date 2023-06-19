package com.example.movieappcompose.movieList.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.movieappcompose.MovieListEvent
import com.example.movieappcompose.UiEvent
import com.example.movieappcompose.models.Movie
import com.example.movieappcompose.movieList.repository.MovieRepository

class MovieViewModelMVI(
    private val movieRepository: MovieRepository
): ViewModel() {

    private fun sendEvent(event: UiEvent) {
        reduce(event)
    }

    private fun reduce(event: UiEvent) {
        when(event) {
            is MovieListEvent.ShowMovieList -> {
                TODO()
            }
            is MovieListEvent.OnMovieClicked -> {
                TODO("Navigation")
            }
            is MovieListEvent.OnIconButtonClicked -> {
                if(event.remove) {

                } else {

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