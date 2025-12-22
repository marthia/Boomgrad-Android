package me.marthia.app.boomgrad.presentation.tour

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.rounded.FiberManualRecord
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import me.marthia.app.boomgrad.R
import me.marthia.app.boomgrad.presentation.components.IconText
import me.marthia.app.boomgrad.presentation.components.JetSnackBackground
import me.marthia.app.boomgrad.presentation.components.JetsnackCard
import me.marthia.app.boomgrad.presentation.components.AppScaffold
import me.marthia.app.boomgrad.presentation.components.JetsnackSurface
import me.marthia.app.boomgrad.presentation.components.QuantitySelector
import me.marthia.app.boomgrad.presentation.home.Itinerary
import me.marthia.app.boomgrad.presentation.home.ItineraryStop
import me.marthia.app.boomgrad.presentation.theme.AppTheme
import me.marthia.app.boomgrad.presentation.theme.BaseTheme
import me.marthia.app.boomgrad.presentation.util.debugPlaceholder

@Composable
fun TourDetail(tourId: Long, upPress: () -> Unit) {


    AppScaffold() {
        TourDetailContent(modifier = Modifier.padding(it))
    }

}

@Composable
fun TourDetailContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        TourImages()
        TourGist(modifier = Modifier.padding(16.dp))
        TourGuideInfo(modifier = Modifier.padding(16.dp))
        Description(modifier = Modifier.padding(16.dp))
        Highlights(modifier = Modifier.padding(16.dp))
        Specs(modifier = Modifier.padding(16.dp))
        Prerequisites(modifier = Modifier.padding(16.dp))
        ItinerarySection(modifier = Modifier.padding(16.dp))
        Price()
        AddToCard()
    }

}

@Composable
fun TourImages(modifier: Modifier = Modifier) {

    val items = listOf(
        R.drawable.ali_qapu,
        R.drawable.naghshe_jahan1,
        R.drawable.ali_qapu,
        R.drawable.naghshe_jahan1
    )
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp),
    ) {

        items(items) { item ->
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item)
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
fun TourGist(modifier: Modifier = Modifier) {

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("تور میدان نقش جهان", style = MaterialTheme.typography.titleMedium)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.icon_star_20),
                contentDescription = "score",
                tint = Color.Unspecified
            )
            Text(text = "4.8 ", style = MaterialTheme.typography.bodySmall)

            Text(
                "(۱۵۶ نظر)",
                style = MaterialTheme.typography.bodySmall,
                color = BaseTheme.colors.textHelp
            )
        }
    }
}

@Composable
fun TourGuideInfo(modifier: Modifier = Modifier) {
    JetsnackCard(modifier = modifier, elevation = 0.dp) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
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
                        "راهنمای رسمی",
                        style = MaterialTheme.typography.labelSmall,
                        color = BaseTheme.colors.textHelp
                    )
                    Text(
                        "امین عدیلی",
                        style = MaterialTheme.typography.titleSmall,
                        color = BaseTheme.colors.textSecondary
                    )
                }
            }

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
                        "4.8",
                        style = MaterialTheme.typography.labelLarge,
                        color = BaseTheme.colors.textSecondary
                    )
                },
            )
        }
    }
}

@Composable
fun Description(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text("توضیحات", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))
        Text(
            "با این تور یک روزه، سفری به قلب تاریخ و هنر اصفهان خواهید داشت. میدان نقش جهان، یکی از بزرگترین و زیباترین میادین جهان، میزبان شما خواهد بود. در این گشت، از شاهکارهای معماری دوران صفوی مانند مسجد شیخ لطف‌الله، مسجد امام و کاخ عالی‌قاپو دیدن خواهید کرد. همچنین، فرصت گشت و گذار در بازار قیصریه و خرید صنایع دستی اصیل اصفهان را خواهید داشت. این تور تجربه‌ای فراموش‌نشدنی از فرهنگ غنی ایران را برای شما به ارمغان می‌آورد.",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Justify,
            color = BaseTheme.colors.textHelp,
        )
    }
}

@Composable
fun Highlights(modifier: Modifier = Modifier) {
    val highlights = listOf(
        "شناخت تاریخ",
        "نوشیدنی مخصوص",
        "گز اصفهان",
        "مسجد و محراب",
        "چاه حاج میرزا",
    )

    Column(modifier = modifier) {


        Text("خدمات سفر", style = MaterialTheme.typography.titleMedium)
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
                    Text(item)
                },
            )
        }
    }
}

