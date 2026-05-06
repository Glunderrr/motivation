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
    primary = Orange30,
    onPrimary = Color.White,
    primaryContainer = Orange90,
    onPrimaryContainer = Orange10,
    secondary = Amber40,
    onSecondary = Color.White,
    secondaryContainer = Amber90,
    onSecondaryContainer = Amber10,
    tertiary = Brown40,
    onTertiary = Color.White,
    tertiaryContainer = Brown90,
    onTertiaryContainer = Brown20,
    background = Color(0xFFFFF8F0),
    onBackground = WarmDark10,
    surface = Color(0xFFFFF8F0),
    onSurface = WarmDark10,
    surfaceVariant = WarmLight90,
    onSurfaceVariant = WarmDark30,
    surfaceTint = Orange30,
    surfaceContainerLowest = Color(0xFFFFF8F0),
    surfaceContainerLow = Color(0xFFF7EFE6),
    surfaceContainer = Color(0xFFF2E8DC),
    surfaceContainerHigh = Color(0xFFECE0D2),
    surfaceContainerHighest = Color(0xFFE5D8C8),
    outline = WarmMid50,
    outlineVariant = WarmLight80,
)

private val DarkColorScheme = darkColorScheme(
    primary = Orange80,
    onPrimary = Orange20,
    primaryContainer = Orange30,
    onPrimaryContainer = Orange90,
    secondary = Amber80,
    onSecondary = Amber20,
    secondaryContainer = Amber30,
    onSecondaryContainer = Amber90,
    tertiary = Brown80,
    onTertiary = Brown20,
    tertiaryContainer = Brown30,
    onTertiaryContainer = Brown90,
    background = WarmDark10,
    onBackground = WarmLight90,
    surface = WarmDark10,
    onSurface = WarmLight90,
    surfaceVariant = WarmDark20,
    onSurfaceVariant = WarmLight80,
    surfaceTint = Orange80,
    surfaceContainerLowest = Color(0xFF120600),
    surfaceContainerLow = WarmDark10,
    surfaceContainer = WarmDark20,
    surfaceContainerHigh = Color(0xFF3A1C00),
    surfaceContainerHighest = Color(0xFF472400),
    outline = WarmMid50,
    outlineVariant = WarmDark30,
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
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
