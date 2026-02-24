package me.marthia.app.boomgrad.presentation.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.marthia.app.boomgrad.domain.model.City
import me.marthia.app.boomgrad.domain.usecase.attraction.GetTopAttractionsUseCase
import me.marthia.app.boomgrad.domain.usecase.category.GetAttractionCategoryUseCase
import me.marthia.app.boomgrad.domain.usecase.common.GetCityUseCase
import me.marthia.app.boomgrad.domain.usecase.tour.GetForYouToursUseCase
import me.marthia.app.boomgrad.domain.usecase.tour.GetWeekRecommendedUseCase
import me.marthia.app.boomgrad.presentation.category.model.toUi
import me.marthia.app.boomgrad.presentation.home.model.HomeUiState
import me.marthia.app.boomgrad.presentation.util.ViewState

class HomeViewModel(
    private val getCity: GetCityUseCase,
    private val getCategories: GetAttractionCategoryUseCase,
    private val getForYouTours: GetForYouToursUseCase,
    private val getTopAttractions: GetTopAttractionsUseCase,
    private val getWeekRecommended: GetWeekRecommendedUseCase,
) : ViewModel() {


    private val _cities = MutableStateFlow<List<City>>(emptyList())
    val cities: StateFlow<List<City>> = _cities.asStateFlow()

    // Optional: error state for showing user-friendly messages
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _uiState = MutableStateFlow<ViewState<HomeUiState>>(ViewState.Idle)
    val uiState: StateFlow<ViewState<HomeUiState>> = _uiState.asStateFlow()

    fun getCity(provinceId: Long, countyId: Long) {
        viewModelScope.launch {
            val result = getCity.invoke(
                GetCityUseCase.Params(provinceId = provinceId, countyId = countyId)
            )
            result.onSuccess { cities ->
                _cities.value = cities
            }.onFailure { error ->
                _error.value = "Failed to load cities"
            }
        }
    }

    fun getAll(context: Context) {
        viewModelScope.launch {
            val forYouToursDeferred = async { getForYouTours.invoke() }
            val topAttractionsDeferred = async { getTopAttractions.invoke() }
            val weekRecommendedDeferred = async { getWeekRecommended.invoke() }
            val categoriesDeferred = async { getCategories.invoke() }

            val forYouTours = forYouToursDeferred.await().getOrNull() ?: return@launch
            val topAttractions = topAttractionsDeferred.await().getOrNull() ?: return@launch
            val weekRecommended = weekRecommendedDeferred.await().getOrNull() ?: return@launch
            val categories = categoriesDeferred.await().getOrNull() ?: return@launch

            _uiState.value = ViewState.Success(
                HomeUiState(
                    categories = categories.map { it.toUi(context = context) },
                    forYouTours = forYouTours,
                    topAttractions = topAttractions,
                    weekRecommended = weekRecommended
                )
            )
        }
    }

    fun clearError() {
        _error.value = null
    }
}