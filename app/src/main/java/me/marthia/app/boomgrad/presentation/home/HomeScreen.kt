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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import me.marthia.app.boomgrad.R
import me.marthia.app.boomgrad.presentation.FilterSharedElementKey
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
import me.marthia.app.boomgrad.presentation.theme.AppTheme
import me.marthia.app.boomgrad.presentation.theme.BaseTheme
import me.marthia.app.boomgrad.presentation.theme.HanaGreen8
import me.marthia.app.boomgrad.presentation.util.debugPlaceholder

@Composable
fun HomeScreen(onTourSelected: (Long) -> Unit) {



    AppScaffold() {
        SharedTransitionLayout {
            Box {
                HomeScreenContent(
                    modifier = Modifier,
                    paddingValues = it,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    onTourSelected = onTourSelected
                )


            }
        }
    }
}


@Composable
private fun HomeScreenContent(
    modifier: Modifier = Modifier,
    sharedTransitionScope: SharedTransitionScope,
    paddingValues: PaddingValues,
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
        StorySection(modifier = Modifier.fillMaxWidth())
        Recommended()
        ForYou(onTourSelected = onTourSelected)
        TopDestinations()

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
                                        text = "جستجوی تور، جاذبه، راهنما",
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
fun StorySection(modifier: Modifier = Modifier) {
    val items = listOf(
        "زیارتی" to "https://picsum.photos/200",
        "تفریحی" to "https://picsum.photos/203",
        "تاریخی" to "https://picsum.photos/205",
        "طبیعت‌گردی" to "https://picsum.photos/210",
        "سلامت" to "https://picsum.photos/220",
    )
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) {
        items(items) { titleImagePair ->
            Story(title = titleImagePair.first, image = titleImagePair.second)
        }
    }
}

@Composable
fun Recommended() {
    val pagerState = rememberPagerState(
        initialPage = 0, initialPageOffsetFraction = 0f,
        pageCount = {
            1
        },
    )
    Column(Modifier.padding(16.dp)) {
        IconText(
            modifier = Modifier.padding(bottom = 16.dp),
            text = { Text("پیشنهادی این هفته", style = MaterialTheme.typography.titleMedium) },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.icon_featured_24),
                    tint = Color.Unspecified,
                    contentDescription = "Featured",
                )
            },
        )
        HorizontalPager(state = pagerState) {
            JetsnackCard(contentColor = BaseTheme.colors.textSecondary) {

                Column(modifier = Modifier.padding(16.dp)) {
                    RecommendedImages(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                    )

                    JetHorizontalDivider(modifier = Modifier.padding(top = 60.dp, bottom = 24.dp))


                    Text("کوه‌گشت یک روزه کلکچال", style = MaterialTheme.typography.titleMedium)
                    Text(
                        " املت، چای، کمپ در برف و مدیتیشن املت، چای، کمپ در برف و مدیتیشن",
                        style = MaterialTheme.typography.bodyMedium
                    )


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {

                        JetsnackSurface(
                            shape = MaterialTheme.shapes.extraLarge,
                            color = BaseTheme.colors.uiContainer,
                        ) {
                            IconText(
                                modifier = Modifier.padding(8.dp),
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(R.drawable.icon_leaf_24),
                                        tint = Color.Unspecified,
                                        contentDescription = "featured",
                                    )
                                },
                                text = {
                                    Text(text = "طبیعت", color = BaseTheme.colors.brand)
                                },
                            )
                        }

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
fun RecommendedImages(modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://picsum.photos/560/800")
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
                .data("https://picsum.photos/500/800")
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
                .data("https://picsum.photos/550/800")
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
fun ForYou(onTourSelected: (Long) -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        IconText(
            modifier = Modifier.padding(bottom = 16.dp),
            text = { Text("برای شما", style = MaterialTheme.typography.titleMedium) },
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
            items(3) {

                Box(
                    modifier = Modifier
                        .width(280.dp)
                        .height(370.dp)
                        .clickable(enabled = true, onClick = { onTourSelected(-1) }),

                    ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("https://picsum.photos/500/800")
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
                            "کوه‌گشت یک روزه کلکچال",
                            style = MaterialTheme.typography.titleMedium,
                            color = BaseTheme.colors.textInteractive,
                        )
                        JetHorizontalDivider(
                            modifier = Modifier.padding(vertical = 8.dp),
                            color = BaseTheme.colors.uiBorder
                        )
                        IconText(
                            text = {
                                Text("تهران، تهران", color = BaseTheme.colors.textInteractive)
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
fun TopDestinations() {

    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        IconText(
            modifier = Modifier.padding(start = 16.dp, bottom = 16.dp),
            text = { Text("جاذبه‌های برتر", style = MaterialTheme.typography.titleMedium) },
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
            items(3) {

                Box(
                    modifier = Modifier
                        .width(150.dp)
                        .height(230.dp),
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("https://picsum.photos/500/800")
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
                        text = "کاخ سرهنگ",
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
                        onTourSelected = {})
                }
            }
        }
    }
}