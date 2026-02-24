package me.marthia.app.boomgrad.presentation.attraction.model

import android.content.Context
import me.marthia.app.boomgrad.domain.model.Attraction
import me.marthia.app.boomgrad.presentation.category.model.toUi

fun Attraction.toUi(context: Context) = AttractionUi(
    id = id,
    category = category.toUi(context = context),
    images = images,
    rating = rating,
    reviewCount = reviewCount,
    contactInfo = contactInfo,
    openingHours = openingHours,
    location = location,
    isFavorite = isFavorite,
    reviews = reviews,
    relatedTours = relatedTours,
)