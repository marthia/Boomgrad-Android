package me.marthia.app.boomgrad.presentation.cart

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.DeleteForever
import androidx.compose.material.icons.rounded.FiberManualRecord
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import me.marthia.app.boomgrad.R
import me.marthia.app.boomgrad.presentation.cart.model.CartItemUiModel
import me.marthia.app.boomgrad.presentation.cart.model.CartUiModel
import me.marthia.app.boomgrad.presentation.common.ErrorScreen
import me.marthia.app.boomgrad.presentation.common.LoadingScreen
import me.marthia.app.boomgrad.presentation.components.ButtonElement
import me.marthia.app.boomgrad.presentation.components.CardElement
import me.marthia.app.boomgrad.presentation.components.FilledIconButtonElement
import me.marthia.app.boomgrad.presentation.components.HorizontalDividerElement
import me.marthia.app.boomgrad.presentation.components.IconText
import me.marthia.app.boomgrad.presentation.components.QuantitySelector
import me.marthia.app.boomgrad.presentation.components.ScaffoldElement
import me.marthia.app.boomgrad.presentation.components.SurfaceElement
import me.marthia.app.boomgrad.presentation.components.TopBar
import me.marthia.app.boomgrad.presentation.theme.Theme
import me.marthia.app.boomgrad.presentation.util.ViewState
import me.marthia.app.boomgrad.presentation.util.debugPlaceholder
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    onCheckout: (totalAmount: Long) -> Unit,
    onBackClick: () -> Unit,
    viewModel: CartViewModel = koinViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ScaffoldElement(
        topBar = {
            TopBar(
                title = {
                    Text(stringResource(R.string.title_guide_info))
                },
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
                    CartContent(
                        cart = state.value,
                        paddingValues = paddingValues,
                        onRemove = viewModel::onRemoveItem,
                        onPeopleCountChanged = viewModel::onPeopleCountChanged,
                        onCheckout = {
                            viewModel.onCheckout()
                            onCheckout(state.value.totalAmountToman)
                        },
                        peopleCountFor = viewModel::peopleCountFor,
                        formatPrice = viewModel::formatPrice,
                    )
                }

                is ViewState.Error -> {
                    ErrorScreen(
                        onRetry = { }
                    )
                }

                else -> {}
            }
        }
    }
}

// ── Content (non-empty cart) ──────────────────────────────────────────────────

