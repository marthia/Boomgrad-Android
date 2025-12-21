package me.marthia.app.boomgrad.presentation.login.otp

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.auth.api.phone.SmsRetriever


/**
 * A Composable function that encapsulates the logic for the SMS User Consent API.
 * It simplifies the process of requesting user consent to read a single SMS message
 * containing a one-time password (OTP) without needing full SMS permissions.
 *
 * This function handles the lifecycle of the `SmsRetrieverClient`, registers a
 * `BroadcastReceiver` to listen for the incoming SMS, and launches the consent
 * activity for the user. It uses a `DisposableEffect` to ensure the receiver
 * is properly unregistered when the composable leaves the composition.
 *
 * Usage:
 * ```
 * val smsVerifier = rememberSmsUserConsent { message ->
 *     // This lambda is called when the user grants consent and the SMS is received.
 *     // Extract the OTP from the 'message' string here.
 *     val code = extractOtpFromMessage(message)
 *     // ... update your UI state with the code ...
 * }
 *
 * // To start listening for an SMS:
 * Button(onClick = { smsVerifier.startListening() }) {
 *     Text("Listen for SMS")
 * }
 * ```
 *
 * @param onSmsReceived A lambda function that will be invoked with the full SMS message
 *                      string when it is successfully retrieved after the user gives consent.
 * @return An instance of [SmsUserConsentHelper] which provides a `startListening()` method
 *         to initiate the SMS listening process.
 */
@Composable
fun rememberSmsUserConsent(
    onSmsReceived: (String) -> Unit
): SmsUserConsentHelper {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val message = result.data?.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
            message?.let { onSmsReceived(it) }
        }
    }

    val helper = remember {
        SmsUserConsentHelper(context, launcher::launch)
    }

    DisposableEffect(Unit) {
        helper.registerReceiver()
        onDispose {
            helper.unregisterReceiver()
        }
    }

    return helper
}

/**
 * Extracts a One-Time Password (OTP) from a given SMS message string.
 *
 * This function searches the message for a sequence of 4, 5, or 6 consecutive digits.
 * It uses regular expressions to find the first match among these common OTP lengths.
 * The search prioritizes 4-digit codes, then 5-digit, and finally 6-digit codes.
 *
 * @param message The full SMS message string from which to extract the OTP.
 * @return The extracted OTP string (e.g., "1234") if a match is found, otherwise `null`.
 */
fun extractOtpFromMessage(message: String): String? {
    // Common patterns for OTP extraction
    val patterns = listOf(
        "\\b\\d{4}\\b",  // 4 digits
        "\\b\\d{5}\\b",  // 5 digits
        "\\b\\d{6}\\b",  // 6 digits
    )

    for (pattern in patterns) {
        val regex = Regex(pattern)
        val match = regex.find(message)
        if (match != null) {
            return match.value
        }
    }

    return null
}