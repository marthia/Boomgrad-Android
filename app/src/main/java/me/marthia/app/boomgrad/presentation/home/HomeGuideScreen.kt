package me.marthia.app.boomgrad.presentation.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import me.marthia.app.boomgrad.R
import me.marthia.app.boomgrad.domain.model.AttractionCategory
import me.marthia.app.boomgrad.domain.model.City
import me.marthia.app.boomgrad.domain.model.Demographic
import me.marthia.app.boomgrad.domain.model.Guide
import me.marthia.app.boomgrad.domain.model.ItineraryStop
import me.marthia.app.boomgrad.domain.model.TourDetail
import me.marthia.app.boomgrad.domain.model.TourStatus
import me.marthia.app.boomgrad.presentation.components.AppContainer
import me.marthia.app.boomgrad.presentation.components.IconText
import me.marthia.app.boomgrad.presentation.components.JetHorizontalDivider
import me.marthia.app.boomgrad.presentation.components.BackgroundElement
import me.marthia.app.boomgrad.presentation.components.ButtonElement
import me.marthia.app.boomgrad.presentation.components.CardElement
import me.marthia.app.boomgrad.presentation.components.SurfaceElement
import me.marthia.app.boomgrad.presentation.theme.AppTheme
import me.marthia.app.boomgrad.presentation.theme.Theme
import me.marthia.app.boomgrad.presentation.util.debugPlaceholder

@Composable
fun HomeScreen() {

    Column() {
        TopBar()
        CreateTour(modifier = Modifier.padding(16.dp))
        Stats(modifier = Modifier.fillMaxWidth())
        ActiveTours(
            modifier = Modifier.padding(top = 16.dp),
            list = listOf(
                TourDetail(
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
                    level = "آسان",
                    dueDate = "۱۶ بهمن",
                    startTime = "۸ صبح",
                    demographic = Demographic.ALL_AGES,
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
                    )
                )
            )
        )
        ActiveTours(
            modifier = Modifier.padding(top = 16.dp),
            list = listOf(
                TourDetail(
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
                    level = "آسان",
                    dueDate = "۱۶ بهمن",
                    startTime = "۸ صبح",
                    demographic = Demographic.ALL_AGES,
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
                    )
                )
            )
        )
    }
}

@Composable
private fun TopBar(modifier: Modifier = Modifier) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        AsyncImage(
            "https://picsum.photos/200",
            contentDescription = "profile",
            placeholder = debugPlaceholder(debugPreview = R.drawable.placeholder),
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
        )
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

            Text(
                text = "محسن رضایی",
                style = MaterialTheme.typography.titleSmall,

            )

            SurfaceElement(
                color = Theme.colors.materialTheme.primaryContainer,
                shape = MaterialTheme.shapes.small
            ) {
                Text(
                    modifier = Modifier.padding(4.dp),
                    text = "راهنمای رسمی",
                    style = MaterialTheme.typography.titleSmall,
                    color = Theme.colors.materialTheme.primary
                )
            }
        }
    }
}


@Composable
fun CreateTour(modifier: Modifier = Modifier) {
    AppContainer(

        modifier = modifier
            .fillMaxWidth()
            .height(100.dp),

        shape = MaterialTheme.shapes.large,
        gradient = Theme.colors.gradientTurq8Green8,
        contentColor = Theme.colors.materialTheme.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.label_home_create_tour),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = stringResource(id = R.string.description_home_create_tour),
                    style = MaterialTheme.typography.bodySmall
                )
            }

            SurfaceElement(
                Modifier
                    .size(40.dp)
                    .align(Alignment.CenterVertically),
                shape = CircleShape,
                contentAlignment = Alignment.Center,
                color = Theme.colors.materialTheme.secondaryContainer
            ) {
                Icon(
                    modifier = Modifier.size(28.dp),
                    imageVector = Icons.Rounded.Add,
                    tint = Theme.colors.materialTheme.secondary,
                    contentDescription = stringResource(id = R.string.label_home_create_tour)
                )
            }


        }
    }
}

@Composable
fun Stats(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        StatItem(
            modifier = Modifier.weight(1f),
            label = stringResource(R.string.label_home_guide_tours_stat),
            value = "۷",
            labelIcon = R.drawable.ic_calendar_24
        )
        StatItem(
            modifier = Modifier.weight(1f),
            label = stringResource(R.string.label_home_guide_people_signed_up),
            value = "235",
            labelIcon = R.drawable.ic_profile_user_24
        )
        StatItem(
            modifier = Modifier.weight(1f),
            label = stringResource(R.string.label_home_guide_people_views),
            value = "2345",
            labelIcon = R.drawable.ic_diagram_24
        )
    }
}

@Composable
fun StatItem(modifier: Modifier = Modifier, label: String, labelIcon: Int, value: String) {
    SurfaceElement(
        modifier = modifier.height(120.dp),
        shape = MaterialTheme.shapes.large,
        color = Theme.colors.materialTheme.secondaryContainer,
        border = BorderStroke(width = 2.dp, color = Theme.colors.materialTheme.outlineVariant),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(labelIcon),
                contentDescription = "Duration",
                tint = Theme.colors.materialTheme.secondary
            )

            Text(text = value, style = MaterialTheme.typography.titleMedium)

            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = Theme.colors.textHelp
            )
        }
    }
}

@Composable
private fun ActiveTours(modifier: Modifier = Modifier, list: List<TourDetail>) {

    Column(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        IconText(
            modifier = Modifier.padding(start = 16.dp),
            text = {
                Text(
                    stringResource(R.string.home_screen_active_tours_label),
                    style = MaterialTheme.typography.titleMedium
                )
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_featured_16),
                    tint = Color.Unspecified,
                    contentDescription = "Featured",
                )
            },
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp),
        ) {
            items(list) { tour ->
                TourItem(title = tour.title)
            }
        }

    }
}

@Composable
fun TourItem(modifier: Modifier = Modifier, title: String) {

    CardElement(color = Theme.colors.materialTheme.surface) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column() {

                    Text(
                        modifier = Modifier,
                        text = title,
                        style = MaterialTheme.typography.titleMedium,

                    )

                    Row {
                        IconText(
                            text = {
                                Text(
                                    "18 دی",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Theme.colors.textHelp
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(R.drawable.icon_due_date_16),
                                    tint = Color.Unspecified,
                                    contentDescription = "Featured",
                                )
                            },
                        )
                        Spacer(Modifier.width(8.dp))

                        IconText(
                            text = {
                                Text(
                                    "8:00",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Theme.colors.textHelp
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(R.drawable.icon_start_time_16),
                                    tint = Color.Unspecified,
                                    contentDescription = "Featured",
                                )
                            },
                        )
                    }
                }

                SurfaceElement(
                    color = Theme.colors.materialTheme.surface,
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        modifier = Modifier.padding(4.dp),
                        text = "در حال رزرو",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Theme.colors.materialTheme.primary
                    )
                }

            }
            JetHorizontalDivider(Modifier.padding(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.padding(4.dp),
                    text = "18 رزرو از ۲۰ ظرفیت رزرو",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Theme.colors.textHelp
                )
                ButtonElement(
                    onClick = {},
                    shape = MaterialTheme.shapes.small
                ) {
                    Text("مدیریت")
                }
            }
        }

    }
}

@Preview(showBackground = true, showSystemUi = true, locale = "fa")
@Composable
private fun PreviewHomeScreenGuide() {
    AppTheme {
        BackgroundElement(modifier = Modifier.fillMaxSize()) {
            HomeScreen()
        }
    }
}