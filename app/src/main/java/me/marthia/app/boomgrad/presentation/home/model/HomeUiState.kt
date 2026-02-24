package me.marthia.app.boomgrad.presentation.home.model

import me.marthia.app.boomgrad.domain.model.Attraction
import me.marthia.app.boomgrad.domain.model.AttractionCategory
import me.marthia.app.boomgrad.domain.model.TourList
import me.marthia.app.boomgrad.presentation.category.model.CategoryUi

data class HomeUiState(
    val forYouTours: List<TourList>,
    val topAttractions: List<Attraction>,
    val weekRecommended: List<TourList>,
    val categories: List<CategoryUi>,
)