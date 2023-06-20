package com.example.movieappcompose.movieDetail.repository

import com.example.movieappcompose.shared.data.models.Movie
import com.example.movieappcompose.shared.data.retrofit.ApiService
import com.example.movieappcompose.utils.Result

class MovieDetailRepositoryImpl(private val apiService: ApiService): MovieDetailRepository {
    override suspend fun getMovieDetail(id: Long): Result<Movie> = try {
        val result = apiService.getMovieDetails(id)
        Result.Success(result)
    } catch (e: Exception) {
        Result.Failure(e.message ?: e.localizedMessage)
    }
}