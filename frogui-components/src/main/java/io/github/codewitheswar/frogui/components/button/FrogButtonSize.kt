package io.github.codewitheswar.frogui.components.button

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Size scale for [FrogButton].
 * Controls height, horizontal padding, content spacing, and icon dimensions.
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
        minHeight = 32.dp,
        horizontalPadding = 12.dp,
        verticalPadding = 6.dp,
        iconSize = 16.dp,
        iconSpacing = 6.dp
    ),

    /** Standard button (40dp height) for most application dialogs and forms. */
    Medium(
        minHeight = 40.dp,
        horizontalPadding = 16.dp,
        verticalPadding = 10.dp,
        iconSize = 18.dp,
        iconSpacing = 8.dp
    ),

    /** Prominent button (48dp height) for hero actions, CTA strips, and primary touch targets. */
    Large(
        minHeight = 48.dp,
        horizontalPadding = 20.dp,
        verticalPadding = 14.dp,
        iconSize = 20.dp,
        iconSpacing = 10.dp
    )
}
