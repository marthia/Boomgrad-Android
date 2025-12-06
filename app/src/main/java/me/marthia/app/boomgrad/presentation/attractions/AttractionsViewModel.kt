package me.marthia.app.boomgrad.presentation.attractions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.marthia.app.boomgrad.domain.model.Attraction
import me.marthia.app.boomgrad.domain.usecase.GetAttractionsUseCase


class AttractionsViewModel(
    private val getAttractionsUseCase: GetAttractionsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<AttractionsUiState>(AttractionsUiState.Loading)
    val uiState: StateFlow<AttractionsUiState> = _uiState.asStateFlow()

    init {
        loadAttractions()
    }

    fun loadAttractions() {
        viewModelScope.launch {
            _uiState.value = AttractionsUiState.Loading
            getAttractionsUseCase()
                .onSuccess { attractions ->
                    _uiState.value = AttractionsUiState.Success(attractions)
                }
                .onFailure { error ->
                    _uiState.value = AttractionsUiState.Error(
                        error.message ?: "Unknown error occurred"
                    )
                }
        }
    }

    fun retry() {
        loadAttractions()
    }
}

sealed interface AttractionsUiState {
    object Loading : AttractionsUiState
    data class Success(val attractions: List<Attraction>) : AttractionsUiState
    data class Error(val message: String) : AttractionsUiState
}