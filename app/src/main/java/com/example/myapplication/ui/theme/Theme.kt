package com.example.myapplication.ui.theme


import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    background = Color.White,

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}

val MaterialTheme.textFieldColors: TextFieldColors
    @Composable
    get() = OutlinedTextFieldDefaults.colors(
        // Text
        focusedTextColor = colorScheme.onBackground,
        unfocusedTextColor = colorScheme.onBackground,
        disabledTextColor = colorScheme.onBackground,

        // background
        focusedContainerColor = colorScheme.background,
        unfocusedContainerColor = colorScheme.background,
        disabledContainerColor = colorScheme.background,

        // Label
        focusedLabelColor = colorScheme.primary,
        unfocusedLabelColor = colorScheme.primary,
        disabledLabelColor = colorScheme.primary,

        // Icons
        focusedTrailingIconColor = colorScheme.primary,
        unfocusedTrailingIconColor = colorScheme.primary,
        disabledTrailingIconColor = colorScheme.primary,

        // Border
        focusedBorderColor = colorScheme.primary,
        unfocusedBorderColor = colorScheme.primary,
        disabledBorderColor = colorScheme.primary,
        errorBorderColor = colorScheme.error,

        unfocusedSupportingTextColor = colorScheme.primary,
        focusedSupportingTextColor = colorScheme.primary,
        disabledSupportingTextColor = colorScheme.primary,
        errorSupportingTextColor = colorScheme.error
    )