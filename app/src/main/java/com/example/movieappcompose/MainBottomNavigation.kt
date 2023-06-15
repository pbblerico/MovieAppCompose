package com.example.movieappcompose

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun MainBottomBarNav(
    navController: NavHostController,
    items: List<BottomBarScreen>
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation {
        items.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(screen.icon, "")},
                selected = currentDestination?.hierarchy?.any {
                    it.route == screen.route } == true,
                onClick = { navController.navigate(screen.route) })
        }
    }
}