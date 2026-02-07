package me.marthia.app.boomgrad.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import me.marthia.app.boomgrad.R
import me.marthia.app.boomgrad.presentation.LocalNavAnimatedVisibilityScope
import me.marthia.app.boomgrad.presentation.attraction.list.AttractionList
import me.marthia.app.boomgrad.presentation.cart.CartScreen
import me.marthia.app.boomgrad.presentation.home.HomeScreen
import me.marthia.app.boomgrad.presentation.nonSpatialExpressiveSpring
import me.marthia.app.boomgrad.presentation.profile.ProfileScreen

fun NavGraphBuilder.composableWithCompositionLocal(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    enterTransition: (@JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = {
        fadeIn(nonSpatialExpressiveSpring())
    },
    exitTransition: (@JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = {
        fadeOut(nonSpatialExpressiveSpring())
    },
    popEnterTransition: (@JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = enterTransition,
    popExitTransition: (@JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = exitTransition,
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) {
    composable(
        route,
        arguments,
        deepLinks,
        enterTransition,
        exitTransition,
        popEnterTransition,
        popExitTransition,
    ) {
        CompositionLocalProvider(
            LocalNavAnimatedVisibilityScope provides this@composable,
        ) {
            content(it)
        }
    }
}

fun NavGraphBuilder.addHomeGraph(
    onSnackSelected: (Long, String, NavBackStackEntry) -> Unit,
    onTourSelected: (Long, NavBackStackEntry) -> Unit,
    onNavigateToLogin: () -> Unit,
    onAttractionSelected: (Long, NavBackStackEntry) -> Unit,
    modifier: Modifier = Modifier,
) {
    composable(HomeSections.FEED.route) { from ->
        HomeScreen(onTourSelected = { id -> onTourSelected(id, from) })
    }
    composable(HomeSections.ATTRACTIONS.route) { from ->
        AttractionList(onAttractionSelected = { id -> onAttractionSelected(id, from) })
    }

    composable(
        HomeSections.CART.route,
        deepLinks = listOf(
            navDeepLink { uriPattern = "https://jetsnack.example.com/home/cart" },
        ),
    ) { from ->
        CartScreen(
//            onSnackClick = { id, origin -> onSnackSelected(id, origin, from) },
//            modifier,
        )
    }


    composable(HomeSections.PROFILE.route) {
        ProfileScreen(onNavigateToLogin = onNavigateToLogin)
    }
}


enum class HomeSections(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    @DrawableRes val selectedIcon: Int,
    val route: String
) {
    FEED(
        title = R.string.home_feed,
        icon = R.drawable.ic_home_24,
        selectedIcon = R.drawable.ic_home_filled_24,
        route = "home/feed",
    ),
    ATTRACTIONS(
        title = R.string.home_attraction,
        icon = R.drawable.ic_location_tick_24,
        selectedIcon = R.drawable.ic_location_tick_filled_24,
        route = "home/attractionList",
    ),

    CART(
        title = R.string.home_cart,
        icon = R.drawable.ic_cart_24,
        selectedIcon = R.drawable.ic_cart_filled_24,
        route = "home/cart",
    ),

    PROFILE(
        title = R.string.home_profile,
        icon = R.drawable.ic_profile_24,
        selectedIcon = R.drawable.ic_profile_filled_24,
        route = "home/profile",
    ),
}