package io.github.codewitheswar.frogui.theme

import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import io.github.codewitheswar.frogui.foundation.color.FrogColors
import io.github.codewitheswar.frogui.foundation.shape.FrogShapes
import io.github.codewitheswar.frogui.foundation.typography.FrogTypography

/** Internal interoperability only: no Material palette defaults should color FrogUI primitives. */
internal fun FrogColors.toMaterialColors() = (if (isDark) darkColorScheme() else lightColorScheme()).copy(
    primary = primary, onPrimary = primaryForeground,
    primaryContainer = secondary, onPrimaryContainer = secondaryForeground,
    inversePrimary = primaryForeground,
    secondary = secondary, onSecondary = secondaryForeground,
    secondaryContainer = subtleSurface, onSecondaryContainer = foreground,
    tertiary = secondary, onTertiary = secondaryForeground,
    tertiaryContainer = subtleSurface, onTertiaryContainer = foreground,
    background = background, onBackground = foreground,
    surface = surface, onSurface = foreground,
    surfaceVariant = subtleSurface, onSurfaceVariant = mutedForeground,
    surfaceTint = Color.Transparent,
    inverseSurface = foreground, inverseOnSurface = background,
    error = destructive, onError = destructiveForeground,
    errorContainer = destructive, onErrorContainer = destructiveForeground,
    outline = borderStrong, outlineVariant = border,
    scrim = Color.Black,
    surfaceBright = surfaceElevated, surfaceDim = background,
    surfaceContainer = surface, surfaceContainerLow = surface,
    surfaceContainerLowest = background, surfaceContainerHigh = surfaceElevated,
    surfaceContainerHighest = subtleSurface,
    primaryFixed = primary, primaryFixedDim = primary,
    onPrimaryFixed = primaryForeground, onPrimaryFixedVariant = primaryForeground,
    secondaryFixed = secondary, secondaryFixedDim = secondary,
    onSecondaryFixed = secondaryForeground, onSecondaryFixedVariant = secondaryForeground,
    tertiaryFixed = secondary, tertiaryFixedDim = secondary,
    onTertiaryFixed = secondaryForeground, onTertiaryFixedVariant = secondaryForeground,
)

internal fun FrogTypography.toMaterialTypography() = Typography(
    displayLarge = display, displayMedium = display, displaySmall = titleLarge,
    headlineLarge = titleLarge, headlineMedium = title, headlineSmall = heading,
    titleLarge = title, titleMedium = subheading, titleSmall = label,
    bodyLarge = body, bodyMedium = bodySmall, bodySmall = caption,
    labelLarge = bodySmall, labelMedium = label, labelSmall = caption,
)

internal fun FrogShapes.toMaterialShapes() = Shapes(
    extraSmall = xs, small = sm, medium = md, large = lg, extraLarge = xl,
)
