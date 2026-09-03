package io.github.codewitheswar.frogui.components.button

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Color configuration for [FrogButton] across enabled, disabled, pressed, and loading states.
 */
@Immutable
data class FrogButtonColors(
    val containerColor: Color,
    val contentColor: Color,
    val disabledContainerColor: Color,
    val disabledContentColor: Color,
    val borderColor: Color = Color.Transparent,
    val disabledBorderColor: Color = Color.Transparent,
    val pressedOverlayColor: Color = Color.Transparent,
    val focusRingColor: Color = Color.Transparent
)
