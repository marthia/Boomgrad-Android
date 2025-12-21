package me.marthia.app.boomgrad.presentation.util


import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    navController: NavController,
    actions: (@Composable RowScope.() -> Unit)? = null,
    title: String,
    subtitle: String? = null,
    drawerState: DrawerState? = null,
    hasBackButton: Boolean = true
) {

    val coroutineScope = rememberCoroutineScope()
    val isTopLevel = navController.previousBackStackEntry != null
    val focusManager = LocalFocusManager.current

    TopAppBar(
        modifier = Modifier/*.height(AppDimens.topBarHeight)*/,
        colors = topAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
        title = {
            if (isTopLevel) {
                Text(text = title, color = MaterialTheme.colorScheme.onPrimary)
            }
            if (subtitle != null) {
                Text(text = subtitle, color = MaterialTheme.colorScheme.onPrimary)
            }
        },
        navigationIcon =
            {
                if (isTopLevel) {
                    if (hasBackButton) {
                        Button(onClick = {
                            focusManager.clearFocus()
                            navController.navigateUp()
                        }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                modifier = Modifier,
                                tint = MaterialTheme.colorScheme.onPrimary,
                                contentDescription = null
                            )
                        }
                    }
                } else {
                    Button(onClick = {
                        coroutineScope.launch {
                            drawerState?.open()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            tint = MaterialTheme.colorScheme.onPrimary,
                            contentDescription = null
                        )
                    }
                }
            },
        actions = actions ?: {}
    )
}

@Composable
fun TopBar(
    navController: NavController,
    actions: @Composable RowScope.() -> Unit = {},
    title: Int,
    drawerState: DrawerState? = null
) {
    TopBar(
        navController = navController,
        actions = actions,
        title = stringResource(id = title),
        drawerState = drawerState
    )
}