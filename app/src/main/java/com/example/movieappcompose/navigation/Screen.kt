package com.example.movieappcompose.navigation

sealed class Screen(val route: String = "entry_screen") {
    object Root: Screen("root")
    object AuthScreen: Screen("auth_screen") {
        object LoginScreen : Screen("login_screen")
        object SignUpScreen : Screen("sign_up_screen")
    }
    object MainScreen: Screen("main_screen") {
        object MovieListScreen : Screen("movie_list_screen")
        object MovieDetailScreen : Screen("movie_detail_screen")
        object MovieFavouriteListScreen: Screen("movie_favourite_list")
    }
}