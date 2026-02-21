package me.marthia.app.boomgrad.presentation.guide

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.marthia.app.boomgrad.domain.usecase.tour.GetGuideInfoUseCase
import me.marthia.app.boomgrad.presentation.guide.model.GuideUi
import me.marthia.app.boomgrad.presentation.guide.model.toUi
import me.marthia.app.boomgrad.presentation.navigation.GuideInfoDestination
import me.marthia.app.boomgrad.presentation.util.ViewState

class GuideInfoViewModel(
    private val getGuideInfoUseCase: GetGuideInfoUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val guideId: Long =
        savedStateHandle.get<Long>(GuideInfoDestination.ARG_GUIDE_ID) ?: -1

    private val _uiState = MutableStateFlow<ViewState<GuideUi>>(ViewState.Idle)
    val uiState: StateFlow<ViewState<GuideUi>> = _uiState.asStateFlow()

    init {
        fetchInfo()
    }

    private fun fetchInfo() {
        viewModelScope.launch {
            _uiState.value = ViewState.Loading
            getGuideInfoUseCase(guideId)
                .onSuccess { guideInfo ->
                    _uiState.value = ViewState.Success(guideInfo.toUi())
                }
                .onFailure { error ->
                    _uiState.value = ViewState.Error(error)
                }
        }
    }
}