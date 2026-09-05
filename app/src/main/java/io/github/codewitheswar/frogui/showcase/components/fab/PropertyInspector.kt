package io.github.codewitheswar.frogui.showcase.components.fab

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.components.fab.FrogFabPresentation
import io.github.codewitheswar.frogui.foundation.color.FrogColors
import io.github.codewitheswar.frogui.showcase.colorpicker.ColorSwatch
import io.github.codewitheswar.frogui.showcase.colorpicker.FrogColorValue
import io.github.codewitheswar.frogui.showcase.colorpicker.hex
import io.github.codewitheswar.frogui.showcase.icons.FrogIcons
import io.github.codewitheswar.frogui.showcase.inspector.FrogBooleanSelector
import io.github.codewitheswar.frogui.showcase.inspector.FrogEnumSelector
import io.github.codewitheswar.frogui.showcase.inspector.FrogInspectorRow
import io.github.codewitheswar.frogui.showcase.inspector.FrogInspectorSection
import io.github.codewitheswar.frogui.showcase.style.showcaseFocus
import io.github.codewitheswar.frogui.theme.FrogTheme

@Composable
internal fun PropertyInspector(
    state: FabDemoState,
    onStateChange: (FabDemoState) -> Unit,
    modifier: Modifier = Modifier,
    onColor: ((FabColorProperty) -> Unit)? = null,
    tokenColors: FrogColors = FrogTheme.colors
) {
    val colors = FrogTheme.colors
    Column(modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(20.dp)) {
        Text("Properties", style = FrogTheme.typography.heading, color = colors.foreground)

        FrogInspectorSection("Appearance") {
            FabQuickControls(state, onStateChange)
            FrogEnumSelector(
                "Elevation",
                listOf("Low (1dp)", "Medium (3dp)", "High (6dp)"),
                when (state.elevationDp) {
                    1 -> "Low (1dp)"
                    6 -> "High (6dp)"
                    else -> "Medium (3dp)"
                }
            ) { selected ->
                val dp = when (selected) {
                    "Low (1dp)" -> 1
                    "High (6dp)" -> 6
                    else -> 3
                }
                onStateChange(state.copy(elevationDp = dp))
            }
        }

        FrogInspectorSection("Content") {
            FrogEnumSelector("Demo icon", FabDemoIcon.entries.map { it.name }, state.icon.name) {
                onStateChange(state.copy(icon = FabDemoIcon.valueOf(it)))
            }
            if (state.presentation == FrogFabPresentation.Extended) {
                FrogInspectorRow("Label text", state.labelText, {
                    val next = when (state.labelText) {
                        "Create" -> "Compose message"
                        "Compose message" -> "New item"
                        "New item" -> "Scan document"
                        else -> "Create"
                    }
                    onStateChange(state.copy(labelText = next))
                })
            }
        }

        FrogInspectorSection("State") {
            if (state.presentation == FrogFabPresentation.Extended) {
                FrogBooleanSelector("Expanded", state.expanded) { onStateChange(state.copy(expanded = it)) }
            }
            FrogBooleanSelector("Visible", state.visible) { onStateChange(state.copy(visible = it)) }
            FrogBooleanSelector("Enabled", state.enabled) { onStateChange(state.copy(enabled = it)) }
        }

        if (onColor != null) {
            FrogInspectorSection("Colors") {
                FabColorProperty.entries.forEach { property ->
                    val value = state.colorValue(property)
                    val resolved = value.resolve(tokenColors)
                    FrogInspectorRow(
                        property.label,
                        when (value) {
                            is FrogColorValue.Token -> value.token.label + if (value.alpha != null) " · ${(value.alpha * 100).toInt()}%" else ""
                            is FrogColorValue.Custom -> if (value.argb == 0L) "Transparent" else resolved.hex()
                        },
                        { onColor(property) },
                        leading = { ColorSwatch(resolved) }
                    )
                }
                TextButton(
                    onClick = { onStateChange(state.resetColors()) },
                    enabled = state.colorOverrides.isNotEmpty()
                ) {
                    Icon(FrogIcons.Reset, null, Modifier.size(16.dp))
                    Spacer(Modifier.width(6.dp))
                    Text("Reset colors")
                }
            }
        }
    }
}

@Composable
internal fun FabQuickControls(
    state: FabDemoState,
    onStateChange: (FabDemoState) -> Unit
) {
    val colors = FrogTheme.colors
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text("Presentation", style = FrogTheme.typography.label, color = colors.mutedForeground)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .selectableGroup(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            FrogFabPresentation.entries.forEach { value ->
                InspectorChoice(
                    label = value.name,
                    selected = state.presentation == value,
                    onClick = { onStateChange(state.withPresentation(value)) },
                    modifier = Modifier.widthIn(min = 84.dp),
                    showCheck = true
                )
            }
        }
    }
}

@Composable
private fun InspectorChoice(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    showCheck: Boolean = false
) {
    val colors = FrogTheme.colors
    val source = remember { MutableInteractionSource() }
    val shape = FrogTheme.shapes.sm
    val selectedFill = if (showCheck) colors.muted else colors.primary
    val foreground = if (selected && !showCheck) colors.primaryForeground else colors.foreground
    val pressed by source.collectIsPressedAsState()
    val fill = if (selected) selectedFill else colors.surface
    Row(
        modifier = modifier
            .heightIn(min = FrogTheme.sizing.minimumTouchTarget)
            .selectable(selected, interactionSource = source, indication = null, role = Role.RadioButton, onClick = onClick)
            .padding(vertical = 6.dp)
            .heightIn(min = 36.dp)
            .showcaseFocus(source)
            .background(if (pressed) lerp(fill, foreground, 0.08f) else fill, shape)
            .border(1.dp, if (selected) colors.foreground else colors.borderStrong, shape)
            .padding(horizontal = 8.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = if (showCheck) Arrangement.spacedBy(4.dp) else Arrangement.Center
    ) {
        Text(
            text = label,
            style = FrogTheme.typography.bodySmall,
            maxLines = 1,
            softWrap = false,
            fontWeight = FontWeight.Medium,
            color = foreground
        )
        if (showCheck) {
            Icon(
                imageVector = FrogIcons.Check,
                contentDescription = null,
                modifier = Modifier.size(14.dp).alpha(if (selected) 1f else 0f),
                tint = foreground
            )
        }
    }
}
