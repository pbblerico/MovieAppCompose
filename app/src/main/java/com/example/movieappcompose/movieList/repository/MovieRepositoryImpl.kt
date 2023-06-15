package com.example.movieappcompose.movieList.repository

import com.example.movieappcompose.models.Movie
import com.example.movieappcompose.retrofit.ApiService
import com.example.movieappcompose.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.movieappcompose.utils.Result

class MovieRepositoryImpl(private val apiService: ApiService, private val pagingSource: MoviePagingSource): MovieRepository {
    override suspend fun getMovieList(page: Int): Result<List<Movie>> = try {
        val result = apiService.getPopularMovie(page).results
        if (result.isEmpty()) Result.Empty()
        else Result.Success(result)
    } catch (e: Exception) {
        Result.Failure(e.message ?: e.localizedMessage)
    }

    override fun getMoviePagingSource() = pagingSource

    override suspend fun addToFavourite(movie: Movie, result: (Result<String>) -> Unit) {
        withContext(Dispatchers.IO) {
            Constants.ref.getReference("Users")
                .child(Constants.auth.uid!!)
                .child("Liked")
                .child(movie.id.toString())
                .setValue(movie)
                .addOnSuccessListener {
                    result.invoke(
                        Result.Success("added")
                    )
                }.addOnFailureListener { e ->
                    result.invoke(
                        Result.Failure(e.message ?: e.localizedMessage)
                    )
                }
        }
    }

}