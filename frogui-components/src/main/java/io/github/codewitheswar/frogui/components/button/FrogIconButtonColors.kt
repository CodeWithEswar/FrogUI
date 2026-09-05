package io.github.codewitheswar.frogui.components.button

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Color configuration for [FrogIconButton] across enabled, disabled, pressed, and focused states.
 *
 * Prefer [FrogIconButtonDefaults.colors] for theme-aware defaults. Explicit colors do not adapt
 * to theme changes automatically.
 *
 * @property containerColor Enabled surface fill.
 * @property contentColor Enabled icon glyph color and progress indicator color.
 * @property disabledContainerColor Surface fill when enabled is false.
 * @property disabledContentColor Icon glyph color when enabled is false.
 * @property borderColor Enabled outline; transparent indicates no default border stroke.
 * @property disabledBorderColor Outline when enabled is false.
 * @property pressedOverlayColor Semi-transparent overlay composited over container when pressed.
 * @property focusRingColor Focus indicator stroke color.
 */
@Immutable
data class FrogIconButtonColors(
    val containerColor: Color,
    val contentColor: Color,
    val disabledContainerColor: Color,
    val disabledContentColor: Color,
    val borderColor: Color = Color.Transparent,
    val disabledBorderColor: Color = Color.Transparent,
    val pressedOverlayColor: Color = Color.Transparent,
    val focusRingColor: Color = Color.Transparent
)