@Composable
fun TourSpecItem(modifier: Modifier = Modifier, label: String, labelIcon: Int, value: String) {
    JetsnackSurface(
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
        border = BorderStroke(width = 2.dp, color = BaseTheme.colors.outline),
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
            Text(text = value)
        }
    }
}

@Composable
fun Specs(modifier: Modifier = Modifier) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            TourSpecItem(
                modifier = Modifier.weight(1f),
                label = "مدت زمان",
                labelIcon = R.drawable.icon_duration_16,
                value = "۸ ساعت"
            )
            TourSpecItem(
                modifier = Modifier.weight(1f),
                label = "درجه سختی",
                labelIcon = R.drawable.icon_level_16,
                value = "آسان"
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TourSpecItem(
                modifier = Modifier.weight(1f),
                label = "تاریخ برگزاری",
                labelIcon = R.drawable.icon_due_date_16,
                value = "۲۵ دی ۱۴۰۴",
            )
            TourSpecItem(
                modifier = Modifier.weight(1f),
                label = "ساعت شروع",
                labelIcon = R.drawable.icon_start_time_16,
                value = "۸ صبح"
            )
        }
        TourSpecItem(
            modifier = Modifier.fillMaxWidth(),
            label = "رده سنی",
            labelIcon = R.drawable.icon_demographic_16,
            value = "۱۴-۶۵"
        )
    }

}

@Composable
fun Prerequisites(modifier: Modifier = Modifier) {
    val highlights = listOf(
        "کفش کوهنوردی",
        "کلاه",
        "عینک آفتابی",
        "تنقلات",
        "زیرانداز تک نفره",
        "پول نقد",
    )

    Column(modifier = modifier) {


        Text(
            stringResource(R.string.tour_detail_title_prerequisites),
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(Modifier.height(8.dp))

        highlights.forEach { item ->
            IconText(
                modifier = Modifier.padding(start = 8.dp),
                leadingIcon = {
                    Icon(
                        modifier = Modifier.size(8.dp),
                        imageVector = Icons.Rounded.FiberManualRecord,
                        tint = BaseTheme.colors.textHelp,
                        contentDescription = "highlight",
                    )
                },
                text = {
                    Text(
                        text = item,
                        style = MaterialTheme.typography.bodySmall,
                        color = BaseTheme.colors.textHelp
                    )
                },
            )
        }
    }
}

@Composable
fun ItinerarySection(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = "برنامه سفر",
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(Modifier.height(8.dp))

        Itinerary(
            modifier = Modifier,
            stops = listOf(
                ItineraryStop("میدان نقش جهان", "شنبه ۲۴ دی ساعت ۱۶"),
                ItineraryStop("سی و سه پل", "ساعت ۱۷"),
                ItineraryStop("کلیسای وانک", "ساعت ۱۸"),
                ItineraryStop("کلیسای مریم مقدس", "ساعت ۱۹"),
                ItineraryStop("میدان جلفا", "شنبه ۲۴ دی ساعت ۲۰"),
            ),
        )
    }

}

@Composable
fun Price() {

    val countState = remember { mutableIntStateOf(1) }

    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                "قیمت هر نفر",
                style = MaterialTheme.typography.bodyMedium,
                color = BaseTheme.colors.textHelp
            )
            Text("۱۲۰۰۰۰ تومان", style = MaterialTheme.typography.bodyLarge)
        }
        Spacer(Modifier.height(8.dp))


        JetsnackSurface(
            shape = MaterialTheme.shapes.medium,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp, horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("تعداد نفرات", style = MaterialTheme.typography.bodyLarge)
                QuantitySelector(
                    modifier = Modifier,
                    count = countState,
                )
            }
        }

        JetsnackSurface(
            shape = MaterialTheme.shapes.medium,
            color = BaseTheme.colors.uiFloated,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    "جمع کل",
                    style = MaterialTheme.typography.bodyMedium,
                    color = BaseTheme.colors.textHelp
                )
                Text(
                    "۱۲۰,۰۰۰ تومان",
                    style = MaterialTheme.typography.titleLarge,
                    color = BaseTheme.colors.brand
                )

            }
        }
    }
}


@Composable
fun AddToCard() {
}


@Preview("default", showBackground = true, showSystemUi = true)
//@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("large font", fontScale = 2f)
@Composable
private fun PreviewTourDetail() {
    AppTheme {

        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {

            JetSnackBackground(modifier = Modifier.fillMaxSize()) {
                TourDetailContent(modifier = Modifier.systemBarsPadding())
            }
        }
    }
}
