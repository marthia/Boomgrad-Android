package me.marthia.app.boomgrad.presentation.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import me.marthia.app.boomgrad.R
import me.marthia.app.boomgrad.presentation.components.BackgroundElement
import me.marthia.app.boomgrad.presentation.components.CardElement
import me.marthia.app.boomgrad.presentation.components.IconText
import me.marthia.app.boomgrad.presentation.components.JetHorizontalDivider
import me.marthia.app.boomgrad.presentation.components.SurfaceElement
import me.marthia.app.boomgrad.presentation.theme.AppTheme
import me.marthia.app.boomgrad.presentation.theme.Theme
import me.marthia.app.boomgrad.presentation.util.debugPlaceholder

@Composable
fun ManageTourScreen() {


}

@Composable
fun ManageTourScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        AddImages(images = listOf())
        TopBar(title = "میدان نقش جهان", description = "بزرگ‌ترین میدان جهان")
        Features()
        Price(modifier = Modifier.padding(16.dp), price = "1.8")
        Participants()
    }
}


@Composable
fun AddImages(modifier: Modifier = Modifier, images: List<String>) {

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
fun TopBar(modifier: Modifier = Modifier, title: String, description: String) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {


        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

            Text(
                modifier = Modifier,
                text = title,
                style = MaterialTheme.typography.titleMedium,

                )

            Text(
                modifier = Modifier,
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = Theme.colors.textHelp
            )

        }
        SurfaceElement(
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
}

@Composable
fun Features(modifier: Modifier = Modifier) {
    CardElement(modifier = modifier.padding(12.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            IconText(
                text = {

                    Text(
                        "تاریخ:",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Theme.colors.textHelp
                    )

                    Text(
                        "28 بهمن",
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_due_date_24),
                        tint = Theme.colors.textHelp,
                        contentDescription = "Due Date"
                    )
                })

            JetHorizontalDivider()

            IconText(
                text = {

                    Text(
                        "ساعت شروع:",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Theme.colors.textHelp
                    )

                    Text(
                        "۸ صبح",
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_due_time_24),
                        tint = Theme.colors.textHelp,
                        contentDescription = "Due Date"
                    )
                })

            JetHorizontalDivider()

            IconText(
                text = {

                    Text(
                        "مکان:",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Theme.colors.textHelp
                    )

                    Text(
                        "تهران روبری ساختمان سردرباغ ملی خیابان فردوسی",
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_location_24),
                        tint = Theme.colors.textHelp,
                        contentDescription = "Due Date"
                    )
                })

            JetHorizontalDivider()

            IconText(
                text = {

                    Text(
                        "مدت زمان تور:",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Theme.colors.textHelp
                    )

                    Text(
                        "4 ساعت",
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_duration_24),
                        tint = Theme.colors.textHelp,
                        contentDescription = "Due Date"
                    )
                })

            JetHorizontalDivider()

            IconText(
                text = {

                    Text(
                        "ظرفیت:",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Theme.colors.textHelp
                    )

                    Text(
                        "12 نفر",
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_profile_user_24),
                        tint = Theme.colors.textHelp,
                        contentDescription = "Due Date"
                    )
                })

            JetHorizontalDivider()

            IconText(
                text = {

                    Text(
                        "قیمت",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Theme.colors.textHelp
                    )

                    Text(
                        "۲۵۰ تومان",
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_dollar_square_24),
                        tint = Theme.colors.textHelp,
                        contentDescription = "Due Date"
                    )
                })

        }

    }
}

@Composable
fun Price(modifier: Modifier = Modifier, price: String) {


    SurfaceElement(
        modifier = modifier,
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

            Column {
                Text(
                    text = stringResource(R.string.tour_detail_total_price),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Theme.colors.textHelp
                )
                Text(
                    text = "۱۴ رزرو * ۲۵۰ تومان",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Theme.colors.textHelp
                )
            }
            Column(horizontalAlignment = Alignment.End) {

                Text(
                    text = "$price میلیون تومان ",
                    style = MaterialTheme.typography.titleLarge,

                    )
                Text(
                    text = "۵ رزرو باقیمانده",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Theme.colors.textHelp
                )
            }
        }
    }
}

@Composable
fun Participants(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {

        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = "لیست رزروهای ۱۵ آذر(۸ رزرو)",
            style = MaterialTheme.typography.titleSmall,

            )

        repeat(5) {
            TouristContactInfo()
        }
    }
}

@Composable
fun TouristContactInfo(modifier: Modifier = Modifier) {
    SurfaceElement(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        color = Color.White,
        border = BorderStroke(width = 1.dp, color = Theme.colors.materialTheme.outline)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Column {
                IconText(text = {
                    Text(
                        text = "کاظم جان قربان",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Theme.colors.textHelp
                    )
                }, leadingIcon = {

                    Icon(painter = painterResource(R.drawable.ic_person_outlined_24), null)
                })

                IconText(text = {
                    Text(
                        text = "09131234567",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Theme.colors.textHelp
                    )
                }, leadingIcon = {

                    Icon(painter = painterResource(R.drawable.ic_call_24), null)
                })
            }
            Column(horizontalAlignment = Alignment.End) {

                Text(
                    text = "۴",
                    style = MaterialTheme.typography.titleLarge,

                    )
                Text(
                    text = "۲ بزرگسال ۲ خردسال",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Theme.colors.textHelp
                )
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true, locale = "fa")
@Composable
private fun PreviewManageTour() {
    AppTheme {
        BackgroundElement(modifier = Modifier.fillMaxSize()) {
            ManageTourScreen(Modifier)
        }
    }
}