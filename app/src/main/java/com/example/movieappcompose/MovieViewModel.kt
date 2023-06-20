package com.example.movieappcompose

import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.movieappcompose.movieList.repository.MovieRepository
import com.example.movieappcompose.shared.data.models.ListItem
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MovieViewModel(private val repository: MovieRepository): BaseViewModel<MovieListContract.Event, MovieListContract.State, MovieListContract.Effect>() {

    private val movieMultipleListPaging: StateFlow<PagingData<ListItem>> =
        Pager(
            PagingConfig(pageSize = 1)
        ) {
            repository.getMoviePagingSource()
        }.flow.cachedIn(viewModelScope).stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    override fun createInitialState(): MovieListContract.State {
        return MovieListContract.State(MovieListContract.MovieListState.Empty)
    }

    init {
        setEvent(MovieListContract.Event.ShowMovieList)
    }

    override fun handleEvent(event: MovieListContract.Event) {
        when(event) {
            is MovieListContract.Event.ShowMovieList -> { showMovieList() }
            is MovieListContract.Event.OnMovieClicked -> {
                setEffect { MovieListContract.Effect.NavigateToDetails }
            }
            is MovieListContract.Event.OnIconButtonClicked -> {}
        }
    }

    private fun showMovieList() {
        setState { copy(movieListState = MovieListContract.MovieListState.Loading) }
        viewModelScope.launch {
            movieMultipleListPaging.collectLatest {
                try {
                    setState { copy(movieListState = MovieListContract.MovieListState.Success(it)) }
                }catch (e: Exception) {
                    setState { copy(movieListState = MovieListContract.MovieListState.Error(e.message ?: e.localizedMessage)) }
                }
            }
        }
    }

}