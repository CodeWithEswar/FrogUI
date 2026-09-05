package io.github.codewitheswar.frogui.showcase.components.iconbutton

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.components.button.FrogIconButtonSize
import io.github.codewitheswar.frogui.components.button.FrogIconButtonVariant
import io.github.codewitheswar.frogui.foundation.color.FrogColors
import io.github.codewitheswar.frogui.showcase.colorpicker.ColorSwatch
import io.github.codewitheswar.frogui.showcase.colorpicker.FrogColorValue
import io.github.codewitheswar.frogui.showcase.colorpicker.hex
import io.github.codewitheswar.frogui.showcase.icons.FrogIcons
import io.github.codewitheswar.frogui.showcase.inspector.*
import io.github.codewitheswar.frogui.showcase.style.showcaseFocus
import io.github.codewitheswar.frogui.theme.FrogTheme

@Composable
internal fun PropertyInspector(
    state: IconButtonDemoState,
    onStateChange: (IconButtonDemoState) -> Unit,
    modifier: Modifier = Modifier,
    onColor: ((IconButtonColorProperty) -> Unit)? = null,
    tokenColors: FrogColors = FrogTheme.colors
) {
    val colors = FrogTheme.colors
    Column(modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(20.dp)) {
        Text("Properties", style = FrogTheme.typography.heading, color = colors.foreground)

        FrogInspectorSection("Appearance") {
            IconButtonQuickControls(state, onStateChange)
            Text("Changing variant restores its default colors.", style = FrogTheme.typography.bodySmall, color = colors.mutedForeground)
        }

        FrogInspectorSection("Content") {
            FrogEnumSelector("Demo icon", IconButtonDemoIcon.entries.map { it.name }, state.icon.name) {
                onStateChange(state.copy(icon = IconButtonDemoIcon.valueOf(it)))
            }
            FrogEnumSelector("Badge", IconButtonDemoBadge.entries.map { it.name }, state.badge.name) {
                onStateChange(state.copy(badge = IconButtonDemoBadge.valueOf(it)))
            }
            if (state.badge == IconButtonDemoBadge.Count) {
                FrogInspectorRow("Badge count", "${state.badgeCount}", {
                    val next = if (state.badgeCount >= 9) 1 else state.badgeCount + 1
                    onStateChange(state.copy(badgeCount = next))
                })
            }
        }

        FrogInspectorSection("State") {
            FrogBooleanSelector("Enabled", state.enabled) { onStateChange(state.copy(enabled = it)) }
            FrogBooleanSelector("Loading", state.loading) { onStateChange(state.copy(loading = it)) }
        }

        if (onColor != null) {
            FrogInspectorSection("Colors") {
                IconButtonColorProperty.entries.forEach { property ->
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
internal fun IconButtonQuickControls(
    state: IconButtonDemoState,
    onStateChange: (IconButtonDemoState) -> Unit
) {
    val colors = FrogTheme.colors
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text("Variant", style = FrogTheme.typography.label, color = colors.mutedForeground)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .selectableGroup(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            FrogIconButtonVariant.entries.forEach { value ->
                InspectorChoice(
                    label = value.name,
                    selected = state.variant == value,
                    onClick = { onStateChange(state.withVariant(value)) },
                    modifier = Modifier.widthIn(min = 84.dp),
                    showCheck = true
                )
            }
        }
        Text("Size", style = FrogTheme.typography.label, color = colors.mutedForeground)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .selectableGroup(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            FrogIconButtonSize.entries.forEach { value ->
                InspectorChoice(
                    label = value.name,
                    selected = state.size == value,
                    onClick = { onStateChange(state.copy(size = value)) },
                    modifier = Modifier.widthIn(min = 64.dp)
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
