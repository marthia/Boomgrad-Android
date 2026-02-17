package me.marthia.app.boomgrad.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import me.marthia.app.boomgrad.presentation.MainContainer
import me.marthia.app.boomgrad.presentation.attraction.detail.AttractionDetailScreen
import me.marthia.app.boomgrad.presentation.login.LoginScreen
import me.marthia.app.boomgrad.presentation.tour.detail.TourDetailScreen

@Composable
fun MainGraph(navigator: AppNavigator = rememberAppNavigator()) {
    NavHost(
        navController = navigator.navController,
        startDestination = HomeDestination.route,
    ) {

        composableWithCompositionLocal(HomeDestination.route) {
            MainContainer(
                onTourSelected = navigator::navigateToTourDetail,
                onAttractionSelected = navigator::navigateToAttraction,
                onNavigateToLogin = navigator::navigateToLogin,
                onCategorySelected = { id, from ->
                    navigator.navigateToTours(categoryId = id)
                }
            )
        }

        composableWithCompositionLocal(
            route = TourDetailDestination.ROUTE_PATTERN,
            arguments = listOf(
                navArgument(TourDetailDestination.ARG_TOUR_ID) {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->

            val tourId =
                requireNotNull(backStackEntry.arguments)
                    .getLong(TourDetailDestination.ARG_TOUR_ID)

            TourDetailScreen(
                tourId = tourId,
                upPress = navigator::navigateUp
            )
        }

        composableWithCompositionLocal(
            route = AttractionDetailDestination.ROUTE_PATTERN,
            arguments = listOf(
                navArgument(AttractionDetailDestination.ARG_ATTRACTION_ID) {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->

            val attractionId =
                requireNotNull(backStackEntry.arguments)
                    .getLong(AttractionDetailDestination.ARG_ATTRACTION_ID)

            AttractionDetailScreen(
                attractionId = attractionId,
                onBackClick = navigator::navigateUp
            )
        }

        composable(LoginDestination.route) {
            LoginScreen(
                navigator::navigateUp
            )
        }
    }
}