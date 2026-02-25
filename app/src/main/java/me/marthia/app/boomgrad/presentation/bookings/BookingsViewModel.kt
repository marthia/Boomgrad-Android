package me.marthia.app.boomgrad.presentation.bookings

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import me.marthia.app.boomgrad.domain.usecase.profile.GetProfileUseCase
import me.marthia.app.boomgrad.presentation.util.ViewState

class MyTripsViewModel(
    private val getProfile: GetProfileUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<ViewState<MyTripsUiState>>(ViewState.Idle)
    val uiState: StateFlow<ViewState<MyTripsUiState>> = _uiState.asStateFlow()



}

data class MyTripsUiState(
    val item: String,
)

