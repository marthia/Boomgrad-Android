package me.marthia.app.boomgrad.presentation.attraction.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import me.marthia.app.boomgrad.R
import me.marthia.app.boomgrad.domain.model.Attraction
import me.marthia.app.boomgrad.domain.model.AttractionCategory
import me.marthia.app.boomgrad.domain.model.AttractionContactInfo
import me.marthia.app.boomgrad.domain.model.AttractionImage
import me.marthia.app.boomgrad.domain.model.AttractionOpeningHours
import me.marthia.app.boomgrad.domain.model.City
import me.marthia.app.boomgrad.domain.model.Guide
import me.marthia.app.boomgrad.domain.model.ItineraryStop
import me.marthia.app.boomgrad.domain.model.Location
import me.marthia.app.boomgrad.domain.model.LocationType
import me.marthia.app.boomgrad.domain.model.Review
import me.marthia.app.boomgrad.domain.model.Tour
import me.marthia.app.boomgrad.domain.model.TourStatus
import me.marthia.app.boomgrad.domain.model.User
import me.marthia.app.boomgrad.presentation.attraction.components.ComposeNewCommentBottomSheet
import me.marthia.app.boomgrad.presentation.category.CategoryTag
import me.marthia.app.boomgrad.presentation.common.ErrorScreen
import me.marthia.app.boomgrad.presentation.common.LoadingScreen
import me.marthia.app.boomgrad.presentation.components.AppScaffold
import me.marthia.app.boomgrad.presentation.components.BackgroundElement
import me.marthia.app.boomgrad.presentation.components.ButtonElement
import me.marthia.app.boomgrad.presentation.components.CardElement
import me.marthia.app.boomgrad.presentation.components.IconText
import me.marthia.app.boomgrad.presentation.components.JetHorizontalDivider
import me.marthia.app.boomgrad.presentation.components.SurfaceElement
import me.marthia.app.boomgrad.presentation.components.TopBar
import me.marthia.app.boomgrad.presentation.profile.component.dashedBorder
import me.marthia.app.boomgrad.presentation.theme.AppTheme
import me.marthia.app.boomgrad.presentation.theme.Theme
import me.marthia.app.boomgrad.presentation.util.RightToLeftLayout
import me.marthia.app.boomgrad.presentation.util.ViewState
import me.marthia.app.boomgrad.presentation.util.debugPlaceholder
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttractionDetailScreen(
    attractionId: String,
    onBackClick: () -> Unit,
    viewModel: AttractionDetailViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AppScaffold(
        topBar = {
            TopBar(
                title = { },
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
                is ViewState.Success -> {
                    AttractionDetailContent(state.value)
                }

                is ViewState.Error -> {
                    ErrorScreen(
                        onRetry = { viewModel.retry() }
                    )
                }

                else -> {}
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AttractionImages(modifier: Modifier = Modifier, images: List<AttractionImage>) {
    val pagerState = rememberPagerState(
        pageCount = { images.size }
    )

    HorizontalPager(
        state = pagerState,
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp),
        contentPadding = PaddingValues(
            start = 8.dp,
            end = 24.dp // 8dp padding + 16dp peek
        ),
        pageSpacing = 8.dp,
        pageSize = PageSize.Fill,
    ) { page ->
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(images[page].imageUrl)
                .crossfade(true)
                .build(),
            placeholder = debugPlaceholder(debugPreview = R.drawable.placeholder),
            contentDescription = "Attraction image ${page + 1}",
            modifier = Modifier
                .fillMaxSize()
                .clip(MaterialTheme.shapes.large),
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
fun AttractionGist(
    modifier: Modifier = Modifier,
    title: String,
    reviewCount: Int,
    rate: Float,
    category: String,
    city: String
) {

    Column(
        modifier = modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(title, style = MaterialTheme.typography.titleLarge)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.icon_star_20),
                    contentDescription = "score",
                    tint = Color.Unspecified
                )
                Text(text = "$rate ", style = MaterialTheme.typography.bodySmall)

                Text(
                    stringResource(R.string.tour_detail_review_count, reviewCount),
                    style = MaterialTheme.typography.bodySmall,
                    color = Theme.colors.textHelp
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {

            IconText(
                text = {
                    Text(
                        text = city,
                        style = MaterialTheme.typography.bodySmall
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.icon_location_16),
                        contentDescription = "Location",
                    )
                },
            )

            CategoryTag(title = category, icon = R.drawable.icon_leaf_16)
        }

    }
}


@Composable
private fun AttractionDetailContent(attraction: Attraction) {
    var showBottomSheet by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {

        AttractionImages(
            modifier = Modifier.padding(vertical = 16.dp),
            images = attraction.images
        )

        AttractionGist(
            title = attraction.location.name,
            reviewCount = attraction.reviewCount,
            rate = attraction.rating,
            category = attraction.category.name,
            city = "${attraction.location.province} , ${attraction.location.city}"
        )

        Text(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
            text = attraction.location.description,
            style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 24.sp),
            textAlign = TextAlign.Justify,
        )

        AttractionSpecs(
            modifier = Modifier.padding(16.dp),
            contactInfo = attraction.contactInfo,
            address = attraction.location.address
        )

        WorkingTimeSection(
            modifier = Modifier.padding(16.dp),
            workingTimes = attraction.openingHours
        )

        AvailableTours(modifier = Modifier.fillMaxWidth(), tours = attraction.relatedTours)

        Reviews(
            modifier = Modifier.fillMaxWidth(),
            reviews = attraction.reviews,
            reviewCount = attraction.reviewCount,
            onNewComment = {
                showBottomSheet = true
            }
        )

        if (showBottomSheet) {
            ComposeNewCommentBottomSheet(
                onDismiss = { showBottomSheet = false }
            )
        }
    }
}

@Composable
fun AttractionSpecs(
    modifier: Modifier = Modifier,
    contactInfo: AttractionContactInfo,
    address: String
) {

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AttractionSpecItem(
                modifier = Modifier.weight(1f),
                label = stringResource(R.string.label_attraction_detail_phone),
                labelIcon = R.drawable.ic_phone_16,
                value = contactInfo.phone
            )

            AttractionSpecItem(
                modifier = Modifier.weight(1f),
                label = stringResource(R.string.label_attraction_detail_mail),
                labelIcon = R.drawable.ic_email_16,
                value = contactInfo.website
            )
        }

        AttractionSpecItem(
            label = stringResource(R.string.label_attraction_detail_address),
            labelIcon = R.drawable.icon_location_16,
            value = address
        )


    }

}


