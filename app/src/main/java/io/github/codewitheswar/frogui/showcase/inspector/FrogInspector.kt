package io.github.codewitheswar.frogui.showcase.inspector

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.semantics.*
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.showcase.icons.FrogIcons
import io.github.codewitheswar.frogui.showcase.style.showcaseFocus
import io.github.codewitheswar.frogui.theme.FrogTheme

import io.github.codewitheswar.frogui.components.textfield.FrogTextField
import io.github.codewitheswar.frogui.components.textfield.FrogTextFieldVariant

@Composable
internal fun FrogInspectorText(label: String, value: String, onChange: (String) -> Unit) {
    FrogTextField(
        value = value,
        onValueChange = onChange,
        label = label,
        variant = FrogTextFieldVariant.Outline,
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
internal fun FrogInspectorSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(title.uppercase(), style = FrogTheme.typography.label, color = FrogTheme.colors.mutedForeground, modifier = Modifier.semantics { heading() })
        HorizontalDivider(color = FrogTheme.colors.border)
        content()
    }
}

@Composable
internal fun FrogInspectorRow(label: String, value: String, onClick: () -> Unit, modifier: Modifier = Modifier,
    description: String? = null, leading: (@Composable () -> Unit)? = null) {
    val source = remember { MutableInteractionSource() }
    Row(modifier.fillMaxWidth().heightIn(min = 56.dp).showcaseFocus(source)
        .clickable(source, indication = null, role = Role.Button, onClick = onClick).padding(vertical = 8.dp, horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        leading?.invoke()
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
            Text(label, style = FrogTheme.typography.bodySmall, color = FrogTheme.colors.foreground)
            Text(value, style = FrogTheme.typography.code, color = FrogTheme.colors.mutedForeground)
            description?.let { Text(it, style = FrogTheme.typography.bodySmall, color = FrogTheme.colors.mutedForeground) }
        }
        Icon(FrogIcons.Forward, null, Modifier.size(FrogTheme.sizing.iconSmall), tint = FrogTheme.colors.mutedForeground)
    }
}

@Composable
internal fun FrogEnumSelector(label: String, values: List<String>, selected: String, onSelect: (String) -> Unit) {
    Column {
        Text(label, style = FrogTheme.typography.label, color = FrogTheme.colors.mutedForeground)
        Row(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()).selectableGroup(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            values.forEach { value ->
                val source = remember { MutableInteractionSource() }
                val active = value == selected
                Box(Modifier.widthIn(min = 64.dp).heightIn(min = FrogTheme.sizing.minimumTouchTarget)
                    .selectable(active, source, indication = null, role = Role.RadioButton, onClick = { onSelect(value) })
                    .padding(vertical = 6.dp).heightIn(min = 36.dp).showcaseFocus(source)
                    .background(if (active) FrogTheme.colors.primary else FrogTheme.colors.surface, FrogTheme.shapes.sm)
                    .border(1.dp, if (active) FrogTheme.colors.foreground else FrogTheme.colors.borderStrong, FrogTheme.shapes.sm)
                    .padding(horizontal = 8.dp, vertical = 6.dp), contentAlignment = Alignment.Center) {
                    Text(value, maxLines = 1, softWrap = false, style = FrogTheme.typography.bodySmall, color = if (active) FrogTheme.colors.primaryForeground else FrogTheme.colors.foreground)
                }
            }
        }
    }
}

@Composable
internal fun FrogBooleanSelector(title: String, checked: Boolean, onChange: (Boolean) -> Unit) {
    val source = remember { MutableInteractionSource() }
    Row(Modifier.fillMaxWidth().heightIn(min = FrogTheme.sizing.minimumTouchTarget).showcaseFocus(source)
        .toggleable(checked, source, indication = null, role = Role.Switch, onValueChange = onChange).padding(horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(title, Modifier.weight(1f), style = FrogTheme.typography.bodySmall, color = FrogTheme.colors.foreground)
        Box(Modifier.size(44.dp, 28.dp), contentAlignment = Alignment.Center) {
            Switch(checked, onCheckedChange = null, modifier = Modifier.requiredSize(52.dp, 32.dp).scale(.8f))
        }
    }
}
