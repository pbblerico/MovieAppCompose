package com.example.movieappcompose.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.movieappcompose.Screen
import com.example.movieappcompose.screens.MainScreen

@Composable
fun RootNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.AuthScreen.route
    ) { 
        authNavGraph(navController = navController)
        composable(route = Screen.MainScreen.route) {
            MainScreen()
        }
    }
}

