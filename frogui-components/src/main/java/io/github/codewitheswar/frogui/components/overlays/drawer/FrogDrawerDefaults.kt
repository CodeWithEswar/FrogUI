package io.github.codewitheswar.frogui.components.overlays.drawer

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.CornerSize
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
     * Legacy duration retained for source/binary compatibility. Drawer transitions use
     * FrogTheme.motion.normalDurationMillis (zero disables motion).
     */
    @Deprecated("This constant does not control FrogDrawer motion. Configure FrogTheme.motion instead.")
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
     * Theme-aware overlay corners. Resolve Auto against host constraints before calling;
     * Bottom uses xl top corners, Side uses lg corners facing the content. Legacy
     * bottomShape/sideShape remain fixed geometry for existing direct callers.
     */
    @Composable
    fun shape(presentation: FrogDrawerPresentation, side: FrogDrawerSide = FrogDrawerSide.End): Shape {
        require(presentation != FrogDrawerPresentation.Auto) { "Resolve Auto before choosing a shape" }
        val square = CornerSize(0.dp)
        return when {
            presentation == FrogDrawerPresentation.Bottom -> FrogTheme.shapes.xl.copy(bottomStart = square, bottomEnd = square)
            side == FrogDrawerSide.End -> FrogTheme.shapes.lg.copy(topEnd = square, bottomEnd = square)
            else -> FrogTheme.shapes.lg.copy(topStart = square, bottomStart = square)
        }
    }

    // Drawer-specific geometry, intentionally outside global theme tokens.
    internal val ContentInset = 18.dp
    internal val FooterVerticalInset = 10.dp
    internal val HandleAreaHeight = 24.dp
    internal val HandleWidth = 32.dp
    internal val HandleHeight = 3.dp

    /**
     * Creates a [FrogDrawerColors] instance with default colors resolved from [FrogTheme].
     * Omit fields to follow local tokens. Explicit color values, including Transparent, are retained.
     *
     * @param containerColor Surface behind the header, content and footer.
     * @param contentColor Default text/icon color supplied to content slots.
     * @param scrimColor Backdrop over the native window or bounded overlay host.
     * @param borderColor Outline of the drawer surface.
     * @param handleColor Bottom presentation's drag indicator.
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
