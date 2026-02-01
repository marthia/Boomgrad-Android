package me.marthia.app.boomgrad.presentation.category

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import me.marthia.app.boomgrad.R
import me.marthia.app.boomgrad.presentation.components.IconText
import me.marthia.app.boomgrad.presentation.components.JetsnackSurface
import me.marthia.app.boomgrad.presentation.theme.BaseTheme

@Composable
fun CategoryTag(modifier: Modifier = Modifier, title: String, icon: Int = R.drawable.icon_leaf_16) {

    JetsnackSurface(
        modifier = modifier,
        shape = MaterialTheme.shapes.extraLarge,
        color = BaseTheme.colors.uiContainer,
    ) {
        IconText(
            modifier = Modifier.padding(8.dp),
            leadingIcon = {
                Icon(
                    painter = painterResource(icon),
                    tint = Color.Unspecified,
                    contentDescription = "featured",
                )
            },
            text = {
                Text(
                    text = title,
                    color = BaseTheme.colors.brand,
                    style = MaterialTheme.typography.labelMedium
                )
            },
        )
    }
}