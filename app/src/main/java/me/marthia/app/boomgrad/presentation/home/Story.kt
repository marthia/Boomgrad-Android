package me.marthia.app.boomgrad.presentation.home

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import me.marthia.app.boomgrad.R
import me.marthia.app.boomgrad.presentation.theme.Theme
import me.marthia.app.boomgrad.presentation.util.debugPlaceholder

@Composable
fun Story(
    modifier: Modifier = Modifier,
    id: Long,
    title: String,
    image: String,
    onCategorySelected: (Long) -> Unit
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(84.dp)
                .border(
                    width = 2.dp,
                    color = Theme.colors.materialTheme.primary,
                    shape = CircleShape,
                )
                .padding(5.dp), // 2dp border + 3dp gap
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(image)
                    .crossfade(true)
                    .build(),
                contentDescription = "profile",
                placeholder = debugPlaceholder(debugPreview = R.drawable.placeholder),
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .clickable { onCategorySelected(id) },
                contentScale = ContentScale.Crop,
            )
        }

        Text(text = title, style = MaterialTheme.typography.labelLarge)
    }
}