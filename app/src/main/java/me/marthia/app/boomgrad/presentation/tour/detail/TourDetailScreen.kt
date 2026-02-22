@file:OptIn(ExperimentalMaterial3Api::class)

package me.marthia.app.boomgrad.presentation.tour.detail

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.rounded.FiberManualRecord
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import me.marthia.app.boomgrad.R
import me.marthia.app.boomgrad.domain.model.AttractionCategory
import me.marthia.app.boomgrad.domain.model.City
import me.marthia.app.boomgrad.domain.model.Demographic
import me.marthia.app.boomgrad.domain.model.Guide
import me.marthia.app.boomgrad.domain.model.ItineraryStop
import me.marthia.app.boomgrad.domain.model.TourDetail
import me.marthia.app.boomgrad.domain.model.TourLevel
import me.marthia.app.boomgrad.domain.model.TourStatus
import me.marthia.app.boomgrad.presentation.cart.CartViewModel
import me.marthia.app.boomgrad.presentation.common.ErrorScreen
import me.marthia.app.boomgrad.presentation.common.LoadingScreen
import me.marthia.app.boomgrad.presentation.components.BackgroundElement
import me.marthia.app.boomgrad.presentation.components.CardElement
import me.marthia.app.boomgrad.presentation.components.HorizontalDividerElement
import me.marthia.app.boomgrad.presentation.components.IconText
import me.marthia.app.boomgrad.presentation.components.PlainButton
import me.marthia.app.boomgrad.presentation.components.QuantitySelector
import me.marthia.app.boomgrad.presentation.components.ScaffoldElement
import me.marthia.app.boomgrad.presentation.components.SurfaceElement
import me.marthia.app.boomgrad.presentation.components.TopBar
import me.marthia.app.boomgrad.presentation.home.Itinerary
import me.marthia.app.boomgrad.presentation.theme.AppTheme
import me.marthia.app.boomgrad.presentation.theme.Theme
import me.marthia.app.boomgrad.presentation.tour.model.TourDetailUi
import me.marthia.app.boomgrad.presentation.tour.model.toUi
import me.marthia.app.boomgrad.presentation.util.ViewState
import me.marthia.app.boomgrad.presentation.util.debugPlaceholder
import me.marthia.app.boomgrad.presentation.util.formatPrice
import org.koin.androidx.compose.koinViewModel

@Composable
fun TourDetailScreen(
    tourId: Long,
    cartViewModel: CartViewModel = koinViewModel(),
    viewModel: TourDetailViewModel = koinViewModel(),
    upPress: () -> Unit,
    onNavigateToGuideInfo: (Long) -> Unit,
    navigateToChat: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.fetchDetail(context)
    }


    ScaffoldElement(topBar = {
        TopBar(
            title = {
                Text(stringResource(R.string.title_tour_detail_screen))
            },
            onBackClick = upPress
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
                is ViewState.Error -> ErrorScreen(onBack = upPress)
                is ViewState.Success -> {
                    TourDetailContent(
                        tour = state.value,
                        onNavigateToGuideInfo = onNavigateToGuideInfo,
                        addToCart = {

                        },
                        directBook = {},
                        chatWithGuide = { navigateToChat() },
                    )
                }

                else -> {}
            }
        }
    }

}

@Composable
fun TourDetailContent(
    modifier: Modifier = Modifier,
    tour: TourDetailUi,
    onNavigateToGuideInfo: (Long) -> Unit,
    addToCart: () -> Unit,
    directBook: () -> Unit,
    chatWithGuide: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        TourImages(images = tour.images)
        TourGist(
            modifier = Modifier.padding(16.dp),
            title = tour.title,
            reviewCount = tour.reviews.size,
            rate = tour.rate,

            )
        TourGuideInfo(
            modifier = Modifier.padding(16.dp),
            guideInfo = tour.guide,
            onNavigateToGuideInfo = onNavigateToGuideInfo
        )
        Description(
            modifier = Modifier.padding(16.dp),
            description = tour.description,
        )
        Highlights(
            modifier = Modifier.padding(16.dp),
            highlights = tour.highlights,
        )
        Specs(
            modifier = Modifier.padding(16.dp),
            duration = tour.duration,
            level = tour.level,
            date = tour.dueDate,
            startTime = tour.startTime,
            targetDemographic = tour.demographic
        )
        Prerequisites(
            modifier = Modifier.padding(16.dp),
            prerequisites = tour.requiredItems
        )
        ItinerarySection(
            modifier = Modifier.padding(16.dp),
            itinerary = tour.itinerary
        )

        HorizontalDividerElement(Modifier.padding(16.dp))

        Price(
            price = tour.price
        )

        AddToCard(
            modifier = Modifier.padding(16.dp),
            addToCart = addToCart,
            directBook = directBook,
            chatWithGuide = chatWithGuide)
    }

}

