package com.example.coinview.presentation.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val backgroundColor = Color(0xfff5f5f5)
val backgroundColorDark = Color(0xff000000)
val textSurface = Color(0xff000000)
val textSurfaceDark = Color(0xffffffff)
val textSurfaceSecondary = Color(0xffbcbcbc)
val surfaceColor = Color(0xffffffff)
val surfaceColorDark = Color(0xff1e1e1e)
val onSurfaceColor = Color(0xff5fa6f8)


private val LightColorScheme = lightColorScheme(
    background = backgroundColor,
    surface = surfaceColor,
    onSurface = textSurface,
    surfaceTint = surfaceColor,
    onSurfaceVariant = textSurfaceSecondary,
)

private val DarkColorScheme = darkColorScheme(
    background = backgroundColorDark,
    surface = surfaceColorDark,
    onSurface = textSurfaceDark,
    surfaceTint = surfaceColorDark,
    onSurfaceVariant = textSurfaceSecondary
)

@Composable
fun CoinsViewTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}