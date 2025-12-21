package me.marthia.app.boomgrad.presentation.login.otp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.core.content.ContextCompat
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import timber.log.Timber

/**
 * A helper class to simplify the implementation of the Google SMS User Consent API.
 *
 * This class encapsulates the logic for starting the SMS listener, registering a `BroadcastReceiver`
 * to capture the SMS, and handling the consent intent flow.
 *
 * It is designed to be used within an Android component (like an Activity or Composable) that
 * needs to request user consent to read a single SMS message for verification purposes,
 * typically for One-Time Passwords (OTP).
 *
 * @property context The application or activity context.
 * @property launchConsent A lambda function to be executed when the consent intent is received.
 *                       This function should launch the provided `Intent` using an activity result
 *                       launcher (e.g., `ActivityResultLauncher.launch()`). The result will contain
 *                       the SMS message content if the user grants consent.
 */
class SmsUserConsentHelper(
    private val context: Context,
    private val launchConsent: (Intent) -> Unit
) {
    private var receiver: BroadcastReceiver? = null

    fun startListening() {
        val task = SmsRetriever.getClient(context).startSmsUserConsent(null)
        task.addOnFailureListener {
            // Handle failure
            Timber.e(it)
        }
    }

    fun registerReceiver() {
        receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if (SmsRetriever.SMS_RETRIEVED_ACTION == intent.action) {
                    val extras = intent.extras
                    val status = extras?.get(SmsRetriever.EXTRA_STATUS) as? Status

                    when (status?.statusCode) {
                        CommonStatusCodes.SUCCESS -> {
                            val consentIntent = extras.getParcelable<Intent>(
                                SmsRetriever.EXTRA_CONSENT_INTENT
                            )
                            consentIntent?.let { launchConsent(it) }
                        }
                        CommonStatusCodes.TIMEOUT -> {
                            // Timeout - stop listening
                        }
                    }
                }
            }
        }

        val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        ContextCompat.registerReceiver(
            context,
            receiver,
            intentFilter,
            ContextCompat.RECEIVER_EXPORTED
        )
    }

    fun unregisterReceiver() {
        receiver?.let {
            try {
                context.unregisterReceiver(it)
            } catch (e: IllegalArgumentException) {
                Timber.e(e)
                // Receiver not registered
            }
        }
        receiver = null
    }
}