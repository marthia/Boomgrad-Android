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
import me.marthia.app.boomgrad.domain.model.Tour
import me.marthia.app.boomgrad.presentation.FilterSharedElementKey
import me.marthia.app.boomgrad.presentation.category.CategoryTag
import me.marthia.app.boomgrad.presentation.common.ErrorScreen
import me.marthia.app.boomgrad.presentation.common.LoadingScreen
import me.marthia.app.boomgrad.presentation.components.AppScaffold
import me.marthia.app.boomgrad.presentation.components.IconText
import me.marthia.app.boomgrad.presentation.components.JetHorizontalDivider
import me.marthia.app.boomgrad.presentation.components.JetSnackBackground
import me.marthia.app.boomgrad.presentation.components.JetVerticalDivider
import me.marthia.app.boomgrad.presentation.components.JetsnackButton
import me.marthia.app.boomgrad.presentation.components.JetsnackCard
import me.marthia.app.boomgrad.presentation.components.JetsnackSearch
import me.marthia.app.boomgrad.presentation.components.JetsnackSurface
import me.marthia.app.boomgrad.presentation.components.PlainButton
import me.marthia.app.boomgrad.presentation.home.model.Filter
import me.marthia.app.boomgrad.presentation.home.model.HomeUiState
import me.marthia.app.boomgrad.presentation.theme.AppTheme
import me.marthia.app.boomgrad.presentation.theme.BaseTheme
import me.marthia.app.boomgrad.presentation.theme.HanaGreen8
import me.marthia.app.boomgrad.presentation.util.ViewState
import me.marthia.app.boomgrad.presentation.util.debugPlaceholder
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(onTourSelected: (Long) -> Unit) {

    val viewModel: HomeViewModel = koinViewModel()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState) {
        is ViewState.Loading -> LoadingScreen()
        is ViewState.Error -> ErrorScreen()
        is ViewState.Success -> {

            AppScaffold() {
                SharedTransitionLayout {
                    Box {
                        HomeScreenContent(
                            modifier = Modifier,
                            paddingValues = it,
                            sharedTransitionScope = this@SharedTransitionLayout,
                            categories = (uiState as ViewState.Success<HomeUiState>).value.categories,
                            topAttractions = (uiState as ViewState.Success<HomeUiState>).value.topAttractions,
                            forYouTours = (uiState as ViewState.Success<HomeUiState>).value.forYouTours,
                            recommendedThisWeek = (uiState as ViewState.Success<HomeUiState>).value.weekRecommended,
                            onTourSelected = onTourSelected,
                        )


                    }
                }
            }
        }

        else -> {}
    }
}


@Composable
private fun HomeScreenContent(
    modifier: Modifier = Modifier,
    sharedTransitionScope: SharedTransitionScope,
    paddingValues: PaddingValues,
    categories: List<AttractionCategory>,
    topAttractions: List<Attraction>,
    forYouTours: List<Tour>,
    recommendedThisWeek: List<Tour>,
    onTourSelected: (Long) -> Unit
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
            cityList = listOf(),
            sharedTransitionScope = sharedTransitionScope,
        )

        Spacer(Modifier.height(24.dp))
        StorySection(modifier = Modifier.fillMaxWidth(), categories = categories)
        Recommended(list = recommendedThisWeek)
        ForYou(list = forYouTours, onTourSelected = onTourSelected)
        TopDestinations(list = topAttractions)

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
    cityList: List<Filter>,
    showCitySelection: () -> Unit,
    filtersVisible: Boolean,
    sharedTransitionScope: SharedTransitionScope
) {
    val expanded = remember { mutableStateOf(false) }
    val searchQuery = remember { mutableStateOf("") }
    JetsnackSearch(
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
                                        tint = BaseTheme.colors.textHelp,
                                    )
                                },
                                text = {
                                    Text(
                                        text = stringResource(R.string.home_screen_search_help_label),
                                        style = MaterialTheme.typography.labelSmall,
                                        color = BaseTheme.colors.textHelp,
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
fun StorySection(modifier: Modifier = Modifier, categories: List<AttractionCategory>) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) {
        items(categories) { category ->
            Story(title = category.name, image = category.image)
        }
    }
}

