package com.example.movieappcompose.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.movieappcompose.movieDetail.ui.CollapsingToolbar
import com.example.movieappcompose.navigation.Screen

@Composable
fun MainNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Screen.MainScreen.route,
        startDestination = Screen.MainScreen.MovieListScreen.route
    ) {
//        composable(route = Screen.MainScreen.MovieListScreen.route) {
//            MainMovieList(
//                onClick = {
//                    navController.navigate(Screen.MainScreen.MovieDetailScreen.route)
//                }
//            )
//        }
        composable(route = Screen.MainScreen.MovieDetailScreen.route) {
            CollapsingToolbar()
        }
//        composable(route = Screen.MainScreen.MovieFavouriteListScreen.route) {
//            FavouriteMovieList(
//                onClick = {
//                    navController.navigate(Screen.MainScreen.MovieDetailScreen.route) {
//                        popUpTo(Screen.MainScreen.MovieListScreen.route)
//                    }
//                }
//            )
//        }
    }
}