package io.github.codewitheswar.frogui.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.theme.FrogTheme
import io.github.codewitheswar.frogui.foundation.sizing.FrogSizing

/**
 * Default configurations, shapes, paddings, and colors for [FrogButton].
 */
object FrogButtonDefaults {

    /** Canonical minimum retained for compatibility; components read FrogTheme.sizing live. */
    val MinTouchTarget = FrogSizing().minimumTouchTarget
    /** Default visible outline thickness; the focus outline is independent. */
    val BorderWidth = 1.dp

    /**
     * Resolves default [FrogButtonColors] for the given [variant] using current [FrogTheme] tokens.
     * Each [Color.Unspecified] override retains the corresponding variant default; Transparent
     * is an explicit color. Resolve again under a new theme rather than remembering stale colors.
     *
     * @param variant Semantic treatment used for every unspecified field.
     * @param containerColor Optional enabled fill override.
     * @param contentColor Optional enabled label/icon override.
     * @param borderColor Optional enabled outline override.
     * @param disabledContainerColor Optional disabled fill override.
     * @param disabledContentColor Optional disabled label/icon override.
     * @param disabledBorderColor Optional disabled outline override.
     */
    @Composable
    @ReadOnlyComposable
    fun colors(
        variant: FrogButtonVariant = FrogButtonVariant.Primary,
        containerColor: Color = Color.Unspecified,
        contentColor: Color = Color.Unspecified,
        borderColor: Color = Color.Unspecified,
        disabledContainerColor: Color = Color.Unspecified,
        disabledContentColor: Color = Color.Unspecified,
        disabledBorderColor: Color = Color.Unspecified,
    ): FrogButtonColors {
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
    private fun variantColors(variant: FrogButtonVariant): FrogButtonColors {
        val colors = FrogTheme.colors

        return when (variant) {
            FrogButtonVariant.Primary -> FrogButtonColors(
                containerColor = colors.primary,
                contentColor = colors.primaryForeground,
                disabledContainerColor = colors.primary.copy(alpha = 0.25f),
                disabledContentColor = colors.primaryForeground.copy(alpha = 0.45f),
                borderColor = Color.Transparent,
                disabledBorderColor = Color.Transparent,
                pressedOverlayColor = if (colors.isDark) Color(0x1F000000) else Color(0x1FFFFFFF),
                focusRingColor = colors.focusRing
            )

            FrogButtonVariant.Secondary -> FrogButtonColors(
                containerColor = colors.secondary,
                contentColor = colors.secondaryForeground,
                disabledContainerColor = colors.secondary.copy(alpha = 0.4f),
                disabledContentColor = colors.secondaryForeground.copy(alpha = 0.4f),
                borderColor = colors.border,
                disabledBorderColor = colors.border.copy(alpha = 0.3f),
                pressedOverlayColor = if (colors.isDark) Color(0x1AFFFFFF) else Color(0x0A000000),
                focusRingColor = colors.focusRing
            )

            FrogButtonVariant.Outline -> FrogButtonColors(
                containerColor = Color.Transparent,
                contentColor = colors.foreground,
                disabledContainerColor = Color.Transparent,
                disabledContentColor = colors.mutedForeground.copy(alpha = 0.5f),
                borderColor = colors.borderStrong,
                disabledBorderColor = colors.border.copy(alpha = 0.35f),
                pressedOverlayColor = colors.muted.copy(alpha = 0.35f),
                focusRingColor = colors.focusRing
            )

            FrogButtonVariant.Ghost -> FrogButtonColors(
                containerColor = Color.Transparent,
                contentColor = colors.foreground,
                disabledContainerColor = Color.Transparent,
                disabledContentColor = colors.mutedForeground.copy(alpha = 0.5f),
                borderColor = Color.Transparent,
                disabledBorderColor = Color.Transparent,
                pressedOverlayColor = colors.muted.copy(alpha = 0.4f),
                focusRingColor = colors.focusRing
            )

            FrogButtonVariant.Destructive -> FrogButtonColors(
                containerColor = colors.destructive,
                contentColor = colors.destructiveForeground,
                disabledContainerColor = colors.destructive.copy(alpha = 0.3f),
                disabledContentColor = colors.destructiveForeground.copy(alpha = 0.5f),
                borderColor = Color.Transparent,
                disabledBorderColor = Color.Transparent,
                pressedOverlayColor = Color(0x2E000000),
                focusRingColor = colors.focusRing
            )
        }
    }

    /**
     * Uses the local small shape for Small and medium shape for Medium/Large.
     */
    @Composable
    @ReadOnlyComposable
    fun shape(size: FrogButtonSize): Shape {
        val shapes = FrogTheme.shapes
        return when (size) {
            FrogButtonSize.Small -> shapes.sm
            FrogButtonSize.Medium -> shapes.md
            FrogButtonSize.Large -> shapes.md
        }
    }

    /** Minimum visual height from the local sizing group; text/padding may require more room. */
    @Composable
    @ReadOnlyComposable
    fun controlHeight(size: FrogButtonSize) = when (size) {
        FrogButtonSize.Small -> FrogTheme.sizing.controlSmall
        FrogButtonSize.Medium -> FrogTheme.sizing.controlMedium
        FrogButtonSize.Large -> FrogTheme.sizing.controlLarge
    }

    /** Theme-aware glyph size for loading feedback and caller-owned icon slots. */
    @Composable
    @ReadOnlyComposable
    fun iconSize(size: FrogButtonSize) = when (size) {
        FrogButtonSize.Small -> FrogTheme.sizing.iconSmall
        FrogButtonSize.Medium -> FrogTheme.sizing.iconMedium
        FrogButtonSize.Large -> FrogTheme.sizing.iconLarge
    }

    /**
     * Resolves border stroke based on variant and interaction state.
     */
    @Composable
    @ReadOnlyComposable
    fun border(variant: FrogButtonVariant, enabled: Boolean): BorderStroke? {
        return border(colors(variant), enabled)
    }

    /** Resolves the stroke from the supplied colors, including custom overrides. */
    fun border(colors: FrogButtonColors, enabled: Boolean): BorderStroke? {
        val strokeColor = if (enabled) colors.borderColor else colors.disabledBorderColor
        return if (strokeColor != Color.Transparent) {
            BorderStroke(BorderWidth, strokeColor)
        } else null
    }

    /**
     * Resolves symmetric content padding from the semantic size; label scaling may increase height.
     */
    fun contentPadding(size: FrogButtonSize): PaddingValues {
        return PaddingValues(
            horizontal = size.horizontalPadding,
            vertical = size.verticalPadding
        )
    }
}
