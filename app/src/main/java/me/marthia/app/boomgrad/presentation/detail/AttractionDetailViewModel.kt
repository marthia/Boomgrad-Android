package me.marthia.app.boomgrad.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import me.marthia.app.boomgrad.domain.model.Attraction
import me.marthia.app.boomgrad.domain.usecase.attraction.GetAttractionDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AttractionDetailViewModel(
    private val getAttractionDetailUseCase: GetAttractionDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val attractionId: String = savedStateHandle.get<String>("attractionId") ?: ""

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    init {
        loadAttractionDetail()
    }

    private fun loadAttractionDetail() {
        viewModelScope.launch {
            _uiState.value = DetailUiState.Loading
            getAttractionDetailUseCase(attractionId)
                .onSuccess { attraction ->
                    _uiState.value = DetailUiState.Success(attraction)
                }
                .onFailure { error ->
                    _uiState.value = DetailUiState.Error(
                        error.message ?: "Unknown error occurred"
                    )
                }
        }
    }

    fun retry() {
        loadAttractionDetail()
    }
}

sealed interface DetailUiState {
    object Loading : DetailUiState
    data class Success(val attraction: Attraction) : DetailUiState
    data class Error(val message: String) : DetailUiState
}