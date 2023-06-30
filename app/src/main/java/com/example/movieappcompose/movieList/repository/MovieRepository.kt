package com.example.movieappcompose.movieList.repository

import com.example.movieappcompose.shared.data.models.Movie
import com.example.movieappcompose.utils.Result
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getMovieList(page: Int = 1): Result<List<Movie>>

    fun getMoviePagingSource(): MoviePagingSource

    suspend fun addToFavourite(movie: Movie, result: (Result<String>) -> Unit)

    suspend fun addToFavourite(movie: Movie): Flow<Result<String>>

    suspend fun getFavouritesList(result: (Result<List<Movie>>) -> Unit)

    suspend fun getFavouritesList(): Flow<Result<List<Movie>>>

    suspend fun removeFromFavourite(id: String, result: (Result<String>) -> Unit)

    suspend fun removeFromFavourite(id: String): Flow<Result<String>>
}