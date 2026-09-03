package io.github.codewitheswar.frogui.showcase.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.registry.*
import io.github.codewitheswar.frogui.showcase.icons.FrogIcons
import io.github.codewitheswar.frogui.showcase.style.*
import io.github.codewitheswar.frogui.theme.FrogTheme

@Composable
fun ComponentsScreen(onSelectComponent: (String) -> Unit, modifier: Modifier = Modifier) {
    var query by rememberSaveable { mutableStateOf("") }
    var category by rememberSaveable { mutableStateOf<FrogComponentCategory?>(null) }
    val components = remember(query, category) { FrogComponentRegistry.search(query, category) }
    val colors = FrogTheme.colors
    LazyColumn(modifier.fillMaxSize(), contentPadding = PaddingValues(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            OutlinedTextField(query, { query = it }, Modifier.fillMaxWidth(), label = { Text("Search components") }, singleLine = true,
                textStyle = FrogTheme.typography.body, shape = FrogTheme.shapes.sm,
                leadingIcon = { Icon(FrogIcons.Search, null, Modifier.size(20.dp)) },
                trailingIcon = if (query.isNotEmpty()) ({ ShowcaseIconButton(FrogIcons.Close, "Clear search", { query = "" }) }) else null,
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = colors.focusRing, unfocusedBorderColor = colors.border, focusedContainerColor = colors.surface, unfocusedContainerColor = colors.surface))
        }
        item { FlowRow(Modifier.fillMaxWidth().selectableGroup()) {
            ShowcaseChoice("All", category == null, { category = null })
            FrogComponentRegistry.allComponents.map { it.category }.distinct().forEach { value -> ShowcaseChoice(value.displayName, category == value, { category = value }) }
        } }
        item { Text("${components.size} ${if (components.size == 1) "component" else "components"}", style = FrogTheme.typography.bodySmall, color = colors.mutedForeground) }
        if (components.isEmpty()) item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("No components found", style = FrogTheme.typography.heading, color = colors.foreground)
                Text("Try another name or clear the category filter.", style = FrogTheme.typography.body, color = colors.mutedForeground)
            }
        }
        items(components, key = { it.id }) { component -> ComponentListItem(component, query) { onSelectComponent(component.id) } }
    }
}

@Composable
private fun ComponentListItem(component: FrogComponentMetadata, query: String, onClick: () -> Unit) {
    val colors = FrogTheme.colors
    val source = remember { MutableInteractionSource() }
    Column(Modifier.fillMaxWidth().background(colors.surfaceElevated, FrogTheme.shapes.md).border(1.dp, colors.border, FrogTheme.shapes.md)
        .showcaseFocus(source).clickable(interactionSource = source, indication = null, role = Role.Button, onClick = onClick).padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(searchText(component.displayName, query), Modifier.weight(1f), style = FrogTheme.typography.heading, color = colors.foreground)
            Icon(FrogIcons.Forward, null, Modifier.size(20.dp), tint = colors.mutedForeground)
        }
        Text(searchText(component.description, query), style = FrogTheme.typography.bodySmall, color = colors.mutedForeground)
        Text(searchText(component.name, query), style = FrogTheme.typography.code, color = colors.mutedForeground)
        Text("${component.category.displayName} · ${component.status.label}", style = FrogTheme.typography.label, color = colors.mutedForeground)
    }
}

@Composable
private fun searchText(text: String, query: String) = buildAnnotatedString {
    append(text)
    if (query.isNotBlank()) {
        var index = text.indexOf(query, ignoreCase = true)
        while (index >= 0) {
            addStyle(SpanStyle(background = FrogTheme.colors.muted, color = FrogTheme.colors.foreground, fontWeight = FontWeight.SemiBold), index, index + query.length)
            index = text.indexOf(query, index + query.length, ignoreCase = true)
        }
    }
}
