package com.example.movieappcompose

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home: BottomBarScreen(
        route = Screen.MainScreen.MovieListScreen.route,
        title = "Home",
        icon = Icons.Rounded.Home
    )
    object Favourite: BottomBarScreen(
        route = Screen.MainScreen.MovieFavouriteListScreen.route,
        title = "Liked",
        icon = Icons.Rounded.Favorite
    )
}