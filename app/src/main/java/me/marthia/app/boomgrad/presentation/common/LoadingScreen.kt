package me.marthia.app.boomgrad.presentation.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.marthia.app.boomgrad.presentation.components.BackgroundElement

@Composable
fun LoadingScreen() {

    BackgroundElement() {

        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator()
        }
    }
}