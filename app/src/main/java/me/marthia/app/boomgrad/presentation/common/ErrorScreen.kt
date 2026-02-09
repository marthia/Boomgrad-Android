package me.marthia.app.boomgrad.presentation.common


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import me.marthia.app.boomgrad.R
import me.marthia.app.boomgrad.presentation.components.BackgroundElement
import me.marthia.app.boomgrad.presentation.components.ButtonElement

@Composable
fun ErrorScreen(onRetry: () -> Unit = {}, onBack: () -> Unit = {}) {

    BackgroundElement() {

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(model = R.drawable.ic_flag_94, contentDescription = "Error image")
                Text(text = stringResource(R.string.description_error))


                ButtonElement(onClick = onBack) {
                    Text(text = stringResource(R.string.label_error_return))
                }
            }
        }
    }
}