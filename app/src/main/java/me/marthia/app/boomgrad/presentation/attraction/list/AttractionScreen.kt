package me.marthia.app.boomgrad.presentation.attraction.list

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import me.marthia.app.boomgrad.R
import me.marthia.app.boomgrad.domain.model.Attraction
import me.marthia.app.boomgrad.domain.model.AttractionCategory
import me.marthia.app.boomgrad.domain.model.AttractionContactInfo
import me.marthia.app.boomgrad.domain.model.AttractionImage
import me.marthia.app.boomgrad.domain.model.AttractionOpeningHours
import me.marthia.app.boomgrad.domain.model.CategoryType
import me.marthia.app.boomgrad.domain.model.Location
import me.marthia.app.boomgrad.domain.model.LocationType
import me.marthia.app.boomgrad.presentation.common.EmptyScreen
import me.marthia.app.boomgrad.presentation.common.ErrorScreen
import me.marthia.app.boomgrad.presentation.common.LoadingScreen
import me.marthia.app.boomgrad.presentation.components.ScaffoldElement
import me.marthia.app.boomgrad.presentation.components.BackgroundElement
import me.marthia.app.boomgrad.presentation.components.CardElement
import me.marthia.app.boomgrad.presentation.components.IconText
import me.marthia.app.boomgrad.presentation.components.SearchElement
import me.marthia.app.boomgrad.presentation.components.SurfaceElement
import me.marthia.app.boomgrad.presentation.theme.AppTheme
import me.marthia.app.boomgrad.presentation.theme.Theme
import me.marthia.app.boomgrad.presentation.util.debugPlaceholder
import me.marthia.app.boomgrad.presentation.util.rememberMockLazyPagingItems
import org.koin.androidx.compose.koinViewModel

@Composable
fun AttractionList(
    onAttractionSelected: (Long) -> Unit,
    viewModel: AttractionsViewModel = koinViewModel()
) {

    val lazyPagingItems = viewModel.attractionsPagingData.collectAsLazyPagingItems()

    ScaffoldElement(topBar = {
        Column(
            modifier = Modifier
                .fillMaxWidth().systemBarsPadding(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(modifier = Modifier.padding(start = 16.dp), text = stringResource(R.string.title_attraction_screen))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {

                SearchAttractions(
                    modifier = Modifier
                        .weight(2.2f)
                        .padding(start = 16.dp, bottom = 8.dp)
                )
                ToggleView(Modifier.weight(1f).padding(end = 16.dp))
            }
        }
    }) { paddingValues ->
        when (val loadState = lazyPagingItems.loadState.refresh) {
            is LoadState.Loading -> {
                LoadingScreen()
            }

            is LoadState.Error -> {
                ErrorScreen(
                    onRetry = { lazyPagingItems.retry() },
                    onBack = {}
                )
            }

            is LoadState.NotLoading -> {
                if (lazyPagingItems.itemCount == 0) {
                    EmptyScreen()
                } else {
                    AttractionList(
                        modifier = Modifier.padding(paddingValues),
                        state = lazyPagingItems,
                        onAttractionSelected = onAttractionSelected
                    )
                }
            }
        }
    }
}

@Composable
fun ToggleView(modifier: Modifier = Modifier) {
    var isMapView by remember { mutableStateOf(false) }
    SurfaceElement(modifier = modifier, shape = RoundedCornerShape(50)) {
        Row() {
            FilledTonalIconToggleButton(
                checked = isMapView,
                onCheckedChange = {
                    isMapView = isMapView.not()
                },
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_map_white_24),
                    contentDescription = "map view"
                )
            }

            FilledTonalIconToggleButton(
                checked = !isMapView,
                onCheckedChange = {
                    isMapView = isMapView.not()
                },
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_list_view_24),
                    contentDescription = "list view"
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAttractions(
    modifier: Modifier = Modifier,
) {
    val expanded = remember { mutableStateOf(false) }
    val searchQuery = remember { mutableStateOf("") }
    SearchElement(
        modifier = modifier,
        inputField = {
            BasicTextField(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                value = searchQuery.value,
                onValueChange = {
                    searchQuery.value = it
                },
                decorationBox = {
                    IconText(
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Rounded.Search,
                                contentDescription = "Search",
                                tint = Theme.colors.textHelp,
                            )
                        },
                        text = {
                            Text(
                                text = stringResource(R.string.home_screen_search_help_label),
                                style = MaterialTheme.typography.labelSmall,
                                color = Theme.colors.textHelp,
                            )
                        },
                    )
                },
            )

        },
        expanded = expanded.value,
        onExpandedChange = {
            expanded.value = false
        },
    ) {

        Text(modifier = Modifier.fillMaxWidth(), text = "Content")

    }
}


