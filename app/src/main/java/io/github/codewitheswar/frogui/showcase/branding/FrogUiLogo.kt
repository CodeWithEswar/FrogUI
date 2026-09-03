package io.github.codewitheswar.frogui.showcase.branding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.R
import io.github.codewitheswar.frogui.theme.FrogTheme

/**
 * Reusable FrogUI brand logo badge.
 * Renders the geometric software-product mark inside its container.
 */
@Composable
internal fun FrogUiLogo(
    modifier: Modifier = Modifier,
    size: Dp = 40.dp,
    variant: FrogUiLogoVariant = FrogUiLogoVariant.Auto,
    tint: Color? = null,
    contentDescription: String? = "FrogUI"
) {
    val isDark = FrogTheme.colors.isDark
    val drawableRes = when (variant) {
        FrogUiLogoVariant.Dark -> R.drawable.frogui_logo
        FrogUiLogoVariant.Light -> R.drawable.frogui_logo_inverse
        FrogUiLogoVariant.Monochrome -> R.drawable.frogui_mark_monochrome
        FrogUiLogoVariant.Auto -> if (isDark) R.drawable.frogui_logo else R.drawable.frogui_logo_inverse
    }

    val semanticsModifier = if (contentDescription != null) {
        Modifier.semantics {
            this.contentDescription = contentDescription
            this.role = Role.Image
        }
    } else {
        Modifier.clearAndSetSemantics { }
    }

    Box(
        modifier = modifier
            .size(size)
            .then(semanticsModifier),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = drawableRes),
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            colorFilter = if (variant == FrogUiLogoVariant.Monochrome && tint != null) {
                ColorFilter.tint(tint)
            } else null
        )
    }
}

/**
 * Standalone geometric frog mark component without the badge container.
 * Intended for app bars, navigation rails, and tight UI spots.
 */
@Composable
internal fun FrogUiMark(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    tint: Color = FrogTheme.colors.foreground,
    contentDescription: String? = null
) {
    val semanticsModifier = if (contentDescription != null) {
        Modifier.semantics {
            this.contentDescription = contentDescription
            this.role = Role.Image
        }
    } else {
        Modifier.clearAndSetSemantics { }
    }

    Box(
        modifier = modifier
            .size(size)
            .then(semanticsModifier),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.frogui_mark_monochrome),
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            colorFilter = ColorFilter.tint(tint)
        )
    }
}
