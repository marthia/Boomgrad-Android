package me.marthia.app.boomgrad.presentation.navigation

sealed interface AppDestination {
    val route: String
}

/* ---------- Static Destinations ---------- */

data object LoginDestination : AppDestination {
    override val route = "login"
}

data object HomeDestination : AppDestination {
    override val route = "home"
}

data object FeedDestination : AppDestination {
    override val route = "home/feed"
}

data object MyTours : AppDestination {
    override val route: String
        get() = "home/mytours"
}

data object BookingsDestination : AppDestination {
    override val route = "home/bookings"
}

data object ProfileDestination : AppDestination {
    override val route = "home/profile"
}

data object ToursDestination : AppDestination {
    override val route = "tours"
}

data object EditProfileDestination : AppDestination {
    override val route: String
        get() = "profile/edit"
}

data object MyTripsDestination : AppDestination {
    override val route: String
        get() = "profile/mytrips"
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

