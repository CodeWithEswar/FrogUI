package io.github.codewitheswar.frogui.components.fab

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Resolved color tokens consumed by [FrogFloatingActionButton] for container, content,
 * disabled states, and interaction feedback.
 *
 * @property containerColor Resting background fill color.
 * @property contentColor Icon glyph and visible label foreground color.
 * @property disabledContainerColor Background fill color when interaction is disabled.
 * @property disabledContentColor Foreground color when interaction is disabled.
 * @property pressedOverlayColor Tone composited over [containerColor] when pressed.
 * @property focusRingColor Outer stroke color highlighting keyboard/D-pad focus.
 */
@Immutable
data class FrogFabColors(
    val containerColor: Color,
    val contentColor: Color,
    val disabledContainerColor: Color,
    val disabledContentColor: Color,
    val pressedOverlayColor: Color,
    val focusRingColor: Color
)
