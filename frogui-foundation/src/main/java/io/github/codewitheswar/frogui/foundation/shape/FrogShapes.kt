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
 * Values are Compose primitives, so consumers can copy a theme with custom corner treatments.
 *
 * @property xs Smallest treatment for inline details.
 * @property sm Compact control corners.
 * @property md Standard control and surface corners.
 * @property lg Larger contextual surface corners.
 * @property xl Most rounded large-surface treatment.
 * @property full Circular/pill-style treatment when that shape has semantic value.
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
