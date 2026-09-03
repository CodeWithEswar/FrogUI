package io.github.codewitheswar.frogui.foundation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import io.github.codewitheswar.frogui.foundation.color.FrogColors
import io.github.codewitheswar.frogui.foundation.color.FrogPalette
import io.github.codewitheswar.frogui.foundation.elevation.FrogElevation
import io.github.codewitheswar.frogui.foundation.motion.FrogMotion
import io.github.codewitheswar.frogui.foundation.shape.FrogShapes
import io.github.codewitheswar.frogui.foundation.spacing.FrogSpacing
import io.github.codewitheswar.frogui.foundation.typography.FrogTypography

val LocalFrogColors = staticCompositionLocalOf { FrogThemeDefaults.darkColors() }
val LocalFrogTypography = staticCompositionLocalOf { FrogTypography() }
val LocalFrogShapes = staticCompositionLocalOf { FrogShapes() }
val LocalFrogSpacing = staticCompositionLocalOf { FrogSpacing() }
val LocalFrogElevation = staticCompositionLocalOf { FrogElevation() }
val LocalFrogMotion = staticCompositionLocalOf { FrogMotion() }

/**
 * Access points for the active FrogUI theme design tokens.
 */
object FrogTheme {
    val colors: FrogColors
        @Composable
        @ReadOnlyComposable
        get() = LocalFrogColors.current

    val typography: FrogTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalFrogTypography.current

    val shapes: FrogShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalFrogShapes.current

    val spacing: FrogSpacing
        @Composable
        @ReadOnlyComposable
        get() = LocalFrogSpacing.current

    val elevation: FrogElevation
        @Composable
        @ReadOnlyComposable
        get() = LocalFrogElevation.current

    val motion: FrogMotion
        @Composable
        @ReadOnlyComposable
        get() = LocalFrogMotion.current
}

/**
 * Main theme entry point for FrogUI.
 * Provides custom FrogUI tokens through CompositionLocals while bridging
 * with underlying Android/Material primitives for accessibility and platform behavior.
 */
@Composable
fun FrogTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    colors: FrogColors = if (darkTheme) FrogThemeDefaults.darkColors() else FrogThemeDefaults.lightColors(),
    typography: FrogTypography = FrogTypography(),
    shapes: FrogShapes = FrogShapes(),
    spacing: FrogSpacing = FrogSpacing(),
    elevation: FrogElevation = FrogElevation(),
    motion: FrogMotion = FrogMotion(),
    content: @Composable () -> Unit
) {
    // Bridge internal MaterialTheme colorScheme for platform primitives (IME, selection, semantics)
    val materialColorScheme = if (colors.isDark) {
        darkColorScheme(
            primary = colors.primary,
            onPrimary = colors.primaryForeground,
            secondary = colors.secondary,
            onSecondary = colors.secondaryForeground,
            background = colors.background,
            onBackground = colors.foreground,
            surface = colors.surface,
            onSurface = colors.foreground,
            outline = colors.borderStrong
        )
    } else {
        lightColorScheme(
            primary = colors.primary,
            onPrimary = colors.primaryForeground,
            secondary = colors.secondary,
            onSecondary = colors.secondaryForeground,
            background = colors.background,
            onBackground = colors.foreground,
            surface = colors.surface,
            onSurface = colors.foreground,
            outline = colors.borderStrong
        )
    }

    CompositionLocalProvider(
        LocalFrogColors provides colors,
        LocalFrogTypography provides typography,
        LocalFrogShapes provides shapes,
        LocalFrogSpacing provides spacing,
        LocalFrogElevation provides elevation,
        LocalFrogMotion provides motion
    ) {
        MaterialTheme(
            colorScheme = materialColorScheme,
            content = content
        )
    }
}
