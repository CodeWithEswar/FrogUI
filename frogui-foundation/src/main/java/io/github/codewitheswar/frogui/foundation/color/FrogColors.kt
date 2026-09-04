package io.github.codewitheswar.frogui.foundation.color

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Semantic color tokens for the FrogUI design system.
 * Components must consume these semantic tokens rather than raw palette colors.
 * Construct a complete palette or copy an existing theme palette; explicit values are fixed
 * until the caller supplies a different instance. Review foreground/background contrast together.
 *
 * @property background Base canvas behind component surfaces.
 * @property foreground Primary text/icon color on neutral surfaces.
 * @property surface Standard component surface.
 * @property surfaceElevated Visually raised contextual surface.
 * @property subtleSurface Low-emphasis surface for grouping.
 * @property muted Muted fills and subdued regions.
 * @property mutedForeground Secondary text and supporting information.
 * @property border Subtle structural boundary.
 * @property borderStrong Higher-emphasis structural boundary.
 * @property primary Primary action fill.
 * @property primaryForeground Content on the primary fill.
 * @property secondary Supporting action fill.
 * @property secondaryForeground Content on the secondary fill.
 * @property destructive Fill conveying destructive intent.
 * @property destructiveForeground Content on the destructive fill.
 * @property focusRing Visible keyboard-focus outline.
 * @property isDark Indicates this palette's luminance mode, independently of system appearance.
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
