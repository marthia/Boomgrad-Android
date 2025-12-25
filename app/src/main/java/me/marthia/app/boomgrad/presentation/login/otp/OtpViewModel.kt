package me.marthia.app.boomgrad.presentation.login.otp

import android.content.Context
import android.content.IntentFilter
import androidx.core.content.ContextCompat
import me.marthia.app.boomgrad.domain.repository.LoginRepository
import me.marthia.app.boomgrad.presentation.util.ViewState
import me.marthia.app.boomgrad.presentation.util.MviViewModel
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.auth.api.phone.SmsRetrieverClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

class OtpViewModel(
    private val repository: LoginRepository,
    private val context: Context
) : MviViewModel<ViewState<OTPState>, OTPEvent>() {

    private val _otpCode = MutableStateFlow("")
    val otpCode = _otpCode.asStateFlow()


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

        safeLaunch {
            delay(100)
            val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)

            ContextCompat.registerReceiver(
                context, smsBroadcastReceiver, intentFilter, ContextCompat.RECEIVER_EXPORTED
            )
        }
    }

    override fun onTriggerEvent(eventType: OTPEvent) {
        when (eventType) {
            is OTPEvent.VerifyOtp -> handleVerifyOtp(eventType.otp)
            is OTPEvent.RequestForOtp -> {}
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

    private fun handleVerifyOtp(otp: String) {
        safeLaunch {
            setState(ViewState.Loading)

            val code = otp.trim()

            if (code.isEmpty()) {
                setState(ViewState.Error(Throwable("کد تایید نمی‌تواند خالی باشد")))
                return@safeLaunch
            }

            val response = repository.checkSmsCode(code)
            if (response.status == true) {
                // Get token from token manager after successful OTP verification
                setState(
                    ViewState.Data(
                        OTPState(isCodeCorrect = true)
                    )
                )
            } else {
                setState(
                    ViewState.Error(
                        Throwable(response.message)/* ?: Throwable("کد تایید نامعتبر است")*/
                    )
                )
            }
        }
    }
}