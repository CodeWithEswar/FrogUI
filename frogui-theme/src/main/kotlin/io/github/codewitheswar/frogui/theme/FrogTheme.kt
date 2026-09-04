package io.github.codewitheswar.frogui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import io.github.codewitheswar.frogui.foundation.color.FrogColors
import io.github.codewitheswar.frogui.foundation.elevation.FrogElevation
import io.github.codewitheswar.frogui.foundation.motion.FrogMotion
import io.github.codewitheswar.frogui.foundation.shape.FrogShapes
import io.github.codewitheswar.frogui.foundation.spacing.FrogSpacing
import io.github.codewitheswar.frogui.foundation.typography.FrogTypography
import io.github.codewitheswar.frogui.foundation.sizing.FrogSizing
import io.github.codewitheswar.frogui.foundation.adaptive.FrogAdaptive

internal val LocalFrogColors = staticCompositionLocalOf { FrogThemeDefaults.darkColors() }
internal val LocalFrogTypography = staticCompositionLocalOf { FrogTypography() }
internal val LocalFrogShapes = staticCompositionLocalOf { FrogShapes() }
internal val LocalFrogSpacing = staticCompositionLocalOf { FrogSpacing() }
internal val LocalFrogElevation = staticCompositionLocalOf { FrogElevation() }
internal val LocalFrogMotion = staticCompositionLocalOf { FrogMotion() }

/**
 * Reads the nearest FrogTheme provider. Nested themes isolate overrides to their content;
 * components consume semantic tokens instead of raw palette values. Accessors are read-only
 * composable values, not global mutable application state.
 */
object FrogTheme {
    /** Semantic foreground, surface, interaction and boundary colors. */
    val colors: FrogColors
        @Composable
        @ReadOnlyComposable
        get() = LocalFrogColors.current

    /** Text styles expressed in scalable font units. */
    val typography: FrogTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalFrogTypography.current

    /** Corner and full-shape tokens for component surfaces. */
    val shapes: FrogShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalFrogShapes.current

    /** Shared spacing scale for layout and content gaps. */
    val spacing: FrogSpacing
        @Composable
        @ReadOnlyComposable
        get() = LocalFrogSpacing.current

    /** Semantic elevation levels; components decide where elevation is meaningful. */
    val elevation: FrogElevation
        @Composable
        @ReadOnlyComposable
        get() = LocalFrogElevation.current

    /** Durations and easing used by component feedback/transitions. */
    val motion: FrogMotion
        @Composable
        @ReadOnlyComposable
        get() = if (reduceMotion) LocalFrogMotion.current.reduced() else LocalFrogMotion.current

    /** Shared visual dimensions and minimum interaction targets. */
    val sizing: FrogSizing
        @Composable @ReadOnlyComposable get() = LocalFrogSizing.current

    /** Policy used to classify actual window/pane/preview constraints. */
    val adaptive: FrogAdaptive
        @Composable @ReadOnlyComposable get() = LocalFrogAdaptive.current

    /** Effective local/system preference, also true for a fully zero-duration motion profile. */
    val reduceMotion: Boolean
        @Composable @ReadOnlyComposable get() = LocalFrogReduceMotion.current ||
            LocalFrogSystemReduceMotion.current == true || LocalFrogMotion.current.isReduced
}

/**
 * Main theme entry point for FrogUI.
 * Provides custom FrogUI tokens through CompositionLocals while bridging
 * with underlying Android/Material primitives for accessibility and platform behavior.
 * Custom token objects remain caller-owned immutable values. The Material bridge is an
 * implementation detail and does not add Material types to this public contract.
 * Omitted non-color groups inherit the nearest theme (safe canonical defaults outside one).
 * Omitted colors select system/light/dark defaults; pass colors explicitly to inherit a palette.
 * Use [ProvideFrogThemeEnvironment] for sizing, adaptive policy and reduced motion.
 *
 * @param darkTheme Selects the default palette; system appearance is used when omitted.
 * @param colors Explicit semantic palette. When supplied, its isDark value drives the bridge.
 * @param typography Scalable text styles used by FrogUI components.
 * @param shapes Semantic surface shapes.
 * @param spacing Shared layout spacing scale.
 * @param elevation Semantic elevation levels.
 * @param motion Motion tokens; zero durations disable duration-based component transitions.
 * @param content Composition receiving these local tokens.
 */
@Composable
fun FrogTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    colors: FrogColors = if (darkTheme) FrogThemeDefaults.darkColors() else FrogThemeDefaults.lightColors(),
    typography: FrogTypography = FrogTheme.typography,
    shapes: FrogShapes = FrogTheme.shapes,
    spacing: FrogSpacing = FrogTheme.spacing,
    elevation: FrogElevation = FrogTheme.elevation,
    motion: FrogMotion = LocalFrogMotion.current,
    content: @Composable () -> Unit
) {
    val systemReduced = systemReduceMotion()

    CompositionLocalProvider(
        LocalFrogColors provides colors,
        LocalFrogTypography provides typography,
        LocalFrogShapes provides shapes,
        LocalFrogSpacing provides spacing,
        LocalFrogElevation provides elevation,
        LocalFrogMotion provides motion,
        LocalFrogSystemReduceMotion provides systemReduced,
        LocalContentColor provides colors.foreground,
    ) {
        MaterialTheme(
            colorScheme = colors.toMaterialColors(),
            typography = typography.toMaterialTypography(),
            shapes = shapes.toMaterialShapes(),
            content = content
        )
    }
}
