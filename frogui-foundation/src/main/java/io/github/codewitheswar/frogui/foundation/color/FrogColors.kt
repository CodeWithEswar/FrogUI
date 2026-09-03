package io.github.codewitheswar.frogui.foundation.color

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Semantic color tokens for the FrogUI design system.
 * Components must consume these semantic tokens rather than raw palette colors.
 */
@Immutable
data class FrogColors(
    val background: Color,
    val foreground: Color,
    val surface: Color,
    val surfaceElevated: Color,
    val subtleSurface: Color,
    val muted: Color,
    val mutedForeground: Color,
    val border: Color,
    val borderStrong: Color,
    val primary: Color,
    val primaryForeground: Color,
    val secondary: Color,
    val secondaryForeground: Color,
    val destructive: Color,
    val destructiveForeground: Color,
    val focusRing: Color,
    val isDark: Boolean
)
