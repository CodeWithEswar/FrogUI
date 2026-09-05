package io.github.codewitheswar.frogui.components.textfield

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.theme.FrogTheme

/**
 * Default configurations, dimensions, shapes, and color models for [FrogTextField].
 */
public object FrogTextFieldDefaults {
    /**
     * Standard minimum height of the text field container.
     */
    public val MinHeight: Dp = 56.dp

    /**
     * Compact minimum height, used for minimal form presentations such as [FrogTextFieldVariant.Underline].
     */
    public val CompactMinHeight: Dp = 48.dp

    /**
     * Unfocused border or indicator underline thickness.
     */
    public val UnfocusedIndicatorThickness: Dp = 1.dp

    /**
     * Focused border or indicator underline thickness.
     */
    public val FocusedIndicatorThickness: Dp = 2.dp

    /**
     * Standard icon dimensions for leading and trailing slots.
     */
    public val IconSize: Dp = 20.dp

    /**
     * Resolves the default shape for the given [variant].
     */
    @Composable
    public fun shape(variant: FrogTextFieldVariant): Shape = when (variant) {
        FrogTextFieldVariant.Filled -> FrogTheme.shapes.sm
        FrogTextFieldVariant.Outline -> FrogTheme.shapes.sm
        FrogTextFieldVariant.Underline -> RectangleShape
    }

    /**
     * Resolves the minimum touch and container height for the given [variant].
     */
    public fun minHeight(variant: FrogTextFieldVariant): Dp = when (variant) {
        FrogTextFieldVariant.Filled -> MinHeight
        FrogTextFieldVariant.Outline -> MinHeight
        FrogTextFieldVariant.Underline -> CompactMinHeight
    }

    /**
     * Indicator thickness based on current focus state.
     */
    public fun indicatorThickness(focused: Boolean): Dp =
        if (focused) FocusedIndicatorThickness else UnfocusedIndicatorThickness

    /**
     * Content padding inside the field input box.
     */
    public fun contentPadding(
        variant: FrogTextFieldVariant = FrogTextFieldVariant.Filled,
        hasLeading: Boolean = false,
        hasTrailing: Boolean = false,
    ): PaddingValues {
        val horizontalStart = when {
            hasLeading -> 8.dp
            variant == FrogTextFieldVariant.Underline -> 0.dp
            else -> 12.dp
        }
        val horizontalEnd = when {
            hasTrailing -> 8.dp
            variant == FrogTextFieldVariant.Underline -> 0.dp
            else -> 12.dp
        }
        return PaddingValues(
            start = horizontalStart,
            end = horizontalEnd,
            top = if (variant == FrogTextFieldVariant.Underline) 8.dp else 10.dp,
            bottom = if (variant == FrogTextFieldVariant.Underline) 8.dp else 10.dp,
        )
    }

    /**
     * Constructs a [FrogTextFieldColors] palette mapped to current [FrogTheme] tokens.
     */
    @Composable
    public fun colors(
        variant: FrogTextFieldVariant = FrogTextFieldVariant.Filled,
        textColor: Color = FrogTheme.colors.foreground,
        disabledTextColor: Color = FrogTheme.colors.mutedForeground.copy(alpha = 0.6f),
        readOnlyTextColor: Color = FrogTheme.colors.foreground,
        containerColor: Color = when (variant) {
            FrogTextFieldVariant.Filled -> FrogTheme.colors.surface
            FrogTextFieldVariant.Outline, FrogTextFieldVariant.Underline -> Color.Transparent
        },
        focusedContainerColor: Color = containerColor,
        disabledContainerColor: Color = when (variant) {
            FrogTextFieldVariant.Filled -> FrogTheme.colors.surface.copy(alpha = 0.5f)
            FrogTextFieldVariant.Outline, FrogTextFieldVariant.Underline -> Color.Transparent
        },
        cursorColor: Color = FrogTheme.colors.primary,
        indicatorColor: Color = FrogTheme.colors.border,
        focusedIndicatorColor: Color = FrogTheme.colors.focusRing,
        disabledIndicatorColor: Color = FrogTheme.colors.border.copy(alpha = 0.4f),
        errorIndicatorColor: Color = FrogTheme.colors.destructive,
        labelColor: Color = FrogTheme.colors.mutedForeground,
        focusedLabelColor: Color = FrogTheme.colors.foreground,
        disabledLabelColor: Color = FrogTheme.colors.mutedForeground.copy(alpha = 0.5f),
        errorLabelColor: Color = FrogTheme.colors.destructive,
        placeholderColor: Color = FrogTheme.colors.mutedForeground.copy(alpha = 0.7f),
        disabledPlaceholderColor: Color = FrogTheme.colors.mutedForeground.copy(alpha = 0.4f),
        helperTextColor: Color = FrogTheme.colors.mutedForeground,
        errorTextColor: Color = FrogTheme.colors.destructive,
        leadingIconColor: Color = FrogTheme.colors.mutedForeground,
        trailingIconColor: Color = FrogTheme.colors.mutedForeground,
        disabledLeadingIconColor: Color = FrogTheme.colors.mutedForeground.copy(alpha = 0.4f),
        disabledTrailingIconColor: Color = FrogTheme.colors.mutedForeground.copy(alpha = 0.4f),
    ): FrogTextFieldColors = FrogTextFieldColors(
        textColor = textColor,
        disabledTextColor = disabledTextColor,
        readOnlyTextColor = readOnlyTextColor,
        containerColor = containerColor,
        focusedContainerColor = focusedContainerColor,
        disabledContainerColor = disabledContainerColor,
        cursorColor = cursorColor,
        indicatorColor = indicatorColor,
        focusedIndicatorColor = focusedIndicatorColor,
        disabledIndicatorColor = disabledIndicatorColor,
        errorIndicatorColor = errorIndicatorColor,
        labelColor = labelColor,
        focusedLabelColor = focusedLabelColor,
        disabledLabelColor = disabledLabelColor,
        errorLabelColor = errorLabelColor,
        placeholderColor = placeholderColor,
        disabledPlaceholderColor = disabledPlaceholderColor,
        helperTextColor = helperTextColor,
        errorTextColor = errorTextColor,
        leadingIconColor = leadingIconColor,
        trailingIconColor = trailingIconColor,
        disabledLeadingIconColor = disabledLeadingIconColor,
        disabledTrailingIconColor = disabledTrailingIconColor,
    )
}
