package io.github.codewitheswar.frogui.components.textfield

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.semantics.error
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.theme.FrogTheme

/**
 * The canonical FrogUI editable text input component for forms and text entry.
 *
 * Implements a state-hoisted design where the caller owns [value] and [onValueChange].
 * Supports three visual presentations: [FrogTextFieldVariant.Filled], [FrogTextFieldVariant.Outline],
 * and [FrogTextFieldVariant.Underline].
 *
 * Features robust accessibility guarantees:
 * - Persistent or floating [label] association (does not rely on [placeholder] alone)
 * - Semantic error reporting via [errorText]
 * - Clear distinction between [enabled] and [readOnly] states
 * - Guaranteed minimum interactive touch height across all variants
 *
 * @param value Current text value owned by the caller.
 * @param onValueChange Callback invoked when proposed text modifications occur.
 * @param modifier Applied to the outer field container layout.
 * @param label Primary identifying text label associated with the field.
 * @param placeholder Secondary contextual hint displayed when [value] is empty.
 * @param helperText Non-error supporting information shown below the field when [errorText] is null.
 * @param errorText Validation error message shown below the field; activates error styling and semantics.
 * @param leading Composable slot rendered at the start of the field (e.g., search or mail icon).
 * @param trailing Composable slot rendered at the end of the field (e.g., clear button or unit suffix).
 * @param variant Visual presentation style: [FrogTextFieldVariant.Filled], [FrogTextFieldVariant.Outline],
 *   or [FrogTextFieldVariant.Underline].
 * @param enabled Controls interactive capability. When false, user editing is suppressed and disabled tones apply.
 * @param readOnly When true, text cannot be edited but remains focusable, selectable, and presented in readable tones.
 * @param singleLine When true, the field is constrained to a single horizontally scrolling line.
 * @param maxLines Maximum visual lines of text allowed. When [singleLine] is true, effectively 1.
 * @param keyboardOptions Software keyboard configuration (e.g., keyboard type, capitalization, IME action).
 * @param keyboardActions Callbacks invoked on IME action triggers (e.g., Done, Next, Search).
 * @param visualTransformation Formats the visual presentation of the text (e.g., masking or formatting).
 * @param interactionSource Hoisted [MutableInteractionSource] to observe focus and interaction events.
 * @param colors Color specification mapped to current [FrogTheme] tokens.
 * @param shape Geometry for the field container border and background.
 */
