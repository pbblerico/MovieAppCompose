package com.example.movieappcompose.shared.data.models

sealed interface ListItem {
    data class Movie(val movie: com.example.movieappcompose.shared.data.models.Movie) : ListItem

    data class Ad(val ad: Advert) : ListItem
}