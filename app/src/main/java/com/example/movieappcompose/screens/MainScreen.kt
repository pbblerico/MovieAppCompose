package com.example.movieappcompose.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.movieappcompose.BottomBarScreen
import com.example.movieappcompose.MainBottomBarNav
import com.example.movieappcompose.graphs.MainNavGraph

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val bottomNavItems = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Favourite
    )

    Scaffold(
        bottomBar = { MainBottomBarNav(navController = navController, items = bottomNavItems)},
    ) {
        Column(modifier = Modifier.padding(it)) {
            MainNavGraph(navController = navController)
        }
    }
}