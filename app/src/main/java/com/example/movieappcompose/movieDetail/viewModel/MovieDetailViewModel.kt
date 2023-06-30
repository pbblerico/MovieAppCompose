package com.example.movieappcompose.movieDetail.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappcompose.MovieListContract
import com.example.movieappcompose.mvi.UiEvent
import com.example.movieappcompose.mvi.UiState
import com.example.movieappcompose.shared.data.models.Movie
import com.example.movieappcompose.movieDetail.repository.MovieDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.movieappcompose.utils.Result
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

class MovieDetailViewModel (private val movieDetailRepository: MovieDetailRepository): ViewModel() {
    private val _movieDetailState = MutableLiveData<Result<Movie>>()
    val movieDetailState: LiveData<Result<Movie>> = _movieDetailState

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(MovieListContract.State(MovieListContract.MovieListState.Empty))
    val uiState = _uiState.asStateFlow()

    private val _event: MutableSharedFlow<UiEvent> = MutableSharedFlow()
    val event = _event.asSharedFlow()

    fun getMovieDetail(id: Long) {
        _movieDetailState.postValue(Result.Loading())
        viewModelScope.launch(Dispatchers.Main) {
            val result = movieDetailRepository.getMovieDetail(id)
            _movieDetailState.postValue(result)
        }
    }
}