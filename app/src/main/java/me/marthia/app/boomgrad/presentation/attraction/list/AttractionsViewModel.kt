package me.marthia.app.boomgrad.presentation.attraction.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.marthia.app.boomgrad.domain.model.Attraction
import me.marthia.app.boomgrad.domain.usecase.attraction.GetAttractionsUseCase
import me.marthia.app.boomgrad.presentation.util.ViewState


class AttractionsViewModel(
    private val getAttractionsUseCase: GetAttractionsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<ViewState<AttractionsUiState>>(ViewState.Idle)
    val uiState: StateFlow<ViewState<AttractionsUiState>> = _uiState.asStateFlow()


    init {
        loadAttractions()
    }

    // mock version
    fun loadAttractions() {
        viewModelScope.launch {
            _uiState.value = ViewState.Loading
            getAttractionsUseCase()
                .onSuccess { attractions ->
                    _uiState.value = ViewState.Success(AttractionsUiState(mockList = attractions))
                }
                .onFailure { _uiState.value = ViewState.Error(it) }
        }
    }

    fun retry() {
        loadAttractions()
    }
}

data class AttractionsUiState(
    val mockList: List<Attraction> = listOf(),
    val list: List<Attraction> = listOf()
)