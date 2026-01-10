package me.marthia.app.boomgrad.presentation.attraction.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
import me.marthia.app.boomgrad.presentation.components.AppScaffold
import me.marthia.app.boomgrad.presentation.components.IconText
import me.marthia.app.boomgrad.presentation.components.JetSnackBackground
import me.marthia.app.boomgrad.presentation.components.JetsnackSurface
import me.marthia.app.boomgrad.presentation.components.TopBar
import me.marthia.app.boomgrad.presentation.theme.AppTheme
import me.marthia.app.boomgrad.presentation.theme.BaseTheme
import me.marthia.app.boomgrad.presentation.util.RightToLeftLayout
import me.marthia.app.boomgrad.presentation.util.debugPlaceholder
import org.koin.androidx.compose.koinViewModel
import java.util.Calendar

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
                    imageUrl = "",
                    rating = 4.8f,
                    contactInfo = AttractionContactInfo(
                        phone = "09035135466",
                        email = "marthia@pm.me",
                        website = "marthia.com",
                    ),
                    openingHours = AttractionOpeningHours(
                        monday = "8:00 تا 22:30",
                        tuesday = "8:00 تا 22:30",
                        wednesday = "8:00 تا 22:30",
                        thursday = "8:00 تا 22:30",
                        friday = "8:00 تا 22:30",
                        saturday = "8:00 تا 22:30",
                        sunday = "8:00 تا 22:30",
                    ),
                    location = Location(
                        latitude = 41.4,
                        longitude = 41.4,
                        name = "میدان نقش جهان",
                        description = "نقش جهان آینه شکوه و عظمت ایران و یادگار دوران صفوی با زیبایی هرچه تمام مایه فخر ایران و ایرانی است.",
                        address = "اصفهان میدان انقلاب",
                        city = City(
                            id = 1,
                            name = "اصفهان",
                            countyId = 1,
                            provinceId = 1,
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
private fun AttractionDetailContent(attraction: Attraction) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(R.drawable.ali_qapu)
                .crossfade(true)
                .build(),
            placeholder = debugPlaceholder(debugPreview = R.drawable.placeholder),
            contentDescription = "contentDescription",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(MaterialTheme.shapes.large),
            contentScale = ContentScale.Crop,
        )

        Column(
            modifier = Modifier.padding(16.dp)
        ) {


            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = attraction.location.name,
                    style = MaterialTheme.typography.headlineMedium
                )

                Row {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = BaseTheme.colors.brand
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${attraction.rating}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            JetsnackSurface(
                modifier = Modifier.align(Alignment.End),
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
                        Text(text = attraction.category, color = BaseTheme.colors.brand)
                    },
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = attraction.location.description,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            ContactInfoSection(attraction)

            Spacer(modifier = Modifier.height(16.dp))

            OpeningHoursSection(attraction)
        }
    }
}

@Composable
private fun ContactInfoSection(attraction: Attraction) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = BaseTheme.colors.uiBackground
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "اطلاعات تماس",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            ContactInfoRow(
                icon = Icons.Default.LocationOn,
                label = "آدرس",
                value = attraction.location.address
            )

            attraction.contactInfo.phone?.let { phone ->
                Spacer(modifier = Modifier.height(8.dp))
                ContactInfoRow(
                    icon = Icons.Default.Phone,
                    label = "تلفن تماس",
                    value = phone
                )
            }

            attraction.contactInfo.email?.let { email ->
                Spacer(modifier = Modifier.height(8.dp))
                ContactInfoRow(
                    icon = Icons.Default.Email,
                    label = "ایمیل",
                    value = email
                )
            }

            attraction.contactInfo.website?.let { website ->
                Spacer(modifier = Modifier.height(8.dp))
                ContactInfoRow(
                    icon = Icons.Default.Public,
                    label = "وبسایت",
                    value = website
                )
            }
        }
    }
}

@Composable
private fun ContactInfoRow(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = BaseTheme.colors.brand
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
//        Column {
//            Text(
//                text = label,
//                style = MaterialTheme.typography.labelSmall,
//                color = BaseTheme.colors.textSecondary
//            )
//
//        }
    }
}

@Composable
private fun OpeningHoursSection(attraction: Attraction) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = BaseTheme.colors.uiBackground
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "ساعات بازدید",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            val calendar = Calendar.getInstance()
            val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
            val todayHours = attraction.openingHours.getTodayHours(dayOfWeek)

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = BaseTheme.colors.uiContainer
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AccessTime,
                        contentDescription = "Today's hours",
                        tint = BaseTheme.colors.textPrimary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "امروز",
                            style = MaterialTheme.typography.labelMedium,
                            color = BaseTheme.colors.textPrimary
                        )
                        Text(
                            text = "تعطیل به علت آلودگی هوا",
                            style = MaterialTheme.typography.bodyLarge,
                            color = BaseTheme.colors.error
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            OpeningHourRow("شنبه", attraction.openingHours.saturday)
            OpeningHourRow("یکشنبه", attraction.openingHours.sunday)
            OpeningHourRow("دوشنبه", attraction.openingHours.monday)
            OpeningHourRow("سه‌شنبه", attraction.openingHours.tuesday)
            OpeningHourRow("چهارشنبه", attraction.openingHours.wednesday)
            OpeningHourRow("پنجشنبه", attraction.openingHours.thursday)
            OpeningHourRow("جمعه", attraction.openingHours.friday)
        }
    }
}

@Composable
private fun OpeningHourRow(day: String, hours: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = day,
            style = MaterialTheme.typography.bodyMedium,
            color = BaseTheme.colors.textHelp,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = hours,
            style = MaterialTheme.typography.bodyMedium,
            color = BaseTheme.colors.textSecondary
        )
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
                        imageUrl = "",
                        rating = 4.8f,
                        contactInfo = AttractionContactInfo(
                            phone = "09035135466",
                            email = "marthia@pm.me",
                            website = "marthia.com",
                        ),
                        openingHours = AttractionOpeningHours(
                            monday = "8:00 تا 22:30",
                            tuesday = "8:00 تا 22:30",
                            wednesday = "8:00 تا 22:30",
                            thursday = "8:00 تا 22:30",
                            friday = "8:00 تا 22:30",
                            saturday = "8:00 تا 22:30",
                            sunday = "8:00 تا 22:30",
                        ),
                        location = Location(
                            latitude = 41.4,
                            longitude = 41.4,
                            name = "میدان نقش جهان",
                            description = "نقش جهان آینه شکوه و عظمت ایران و یادگار دوران صفوی با زیبایی هرچه تمام مایه فخر ایران و ایرانی است.",
                            address = "اصفهان میدان انقلاب",
                            city = City(
                                id = 1,
                                name = "اصفهان",
                                countyId = 1,
                                provinceId = 1,
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
