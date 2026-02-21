@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)

package me.marthia.app.boomgrad.presentation.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
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
import me.marthia.app.boomgrad.domain.model.Attraction
import me.marthia.app.boomgrad.domain.model.AttractionCategory
import me.marthia.app.boomgrad.domain.model.TourList
import me.marthia.app.boomgrad.presentation.FilterSharedElementKey
import me.marthia.app.boomgrad.presentation.category.CategoryTag
import me.marthia.app.boomgrad.presentation.common.ErrorScreen
import me.marthia.app.boomgrad.presentation.common.LoadingScreen
import me.marthia.app.boomgrad.presentation.components.BackgroundElement
import me.marthia.app.boomgrad.presentation.components.ButtonElement
import me.marthia.app.boomgrad.presentation.components.CardElement
import me.marthia.app.boomgrad.presentation.components.IconText
import me.marthia.app.boomgrad.presentation.components.HorizontalDividerElement
import me.marthia.app.boomgrad.presentation.components.JetVerticalDivider
import me.marthia.app.boomgrad.presentation.components.PlainButton
import me.marthia.app.boomgrad.presentation.components.ScaffoldElement
import me.marthia.app.boomgrad.presentation.components.SearchElement
import me.marthia.app.boomgrad.presentation.components.SurfaceElement
import me.marthia.app.boomgrad.presentation.theme.AppTheme
import me.marthia.app.boomgrad.presentation.theme.HanaGreen8
import me.marthia.app.boomgrad.presentation.theme.Theme
import me.marthia.app.boomgrad.presentation.util.ViewState
import me.marthia.app.boomgrad.presentation.util.debugPlaceholder
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    onTourSelected: (Long) -> Unit,
    onCategorySelected: (Long) -> Unit,
    onAttractionSelected: (Long) -> Unit
) {

    val viewModel: HomeViewModel = koinViewModel()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()



    ScaffoldElement() {
        when (val state = uiState) {
            is ViewState.Loading -> LoadingScreen()
            is ViewState.Error -> ErrorScreen()
            is ViewState.Success -> {
                SharedTransitionLayout {
                    Box {
                        HomeScreenContent(
                            modifier = Modifier,
                            paddingValues = it,
                            sharedTransitionScope = this@SharedTransitionLayout,
                            categories = state.value.categories,
                            topAttractions = state.value.topAttractions,
                            forYouTours = state.value.forYouTours,
                            recommendedThisWeek = state.value.weekRecommended,
                            onTourSelected = onTourSelected,
                            onCategorySelected = onCategorySelected,
                            onAttractionSelected = onAttractionSelected,
                        )


                    }
                }
            }

            else -> {}
        }
    }
}


@Composable
private fun HomeScreenContent(
    modifier: Modifier = Modifier,
    sharedTransitionScope: SharedTransitionScope,
    paddingValues: PaddingValues,
    categories: List<AttractionCategory>,
    topAttractions: List<Attraction>,
    forYouTours: List<TourList>,
    recommendedThisWeek: List<TourList>,
    onTourSelected: (Long) -> Unit,
    onCategorySelected: (Long) -> Unit,
    onAttractionSelected: (Long) -> Unit,
) {
    var citySelectionVisible by remember {
        mutableStateOf(false)
    }


    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
    ) {
        Spacer(Modifier.height(paddingValues.calculateTopPadding()))

        HomeTopBar(modifier = Modifier.fillMaxWidth())


        SearchAll(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            filtersVisible = citySelectionVisible,
            showCitySelection = {
                citySelectionVisible = true
            },
            sharedTransitionScope = sharedTransitionScope,
        )

        Spacer(Modifier.height(24.dp))
        StorySection(
            modifier = Modifier.fillMaxWidth(),
            categories = categories,
            onCategorySelected = onCategorySelected
        )
        Recommended(list = recommendedThisWeek, onTourSelected = onTourSelected)
        ForYou(list = forYouTours, onTourSelected = onTourSelected)
        TopDestinations(list = topAttractions, onAttractionSelected = onAttractionSelected)

        Spacer(
            Modifier.windowInsetsBottomHeight(
                WindowInsets.navigationBars.add(WindowInsets(bottom = 80.dp + paddingValues.calculateBottomPadding())),
            ),
        )
    }

    AnimatedVisibility(citySelectionVisible, enter = fadeIn(), exit = fadeOut()) {
        CurrentCityScreen(
            animatedVisibilityScope = this@AnimatedVisibility,
            sharedTransitionScope = sharedTransitionScope,
        ) { citySelectionVisible = false }
    }
}

