package io.github.codewitheswar.frogui.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.foundation.sizing.FrogSizing
import io.github.codewitheswar.frogui.theme.FrogTheme

/**
 * Default configurations, shapes, sizes, and theme colors for [FrogIconButton].
 */
object FrogIconButtonDefaults {

    /** Canonical minimum touch target (48dp). */
    val MinTouchTarget: Dp = FrogSizing().minimumTouchTarget

    /** Default border stroke width for outlined variants. */
    val BorderWidth: Dp = 1.dp

    /**
     * Resolves default [FrogIconButtonColors] for the given [variant] using current [FrogTheme] tokens.
     *
     * @param variant Semantic emphasis variant.
     * @param containerColor Optional enabled background fill override.
     * @param contentColor Optional enabled icon glyph color override.
     * @param borderColor Optional enabled outline color override.
     * @param disabledContainerColor Optional disabled background fill override.
     * @param disabledContentColor Optional disabled icon glyph color override.
     * @param disabledBorderColor Optional disabled outline color override.
     */
    @Composable
    @ReadOnlyComposable
    fun colors(
        variant: FrogIconButtonVariant = FrogIconButtonVariant.Filled,
        containerColor: Color = Color.Unspecified,
        contentColor: Color = Color.Unspecified,
        borderColor: Color = Color.Unspecified,
        disabledContainerColor: Color = Color.Unspecified,
        disabledContentColor: Color = Color.Unspecified,
        disabledBorderColor: Color = Color.Unspecified,
    ): FrogIconButtonColors {
        val defaults = variantColors(variant)
        return defaults.copy(
            containerColor = if (containerColor == Color.Unspecified) defaults.containerColor else containerColor,
            contentColor = if (contentColor == Color.Unspecified) defaults.contentColor else contentColor,
            borderColor = if (borderColor == Color.Unspecified) defaults.borderColor else borderColor,
            disabledContainerColor = if (disabledContainerColor == Color.Unspecified) defaults.disabledContainerColor else disabledContainerColor,
            disabledContentColor = if (disabledContentColor == Color.Unspecified) defaults.disabledContentColor else disabledContentColor,
            disabledBorderColor = if (disabledBorderColor == Color.Unspecified) defaults.disabledBorderColor else disabledBorderColor,
        )
    }

    @Composable
    @ReadOnlyComposable
    private fun variantColors(variant: FrogIconButtonVariant): FrogIconButtonColors {
        val colors = FrogTheme.colors

        return when (variant) {
            FrogIconButtonVariant.Filled -> FrogIconButtonColors(
                containerColor = colors.primary,
                contentColor = colors.primaryForeground,
                disabledContainerColor = colors.primary.copy(alpha = 0.25f),
                disabledContentColor = colors.primaryForeground.copy(alpha = 0.45f),
                borderColor = Color.Transparent,
                disabledBorderColor = Color.Transparent,
                pressedOverlayColor = if (colors.isDark) Color(0x1F000000) else Color(0x1FFFFFFF),
                focusRingColor = colors.focusRing
            )

            FrogIconButtonVariant.Tonal -> FrogIconButtonColors(
                containerColor = colors.secondary,
                contentColor = colors.secondaryForeground,
                disabledContainerColor = colors.secondary.copy(alpha = 0.4f),
                disabledContentColor = colors.secondaryForeground.copy(alpha = 0.4f),
                borderColor = Color.Transparent,
                disabledBorderColor = Color.Transparent,
                pressedOverlayColor = if (colors.isDark) Color(0x1AFFFFFF) else Color(0x0A000000),
                focusRingColor = colors.focusRing
            )

            FrogIconButtonVariant.Outline -> FrogIconButtonColors(
                containerColor = Color.Transparent,
                contentColor = colors.foreground,
                disabledContainerColor = Color.Transparent,
                disabledContentColor = colors.mutedForeground.copy(alpha = 0.5f),
                borderColor = colors.border,
                disabledBorderColor = colors.border.copy(alpha = 0.3f),
                pressedOverlayColor = if (colors.isDark) Color(0x1AFFFFFF) else Color(0x0A000000),
                focusRingColor = colors.focusRing
            )

            FrogIconButtonVariant.Ghost -> FrogIconButtonColors(
                containerColor = Color.Transparent,
                contentColor = colors.foreground,
                disabledContainerColor = Color.Transparent,
                disabledContentColor = colors.mutedForeground.copy(alpha = 0.5f),
                borderColor = Color.Transparent,
                disabledBorderColor = Color.Transparent,
                pressedOverlayColor = if (colors.isDark) Color(0x1AFFFFFF) else Color(0x0A000000),
                focusRingColor = colors.focusRing
            )
        }
    }

    /**
     * Resolves the visible square width and height for [size] from current [FrogTheme.sizing] tokens.
     */
    @Composable
    @ReadOnlyComposable
    fun containerSize(size: FrogIconButtonSize): Dp = when (size) {
        FrogIconButtonSize.Small -> FrogTheme.sizing.controlSmall
        FrogIconButtonSize.Medium -> FrogTheme.sizing.controlMedium
        FrogIconButtonSize.Large -> FrogTheme.sizing.controlLarge
    }

    /**
     * Resolves the recommended icon glyph and progress indicator dimensions for [size].
     */
    @Composable
    @ReadOnlyComposable
    fun iconSize(size: FrogIconButtonSize): Dp = when (size) {
        FrogIconButtonSize.Small -> FrogTheme.sizing.iconSmall
        FrogIconButtonSize.Medium -> FrogTheme.sizing.iconMedium
        FrogIconButtonSize.Large -> FrogTheme.sizing.iconLarge
    }

    /**
     * Resolves corner radius shape for [size] using current [FrogTheme.shapes] tokens.
     */
    @Composable
    @ReadOnlyComposable
    fun shape(size: FrogIconButtonSize): Shape = when (size) {
        FrogIconButtonSize.Small -> FrogTheme.shapes.sm
        FrogIconButtonSize.Medium -> FrogTheme.shapes.md
        FrogIconButtonSize.Large -> FrogTheme.shapes.lg
    }

    /**
     * Resolves [BorderStroke] for the resolved colors and interaction state.
     */
    fun border(colors: FrogIconButtonColors, enabled: Boolean): BorderStroke? {
        val color = if (enabled) colors.borderColor else colors.disabledBorderColor
        return if (color != Color.Transparent) BorderStroke(BorderWidth, color) else null
    }

    /**
     * Resolves [BorderStroke] for a [variant] using current theme tokens.
     */
    @Composable
    @ReadOnlyComposable
    fun border(variant: FrogIconButtonVariant, enabled: Boolean): BorderStroke? {
        val colors = colors(variant)
        return border(colors, enabled)
    }
}
