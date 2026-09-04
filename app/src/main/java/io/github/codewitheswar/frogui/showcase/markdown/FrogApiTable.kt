package io.github.codewitheswar.frogui.showcase.markdown

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.registry.ComponentPropertyMetadata
import io.github.codewitheswar.frogui.theme.FrogTheme

@Composable
internal fun FrogApiTable(properties: List<ComponentPropertyMetadata>, modifier: Modifier = Modifier,
    onPropertyClick: ((ComponentPropertyMetadata) -> Unit)? = null, tagged: Boolean = true) {
    val colors = FrogTheme.colors
    BoxWithConstraints(modifier.fillMaxWidth()) {
        val table = maxWidth >= 680.dp
        Column(if (tagged) Modifier.testTag(if (table) "api-table" else "api-stacked") else Modifier) {
            if (table) Row(Modifier.fillMaxWidth().background(colors.muted).padding(12.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                listOf("Property", "Type", "Default", "Description").forEachIndexed { index, label -> Text(label, Modifier.weight(if (index == 3) 1.5f else 1f), style = FrogTheme.typography.label, color = colors.foreground) }
            }
            properties.forEach { property ->
                val action = if (onPropertyClick != null) Modifier.clickable(role = Role.Button, onClickLabel = "Inspect ${property.name}") { onPropertyClick(property) } else Modifier
                if (table) Row(Modifier.fillMaxWidth().heightIn(min = FrogTheme.sizing.minimumTouchTarget).then(action).padding(12.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(property.name, Modifier.weight(1f), style = FrogTheme.typography.code, color = colors.foreground)
                    Text(property.type, Modifier.weight(1f), style = FrogTheme.typography.code, color = colors.mutedForeground)
                    Text(property.defaultValue, Modifier.weight(1f), style = FrogTheme.typography.code, color = colors.mutedForeground)
                    Text(property.description, Modifier.weight(1.5f), style = FrogTheme.typography.bodySmall, color = colors.foreground)
                } else Column(Modifier.fillMaxWidth().then(action).padding(vertical = 14.dp), verticalArrangement = Arrangement.spacedBy(5.dp)) {
                    Text(property.name, style = FrogTheme.typography.subheading, color = colors.foreground)
                    Text(property.type, style = FrogTheme.typography.code, color = colors.mutedForeground)
                    Text("Default: ${property.defaultValue}", style = FrogTheme.typography.code, color = colors.mutedForeground)
                    Text(property.description, style = FrogTheme.typography.bodySmall, color = colors.foreground)
                    if (onPropertyClick != null) Text("View guidance and example", style = FrogTheme.typography.label, color = colors.foreground)
                }
                HorizontalDivider(color = colors.border)
            }
        }
    }
}
