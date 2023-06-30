package com.example.movieappcompose.movieList.viewModel

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movieappcompose.mvi.BaseViewModel
import com.example.movieappcompose.MovieListContract
import com.example.movieappcompose.movieList.repository.MovieRepository
import com.example.movieappcompose.shared.data.models.ListItem
import com.example.movieappcompose.shared.data.models.Movie
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MovieViewModel(private val repository: MovieRepository) :
    BaseViewModel<MovieListContract.Event, MovieListContract.State, MovieListContract.Effect>() {

    private val movieMultipleListPaging: StateFlow<PagingData<ListItem>> =
        Pager(
            PagingConfig(pageSize = 1)
        ) {
            repository.getMoviePagingSource()
        }.flow.cachedIn(viewModelScope).stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())


    override fun createInitialState(): MovieListContract.State {
        return MovieListContract.State(MovieListContract.MovieListState.Empty)
    }

    override fun handleEvent(event: MovieListContract.Event) {
        when (event) {
            is MovieListContract.Event.ShowMovieList -> {
                getMovieList()
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

    private fun getMovieList() {
        setState { copy(movieListState = MovieListContract.MovieListState.Loading) }
        viewModelScope.launch {
            movieMultipleListPaging.collectLatest {
                setState { copy(movieListState = MovieListContract.MovieListState.Success(pagingData = it)) }
            }
        }
    }


    fun addToFavourite(movie: Movie) {
        viewModelScope.launch {
            repository.addToFavourite(movie) {}
        }
    }
}
