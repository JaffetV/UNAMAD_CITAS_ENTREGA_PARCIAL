package com.example.comunamadsistemacitas.core.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val UnamadColorScheme = lightColorScheme(
    primary = UnamadPalette.PrimaryGreen,
    onPrimary = UnamadPalette.BackgroundWhite,
    secondary = UnamadPalette.AccentGreen,
    onSecondary = UnamadPalette.BackgroundWhite,
    background = UnamadPalette.BackgroundWhite,
    onBackground = UnamadPalette.TextPrimary,
    surface = UnamadPalette.BackgroundWhite,
    onSurface = UnamadPalette.TextPrimary,
    surfaceVariant = UnamadPalette.LightGreenBg,
    onSurfaceVariant = UnamadPalette.TextSecondary,
    outline = UnamadPalette.BorderGray,
    error = UnamadPalette.RedCancel,
    onError = UnamadPalette.BackgroundWhite
)

@Composable
fun UnamadMaterialTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = UnamadColorScheme,
        typography = UnamadTypography,
        shapes = UnamadShapes,
        content = content
    )
}
