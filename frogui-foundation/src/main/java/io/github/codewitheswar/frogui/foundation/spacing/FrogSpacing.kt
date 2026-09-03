package io.github.codewitheswar.frogui.foundation.spacing

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Intentional spacing tokens for the FrogUI design system.
 * Scale: 2, 4, 6, 8, 12, 16, 20, 24, 32, 40, 48, 64 dp.
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
