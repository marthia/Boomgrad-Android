package me.marthia.app.boomgrad.presentation.profile

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import me.marthia.app.boomgrad.presentation.components.JetSnackBackground
import me.marthia.app.boomgrad.presentation.components.AppScaffold
import me.marthia.app.boomgrad.presentation.theme.AppTheme

@Composable
fun ProfileScreen() {
    AppScaffold {}

}


@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {


}


@Preview("default", showBackground = true, showSystemUi = true)
//@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
//@Preview("large font", fontScale = 2f)
@Composable
private fun PreviewProfile() {
    AppTheme {

        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {

            JetSnackBackground(modifier = Modifier.fillMaxSize()) {
                ProfileScreen(modifier = Modifier.systemBarsPadding())
            }
        }
    }
}