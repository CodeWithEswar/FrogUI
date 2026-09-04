package io.github.codewitheswar.frogui.foundation.motion

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Immutable

/**
 * FrogUI motion tokens.
 * Communicates hierarchy, state, continuity, and feedback with native spring and easing physics.
 * Pass nonnegative durations; zero disables duration-based motion. Custom easing instances
 * must have stable, side-effect-free behavior. Spec helpers are existing Compose interop APIs;
 * individual components should consume these tokens instead of exposing animation machinery.
 *
 * @property fastDurationMillis Duration for short interaction feedback.
 * @property normalDurationMillis Duration for ordinary state transitions.
 * @property largeDurationMillis Duration for larger spatial transitions.
 * @property standardEasing Curve used by duration-based spec helpers.
 * @property enterEasing Available curve for entering content.
 * @property exitEasing Available curve for exiting content.
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
    /** True when all duration channels are off; continuous/decorative motion should stop. */
    val isReduced: Boolean get() = fastDurationMillis <= 0 && normalDurationMillis <= 0 && largeDurationMillis <= 0

    /** An independent reduced-motion value preserving this profile's easing choices. */
    fun reduced(): FrogMotion = copy(fastDurationMillis = 0, normalDurationMillis = 0, largeDurationMillis = 0)

    /** Short tween using the standard curve; zero duration finishes immediately. */
    fun <T> fastSpec(): AnimationSpec<T> = tween(fastDurationMillis, easing = standardEasing)
    /** Ordinary-duration tween using the standard curve. */
    fun <T> normalSpec(): AnimationSpec<T> = tween(normalDurationMillis, easing = standardEasing)
    /** Longer-duration tween using the standard curve. */
    fun <T> largeSpec(): AnimationSpec<T> = tween(largeDurationMillis, easing = standardEasing)
    /** Responsive spring; a fully reduced profile snaps to preserve essential state feedback. */
    fun <T> responsiveSpring(): AnimationSpec<T> = if (isReduced) snap() else spring(
        dampingRatio = Spring.DampingRatioLowBouncy,
        stiffness = Spring.StiffnessMediumLow
    )
}
