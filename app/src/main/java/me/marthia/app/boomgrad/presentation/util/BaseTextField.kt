package me.marthia.app.boomgrad.presentation.util

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import me.marthia.app.boomgrad.presentation.theme.AppTheme


@Composable
fun BaseTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeHolderString: String = "",
    title: String = "",
    enabled: Boolean = true,
    readOnly: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val onPrimaryColor = MaterialTheme.colorScheme.onPrimary
    val onBackground = MaterialTheme.colorScheme.onBackground
    val onSurfaceLowOpacity = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5F)
    val containerColor = Color.Transparent
    val borderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3F)


    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .size(56.dp)
            .padding(0.dp),
        enabled = enabled,
        readOnly = readOnly,
        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center).merge(
            MaterialTheme.typography.bodyMedium
        ),
        label = null,
        placeholder = {
            Text(
                text = placeHolderString,
                style = MaterialTheme.typography.labelSmall.copy(),
            )
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = true,
        shape = RoundedCornerShape(8.dp),

        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = primaryColor,
            unfocusedContainerColor = containerColor,
            disabledContainerColor = containerColor,

            focusedTextColor = onPrimaryColor,
            unfocusedTextColor = onBackground,
            disabledTextColor = onBackground,

            cursorColor = onPrimaryColor.copy(alpha = 0.7F),

            focusedBorderColor = borderColor,
            disabledBorderColor = borderColor,
            unfocusedBorderColor = borderColor,

            focusedPlaceholderColor = onPrimaryColor.copy(alpha = 0.3F),
            unfocusedPlaceholderColor = onSurfaceLowOpacity,
            disabledPlaceholderColor = onSurfaceLowOpacity,
        )
    )
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
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                shape = RoundedCornerShape(4.dp)
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


@Composable
fun TaninVahyTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
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
    label: @Composable (() -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {

    val primaryColor = MaterialTheme.colorScheme.primary
    val onContainer = MaterialTheme.colorScheme.onSurface
    val onContainerLowOpacity = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5F)
    val containerColor = Color.Transparent
    val borderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3F)

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            fontWeight = FontWeight.Normal,
            textDirection = TextDirection.Content
        ),
        label = label,
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
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = containerColor,
            unfocusedContainerColor = containerColor,
            disabledContainerColor = containerColor,

            focusedTextColor = onContainer,
            unfocusedTextColor = onContainer,
            disabledTextColor = onContainer,

            cursorColor = primaryColor,

            focusedBorderColor = primaryColor,
            unfocusedBorderColor = borderColor,
            disabledBorderColor = borderColor,

            focusedPlaceholderColor = onContainerLowOpacity,
            unfocusedPlaceholderColor = onContainerLowOpacity,
            disabledPlaceholderColor = onContainerLowOpacity,

            focusedLeadingIconColor = onContainer,
            unfocusedLeadingIconColor = onContainer,
            disabledLeadingIconColor = onContainer,

            focusedTrailingIconColor = onContainer,
            unfocusedTrailingIconColor = onContainer,
            disabledTrailingIconColor = onContainer
        )
    )
}

@Composable
fun TaninVahyTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
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
    label: @Composable (() -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {

    val primaryColor = MaterialTheme.colorScheme.primary
    val onContainer = MaterialTheme.colorScheme.onSurface
    val onContainerLowOpacity = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5F)
    val containerColor = Color.Transparent
    val borderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3F)

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            fontWeight = FontWeight.Normal,
            textDirection = TextDirection.Content
        ),
        label = label,
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
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = containerColor,
            unfocusedContainerColor = containerColor,
            disabledContainerColor = containerColor,

            focusedTextColor = onContainer,
            unfocusedTextColor = onContainer,
            disabledTextColor = onContainer,

            cursorColor = primaryColor,

            focusedBorderColor = primaryColor,
            unfocusedBorderColor = borderColor,
            disabledBorderColor = borderColor,

            focusedPlaceholderColor = onContainerLowOpacity,
            unfocusedPlaceholderColor = onContainerLowOpacity,
            disabledPlaceholderColor = onContainerLowOpacity,

            focusedLeadingIconColor = onContainer,
            unfocusedLeadingIconColor = onContainer,
            disabledLeadingIconColor = onContainer,

            focusedTrailingIconColor = onContainer,
            unfocusedTrailingIconColor = onContainer,
            disabledTrailingIconColor = onContainer
        )
    )
}

