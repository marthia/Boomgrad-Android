package me.marthia.app.boomgrad.presentation.login.otp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status


/**
 * A Composable function that manages the lifecycle of an SMS Retriever.
 * It uses the Google Play Services' SMS Retriever API to automatically read an SMS
 * containing a one-time password (OTP) without requiring user permissions, provided the SMS
 * is formatted with a specific hash string unique to the app.
 *
 * This function creates and remembers a [SmsRetrieverHelper], registers a [BroadcastReceiver]
 * to listen for incoming SMS messages when the composable enters the composition, and
 * unregisters it upon leaving the composition to prevent memory leaks.
 *
 * The helper's `startListening()` method must be called to initiate the listening process.
 *
 * @param onSmsReceived A lambda function that will be invoked with the full content of the
 * retrieved SMS message. The caller is responsible for parsing the OTP from this message.
 * @return An instance of [SmsRetrieverHelper] which can be used to start the listening process
 * via its `startListening()` method.
 */
@Composable
fun rememberSmsRetriever(
    onSmsReceived: (String) -> Unit
): SmsRetrieverHelper {
    val context = LocalContext.current

    val helper = remember {
        SmsRetrieverHelper(context, onSmsReceived)
    }

    DisposableEffect(Unit) {
        helper.registerReceiver()
        onDispose {
            helper.unregisterReceiver()
        }
    }

    return helper
}

class SmsRetrieverHelper(
    private val context: Context,
    private val onSmsReceived: (String) -> Unit
) {
    private var receiver: BroadcastReceiver? = null

    fun startListening() {
        val client = SmsRetriever.getClient(context)
        val task = client.startSmsRetriever()

        task.addOnSuccessListener {
            // Started listening
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
                            val message = extras.get(SmsRetriever.EXTRA_SMS_MESSAGE) as? String
                            message?.let { onSmsReceived(it) }
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
                // Receiver not registered
            }
        }
        receiver = null
    }
}