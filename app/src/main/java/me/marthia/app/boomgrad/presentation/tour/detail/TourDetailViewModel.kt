package me.marthia.app.boomgrad.presentation.tour.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.marthia.app.boomgrad.domain.model.TourDetail
import me.marthia.app.boomgrad.domain.usecase.tour.GetTourDetailUseCase
import me.marthia.app.boomgrad.presentation.util.ViewState

class TourDetailViewModel(val tourId: Long, val get: GetTourDetailUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow<ViewState<TourDetail>>(ViewState.Idle)
    val uiState: StateFlow<ViewState<TourDetail>> = _uiState.asStateFlow()

    init {
        fetchDetail()
    }


    fun fetchDetail() {
        viewModelScope.launch {
            _uiState.value = ViewState.Loading
            get.invoke(tourId = tourId)
                .onSuccess { tour ->
                    _uiState.value = ViewState.Success(tour)
                }
                .onFailure { _uiState.value = ViewState.Error(it) }
        }
    }

}