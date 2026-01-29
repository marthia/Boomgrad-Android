package me.marthia.app.boomgrad.presentation.profile

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

class ProfileViewModel(
    private val getProfile: GetProfileUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<ViewState<ProfileUiState>>(ViewState.Idle)
    val uiState: StateFlow<ViewState<ProfileUiState>> = _uiState.asStateFlow()


    init {
        loadAttractions()
    }

    // mock version
    fun loadAttractions() {
        viewModelScope.launch {
            _uiState.value = ViewState.Loading
            getProfile.invoke()
                .onSuccess { profile ->
                    _uiState.value = ViewState.Success(ProfileUiState(item = profile))
                }
                .onFailure { _uiState.value = ViewState.Error(it) }
        }
    }

    fun retry() {
        loadAttractions()
    }
}

data class ProfileUiState(
    val item: Profile,
)

