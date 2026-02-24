package me.marthia.app.boomgrad.presentation.tour.list

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
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import me.marthia.app.boomgrad.R
import me.marthia.app.boomgrad.domain.model.AttractionCategory
import me.marthia.app.boomgrad.domain.model.CategoryType
import me.marthia.app.boomgrad.domain.model.TourList
import me.marthia.app.boomgrad.presentation.category.model.CategoryUi
import me.marthia.app.boomgrad.presentation.common.EmptyScreen
import me.marthia.app.boomgrad.presentation.common.ErrorScreen
import me.marthia.app.boomgrad.presentation.common.LoadingScreen
import me.marthia.app.boomgrad.presentation.components.ScaffoldElement
import me.marthia.app.boomgrad.presentation.components.BackgroundElement
import me.marthia.app.boomgrad.presentation.components.CardElement
import me.marthia.app.boomgrad.presentation.components.IconText
import me.marthia.app.boomgrad.presentation.theme.AppTheme
import me.marthia.app.boomgrad.presentation.util.debugPlaceholder
import me.marthia.app.boomgrad.presentation.util.rememberMockLazyPagingItems
import org.koin.androidx.compose.koinViewModel

@Composable
fun ToursListScreen(
    onAttractionSelected: (Long) -> Unit,
    viewModel: ToursViewModel = koinViewModel()
) {

    val lazyPagingItems = viewModel.toursPagingData.collectAsLazyPagingItems()

    ScaffoldElement { paddingValues ->
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
                    ToursList(
                        modifier = Modifier.padding(paddingValues),
                        state = lazyPagingItems,
                        onTourSelected = onAttractionSelected
                    )
                }
            }
        }
    }
}


@Composable
fun ToursList(
    modifier: Modifier = Modifier,
    state: LazyPagingItems<TourList>,
    onTourSelected: (Long) -> Unit
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
                val tour = state[index]
                tour?.let { item ->
                    TourListItem(item = item, onTourSelected = onTourSelected)
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
fun TourListItem(
    modifier: Modifier = Modifier,
    item: TourList,
    onTourSelected: (Long) -> Unit
) {
    CardElement(
        modifier = modifier.clickable { onTourSelected(item.id) },
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
//                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
//                    Text(
//                        text = item.location.name,
//                        style = MaterialTheme.typography.titleSmall,
//
//                        )
//                    Text(
//                        text = item.location.address,
//                        style = MaterialTheme.typography.labelSmall,
//                        color = Theme.colors.textHelp,
//                        overflow = TextOverflow.Ellipsis
//                    )
//                }
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
private fun PreviewToursList() {
    AppTheme {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            val mockAttractions = createMockTours(10)
            val lazyPagingItems = rememberMockLazyPagingItems(mockAttractions)

            ToursList(
                modifier = Modifier.systemBarsPadding(),
                state = lazyPagingItems,
                onTourSelected = {}
            )
        }
    }
}

// Preview for Loading State
@Preview("Loading State", showBackground = true)
@Composable
private fun PreviewToursListLoading() {
    AppTheme {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            LoadingScreen()
        }
    }
}

// Preview for Error State
@Preview("Error State", showBackground = true)
@Composable
private fun PreviewToursListError() {
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
private fun PreviewToursListEmpty() {
    AppTheme {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            EmptyScreen()
        }
    }
}

// Helper function to create mock data
private fun createMockTours(count: Int) = List(count) { index ->
    TourList(
        id = index.toLong(),
        title = "میدان نقش جهان $index",
        description = "با این تور یک روزه، سفری به قلب تاریخ و هنر اصفهان خواهید داشت. میدان نقش جهان، یکی از بزرگترین و زیباترین میادین جهان، میزبان شما خواهد بود. در این گشت، از شاهکارهای معماری دوران صفوی مانند مسجد شیخ لطف‌الله، مسجد امام و کاخ عالی‌قاپو دیدن خواهید کرد. همچنین، فرصت گشت و گذار در بازار قیصریه و خرید صنایع دستی اصیل اصفهان را خواهید داشت. این تور تجربه‌ای فراموش‌نشدنی از فرهنگ غنی ایران را برای شما به ارمغان می‌آورد.",
        images = listOf(),
        duration = 108,
        price = 123546.8,
        dueDate = "۱۶ بهمن",
        city = "اصفهان",
        category = AttractionCategory(
            id = 0,
            type = CategoryType.CREATIVE,
            description = "",
            image = ""
        ),
    )
}