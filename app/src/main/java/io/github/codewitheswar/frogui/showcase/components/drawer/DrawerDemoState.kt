package io.github.codewitheswar.frogui.showcase.components.drawer

import androidx.compose.runtime.saveable.listSaver
import io.github.codewitheswar.frogui.components.overlays.drawer.FrogDrawerPresentation
import io.github.codewitheswar.frogui.components.overlays.drawer.FrogDrawerSide
import io.github.codewitheswar.frogui.showcase.colorpicker.FrogColorValue

internal data class DrawerDemoState(
    val presentation: FrogDrawerPresentation = FrogDrawerPresentation.Auto,
    val side: FrogDrawerSide = FrogDrawerSide.End,
    val title: String = "Properties",
    val subtitle: String = "Configure the current component",
    val showSubtitle: Boolean = true,
    val showFooter: Boolean = true,
    val longContent: Boolean = false,
    val shape: DrawerShapePreset = DrawerShapePreset.Theme,
    val colorOverrides: Map<DrawerColorProperty, FrogColorValue> = emptyMap(),
) {
    fun toCodeSnippet(): String {
        fun literal(value: String) = "\"" + value.replace("\\", "\\\\").replace("\"", "\\\"").replace("$", "\\$").replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t") + "\""
        val parameters = mutableListOf("    state = drawerState", "    onDismissRequest = { scope.launch { drawerState.close() } }")
        if (presentation != FrogDrawerPresentation.Auto) parameters += "    presentation = FrogDrawerPresentation.$presentation"
        if (side != FrogDrawerSide.End) parameters += "    side = FrogDrawerSide.$side"
        parameters += "    title = ${literal(title)}"
        if (showSubtitle) parameters += "    subtitle = ${literal(subtitle)}"
        if (showFooter) parameters += "    footer = {\n        FrogButton(onClick = { scope.launch { drawerState.close() } }) {\n            Text(\"Done\")\n        }\n    }"
        shape.code?.let { parameters += "    shape = $it" }
        if (colorOverrides.isNotEmpty()) {
            val values = DrawerColorProperty.entries.mapNotNull { property -> colorOverrides[property]?.let { "        ${property.parameter} = ${it.code()}" } }
            parameters += "    colors = FrogDrawerDefaults.colors(\n${values.joinToString(",\n")}\n    )"
        }
        val content = if (longContent) "    repeat(12) { index ->\n        Text(\"Section \${index + 1}\")\n        Text(\"Contextual options and guidance.\")\n    }" else "    Text(\"Contextual options and guidance.\")"
        return "val drawerState = rememberFrogDrawerState()\nval scope = rememberCoroutineScope()\n\nFrogButton(onClick = { scope.launch { drawerState.open() } }) {\n    Text(\"Open drawer\")\n}\n\nFrogDrawer(\n${parameters.joinToString(",\n")}\n) {\n$content\n}"
    }
    companion object {
        val saver = listSaver<DrawerDemoState, Any>(save = {
            listOf(it.presentation.name, it.side.name, it.title, it.subtitle, it.showSubtitle, it.showFooter, it.longContent,
                it.shape.name, it.colorOverrides.entries.joinToString("|") { entry -> "${entry.key.name}=${entry.value.encode()}" })
        }, restore = {
            val colors = (it.getOrNull(8) as? String).orEmpty().split('|').mapNotNull { value ->
                val separator = value.indexOf('=')
                if (separator <= 0) null else runCatching { DrawerColorProperty.valueOf(value.take(separator)) }.getOrNull()
                    ?.let { property -> FrogColorValue.decode(value.drop(separator + 1))?.let { color -> property to color } }
            }.toMap()
            DrawerDemoState(FrogDrawerPresentation.valueOf(it[0] as String), FrogDrawerSide.valueOf(it[1] as String),
                it[2] as String, it[3] as String, it[4] as Boolean, it[5] as Boolean, it[6] as Boolean,
                (it.getOrNull(7) as? String)?.let { value -> runCatching { DrawerShapePreset.valueOf(value) }.getOrNull() } ?: DrawerShapePreset.Theme,
                colors)
        })
    }
}