@Composable
fun SearchAll(
    modifier: Modifier = Modifier,
    showCitySelection: () -> Unit,
    filtersVisible: Boolean,
    sharedTransitionScope: SharedTransitionScope
) {
    val expanded = remember { mutableStateOf(false) }
    val searchQuery = remember { mutableStateOf("") }
    SearchElement(
        modifier = modifier,
        inputField = {
            with(sharedTransitionScope) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {

                    BasicTextField(
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

                    JetVerticalDivider()

                    AnimatedVisibility(!filtersVisible) {
                        PlainButton(
                            onClick = { showCitySelection() },
                            modifier = Modifier
                                .sharedBounds(
                                    sharedContentState =
                                        rememberSharedContentState(FilterSharedElementKey),
                                    animatedVisibilityScope = this@AnimatedVisibility,
                                    resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
                                ),
                            contentPadding = PaddingValues(8.dp)
                        ) {
                            IconText(
                                text = {
                                    Text(
                                        text = "تهران | تهران",
                                        style = MaterialTheme.typography.labelMedium
                                    )
                                },
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(R.drawable.icon_location_16),
                                        "Choose Your Location"
                                    )
                                },
                                trailingIcon = {
                                    Icon(Icons.Rounded.KeyboardArrowDown, "Arrow Icon")
                                },
                            )
                        }
                    }
                }
            }


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
fun HomeTopBar(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(R.drawable.cloud_top_03)
                .crossfade(true)
                .build(),
            contentDescription = "profile",
            modifier = Modifier
                .align(Alignment.TopStart)
                .scale(8f),
            contentScale = ContentScale.Inside,
        )

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(R.drawable.cloud_top_01)
                .crossfade(true)
                .build(),
            contentDescription = "profile",
            modifier = Modifier
                .align(Alignment.TopEnd)
                .scale(8f),
            contentScale = ContentScale.Inside,
        )
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(R.drawable.cloud_top_02)
                .crossfade(true)
                .build(),
            contentDescription = "profile",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .scale(8f),
            contentScale = ContentScale.Inside,
        )

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(R.drawable.logo)
                .crossfade(true)
                .build(),
            contentDescription = "profile",
            modifier = Modifier
                .size(96.dp)
                .align(Alignment.Center)
                .scale(3f),
            contentScale = ContentScale.Inside,
        )
    }
}

@Composable
fun StorySection(
    modifier: Modifier = Modifier,
    categories: List<AttractionCategory>,
    onCategorySelected: (Long) -> Unit
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) {
        items(categories) { category ->
            Story(
                id = category.id,
                title = category.name,
                image = category.image,
                onCategorySelected = onCategorySelected
            )
        }
    }
}

@Composable
fun Recommended(list: List<TourList>, onTourSelected: (Long) -> Unit) {
    val pagerState = rememberPagerState(
        initialPage = 0, initialPageOffsetFraction = 0f,
        pageCount = {
            list.size
        },
    )
    Column(Modifier.padding(16.dp)) {
        IconText(
            modifier = Modifier.padding(bottom = 16.dp),
            text = {
                Text(
                    stringResource(R.string.home_screen_week_recommendation_label),
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
        HorizontalPager(state = pagerState) { index ->
            CardElement {

                Column(modifier = Modifier.padding(16.dp)) {
                    RecommendedImages(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        images = list[index].images
                    )

                    HorizontalDividerElement(modifier = Modifier.padding(top = 60.dp, bottom = 24.dp))


                    Text(text = list[index].title, style = MaterialTheme.typography.titleMedium)
                    Text(
                        list[index].description,
                        style = MaterialTheme.typography.bodyMedium
                    )


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {

                        CategoryTag(title = list[index].category.name)

                        ButtonElement(
                            shape = CircleShape,
                            contentPadding = PaddingValues(8.dp),
                            onClick = { onTourSelected(list[index].id) },
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                                contentDescription = "add"
                            )
                        }

                    }
                }
            }
        }

    }
}

@Composable
fun RecommendedImages(modifier: Modifier = Modifier, images: List<String>) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://api2.kojaro.com/media/2024-6-9d0b4e18-dd75-4c03-98bd-681d3c5405dd-67c46117c1067c5ba768c245?w=750&q=80")
                .crossfade(true)
                .build(),
            placeholder = debugPlaceholder(debugPreview = R.drawable.placeholder_vertical),
            contentDescription = "contentDescription",
            modifier = Modifier
                .width(130.dp)
                .fillMaxHeight()
                .offset(x = 80.dp, y = 24.dp)
                .rotate(-6f)
                .clip(MaterialTheme.shapes.large),
            contentScale = ContentScale.Crop,
        )

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://api2.kojaro.com/media/2024-6-fa531ae0-4d15-4ce9-8ebb-b241b57a1f66-67c46117c1067c5ba768c248?w=750&q=80")
                .crossfade(true)
                .build(),
            placeholder = debugPlaceholder(debugPreview = R.drawable.placeholder_vertical),
            contentDescription = "contentDescription",
            modifier = Modifier
                .width(130.dp)
                .fillMaxHeight()
                .offset(x = -68.dp, y = 16.dp)
                .rotate(13f)
                .clip(MaterialTheme.shapes.large),
            contentScale = ContentScale.Crop,
        )

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://api2.kojaro.com/media/2024-6-14705d7f-5b2d-4bc4-90f6-08e1fdcdc1dd-67c46117c1067c5ba768c252?w=750&q=80")
                .crossfade(true)
                .build(),
            placeholder = debugPlaceholder(debugPreview = R.drawable.placeholder_vertical),
            contentDescription = "contentDescription",
            modifier = Modifier
                .width(130.dp)
                .fillMaxHeight()
                .offset(y = 32.dp)
                .clip(MaterialTheme.shapes.large),
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
fun ForYou(list: List<TourList>, onTourSelected: (Long) -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
    ) {
        IconText(
            modifier = Modifier.padding(bottom = 16.dp, start = 16.dp),
            text = {
                Text(
                    text = stringResource(R.string.home_screen_for_you_label),
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
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(list) { item ->

                Box(
                    modifier = Modifier
                        .width(280.dp)
                        .height(370.dp)
                        .clickable(enabled = true, onClick = { onTourSelected(item.id) }),

                    ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("https://api2.kojaro.com/media/2024-6-94452122-7d0c-4af2-91c7-f3e96febae2a-67c46117c1067c5ba768c24e")
                            .crossfade(true)
                            .build(),
                        placeholder = debugPlaceholder(debugPreview = R.drawable.placeholder_vertical),
                        contentDescription = "contentDescription",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(MaterialTheme.shapes.medium),
                        contentScale = ContentScale.Crop,
                        colorFilter = ColorFilter.tint(
                            color = Color.Black.copy(alpha = 0.4f),
                            blendMode = BlendMode.Darken,
                        ),
                    )
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.BottomStart),
                    ) {


                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.titleMedium,
                            color = Theme.colors.materialTheme.surface,
                        )
                        HorizontalDividerElement(
                            modifier = Modifier.padding(vertical = 8.dp),
                            color = Theme.colors.materialTheme.outline
                        )
                        IconText(
                            text = {
                                Text(
                                    text = item.city,
                                    color = Theme.colors.materialTheme.surface
                                )
                            },
                            leadingIcon = {

                                SurfaceElement(
                                    shape = CircleShape,
                                    color = Theme.colors.materialTheme.background,
                                ) {
                                    Icon(
                                        modifier = Modifier.padding(8.dp),
                                        painter = painterResource(R.drawable.icon_location_16),
                                        contentDescription = "Location",
                                    )
                                }
                            },
                        )
                    }

                }
            }
        }

    }
}

