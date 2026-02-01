package me.marthia.app.boomgrad.presentation.attraction.detail

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import me.marthia.app.boomgrad.domain.model.AttractionContactInfo
import me.marthia.app.boomgrad.domain.model.AttractionOpeningHours
import me.marthia.app.boomgrad.domain.model.City
import me.marthia.app.boomgrad.domain.model.Location
import me.marthia.app.boomgrad.domain.model.LocationType
import me.marthia.app.boomgrad.presentation.category.CategoryTag
import me.marthia.app.boomgrad.presentation.components.AppScaffold
import me.marthia.app.boomgrad.presentation.components.IconText
import me.marthia.app.boomgrad.presentation.components.JetSnackBackground
import me.marthia.app.boomgrad.presentation.components.JetsnackSurface
import me.marthia.app.boomgrad.presentation.components.TopBar
import me.marthia.app.boomgrad.presentation.profile.component.dashedBorder
import me.marthia.app.boomgrad.presentation.theme.AppTheme
import me.marthia.app.boomgrad.presentation.theme.BaseTheme
import me.marthia.app.boomgrad.presentation.util.RightToLeftLayout
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
//            when (val state = uiState) {
//                is DetailUiState.Loading -> LoadingState()
//                is DetailUiState.Success -> {
            AttractionDetailContent(
                attraction = Attraction(
                    id = "",


                    category = "تاریخی",
                    images = listOf(),
                    rating = 4.8f,
                    contactInfo = AttractionContactInfo(
                        phone = "09035135466",
                        email = "marthia@pm.me",
                        website = "marthia.com",
                    ),
                    openingHours = listOf(
                        AttractionOpeningHours(
                            date = "شنبه ۲۹ آذر",
                            workingHour = "8:00 تا 22:30",
                        ),
                        AttractionOpeningHours(
                            date = "یکشنبه ۲۹ آذر",
                            workingHour = "8:00 تا 22:30",
                        ),
                        AttractionOpeningHours(
                            date = "دوشنبه ۲۹ آذر",
                            workingHour = "8:00 تا 22:30",
                        ),
                        AttractionOpeningHours(
                            date = "سه‌شنبه ۲۹ آذر",
                            workingHour = "8:00 تا 22:30",
                        ),
                        AttractionOpeningHours(
                            date = "چهارشنبه ۲۹ آذر",
                            workingHour = "8:00 تا 22:30",
                        ),
                        AttractionOpeningHours(
                            date = "پنج‌شنبه ۲۹ آذر",
                            workingHour = "8:00 تا 22:30",
                        ),

                        ),
                    reviewCount = 160,
                    location = Location(
                        latitude = 41.4,
                        longitude = 41.4,
                        name = "میدان نقش جهان",
                        description = "نقش جهان آینه شکوه و عظمت ایران و یادگار دوران صفوی با زیبایی هرچه تمام مایه فخر ایران و ایرانی است. نقش جهان آینه شکوه و عظمت ایران و یادگار دوران صفوی با زیبایی هرچه تمام مایه فخر ایران و ایرانی است. نقش جهان آینه شکوه و عظمت ایران و یادگار دوران صفوی با زیبایی هرچه تمام مایه فخر ایران و ایرانی است.",
                        address = "اصفهان میدان انقلاب",
                        city = City(
                            id = 1,
                            name = "اصفهان",
                            county = "مرکزی",
                            province = "اصفهان",
                        ),
                        id = 1,
                        type = LocationType.ATTRACTION,
                    ),
                )
            )
//                }

//                is DetailUiState.Error -> {
//                    ErrorState(
//                        message = state.message,
//                        onRetry = { viewModel.retry() }
//                    )
//                }
//            }
        }
    }
}


@Composable
fun AttractionImages(modifier: Modifier = Modifier, images: List<String>) {

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
                    .data(imageLink)
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
                    color = BaseTheme.colors.textHelp
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {

        AttractionImages(images = attraction.images)

        AttractionGist(
            title = attraction.location.name,
            reviewCount = attraction.reviewCount,
            rate = attraction.rating,
            category = attraction.category,
            city = "${attraction.location.city.province} , ${attraction.location.city.name}"
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
                value = contactInfo.email
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
    JetsnackSurface(
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
        border = BorderStroke(width = 2.dp, color = BaseTheme.colors.outline),
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
                        tint = BaseTheme.colors.textHelp
                    )
                },
                text = {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelSmall,
                        color = BaseTheme.colors.textHelp
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

        JetsnackSurface(
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
                            text = it.date,
                            style = MaterialTheme.typography.bodySmall,
                            color = BaseTheme.colors.textHelp,
                        )
                        Text(
                            text = it.workingHour,
                            style = MaterialTheme.typography.bodyLarge,
                            color = BaseTheme.colors.textSecondary,
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
    textColor: Color = BaseTheme.colors.error,
    iconContainerColor: Color = BaseTheme.colors.iconInteractive,
    iconTint: Color = BaseTheme.colors.error,
    containerColor: Color = BaseTheme.colors.errorContainer,
    borderColor: Color = BaseTheme.colors.error,
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


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewAttraction() {
    AppTheme() {
        RightToLeftLayout {
            JetSnackBackground() {
                AttractionDetailContent(
                    Attraction(
                        id = "",
                        category = "تاریخی",
                        images = listOf(),
                        rating = 4.8f,
                        contactInfo = AttractionContactInfo(
                            phone = "09035135466",
                            email = "marthia@pm.me",
                            website = "marthia.com",
                        ),
                        openingHours = listOf(
                            AttractionOpeningHours(
                                date = "شنبه ۲۹ آذر",
                                workingHour = "8:00 تا 22:30",
                            ),
                            AttractionOpeningHours(
                                date = "یکشنبه ۲۹ آذر",
                                workingHour = "8:00 تا 22:30",
                            ),
                            AttractionOpeningHours(
                                date = "دوشنبه ۲۹ آذر",
                                workingHour = "8:00 تا 22:30",
                            ),
                            AttractionOpeningHours(
                                date = "سه‌شنبه ۲۹ آذر",
                                workingHour = "8:00 تا 22:30",
                            ),
                            AttractionOpeningHours(
                                date = "چهارشنبه ۲۹ آذر",
                                workingHour = "8:00 تا 22:30",
                            ),
                            AttractionOpeningHours(
                                date = "پنج‌شنبه ۲۹ آذر",
                                workingHour = "8:00 تا 22:30",
                            ),

                            ),
                        reviewCount = 150,
                        location = Location(
                            latitude = 41.4,
                            longitude = 41.4,
                            name = "میدان نقش جهان",
                            description = " نقش جهان آینه شکوه و عظمت ایران و یادگار دوران صفوی با زیبایی هرچه تمام مایه فخر ایران و ایرانی است. نقش جهان آینه شکوه و عظمت ایران و یادگار دوران صفوی با زیبایی هرچه تمام مایه فخر ایران و ایرانی است. نقش جهان آینه شکوه و عظمت ایران و یادگار دوران صفوی با زیبایی هرچه تمام مایه فخر ایران و ایرانی است.",
                            address = "اصفهان میدان انقلاب",
                            city = City(
                                id = 1,
                                name = "اصفهان",
                                county = "مرکزی",
                                province = "اصفهان",
                            ),
                            id = 1,
                            type = LocationType.ATTRACTION,
                        ),
                        isFavorite = true,
                    )
                )
            }
        }
    }
}
