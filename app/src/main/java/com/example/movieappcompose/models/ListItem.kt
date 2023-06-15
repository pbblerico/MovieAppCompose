package com.example.movieappcompose.models

sealed interface ListItem {
    data class Movie(val movie: com.example.movieappcompose.models.Movie) : ListItem

    data class Ad(val ad: Advert) : ListItem
}