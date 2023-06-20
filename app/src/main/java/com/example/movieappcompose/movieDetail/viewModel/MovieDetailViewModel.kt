package com.example.movieappcompose.movieDetail.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappcompose.models.Movie
import com.example.movieappcompose.movieDetail.repository.MovieDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.movieappcompose.utils.Result

class MovieDetailViewModel (private val movieDetailRepository: MovieDetailRepository): ViewModel() {
    private val _movieDetailState = MutableLiveData<Result<Movie>>()
    val movieDetailState: LiveData<Result<Movie>> = _movieDetailState

    fun getMovieDetail(id: Long) {
        _movieDetailState.postValue(Result.Loading())
        viewModelScope.launch(Dispatchers.Main) {
            val result = movieDetailRepository.getMovieDetail(id)
            _movieDetailState.postValue(result)
        }
    }
}