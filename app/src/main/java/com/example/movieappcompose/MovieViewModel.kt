package com.example.movieappcompose

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movieappcompose.movieList.repository.MovieRepository
import com.example.movieappcompose.shared.data.models.ListItem
import com.example.movieappcompose.shared.data.models.Movie
import com.example.movieappcompose.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MovieViewModel(private val repository: MovieRepository) :
    BaseViewModel<MovieListContract.Event, MovieListContract.State, MovieListContract.Effect>() {

    private val _favouritesListStatus = MutableStateFlow<Result<List<Movie>>>(Result.Loading())
    val favouritesListStatus: StateFlow<Result<List<Movie>>> = _favouritesListStatus

    private val _favouritesListState = MutableStateFlow<UiState>(createInitialState())
    val favouritesListState: StateFlow<UiState> = _favouritesListState

    val movieMultipleListPaging: StateFlow<PagingData<ListItem>> =
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
                setState { copy(movieListState = MovieListContract.MovieListState.Loading) }
            }
            is MovieListContract.Event.OnMovieClicked -> {
                setEffect { MovieListContract.Effect.NavigateToDetails(event.id) }
            }
            is MovieListContract.Event.OnIconButtonClicked -> {
                event.data.let { movie ->
                    setEffect { MovieListContract.Effect.OnIconButtonClick(movie, event.remove) }
                }
            }
            else -> Unit
        }
    }

    fun getFavoritesList() {
//        _favouritesListStatus.value = Result.Loading()
        viewModelScope.launch(Dispatchers.Main) {
            repository.getFavouritesList().collectLatest {
                _favouritesListStatus.value = it
//                _favouritesListStatus.
//                val newState = _favouritesListStatus.value
//                _favouritesListStatus.value.data -
            }
        }
    }

    fun removeFromFavourite(id: String) {
        viewModelScope.launch(Dispatchers.Main) {
            repository.removeFromFavourite(id) {}
        }
    }

    fun addToFavourite(movie: Movie) {

        viewModelScope.launch {
            repository.addToFavourite(movie) {}
        }
    }
}
