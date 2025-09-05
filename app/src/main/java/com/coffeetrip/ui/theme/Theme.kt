package com.coffeetrip.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.coffeetrip.ui.theme.CoffeeTripColors

// Los colores ahora están definidos en Color.kt

private val DarkColorScheme = darkColorScheme(
    primary = CoffeeTripColors.accentBrown,
    secondary = CoffeeTripColors.beige,
    tertiary = CoffeeTripColors.textSecondary,
    background = CoffeeTripColors.bgBase,
    surface = CoffeeTripColors.bgSurface,
    onPrimary = CoffeeTripColors.textPrimary,
    onSecondary = CoffeeTripColors.activeBrown,
    onTertiary = CoffeeTripColors.textPrimary,
    onBackground = CoffeeTripColors.textPrimary,
    onSurface = CoffeeTripColors.textPrimary,
)

private val LightColorScheme = lightColorScheme(
    primary = CoffeeTripColors.accentBrown,
    secondary = CoffeeTripColors.beige,
    tertiary = CoffeeTripColors.darkGray,
    background = CoffeeTripColors.navbarBg,
    surface = CoffeeTripColors.navbarBg,
    onPrimary = CoffeeTripColors.textPrimary,
    onSecondary = CoffeeTripColors.activeBrown,
    onTertiary = CoffeeTripColors.textPrimary,
    onBackground = CoffeeTripColors.bgBase,
    onSurface = CoffeeTripColors.bgBase,
)

@Composable
fun CoffeeTripTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
