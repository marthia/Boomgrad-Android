package me.marthia.app.boomgrad.presentation.profile.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import me.marthia.app.boomgrad.R
import me.marthia.app.boomgrad.presentation.components.BackgroundElement
import me.marthia.app.boomgrad.presentation.components.ButtonElement
import me.marthia.app.boomgrad.presentation.components.TextFieldElement
import me.marthia.app.boomgrad.presentation.theme.AppTheme
import me.marthia.app.boomgrad.presentation.theme.Theme
import me.marthia.app.boomgrad.presentation.util.ViewState
import me.marthia.app.boomgrad.presentation.util.debugPlaceholder

@Composable
fun EditProfileScreen() {

}


@Composable
fun EditProfileScreen(modifier: Modifier = Modifier) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {

        ProfilePicture()

        TextFieldElement(
            modifier = Modifier.fillMaxWidth(),
            value = "",
            label = {
                Text(
                    text = stringResource(R.string.label_edit_profile_fullname),
                    style = MaterialTheme.typography.bodyMedium,

                )
            },
            placeholder = {
                Text(text = stringResource(R.string.description_edit_profile_fullname))
            },
            onValueChange = {},
        )
        TextFieldElement(
            modifier = Modifier.fillMaxWidth(), value = "",
            label = {
                Text(
                    text = stringResource(R.string.label_edit_profile_email),
                    style = MaterialTheme.typography.bodyMedium,

                )
            },
            placeholder = {
                Text(text = stringResource(R.string.description_edit_profile_email))
            },
            onValueChange = {},
        )

        TextFieldElement(
            modifier = Modifier.fillMaxWidth(), value = "",
            label = {
                Text(
                    text = stringResource(R.string.label_edit_profile_phone),
                    style = MaterialTheme.typography.bodyMedium,

                )
            },
            placeholder = {
                Text(text = stringResource(R.string.description_edit_profile_phone))
            },
            onValueChange = {},
        )

        Spacer(Modifier.weight(1f))


        ButtonElement(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp, start = 16.dp, end = 16.dp, top = 48.dp),
            innerRowModifier = Modifier.fillMaxWidth(),
            backgroundGradient = Theme.colors.interactivePrimary,
            enabled = true,
            contentPadding = PaddingValues(
                horizontal = 16.dp,
                vertical = 8.dp
            ),
            onClick = {

            }) {
            if (Modifier is ViewState.Loading) {
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
    }
}

@Composable
fun ProfilePicture() {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data("image")
            .crossfade(true)
            .build(),
        contentDescription = "profile",
        placeholder = debugPlaceholder(debugPreview = R.drawable.placeholder),
        modifier = Modifier

            .size(120.dp)
            .clip(CircleShape),
        contentScale = ContentScale.Crop,
    )
}


@Preview("default", showBackground = true, showSystemUi = true)
@Composable
private fun PreviewEditProfile() {
    AppTheme {

        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {

            BackgroundElement(modifier = Modifier.fillMaxSize()) {
                EditProfileScreen(Modifier)
            }
        }
    }
}