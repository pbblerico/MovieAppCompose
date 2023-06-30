package com.example.movieappcompose.movieList.repository

import com.example.movieappcompose.shared.data.models.Movie
import com.example.movieappcompose.shared.data.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.movieappcompose.utils.Result
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class MovieRepositoryImpl(
    private val apiService: ApiService,
    private val pagingSource: MoviePagingSource,
    private val auth: FirebaseAuth,
    private val ref: FirebaseDatabase
    ): MovieRepository {

    private val favourites = auth.uid?.let {uid ->
        ref.getReference("Users")
            .child(uid)
            .child("Liked")
    }

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

    override suspend fun addToFavourite(movie: Movie): Flow<Result<String>> = callbackFlow {

    }

    override suspend fun getFavouritesList(result: (Result<List<Movie>>) -> Unit) {
        withContext(Dispatchers.IO) {
            try {
                val favArrayList: ArrayList<Movie> = ArrayList()

                ref.getReference("Users")
                    .child(auth.uid!!)
                    .child("Liked")
                    .addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            favArrayList.clear()

                            for (ds in snapshot.children) {
                                val model = ds.getValue(Movie::class.java)

                                favArrayList.add(model!!)
                            }

                            result.invoke(
                                Result.Success(favArrayList.toList())
                            )
                        }

                        override fun onCancelled(error: DatabaseError) {}
                    })


            } catch (e: java.lang.Exception) {
                result.invoke(
                    Result.Failure(e.message ?: e.localizedMessage)
                )
            }
        }
    }

    override suspend fun getFavouritesList(): Flow<Result<List<Movie>>> = callbackFlow {
        val listener = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val movies = snapshot.children.map {
                    it.getValue(Movie::class.java)
                }

                if(movies.isNotEmpty()) trySend(Result.Success(movies as List<Movie>))

                trySend(Result.Empty())
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(Result.Failure(error.message))
            }
        }

        favourites?.addValueEventListener(listener)

        awaitClose {
            favourites?.removeEventListener(listener)
            channel.close()
        }
    }

    override suspend fun removeFromFavourite(id: String, result: (Result<String>) -> Unit) {
        withContext(Dispatchers.IO) {
            try {
                ref.getReference("Users")
                    .child(auth.uid!!)
                    .child("Liked")
                    .child(id)
                    .removeValue().addOnSuccessListener {
                        result.invoke(
                            Result.Success("Deleted")
                        )
                    }
                    .addOnFailureListener {
                        result.invoke(
                            Result.Failure("Error")
                        )
                    }
            } catch(e: java.lang.Exception) {
                result.invoke(
                    Result.Failure(e.message ?: e.localizedMessage)
                )
            }
        }
    }

    override suspend fun removeFromFavourite(id: String): Flow<Result<String>> {
        TODO("Not yet implemented")
    }

}