@Composable
private fun TourImages(modifier: Modifier = Modifier, images: List<String>) {

    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp),
    ) {

        items(images) { imageLink ->
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(R.drawable.ali_qapu)
                    .crossfade(true)
                    .build(),
                placeholder = debugPlaceholder(debugPreview = R.drawable.placeholder),
                contentDescription = "contentDescription",
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .clip(MaterialTheme.shapes.large),
                contentScale = ContentScale.FillBounds,
            )

        }
    }
}

@Composable
fun TourGist(modifier: Modifier = Modifier, title: String, reviewCount: Int, rate: Float) {

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(title, style = MaterialTheme.typography.titleMedium)
        IconText(leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.icon_star_20),
                contentDescription = "score",
                tint = Color.Unspecified
            )
        }, text = {
            Text(text = "$rate ", style = MaterialTheme.typography.bodySmall)
            Text(
                stringResource(R.string.tour_detail_review_count, reviewCount),
                style = MaterialTheme.typography.bodySmall,
                color = Theme.colors.textHelp
            )
        })
    }
}

@Composable
fun TourGuideInfo(
    modifier: Modifier = Modifier,
    guideInfo: Guide,
    onNavigateToGuideInfo: (Long) -> Unit
) {
    CardElement(modifier = modifier, elevation = 0.dp) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                AsyncImage(
                    R.drawable.face,
                    contentDescription = "profile",
                    placeholder = debugPlaceholder(debugPreview = R.drawable.placeholder),
                    modifier = Modifier
                        .size(48.dp)
                        .clickable(onClick = {
                            onNavigateToGuideInfo(guideInfo.id)
                        })
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                )
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        stringResource(R.string.tour_detail_guide_label),
                        style = MaterialTheme.typography.labelSmall,
                        color = Theme.colors.textHelp
                    )
                    IconText(text = {
                        Text(
                            text = guideInfo.fullName,
                            style = MaterialTheme.typography.titleSmall,

                            )
                    }, trailingIcon = {


                        Icon(
                            painter = painterResource(R.drawable.ic_verify_22),
                            contentDescription = stringResource(R.string.cd_verified_guide)
                        )
                    })
                }
            }
            Column(horizontalAlignment = Alignment.End) {
                IconText(
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.icon_star_20),
                            contentDescription = "score",
                            tint = Color.Unspecified
                        )
                    },
                    text = {
                        Text(
                            text = "${guideInfo.averageRating}",
                            style = MaterialTheme.typography.labelLarge,

                            )
                    },
                )


                Text(
                    text = stringResource(R.string.label_tour_total_tours, guideInfo.totalTours),
                    style = MaterialTheme.typography.labelMedium,
                    color = Theme.colors.textHelp
                )
            }
        }
    }
}

@Composable
fun Description(modifier: Modifier = Modifier, description: String) {
    Column(modifier = modifier) {
        Text(
            stringResource(R.string.tour_detail_description_label),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Justify,
            color = Theme.colors.textHelp,
        )
    }
}

@Composable
fun Highlights(modifier: Modifier = Modifier, highlights: List<String>) {
    Column(modifier = modifier) {


        Text(
            stringResource(R.string.tour_detail_highlights_label),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.height(8.dp))

        highlights.forEach { item ->
            IconText(
                modifier = Modifier.padding(start = 8.dp),
                leadingIcon = {
                    Icon(
                        modifier = Modifier.size(8.dp),
                        imageVector = Icons.Rounded.FiberManualRecord,
                        contentDescription = "highlight",
                    )
                },
                text = {
                    Text(
                        item,
                        style = MaterialTheme.typography.bodySmall,
                        color = Theme.colors.textHelp
                    )
                },
            )
        }
    }
}

