package com.example.movieappcompose.models

import androidx.compose.ui.unit.Constraints
import com.example.movieappcompose.utils.Constants
import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("id")
    val id: Long = 603692, // 453395
    @SerializedName("original_language")
    val language: String = "en", // en
    @SerializedName("overview")
    val overview: String = "With the price on his head ever increasing, John Wick uncovers a path to defeating The High Table. But before he can earn his freedom, Wick must face off against a new enemy with powerful alliances across the globe and forces that turn old friends into foes.", // Doctor Strange, with the help of mystical allies both old and new, traverses the mind-bending and dangerous alternate realities of the Multiverse to confront a mysterious new adversary.
    @SerializedName("poster_path")
    val posterPath: String = Constants.POSTER_BASE_URL + "/vZloFAK7NmvMGKE7VkF5UHaz0I.jpg", // /9Gtg2DzBhmYamXBS1hKAhiwbBKS.jpg
    @SerializedName("release_date")
    val releaseDate: String = "2023-03-22", // 2022-05-04
    @SerializedName("title")
    val title: String = "John Wick: Chapter 4", // Doctor Strange in the Multiverse of Madness
    @SerializedName("vote_average")
    val rating: Double = 7.92, // 7.5
)
