package me.marthia.app.boomgrad.presentation.profile.mytrips

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Phone
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.marthia.app.boomgrad.domain.model.Profile
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

