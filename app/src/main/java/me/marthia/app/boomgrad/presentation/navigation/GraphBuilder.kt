package me.marthia.app.boomgrad.presentation.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import me.marthia.app.boomgrad.presentation.attraction.list.AttractionList
import me.marthia.app.boomgrad.presentation.cart.CartScreen
import me.marthia.app.boomgrad.presentation.home.HomeScreen
import me.marthia.app.boomgrad.presentation.profile.ProfileScreen

fun NavGraphBuilder.addHomeGraph(
    onTourSelected: (Long, NavBackStackEntry) -> Unit,
    onCategorySelected: (Long, NavBackStackEntry) -> Unit,
    onNavigateToLogin: () -> Unit,
    onAttractionSelected: (Long, NavBackStackEntry) -> Unit,
) {
    composable(FeedDestination.route) { from ->
        HomeScreen(
            onTourSelected = { id -> onTourSelected(id, from) },
            onCategorySelected = { id -> onCategorySelected(id, from) },
            onAttractionSelected = { id -> onAttractionSelected(id, from) },

            )
    }
    composable(AttractionsDestination.route) { from ->
        AttractionList(onAttractionSelected = { id -> onAttractionSelected(id, from) })
    }

    composable(CartDestination.route) { from ->
        CartScreen(onBackClick = {}, onCheckout = {})
    }


    composable(ProfileDestination.route) {
        ProfileScreen(onNavigateToLogin = onNavigateToLogin)
    }
}