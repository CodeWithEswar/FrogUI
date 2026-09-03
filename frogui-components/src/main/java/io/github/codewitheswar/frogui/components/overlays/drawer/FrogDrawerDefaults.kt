package io.github.codewitheswar.frogui.components.overlays.drawer

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.theme.FrogTheme

/**
 * Default values and semantic styling tokens for [FrogDrawer].
 */
object FrogDrawerDefaults {
    /**
     * Default maximum width for side panel presentation.
     */
    val SideWidth: Dp = 400.dp

    /**
     * Default maximum width for compact bottom sheet presentation.
     */
    val BottomMaxWidth: Dp = 600.dp

    /**
     * Standard drag dismissal threshold distance.
     */
    val DragDismissThreshold: Dp = 64.dp

    /**
     * Default animation duration for drawer entrance and exit transitions.
     */
    const val AnimationDurationMs: Int = 220

    /**
     * Shape applied to bottom drawer presentation.
     */
    val bottomShape: Shape = RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp)

    /**
     * Shape applied to side drawer presentation based on docking edge.
     */
    fun sideShape(side: FrogDrawerSide = FrogDrawerSide.End): Shape {
        return when (side) {
            FrogDrawerSide.End -> RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp)
            FrogDrawerSide.Start -> RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp)
        }
    }

    /**
     * Creates a [FrogDrawerColors] instance with default colors resolved from [FrogTheme].
     */
    @Composable
    fun colors(
        containerColor: Color = FrogTheme.colors.surfaceElevated,
        contentColor: Color = FrogTheme.colors.foreground,
        scrimColor: Color = Color.Black.copy(alpha = 0.48f),
        borderColor: Color = FrogTheme.colors.borderStrong,
        handleColor: Color = FrogTheme.colors.borderStrong
    ): FrogDrawerColors = FrogDrawerColors(
        containerColor = containerColor,
        contentColor = contentColor,
        scrimColor = scrimColor,
        borderColor = borderColor,
        handleColor = handleColor
    )
}
