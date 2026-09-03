package io.github.codewitheswar.frogui.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = FrogUiWhite,
    onPrimary = FrogUiBlack,
    primaryContainer = Zinc800,
    onPrimaryContainer = FrogUiWhite,
    secondary = Zinc400,
    onSecondary = FrogUiBlack,
    background = Zinc950,
    onBackground = Zinc100,
    surface = Zinc900,
    onSurface = Zinc100,
    surfaceVariant = Zinc800,
    onSurfaceVariant = Zinc400,
    outline = Zinc700
)

private val LightColorScheme = lightColorScheme(
    primary = FrogUiBlack,
    onPrimary = FrogUiWhite,
    primaryContainer = Zinc200,
    onPrimaryContainer = FrogUiBlack,
    secondary = Zinc600,
    onSecondary = FrogUiWhite,
    background = FrogUiWhite,
    onBackground = Zinc950,
    surface = Zinc50,
    onSurface = Zinc950,
    surfaceVariant = Zinc100,
    onSurfaceVariant = Zinc600,
    outline = Zinc300
)

@Composable
fun FrogUITheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color support on Android 12+ (defaults to false to preserve strict monochrome FrogUI branding)
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