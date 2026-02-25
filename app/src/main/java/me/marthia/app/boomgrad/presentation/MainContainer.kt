@file:OptIn(ExperimentalSharedTransitionApi::class)

package me.marthia.app.boomgrad.presentation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import me.marthia.app.boomgrad.presentation.bottombar.AppBottomBar
import me.marthia.app.boomgrad.presentation.components.ScaffoldElement
import me.marthia.app.boomgrad.presentation.components.SnackBarElement
import me.marthia.app.boomgrad.presentation.components.rememberAppScaffoldState
import me.marthia.app.boomgrad.presentation.navigation.FeedDestination
import me.marthia.app.boomgrad.presentation.navigation.LocalNavAnimatedVisibilityScope
import me.marthia.app.boomgrad.presentation.navigation.LocalSharedTransitionScope
import me.marthia.app.boomgrad.presentation.navigation.addHomeGraph
import me.marthia.app.boomgrad.presentation.navigation.bottomBarDestinations
import me.marthia.app.boomgrad.presentation.navigation.rememberAppNavigator

@Composable
fun MainContainer(
    modifier: Modifier = Modifier,
    onNavigateToLogin: () -> Unit,
) {
    val scaffoldState = rememberAppScaffoldState()
    val nestedNavController = rememberAppNavigator()
    val navBackStackEntry by nestedNavController.navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val sharedTransitionScope = LocalSharedTransitionScope.current
        ?: throw IllegalStateException("No SharedElementScope found")
    val animatedVisibilityScope = LocalNavAnimatedVisibilityScope.current
        ?: throw IllegalStateException("No SharedElementScope found")
    ScaffoldElement(
        bottomBar = {
            with(animatedVisibilityScope) {
                with(sharedTransitionScope) {
                    AppBottomBar(
                        tabs = bottomBarDestinations,
                        currentRoute = currentRoute ?: FeedDestination.route,
                        navigateToRoute = nestedNavController::navigateToBottomBarRoute,
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, bottom = 24.dp)
                            .renderInSharedTransitionScopeOverlay(
                                zIndexInOverlay = 1f,
                            )
                            .animateEnterExit(
                                enter = fadeIn(nonSpatialExpressiveSpring()) + slideInVertically(
                                    spatialExpressiveSpring(),
                                ) {
                                    it
                                },
                                exit = fadeOut(nonSpatialExpressiveSpring()) + slideOutVertically(
                                    spatialExpressiveSpring(),
                                ) {
                                    it
                                },
                            ),
                    )
                }
            }
        },
        modifier = modifier,
        snackbarHost = {
            SnackbarHost(
                hostState = it,
                modifier = Modifier.systemBarsPadding(),
                snackbar = { snackbarData -> SnackBarElement(snackbarData) },
            )
        },
        snackBarHostState = scaffoldState.snackBarHostState,
    ) { padding ->
        NavHost(
            navController = nestedNavController.navController,
            startDestination = FeedDestination.route,
        ) {
            addHomeGraph(
                onNavigateToLogin = onNavigateToLogin,
            )

        }
    }
}