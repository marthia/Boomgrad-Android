package me.marthia.app.boomgrad.presentation.login.otp

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import me.marthia.app.boomgrad.presentation.components.AppScaffold
import me.marthia.app.boomgrad.presentation.components.DecoratedTextField
import me.marthia.app.boomgrad.presentation.components.JetsnackButton
import me.marthia.app.boomgrad.presentation.theme.AppTheme
import me.marthia.app.boomgrad.presentation.theme.BaseTheme
import me.marthia.app.boomgrad.presentation.util.KeyboardAware
import me.marthia.app.boomgrad.presentation.util.LeftToRightLayout
import me.marthia.app.boomgrad.presentation.util.ViewState
import org.koin.androidx.compose.koinViewModel

@Composable
fun OtpScreen(
    navBackStackEntry: NavBackStackEntry,
    phone: String,
) {

    val viewModel = koinViewModel<OtpViewModel>()
    val otp = viewModel.otpCode.collectAsState()

    val context = LocalContext.current
    val uiState = viewModel.otpState.collectAsState()


    // we navigate to home screen when otp is verified
    // otherwise show the error messages on toast
    // listening on ui state changes
    LaunchedEffect(uiState.value) {
        when (uiState.value) {
            is ViewState.Success<*> -> {
//                if (uiState.value.cast<BaseViewState.Data<OTPState>>().value.isCodeCorrect == true)
//                    navigator.navigate(HomeRouteDestination) {
                // Important: Pop up to root and inclusive=true cleans the back stack
//                        popUpTo(NavGraphs.root) { inclusive = true }
//                    }
            }

            is ViewState.Error -> {
                Toast.makeText(
                    context,
                    (uiState.value as ViewState.Error).throwable.message,
                    Toast.LENGTH_LONG
                ).show()
            }

            else -> {}
        }
    }

    AppScaffold(
        topBar = {
//            TopBar(
//                title = "احراز هویت شما",
//                navController = navController
//            )
        }
    ) { paddingValues ->


        OtpScreenContent(
            modifier = Modifier.padding(paddingValues),
            otpFromSms = otp.value,
            onAuthorize = { otp ->
                viewModel.verifyOtp(otp = otp)
            },
            onResendCode = {
//                viewModel.onTriggerEvent(OTPEvent.RequestForOtp(phone))
            }
        )
    }
}

@Composable
private fun OtpScreenContent(
    modifier: Modifier = Modifier,
    otpFromSms: String = "",
    onAuthorize: (String) -> Unit = {},
    onResendCode: () -> Unit = {}
) {

    val otpLength = 4
    val otp = remember { mutableStateOf(otpFromSms) }
    // SMS detection
    rememberSmsUserConsent { message ->
        // Extract OTP from message
        val code = extractOtpFromMessage(message)
        otp.value = code ?: ""
    }.also { // start listening for sms when composable is created
        it.startListening()
    }

    KeyboardAware(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                text = "کد تایید",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "کد تایید به شماره فلان ارسال شد",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(16.dp))
            LeftToRightLayout {
                DecoratedTextField(
                    modifier = Modifier.padding(),
                    value = otp.value,
                    length = otpLength,
                    onValueChange = { v ->
                        otp.value = v
                    })
            }

            Row {
                Text("کد احراز هویت ارسال نشد؟", style = MaterialTheme.typography.bodySmall)
                Text(
                    modifier = Modifier.clickable {
                        onResendCode()
                    },
                    text = "برای دریافت مجدد، کلیک کنید",
                    color = BaseTheme.colors.brand,
                    style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onPrimaryFixed)
                )
            }

            Spacer(modifier = Modifier.height(68.dp))

            JetsnackButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp, start = 16.dp, end = 16.dp),
                shape = MaterialTheme.shapes.small,
                innerRowModifier = Modifier.fillMaxWidth(),
                enabled = otp.value.length == 4,
                contentPadding = PaddingValues(
                    horizontal = 16.dp,
                    vertical = 8.dp
                ),
                onClick = { onAuthorize(otp.value) }
            ) {
                Text(
                    text = "احراز هویت",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewOtpContent() {
    AppTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            OtpScreenContent(
                onAuthorize = {}
            )
        }
    }
}