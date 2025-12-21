package me.marthia.app.boomgrad.presentation.login.otp

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import me.marthia.app.boomgrad.presentation.util.AndroidVersionUtils
import me.marthia.app.boomgrad.presentation.util.SDK
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import timber.log.Timber

/**
 * BroadcastReceiver to wait for SMS messages. This can be registered either
 * in the AndroidManifest or at runtime.  Should filter Intents on
 * SmsRetriever.SMS_RETRIEVED_ACTION.
 */
class SmsRetrieverOTP : BroadcastReceiver() {
    private var otpReceiveListener: OTPReceiveListener? = null

    fun init(otpReceiveListener: OTPReceiveListener?) {
        this.otpReceiveListener = otpReceiveListener
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (SmsRetriever.SMS_RETRIEVED_ACTION == intent?.action) {
            Timber.d("SMS_RETRIEVED_ACTION Received")

            val extras: Bundle? = intent.extras
            val status: Status? = if (AndroidVersionUtils.atLeast(SDK.Android13)) {
                @SuppressLint("NewApi")
                extras?.getParcelable(SmsRetriever.EXTRA_STATUS, Status::class.java)
            } else {
                @Suppress("DEPRECATION")
                extras?.getParcelable(SmsRetriever.EXTRA_STATUS)
            }

            when (status?.statusCode) {
                CommonStatusCodes.SUCCESS -> {
                    Timber.d("gms: CommonStatusCodes.SUCCESS")

                    // Get SMS message contents
                    val msg: String = extras?.getString(SmsRetriever.EXTRA_SMS_MESSAGE, "") ?: ""

                    // extract the 4-digit code from the SMS
                    val smsCode = msg.let { "[0-9]{4}".toRegex().find(it) }
                    smsCode?.value?.let { otpReceiveListener?.onOTPReceived(it) }
                }

                CommonStatusCodes.TIMEOUT -> {
                    otpReceiveListener?.onOTPTimeOut()
                }
            }
        }
    }

    interface OTPReceiveListener {
        fun onOTPReceived(otp: String?)
        fun onOTPTimeOut()
    }
}