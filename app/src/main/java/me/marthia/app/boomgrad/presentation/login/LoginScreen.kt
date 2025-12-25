package me.marthia.app.boomgrad.presentation.login

import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.marthia.app.boomgrad.R
import me.marthia.app.boomgrad.presentation.components.AppScaffold
import me.marthia.app.boomgrad.presentation.components.JetSnackBackground
import me.marthia.app.boomgrad.presentation.components.JetsnackButton
import me.marthia.app.boomgrad.presentation.components.JetsnackTextField
import me.marthia.app.boomgrad.presentation.theme.AppTheme
import me.marthia.app.boomgrad.presentation.theme.BaseTheme
import me.marthia.app.boomgrad.presentation.util.ViewState
import me.marthia.app.boomgrad.presentation.util.KeyboardAware
import me.marthia.app.boomgrad.presentation.util.RightToLeftLayout
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen() {

    val viewModel = koinViewModel<LoginViewModel>()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.uiState.collect {
            when (it) {
                is ViewState.Data<*> -> {
//                    navigator.navigate(OtpScreenDestination(phoneNumber = "09035135466")) // fixme
                    // remove callbacks
                    viewModel.clearError()
                }

                is ViewState.Error -> {
                    Toast.makeText(
                        context,
                        it.throwable.message,
                        Toast.LENGTH_LONG
                    ).show()
                    // remove callbacks
                    viewModel.clearError()
                }

                else -> {}
            }
        }
    }

    AppScaffold { p ->
        LoginScreenContent(modifier = Modifier.padding(p), onLogin = { username, password ->
            viewModel.onTriggerEvent(LoginEvent.Login(username = username, password = password))
        })
    }
}

@Composable
private fun LoginScreenContent(
    modifier: Modifier = Modifier,
    onLogin: (username: String, password: String) -> Unit
) {

    KeyboardAware(modifier = modifier.fillMaxSize()) { isKeyboardOpen ->
        val cardTopPadding by animateDpAsState(
            targetValue = if (isKeyboardOpen) 0.dp else 100.dp,
            label = "CardTopPaddingAnimation",
        )

        LoginCard(
            modifier = Modifier

                .padding(top = cardTopPadding),
            onLogin = onLogin
        )

    }
}


@Composable
fun LoginCard(
    modifier: Modifier = Modifier,
    onLogin: (username: String, password: String) -> Unit
) {
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }


    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
    ) {

        Text(
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
            text = stringResource(R.string.title_login_name),
            style = MaterialTheme.typography.headlineLarge
        )
        Text(
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
            text = stringResource(R.string.subtitle_login_description),
            style = MaterialTheme.typography.titleMedium
        )

        JetsnackTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            value = username,
            shape = RoundedCornerShape(50),
            label = {
                Text(
                    modifier = Modifier.padding(start = 16.dp, bottom = 8.dp),
                    text = stringResource(R.string.placeholder_login_username),
                    style = MaterialTheme.typography.labelLarge
                )
            },
            placeholder = {
                Text(
                    text = "example@gmail.com",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface.copy(
                            alpha = 0.2f
                        )
                    )
                )
            },
            onValueChange = {
                username = it
            },
        )

        JetsnackTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            value = password,
            shape = RoundedCornerShape(50),
            label = {
                Text(
                    modifier = Modifier.padding(start = 16.dp, bottom = 8.dp),
                    text = stringResource(R.string.placeholder_login_password),
                    style = MaterialTheme.typography.labelLarge
                )
            },
            placeholder = {
                Text(
                    text = "",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface.copy(
                            alpha = 0.2f
                        )
                    )
                )
            },
            onValueChange = {
                password = it
            },
        )


        JetsnackButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp, start = 16.dp, end = 16.dp, top = 48.dp),
            backgroundGradient = BaseTheme.colors.interactivePrimary,
            enabled = username.isNotBlank(),
            contentPadding = PaddingValues(
                horizontal = 16.dp,
                vertical = 8.dp
            ),
            onClick = {
                onLogin(username, password)
            }) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp, horizontal = 16.dp),
                text = stringResource(id = R.string.label_login_button),
                style = MaterialTheme.typography.labelLarge
            )
        }
    }

}


@Preview(showBackground = true, showSystemUi = true, backgroundColor = 0XFFE7F2FE)
@Composable
private fun PreviewLoginScreen() {
    AppTheme {

        RightToLeftLayout {
            JetSnackBackground(modifier = Modifier.fillMaxSize()) {
                LoginScreenContent { _, _ ->

                }
            }
        }
    }
}