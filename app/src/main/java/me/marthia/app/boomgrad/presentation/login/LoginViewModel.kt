package me.marthia.app.boomgrad.presentation.login

import android.net.http.HttpException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.io.IOException
import me.marthia.app.boomgrad.R
import me.marthia.app.boomgrad.domain.usecase.login.ClearTokenUseCase
import me.marthia.app.boomgrad.domain.usecase.login.LoginUseCase
import me.marthia.app.boomgrad.presentation.components.SnackbarManager
import me.marthia.app.boomgrad.presentation.util.ViewState
import org.koin.android.annotation.KoinViewModel


@KoinViewModel
class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val clearToken: ClearTokenUseCase,
    private val snackbarManager: SnackbarManager,
) : ViewModel() {

    private val _loginState = MutableStateFlow<ViewState<LoginState>>(ViewState.Idle)
    val loginState = _loginState.asStateFlow()


    fun login(username: String, password: String) {
        viewModelScope.launch {
            _loginState.value = ViewState.Loading

            loginUseCase(LoginUseCase.LoginParams(username, password))
                .onSuccess { state ->
                    _loginState.value = ViewState.Success(LoginState(state.token))
                    snackbarManager.showMessage(R.string.message_login_success)
                }
                .onFailure { error ->
//                    _loginState.value = ViewState.Error(
//                        error.toUiMessage()
//                    )
                    snackbarManager.showMessage(R.string.message_login_failure)
                }
        }
    }

    fun clearError() {
        clearToken.invoke()
        _loginState.value = ViewState.Idle
    }

}

// If you need common error mapping
fun Throwable.toUiMessage(): String = when (this) {
    is IOException -> "Network error. Please check your connection."
    is HttpException -> "Server error. Please try again."
    else -> message ?: "An unexpected error occurred"
}