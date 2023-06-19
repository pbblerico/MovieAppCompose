package com.example.movieappcompose.favouriteList.repository

import com.example.movieappcompose.models.Movie
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import com.example.movieappcompose.utils.Result
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class FavoritesRepositoryImpl(
    private val auth: FirebaseAuth,
    private val ref: FirebaseDatabase
): FavouritesRepository {
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


            } catch (e: Exception) {
                result.invoke(
                    Result.Failure(e.message ?: e.localizedMessage)
                )
            }
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
            } catch(e: Exception) {
                result.invoke(
                    Result.Failure(e.message ?: e.localizedMessage)
                )
            }
        }
    }
}