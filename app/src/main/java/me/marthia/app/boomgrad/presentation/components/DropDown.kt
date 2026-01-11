package me.marthia.app.boomgrad.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.marthia.app.boomgrad.presentation.home.model.Filter
import me.marthia.app.boomgrad.presentation.theme.BaseTheme

@Composable
fun DropDown(
    modifier: Modifier = Modifier,
    label: @Composable () -> Unit = {},
    placeholder: @Composable () -> Unit = {},
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
                Spacer(Modifier.height(16.dp))
                placeholder()
                Icon(Icons.Rounded.ExpandMore, "Expand")
            }
        }
    }
}

@Composable
fun DropDownPicker(
    modifier: Modifier = Modifier,
    label: @Composable () -> Unit = {},
    placeholder: @Composable () -> Unit = {},
    items: List<Filter>,
    expanded: MutableState<Boolean> = remember { mutableStateOf(false) },
) {

    Box(modifier = modifier.fillMaxWidth()) {
        // Button to trigger the dropdown
        DropDown(label = {
            label()

        }, placeholder = {
            placeholder()
        })
        // Dropdown menu
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            modifier = Modifier.fillMaxWidth(),
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item.name) },
                    onClick = {
                        // Handle selection
                        expanded.value = false
                    },
                )
            }
        }
    }
}

@Composable
fun <T : Any> DropDownPicker(
    modifier: Modifier = Modifier,
    label: @Composable () -> Unit = {},
    placeholder: @Composable () -> Unit = {},
    items: List<T>,
    selectedItem: T? = null,
    enabled: Boolean = true,
    onItemSelected: (T) -> Unit,
    itemText: (T) -> String = { it.toString() }
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxWidth()) {
        // Button to trigger the dropdown
        JetsnackTextField(
            value = selectedItem?.let { itemText(it) } ?: "",
            onValueChange = {},
            readOnly = true,
            enabled = enabled,
            label = label,
            placeholder = placeholder,
            trailingIcon = {
                Icon(
                    imageVector = if (expanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                    contentDescription = null
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = enabled) { expanded = !expanded }
        )

        // Dropdown menu
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(itemText(item)) },
                    onClick = {
                        onItemSelected(item)
                        expanded = false
                    }
                )
            }
        }
    }
}
