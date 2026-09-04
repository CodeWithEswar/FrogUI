package io.github.codewitheswar.frogui.foundation.spacing

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Intentional spacing tokens for the FrogUI design system.
 * Scale: 2, 4, 6, 8, 12, 16, 20, 24, 32, 40, 48, 64 dp.
 * Choose a semantic level rather than embedding measurements throughout components.
 * Defaults can be copied for a local theme without mutating other compositions.
 *
 * @property xxs Finest separation (2dp by default).
 * @property xs Small inline separation (4dp).
 * @property sm Compact content gap (6dp).
 * @property md Ordinary inline gap (8dp).
 * @property lg Control/group gap (12dp).
 * @property xl Standard surface padding (16dp).
 * @property xxl Generous content gap (20dp).
 * @property xxxl Section separation (24dp).
 * @property x4l Large section separation (32dp).
 * @property x5l Extra large separation (40dp).
 * @property x6l Major region separation (48dp).
 * @property x7l Largest standard region separation (64dp).
 */
@Immutable
data class FrogSpacing(
    val xxs: Dp = 2.dp,
    val xs: Dp = 4.dp,
    val sm: Dp = 6.dp,
    val md: Dp = 8.dp,
    val lg: Dp = 12.dp,
    val xl: Dp = 16.dp,
    val xxl: Dp = 20.dp,
    val xxxl: Dp = 24.dp,
    val x4l: Dp = 32.dp,
    val x5l: Dp = 40.dp,
    val x6l: Dp = 48.dp,
    val x7l: Dp = 64.dp
)
