package me.marthia.app.boomgrad.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import me.marthia.app.boomgrad.presentation.theme.AppTheme
import me.marthia.app.boomgrad.presentation.theme.Theme

@Composable
fun TextFieldElement(

    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable () -> Unit,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource? = null,
    shape: Shape = MaterialTheme.shapes.medium,
    colors: TextFieldColors = TextFieldDefaults.colors(
        focusedTextColor = Theme.colors.materialTheme.surfaceDim,
        unfocusedTextColor = Theme.colors.textHelp,
        disabledTextColor = Theme.colors.textHelp,
        errorTextColor = Theme.colors.textHelp,
        focusedContainerColor = Theme.colors.materialTheme.secondaryContainer,
        unfocusedContainerColor = Theme.colors.materialTheme.secondaryContainer,
        disabledContainerColor = Theme.colors.materialTheme.secondaryContainer,
        errorContainerColor = Theme.colors.textHelp,
        cursorColor = Theme.colors.textHelp,
        errorCursorColor = Theme.colors.textHelp,
        selectionColors = null,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Theme.colors.textHelp,
        errorIndicatorColor = Theme.colors.textHelp,
        focusedLeadingIconColor = Theme.colors.textHelp,
        unfocusedLeadingIconColor = Theme.colors.textHelp,
        disabledLeadingIconColor = Theme.colors.textHelp,
        errorLeadingIconColor = Theme.colors.textHelp,
        focusedTrailingIconColor = Theme.colors.textHelp,
        unfocusedTrailingIconColor = Theme.colors.textHelp,
        disabledTrailingIconColor = Theme.colors.textHelp,
        errorTrailingIconColor = Theme.colors.textHelp,
        focusedLabelColor = Theme.colors.textHelp,
        unfocusedLabelColor = Theme.colors.textHelp,
        disabledLabelColor = Theme.colors.textHelp,
        errorLabelColor = Theme.colors.textHelp,
        focusedPlaceholderColor = Theme.colors.materialTheme.surfaceDim,
        unfocusedPlaceholderColor = Theme.colors.textHelp,
        disabledPlaceholderColor = Theme.colors.textHelp,
        errorPlaceholderColor = Theme.colors.textHelp,
        focusedSupportingTextColor = Theme.colors.textHelp,
        unfocusedSupportingTextColor = Theme.colors.textHelp,
        disabledSupportingTextColor = Theme.colors.textHelp,
        errorSupportingTextColor = Theme.colors.textHelp,
        focusedPrefixColor = Theme.colors.textHelp,
        unfocusedPrefixColor = Theme.colors.textHelp,
        disabledPrefixColor = Theme.colors.textHelp,
        errorPrefixColor = Theme.colors.textHelp,
        focusedSuffixColor = Theme.colors.textHelp,
        unfocusedSuffixColor = Theme.colors.textHelp,
        disabledSuffixColor = Theme.colors.textHelp,
        errorSuffixColor = Theme.colors.textHelp,

        ),
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        label()
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            readOnly = readOnly,
            textStyle = textStyle,
            label = {},
            placeholder = placeholder,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            prefix = prefix,
            suffix = suffix,
            supportingText = supportingText,
            isError = isError,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = singleLine,
            maxLines = maxLines,
            minLines = minLines,
            interactionSource = interactionSource,
            shape = shape,
            colors = colors,
        )
    }
}

@Composable
fun DecoratedTextField(
    value: String,
    length: Int,
    modifier: Modifier = Modifier,
    boxWidth: Dp = 48.dp,
    boxHeight: Dp = 48.dp,
    enabled: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    keyboardActions: KeyboardActions = KeyboardActions(),
    color: Color = Theme.colors.materialTheme.surface,
    shape: Shape = RoundedCornerShape(4.dp),
    onValueChange: (String) -> Unit,
) {
    val spaceBetweenBoxes = 8.dp
    BasicTextField(
        modifier = modifier,
        value = value,
        singleLine = true,
        onValueChange = {
            if (it.length <= length) {
                onValueChange(it)
            }
        },
        enabled = enabled,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        decorationBox = {
            Row(
                Modifier.size(width = (boxWidth + spaceBetweenBoxes) * length, height = boxHeight),
                horizontalArrangement = Arrangement.spacedBy(spaceBetweenBoxes),
            ) {
                repeat(length) { index ->
                    Box(
                        modifier = Modifier
                            .size(boxWidth, boxHeight)
                            .background(
                                color = color,
                                shape = shape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = value.getOrNull(index)?.toString() ?: "",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        })
}


@Preview("default", showBackground = true, showSystemUi = true)
@Preview(
    "dark theme",
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview("large font", showBackground = true, showSystemUi = true, fontScale = 2f)
@Composable
private fun PreviewTextField() {
    AppTheme() {
        TextFieldElement(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),

            label = { Text("عنوان تور") },
            placeholder = {
                Text("عنوان مورد نظر خود را وارد کنید")
            },
            value = "", onValueChange = {},
        )
    }
}