package me.marthia.app.boomgrad.presentation.tour.detail

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.marthia.app.boomgrad.domain.usecase.tour.GetTourDetailUseCase
import me.marthia.app.boomgrad.presentation.navigation.TourDetailDestination
import me.marthia.app.boomgrad.presentation.tour.model.TourDetailUi
import me.marthia.app.boomgrad.presentation.tour.model.toUi
import me.marthia.app.boomgrad.presentation.util.ViewState

class TourDetailViewModel(
    private val getTourDetail: GetTourDetailUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val tourId: Long = savedStateHandle.get<Long>(TourDetailDestination.ARG_TOUR_ID) ?: -1

    private val _uiState = MutableStateFlow<ViewState<TourDetailUi>>(ViewState.Idle)
    val uiState: StateFlow<ViewState<TourDetailUi>> = _uiState.asStateFlow()


    fun fetchDetail(context: Context) {
        viewModelScope.launch {
            _uiState.value = ViewState.Loading
            getTourDetail.invoke(tourId = tourId)
                .onSuccess { tour ->
                    _uiState.value = ViewState.Success(tour.toUi(context = context))
                }
                .onFailure { _uiState.value = ViewState.Error(it) }
        }
    }

}