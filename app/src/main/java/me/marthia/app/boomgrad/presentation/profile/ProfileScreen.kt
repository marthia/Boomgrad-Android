package me.marthia.app.boomgrad.presentation.profile

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import me.marthia.app.boomgrad.R
import me.marthia.app.boomgrad.domain.model.Profile
import me.marthia.app.boomgrad.presentation.common.ErrorScreen
import me.marthia.app.boomgrad.presentation.common.LoadingScreen
import me.marthia.app.boomgrad.presentation.components.AppScaffold
import me.marthia.app.boomgrad.presentation.components.BackgroundElement
import me.marthia.app.boomgrad.presentation.profile.component.LinkItem
import me.marthia.app.boomgrad.presentation.theme.AppTheme
import me.marthia.app.boomgrad.presentation.theme.Theme
import me.marthia.app.boomgrad.presentation.util.ViewState
import me.marthia.app.boomgrad.presentation.util.debugPlaceholder
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState) {
        is ViewState.Loading -> LoadingScreen()
        is ViewState.Error -> ErrorScreen()
        is ViewState.Success -> {

            AppScaffold() {
                ProfileScreen(
                    modifier = Modifier
                        .padding(it),
                    state = (uiState as ViewState.Success<ProfileUiState>).value
                )
            }
        }

        else -> {}
    }

}


@Composable
fun ProfileScreen(modifier: Modifier = Modifier, state: ProfileUiState) {

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Banner(
            image = state.item.image,
            name = state.item.name,
            phone = state.item.phone,
        )
        Links()
        Logout()
    }
}

@Composable
private fun ColumnScope.Banner(image: String, name: String, phone: String) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(image)
            .crossfade(true)
            .build(),
        contentDescription = "profile",
        placeholder = debugPlaceholder(debugPreview = R.drawable.placeholder),
        modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .size(120.dp)
            .clip(CircleShape),
        contentScale = ContentScale.Crop,
    )
    Text(
        modifier = Modifier
            .align(Alignment.CenterHorizontally),
        text = name,
        style = MaterialTheme.typography.titleLarge
    )
    Text(
        modifier = Modifier
            .align(Alignment.CenterHorizontally),
        text = phone
    )
    Spacer(Modifier.height(8.dp))
}

@Composable
private fun Links() {

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        LinkItem(
            label = stringResource(R.string.label_profile_edit),
            icon = painterResource(R.drawable.icon_user_edit_24)
        )
        LinkItem(
            label = stringResource(R.string.label_profile_my_trips),
            icon = painterResource(R.drawable.icon_briefcase_24)
        )

        LinkItem(
            label = stringResource(R.string.label_profile_logout),
            icon = painterResource(R.drawable.icon_logout_red_24),
            containerColor = Theme.colors.materialTheme.errorContainer,
            iconContainerColor = Theme.colors.materialTheme.surface,
            iconTint = Theme.colors.materialTheme.error,
            textColor = Theme.colors.materialTheme.error,
            navigateIconVisible = false
        )
    }
}


@Composable
private fun Logout() {

}


@Preview("default", showBackground = true, showSystemUi = true, locale = "fa")
@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("large font", fontScale = 2f)
@Composable
private fun PreviewProfile() {
    AppTheme {

        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {

            BackgroundElement(modifier = Modifier.fillMaxSize()) {
                ProfileScreen(
                    modifier = Modifier.systemBarsPadding(), state = ProfileUiState(
                        item = Profile(
                            id = 1,
                            name = "محمد رضایی",
                            username = "",
                            email = "",
                            phone = "09035135466",
                            image = "",
                        )
                    )
                )
            }
        }
    }
}