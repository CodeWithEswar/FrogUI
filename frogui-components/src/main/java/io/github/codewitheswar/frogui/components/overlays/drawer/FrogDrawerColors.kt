package io.github.codewitheswar.frogui.components.overlays.drawer

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Represents the container, content, border, handle, and scrim colors used in a [FrogDrawer].
 *
 * @param containerColor The background color of the drawer sheet or side panel surface.
 * @param contentColor The default text and icon color for content within the drawer.
 * @param scrimColor The color of the modal backdrop overlay rendered behind the drawer.
 * @param borderColor The border stroke color applied to the perimeter of the drawer.
 * @param handleColor The color of the drag handle indicator in bottom sheet presentation.
 */
@Immutable
data class FrogDrawerColors(
    val containerColor: Color,
    val contentColor: Color,
    val scrimColor: Color,
    val borderColor: Color,
    val handleColor: Color
)
