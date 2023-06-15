package com.example.movieappcompose.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.movieappcompose.LoginPage
import com.example.movieappcompose.navigation.Screen
import com.example.movieappcompose.SignUpPage

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        route = Screen.AuthScreen.route,
        startDestination = Screen.AuthScreen.LoginScreen.route
    ) {
        composable(route = Screen.AuthScreen.LoginScreen.route) {
            LoginPage(
                login = {
                    navController.navigate(Screen.MainScreen.route) {
                        popUpTo(Screen.MainScreen.route)
                    } },
                signUp = {
                    navController.navigate(Screen.AuthScreen.SignUpScreen.route) {
                        popUpTo(Screen.AuthScreen.route)
                    } }
            )
        }
        composable(route = Screen.AuthScreen.SignUpScreen.route) {
            SignUpPage(
                login = {
                    navController.navigate(Screen.AuthScreen.LoginScreen.route) {
                        popUpTo(Screen.AuthScreen.SignUpScreen.route)
                    } },
                signUp = {
                    navController.navigate(Screen.AuthScreen.LoginScreen.route) {
                        popUpTo(Screen.AuthScreen.route)
                    }
                }
            )
        }
    }
}