package me.marthia.app.boomgrad.presentation.navigation

sealed class Screen(val route: String) {
    object Attractions : Screen("attractions")
    object Search : Screen("search")
    object Favorites : Screen("favorites")
    object AttractionDetail : Screen("attraction/{attractionId}") {
        fun createRoute(attractionId: String) = "attraction/$attractionId"
    }
}