package me.marthia.app.boomgrad.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import me.marthia.app.boomgrad.R

data class BottomBarDestination(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    @DrawableRes val selectedIcon: Int,
    val route: String
)

val bottomBarDestinations = listOf(
    BottomBarDestination(
        title = R.string.home_feed,
        icon = R.drawable.ic_home_24,
        selectedIcon = R.drawable.ic_home_filled_24,
        route = FeedDestination.route
    ),
    BottomBarDestination(
        title = R.string.home_attraction,
        icon = R.drawable.ic_location_tick_24,
        selectedIcon = R.drawable.ic_location_tick_filled_24,
        route = AttractionsDestination.route
    ),
    BottomBarDestination(
        title = R.string.home_cart,
        icon = R.drawable.ic_cart_24,
        selectedIcon = R.drawable.ic_cart_filled_24,
        route = CartDestination.route
    ),
    BottomBarDestination(
        title = R.string.home_profile,
        icon = R.drawable.ic_profile_24,
        selectedIcon = R.drawable.ic_profile_filled_24,
        route = ProfileDestination.route
    ),
)