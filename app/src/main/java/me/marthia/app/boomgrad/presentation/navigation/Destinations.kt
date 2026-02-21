package me.marthia.app.boomgrad.presentation.navigation

sealed interface AppDestination {
    val route: String
}

/* ---------- Static Destinations ---------- */

data object LoginDestination : AppDestination {
    override val route = "login"
}

data object OtpDestination : AppDestination {
    override val route = "otp"
}

data object HomeDestination : AppDestination {
    override val route = "home"
}

data object FeedDestination : AppDestination {
    override val route = "home/feed"
}

data object AttractionsDestination : AppDestination {
    override val route = "home/attractions"
}

data object CartDestination : AppDestination {
    override val route = "home/cart"
}

data object ProfileDestination : AppDestination {
    override val route = "home/profile"
}

data object ToursDestination : AppDestination {
    override val route = "tours"
}

/* ---------- Dynamic Destinations ---------- */

data class TourDetailDestination(
    val tourId: Long
) : AppDestination {

    override val route: String
        get() = "$BASE_ROUTE/$tourId"

    companion object {
        const val BASE_ROUTE = "tour"
        const val ARG_TOUR_ID = "tourId"
        const val ROUTE_PATTERN = "$BASE_ROUTE/{$ARG_TOUR_ID}"
    }
}

data class AttractionDetailDestination(
    val attractionId: Long
) : AppDestination {

    override val route: String
        get() = "$BASE_ROUTE/$attractionId"

    companion object {
        const val BASE_ROUTE = "attraction"
        const val ARG_ATTRACTION_ID = "attractionId"
        const val ROUTE_PATTERN = "$BASE_ROUTE/{$ARG_ATTRACTION_ID}"
    }
}

data class GuideInfoDestination(
    val guideId: Long
) : AppDestination {

    override val route: String
        get() = "$BASE_ROUTE/$guideId"

    companion object {
        const val BASE_ROUTE = "guide"
        const val ARG_GUIDE_ID = "guideId"
        const val ROUTE_PATTERN = "$BASE_ROUTE/{$ARG_GUIDE_ID}"
    }
}

