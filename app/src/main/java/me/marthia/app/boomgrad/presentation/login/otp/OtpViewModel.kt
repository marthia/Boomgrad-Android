package me.marthia.app.boomgrad.presentation.login.otp

import android.content.Context
import android.content.IntentFilter
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.auth.api.phone.SmsRetrieverClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.marthia.app.boomgrad.domain.repository.LoginRepository
import me.marthia.app.boomgrad.presentation.util.ViewState
import timber.log.Timber

class OtpViewModel(
    private val repository: LoginRepository,
    private val context: Context
) : ViewModel() {

    private val _otpCode = MutableStateFlow("")
    val otpCode = _otpCode.asStateFlow()

    private val _otpState = MutableStateFlow<ViewState<OTPState>>(ViewState.Idle)
    val otpState = _otpState.asStateFlow()

    private val smsBroadcastReceiver: SmsRetrieverOTP = SmsRetrieverOTP()

    init {
        startSMSRetrieverClient(context)

        smsBroadcastReceiver.init(object : SmsRetrieverOTP.OTPReceiveListener {
            override fun onOTPReceived(otp: String?) {
                Timber.d("OTP Received $otp")

                otp?.let {
                    _otpCode.value = it
                }

                context.unregisterReceiver(smsBroadcastReceiver)
            }

            override fun onOTPTimeOut() {
                Timber.d("OTP Timeout")
            }
        })

        viewModelScope.launch {
            delay(100)
            val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)

            ContextCompat.registerReceiver(
                context, smsBroadcastReceiver, intentFilter, ContextCompat.RECEIVER_EXPORTED
            )
        }
    }


    private fun startSMSRetrieverClient(context: Context) {
        val client: SmsRetrieverClient = SmsRetriever.getClient(context)
        val task = client.startSmsRetriever()
        task.addOnSuccessListener {
            Timber.d("startSMSRetrieverClient addOnSuccessListener")
        }
        task.addOnFailureListener { e ->
            Timber.d("startSMSRetrieverClient addOnFailureListener" + e.stackTrace)
        }
    }

    fun verifyOtp(otp: String) {
        viewModelScope.launch {
            _otpState.value = ViewState.Loading

            val code = otp.trim()

            if (code.isEmpty()) {
                _otpState.value = ViewState.Error(Throwable("کد تایید نمی‌تواند خالی باشد"))
                return@launch
            }

            val response = repository.checkSmsCode(code)
            if (response.status == true) {
                // Get token from token manager after successful OTP verification
                _otpState.value =
                    ViewState.Success(
                        OTPState(isCodeCorrect = true)
                    )

            } else {
                _otpState.value =
                    ViewState.Error(
                        Throwable(response.message)/* ?: Throwable("کد تایید نامعتبر است")*/
                    )

            }
        }
    }
}