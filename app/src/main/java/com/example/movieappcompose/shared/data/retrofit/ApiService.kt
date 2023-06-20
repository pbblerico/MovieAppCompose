package com.example.movieappcompose.shared.data.retrofit

import com.example.movieappcompose.shared.data.models.Movie
import com.example.movieappcompose.shared.data.models.MovieListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") id: Long): Movie

    @GET("movie/popular")
    suspend fun getPopularMovie(@Query("page") page: Int): MovieListResponse
}
