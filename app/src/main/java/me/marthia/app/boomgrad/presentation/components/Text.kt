package me.marthia.app.boomgrad.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun IconText(
    modifier: Modifier = Modifier,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    horizontalArrangement: Arrangement.HorizontalOrVertical = Arrangement.spacedBy(8.dp),
    text: @Composable () -> Unit,
    leadingIcon: @Composable () -> Unit = {},
    trailingIcon: @Composable () -> Unit = {},
    onClick: (() -> Unit)? = null,
) {

    SurfaceElement(onClick = onClick, color = Color.Transparent) {
        Row(
            modifier = modifier.defaultMinSize(48.dp, 28.dp),
            verticalAlignment = verticalAlignment,
            horizontalArrangement = horizontalArrangement,
        ) {

            leadingIcon()
            text()
            trailingIcon()
        }
    }
}