@Composable
fun AttractionSpecItem(
    modifier: Modifier = Modifier,
    label: String,
    labelIcon: Int,
    value: String
) {
    SurfaceElement(
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
        border = BorderStroke(width = 2.dp, color = Theme.colors.materialTheme.outline),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
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
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
fun WorkingTimeSection(modifier: Modifier = Modifier, workingTimes: List<AttractionOpeningHours>) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.label_attraction_detail_working_time),
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(Modifier.height(8.dp))

        SurfaceElement(
            shape = MaterialTheme.shapes.large,

            ) {


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(Modifier.height(8.dp))

                WorkingHoursNote(
                    label = "تعطیل به علت آلودگی هوا",
                    icon = painterResource(R.drawable.ic_info_filled_24),
                )

                Spacer(Modifier.height(20.dp))

                workingTimes.forEach {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = it.workingDate,
                            style = MaterialTheme.typography.bodySmall,
                            color = Theme.colors.textHelp,
                        )
                        Text(
                            text = it.workingTime,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                    Spacer(Modifier.height(12.dp))
                }
            }
        }
    }

}

@Composable
fun WorkingHoursNote(
    modifier: Modifier = Modifier,
    label: String,
    icon: Painter,
    textColor: Color = Theme.colors.materialTheme.onErrorContainer,
    iconContainerColor: Color = Theme.colors.materialTheme.onError,
    iconTint: Color = Theme.colors.materialTheme.error,
    containerColor: Color = Theme.colors.materialTheme.errorContainer,
    borderColor: Color = Theme.colors.materialTheme.error,
    shape: Shape = RoundedCornerShape(50)
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = containerColor, shape = shape)
            .dashedBorder(color = borderColor, shape = shape),

        verticalAlignment = Alignment.CenterVertically,

        ) {

        Surface(
            modifier = Modifier
                .padding(top = 2.dp, start = 2.dp, bottom = 2.dp)
                .size(48.dp),
            shape = CircleShape,
            color = iconContainerColor
        ) {
            Icon(
                modifier = Modifier.padding(12.dp),
                painter = icon,
                tint = iconTint,
                contentDescription = label
            )
        }

        Spacer(Modifier.width(8.dp))

        Text(text = label, color = textColor, style = MaterialTheme.typography.bodyLarge)

        Spacer(Modifier.weight(1f))
    }
}


