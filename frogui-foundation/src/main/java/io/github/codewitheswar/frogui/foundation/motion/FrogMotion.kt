package io.github.codewitheswar.frogui.foundation.motion

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Immutable

/**
 * FrogUI motion tokens.
 * Communicates hierarchy, state, continuity, and feedback with native spring and easing physics.
 */
@Immutable
data class FrogMotion(
    val fastDurationMillis: Int = 120,
    val normalDurationMillis: Int = 200,
    val largeDurationMillis: Int = 280,
    val standardEasing: Easing = FastOutSlowInEasing,
    val enterEasing: Easing = LinearOutSlowInEasing,
    val exitEasing: Easing = CubicBezierEasing(0.4f, 0.0f, 1.0f, 1.0f)
) {
    fun <T> fastSpec(): AnimationSpec<T> = tween(fastDurationMillis, easing = standardEasing)
    fun <T> normalSpec(): AnimationSpec<T> = tween(normalDurationMillis, easing = standardEasing)
    fun <T> largeSpec(): AnimationSpec<T> = tween(largeDurationMillis, easing = standardEasing)
    fun <T> responsiveSpring(): AnimationSpec<T> = spring(
        dampingRatio = Spring.DampingRatioLowBouncy,
        stiffness = Spring.StiffnessMediumLow
    )
}
