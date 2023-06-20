package com.example.movieappcompose.shared.data.models

import com.example.movieappcompose.shared.data.models.Movie
import com.google.gson.annotations.SerializedName

data class MovieListResponse(
    @SerializedName("results")
    val results: List<Movie>,
    @SerializedName("page")
    val page: Int, // 1
    @SerializedName("total_pages")
    val totalPages: Int, // 34218
)
