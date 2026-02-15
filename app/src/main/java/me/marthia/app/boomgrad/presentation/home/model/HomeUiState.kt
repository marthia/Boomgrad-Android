package me.marthia.app.boomgrad.presentation.home.model

import me.marthia.app.boomgrad.domain.model.Attraction
import me.marthia.app.boomgrad.domain.model.AttractionCategory
import me.marthia.app.boomgrad.domain.model.TourList

data class HomeUiState(
    val forYouTours: List<TourList>,
    val topAttractions: List<Attraction>,
    val weekRecommended: List<TourList>,
    val categories: List<AttractionCategory>,
)