package io.github.codewitheswar.frogui.foundation.sizing

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Shared control geometry, independent of font scale and available window width.
 * Visual controls may be compact; their interaction area must remain accessible.
 * Component padding and overlay widths belong in their component Defaults.
 *
 * @property minimumTouchTarget Minimum width and height reserved for an action, at least 48dp.
 * @property iconSmall Decorative glyph size for compact controls.
 * @property iconMedium Decorative glyph size for ordinary controls.
 * @property iconLarge Decorative glyph size for prominent controls/navigation.
 * @property controlSmall Minimum visual height for compact controls, separate from their target.
 * @property controlMedium Minimum visual height for ordinary controls.
 * @property controlLarge Minimum visual height for prominent controls.
 */
@Immutable
data class FrogSizing(
    val minimumTouchTarget: Dp = 48.dp,
    val iconSmall: Dp = 16.dp,
    val iconMedium: Dp = 18.dp,
    val iconLarge: Dp = 20.dp,
    val controlSmall: Dp = 32.dp,
    val controlMedium: Dp = 40.dp,
    val controlLarge: Dp = 48.dp,
) {
    init {
        require(minimumTouchTarget.value.isFinite() && minimumTouchTarget >= 48.dp) {
            "minimumTouchTarget must be finite and at least 48dp"
        }
        require(listOf(iconSmall, iconMedium, iconLarge, controlSmall, controlMedium, controlLarge)
            .all { it.value.isFinite() && it > 0.dp }) { "Visual sizes must be finite and positive" }
    }
}
