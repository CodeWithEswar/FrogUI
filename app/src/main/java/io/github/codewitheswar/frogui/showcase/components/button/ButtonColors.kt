package io.github.codewitheswar.frogui.showcase.components.button

import androidx.compose.runtime.Composable
import io.github.codewitheswar.frogui.components.button.*
import io.github.codewitheswar.frogui.showcase.colorpicker.*
import io.github.codewitheswar.frogui.theme.FrogTheme

internal enum class ButtonColorProperty(val label: String, val parameter: String) {
    Container("Container color", "containerColor"), Content("Content color", "contentColor"), Border("Border color", "borderColor"),
    DisabledContainer("Disabled container", "disabledContainerColor"), DisabledContent("Disabled content", "disabledContentColor"),
    DisabledBorder("Disabled border", "disabledBorderColor");
}

internal fun defaultButtonColor(variant: FrogButtonVariant, property: ButtonColorProperty): FrogColorValue {
    fun token(value: FrogColorToken, alpha: Float? = null) = FrogColorValue.Token(value, alpha)
    val transparent = FrogColorValue.Custom(0)
    val container = when (variant) {
        FrogButtonVariant.Primary -> FrogColorToken.Primary
        FrogButtonVariant.Secondary -> FrogColorToken.Secondary
        FrogButtonVariant.Destructive -> FrogColorToken.Destructive
        else -> null
    }
    val foreground = when (variant) {
        FrogButtonVariant.Primary -> FrogColorToken.PrimaryForeground
        FrogButtonVariant.Secondary -> FrogColorToken.SecondaryForeground
        FrogButtonVariant.Destructive -> FrogColorToken.DestructiveForeground
        else -> FrogColorToken.Foreground
    }
    return when (property) {
        ButtonColorProperty.Container -> container?.let { token(it) } ?: transparent
        ButtonColorProperty.Content -> token(foreground)
        ButtonColorProperty.Border -> when (variant) {
            FrogButtonVariant.Secondary -> token(FrogColorToken.Border)
            FrogButtonVariant.Outline -> token(FrogColorToken.BorderStrong)
            else -> transparent
        }
        ButtonColorProperty.DisabledContainer -> container?.let { token(it, when (variant) {
            FrogButtonVariant.Primary -> .25f; FrogButtonVariant.Secondary -> .4f; else -> .3f
        }) } ?: transparent
        ButtonColorProperty.DisabledContent -> when (variant) {
            FrogButtonVariant.Primary -> token(foreground, .45f)
            FrogButtonVariant.Secondary -> token(foreground, .4f)
            FrogButtonVariant.Destructive -> token(foreground, .5f)
            else -> token(FrogColorToken.MutedForeground, .5f)
        }
        ButtonColorProperty.DisabledBorder -> when (variant) {
            FrogButtonVariant.Secondary -> token(FrogColorToken.Border, .3f)
            FrogButtonVariant.Outline -> token(FrogColorToken.Border, .35f)
            else -> transparent
        }
    }
}

internal fun ButtonDemoState.colorValue(property: ButtonColorProperty) = colorOverrides[property] ?: defaultButtonColor(variant, property)

@Composable
internal fun ButtonDemoState.resolvedColors(): FrogButtonColors {
    val colors = FrogTheme.colors
    return FrogButtonDefaults.colors(variant,
        containerColor = colorValue(ButtonColorProperty.Container).resolve(colors),
        contentColor = colorValue(ButtonColorProperty.Content).resolve(colors),
        borderColor = colorValue(ButtonColorProperty.Border).resolve(colors),
        disabledContainerColor = colorValue(ButtonColorProperty.DisabledContainer).resolve(colors),
        disabledContentColor = colorValue(ButtonColorProperty.DisabledContent).resolve(colors),
        disabledBorderColor = colorValue(ButtonColorProperty.DisabledBorder).resolve(colors),
    )
}