@Composable
fun AvailableTours(modifier: Modifier = Modifier, tours: List<Tour>) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {

        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = stringResource(R.string.label_attraction_detail_related_tours),
            style = MaterialTheme.typography.titleMedium,
        )
        tours.forEach { item ->
            TourItem(modifier = Modifier.padding(horizontal = 16.dp), tour = item)
        }
    }
}

@Composable
fun TourItem(modifier: Modifier = Modifier, tour: Tour) {
    CardElement(modifier = modifier) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AsyncImage(
                    model = tour.images.first(),
                    contentDescription = "attraction image",
                    placeholder = debugPlaceholder(debugPreview = R.drawable.placeholder),
                    modifier = Modifier
                        .size(64.dp)
                        .clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop,
                )
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(tour.title, style = MaterialTheme.typography.titleSmall)
                    Text("${tour.price} نفر", style = MaterialTheme.typography.bodySmall)
                }
            }
            JetHorizontalDivider()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconText(leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.icon_due_date_16),
                        contentDescription = "Due Date"
                    )
                }, text = {
                    Text(
                        "${tour.dueDate} ${tour.duration}",
                        style = MaterialTheme.typography.bodySmall
                    )
                })
                ButtonElement(onClick = {}, shape = MaterialTheme.shapes.small) {
                    Text(stringResource(R.string.label_attraction_detail_button_book))
                }
            }
        }
    }
}

@Composable
fun Reviews(
    modifier: Modifier = Modifier,
    reviews: List<Review>,
    reviewCount: Int,
    onNewComment: () -> Unit
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = stringResource(R.string.label_attraction_detail_reviews),
                    style = MaterialTheme.typography.titleMedium,
                )
                Badge(
                    containerColor = Theme.colors.materialTheme.primaryContainer
                ) {
                    Text(
                        text = "$reviewCount",
                        modifier = Modifier.padding(2.dp),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }

            ButtonElement(onClick = onNewComment, shape = MaterialTheme.shapes.small) {
                Text(stringResource(R.string.label_attraction_detail_new_review))
            }
        }
        reviews.forEach { item ->
            ReviewItem(modifier = Modifier.padding(horizontal = 16.dp), review = item)
        }
    }
}