@Composable
fun TourSpecItem(modifier: Modifier = Modifier, label: String, labelIcon: Int, value: String) {
    SurfaceElement(
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
        border = BorderStroke(width = 2.dp, color = Theme.colors.materialTheme.outline),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            IconText(
                leadingIcon = {
                    Icon(
                        painter = painterResource(labelIcon),
                        contentDescription = "Duration",
                        tint = Theme.colors.textHelp
                    )
                },
                text = {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelSmall,
                        color = Theme.colors.textHelp
                    )
                },
            )
            Text(
                text = value,
                style = MaterialTheme.typography.labelLarge,
                color = Theme.colors.materialTheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun Specs(
    modifier: Modifier = Modifier,
    duration: Int,
    level: String,
    date: String,
    startTime: String,
    targetDemographic: String
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            TourSpecItem(
                modifier = Modifier.weight(1f),
                label = stringResource(R.string.tour_detail_duration_label),
                labelIcon = R.drawable.icon_duration_16,
                value = stringResource(R.string.tour_detail_duration_value, duration)
            )

            TourSpecItem(
                modifier = Modifier.weight(1f),
                label = stringResource(R.string.tour_detail_due_date),
                labelIcon = R.drawable.icon_due_date_16,
                value = date,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            TourSpecItem(
                modifier = Modifier.weight(1f),
                label = stringResource(R.string.tour_detail_level_label),
                labelIcon = R.drawable.icon_level_16,
                value = level
            )

            TourSpecItem(
                modifier = Modifier.weight(1f),
                label = stringResource(R.string.tour_detail_start_time_label),
                labelIcon = R.drawable.icon_start_time_16,
                value = startTime
            )

            TourSpecItem(
                modifier = Modifier.weight(1f),
                label = stringResource(R.string.tour_detail_target_demographic_label),
                labelIcon = R.drawable.icon_demographic_16,
                value = targetDemographic
            )
        }
    }

}

@Composable
fun Prerequisites(modifier: Modifier = Modifier, prerequisites: List<String>) {


    Column(modifier = modifier) {


        Text(
            stringResource(R.string.tour_detail_title_prerequisites),
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(Modifier.height(8.dp))

        prerequisites.forEach { item ->
            IconText(
                modifier = Modifier.padding(start = 8.dp),
                leadingIcon = {
                    Icon(
                        modifier = Modifier.size(8.dp),
                        imageVector = Icons.Rounded.FiberManualRecord,
                        tint = Theme.colors.textHelp,
                        contentDescription = "highlight",
                    )
                },
                text = {
                    Text(
                        text = item,
                        style = MaterialTheme.typography.bodySmall,
                        color = Theme.colors.textHelp
                    )
                },
            )
        }
    }
}

@Composable
fun ItinerarySection(modifier: Modifier = Modifier, itinerary: List<ItineraryStop>) {

    val expanded = remember { mutableStateOf(false) }
    Column(modifier = modifier) {

        SurfaceElement(shape = MaterialTheme.shapes.medium) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.tour_detail_itinerary_label),
                    style = MaterialTheme.typography.titleMedium,
                )

                IconButton(
                    onClick = { expanded.value = expanded.value.not() },
                    modifier = Modifier.size(32.dp),
                ) {
                    Icon(
                        imageVector = if (expanded.value) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = stringResource(R.string.cd_move_stop_up),
                    )
                }
            }
        }

        AnimatedVisibility(expanded.value) {

            Spacer(Modifier.height(16.dp))

            Itinerary(
                modifier = Modifier,
                stops = itinerary,
            )
        }
    }

}

@Composable
fun Price(price: Double) {

    val countState = remember { mutableIntStateOf(1) }
    val locale = LocalConfiguration.current.locales[0]

    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                stringResource(R.string.tour_detail_price_per_person),
                style = MaterialTheme.typography.bodyMedium,
                color = Theme.colors.textHelp
            )
            Text(
                text = formatPrice(amount = price, currencyCode = "IRR", locale = locale),
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Spacer(Modifier.height(8.dp))


        SurfaceElement(
            shape = MaterialTheme.shapes.medium,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp, horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    stringResource(R.string.tour_detail_max_people_label),
                    style = MaterialTheme.typography.bodyLarge
                )
                QuantitySelector(
                    modifier = Modifier,
                    count = countState,
                )
            }
        }

        SurfaceElement(
            shape = MaterialTheme.shapes.medium,
            color = Theme.colors.materialTheme.surfaceContainerHighest,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.tour_detail_total_price),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Theme.colors.textHelp
                )
                Text(
                    text = formatPrice(amount = price, currencyCode = "IRR", locale = locale),
                    style = MaterialTheme.typography.titleLarge,
                    color = Theme.colors.materialTheme.primary
                )

            }
        }
    }
}


@Composable
fun AddToCard(
    modifier: Modifier = Modifier,
    addToCart: () -> Unit,
    directBook: () -> Unit,
    chatWithGuide: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Theme.colors.materialTheme.secondary),
            onClick = addToCart
        ) {
            Text("افزودن به سبد خرید")
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = directBook
        ) {
            Text("رزرو سریع")
        }

        PlainButton(onClick = chatWithGuide, contentColor = Theme.colors.materialTheme.primary) {
            IconText(leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_chat_outlined_24),
                    contentDescription = null
                )
            }, text = {
                Text("چت با راهنما",)
            })
        }
    }
}


