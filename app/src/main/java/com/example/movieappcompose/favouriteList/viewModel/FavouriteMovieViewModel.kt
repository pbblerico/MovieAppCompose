package com.example.movieappcompose.favouriteList.viewModel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.movieappcompose.mvi.BaseViewModel
import com.example.movieappcompose.MovieListContract
import com.example.movieappcompose.movieList.repository.MovieRepository
import kotlinx.coroutines.launch
import com.example.movieappcompose.utils.Result
import kotlinx.coroutines.Dispatchers

class FavouriteMovieViewModel(
    private val repository: MovieRepository
): BaseViewModel<MovieListContract.Event, MovieListContract.State, MovieListContract.Effect>() {
    override fun createInitialState(): MovieListContract.State =
        MovieListContract.State(MovieListContract.MovieListState.Empty)


    override fun handleEvent(event: MovieListContract.Event) {
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
                               movieList = movies
                           )
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
}