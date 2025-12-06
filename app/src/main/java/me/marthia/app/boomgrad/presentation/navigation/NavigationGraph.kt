package me.marthia.app.boomgrad.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import me.marthia.app.boomgrad.presentation.SearchScreen
import me.marthia.app.boomgrad.presentation.attractions.AttractionsScreen
import me.marthia.app.boomgrad.presentation.detail.AttractionDetailScreen
import me.marthia.app.boomgrad.presentation.favorites.FavoritesScreen

@Composable
fun NavigationGraph(
    navController: NavHostController,
    startDestination: String = Screen.Attractions.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Attractions.route) {
            AttractionsScreen(
                onAttractionClick = { attractionId ->
                    navController.navigate(Screen.AttractionDetail.createRoute(attractionId))
                }
            )
        }

        composable(Screen.Search.route) {
            SearchScreen(
                onAttractionClick = { attractionId ->
                    navController.navigate(Screen.AttractionDetail.createRoute(attractionId))
                }
            )
        }

        composable(Screen.Favorites.route) {
            FavoritesScreen(
                onAttractionClick = { attractionId ->
                    navController.navigate(Screen.AttractionDetail.createRoute(attractionId))
                }
            )
        }

        composable(
            route = Screen.AttractionDetail.route,
            arguments = listOf(
                navArgument("attractionId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val attractionId =
                backStackEntry.arguments?.getString("attractionId") ?: return@composable
            AttractionDetailScreen(
                attractionId = attractionId,
                onBackClick = { navController.navigateUp() }
            )
        }
    }
}