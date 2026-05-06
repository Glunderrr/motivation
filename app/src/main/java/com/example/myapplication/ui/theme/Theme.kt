package com.example.myapplication.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = Emerald40,
    onPrimary = Color.White,
    primaryContainer = Emerald90,
    onPrimaryContainer = Emerald10,
    secondary = Slate40,
    onSecondary = Color.White,
    secondaryContainer = Slate90,
    onSecondaryContainer = Slate10,
    tertiary = Sky40,
    onTertiary = Color.White,
    tertiaryContainer = Sky90,
    onTertiaryContainer = Sky20,
    background = Color(0xFFF8FAFC),
    onBackground = Slate10,
    surface = Color(0xFFF8FAFC),
    onSurface = Slate10,
    surfaceVariant = Slate90,
    onSurfaceVariant = Slate30,
    outline = Slate50,
    outlineVariant = Slate80,
)

private val DarkColorScheme = darkColorScheme(
    primary = Emerald80,
    onPrimary = Emerald20,
    primaryContainer = Emerald30,
    onPrimaryContainer = Emerald90,
    secondary = Slate80,
    onSecondary = Slate20,
    secondaryContainer = Slate30,
    onSecondaryContainer = Slate90,
    tertiary = Sky80,
    onTertiary = Sky20,
    tertiaryContainer = Sky30,
    onTertiaryContainer = Sky90,
    background = Slate10,
    onBackground = Slate90,
    surface = Slate10,
    onSurface = Slate90,
    surfaceVariant = Slate20,
    onSurfaceVariant = Slate80,
    outline = Slate50,
    outlineVariant = Slate30,
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

val MaterialTheme.textFieldColors: TextFieldColors
    @Composable
    get() = OutlinedTextFieldDefaults.colors(
        focusedTextColor = colorScheme.onSurface,
        unfocusedTextColor = colorScheme.onSurface,
        disabledTextColor = colorScheme.onSurface,
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        disabledContainerColor = Color.Transparent,
        focusedLabelColor = colorScheme.primary,
        unfocusedLabelColor = colorScheme.primary,
        disabledLabelColor = colorScheme.primary,
        focusedTrailingIconColor = colorScheme.primary,
        unfocusedTrailingIconColor = colorScheme.onSurfaceVariant,
        disabledTrailingIconColor = colorScheme.onSurfaceVariant,
        focusedBorderColor = colorScheme.primary,
        unfocusedBorderColor = colorScheme.outline,
        disabledBorderColor = colorScheme.outline,
        errorBorderColor = colorScheme.error,
        unfocusedSupportingTextColor = colorScheme.onSurfaceVariant,
        focusedSupportingTextColor = colorScheme.onSurfaceVariant,
        disabledSupportingTextColor = colorScheme.onSurfaceVariant,
        errorSupportingTextColor = colorScheme.error
    )