@Composable
fun AttractionList(
    modifier: Modifier = Modifier,
    state: LazyPagingItems<Attraction>,
    onAttractionSelected: (Long) -> Unit
) {

    BackgroundElement(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {

            items(
                count = state.itemCount,
                key = { index -> state[index]?.id ?: index }
            ) { index ->
                val attraction = state[index]
                attraction?.let { item ->
                    AttractionListItem(item = item, onAttractionSelected = onAttractionSelected)
                }
            }

            // Handle loading more items
            when (state.loadState.append) {
                is LoadState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                is LoadState.Error -> {
                    item {
                        ErrorItem(
                            message = "Failed to load more",
                            onRetry = { state.retry() }
                        )
                    }
                }

                is LoadState.NotLoading -> {
                    // Do nothing
                }
            }
        }
    }

}

@Composable
fun ErrorItem(
    message: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = message)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onRetry) {
            Text("Retry")
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
        modifier = modifier.clickable { onAttractionSelected(item.id) },
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
                    model = R.drawable.naghshe_jahan1,
                    contentDescription = "attraction image",
                    placeholder = debugPlaceholder(debugPreview = R.drawable.placeholder),
                    modifier = Modifier
                        .width(68.dp)
                        .height(80.dp)
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
@Composable
private fun PreviewAttractionList() {
    AppTheme {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            val mockAttractions = createMockAttractions(10)
            val lazyPagingItems = rememberMockLazyPagingItems(mockAttractions)

            AttractionList(
                modifier = Modifier.systemBarsPadding(),
                state = lazyPagingItems,
                onAttractionSelected = {}
            )
        }
    }
}

// Preview for Loading State
@Preview("Loading State", showBackground = true)
@Composable
private fun PreviewAttractionListLoading() {
    AppTheme {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            LoadingScreen()
        }
    }
}

// Preview for Error State
@Preview("Error State", showBackground = true)
@Composable
private fun PreviewAttractionListError() {
    AppTheme {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            ErrorScreen(
                onBack = { }
            )
        }
    }
}

// Preview for Empty State
@Preview("Empty State", showBackground = true)
@Composable
private fun PreviewAttractionListEmpty() {
    AppTheme {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            EmptyScreen()
        }
    }
}

// Helper function to create mock data
private fun createMockAttractions(count: Int) = List(count) { index ->
    Attraction(
        id = index.toLong(),
        category = AttractionCategory(
            id = 0,
            type = CategoryType.HERITAGE,
            description = "",
            image = ""
        ),
        images = listOf(
            AttractionImage(1, "https://picsum.photos/400"),
            AttractionImage(2, "https://picsum.photos/400")
        ),
        rating = 4.8f,
        reviewCount = 45,
        contactInfo = AttractionContactInfo(
            phone = "090312345678",
            address = "",
            website = "info@kal.com"
        ),
        openingHours = listOf(
            AttractionOpeningHours(
                id = 1,
                workingDate = "۱۸ فروردین ۱۳۹۰",
                workingTime = "۸ تا ۱۴",
                isClosed = false,
                notes = ""
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
            city = "اصفهان",
            province = "اصفهان"
        ),
        isFavorite = index % 2 == 0,
        reviews = listOf(),
        relatedTours = listOf() //fixme
    )
}