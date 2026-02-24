package me.marthia.app.boomgrad.presentation.attraction.detail

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.marthia.app.boomgrad.domain.usecase.attraction.GetAttractionDetailUseCase
import me.marthia.app.boomgrad.presentation.attraction.model.AttractionUi
import me.marthia.app.boomgrad.presentation.attraction.model.toUi
import me.marthia.app.boomgrad.presentation.navigation.AttractionDetailDestination
import me.marthia.app.boomgrad.presentation.util.ViewState

class AttractionDetailViewModel(
    private val getAttractionDetailUseCase: GetAttractionDetailUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val attractionId: Long =
        savedStateHandle.get<Long>(AttractionDetailDestination.ARG_ATTRACTION_ID) ?: -1

    private val _uiState = MutableStateFlow<ViewState<AttractionUi>>(ViewState.Idle)
    val uiState: StateFlow<ViewState<AttractionUi>> = _uiState.asStateFlow()

    fun loadAttractionDetail(context: Context) {
        viewModelScope.launch {
            _uiState.value = ViewState.Loading
            getAttractionDetailUseCase(attractionId)
                .onSuccess { attraction ->
                    _uiState.value = ViewState.Success(attraction.toUi(context = context))
                }
                .onFailure { error ->
                    _uiState.value = ViewState.Error(error)
                }
        }
    }
}