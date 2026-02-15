package me.marthia.app.boomgrad.presentation.tour.detail.guide

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import me.marthia.app.boomgrad.domain.model.Guide
import me.marthia.app.boomgrad.domain.model.TourDetail
import me.marthia.app.boomgrad.presentation.util.ViewState

class GuideInfoViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<ViewState<GuideUiState>>(ViewState.Idle)
    val uiState: StateFlow<ViewState<GuideUiState>> = _uiState.asStateFlow()

}

data class GuideUiState(
    val guideInfo: Guide,
    val tours: List<TourDetail>
)