@Composable
private fun CartContent(
    cart: CartUiModel,
    paddingValues: PaddingValues,
    onRemove: (Long) -> Unit,
    onPeopleCountChanged: (CartItemUiModel, Int) -> Unit,
    onCheckout: () -> Unit,
    peopleCountFor: (Long) -> androidx.compose.runtime.MutableIntState,
    formatPrice: (Long) -> String,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item { Spacer(Modifier.height(paddingValues.calculateTopPadding())) }

        items(
            items = cart.items,
            key = { it.id },
        ) { item ->
            val countState = peopleCountFor(item.id)

            // Sync QuantitySelector changes back to ViewModel
            LaunchedEffect(countState.intValue) {
                if (countState.intValue != item.peopleCount) {
                    onPeopleCountChanged(item, countState.intValue)
                }
            }

            CartItemCard(
                item = item,
                countState = countState,
                onRemove = { onRemove(item.id) },
                formatPrice = formatPrice,
            )
        }

        item {
            CartOverview(
                cart = cart,
            )
        }

        item { CancellationPolicy() }

        item {
            ButtonElement(
                onClick = onCheckout,
                enabled = cart.isCheckoutEnabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = MaterialTheme.shapes.medium,
            ) {
                Text(
                    stringResource(
                        R.string.cart_checkout_total,
                        cart.formattedTotal,
                    ),
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }

        item {
            Spacer(
                Modifier.windowInsetsBottomHeight(
                    WindowInsets.navigationBars.add(
                        WindowInsets(bottom = 80.dp + paddingValues.calculateBottomPadding())
                    )
                )
            )
        }
    }
}

// ── Cart Item Card ────────────────────────────────────────────────────────────

@Composable
private fun CartItemCard(
    item: CartItemUiModel,
    countState: androidx.compose.runtime.MutableIntState,
    onRemove: () -> Unit,
    formatPrice: (Long) -> String,
    modifier: Modifier = Modifier,
) {
    CardElement(modifier = modifier) {
        Column {

            // ── Availability warning chip ─────────────────────────────────────
            AnimatedVisibility(visible = item.availabilityWarning != null) {
                item.availabilityWarning?.let { warning ->
                    SurfaceElement(
                        color = MaterialTheme.colorScheme.errorContainer,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            Icon(
                                Icons.Rounded.Warning,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.error,
                                modifier = Modifier.size(16.dp),
                            )
                            Text(
                                warning,
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.error,
                            )
                        }
                    }
                }
            }

            // ── Price changed chip ────────────────────────────────────────────
            AnimatedVisibility(visible = item.priceChanged == true) {
                SurfaceElement(
                    color = MaterialTheme.colorScheme.tertiaryContainer,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = buildAnnotatedString {
                            append("قیمت به ")
                            withStyle(SpanStyle(color = MaterialTheme.colorScheme.tertiary)) {
                                append(item.currentPrice?.let { formatPrice(it) } ?: "")
                            }
                            append(" تغییر کرد")
                        },
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    )
                }
            }

            // ── Tour image + title + date ─────────────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                AsyncImage(
                    model = item.tourImageUrl ?: "https://picsum.photos/400",
                    contentDescription = item.tourTitle,
                    placeholder = debugPlaceholder(R.drawable.placeholder),
                    modifier = Modifier
                        .size(96.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop,
                )

                Spacer(Modifier.width(8.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = buildAnnotatedString {
                            append(item.tourTitle)
                            appendLine()
                            append(
                                stringResource(
                                    R.string.cart_item_per_person,
                                    item.formattedPricePerPerson,
                                )
                            )
                        },
                        style = MaterialTheme.typography.titleSmall,
                    )

                    Spacer(Modifier.height(4.dp))

                    IconText(
                        leadingIcon = {
                            Icon(Icons.Rounded.DateRange, contentDescription = null)
                        },
                        text = {
                            Text(
                                item.formattedDate,
                                style = MaterialTheme.typography.labelSmall,
                            )
                        },
                    )

                    IconText(
                        leadingIcon = {
                            Icon(Icons.Rounded.Schedule, contentDescription = null)
                        },
                        text = {
                            Text(
                                item.formattedTimeRange,
                                style = MaterialTheme.typography.labelSmall,
                            )
                        },
                    )
                }

                Spacer(Modifier.width(8.dp))

                FilledIconButtonElement(
                    onClick = onRemove,
                    containerColor = Theme.colors.materialTheme.secondaryContainer,
                    contentColor = Theme.colors.materialTheme.secondary,
                ) {
                    Icon(
                        Icons.Rounded.DeleteForever,
                        contentDescription = stringResource(R.string.cart_item_delete_description),
                    )
                }
            }

            // ── People count selector ─────────────────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp, horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    stringResource(R.string.cart_item_people_count),
                    style = MaterialTheme.typography.labelMedium,
                )
                QuantitySelector(
                    count = countState,
                    maxCount = item.maxPeople,
                )
            }

            HorizontalDividerElement(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            )

            // ── Item subtotal ─────────────────────────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    stringResource(R.string.cart_item_subtotal),
                    style = MaterialTheme.typography.labelLarge,
                    color = Theme.colors.textHelp,
                )
                // Recompute live from countState so it updates instantly without waiting for API
                Text(
                    formatPrice(item.pricePerPerson * countState.intValue),
                    style = MaterialTheme.typography.titleMedium,
                    color = Theme.colors.materialTheme.primary,
                )
            }
        }
    }
}

// ── Order Summary ─────────────────────────────────────────────────────────────

@Composable
fun CartOverview(
    cart: CartUiModel,
    modifier: Modifier = Modifier,
) {
    CardElement(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp),
        ) {
            Text(
                stringResource(R.string.cart_overview_title),
                style = MaterialTheme.typography.titleMedium,
            )

            Spacer(Modifier.height(16.dp))

            CartOverviewRow(
                label = stringResource(R.string.cart_overview_tours_subtotal),
                value = cart.formattedSubtotal,
            )

            CartOverviewRow(
                label = stringResource(R.string.cart_overview_service_fee),
                value = cart.formattedServiceFee,
            )

            HorizontalDividerElement(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
            )

            CartOverviewRow(
                label = stringResource(R.string.cart_overview_total),
                value = cart.formattedTotal,
                valueColor = Theme.colors.materialTheme.primary,
            )
        }
    }
}

@Composable
private fun CartOverviewRow(
    label: String,
    value: String,
    valueColor: Color = Color.Unspecified,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(label, style = MaterialTheme.typography.bodyLarge, color = Theme.colors.textHelp)
        Text(value, style = MaterialTheme.typography.bodyLarge, color = valueColor)
    }
}

// ── Cancellation Policy ───────────────────────────────────────────────────────

@Composable
fun CancellationPolicy(modifier: Modifier = Modifier) {
    CardElement(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Text(
                stringResource(R.string.cancellation_policy_title),
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(Modifier.height(8.dp))

            listOf(
                R.string.cancellation_policy_48h,
                R.string.cancellation_policy_24h,
                R.string.cancellation_policy_no_refund,
            ).forEach { resId ->
                IconText(
                    modifier = Modifier.padding(start = 8.dp, bottom = 4.dp),
                    leadingIcon = {
                        Icon(
                            modifier = Modifier.size(8.dp),
                            imageVector = Icons.Rounded.FiberManualRecord,
                            contentDescription = null,
                            tint = Color.Black,
                        )
                    },
                    text = {
                        Text(
                            stringResource(resId),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    },
                )
            }
        }
    }
}