package com.example.movieappcompose.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.movieappcompose.navigation.Screen
import com.example.movieappcompose.screens.MainScreen

@Composable
fun RootNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Screen.Root.route,
        startDestination = Screen.AuthScreen.route
    ) { 
        authNavGraph(navController = navController)
        composable(route = Screen.MainScreen.route) {
            MainScreen()
        }
    }
}

