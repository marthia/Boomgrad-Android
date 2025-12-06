package me.marthia.app.boomgrad.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import me.marthia.app.boomgrad.presentation.navigation.NavigationGraph
import me.marthia.app.boomgrad.presentation.navigation.Screen
import me.marthia.app.boomgrad.presentation.theme.TourAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TourAppTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                
                Scaffold(
                    bottomBar = {
                        // Hide bottom bar on detail screen
                        if (currentRoute != null && !currentRoute.startsWith("attraction/")) {
                            NavigationBar {
                                NavigationBarItem(
                                    icon = { Icon(Icons.Default.Home, "Attractions") },
                                    label = { Text("Attractions") },
                                    selected = currentRoute == Screen.Attractions.route,
                                    onClick = {
                                        navController.navigate(Screen.Attractions.route) {
                                            popUpTo(Screen.Attractions.route) { inclusive = true }
                                        }
                                    }
                                )
                                NavigationBarItem(
                                    icon = { Icon(Icons.Default.Search, "Search") },
                                    label = { Text("Search") },
                                    selected = currentRoute == Screen.Search.route,
                                    onClick = {
                                        navController.navigate(Screen.Search.route) {
                                            popUpTo(Screen.Attractions.route)
                                        }
                                    }
                                )
                                NavigationBarItem(
                                    icon = { Icon(Icons.Default.Favorite, "Favorites") },
                                    label = { Text("Favorites") },
                                    selected = currentRoute == Screen.Favorites.route,
                                    onClick = {
                                        navController.navigate(Screen.Favorites.route) {
                                            popUpTo(Screen.Attractions.route)
                                        }
                                    }
                                )
                            }
                        }
                    }
                ) { paddingValues ->
                    NavigationGraph(
                        navController = navController,
                        startDestination = Screen.Attractions.route
                    )
                }
            }
        }
    }
}