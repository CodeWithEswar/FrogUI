package io.github.codewitheswar.frogui.showcase.components.textfield

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.components.textfield.FrogTextFieldVariant
import io.github.codewitheswar.frogui.showcase.inspector.FrogBooleanSelector
import io.github.codewitheswar.frogui.showcase.inspector.FrogEnumSelector
import io.github.codewitheswar.frogui.showcase.inspector.FrogInspectorSection
import io.github.codewitheswar.frogui.showcase.inspector.FrogInspectorText

@Composable
internal fun PropertyInspector(
    state: TextFieldDemoState,
    onChange: (TextFieldDemoState) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        // Appearance
        FrogInspectorSection(title = "Appearance") {
            FrogEnumSelector(
                label = "Variant",
                values = listOf("Filled", "Outline", "Underline"),
                selected = state.variant.name,
                onSelect = { selected ->
                    onChange(state.copy(variant = FrogTextFieldVariant.valueOf(selected)))
                }
            )
        }

        // Content
        FrogInspectorSection(title = "Content") {
            FrogInspectorText(
                label = "Input value",
                value = state.value,
                onChange = { onChange(state.copy(value = it)) },
            )
            FrogInspectorText(
                label = "Label",
                value = state.label,
                onChange = { onChange(state.copy(label = it)) },
            )
            FrogInspectorText(
                label = "Placeholder",
                value = state.placeholder,
                onChange = { onChange(state.copy(placeholder = it)) },
            )
            FrogInspectorText(
                label = "Helper text",
                value = state.helperText,
                onChange = { onChange(state.copy(helperText = it)) },
            )

            FrogBooleanSelector(
                title = "Leading icon",
                checked = state.leadingEnabled,
                onChange = { onChange(state.copy(leadingEnabled = it)) },
            )
            FrogBooleanSelector(
                title = "Trailing clear action",
                checked = state.trailingEnabled,
                onChange = { onChange(state.copy(trailingEnabled = it)) },
            )
        }

        // State & Validation
        FrogInspectorSection(title = "State & Validation") {
            val errorPreset = when (state.errorText) {
                null -> "None"
                "This field is required" -> "Required"
                "Enter a valid email address" -> "Invalid email"
                else -> "Custom error"
            }

            FrogEnumSelector(
                label = "Validation preset",
                values = listOf("None", "Required", "Invalid email"),
                selected = if (errorPreset in listOf("None", "Required", "Invalid email")) errorPreset else "None",
                onSelect = { selected ->
                    val newError = when (selected) {
                        "Required" -> "This field is required"
                        "Invalid email" -> "Enter a valid email address"
                        else -> null
                    }
                    onChange(state.copy(errorText = newError))
                }
            )

            FrogBooleanSelector(
                title = "Enabled",
                checked = state.enabled,
                onChange = { onChange(state.copy(enabled = it)) },
            )
            FrogBooleanSelector(
                title = "Read only",
                checked = state.readOnly,
                onChange = { onChange(state.copy(readOnly = it)) },
            )
        }

        // Behavior
        FrogInspectorSection(title = "Behavior") {
            FrogBooleanSelector(
                title = "Single line",
                checked = state.singleLine,
                onChange = { isSingle ->
                    onChange(
                        state.copy(
                            singleLine = isSingle,
                            maxLines = if (isSingle) 1 else 4,
                        )
                    )
                },
            )

            if (!state.singleLine) {
                FrogEnumSelector(
                    label = "Max lines",
                    values = listOf("2", "3", "4", "6"),
                    selected = state.maxLines.toString(),
                    onSelect = { lines ->
                        onChange(state.copy(maxLines = lines.toIntOrNull() ?: 4))
                    }
                )
            }
        }
    }
}
