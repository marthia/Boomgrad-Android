package me.marthia.app.boomgrad.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun rememberAppNavigator(
    navController: NavHostController = rememberNavController()
): AppNavigator = remember(navController) {
    AppNavigator(navController)
}

@Stable
class AppNavigator(
    val navController: NavHostController
) {

    fun navigate(destination: AppDestination) {
        navController.navigate(destination.route)
    }

    fun navigateUp() {
        navController.navigateUp()
    }

    fun navigateToBottomBarRoute(route: String) {
        if (route == navController.currentDestination?.route) return

        navController.navigate(route) {
            launchSingleTop = true
            restoreState = true

            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
        }
    }

    fun navigateToTourDetail(
        tourId: Long,
        from: NavBackStackEntry
    ) {
        if (!from.lifecycleIsResumed()) return

        navController.navigate(
            TourDetailDestination(tourId).route
        )
    }

    fun navigateToAttraction(
        attractionId: Long,
        from: NavBackStackEntry
    ) {
        if (!from.lifecycleIsResumed()) return

        navController.navigate(
            AttractionDetailDestination(attractionId)
        )
    }

    fun navigateToLogin() {
        navController.navigate(
            LoginDestination.route
        )
    }

    fun navigateToTours(categoryId: Long) {
        navController.navigate(ToursDestination.route)
    }
}

