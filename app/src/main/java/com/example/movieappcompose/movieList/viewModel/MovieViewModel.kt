package com.example.movieappcompose.movieList.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movieappcompose.models.ListItem
import com.example.movieappcompose.models.Movie
import com.example.movieappcompose.movieList.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.movieappcompose.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.stateIn

class MovieViewModel(private val movieRepository: MovieRepository): ViewModel() {
    private val _movieListStatus = MutableLiveData<Result<List<Movie>>>()
    val movieListStatus: LiveData<Result<List<Movie>>> = _movieListStatus


    private val _addToFavStatus = MutableLiveData<Result<String>>()
    val addToFavStatus: LiveData<Result<String>> = _addToFavStatus

    fun getMovieList(page: Int) {
        _movieListStatus.postValue(Result.Loading())
        viewModelScope.launch(Dispatchers.Main) {
            val result = movieRepository.getMovieList(page)
            _movieListStatus.postValue(result)
        }
    }

    val movieMultipleListPaging: StateFlow<PagingData<ListItem>> =
        Pager(
            PagingConfig(pageSize = 1)
        ) {
            movieRepository.getMoviePagingSource()
        }.flow.cachedIn(viewModelScope).stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    fun movieList(): Flow<PagingData<ListItem>> =
        Pager(
            PagingConfig(pageSize = 1)
        ) {
            movieRepository.getMoviePagingSource()
        }.flow.cachedIn(viewModelScope)

    fun addToFavourite(movie: Movie) {
        viewModelScope.launch(Dispatchers.Main) {
            movieRepository.addToFavourite(movie){
                _addToFavStatus.postValue(it)
            }
        }
    }
}