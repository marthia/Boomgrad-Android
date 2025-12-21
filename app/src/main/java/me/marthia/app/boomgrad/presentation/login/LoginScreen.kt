package me.marthia.app.boomgrad.presentation.login

import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.marthia.app.boomgrad.R
import me.marthia.app.boomgrad.presentation.theme.AppTheme
import me.marthia.app.boomgrad.presentation.theme.BaseTheme
import me.marthia.app.boomgrad.presentation.util.BaseFilledTextField
import me.marthia.app.boomgrad.presentation.util.BaseViewState
import me.marthia.app.boomgrad.presentation.util.KeyboardAware
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen() {

    val viewModel = koinViewModel<LoginViewModel>()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.uiState.collect {
            when (it) {
                is BaseViewState.Data<*> -> {
//                    navigator.navigate(OtpScreenDestination(phoneNumber = "09035135466")) // fixme
                    // remove callbacks
                    viewModel.clearError()
                }

                is BaseViewState.Error -> {
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

    Scaffold { p ->
        LoginScreenContent(modifier = Modifier.padding(p), onLogin = { phone ->
            viewModel.onTriggerEvent(LoginEvent.Login(phone))
        })
    }
}

@Composable
private fun LoginScreenContent(
    modifier: Modifier = Modifier,
    onLogin: (username: String) -> Unit
) {

    KeyboardAware(modifier = modifier.fillMaxSize()) { isKeyboardOpen ->
        val cardTopPadding by animateDpAsState(
            targetValue = if (isKeyboardOpen) 0.dp else 100.dp,
            label = "CardTopPaddingAnimation",
        )

        LoginCard(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = cardTopPadding),
            onLogin = onLogin
        )

    }
}


@Composable
fun LoginCard(
    modifier: Modifier = Modifier,
    onLogin: (phone: String) -> Unit
) {
    var phone by rememberSaveable { mutableStateOf("") }


    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
    ) {

        Text(
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
            text = stringResource(R.string.title_login_name),
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
        )
        Text(
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
            text = stringResource(R.string.subtitle_login_description),
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Light)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Surface(
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
            shape = MaterialTheme.shapes.extraLarge,
            color = MaterialTheme.colorScheme.primary
        ) {
            Box(modifier = Modifier.size(width = 32.dp, height = 4.dp))
        }

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 16.dp),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant
        )
    }


    BaseFilledTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Person, contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
            )
        },

        placeholder = {
            Text(
                text = stringResource(R.string.placeholder_login_username_description),
                style = MaterialTheme.typography.labelMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface.copy(
                        alpha = 0.2f
                    )
                )
            )
        },
        title = {
            Text(
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp),
                text = stringResource(R.string.placeholder_login_username),
                style = MaterialTheme.typography.labelLarge
            )
        },


        singleLine = true,
        value = phone,
        onValueChange = {
            phone = it
        })




    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp, start = 16.dp, end = 16.dp, top = 48.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
        shape = MaterialTheme.shapes.small,
        enabled = phone.isNotBlank(),
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 8.dp
        ),
        onClick = {
            onLogin(phone)
        }) {
        Text(
            text = stringResource(id = R.string.label_login_button),
            style = MaterialTheme.typography.labelLarge
        )
    }
}


@Preview(showBackground = true, showSystemUi = true, backgroundColor = 0XFFE7F2FE)
@Composable
private fun PreviewLoginScreen() {
    AppTheme {

        LoginScreenContent { _ ->

        }
    }
}