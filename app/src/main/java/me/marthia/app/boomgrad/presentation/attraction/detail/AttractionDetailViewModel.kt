package me.marthia.app.boomgrad.presentation.attraction.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.marthia.app.boomgrad.domain.model.Attraction
import me.marthia.app.boomgrad.domain.usecase.attraction.GetAttractionDetailUseCase
import me.marthia.app.boomgrad.presentation.navigation.AttractionDetailDestination
import me.marthia.app.boomgrad.presentation.util.ViewState

class AttractionDetailViewModel(
    private val getAttractionDetailUseCase: GetAttractionDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val attractionId: Long =
        savedStateHandle.get<Long>(AttractionDetailDestination.ARG_ATTRACTION_ID) ?: -1

    private val _uiState = MutableStateFlow<ViewState<Attraction>>(ViewState.Idle)
    val uiState: StateFlow<ViewState<Attraction>> = _uiState.asStateFlow()

    init {
        loadAttractionDetail()
    }

    private fun loadAttractionDetail() {
        viewModelScope.launch {
            _uiState.value = ViewState.Loading
            getAttractionDetailUseCase(attractionId)
                .onSuccess { attraction ->
                    _uiState.value = ViewState.Success(attraction)
                }
                .onFailure { error ->
                    _uiState.value = ViewState.Error(error)
                }
        }
    }

    fun retry() {
        loadAttractionDetail()
    }
}