package me.marthia.app.boomgrad.presentation.login.otp

data class OTPState(
        val isCodeCorrect: Boolean? = null,
)


sealed class OTPEvent {
    data class RequestForOtp(val phoneNumber: String) : OTPEvent()

    data class VerifyOtp(val otp: String) : OTPEvent()
}