@Preview("default", showBackground = true, showSystemUi = true, locale = "fa")
@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "fa")
@Preview("large font", fontScale = 2f, locale = "fa")
@Composable
private fun PreviewTourDetail() {
    AppTheme {


        BackgroundElement(modifier = Modifier.fillMaxSize()) {
            TourDetailContent(
                modifier = Modifier.systemBarsPadding(),
                onNavigateToGuideInfo = {},
                chatWithGuide = {},
                addToCart = {},
                directBook = {},
                tour = TourDetail(
                    id = -1,
                    title = "میدان نقش جهان",
                    description = "با این تور یک روزه، سفری به قلب تاریخ و هنر اصفهان خواهید داشت. میدان نقش جهان، یکی از بزرگترین و زیباترین میادین جهان، میزبان شما خواهد بود. در این گشت، از شاهکارهای معماری دوران صفوی مانند مسجد شیخ لطف‌الله، مسجد امام و کاخ عالی‌قاپو دیدن خواهید کرد. همچنین، فرصت گشت و گذار در بازار قیصریه و خرید صنایع دستی اصیل اصفهان را خواهید داشت. این تور تجربه‌ای فراموش‌نشدنی از فرهنگ غنی ایران را برای شما به ارمغان می‌آورد.",
                    guide = Guide(
                        id = 1,
                        bio = "امین عدیلی دانش‌آموخته رشته مهندسی مواد در دانشگاه پلی‌تکنیک لندن اهل کرمانشاه یکی از برجسته‌ترین راهنماهای گردشگری با تخصص تاریخ هخامنشیان است.",
                        fullName = "امین عدیلی",
                        userId = 10,
                        verified = true,
                        totalTours = 46,
                        averageRating = 4.1f,
                    ),
                    highlights = listOf(
                        "شناخت تاریخ",
                        "نوشیدنی مخصوص",
                        "گز اصفهان",
                        "مسجد و محراب",
                        "چاه حاج میرزا",
                    ),
                    duration = 108,
                    price = 1235.1,
                    category = AttractionCategory(
                        id = 0,
                        name = "تاریخی",
                        description = "",
                        image = ""
                    ),
                    maxPeople = 7,
                    status = TourStatus.PENDING,
                    rate = 4.8f,
                    reviews = listOf(),
                    images = listOf(),
                    requiredItems = listOf(
                        "کفش کوهنوردی",
                        "کلاه",
                        "عینک آفتابی",
                        "تنقلات",
                        "زیرانداز تک نفره",
                        "پول نقد",
                    ),
                    level = TourLevel.INTERMEDIATE,
                    dueDate = "۱۶ بهمن",
                    startTime = "۸ صبح",
                    demographic = Demographic.ALL_AGES,
                    itinerary =
                        listOf(
                            ItineraryStop(
                                id = 1634,
                                stopOrder = 1,
                                title = "میدان نقش جهان",
                                description = "شنبه ۲۴ دی ساعت ۱۶",
                                date = "چهارشنبه ۱۸ دی ۱۴۰۴",
                            ),
                            ItineraryStop(
                                id = 166,
                                stopOrder = 2,
                                title = "سی و سه پل",
                                description = "ساعت ۱۷",
                                date = "چهارشنبه ۱۸ دی ۱۴۰۴",
                            ),
                            ItineraryStop(
                                id = 1555,
                                stopOrder = 3,
                                title = "کلیسای وانک",
                                description = "ساعت ۱۸",
                                date = "چهارشنبه ۱۸ دی ۱۴۰۴",
                            ),
                            ItineraryStop(
                                id = 144,
                                stopOrder = 4,
                                title = "کلیسای مریم مقدس",
                                description = "ساعت ۱۹",
                                date = "چهارشنبه ۱۸ دی ۱۴۰۴",
                            ),
                            ItineraryStop(
                                id = 122,
                                stopOrder = 5,
                                title = "میدان جلفا",
                                description = "شنبه ۲۴ دی ساعت ۲۰",
                                date = "چهارشنبه ۱۸ دی ۱۴۰۴",
                            ),
                        ),
                    city = City(
                        id = 1,
                        name = "اصفهان",
                        latitude = 52.4888,
                        countyId = 1,
                        imageUrl = "",
                        longitude = 32.4564,
                        countyName = "اصفهان",
                        provinceId = 1,
                        description = "",
                        provinceName = "اصفهان",
                    ),
                ).toUi(LocalContext.current)
            )
        }
    }
}
