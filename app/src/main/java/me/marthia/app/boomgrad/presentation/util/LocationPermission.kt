@file:OptIn(ExperimentalPermissionsApi::class)

package me.marthia.app.boomgrad.presentation.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@Composable
fun LocationPermission(
    showRational: () -> Unit = {},
    onGranted: () -> Unit = {}
) {

    val permissionState = rememberPermissionState(
        Manifest.permission.ACCESS_FINE_LOCATION,
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

// check if location permissions are granted
fun Context.hasLocationPermissions(): Boolean {
    return ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}