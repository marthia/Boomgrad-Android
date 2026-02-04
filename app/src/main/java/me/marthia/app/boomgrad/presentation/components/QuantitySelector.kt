/*
 * Copyright 2020 The Android Open Source Project
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

package me.marthia.app.boomgrad.presentation.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.marthia.app.boomgrad.R
import me.marthia.app.boomgrad.presentation.theme.AppTheme
import me.marthia.app.boomgrad.presentation.theme.Theme


@Composable
fun QuantitySelector(
    modifier: Modifier = Modifier,
    count: MutableIntState = remember { mutableIntStateOf(1) }
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        JetsnackButton(
            onClick = { if (count.intValue > 0) count.intValue -= 1 },
            shape = MaterialTheme.shapes.small,
            contentColor = Theme.colors.materialTheme.primary,
            backgroundGradient = listOf(
                Theme.colors.materialTheme.background,
                Theme.colors.materialTheme.background
            ),
            border = BorderStroke(width = 2.dp, color = Theme.colors.uiBorder),
            contentPadding = PaddingValues(8.dp),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_minus_24),
                contentDescription = "minus"
            )
        }


        Crossfade(
            targetState = count.intValue,
            modifier = Modifier
                .align(Alignment.CenterVertically),
        ) {
            Text(
                text = "$it",
                style = MaterialTheme.typography.titleSmall,
                fontSize = 18.sp,
                color = Theme.colors.materialTheme.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier.widthIn(min = 24.dp),
            )
        }



        JetsnackButton(
            shape = MaterialTheme.shapes.small,
            contentPadding = PaddingValues(8.dp),
            onClick = { count.intValue += 1 },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_add_24),
                contentDescription = "add"
            )
        }
    }
}

@Preview("RTL")
@Composable
fun QuantitySelectorPreviewRtl2() {
    AppTheme {
        SurfaceElement {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                QuantitySelector()
            }
        }
    }
}
