package io.github.codewitheswar.frogui.showcase.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.registry.ComponentExampleMetadata
import io.github.codewitheswar.frogui.registry.ComponentPropertyMetadata
import io.github.codewitheswar.frogui.showcase.code.*
import io.github.codewitheswar.frogui.showcase.markdown.*
import io.github.codewitheswar.frogui.theme.FrogTheme

internal enum class ApiCategory { Core, Appearance, Content, State, Layout, Behavior, Style, Advanced }
internal data class ComponentApiValue(val name: String, val description: String)
internal data class ComponentApiProperty(val metadata: ComponentPropertyMetadata, val category: ApiCategory,
    val guidance: String, val example: String? = null, val values: List<ComponentApiValue> = emptyList()) {
    val required get() = metadata.defaultValue == "required"
}
internal data class AccessibilityFact(val title: String, val description: String, val verification: String = "Guidance")
internal data class ComponentAccessibilityInfo(val summary: String, val facts: List<AccessibilityFact>)

@Composable
internal fun ComponentDocSection(title: String, description: String? = null, content: @Composable ColumnScope.() -> Unit) {
    Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(FrogTheme.spacing.lg)) {
        Text(title, style = FrogTheme.typography.heading, color = FrogTheme.colors.foreground, modifier = Modifier.semantics { heading() })
        description?.let { Text(it, style = FrogTheme.typography.bodySmall, color = FrogTheme.colors.mutedForeground) }
        content()
    }
}

@Composable
internal fun ComponentCodeContent(code: String, name: String, note: String? = null) {
    ComponentDocSection("Generated usage", "Current configuration") {
        FrogCodeSnippet(code, filename = "$name.kt")
        note?.let { Text(it, style = FrogTheme.typography.bodySmall, color = FrogTheme.colors.mutedForeground) }
    }
}

@Composable
internal fun ComponentApiReference(name: String, properties: List<ComponentApiProperty>, onProperty: (String) -> Unit) {
    if (properties.isEmpty()) {
        ComponentDocSection("API") { Text("This item has no public API reference.", color = FrogTheme.colors.mutedForeground) }
        return
    }
    FrogCodeSnippet("@Composable\nfun $name(\n" + properties.joinToString(",\n") {
        "    ${it.metadata.name}: ${it.metadata.type}" + if (it.required) "" else " = ${it.metadata.defaultValue}"
    } + "\n)")
    BoxWithConstraints(Modifier.fillMaxWidth()) {
        Column(Modifier.testTag(if (maxWidth >= 680.dp) "api-table" else "api-stacked"), verticalArrangement = Arrangement.spacedBy(FrogTheme.spacing.xxl)) {
            properties.groupBy { it.category }.toSortedMap().forEach { (category, entries) ->
                ComponentDocSection(category.name) {
                    FrogApiTable(entries.map { it.metadata }, onPropertyClick = { onProperty(it.name) }, tagged = false)
                }
            }
        }
    }
}

@Composable
internal fun ComponentApiPropertyDetail(property: ComponentApiProperty) {
    FrogInlineCode(property.metadata.type)
    Text(if (property.required) "Required" else "Default", style = FrogTheme.typography.label, color = FrogTheme.colors.mutedForeground)
    if (!property.required) FrogInlineCode(property.metadata.defaultValue)
    Text(property.metadata.description, style = FrogTheme.typography.body, color = FrogTheme.colors.foreground)
    if (property.values.isNotEmpty()) ComponentDocSection("Values") {
        property.values.forEach { value ->
            FrogInlineCode(value.name)
            Text(value.description, style = FrogTheme.typography.bodySmall, color = FrogTheme.colors.foreground)
        }
    }
    FrogMarkdown(property.guidance)
    property.example?.let { ComponentDocSection("Example") { FrogCodeBlock(it) } }
}

@Composable
internal fun ComponentAccessibilityContent(info: ComponentAccessibilityInfo, docs: MarkdownDocument) {
    ComponentDocSection("Accessibility", info.summary) {
        info.facts.forEach { fact ->
            Column(verticalArrangement = Arrangement.spacedBy(FrogTheme.spacing.sm)) {
                Text(fact.title, style = FrogTheme.typography.subheading, color = FrogTheme.colors.foreground, modifier = Modifier.semantics { heading() })
                Text(fact.verification, style = FrogTheme.typography.label, color = FrogTheme.colors.mutedForeground)
                Text(fact.description, style = FrogTheme.typography.bodySmall, color = FrogTheme.colors.foreground)
            }
            HorizontalDivider(color = FrogTheme.colors.border)
        }
        FrogMarkdownDocument(docs.section("Accessibility"))
    }
}

@Composable
internal fun ComponentExampleSection(example: ComponentExampleMetadata, preview: @Composable () -> Unit) {
    ComponentDocSection(example.title, example.description) {
        BoxWithConstraints(Modifier.fillMaxWidth()) {
            if (maxWidth >= 680.dp) Row(horizontalArrangement = Arrangement.spacedBy(FrogTheme.spacing.xxl)) {
                Box(Modifier.weight(1f)) { preview() }
                Box(Modifier.weight(1f)) { FrogCodeSnippet(example.codeSnippet) }
            } else Column(verticalArrangement = Arrangement.spacedBy(FrogTheme.spacing.lg)) { preview(); FrogCodeSnippet(example.codeSnippet) }
        }
        HorizontalDivider(color = FrogTheme.colors.border)
    }
}

@Composable
internal fun rememberComponentDocs(id: String): MarkdownDocument {
    val assets = LocalContext.current.assets
    return remember(id, assets) { MarkdownParser().parse(runCatching { assets.open("components/$id.md").bufferedReader().use { it.readText() } }.getOrDefault("")) }
}

@Composable
internal fun ComponentLongFormDocs(document: MarkdownDocument) {
    Column(Modifier.widthIn(max = 760.dp)) { FrogMarkdownDocument(document) }
}
