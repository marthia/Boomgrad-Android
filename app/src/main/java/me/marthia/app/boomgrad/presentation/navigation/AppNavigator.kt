package me.marthia.app.boomgrad.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import timber.log.Timber

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
        if (route == navController.currentDestination?.route) {
            Timber.d("$route duplicate navigation: skipping")
            return
        }

        navController.navigate(route) {
            launchSingleTop = true
            restoreState = true
            // Pop up backstack to the first destination and save state. This makes going back
            // to the start destination when pressing back in any other bottom tab.
            popUpTo(findStartDestination(navController.graph).id) {
                saveState = true
            }
        }
    }

    fun navigateToTourDetail(
        tourId: Long,
        from: NavBackStackEntry
    ) {
        if (!from.lifecycleIsResumed()) return

        navigate(destination = TourDetailDestination(tourId))
    }

    fun navigateToLogin() {
        navigate(LoginDestination)
    }

    fun navigateToTours(categoryId: Long) {
        navigate(destination = ToursDestination)
    }

    fun navigateToGuideInfo(guideId: Long) {
        navigate(destination = GuideInfoDestination(guideId = guideId))
    }
}

