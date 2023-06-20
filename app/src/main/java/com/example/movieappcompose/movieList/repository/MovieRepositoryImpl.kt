package com.example.movieappcompose.movieList.repository

import com.example.movieappcompose.shared.data.models.Movie
import com.example.movieappcompose.shared.data.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.movieappcompose.utils.Result
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MovieRepositoryImpl(
    private val apiService: ApiService,
    private val pagingSource: MoviePagingSource,
    private val auth: FirebaseAuth,
    private val ref: FirebaseDatabase
    ): MovieRepository {
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
            ref.getReference("Users")
                .child(auth.uid!!)
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