@Composable
public fun FrogTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String? = null,
    helperText: String? = null,
    errorText: String? = null,
    leading: (@Composable () -> Unit)? = null,
    trailing: (@Composable () -> Unit)? = null,
    variant: FrogTextFieldVariant = FrogTextFieldVariant.Filled,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    interactionSource: MutableInteractionSource? = null,
    colors: FrogTextFieldColors = FrogTextFieldDefaults.colors(variant),
    shape: Shape = FrogTextFieldDefaults.shape(variant),
) {
    val actualInteractionSource = interactionSource ?: remember { MutableInteractionSource() }
    val isFocused by actualInteractionSource.collectIsFocusedAsState()
    val isError = errorText != null

    val isReducedMotion = FrogTheme.motion.isReduced

    // Resolve active colors
    val currentTextColor = when {
        !enabled -> colors.disabledTextColor
        readOnly -> colors.readOnlyTextColor
        else -> colors.textColor
    }

    val currentContainerColor = when {
        !enabled -> colors.disabledContainerColor
        isFocused -> colors.focusedContainerColor
        else -> colors.containerColor
    }

    val currentIndicatorColor by animateColorAsState(
        targetValue = when {
            !enabled -> colors.disabledIndicatorColor
            isError -> colors.errorIndicatorColor
            isFocused -> colors.focusedIndicatorColor
            else -> colors.indicatorColor
        },
        animationSpec = if (isReducedMotion) snap() else tween(150),
        label = "FrogTextFieldIndicatorColor",
    )

    val currentLabelColor by animateColorAsState(
        targetValue = when {
            !enabled -> colors.disabledLabelColor
            isError -> colors.errorLabelColor
            isFocused -> colors.focusedLabelColor
            else -> colors.labelColor
        },
        animationSpec = if (isReducedMotion) snap() else tween(150),
        label = "FrogTextFieldLabelColor",
    )

    val indicatorThickness = FrogTextFieldDefaults.indicatorThickness(isFocused)

    // Label floating animation state
    val hasLabel = label != null
    val isFloated = !hasLabel || isFocused || value.isNotEmpty()
    val labelProgress by animateFloatAsState(
        targetValue = if (isFloated) 1f else 0f,
        animationSpec = if (isReducedMotion) snap() else tween(150),
        label = "FrogTextFieldLabelProgress",
    )

    Column(
        modifier = modifier
            .semantics {
                if (errorText != null) {
                    error(errorText)
                }
            },
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        // Main input container surface
        val containerModifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = FrogTextFieldDefaults.minHeight(variant))
            .then(
                when (variant) {
                    FrogTextFieldVariant.Filled -> Modifier
                        .clip(shape)
                        .background(currentContainerColor)
                        .border(
                            width = indicatorThickness,
                            color = currentIndicatorColor,
                            shape = shape,
                        )

                    FrogTextFieldVariant.Outline -> Modifier
                        .clip(shape)
                        .background(currentContainerColor)
                        .border(
                            width = indicatorThickness,
                            color = currentIndicatorColor,
                            shape = shape,
                        )

                    FrogTextFieldVariant.Underline -> Modifier
                        .background(currentContainerColor)
                        .drawBehind {
                            val strokeWidthPx = indicatorThickness.toPx()
                            val y = size.height - strokeWidthPx / 2f
                            drawLine(
                                color = currentIndicatorColor,
                                start = Offset(0f, y),
                                end = Offset(size.width, y),
                                strokeWidth = strokeWidthPx,
                            )
                        }
                }
            )

        Box(
            modifier = containerModifier,
            contentAlignment = Alignment.CenterStart,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        FrogTextFieldDefaults.contentPadding(
                            variant = variant,
                            hasLeading = leading != null,
                            hasTrailing = trailing != null,
                        )
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Leading content slot
                if (leading != null) {
                    Box(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(FrogTextFieldDefaults.IconSize),
                        contentAlignment = Alignment.Center,
                    ) {
                        CompositionLocalProvider(
                            LocalContentColor provides if (enabled) colors.leadingIconColor else colors.disabledLeadingIconColor
                        ) {
                            leading()
                        }
                    }
                }

                // Core editable text column with label and placeholder
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center,
                ) {
                    // Floating or stationary label
                    if (label != null) {
                        val labelAlpha = if (!hasLabel) 0f else 1f
                        val labelStyle = if (labelProgress > 0.5f) {
                            FrogTheme.typography.label
                        } else {
                            FrogTheme.typography.body
                        }

                        Text(
                            text = label,
                            style = labelStyle,
                            color = currentLabelColor,
                            maxLines = 1,
                            modifier = Modifier
                                .alpha(labelAlpha)
                                .padding(bottom = if (labelProgress > 0.5f) 2.dp else 0.dp),
                        )
                    }

                    // The actual BasicTextField box
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterStart,
                    ) {
                        // Placeholder slot (only visible when value is empty and field is focused or has no label)
                        if (value.isEmpty() && placeholder != null && isFloated) {
                            Text(
                                text = placeholder,
                                style = FrogTheme.typography.body,
                                color = if (enabled) colors.placeholderColor else colors.disabledPlaceholderColor,
                                maxLines = if (singleLine) 1 else maxLines,
                            )
                        }

                        // BasicTextField input
                        BasicTextField(
                            value = value,
                            onValueChange = onValueChange,
                            modifier = Modifier.fillMaxWidth(),
                            enabled = enabled,
                            readOnly = readOnly,
                            textStyle = FrogTheme.typography.body.copy(color = currentTextColor),
                            cursorBrush = SolidColor(colors.cursorColor),
                            singleLine = singleLine,
                            maxLines = if (singleLine) 1 else maxLines,
                            keyboardOptions = keyboardOptions,
                            keyboardActions = keyboardActions,
                            visualTransformation = visualTransformation,
                            interactionSource = actualInteractionSource,
                        )
                    }
                }

                // Trailing content slot
                if (trailing != null) {
                    Box(
                        modifier = Modifier
                            .padding(start = 8.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        CompositionLocalProvider(
                            LocalContentColor provides if (enabled) colors.trailingIconColor else colors.disabledTrailingIconColor
                        ) {
                            trailing()
                        }
                    }
                }
            }
        }

        // Supporting text area: Error takes priority over Helper
        val supportingMessage = errorText ?: helperText
        val isShowingError = errorText != null

        if (supportingMessage != null) {
            val supportingColor = if (isShowingError) {
                colors.errorTextColor
            } else {
                colors.helperTextColor
            }

            val supportingHorizontalPadding = if (variant == FrogTextFieldVariant.Underline) 0.dp else 4.dp

            Text(
                text = supportingMessage,
                style = FrogTheme.typography.bodySmall,
                color = supportingColor,
                modifier = Modifier.padding(horizontal = supportingHorizontalPadding, vertical = 2.dp),
            )
        }
    }
}
