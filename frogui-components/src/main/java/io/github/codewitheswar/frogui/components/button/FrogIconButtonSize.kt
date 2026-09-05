package io.github.codewitheswar.frogui.components.button

import androidx.compose.ui.unit.Dp
import io.github.codewitheswar.frogui.foundation.sizing.FrogSizing

private val canonicalSizing = FrogSizing()

/**
 * Size scale for [FrogIconButton].
 *
 * Controls visible square container bounds and recommended icon dimensions.
 * All sizes maintain at least the canonical 48dp interactive touch target.
 *
 * @property containerSize Visible width and height of the icon button square.
 * @property iconSize Recommended glyph / progress indicator dimensions.
 * @property minTouchTarget Minimum accessible touch target reserved for interaction (at least 48dp).
 */
enum class FrogIconButtonSize(
    val containerSize: Dp,
    val iconSize: Dp,
    val minTouchTarget: Dp = canonicalSizing.minimumTouchTarget
) {
    /** Compact icon button (32dp visible container, 16dp icon) for dense toolbars and table rows. */
    Small(
        containerSize = canonicalSizing.controlSmall,
        iconSize = canonicalSizing.iconSmall
    ),

    /** Standard icon button (40dp visible container, 18dp icon) for general application workflows. */
    Medium(
        containerSize = canonicalSizing.controlMedium,
        iconSize = canonicalSizing.iconMedium
    ),

    /** Prominent icon button (48dp visible container, 20dp icon) for touch-first navigation and hero actions. */
    Large(
        containerSize = canonicalSizing.controlLarge,
        iconSize = canonicalSizing.iconLarge
    )
}
