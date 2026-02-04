package me.marthia.app.boomgrad.presentation.login

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.marthia.app.boomgrad.R
import me.marthia.app.boomgrad.presentation.components.AppScaffold
import me.marthia.app.boomgrad.presentation.components.BackgroundElement
import me.marthia.app.boomgrad.presentation.components.JetsnackButton
import me.marthia.app.boomgrad.presentation.components.TextFieldElement
import me.marthia.app.boomgrad.presentation.theme.AppTheme
import me.marthia.app.boomgrad.presentation.theme.Theme
import me.marthia.app.boomgrad.presentation.util.KeyboardAware
import me.marthia.app.boomgrad.presentation.util.ViewState
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {

    val viewModel = koinViewModel<LoginViewModel>()

    val viewState by viewModel.loginState.collectAsState()

    AppScaffold { paddingValues ->

        LoginScreenContent(
            modifier = Modifier.padding(paddingValues),
            viewState = viewState, onLogin = viewModel::login
        )
    }

    LaunchedEffect(viewState) {
        when (viewState) {
            is ViewState.Error -> {

            }

            is ViewState.Success<*> -> {

            }

            else -> {}
        }
    }
}

@Composable
private fun LoginScreenContent(
    modifier: Modifier = Modifier,
    viewState: ViewState<*>,
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
            viewState = viewState,
            onLogin = onLogin
        )

    }
}


@Composable
fun LoginCard(
    modifier: Modifier = Modifier,
    viewState: ViewState<*>,
    onLogin: (username: String, password: String) -> Unit
) {
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }


    Column(
        modifier = modifier
            .fillMaxWidth(),
    ) {

        Text(
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
            text = stringResource(R.string.title_login_name),
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
            text = stringResource(R.string.subtitle_login_description),
            style = MaterialTheme.typography.titleSmall
        )


        Spacer(Modifier.height(24.dp))

        TextFieldElement(
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

        TextFieldElement(
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
            innerRowModifier = Modifier.fillMaxWidth(),
            backgroundGradient = Theme.colors.gradientTurq8Green8,
            enabled = true/*username.isNotBlank() && password.isNotBlank() && viewState !is ViewState.Loading*/,
            contentPadding = PaddingValues(
                horizontal = 16.dp,
                vertical = 8.dp
            ),
            onClick = {
                onLogin(username, password)
            }) {
            if (viewState is ViewState.Loading) {
                CircularProgressIndicator()
            } else {
                Text(
                    modifier = Modifier
                        .padding(vertical = 12.dp, horizontal = 16.dp),
                    text = stringResource(id = R.string.label_login_button),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
        TermsAndConditionsText(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), onTermsClick = {

        })
    }

}


@Composable
fun TermsAndConditionsText(
    modifier: Modifier = Modifier,
    onTermsClick: () -> Unit,
) {
    val annotatedText = buildAnnotatedString {
        append(stringResource(R.string.description_terms_and_conditions_1))
        append(" ")

        // Create the link annotation
        val linkAnnotation = LinkAnnotation.Clickable(
            tag = "terms",
            styles = TextLinkStyles(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.primary,
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.Medium
                )
            ),
            linkInteractionListener = {
                onTermsClick()
            }
        )

        // Apply the link to the text
        withLink(linkAnnotation) {
            append(stringResource(R.string.description_terms_and_conditions_2)) // "شرایط و قوانین"
        }

        append(" ")

        append(stringResource(R.string.description_terms_and_conditions_3))
    }

    Text(
        text = annotatedText,
        modifier = modifier,
        style = MaterialTheme.typography.bodyMedium.copy(
            textAlign = TextAlign.Center
        )
    )
}


@Preview(showBackground = true, showSystemUi = true, locale = "fa")
@Composable
private fun PreviewLoginScreen() {
    AppTheme {
        BackgroundElement(modifier = Modifier.fillMaxSize()) {
            LoginScreenContent(viewState = ViewState.Idle) { _, _ ->
            }
        }
    }
}