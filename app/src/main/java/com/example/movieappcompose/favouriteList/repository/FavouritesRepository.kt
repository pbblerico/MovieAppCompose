package com.example.movieappcompose.favouriteList.repository

import com.example.movieappcompose.models.Movie
import com.example.movieappcompose.utils.Result

interface FavouritesRepository {
    suspend fun getFavouritesList(result: (Result<List<Movie>>) -> Unit)

    suspend fun removeFromFavourite(id: String, result: (Result<String>) -> Unit)

}