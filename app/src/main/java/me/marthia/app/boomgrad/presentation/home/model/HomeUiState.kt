package me.marthia.app.boomgrad.presentation.home.model

import me.marthia.app.boomgrad.domain.model.Attraction
import me.marthia.app.boomgrad.domain.model.AttractionCategory
import me.marthia.app.boomgrad.domain.model.Tour

data class HomeUiState(
    val forYouTours: List<Tour>,
    val topAttractions: List<Attraction>,
    val weekRecommended: List<Tour>,
    val categories: List<AttractionCategory>,
)