package me.marthia.app.boomgrad.presentation.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import me.marthia.app.boomgrad.presentation.components.JetSnackBackground
import me.marthia.app.boomgrad.presentation.components.JetsnackCard
import me.marthia.app.boomgrad.presentation.components.JetsnackSurface
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
                Text(text = "اطلاعات پایه", style = MaterialTheme.typography.titleMedium, color = BaseTheme.colors.textSecondary)

                JetsnackTextField(
                    modifier = Modifier.fillMaxWidth(), value = "", label = { Text("عنوان تور") },
                    placeholder = {
                        Text("عنوان مورد نظر خود را وارد کنید")
                    },
                    onValueChange = {},
                )
                JetsnackTextField(
                    modifier = Modifier.fillMaxWidth(), value = "", label = { Text("زیر عنوان") },
                    placeholder = {
                        Text("متن مورد نظر خود را وارد کنید")
                    },
                    onValueChange = {},
                )

                CitySelection()
                CitySelection()
                CitySelection()


                Row(modifier = Modifier.fillMaxWidth()) {
                    CitySelection(modifier = Modifier.weight(1f))
                    Spacer(Modifier.width(16.dp))
                    CitySelection(modifier = Modifier.weight(1f))
                }

            }
        }
    }
}


@Composable
fun DropDown(
    modifier: Modifier = Modifier,
    label: @Composable () -> Unit,
) {

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        label()
        JetsnackSurface(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            color = BaseTheme.colors.textField,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = "رده سنی", modifier = Modifier.padding(vertical = 16.dp))
                Icon(Icons.Rounded.ExpandMore, "Expand")
            }
        }
    }
}

@Composable
fun CitySelection(
    modifier: Modifier = Modifier,
    expanded: MutableState<Boolean> = remember { mutableStateOf(false) },
) {

    Box(modifier = modifier.fillMaxWidth()) {
        // Button to trigger the dropdown
        DropDown {
            Text("شهر")
        }
        // Dropdown menu
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            modifier = Modifier.fillMaxWidth(),
        ) {
            DropdownMenuItem(
                text = { Text("اصفهان") },
                onClick = {
                    // Handle selection
                    expanded.value = false
                },
            )
            DropdownMenuItem(
                text = { Text("تهران") },
                onClick = {
                    expanded.value = false
                },
            )
            DropdownMenuItem(
                text = { Text("شیراز") },
                onClick = {
                    expanded.value = false
                },
            )
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