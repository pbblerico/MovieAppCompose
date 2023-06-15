package com.example.movieappcompose.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.movieappcompose.LoginPage
import com.example.movieappcompose.Screen
import com.example.movieappcompose.SignUpPage

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        route = Screen.AuthScreen.route,
        startDestination = Screen.AuthScreen.LoginScreen.route
    ) {
        composable(route = Screen.AuthScreen.LoginScreen.route) {
            LoginPage(
                login = { navController.navigate(Screen.MainScreen.route) },
                signUp = { navController.navigate(Screen.AuthScreen.SignUpScreen.route)}
            )
        }
        composable(route = Screen.AuthScreen.SignUpScreen.route) {
            SignUpPage(
                login = { navController.navigate(Screen.AuthScreen.LoginScreen.route)},
                signUp = { navController.navigate(Screen.AuthScreen.LoginScreen.route)}
            )
        }
    }
}