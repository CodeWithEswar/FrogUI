package io.github.codewitheswar.frogui.showcase.inspector

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.components.button.FrogButtonSize
import io.github.codewitheswar.frogui.components.button.FrogButtonVariant
import io.github.codewitheswar.frogui.foundation.color.FrogPalette
import io.github.codewitheswar.frogui.foundation.theme.FrogTheme

/**
 * Signature FrogUI Property Inspector.
 * High-density developer-tool control panel for manipulating component props in real time.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PropertyInspector(
    state: ButtonDemoState,
    onStateChange: (ButtonDemoState) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = FrogTheme.colors
    val shapes = FrogTheme.shapes
    val spacing = FrogTheme.spacing

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shapes.lg)
            .background(colors.surfaceElevated)
            .border(1.dp, colors.borderStrong, shapes.lg)
            .padding(spacing.md),
        verticalArrangement = Arrangement.spacedBy(spacing.md)
    ) {
        // Section Header
        Text(
            text = "PROPERTIES INSPECTOR",
            style = FrogTheme.typography.caption,
            color = colors.mutedForeground
        )

        // 1. APPEARANCE - Variant
        Column(verticalArrangement = Arrangement.spacedBy(spacing.xs)) {
            Text(
                text = "Variant",
                style = FrogTheme.typography.label,
                color = colors.foreground
            )
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(spacing.xs),
                verticalArrangement = Arrangement.spacedBy(spacing.xs)
            ) {
                FrogButtonVariant.entries.forEach { v ->
                    val isSelected = state.variant == v
                    Box(
                        modifier = Modifier
                            .clip(shapes.sm)
                            .background(if (isSelected) colors.primary else colors.subtleSurface)
                            .border(
                                1.dp,
                                if (isSelected) colors.primary else colors.border,
                                shapes.sm
                            )
                            .clickable { onStateChange(state.copy(variant = v)) }
                            .padding(horizontal = spacing.md, vertical = 6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = v.name,
                            style = FrogTheme.typography.caption,
                            color = if (isSelected) colors.primaryForeground else colors.foreground
                        )
                    }
                }
            }
        }

        // 2. APPEARANCE - Size
        Column(verticalArrangement = Arrangement.spacedBy(spacing.xs)) {
            Text(
                text = "Size",
                style = FrogTheme.typography.label,
                color = colors.foreground
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(spacing.xs)
            ) {
                FrogButtonSize.entries.forEach { s ->
                    val isSelected = state.size == s
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(shapes.sm)
                            .background(if (isSelected) colors.primary else colors.subtleSurface)
                            .border(
                                1.dp,
                                if (isSelected) colors.primary else colors.border,
                                shapes.sm
                            )
                            .clickable { onStateChange(state.copy(size = s)) }
                            .padding(vertical = 6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = s.name,
                            style = FrogTheme.typography.caption,
                            color = if (isSelected) colors.primaryForeground else colors.foreground
                        )
                    }
                }
            }
        }

        // 3. STATE CONTROLS
        Column(verticalArrangement = Arrangement.spacedBy(spacing.xs)) {
            Text(
                text = "State",
                style = FrogTheme.typography.label,
                color = colors.foreground
            )

            InspectorSwitchRow(
                title = "Enabled",
                checked = state.enabled,
                onCheckedChange = { onStateChange(state.copy(enabled = it)) }
            )

            InspectorSwitchRow(
                title = "Loading",
                checked = state.loading,
                onCheckedChange = { onStateChange(state.copy(loading = it)) }
            )
        }

        // 4. CONTENT & SLOTS
        Column(verticalArrangement = Arrangement.spacedBy(spacing.xs)) {
            Text(
                text = "Content & Slots",
                style = FrogTheme.typography.label,
                color = colors.foreground
            )

            InspectorSwitchRow(
                title = "Leading Icon",
                checked = state.hasLeadingIcon,
                onCheckedChange = { onStateChange(state.copy(hasLeadingIcon = it)) }
            )

            InspectorSwitchRow(
                title = "Trailing Icon",
                checked = state.hasTrailingIcon,
                onCheckedChange = { onStateChange(state.copy(hasTrailingIcon = it)) }
            )

            InspectorSwitchRow(
                title = "Full Width",
                checked = state.fullWidth,
                onCheckedChange = { onStateChange(state.copy(fullWidth = it)) }
            )

            // Button label text field
            OutlinedTextField(
                value = state.buttonText,
                onValueChange = { onStateChange(state.copy(buttonText = it)) },
                label = { Text("Button Label", style = FrogTheme.typography.caption) },
                singleLine = true,
                textStyle = FrogTheme.typography.bodySmall,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colors.primary,
                    unfocusedBorderColor = colors.border,
                    focusedTextColor = colors.foreground,
                    unfocusedTextColor = colors.foreground
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = spacing.xs)
            )
        }
    }
}

@Composable
private fun InspectorSwitchRow(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val colors = FrogTheme.colors

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(FrogTheme.shapes.sm)
            .background(colors.subtleSurface)
            .padding(horizontal = FrogTheme.spacing.md, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = FrogTheme.typography.bodySmall,
            color = colors.foreground
        )

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.size(width = 36.dp, height = 24.dp),
            colors = SwitchDefaults.colors(
                checkedThumbColor = colors.primaryForeground,
                checkedTrackColor = colors.primary,
                uncheckedThumbColor = colors.mutedForeground,
                uncheckedTrackColor = colors.muted
            )
        )
    }
}
