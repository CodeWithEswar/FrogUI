package io.github.codewitheswar.frogui.ui.branding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.codewitheswar.frogui.R

/**
 * Canonical FrogUI brand logo component.
 *
 * Renders the official FrogUI badge or standalone mark with pixel-aligned precision,
 * full dark/light theme support, and TalkBack accessibility semantics.
 *
 * @param modifier Optional [Modifier] for layout adjustments.
 * @param size Target square dimension for the logo (default: 32.dp).
 * @param variant Visual variant: [FrogUiLogoVariant.Auto], [FrogUiLogoVariant.Dark],
 * [FrogUiLogoVariant.Light], or [FrogUiLogoVariant.Monochrome].
 * @param contentDescription Optional accessibility label. If null, treated as decorative.
 */
@Composable
fun FrogUiLogo(
    modifier: Modifier = Modifier,
    size: Dp = 32.dp,
    variant: FrogUiLogoVariant = FrogUiLogoVariant.Auto,
    contentDescription: String? = null
) {
    val isDark = isSystemInDarkTheme()
    val resolvedVariant = when (variant) {
        FrogUiLogoVariant.Auto -> if (isDark) FrogUiLogoVariant.Dark else FrogUiLogoVariant.Light
        else -> variant
    }

    val semanticsModifier = if (contentDescription != null) {
        modifier
            .size(size)
            .semantics(mergeDescendants = true) {
                this.contentDescription = contentDescription
                this.role = Role.Image
            }
    } else {
        modifier.size(size)
    }

    when (resolvedVariant) {
        FrogUiLogoVariant.Dark -> {
            Image(
                painter = painterResource(id = R.drawable.frogui_logo),
                contentDescription = null,
                modifier = semanticsModifier
            )
        }
        FrogUiLogoVariant.Light -> {
            Image(
                painter = painterResource(id = R.drawable.frogui_logo_inverse),
                contentDescription = null,
                modifier = semanticsModifier
            )
        }
        FrogUiLogoVariant.Monochrome -> {
            Icon(
                painter = painterResource(id = R.drawable.frogui_mark),
                contentDescription = null,
                modifier = semanticsModifier,
                tint = LocalContentColor.current
            )
        }
        FrogUiLogoVariant.Auto -> {
            // Evaluated above
        }
    }
}

/**
 * Standalone FrogUI geometric mark (without container badge).
 *
 * Designed for toolbars, navigation bars, headers, and compact indicators.
 *
 * @param modifier Optional [Modifier].
 * @param size Desired square dimensions (default: 24.dp).
 * @param tint Color to tint the frog silhouette (defaults to [LocalContentColor]).
 * @param contentDescription Optional TalkBack accessibility label.
 */
@Composable
fun FrogUiMark(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    tint: Color = LocalContentColor.current,
    contentDescription: String? = null
) {
    val semanticsModifier = if (contentDescription != null) {
        modifier
            .size(size)
            .semantics(mergeDescendants = true) {
                this.contentDescription = contentDescription
                this.role = Role.Image
            }
    } else {
        modifier.size(size)
    }

    Icon(
        painter = painterResource(id = R.drawable.frogui_mark),
        contentDescription = null,
        modifier = semanticsModifier,
        tint = tint
    )
}

// ---------------------------------------------------------------------------
// Previews
// ---------------------------------------------------------------------------

@Preview(name = "Dark Surface — Canonical Brand Badge", showBackground = true, backgroundColor = 0xFF09090B)
@Composable
fun FrogUiLogoDarkPreview() {
    Surface(color = Color(0xFF09090B), modifier = Modifier.padding(16.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            FrogUiLogo(size = 40.dp, variant = FrogUiLogoVariant.Dark, contentDescription = "FrogUI")
            Text(
                text = "FrogUI",
                color = Color.White,
                fontSize = 20.sp,
                fontFamily = FontFamily.Monospace
            )
        }
    }
}

@Preview(name = "Light Surface — Inverted Brand Badge", showBackground = true, backgroundColor = 0xFFF4F4F5)
@Composable
fun FrogUiLogoLightPreview() {
    Surface(color = Color(0xFFF4F4F5), modifier = Modifier.padding(16.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            FrogUiLogo(size = 40.dp, variant = FrogUiLogoVariant.Light, contentDescription = "FrogUI")
            Text(
                text = "FrogUI",
                color = Color(0xFF09090B),
                fontSize = 20.sp,
                fontFamily = FontFamily.Monospace
            )
        }
    }
}

@Preview(name = "Small Mark (16dp to 32dp)", showBackground = true, backgroundColor = 0xFF18181B)
@Composable
fun FrogUiLogoSmallSizesPreview() {
    Surface(color = Color(0xFF18181B), modifier = Modifier.padding(16.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FrogUiMark(size = 16.dp, tint = Color.White)
            FrogUiMark(size = 20.dp, tint = Color.White)
            FrogUiMark(size = 24.dp, tint = Color.White)
            FrogUiMark(size = 32.dp, tint = Color.White)
            FrogUiLogo(size = 32.dp, variant = FrogUiLogoVariant.Dark)
        }
    }
}

@Preview(name = "Standard Mark (40dp & 48dp)", showBackground = true, backgroundColor = 0xFF09090B)
@Composable
fun FrogUiLogoStandardPreview() {
    Surface(color = Color(0xFF09090B), modifier = Modifier.padding(16.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            FrogUiLogo(size = 40.dp, variant = FrogUiLogoVariant.Dark)
            FrogUiLogo(size = 48.dp, variant = FrogUiLogoVariant.Dark)
            FrogUiMark(size = 40.dp, tint = Color.White)
            FrogUiMark(size = 48.dp, tint = Color.White)
        }
    }
}

@Preview(name = "Large Mark (64dp & 96dp)", showBackground = true, backgroundColor = 0xFF09090B)
@Composable
fun FrogUiLogoLargePreview() {
    Surface(color = Color(0xFF09090B), modifier = Modifier.padding(16.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            FrogUiLogo(size = 64.dp, variant = FrogUiLogoVariant.Dark)
            FrogUiLogo(size = 96.dp, variant = FrogUiLogoVariant.Dark)
            FrogUiMark(size = 64.dp, tint = Color.White)
            FrogUiMark(size = 96.dp, tint = Color.White)
        }
    }
}

@Preview(name = "Monochrome Tinting Variants", showBackground = true, backgroundColor = 0xFF18181B)
@Composable
fun FrogUiLogoMonochromePreview() {
    Surface(color = Color(0xFF18181B), modifier = Modifier.padding(16.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FrogUiMark(size = 32.dp, tint = Color(0xFFFFFFFF))
            FrogUiMark(size = 32.dp, tint = Color(0xFFA1A1AA))
            FrogUiMark(size = 32.dp, tint = Color(0xFF71717A))
            FrogUiMark(size = 32.dp, tint = Color(0xFF3F3F46))
        }
    }
}
