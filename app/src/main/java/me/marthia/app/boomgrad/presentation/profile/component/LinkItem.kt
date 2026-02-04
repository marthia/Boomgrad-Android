package me.marthia.app.boomgrad.presentation.profile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import me.marthia.app.boomgrad.R
import me.marthia.app.boomgrad.presentation.components.BackgroundElement
import me.marthia.app.boomgrad.presentation.components.SurfaceElement
import me.marthia.app.boomgrad.presentation.theme.AppTheme
import me.marthia.app.boomgrad.presentation.theme.Theme

@Composable
fun LinkItem(
    modifier: Modifier = Modifier,
    label: String,
    icon: Painter,
    navigateIconVisible: Boolean = true,
    textColor: Color = Color.Unspecified,
    iconContainerColor: Color = Theme.colors.materialTheme.primaryContainer,
    iconTint: Color = Theme.colors.materialTheme.primary,
    containerColor: Color = Theme.colors.materialTheme.surface,
    shape: Shape = RoundedCornerShape(50)
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(color = containerColor, shape = shape),

        verticalAlignment = Alignment.CenterVertically,

        ) {

        SurfaceElement(
            modifier = Modifier
                .padding(top = 2.dp, start = 2.dp, bottom = 2.dp)
                .size(48.dp),
            shape = CircleShape,
            color = iconContainerColor
        ) {
            Icon(
                modifier = Modifier.padding(12.dp),
                painter = icon,
                tint = iconTint,
                contentDescription = label
            )
        }

        Spacer(Modifier.width(8.dp))

        Text(text = label, color = textColor, style = MaterialTheme.typography.titleMedium)

        Spacer(Modifier.weight(1f))

        if (navigateIconVisible) {
            Icon(
                modifier = Modifier.padding(end = 16.dp),
                imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                contentDescription = "Navigate"
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun PreviewLinkItem() {
    AppTheme {

        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            BackgroundElement(modifier = Modifier.fillMaxSize()) {
                LinkItem(
                    modifier = Modifier.padding(top = 45.dp),
                    label = stringResource(R.string.label_profile_edit),
                    icon = painterResource(R.drawable.icon_user_edit_24)
                )
            }
        }
    }
}