@file:OptIn(ExperimentalMaterial3Api::class)

package me.marthia.app.boomgrad.presentation.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import me.marthia.app.boomgrad.presentation.theme.BaseTheme

@Composable
fun TopBar(
    title: @Composable () -> Unit = {},
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {}, // required only if using generic navigationIcon
    navigationIcon: @Composable () -> Unit = {
        IconButton(onClick = onBackClick, shape = CircleShape, colors = IconButtonDefaults.filledIconButtonColors(containerColor = BaseTheme.colors.iconInteractive)) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
        }
    },
    actions: @Composable RowScope.() -> Unit = {},
    expandedHeight: Dp = TopAppBarDefaults.TopAppBarExpandedHeight,
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = Color.Transparent,
        scrolledContainerColor = Color.Transparent,
        navigationIconContentColor = BaseTheme.colors.iconSecondary,
        titleContentColor = BaseTheme.colors.textSecondary,
        actionIconContentColor = BaseTheme.colors.iconSecondary,
        subtitleContentColor = BaseTheme.colors.textHelp,
    ),
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    TopAppBar(
        title = title,
        modifier = modifier,
        navigationIcon = navigationIcon,
        actions = actions,
        expandedHeight = expandedHeight,
        windowInsets = windowInsets,
        colors = colors,
        scrollBehavior = scrollBehavior,
    )
}