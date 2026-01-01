/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:OptIn(ExperimentalLayoutApi::class, ExperimentalSharedTransitionApi::class)

package me.marthia.app.boomgrad.presentation.home

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import me.marthia.app.boomgrad.domain.model.City
import me.marthia.app.boomgrad.domain.model.County
import me.marthia.app.boomgrad.domain.model.Province
import me.marthia.app.boomgrad.presentation.FilterSharedElementKey
import me.marthia.app.boomgrad.presentation.components.DropDownPicker
import me.marthia.app.boomgrad.presentation.theme.BaseTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CurrentCityScreen(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    viewModel: HomeViewModel = koinViewModel(),
    onDismiss: () -> Unit
) {

    // State for dropdown data
    val provinces by viewModel.provinces.collectAsState()
    val counties by viewModel.counties.collectAsState()
    val cities by viewModel.cities.collectAsState()

    // Selected values
    var selectedProvince by remember { mutableStateOf<Province?>(null) }
    var selectedCounty by remember { mutableStateOf<County?>(null) }
    var selectedCity by remember { mutableStateOf<City?>(null) }

    // Load provinces on first composition
    LaunchedEffect(Unit) {
        viewModel.getProvince()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
            ) { /* capture click */ },
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                ) { onDismiss() },
        )

        with(sharedTransitionScope) {
            Column(
                Modifier
                    .padding(16.dp)
                    .align(Alignment.Center)
                    .clip(MaterialTheme.shapes.medium)
                    .sharedBounds(
                        rememberSharedContentState(FilterSharedElementKey),
                        animatedVisibilityScope = animatedVisibilityScope,
                        resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
                        clipInOverlayDuringTransition = OverlayClip(MaterialTheme.shapes.medium),
                    )
                    .wrapContentSize()
                    .heightIn(max = 450.dp)
                    .verticalScroll(rememberScrollState())
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                    ) { /* prevent dismiss */ }
                    .background(BaseTheme.colors.uiFloated)
                    .padding(horizontal = 24.dp, vertical = 16.dp)
                    .skipToLookaheadSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Text(
                    text = "انتخاب شهر",
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "برای نمایش دقیق‌تر جاذبه‌ها و تورهای اطراف شهر خود را انتخاب نمایید",
                    style = MaterialTheme.typography.bodyMedium
                )

                // Province Dropdown
                DropDownPicker(
                    label = { Text("Province") },
                    placeholder = { Text("Select Province") },
                    items = provinces,
                    selectedItem = selectedProvince,
                    onItemSelected = { province ->
                        selectedProvince = province
                        selectedCounty = null  // Reset county when province changes
                        selectedCity = null     // Reset city when province changes
                        viewModel.getCounty(province.id)
                    }
                )

                // County Dropdown (only enabled when province is selected)
                DropDownPicker(
                    label = { Text("County") },
                    placeholder = { Text("Select County") },
                    items = counties,
                    selectedItem = selectedCounty,
                    enabled = selectedProvince != null,
                    onItemSelected = { county ->
                        selectedCounty = county
                        selectedCity = null  // Reset city when county changes
                        selectedProvince?.let { province ->
                            viewModel.getCity(province.id, county.id)
                        }
                    }
                )

                // City Dropdown (only enabled when county is selected)
                DropDownPicker(
                    label = { Text("City") },
                    placeholder = { Text("Select City") },
                    items = cities,
                    selectedItem = selectedCity,
                    enabled = selectedCounty != null,
                    onItemSelected = { city ->
                        selectedCity = city
                    }
                )

                Button(
                    onClick = {
                        selectedCity?.let {
                            // Handle final selection
                            onDismiss()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = selectedCity != null
                ) {
                    Text("Confirm")
                }
            }
        }
    }
}

