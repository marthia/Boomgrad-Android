package me.marthia.app.boomgrad.presentation.cart

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.DeleteForever
import androidx.compose.material.icons.rounded.FiberManualRecord
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import me.marthia.app.boomgrad.R
import me.marthia.app.boomgrad.presentation.components.AppScaffold
import me.marthia.app.boomgrad.presentation.components.IconText
import me.marthia.app.boomgrad.presentation.components.JetHorizontalDivider
import me.marthia.app.boomgrad.presentation.components.JetSnackBackground
import me.marthia.app.boomgrad.presentation.components.JetsnackCard
import me.marthia.app.boomgrad.presentation.components.JetsnackFilledIconButton
import me.marthia.app.boomgrad.presentation.components.QuantitySelector
import me.marthia.app.boomgrad.presentation.theme.AppTheme
import me.marthia.app.boomgrad.presentation.theme.BaseTheme
import me.marthia.app.boomgrad.presentation.util.debugPlaceholder

@Composable
fun CartScreen() {

    AppScaffold() {
        CartScreen(modifier = Modifier, paddingValues = it,)
    }
}

@Composable
fun CartScreen(modifier: Modifier = Modifier,
               paddingValues: PaddingValues,) {

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        item {
            Spacer(Modifier.height(paddingValues.calculateTopPadding()))
        }

        items(2) {
            CartItem()
        }
        item {
            CartOverview()
        }

        item {
            CancellationPolicy()
        }

        item {
            Spacer(
                Modifier.windowInsetsBottomHeight(
                    WindowInsets.navigationBars.add(WindowInsets(bottom = 80.dp + paddingValues.calculateBottomPadding())),
                ),
            )
        }
    }
}

@Composable
private fun CartItem(modifier: Modifier = Modifier) {
    JetsnackCard(modifier = modifier) {

        Column() {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {

                AsyncImage(
                    "https://picsum.photos/400",
                    contentDescription = "profile",
                    placeholder = debugPlaceholder(debugPreview = R.drawable.placeholder),
                    modifier = Modifier
                        .size(96.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop,
                )
                Spacer(Modifier.width(8.dp))

                Column {
                    Text(
                        buildAnnotatedString {
                            append("تور یک روزه میدان نقش جهان")
                            appendLine()
                            append("هر نفر ۱۲۰۰ تومان")
                        },
                        color = BaseTheme.colors.textSecondary,
                        style = MaterialTheme.typography.titleSmall,
                    )

                    IconText(
                        text = {
                            Text(
                                buildAnnotatedString {
                                    append("از ۲۶ دی")
                                    append("\t\t")
                                    withStyle(SpanStyle(color = Color.Black)) {
                                        append("تا")
                                    }
                                    append("\t\t")
                                    append("۳۰ دی")
                                },
                                style = MaterialTheme.typography.labelSmall,
                            )
                        },
                        leadingIcon = {
                            Icon(Icons.Rounded.DateRange, "DateRange")
                        },
                    )
                }

                Spacer(Modifier.width(8.dp))

                JetsnackFilledIconButton(onClick = {}) {
                    Icon(Icons.Rounded.DeleteForever, "Delete")
                }
            }

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
//                        count = countState,
                )
            }

            JetHorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            )

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
                    color = BaseTheme.colors.textHelp,
                )
                Text(
                    "۱۲۰,۰۰۰ تومان",
                    style = MaterialTheme.typography.titleMedium,
                    color = BaseTheme.colors.brand,
                )

            }
        }
    }
}


@Composable
fun CartOverview(modifier: Modifier = Modifier) {
    JetsnackCard(modifier = modifier) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp),
        ) {
            Text(
                "خلاصه سفارش",
                style = MaterialTheme.typography.titleMedium,
                color = BaseTheme.colors.textSecondary
            )

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    "جمع تورها",
                    style = MaterialTheme.typography.bodyLarge,
                    color = BaseTheme.colors.textHelp
                )
                Text(
                    "۱۲۶۹۹۰ تومان",
                    style = MaterialTheme.typography.bodyLarge,
                    color = BaseTheme.colors.textSecondary
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    "هزینه سرویس",
                    style = MaterialTheme.typography.bodyLarge,
                    color = BaseTheme.colors.textHelp
                )
                Text(
                    "۱۲۶۹۹۰ تومان",
                    style = MaterialTheme.typography.bodyLarge,
                    color = BaseTheme.colors.textSecondary
                )
            }

            JetHorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    "مجموع نهایی",
                    style = MaterialTheme.typography.bodyLarge,
                    color = BaseTheme.colors.textHelp
                )
                Text(
                    "۱۲۶۹۹۰ تومان",
                    style = MaterialTheme.typography.bodyLarge,
                    color = BaseTheme.colors.textPrimary
                )
            }
        }
    }

}


@Composable
fun CancellationPolicy(modifier: Modifier = Modifier) {
    val highlights = listOf(
        "تا ۴۸ ساعت قبل از شروع تور: بازگشت کامل وجه",
        "تا ۲۴ ساعت قبل از شروع تور: بازگشت ۵۰٪ وجه",
        "کمتر از ۲۴ ساعت قبل از شروع تور: عدم بازگشت وجه",
    )

    JetsnackCard(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Text(
                "سیاست لغو",
                style = MaterialTheme.typography.titleMedium,
                color = BaseTheme.colors.textSecondary
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
                            tint = Color.Black,
                        )
                    },
                    text = {
                        Text(
                            item,
                            style = MaterialTheme.typography.bodyMedium,
                            color = BaseTheme.colors.textSecondary,
                        )
                    },
                )
            }
        }
    }
}

@Preview("default", showBackground = true, showSystemUi = true)
//@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
//@Preview("large font", fontScale = 2f)
@Composable
private fun PreviewCart() {
    AppTheme {

        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {

            JetSnackBackground(modifier = Modifier.fillMaxSize()) {
                CartScreen(modifier = Modifier.systemBarsPadding(), PaddingValues(0.dp))
            }
        }
    }
}