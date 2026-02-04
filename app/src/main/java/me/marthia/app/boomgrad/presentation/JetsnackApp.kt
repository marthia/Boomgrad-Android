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

@file:OptIn(
    ExperimentalSharedTransitionApi::class,
)

package me.marthia.app.boomgrad.presentation

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import me.marthia.app.boomgrad.presentation.attraction.detail.AttractionDetailScreen
import me.marthia.app.boomgrad.presentation.components.AppScaffold
import me.marthia.app.boomgrad.presentation.components.SnackBarElement
import me.marthia.app.boomgrad.presentation.components.rememberJetsnackScaffoldState
import me.marthia.app.boomgrad.presentation.home.AppBottomBar
import me.marthia.app.boomgrad.presentation.login.LoginScreen
import me.marthia.app.boomgrad.presentation.navigation.HomeSections
import me.marthia.app.boomgrad.presentation.navigation.MainDestinations
import me.marthia.app.boomgrad.presentation.navigation.Routes
import me.marthia.app.boomgrad.presentation.navigation.addHomeGraph
import me.marthia.app.boomgrad.presentation.navigation.composableWithCompositionLocal
import me.marthia.app.boomgrad.presentation.navigation.rememberJetsnackNavController
import me.marthia.app.boomgrad.presentation.theme.AppTheme
import me.marthia.app.boomgrad.presentation.tour.detail.TourDetail

@Preview
@Composable
fun JetsnackApp() {
    AppTheme {
        val jetsnackNavController = rememberJetsnackNavController()
        SharedTransitionLayout {
            CompositionLocalProvider(
                LocalSharedTransitionScope provides this,
            ) {
                NavHost(
                    navController = jetsnackNavController.navController,
                    startDestination = MainDestinations.HOME_ROUTE,
                ) {
                    composableWithCompositionLocal(
                        route = MainDestinations.HOME_ROUTE,
                    ) { backStackEntry ->
                        MainContainer(
                            onSnackSelected = jetsnackNavController::navigateToSnackDetail,
                            onTourSelected = { _, _ -> jetsnackNavController.navigateToLogin() },
                            onAttractionSelected = jetsnackNavController::navigateToAttraction,
                        )
                    }

                    composableWithCompositionLocal(
                        "${MainDestinations.TOUR_DETAIL_ROUTE}/" +
                                "{${MainDestinations.TOUR_ID_KEY}}",
                        arguments = listOf(
                            navArgument(MainDestinations.TOUR_ID_KEY) {
                                type = NavType.LongType
                            },
                        ),

                        ) { backStackEntry ->
                        val arguments = requireNotNull(backStackEntry.arguments)
                        val tourId = arguments.getLong(MainDestinations.TOUR_ID_KEY)
                        TourDetail(
                            tourId = tourId,
                            upPress = jetsnackNavController::upPress,
                        )
                    }

                    composableWithCompositionLocal(
                        "${MainDestinations.ATTRACTION_DETAIL_ROUTE}/" +
                                "{${MainDestinations.ATTRACTION_ID_KEY}}",
                        arguments = listOf(
                            navArgument(MainDestinations.ATTRACTION_ID_KEY) {
                                type = NavType.StringType
                            },
                        ),

                        ) { backStackEntry ->
                        val arguments = requireNotNull(backStackEntry.arguments)
                        val attractionId = arguments.getLong(MainDestinations.TOUR_ID_KEY)
                        AttractionDetailScreen(
                            attractionId = "$attractionId",
                            onBackClick = jetsnackNavController::upPress,
                        )
                    }

                    composable(Routes.LOGIN) {
                        LoginScreen(jetsnackNavController::upPress)
                    }
                }
            }
        }
    }
}

@Composable
fun MainContainer(
    modifier: Modifier = Modifier,
    onSnackSelected: (Long, String, NavBackStackEntry) -> Unit,
    onTourSelected: (Long, NavBackStackEntry) -> Unit,
    onAttractionSelected: (Long, NavBackStackEntry) -> Unit,
) {
    val jetsnackScaffoldState = rememberJetsnackScaffoldState()
    val nestedNavController = rememberJetsnackNavController()
    val navBackStackEntry by nestedNavController.navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val sharedTransitionScope = LocalSharedTransitionScope.current
        ?: throw IllegalStateException("No SharedElementScope found")
    val animatedVisibilityScope = LocalNavAnimatedVisibilityScope.current
        ?: throw IllegalStateException("No SharedElementScope found")
    AppScaffold(
        bottomBar = {
            with(animatedVisibilityScope) {
                with(sharedTransitionScope) {
                    AppBottomBar(
                        tabs = HomeSections.entries.toTypedArray(),
                        currentRoute = currentRoute ?: HomeSections.FEED.route,
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
        snackBarHostState = jetsnackScaffoldState.snackBarHostState,
    ) { padding ->
        NavHost(
            navController = nestedNavController.navController,
            startDestination = HomeSections.FEED.route,
        ) {
            addHomeGraph(
                onSnackSelected = onSnackSelected,
                onTourSelected = onTourSelected,
                onAttractionSelected = onAttractionSelected,
                modifier = Modifier
                    .padding(padding)
                    .consumeWindowInsets(padding),
            )

        }
    }
}

val LocalNavAnimatedVisibilityScope = compositionLocalOf<AnimatedVisibilityScope?> { null }
val LocalSharedTransitionScope = compositionLocalOf<SharedTransitionScope?> { null }
