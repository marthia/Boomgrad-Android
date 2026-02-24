package me.marthia.app.boomgrad.presentation.tour.model

import android.content.Context
import me.marthia.app.boomgrad.R
import me.marthia.app.boomgrad.domain.model.Demographic
import me.marthia.app.boomgrad.domain.model.TourDetail
import me.marthia.app.boomgrad.domain.model.TourLevel
import me.marthia.app.boomgrad.presentation.category.model.toUi

fun TourDetail.toUi(context: Context) = TourDetailUi(
    id = id,
    title = title,
    description = description,
    guide = guide,
    highlights = highlights,
    duration = duration,
    price = price,
    category = category.toUi(context),
    maxPeople = maxPeople,
    status = status,
    rate = rate,
    reviews = reviews,
    images = images,
    requiredItems = requiredItems,
    level = level.toUiLabel(context),
    dueDate = dueDate,
    startTime = startTime,
    demographic = demographic.toUiDemographicLabel(context = context),
    itinerary = itinerary,
    city = city,
)

fun TourLevel.toUiLabel(context: Context): String {
    return when (this) {
        TourLevel.BEGINNER -> context.getString(R.string.label_level_beginner)
        TourLevel.INTERMEDIATE -> context.getString(R.string.label_level_intermediate)
        TourLevel.ADVANCED -> context.getString(R.string.label_level_advanced)
        TourLevel.EXPERT -> context.getString(R.string.label_level_expert)
    }
}

fun Demographic.toUiDemographicLabel(context: Context): String {
    return when (this) {
        Demographic.ALL_AGES -> context.getString(R.string.label_demographic_all_ages)
        Demographic.FAMILIES -> context.getString(R.string.label_demographic_families)
        Demographic.ADULTS_ONLY -> context.getString(R.string.label_demographic_adults_only)
        Demographic.SENIORS -> context.getString(R.string.label_demographic_seniors)
        Demographic.YOUTH -> context.getString(R.string.label_demographic_youth)
        Demographic.COUPLES -> context.getString(R.string.label_demographic_couples)
    }
}