@Composable
fun BaseFilledTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    title: @Composable (() -> Unit)? = null,
    label: @Composable (() -> Unit)? = null,
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
    containerColor: Color = MaterialTheme.colorScheme.surfaceContainer,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = MaterialTheme.shapes.small,
) {
    val onContainer = Color.Transparent

    Column() {

        title?.invoke()

        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier,
            enabled = enabled,
            readOnly = readOnly,
            textStyle = textStyle,
            label = label,
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
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = containerColor,
                unfocusedIndicatorColor = containerColor,
                disabledIndicatorColor = onContainer,
                errorIndicatorColor = onContainer,
            )
        )
    }
}

//@Composable
//fun PasswordTextField(
//    value: String,
//    onValueChange: (String) -> Unit,
//    modifier: Modifier = Modifier,
//    enabled: Boolean = true,
//    readOnly: Boolean = false,
//    textStyle: TextStyle = LocalTextStyle.current,
//    title: @Composable (() -> Unit)? = null,
//    label: @Composable (() -> Unit)? = null,
//    placeholder: @Composable (() -> Unit)? = null,
//    leadingIcon: @Composable (() -> Unit)? = null,
//    trailingIcon: @Composable (() -> Unit)? = null,
//    prefix: @Composable (() -> Unit)? = null,
//    suffix: @Composable (() -> Unit)? = null,
//    supportingText: @Composable (() -> Unit)? = null,
//    isError: Boolean = false,
//    visualTransformation: VisualTransformation = VisualTransformation.None,
//    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
//    keyboardActions: KeyboardActions = KeyboardActions.Default,
//    singleLine: Boolean = false,
//    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
//    minLines: Int = 1,
//    containerColor: Color = MaterialTheme.colorScheme.surfaceContainer,
//    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
//    shape: Shape = MaterialTheme.shapes.small,
//) {
//    val state = remember { TextFieldState() }
//    var showPassword by remember { mutableStateOf(false) }
//
//    val onContainer = Color.Transparent
//
//    Column() {
//
//        title?.invoke()
//
//        SecureTextField(
//            state = state,
//            textObfuscationMode =
//                if (showPassword) {
//                    TextObfuscationMode.Visible
//                } else {
//                    TextObfuscationMode.RevealLastTyped
//                },
//            value = value,
//            onValueChange = onValueChange,
//            modifier = modifier,
//            enabled = enabled,
//            readOnly = readOnly,
//            textStyle = textStyle,
//            label = label,
//            placeholder = placeholder,
//            leadingIcon = leadingIcon,
//            trailingIcon = trailingIcon,
//            prefix = prefix,
//            suffix = suffix,
//            supportingText = supportingText,
//            isError = isError,
//            visualTransformation = visualTransformation,
//            keyboardOptions = keyboardOptions,
//            keyboardActions = keyboardActions,
//            singleLine = singleLine,
//            maxLines = maxLines,
//            minLines = minLines,
//            interactionSource = interactionSource,
//            shape = shape,
//            colors = TextFieldDefaults.colors(
//                focusedIndicatorColor = containerColor,
//                unfocusedIndicatorColor = containerColor,
//                disabledIndicatorColor = onContainer,
//                errorIndicatorColor = onContainer,
//            )
//            decorator = { innerTextField ->
//                Box(modifier = Modifier.fillMaxWidth()) {
//                    Box(
//                        modifier = Modifier
//                            .align(Alignment.CenterStart)
//                            .padding(start = 16.dp, end = 48.dp)
//                    ) {
//                        innerTextField()
//                    }
//                    Icon(
//                        if (showPassword) {
//                            Icons.Filled.Visibility
//                        } else {
//                            Icons.Filled.VisibilityOff
//                        },
//                        contentDescription = "Toggle password visibility",
//                        modifier = Modifier
//                            .align(Alignment.CenterEnd)
//                            .requiredSize(48.dp)
//                            .padding(16.dp)
//                            .clickable { showPassword = !showPassword }
//                    )
//                }
//            }
//
//        )
//    }
//}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun Preview() {
    AppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp), contentAlignment = Alignment.Center
        ) {

            BaseFilledTextField(value = "", supportingText = {
                Text("Support Text")
            }, placeholder = {
                Text("Placeholder")
            }, label = {
                Text("Label")
            }, onValueChange = {

            })
        }
    }
}