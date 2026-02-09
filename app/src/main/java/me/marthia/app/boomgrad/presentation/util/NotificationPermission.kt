@file:OptIn(ExperimentalPermissionsApi::class)

package me.marthia.app.boomgrad.presentation.util

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun NotificationPermission(
    showRational: () -> Unit = {},
    onGranted: () -> Unit = {}
) {

    if (AndroidVersionUtils.atLeast(SDK.Android13)) {

        val permissionState = rememberPermissionState(
            Manifest.permission.POST_NOTIFICATIONS,
            onPermissionResult = { isGranted ->
                if (!isGranted) {
                    showRational()
                } else {
                    onGranted()
                }
            }
        )

        if (!permissionState.status.isGranted && permissionState.status.shouldShowRationale) {
            showRational()
        } else {
            // Request the permission
            LaunchedEffect(Unit) {
                permissionState.launchPermissionRequest()
            }
        }
    }
}