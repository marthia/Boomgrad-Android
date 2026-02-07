package me.marthia.app.boomgrad.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import me.marthia.app.boomgrad.domain.model.Profile
import me.marthia.app.boomgrad.domain.usecase.login.CheckAuthorizationUseCase
import me.marthia.app.boomgrad.domain.usecase.profile.GetProfileUseCase
import me.marthia.app.boomgrad.presentation.util.ViewState

class ProfileViewModel(
    private val getProfile: GetProfileUseCase,
    private val isUserLoggedIn: CheckAuthorizationUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ViewState<ProfileUiState>>(ViewState.Idle)
    val uiState: StateFlow<ViewState<ProfileUiState>> = _uiState.asStateFlow()


    fun loadProfileDetails(userId: Long) {
        viewModelScope.launch {
            _uiState.value = ViewState.Loading
            getProfile.invoke(userId = userId)
                .onSuccess { profile ->
                    _uiState.value = ViewState.Success(ProfileUiState(item = profile))
                }
                .onFailure { _uiState.value = ViewState.Error(it) }
        }
    }

    fun retry() {
        loadProfileDetails(14) // fixme
    }

    fun isLogin(): Boolean {
        return runBlocking { isUserLoggedIn.invoke() }
    }
}

data class ProfileUiState(
    val item: Profile,
)

