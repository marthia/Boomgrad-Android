package me.marthia.app.boomgrad.presentation.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
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
import me.marthia.app.boomgrad.presentation.components.DropDownPicker
import me.marthia.app.boomgrad.presentation.components.JetSnackBackground
import me.marthia.app.boomgrad.presentation.components.JetsnackCard
import me.marthia.app.boomgrad.presentation.components.JetsnackSurface
import me.marthia.app.boomgrad.presentation.components.JetsnackTextField
import me.marthia.app.boomgrad.presentation.home.model.Filter
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

                DropDownPicker(label = {
                    Text("شهر")
                }, placeholder = {
                    Text("انتخاب شهر")
                }, items = listOf())


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