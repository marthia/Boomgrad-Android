package me.marthia.app.boomgrad.presentation.bottombar

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.os.ConfigurationCompat
import me.marthia.app.boomgrad.presentation.components.ContainerElement
import me.marthia.app.boomgrad.presentation.navigation.BottomBarDestination
import me.marthia.app.boomgrad.presentation.navigation.bottomBarDestinations
import me.marthia.app.boomgrad.presentation.spatialExpressiveSpring
import me.marthia.app.boomgrad.presentation.theme.AppTheme
import me.marthia.app.boomgrad.presentation.theme.Theme
import java.util.Locale

@Composable
fun AppBottomBar(
    tabs: List<BottomBarDestination>,
    currentRoute: String,
    navigateToRoute: (String) -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(50),
    gradient: List<Color> = Theme.colors.uiContainerGradient,
    contentColor: Color = Theme.colors.materialTheme.onPrimaryContainer,
) {
    val routes = remember { tabs.map { it.route } }

    // startsWith handles nested routes like "home/feed" matching "home"
    val currentIndex = tabs.indexOfFirst {
        currentRoute == it.route
    }
    val currentItem = tabs.getOrNull(currentIndex)

    ContainerElement(
        modifier = modifier,
        gradient = gradient,
        shape = shape,
        contentColor = contentColor,
    ) {
        val springSpec = spatialExpressiveSpring<Float>()

        AppBottomNavLayout(
            selectedIndex = currentIndex,
            itemCount = routes.size,
            indicator = { AppBottomNavIndicator() },
            animSpec = springSpec,
            modifier = Modifier.clip(shape = shape),
        ) {
            val configuration = LocalConfiguration.current
            val currentLocale: Locale =
                ConfigurationCompat.getLocales(configuration).get(0) ?: Locale.getDefault()

            tabs.forEach { section ->
                val selected = section == currentItem
                val text = stringResource(section.title).uppercase(currentLocale)

                AppBottomNavigationItem(
                    icon = {
                        AppBottomNavIcon(
                            section = section,
                            selected = selected,
                            text = text,
                        )
                    },
                    text = {
                        AppBottomNavLabel(
                            text = text,
                        )
                    },
                    selected = selected,
                    onSelected = { navigateToRoute(section.route) },
                    animSpec = springSpec,
                    modifier = BottomNavigationItemPadding.clip(BottomNavIndicatorShape),
                )
            }
        }
    }
}

@Preview(locale = "fa")
@Composable
private fun AppBottomNavPreview() {
    AppTheme {
        AppBottomBar(
            tabs = bottomBarDestinations,
            currentRoute = "home/feed",
            navigateToRoute = { },
        )
    }
}