@Composable
fun Recommended(list: List<Tour>) {
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
            JetsnackCard(contentColor = BaseTheme.colors.textSecondary) {

                Column(modifier = Modifier.padding(16.dp)) {
                    RecommendedImages(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        images = list[index].images
                    )

                    JetHorizontalDivider(modifier = Modifier.padding(top = 60.dp, bottom = 24.dp))


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

                        JetsnackButton(
                            shape = CircleShape,
                            contentPadding = PaddingValues(8.dp),
                            onClick = { },
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
                .data(images[2])
                .crossfade(true)
                .build(),
            placeholder = debugPlaceholder(debugPreview = R.drawable.placeholder_vertical),
            contentDescription = "contentDescription",
            modifier = Modifier
                .offset(x = 80.dp, y = 24.dp)
                .rotate(-6f)
                .clip(MaterialTheme.shapes.large),
            contentScale = ContentScale.FillHeight,
        )

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(images[1])
                .crossfade(true)
                .build(),
            placeholder = debugPlaceholder(debugPreview = R.drawable.placeholder_vertical),
            contentDescription = "contentDescription",
            modifier = Modifier
                .offset(x = -68.dp, y = 16.dp)
                .rotate(13f)
                .clip(MaterialTheme.shapes.large),
            contentScale = ContentScale.FillHeight,
        )

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(images[0])
                .crossfade(true)
                .build(),
            placeholder = debugPlaceholder(debugPreview = R.drawable.placeholder_vertical),
            contentDescription = "contentDescription",
            modifier = Modifier
                .offset(y = 32.dp)
                .clip(MaterialTheme.shapes.large),
            contentScale = ContentScale.FillWidth,
        )
    }
}

@Composable
fun ForYou(list: List<Tour>, onTourSelected: (Long) -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        IconText(
            modifier = Modifier.padding(bottom = 16.dp),
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
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(list) { item ->

                Box(
                    modifier = Modifier
                        .width(280.dp)
                        .height(370.dp)
                        .clickable(enabled = true, onClick = { onTourSelected(-1) }),

                    ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(item.images.first())
                            .crossfade(true)
                            .build(),
                        placeholder = debugPlaceholder(debugPreview = R.drawable.placeholder_vertical),
                        contentDescription = "contentDescription",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(MaterialTheme.shapes.medium),
                        contentScale = ContentScale.FillWidth,
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
                            color = BaseTheme.colors.textInteractive,
                        )
                        JetHorizontalDivider(
                            modifier = Modifier.padding(vertical = 8.dp),
                            color = BaseTheme.colors.uiBorder
                        )
                        IconText(
                            text = {
                                Text(
                                    text = item.city.name,
                                    color = BaseTheme.colors.textInteractive
                                )
                            },
                            leadingIcon = {

                                JetsnackSurface(
                                    shape = CircleShape,
                                    color = BaseTheme.colors.uiBackground,
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
fun TopDestinations(list: List<Attraction>) {

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
                        .height(230.dp),
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(attraction.imageUrl)
                            .crossfade(true)
                            .build(),
                        placeholder = debugPlaceholder(debugPreview = R.drawable.placeholder_vertical),
                        contentDescription = "contentDescription",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(MaterialTheme.shapes.medium),
                        contentScale = ContentScale.FillWidth,
                    )

                    Image(
                        painter = painterResource(R.drawable.icon_leaf_24),
                        modifier = Modifier
                            .offset(y = (-24).dp)
                            .border(
                                width = 1.dp,
                                color = BaseTheme.colors.uiBackground,
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
                JetSnackBackground(modifier = Modifier.fillMaxSize()) {
                    HomeScreenContent(
                        modifier = Modifier.systemBarsPadding(),
                        sharedTransitionScope = this@SharedTransitionLayout,
                        paddingValues = PaddingValues(0.dp),
                        categories = listOf(),
                        topAttractions = listOf(),
                        forYouTours = listOf(),
                        recommendedThisWeek = listOf(),
                        onTourSelected = {})
                }
            }
        }
    }
}