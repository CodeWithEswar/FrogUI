package io.github.codewitheswar.frogui.foundation.shape

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

/**
 * Semantic corner radius shape tokens for FrogUI.
 * Avoids indiscriminate over-rounding; provides precise structural radii.
 */
@Immutable
data class FrogShapes(
    val xs: CornerBasedShape = RoundedCornerShape(4.dp),
    val sm: CornerBasedShape = RoundedCornerShape(6.dp),
    val md: CornerBasedShape = RoundedCornerShape(10.dp),
    val lg: CornerBasedShape = RoundedCornerShape(14.dp),
    val xl: CornerBasedShape = RoundedCornerShape(18.dp),
    val full: Shape = CircleShape
)
