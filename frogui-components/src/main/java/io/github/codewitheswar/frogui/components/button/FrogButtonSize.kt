package io.github.codewitheswar.frogui.components.button

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.foundation.sizing.FrogSizing

private val canonicalSizing = FrogSizing()

/**
 * Size scale for [FrogButton].
 * Controls height, horizontal padding, content spacing, and icon dimensions.
 * Heights are minimum visible dimensions, not maximum text heights or touch-target sizes.
 * These properties retain canonical metrics for compatibility. Components resolve live
 * visual heights and glyph sizes through FrogButtonDefaults.controlHeight/iconSize so
 * a local FrogSizing override is honored. Padding and icon gaps remain component-specific.
 *
 * @property minHeight Minimum visible surface height at this scale.
 * @property horizontalPadding Space between content and each horizontal edge.
 * @property verticalPadding Space above and below content; can increase measured height.
 * @property iconSize Recommended slot icon/progress dimensions.
 * @property iconSpacing Gap between a present icon slot and the label.
 */
enum class FrogButtonSize(
    val minHeight: Dp,
    val horizontalPadding: Dp,
    val verticalPadding: Dp,
    val iconSize: Dp,
    val iconSpacing: Dp
) {
    /** Compact button (32dp height) for toolbars, inline actions, and dense tables. */
    Small(
        minHeight = canonicalSizing.controlSmall,
        horizontalPadding = 12.dp,
        verticalPadding = 6.dp,
        iconSize = canonicalSizing.iconSmall,
        iconSpacing = 6.dp
    ),

    /** Standard button (40dp height) for most application dialogs and forms. */
    Medium(
        minHeight = canonicalSizing.controlMedium,
        horizontalPadding = 16.dp,
        verticalPadding = 10.dp,
        iconSize = canonicalSizing.iconMedium,
        iconSpacing = 8.dp
    ),

    /** Prominent button (48dp height) for hero actions, CTA strips, and primary touch targets. */
    Large(
        minHeight = canonicalSizing.controlLarge,
        horizontalPadding = 20.dp,
        verticalPadding = 14.dp,
        iconSize = canonicalSizing.iconLarge,
        iconSpacing = 10.dp
    )
}
