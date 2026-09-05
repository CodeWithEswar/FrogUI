package io.github.codewitheswar.frogui.components.textfield

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Immutable color configuration for [FrogTextField] across default, focused, disabled, read-only, and error states.
 */
@Immutable
public data class FrogTextFieldColors(
    val textColor: Color,
    val disabledTextColor: Color,
    val readOnlyTextColor: Color,
    val containerColor: Color,
    val focusedContainerColor: Color,
    val disabledContainerColor: Color,
    val cursorColor: Color,
    val indicatorColor: Color,
    val focusedIndicatorColor: Color,
    val disabledIndicatorColor: Color,
    val errorIndicatorColor: Color,
    val labelColor: Color,
    val focusedLabelColor: Color,
    val disabledLabelColor: Color,
    val errorLabelColor: Color,
    val placeholderColor: Color,
    val disabledPlaceholderColor: Color,
    val helperTextColor: Color,
    val errorTextColor: Color,
    val leadingIconColor: Color,
    val trailingIconColor: Color,
    val disabledLeadingIconColor: Color,
    val disabledTrailingIconColor: Color,
)
