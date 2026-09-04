package io.github.codewitheswar.frogui.foundation.elevation

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Elevation tokens for the FrogUI design system.
 * Restrained tonal elevation rather than heavy artificial shadows.
 *
 * @property none Flat surfaces without elevation.
 * @property low Subtle separation from surrounding content.
 * @property medium Contextual emphasis above ordinary surfaces.
 * @property high Highest standard elevation level.
 */
@Immutable
data class FrogElevation(
    val none: Dp = 0.dp,
    val low: Dp = 1.dp,
    val medium: Dp = 3.dp,
    val high: Dp = 6.dp
) {
    companion object {
        /** Reusable immutable standard scale. */
        val Default = FrogElevation()
    }
}
