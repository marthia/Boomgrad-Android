package me.marthia.app.boomgrad.presentation.login

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.marthia.app.boomgrad.domain.usecase.login.LoginUseCase
import me.marthia.app.boomgrad.presentation.util.MviViewModel
import me.marthia.app.boomgrad.presentation.util.ViewState
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class LoginViewModel(
    private val login: LoginUseCase,
) : MviViewModel<ViewState<LoginState>, LoginEvent>() {
    private val _token = MutableStateFlow("")
    val token = _token.asStateFlow()

//    fun isLoggedIn() = repository.getToken().isNullOrBlank().not()

    override fun onTriggerEvent(eventType: LoginEvent) {
        when (eventType) {
            is LoginEvent.Login -> handleLogin(eventType.username, eventType.password)
            is LoginEvent.ClearError -> clearError()
            LoginEvent.Logout -> handleLogout()
        }
    }

    private fun handleLogin(username: String, password: String) {
        safeLaunch {
            val params = LoginUseCase.LoginParams(username = username, password = password)

            execute(login.invoke(params = params)) {
                setState(ViewState.Data(LoginState.LoginSuccess(token = "")))
            }
        }
    }

    private fun handleLogout() {
        viewModelScope.launch {
//            repository.clearToken()
            setState(ViewState.Data(LoginState.LoggedOut))
        }
    }

    fun clearError() {
        setState(ViewState.Empty)
    }
}