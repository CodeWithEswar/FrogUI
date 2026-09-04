package io.github.codewitheswar.frogui.components.button

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Color configuration for [FrogButton] across enabled, disabled, pressed, and loading states.
 * Also used by [FrogIconButton]. Prefer [FrogButtonDefaults.colors] for theme-aware defaults;
 * use [copy] for the additional press/focus overrides. Explicit colors do not adapt to theme changes.
 *
 * @property containerColor Enabled surface fill; also used while loading if enabled.
 * @property contentColor Enabled label, icon and progress color.
 * @property disabledContainerColor Surface fill when enabled is false.
 * @property disabledContentColor Label/icon color when enabled is false.
 * @property borderColor Enabled outline; transparent means no default stroke.
 * @property disabledBorderColor Outline when enabled is false.
 * @property pressedOverlayColor Composited over the enabled fill while pressed.
 * @property focusRingColor Keyboard focus outline; transparent opts out of the built-in ring.
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
