package me.marthia.app.boomgrad.presentation.tour.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import me.marthia.app.boomgrad.presentation.components.JetSnackBackground
import me.marthia.app.boomgrad.presentation.components.JetsnackCard
import me.marthia.app.boomgrad.presentation.components.JetsnackTextField
import me.marthia.app.boomgrad.presentation.theme.AppTheme
import me.marthia.app.boomgrad.presentation.theme.BaseTheme

@Composable
fun CreateEditTourScreen() {

    CreateEditTour()
}

@Composable
fun CreateEditTour(modifier: Modifier = Modifier) {
    val expanded = remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        JetsnackCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(
                    text = "اطلاعات پایه",
                    style = MaterialTheme.typography.titleMedium,
                    color = BaseTheme.colors.textSecondary
                )

                JetsnackTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = "",
                    label = {
                        Text(
                            text = "عنوان تور",
                            style = MaterialTheme.typography.bodyMedium,
                            color = BaseTheme.colors.textSecondary
                        )
                    },
                    placeholder = {
                        Text("عنوان مورد نظر خود را وارد کنید")
                    },
                    onValueChange = {},
                )
                JetsnackTextField(
                    modifier = Modifier.fillMaxWidth(), value = "",
                    label = {
                        Text(
                            text = "زیر عنوان",
                            style = MaterialTheme.typography.bodyMedium,
                            color = BaseTheme.colors.textSecondary
                        )
                    },
                    placeholder = {
                        Text("متن مورد نظر خود را وارد کنید")
                    },
                    onValueChange = {},
                )

                var expanded by remember { mutableStateOf(false) }
                JetsnackTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {

                        },
                    label = {
                        Text(
                            text = "شهر",
                            style = MaterialTheme.typography.bodyMedium,
                            color = BaseTheme.colors.textSecondary
                        )
                    },
                    value = "متن مورد نظر خود را وارد کنید",
                    readOnly = true,
                    trailingIcon = {
                        Icon(
                            imageVector = if (expanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                            contentDescription = null
                        )
                    },
                    onValueChange = {},
                )


//                Row(modifier = Modifier.fillMaxWidth()) {
//                    CitySelection(modifier = Modifier.weight(1f))
//                    Spacer(Modifier.width(16.dp))
//                    CitySelection(modifier = Modifier.weight(1f))
//                }

            }
        }
    }
}


@Preview("default", showBackground = true, showSystemUi = true)
@Composable
private fun PreviewCreateEditTour() {
    AppTheme {

        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {

            JetSnackBackground(modifier = Modifier.fillMaxSize()) {
                CreateEditTour(modifier = Modifier.systemBarsPadding())
            }
        }
    }
}