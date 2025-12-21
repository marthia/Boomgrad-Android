package me.marthia.app.boomgrad.presentation.login

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.marthia.app.boomgrad.domain.repository.LoginRepository
import me.marthia.app.boomgrad.presentation.util.BaseViewState
import me.marthia.app.boomgrad.presentation.util.MviViewModel
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class LoginViewModel(
    private val repository: LoginRepository,
) : MviViewModel<BaseViewState<LoginState>, LoginEvent>() {
    private val _token = MutableStateFlow("")
    val token = _token.asStateFlow()

    fun isLoggedIn() = repository.getToken().isNullOrBlank().not()

    override fun onTriggerEvent(eventType: LoginEvent) {
        when (eventType) {
            is LoginEvent.Login -> handleLogin(eventType.phone)
            is LoginEvent.ClearError -> clearError()
            LoginEvent.Logout -> handleLogout()
        }
    }

    private fun handleLogin(phone: String) {
        safeLaunch {
            setState(BaseViewState.Loading)

            // Validate input
            if (phone.isEmpty()) {
                setState(BaseViewState.Error(Throwable("نام کاربری نمی‌تواند خالی باشد")))
                return@safeLaunch
            }

            // Call API
            val response = repository.login(phone)
            if (response.success == true) {
                setState(
                    BaseViewState.Data(
                        LoginState.OtpRequired(
                            message = response.message
                                ?: "کد تایید به شماره شما ارسال شد"
                        )
                    )
                )
            } else {
                setState(
                    BaseViewState.Error(
                        Throwable(response.message ?: "ورود ناموفق بود")
                    )
                )
            }

        }
    }

    private fun handleLogout() {
        viewModelScope.launch {
            repository.clearToken()
            setState(BaseViewState.Data(LoginState.LoggedOut))
        }
    }

    fun clearError() {
        setState(BaseViewState.Empty)
    }
}