@Composable
fun ReviewItem(modifier: Modifier, review: Review) {
    CardElement(modifier = modifier) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AsyncImage(
                    model = review.user.image,
                    contentDescription = "attraction image",
                    placeholder = debugPlaceholder(debugPreview = R.drawable.placeholder),
                    modifier = Modifier
                        .size(64.dp)
                        .clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop,
                )
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

                    Text(review.user.name, style = MaterialTheme.typography.titleSmall)
//                    Text(review.title, style = MaterialTheme.typography.bodySmall)
                    Text(
                        review.content,
                        style = MaterialTheme.typography.bodySmall,
                        color = Theme.colors.textHelp
                    )

                }
                Spacer(Modifier.weight(1f))
                Text(
                    review.date,
                    style = MaterialTheme.typography.labelSmall,
                    color = Theme.colors.textHelp
                )
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true, locale = "fa")
@Composable
private fun PreviewAttraction() {
    AppTheme {
        RightToLeftLayout {
            BackgroundElement {
                AttractionDetailContent(
                    Attraction(
                        id = -1,
                        category = AttractionCategory(1, "تاریخی", "", ""),
                        images = listOf(
                            AttractionImage(1, "https://picsum.photos/1200"),
                            AttractionImage(2, "https://picsum.photos/1300")
                        ),
                        rating = 4.8f,
                        contactInfo = AttractionContactInfo(
                            phone = "09035135466",
                            address = "اصفان",
                            website = "marthia.com",
                        ),
                        openingHours = listOf(
                            AttractionOpeningHours(
                                id = 1,
                                workingDate = "شنبه ۲۹ آذر",
                                workingTime = "8:00 تا 22:30",
                                isClosed = false,
                                notes = ""
                            ),
                            AttractionOpeningHours(
                                id = 2,
                                workingDate = "یکشنبه ۲۹ آذر",
                                workingTime = "8:00 تا 22:30",
                                isClosed = false,
                                notes = ""
                            ),
                            AttractionOpeningHours(
                                id = 3,
                                workingDate = "دوشنبه ۲۹ آذر",
                                workingTime = "8:00 تا 22:30",
                                isClosed = false,
                                notes = ""
                            ),
                            AttractionOpeningHours(
                                id = 4,
                                workingDate = "سه‌شنبه ۲۹ آذر",
                                workingTime = "8:00 تا 22:30",
                                isClosed = false,
                                notes = ""
                            ),
                            AttractionOpeningHours(
                                id = 5,
                                workingDate = "چهارشنبه ۲۹ آذر",
                                workingTime = "8:00 تا 22:30",
                                isClosed = false,
                                notes = ""
                            ),
                            AttractionOpeningHours(
                                id = 6,
                                workingDate = "پنج‌شنبه ۲۹ آذر",
                                workingTime = "8:00 تا 22:30",
                                isClosed = true,
                                notes = "به دلیل آلودگی هوا تعطیل است"
                            ),

                            ),
                        reviewCount = 150,
                        location = Location(
                            latitude = 41.4,
                            longitude = 41.4,
                            name = "میدان نقش جهان",
                            description = " نقش جهان آینه شکوه و عظمت ایران و یادگار دوران صفوی با زیبایی هرچه تمام مایه فخر ایران و ایرانی است. نقش جهان آینه شکوه و عظمت ایران و یادگار دوران صفوی با زیبایی هرچه تمام مایه فخر ایران و ایرانی است. نقش جهان آینه شکوه و عظمت ایران و یادگار دوران صفوی با زیبایی هرچه تمام مایه فخر ایران و ایرانی است.",
                            address = "اصفهان میدان انقلاب",
                            city = "اصفهان",
                            province = "اصفهان",
                            id = 1,
                            type = LocationType.ATTRACTION,
                        ),
                        isFavorite = true,
                        reviews = listOf(
                            Review(
                                id = 0,
                                user = User(
                                    id = 1,
                                    name = "سارا اسدی",
                                    username = "sara",
                                    email = "sara@email.me",
                                    image = "https://picsum.photos/200",
                                    phone = "091412345678"
                                ),
                                title = "عالی بود",
                                content = "تور از همه نظر عالی بود",
                                date = "24/11/1395"
                            ),
                            Review(
                                id = 2,
                                user = User(
                                    id = 2,
                                    name = "آنیتا کریمی",
                                    username = "sara",
                                    email = "sara@email.me",
                                    image = "https://picsum.photos/200",
                                    phone = "091412345678"
                                ),
                                title = "عالی بود",
                                content = "تور از همه نظر عالی بود",
                                date = "24/11/1395"
                            )
                        ),
                        relatedTours = listOf(
                            Tour(
                                id = -1,
                                title = "تور یک روزه میدان نقش جهان",
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
                                price = 123546.8,
                                category = AttractionCategory(
                                    id = 0,
                                    name = "تاریخی",
                                    description = "",
                                    image = ""
                                ),
                                maxPeople = 7,
                                status = TourStatus.PENDING,
                                rate = 4.8f,
                                reviews = listOf(
                                    Review(
                                        id = 0,
                                        user = User(
                                            id = 1,
                                            name = "سارا اسدی",
                                            username = "sara",
                                            email = "sara@email.me",
                                            image = "https://picsum.photos/200",
                                            phone = "091412345678"
                                        ),
                                        title = "عالی بود",
                                        content = "تور از همه نظر عالی بود",
                                        date = "24/11/1395"
                                    ),
                                    Review(
                                        id = 2,
                                        user = User(
                                            id = 2,
                                            name = "آنیتا کریمی",
                                            username = "sara",
                                            email = "sara@email.me",
                                            image = "https://picsum.photos/200",
                                            phone = "091412345678"
                                        ),
                                        title = "عالی بود",
                                        content = "تور از همه نظر عالی بود",
                                        date = "24/11/1395"
                                    )
                                ),
                                images = listOf("https://picsum.photos/400"),
                                requiredItems = listOf(
                                    "کفش کوهنوردی",
                                    "کلاه",
                                    "عینک آفتابی",
                                    "تنقلات",
                                    "زیرانداز تک نفره",
                                    "پول نقد",
                                ),
                                level = "آسان",
                                dueDate = "۱۶ بهمن",
                                startTime = "۸ صبح",
                                demographic = "14-65",
                                itinerary =
                                    listOf(
                                        ItineraryStop("میدان نقش جهان", "شنبه ۲۴ دی ساعت ۱۶"),
                                        ItineraryStop("سی و سه پل", "ساعت ۱۷"),
                                        ItineraryStop("کلیسای وانک", "ساعت ۱۸"),
                                        ItineraryStop("کلیسای مریم مقدس", "ساعت ۱۹"),
                                        ItineraryStop("میدان جلفا", "شنبه ۲۴ دی ساعت ۲۰"),
                                    ),
                                city = City(
                                    id = 1,
                                    name = "اصفهان",
                                    county = "مرکزی",
                                    province = "اصفهان",
                                ),
                            )
                        )
                    )
                )
            }
        }
    }
}
