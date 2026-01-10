package me.marthia.app.boomgrad.domain.model

import kotlinx.serialization.Serializable

@Serializable
enum class LocationType {
    ATTRACTION,
    VIEWPOINT,
    MEETING_POINT,
    REST_STOP,
    PHOTO_SPOT,
    RESTAURANT,
    HOTEL,
    OTHER;
}

