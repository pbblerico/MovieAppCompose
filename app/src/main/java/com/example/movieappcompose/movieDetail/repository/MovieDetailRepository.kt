package com.example.movieappcompose.movieDetail.repository

import com.example.movieappcompose.shared.data.models.Movie
import com.example.movieappcompose.utils.Result

interface MovieDetailRepository {
    suspend fun getMovieDetail(id: Long): Result<Movie>
}