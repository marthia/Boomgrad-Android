package me.marthia.app.boomgrad.presentation.login

sealed class LoginState {
    data class OtpRequired(val message: String) : LoginState()
    data class LoginSuccess(val token: String) : LoginState()
    data object LoggedOut : LoginState()
}


sealed class LoginEvent {
    data class Login(val username: String, val password: String) : LoginEvent()
    data object ClearError : LoginEvent()
    data object Logout : LoginEvent()

}