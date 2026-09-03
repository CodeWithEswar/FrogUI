package io.github.codewitheswar.frogui.showcase.components.button

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.components.button.FrogButtonSize
import io.github.codewitheswar.frogui.components.button.FrogButtonVariant
import io.github.codewitheswar.frogui.showcase.icons.FrogIcons
import io.github.codewitheswar.frogui.showcase.style.showcaseFocus
import io.github.codewitheswar.frogui.theme.FrogTheme
import io.github.codewitheswar.frogui.showcase.inspector.*
import io.github.codewitheswar.frogui.showcase.colorpicker.*
import io.github.codewitheswar.frogui.foundation.color.FrogColors

@Composable
internal fun PropertyInspector(state: ButtonDemoState, onStateChange: (ButtonDemoState) -> Unit, modifier: Modifier = Modifier,
    onColor: ((ButtonColorProperty) -> Unit)? = null, tokenColors: FrogColors = FrogTheme.colors) {
    val colors = FrogTheme.colors
    Column(modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(20.dp)) {
        Text("Properties", style = FrogTheme.typography.heading, color = colors.foreground)
        FrogInspectorSection("Appearance") {
            ButtonQuickControls(state, onStateChange)
            Text("Changing variant restores its default colors.", style = FrogTheme.typography.bodySmall, color = colors.mutedForeground)
            FrogBooleanSelector("Full width", state.fullWidth) { onStateChange(state.copy(fullWidth = it)) }
            FrogEnumSelector("Shape", ButtonShape.entries.map { it.name }, state.shape.name) { onStateChange(state.copy(shape = ButtonShape.valueOf(it))) }
        }
        FrogInspectorSection("Content") {
            OutlinedTextField(state.buttonText, { onStateChange(state.copy(buttonText = it)) }, Modifier.fillMaxWidth(),
                label = { Text("Button label") }, singleLine = true, textStyle = FrogTheme.typography.bodySmall,
                shape = FrogTheme.shapes.sm, colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = colors.focusRing, unfocusedBorderColor = colors.border))
            FrogBooleanSelector("Leading icon", state.hasLeadingIcon) { onStateChange(state.copy(hasLeadingIcon = it)) }
            FrogBooleanSelector("Trailing icon", state.hasTrailingIcon) { onStateChange(state.copy(hasTrailingIcon = it)) }
        }
        FrogInspectorSection("State") {
            FrogBooleanSelector("Enabled", state.enabled) { onStateChange(state.copy(enabled = it)) }
            FrogBooleanSelector("Loading", state.loading) { onStateChange(state.copy(loading = it)) }
        }
        if (onColor != null) FrogInspectorSection("Colors") {
            ButtonColorProperty.entries.forEach { property ->
                val value = state.colorValue(property)
                val resolved = value.resolve(tokenColors)
                FrogInspectorRow(property.label, when (value) {
                    is FrogColorValue.Token -> value.token.label + if (value.alpha != null) " · ${(value.alpha * 100).toInt()}%" else ""
                    is FrogColorValue.Custom -> if (value.argb == 0L) "Transparent" else resolved.hex()
                }, { onColor(property) }, leading = { ColorSwatch(resolved) })
            }
            TextButton(onClick = { onStateChange(state.resetColors()) }, enabled = state.colorOverrides.isNotEmpty()) {
                Icon(FrogIcons.Reset, null, Modifier.size(16.dp)); Spacer(Modifier.width(6.dp)); Text("Reset colors")
            }
        }
    }
}

@Composable
internal fun ButtonQuickControls(state: ButtonDemoState, onStateChange: (ButtonDemoState) -> Unit) {
    val colors = FrogTheme.colors
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text("Variant", style = FrogTheme.typography.label, color = colors.mutedForeground)
        Row(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()).selectableGroup(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            FrogButtonVariant.entries.forEach { value ->
                InspectorChoice(value.name, state.variant == value, { onStateChange(state.withVariant(value)) }, Modifier.widthIn(min = 84.dp), showCheck = true)
            }
        }
        Text("Size", style = FrogTheme.typography.label, color = colors.mutedForeground)
        Row(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()).selectableGroup(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            FrogButtonSize.entries.forEach { value -> InspectorChoice(value.name, state.size == value, { onStateChange(state.copy(size = value)) }, Modifier.widthIn(min = 64.dp)) }
        }
    }
}

/** Compact surfaces retain 48dp touch targets; the reserved check slot prevents label jumps. */
@Composable
private fun InspectorChoice(label: String, selected: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier, showCheck: Boolean = false) {
    val colors = FrogTheme.colors
    val source = remember { MutableInteractionSource() }
    val shape = FrogTheme.shapes.sm
    val selectedFill = if (showCheck) colors.muted else colors.primary
    val foreground = if (selected && !showCheck) colors.primaryForeground else colors.foreground
    val pressed by source.collectIsPressedAsState()
    val fill = if (selected) selectedFill else colors.surface
    Row(modifier.heightIn(min = 48.dp)
        .selectable(selected, interactionSource = source, indication = null, role = Role.RadioButton, onClick = onClick)
        .padding(vertical = 6.dp).heightIn(min = 36.dp).showcaseFocus(source)
        .background(if (pressed) lerp(fill, foreground, .08f) else fill, shape)
        .border(1.dp, if (selected) colors.foreground else colors.borderStrong, shape)
        .padding(horizontal = 8.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = if (showCheck) Arrangement.spacedBy(4.dp) else Arrangement.Center) {
        Text(label, style = FrogTheme.typography.bodySmall, maxLines = 1, softWrap = false,
            fontWeight = FontWeight.Medium, color = foreground)
        if (showCheck) Icon(FrogIcons.Check, null, Modifier.size(14.dp).alpha(if (selected) 1f else 0f), tint = foreground)
    }
}
