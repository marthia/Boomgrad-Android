package me.marthia.app.boomgrad.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.marthia.app.boomgrad.presentation.theme.AppTheme
import me.marthia.app.boomgrad.presentation.theme.BaseTheme

@Composable
fun JetsnackTextField(

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
        focusedTextColor = BaseTheme.colors.textHelp,
        unfocusedTextColor = BaseTheme.colors.textHelp,
        disabledTextColor = BaseTheme.colors.textHelp,
        errorTextColor = BaseTheme.colors.textHelp,
        focusedContainerColor = BaseTheme.colors.uiContainer,
        unfocusedContainerColor = BaseTheme.colors.textField,
        disabledContainerColor = BaseTheme.colors.textHelp,
        errorContainerColor = BaseTheme.colors.textHelp,
        cursorColor = BaseTheme.colors.textHelp,
        errorCursorColor = BaseTheme.colors.textHelp,
        selectionColors = null,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = BaseTheme.colors.textHelp,
        errorIndicatorColor = BaseTheme.colors.textHelp,
        focusedLeadingIconColor = BaseTheme.colors.textHelp,
        unfocusedLeadingIconColor = BaseTheme.colors.textHelp,
        disabledLeadingIconColor = BaseTheme.colors.textHelp,
        errorLeadingIconColor = BaseTheme.colors.textHelp,
        focusedTrailingIconColor = BaseTheme.colors.textHelp,
        unfocusedTrailingIconColor = BaseTheme.colors.textHelp,
        disabledTrailingIconColor = BaseTheme.colors.textHelp,
        errorTrailingIconColor = BaseTheme.colors.textHelp,
        focusedLabelColor = BaseTheme.colors.textHelp,
        unfocusedLabelColor = BaseTheme.colors.textHelp,
        disabledLabelColor = BaseTheme.colors.textHelp,
        errorLabelColor = BaseTheme.colors.textHelp,
        focusedPlaceholderColor = BaseTheme.colors.textField,
        unfocusedPlaceholderColor = BaseTheme.colors.textHelp,
        disabledPlaceholderColor = BaseTheme.colors.textHelp,
        errorPlaceholderColor = BaseTheme.colors.textHelp,
        focusedSupportingTextColor = BaseTheme.colors.textHelp,
        unfocusedSupportingTextColor = BaseTheme.colors.textHelp,
        disabledSupportingTextColor = BaseTheme.colors.textHelp,
        errorSupportingTextColor = BaseTheme.colors.textHelp,
        focusedPrefixColor = BaseTheme.colors.textHelp,
        unfocusedPrefixColor = BaseTheme.colors.textHelp,
        disabledPrefixColor = BaseTheme.colors.textHelp,
        errorPrefixColor = BaseTheme.colors.textHelp,
        focusedSuffixColor = BaseTheme.colors.textHelp,
        unfocusedSuffixColor = BaseTheme.colors.textHelp,
        disabledSuffixColor = BaseTheme.colors.textHelp,
        errorSuffixColor = BaseTheme.colors.textHelp,

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


@Preview("default", showBackground = true, showSystemUi = true)
@Preview("dark theme", showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("large font", showBackground = true, showSystemUi = true, fontScale = 2f)
@Composable
private fun PreviewTextField() {
    AppTheme() {
        JetsnackTextField(
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