@Composable
fun TopDestinations(list: List<Attraction>, onAttractionSelected: (Long) -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        IconText(
            modifier = Modifier.padding(start = 16.dp, bottom = 16.dp),
            text = {
                Text(
                    stringResource(R.string.home_screen_top_attractions_label),
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
        LazyRow(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp),
        ) {
            items(list) { attraction ->

                Box(
                    modifier = Modifier
                        .width(150.dp)
                        .height(230.dp)
                        .clickable { onAttractionSelected(attraction.id) },
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("https://api2.kojaro.com/media/2024-6-94452122-7d0c-4af2-91c7-f3e96febae2a-67c46117c1067c5ba768c24e")
                            .crossfade(true)
                            .build(),
                        placeholder = debugPlaceholder(debugPreview = R.drawable.placeholder_vertical),
                        contentDescription = "contentDescription",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(MaterialTheme.shapes.medium),
                        contentScale = ContentScale.Crop,
                    )

                    Image(
                        painter = painterResource(R.drawable.icon_leaf_24),
                        modifier = Modifier
                            .offset(y = (-24).dp)
                            .border(
                                width = 1.dp,
                                color = Theme.colors.materialTheme.background,
                                shape = CircleShape,
                            )
                            .background(HanaGreen8, CircleShape)
                            .padding(12.dp)
                            .clip(CircleShape)
                            .align(Alignment.TopCenter),
                        contentDescription = "Category Tag",
                    )
                    Text(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .offset(y = 32.dp),
                        text = attraction.location.name,
                        style = MaterialTheme.typography.titleSmall,
                    )
                }


            }
        }

    }
}

@Preview("default", showBackground = true, showSystemUi = true)
@Composable
private fun PreviewHomeScreen() {
    AppTheme {

        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            SharedTransitionLayout {
                BackgroundElement(modifier = Modifier.fillMaxSize()) {
                    HomeScreenContent(
                        modifier = Modifier.systemBarsPadding(),
                        sharedTransitionScope = this@SharedTransitionLayout,
                        paddingValues = PaddingValues(0.dp),
                        categories = listOf(),
                        topAttractions = listOf(),
                        forYouTours = listOf(),
                        recommendedThisWeek = listOf(),
                        onTourSelected = {},
                        onCategorySelected = {},
                        onAttractionSelected = {},
                    )
                }
            }
        }
    }
}