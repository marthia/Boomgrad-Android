package me.marthia.app.boomgrad.presentation.guide

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import me.marthia.app.boomgrad.R
import me.marthia.app.boomgrad.presentation.common.ErrorScreen
import me.marthia.app.boomgrad.presentation.common.LoadingScreen
import me.marthia.app.boomgrad.presentation.components.ScaffoldElement
import me.marthia.app.boomgrad.presentation.components.TopBar
import me.marthia.app.boomgrad.presentation.guide.model.GuideUi
import me.marthia.app.boomgrad.presentation.util.ViewState
import me.marthia.app.boomgrad.presentation.util.debugPlaceholder
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuideInfoScreen(
    guideId: Long,
    viewModel: GuideInfoViewModel = koinViewModel(),
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()


    ScaffoldElement(
        topBar = {
            TopBar(
                title = {
                    Text(stringResource(R.string.title_guide_info))
                },
                onBackClick = onBackClick
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (val state = uiState) {
                is ViewState.Loading -> LoadingScreen()
                is ViewState.Error -> ErrorScreen(onBack = onBackClick)
                is ViewState.Success -> {
                    GuideInfoScreen(state = state.value)
                }

                else -> {}
            }
        }
    }
}


@Composable
fun GuideInfoScreen(modifier: Modifier = Modifier, state: GuideUi) {
    Column(modifier = modifier.fillMaxWidth()) {
        Banner(
            modifier = Modifier,
            image = state.userImage,
            name = state.fullName,
            phone = state.phone
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = state.bio,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Justify,
        )
//        AvailableTours(tours = state.tours)
    }
}

@Composable
fun Banner(modifier: Modifier = Modifier, image: String, name: String, phone: String) {
    Column(modifier = modifier.fillMaxWidth()) {
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
}
