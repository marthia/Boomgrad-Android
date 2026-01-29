package me.marthia.app.boomgrad.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import me.marthia.app.boomgrad.R
import me.marthia.app.boomgrad.domain.model.Attraction
import me.marthia.app.boomgrad.domain.model.Tour
import me.marthia.app.boomgrad.presentation.components.IconText
import me.marthia.app.boomgrad.presentation.components.JetsnackSurface
import me.marthia.app.boomgrad.presentation.theme.BaseTheme
import me.marthia.app.boomgrad.presentation.theme.HanaGreen8
import me.marthia.app.boomgrad.presentation.util.debugPlaceholder

@Composable
fun HomeScreen() {

    Column() {
        TopBar()
        CreateTour(modifier = Modifier.padding(16.dp))
    }
}

@Composable
private fun TopBar(modifier: Modifier = Modifier) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        AsyncImage(
            "https://picsum.photos/200",
            contentDescription = "profile",
            placeholder = debugPlaceholder(debugPreview = R.drawable.placeholder),
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
        )
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                stringResource(R.string.tour_detail_guide_label),
                style = MaterialTheme.typography.labelSmall,
                color = BaseTheme.colors.textHelp
            )
            Text(
                text = "guideInfo.fullName",
                style = MaterialTheme.typography.titleSmall,
                color = BaseTheme.colors.textSecondary
            )

            HorizontalDivider()

            Text(
                text = "۱۲ رزرو از ظرفیت ۱۲",
                style = MaterialTheme.typography.titleSmall,
                color = BaseTheme.colors.textSecondary
            )
        }
    }
}


@Composable
fun CreateTour(modifier: Modifier = Modifier) {
    JetsnackSurface(

        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column() {
                Text(text = stringResource(id = R.string.label_home_create_tour))
                Text(text = stringResource(id = R.string.description_home_create_tour))
            }

            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = stringResource(id = R.string.label_home_create_tour)
            )
        }
    }
}


@Composable
private fun ActiveTours(list: List<Tour>) {

    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        IconText(
            modifier = Modifier.padding(start = 16.dp, bottom = 16.dp),
            text = {
                Text(
                    stringResource(R.string.home_screen_active_tours_label),
                    style = MaterialTheme.typography.titleMedium
                )
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.icon_featured_24),
                    tint = Color.Unspecified,
                    contentDescription = "Featured",
                )
            },
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp),
        ) {
            items(list) { tour ->

                Row(
                    modifier = Modifier
                        .width(150.dp)
                        .height(230.dp),
                ) {
                    Column {

                        Text(
                            modifier = Modifier,
                            text = tour.title,
                            style = MaterialTheme.typography.titleSmall,
                        )
                    }

                }


            }
        }

    }
}

@Preview
@Composable
private fun PreviewHomeScreenGuide() {
    HomeScreen()
}