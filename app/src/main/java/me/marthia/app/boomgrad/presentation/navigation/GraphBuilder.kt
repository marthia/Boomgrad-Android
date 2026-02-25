package me.marthia.app.boomgrad.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import me.marthia.app.boomgrad.presentation.cart.CartScreen
import me.marthia.app.boomgrad.presentation.home.HomeScreen
import me.marthia.app.boomgrad.presentation.mytours.MyToursScreen
import me.marthia.app.boomgrad.presentation.profile.ProfileScreen

fun NavGraphBuilder.addHomeGraph(
    onNavigateToLogin: () -> Unit,
) {
    composable(FeedDestination.route) { from ->
        HomeScreen()
    }
    composable(MyTours.route) { from ->
        MyToursScreen()
    }

    composable(BookingsDestination.route) { from ->
        CartScreen(onBackClick = {}, onCheckout = {})
    }


    composable(ProfileDestination.route) {
        ProfileScreen(
            onNavigateToLogin = onNavigateToLogin,
            onEditProfileClick = {},
            onMyTripsClick = {},
            onLogoutClick = {},
        )
    }
}