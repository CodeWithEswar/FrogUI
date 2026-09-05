package io.github.codewitheswar.frogui.components.fab

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.foundation.sizing.FrogSizing
import io.github.codewitheswar.frogui.theme.FrogTheme

/**
 * Default configurations, shapes, sizes, elevations, and theme colors for [FrogFloatingActionButton].
 */
object FrogFloatingActionButtonDefaults {

    /** Canonical minimum touch target (48dp). */
    val MinTouchTarget: Dp = FrogSizing().minimumTouchTarget

    /** Recommended spacing between the icon and label in an Extended FAB. */
    val IconLabelSpacing: Dp = 10.dp

    /**
     * Resolves default [FrogFabColors] using current [FrogTheme] tokens.
     *
     * @param containerColor Optional enabled background fill override.
     * @param contentColor Optional enabled icon and label foreground override.
     * @param disabledContainerColor Optional disabled background fill override.
     * @param disabledContentColor Optional disabled foreground override.
     */
    @Composable
    @ReadOnlyComposable
    fun colors(
        containerColor: Color = Color.Unspecified,
        contentColor: Color = Color.Unspecified,
        disabledContainerColor: Color = Color.Unspecified,
        disabledContentColor: Color = Color.Unspecified
    ): FrogFabColors {
        val colors = FrogTheme.colors
        val defaultContainer = colors.primary
        val defaultContent = colors.primaryForeground
        val defaultDisabledContainer = colors.primary.copy(alpha = 0.25f)
        val defaultDisabledContent = colors.primaryForeground.copy(alpha = 0.45f)
        val defaultPressedOverlay = if (colors.isDark) Color(0x1F000000) else Color(0x1FFFFFFF)
        val defaultFocusRing = colors.focusRing

        return FrogFabColors(
            containerColor = if (containerColor == Color.Unspecified) defaultContainer else containerColor,
            contentColor = if (contentColor == Color.Unspecified) defaultContent else contentColor,
            disabledContainerColor = if (disabledContainerColor == Color.Unspecified) defaultDisabledContainer else disabledContainerColor,
            disabledContentColor = if (disabledContentColor == Color.Unspecified) defaultDisabledContent else disabledContentColor,
            pressedOverlayColor = defaultPressedOverlay,
            focusRingColor = defaultFocusRing
        )
    }

    /**
     * Resolves default [FrogFabElevation] from current [FrogTheme.elevation] tokens.
     *
     * @param default Resting elevation. Defaults to [FrogTheme.elevation.medium] (3dp).
     * @param pressed Elevation while actively pressed. Defaults to [FrogTheme.elevation.high] (6dp).
     * @param focused Elevation while focused via keyboard or D-pad. Defaults to [FrogTheme.elevation.medium] (3dp).
     * @param disabled Elevation when disabled. Defaults to 0dp.
     */
    @Composable
    @ReadOnlyComposable
    fun elevation(
        default: Dp = Dp.Unspecified,
        pressed: Dp = Dp.Unspecified,
        focused: Dp = Dp.Unspecified,
        disabled: Dp = 0.dp
    ): FrogFabElevation {
        val elevation = FrogTheme.elevation
        return FrogFabElevation(
            default = if (default == Dp.Unspecified) elevation.medium else default,
            pressed = if (pressed == Dp.Unspecified) elevation.high else pressed,
            focused = if (focused == Dp.Unspecified) elevation.medium else focused,
            disabled = disabled
        )
    }

    /**
     * Resolves corner radius shape for [presentation] using current [FrogTheme.shapes] tokens.
     */
    @Composable
    @ReadOnlyComposable
    fun shape(presentation: FrogFabPresentation): Shape = when (presentation) {
        FrogFabPresentation.Regular -> FrogTheme.shapes.lg
        FrogFabPresentation.Small -> FrogTheme.shapes.md
        FrogFabPresentation.Extended -> FrogTheme.shapes.lg
    }

    /**
     * Resolves the visual container height for [presentation].
     */
    fun containerHeight(presentation: FrogFabPresentation): Dp = when (presentation) {
        FrogFabPresentation.Regular -> 56.dp
        FrogFabPresentation.Small -> 40.dp
        FrogFabPresentation.Extended -> 48.dp
    }

    /**
     * Resolves the visual container width for fixed icon-only presentations.
     * For [FrogFabPresentation.Extended], width is content-driven with minimum bounds.
     */
    fun containerWidth(presentation: FrogFabPresentation): Dp = when (presentation) {
        FrogFabPresentation.Regular -> 56.dp
        FrogFabPresentation.Small -> 40.dp
        FrogFabPresentation.Extended -> 48.dp
    }

    /**
     * Resolves the recommended icon glyph dimension for [presentation].
     */
    fun iconSize(presentation: FrogFabPresentation): Dp = when (presentation) {
        FrogFabPresentation.Regular -> 24.dp
        FrogFabPresentation.Small -> 20.dp
        FrogFabPresentation.Extended -> 20.dp
    }

    /**
     * Resolves content padding applied to [presentation].
     */
    fun contentPadding(presentation: FrogFabPresentation): PaddingValues = when (presentation) {
        FrogFabPresentation.Regular -> PaddingValues(0.dp)
        FrogFabPresentation.Small -> PaddingValues(0.dp)
        FrogFabPresentation.Extended -> PaddingValues(start = 16.dp, end = 20.dp)
    }
}
