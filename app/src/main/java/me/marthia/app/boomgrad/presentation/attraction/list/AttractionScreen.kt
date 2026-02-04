package me.marthia.app.boomgrad.presentation.attraction.list

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import me.marthia.app.boomgrad.R
import me.marthia.app.boomgrad.domain.model.Attraction
import me.marthia.app.boomgrad.domain.model.AttractionContactInfo
import me.marthia.app.boomgrad.domain.model.AttractionOpeningHours
import me.marthia.app.boomgrad.domain.model.City
import me.marthia.app.boomgrad.domain.model.Location
import me.marthia.app.boomgrad.domain.model.LocationType
import me.marthia.app.boomgrad.presentation.common.ErrorScreen
import me.marthia.app.boomgrad.presentation.common.LoadingScreen
import me.marthia.app.boomgrad.presentation.components.AppScaffold
import me.marthia.app.boomgrad.presentation.components.IconText
import me.marthia.app.boomgrad.presentation.components.BackgroundElement
import me.marthia.app.boomgrad.presentation.components.CardElement
import me.marthia.app.boomgrad.presentation.theme.AppTheme
import me.marthia.app.boomgrad.presentation.theme.Theme
import me.marthia.app.boomgrad.presentation.util.ViewState
import me.marthia.app.boomgrad.presentation.util.debugPlaceholder
import org.koin.androidx.compose.koinViewModel

@Composable
fun AttractionList(
    onAttractionSelected: (Long) -> Unit,
    viewModel: AttractionsViewModel = koinViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()



    when (uiState) {
        is ViewState.Loading -> LoadingScreen()
        is ViewState.Error -> ErrorScreen()
        is ViewState.Success -> {

            AppScaffold() {
                AttractionList(
                    Modifier.padding(it),
                    state = (uiState as ViewState.Success<AttractionsUiState>).value.mockList,
                    onAttractionSelected = onAttractionSelected
                )
            }
        }

        else -> {}
    }
}


@Composable
fun AttractionList(
    modifier: Modifier = Modifier,
    state: List<Attraction>,
    onAttractionSelected: (Long) -> Unit
) {

    BackgroundElement(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(state) { item ->
                AttractionListItem(item = item, onAttractionSelected = onAttractionSelected)
            }
        }
    }
}

@Composable
fun AttractionListItem(
    modifier: Modifier = Modifier,
    item: Attraction,
    onAttractionSelected: (Long) -> Unit
) {
    CardElement(
        modifier = modifier.clickable { onAttractionSelected(-1) },
        elevation = 0.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                modifier = Modifier.weight(4f),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = item.images.first(),
                    contentDescription = "attraction image",
                    placeholder = debugPlaceholder(debugPreview = R.drawable.placeholder),
                    modifier = Modifier
                        .width(68.dp)
                        .clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop,
                )
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = item.location.name,
                        style = MaterialTheme.typography.titleSmall,

                    )
                    Text(
                        text = item.location.address,
                        style = MaterialTheme.typography.labelSmall,
                        color = Theme.colors.textHelp,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            IconText(
                modifier = Modifier.weight(1f),
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.icon_star_20),
                        contentDescription = "score",
                        tint = Color.Unspecified
                    )
                },
                text = {
                    Text(
                        "4.8",
                        style = MaterialTheme.typography.labelLarge,

                    )
                },
            )
        }
    }
}


@Preview("default", showBackground = true, showSystemUi = true)
@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("large font", fontScale = 2f)
@Composable
private fun PreviewCategory() {
    AppTheme {

        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {

            AttractionList(
                modifier = Modifier.systemBarsPadding(), listOf<Attraction>(
                    Attraction(
                        id = "id",
                        category = "category",
                        images = listOf("https://picsum.photos/400", "https://picsum.photos/400"),
                        rating = 4.8f,
                        reviewCount = 45,
                        contactInfo = AttractionContactInfo(
                            phone = "090312345678",
                            email = "",
                            website = "info@kal.com"
                        ),
                        openingHours = listOf(
                            AttractionOpeningHours(
                                date = "۱۸ فروردین ۱۳۹۰",
                                workingHour = "۸ تا ۱۴"
                            )
                        ),
                        location = Location(
                            id = 1,
                            name = "میدان نقش جهان",
                            description = "description",
                            latitude = 52.1232,
                            longitude = 30.123123,
                            type = LocationType.ATTRACTION,
                            address = "خیابان استانداری خیابان سپه بانک ملی ایران ورودی میدان نقش جهان",
                            city = City(
                                id = 1,
                                name = "اصفهان",
                                county = "اصفهان",
                                province = "اصفهان"
                            ),
                        ),
                        isFavorite = true,
                    )
                ),
                onAttractionSelected = { }
            )
        }
    }
}