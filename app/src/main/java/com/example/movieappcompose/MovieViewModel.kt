package com.example.movieappcompose

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.movieappcompose.movieList.repository.MovieRepository
import com.example.movieappcompose.shared.data.models.ListItem
import com.example.movieappcompose.shared.data.models.Movie
import kotlinx.coroutines.launch
import com.example.movieappcompose.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class MovieViewModel(private val repository: MovieRepository) :
    BaseViewModel<MovieListContract.Event, MovieListContract.State, MovieListContract.Effect>() {

    private val _favouritesListStatus = MutableLiveData<Result<List<Movie>>>()
    val favouritesListStatus: LiveData<Result<List<Movie>>> = _favouritesListStatus

    private val movieMultipleListPaging: StateFlow<PagingData<ListItem>> =
        flow<PagingData<ListItem>> {
            Pager(
                PagingConfig(
                    pageSize = 1
                )
            ) {
                repository.getMoviePagingSource()
            }
        }
            .cachedIn(viewModelScope)
            .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    override fun createInitialState(): MovieListContract.State {
        return MovieListContract.State(MovieListContract.MovieListState.Empty)
    }

    override fun createInitialEvent(): MovieListContract.Event {
        return MovieListContract.Event.Initial
    }

    override fun handleEvent(event: MovieListContract.Event) {
        when (event) {
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
        _favouritesListStatus.postValue(Result.Loading())
        viewModelScope.launch(Dispatchers.Main) {
            repository.getFavouritesList {
                _favouritesListStatus.postValue(it)
            }
        }
    }

    fun removeFromFavourite(id: String) {
        viewModelScope.launch(Dispatchers.Main) {
            repository.removeFromFavourite(id) {
            }
        }
    }

    fun addToFavourite(movie: Movie) {

        viewModelScope.launch {
            repository.addToFavourite(movie) {
                when (it) {
                    is Result.Success -> {
                    }
                    else -> {}
                }
            }
        }
    }

    fun removeFromFavourite(movie: Movie) {
        setEffect { MovieListContract.Effect.OnIconButtonClick(movie, true) }
        viewModelScope.launch {
            TODO("Remove from favourite")
        }
    }
}
