package io.github.codewitheswar.frogui.components.fab

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Semantic elevation levels applied to [FrogFloatingActionButton] across interaction states.
 *
 * @property default Resting elevation above the primary canvas.
 * @property pressed Elevation applied when the button is actively pressed.
 * @property focused Elevation applied when focused via keyboard or directional pad.
 * @property disabled Elevation applied when the button is disabled. Defaults to 0dp.
 */
@Immutable
data class FrogFabElevation(
    val default: Dp,
    val pressed: Dp,
    val focused: Dp,
    val disabled: Dp = 0.dp
)
