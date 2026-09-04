package io.github.codewitheswar.frogui.foundation.adaptive

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/** Width categories for composition decisions; they do not scale ordinary controls. */
enum class FrogWindowSizeClass {
    /** A stacked layout; modal inspectors usually enter from the bottom. */
    Compact,
    /** Room for a navigation rail and contextual side panels. */
    Medium,
    /** Room for a sidebar and multiple panes when the remaining content width allows them. */
    Expanded,
}

/**
 * Shared width policy for window, pane and bounded preview constraints in Compose dp.
 * Feed actual available width, not physical display pixels or device identity. A nested
 * preview can resolve its own constraints without changing the outer app's environment.
 * This model deliberately does not describe fold posture, hinges or window height.
 *
 * @property mediumMinWidth Inclusive lower bound of Medium; Android default is 600dp.
 * @property expandedMinWidth Inclusive lower bound of Expanded; Android default is 840dp.
 */
@Immutable
data class FrogAdaptive(
    val mediumMinWidth: Dp = 600.dp,
    val expandedMinWidth: Dp = 840.dp,
) {
    init {
        require(mediumMinWidth.value.isFinite() && expandedMinWidth.value.isFinite() &&
            mediumMinWidth > 0.dp && expandedMinWidth > mediumMinWidth) {
            "Adaptive thresholds must be finite, positive and increasing"
        }
    }

    /** Resolves a finite, nonnegative available width without rounding at boundaries. */
    fun windowSizeClass(availableWidth: Dp): FrogWindowSizeClass {
        require(availableWidth.value.isFinite() && availableWidth >= 0.dp) {
            "Resolve adaptive layout from finite, nonnegative constraints"
        }
        return when {
            availableWidth < mediumMinWidth -> FrogWindowSizeClass.Compact
            availableWidth < expandedMinWidth -> FrogWindowSizeClass.Medium
            else -> FrogWindowSizeClass.Expanded
        }
    }
}
