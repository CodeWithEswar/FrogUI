package io.github.codewitheswar.frogui.showcase.components.iconbutton

import androidx.compose.runtime.Composable
import io.github.codewitheswar.frogui.components.button.FrogIconButtonColors
import io.github.codewitheswar.frogui.components.button.FrogIconButtonDefaults
import io.github.codewitheswar.frogui.components.button.FrogIconButtonVariant
import io.github.codewitheswar.frogui.showcase.colorpicker.FrogColorToken
import io.github.codewitheswar.frogui.showcase.colorpicker.FrogColorValue
import io.github.codewitheswar.frogui.theme.FrogTheme

internal enum class IconButtonColorProperty(val label: String, val parameter: String) {
    Container("Container color", "containerColor"),
    Content("Content color", "contentColor"),
    Border("Border color", "borderColor"),
    DisabledContainer("Disabled container", "disabledContainerColor"),
    DisabledContent("Disabled content", "disabledContentColor"),
    DisabledBorder("Disabled border", "disabledBorderColor")
}

internal fun defaultIconButtonColor(variant: FrogIconButtonVariant, property: IconButtonColorProperty): FrogColorValue {
    fun token(value: FrogColorToken, alpha: Float? = null) = FrogColorValue.Token(value, alpha)
    val transparent = FrogColorValue.Custom(0)

    val container = when (variant) {
        FrogIconButtonVariant.Filled -> FrogColorToken.Primary
        FrogIconButtonVariant.Tonal -> FrogColorToken.Secondary
        FrogIconButtonVariant.Outline -> null
        FrogIconButtonVariant.Ghost -> null
    }

    val foreground = when (variant) {
        FrogIconButtonVariant.Filled -> FrogColorToken.PrimaryForeground
        FrogIconButtonVariant.Tonal -> FrogColorToken.SecondaryForeground
        FrogIconButtonVariant.Outline -> FrogColorToken.Foreground
        FrogIconButtonVariant.Ghost -> FrogColorToken.Foreground
    }

    return when (property) {
        IconButtonColorProperty.Container -> container?.let { token(it) } ?: transparent
        IconButtonColorProperty.Content -> token(foreground)
        IconButtonColorProperty.Border -> when (variant) {
            FrogIconButtonVariant.Outline -> token(FrogColorToken.BorderStrong)
            else -> transparent
        }
        IconButtonColorProperty.DisabledContainer -> container?.let {
            token(it, when (variant) {
                FrogIconButtonVariant.Filled -> 0.25f
                FrogIconButtonVariant.Tonal -> 0.4f
                else -> 0.3f
            })
        } ?: transparent
        IconButtonColorProperty.DisabledContent -> when (variant) {
            FrogIconButtonVariant.Filled -> token(foreground, 0.45f)
            FrogIconButtonVariant.Tonal -> token(foreground, 0.4f)
            else -> token(FrogColorToken.MutedForeground, 0.45f)
        }
        IconButtonColorProperty.DisabledBorder -> when (variant) {
            FrogIconButtonVariant.Outline -> token(FrogColorToken.Border, 0.35f)
            else -> transparent
        }
    }
}

internal fun IconButtonDemoState.colorValue(property: IconButtonColorProperty): FrogColorValue =
    colorOverrides[property] ?: defaultIconButtonColor(variant, property)

@Composable
internal fun IconButtonDemoState.resolvedColors(): FrogIconButtonColors {
    val colors = FrogTheme.colors
    return FrogIconButtonDefaults.colors(
        variant = variant,
        containerColor = colorValue(IconButtonColorProperty.Container).resolve(colors),
        contentColor = colorValue(IconButtonColorProperty.Content).resolve(colors),
        borderColor = colorValue(IconButtonColorProperty.Border).resolve(colors),
        disabledContainerColor = colorValue(IconButtonColorProperty.DisabledContainer).resolve(colors),
        disabledContentColor = colorValue(IconButtonColorProperty.DisabledContent).resolve(colors),
        disabledBorderColor = colorValue(IconButtonColorProperty.DisabledBorder).resolve(colors)
    )
}
