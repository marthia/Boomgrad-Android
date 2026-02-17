@file:OptIn(ExperimentalMaterial3Api::class)

package me.marthia.app.boomgrad.presentation.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarColors
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SearchBarDefaults.inputFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import me.marthia.app.boomgrad.presentation.theme.Theme

@Composable
fun SearchElement(
    inputField: @Composable () -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = SearchBarDefaults.inputFieldShape,
    colors: SearchBarColors = SearchBarDefaults.colors(
        containerColor = Theme.colors.materialTheme.surface,
        dividerColor = Theme.colors.materialTheme.outline,
        inputFieldColors = inputFieldColors(
            focusedContainerColor = Theme.colors.materialTheme.surfaceContainerHighest,
            unfocusedContainerColor = Theme.colors.materialTheme.surfaceContainerLow,
            disabledContainerColor = Theme.colors.materialTheme.surfaceContainerLowest,
        ),
    ),
    tonalElevation: Dp = SearchBarDefaults.TonalElevation,
    shadowElevation: Dp = SearchBarDefaults.ShadowElevation,
    windowInsets: WindowInsets = SearchBarDefaults.windowInsets,
    content: @Composable ColumnScope.() -> Unit,
) {

    SearchBar(
        inputField = inputField,
        expanded = expanded,
        onExpandedChange = onExpandedChange,
        modifier = modifier,
        shape = shape,
        colors = colors,
        tonalElevation = tonalElevation,
        shadowElevation = shadowElevation,
        windowInsets = windowInsets,
        content = content,
    )

}