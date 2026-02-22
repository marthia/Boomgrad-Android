package me.marthia.app.boomgrad.presentation.profile.mytrips

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import me.marthia.app.boomgrad.R
import me.marthia.app.boomgrad.presentation.common.ErrorScreen
import me.marthia.app.boomgrad.presentation.common.LoadingScreen
import me.marthia.app.boomgrad.presentation.components.BackgroundElement
import me.marthia.app.boomgrad.presentation.components.CardElement
import me.marthia.app.boomgrad.presentation.components.IconText
import me.marthia.app.boomgrad.presentation.components.ScaffoldElement
import me.marthia.app.boomgrad.presentation.components.SurfaceElement
import me.marthia.app.boomgrad.presentation.profile.component.PillTabRow
import me.marthia.app.boomgrad.presentation.theme.AppTheme
import me.marthia.app.boomgrad.presentation.theme.Theme
import me.marthia.app.boomgrad.presentation.util.ViewState
import org.koin.androidx.compose.koinViewModel

@Composable
fun MyTripsScreen(
    viewModel: MyTripsViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState) {
        is ViewState.Loading -> LoadingScreen()
        is ViewState.Error -> ErrorScreen()
        is ViewState.Success -> {

            ScaffoldElement() {
                MyTripsScreen(
                    modifier = Modifier
                        .padding(it),
                    state = (uiState as ViewState.Success<MyTripsUiState>).value
                )
            }
        }

        else -> {}
    }
}

@Composable
fun MyTripsScreen(modifier: Modifier = Modifier, state: MyTripsUiState) {
    val scope = rememberCoroutineScope()
    val tabs = listOf(
        stringResource(R.string.label_my_trips_tab_scheduled),
        stringResource(R.string.label_my_trips_tab_completed),
        stringResource(R.string.label_my_trips_tab_Cancelled),
    )
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { tabs.size })

    Column(modifier = modifier) {
        PillTabRow(
            tabs = tabs,
            selectedIndex = pagerState.currentPage,
            onTabSelected = { scope.launch { pagerState.animateScrollToPage(it) } },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp),
        )

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
        ) { page ->
            TabContent(page = page)
        }
    }
}


@Composable
fun TabContent(page: Int) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(20) { index ->

            TripItem(
                tourTitle = "تور نیم‌روزه‌ی میدان نقش جهان",
                dueTime = "۲۰ دی",
                count = "4 نفر"
            )
        }
    }
}

@Composable
fun TripItem(modifier: Modifier = Modifier, tourTitle: String, dueTime: String, count: String) {
    CardElement() {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp),
        ) {
            Text(
                text = tourTitle,
                style = MaterialTheme.typography.titleMedium,

                )

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {

                IconText(
                    modifier = Modifier.padding(bottom = 16.dp),
                    text = {
                        Text(
                            text = dueTime,
                            style = MaterialTheme.typography.titleMedium
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
                Text(
                    text = count,
                    style = MaterialTheme.typography.bodyLarge,

                    )
            }
            SurfaceElement(
                shape = MaterialTheme.shapes.small,
                color = Theme.colors.materialTheme.secondaryContainer
            ) {
                Text(text = "تایید شده", modifier = Modifier.padding(4.dp))
            }
        }
    }
}


@Preview("default", showBackground = true, showSystemUi = true, locale = "fa")
@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "fa")
@Preview("large font", fontScale = 2f, locale = "fa")

@Composable
private fun PreviewMyTrips() {
    AppTheme {

        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {

            BackgroundElement(modifier = Modifier.fillMaxSize()) {

                MyTripsScreen(modifier = Modifier, state = MyTripsUiState("خالی"))
            }
        }
    }
}