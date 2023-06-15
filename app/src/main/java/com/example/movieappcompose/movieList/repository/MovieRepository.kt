package com.example.movieappcompose.movieList.repository

import com.example.movieappcompose.models.Movie
import com.example.movieappcompose.utils.Result

interface MovieRepository {
    suspend fun getMovieList(page: Int = 1): Result<List<Movie>>

    fun getMoviePagingSource(): MoviePagingSource

    suspend fun addToFavourite(movie: Movie, result: (Result<String>) -> Unit)
}