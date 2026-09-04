package io.github.codewitheswar.frogui.showcase.components.drawer

import androidx.compose.runtime.saveable.listSaver
import io.github.codewitheswar.frogui.components.overlays.drawer.FrogDrawerPresentation
import io.github.codewitheswar.frogui.components.overlays.drawer.FrogDrawerSide

internal data class DrawerDemoState(
    val presentation: FrogDrawerPresentation = FrogDrawerPresentation.Auto,
    val side: FrogDrawerSide = FrogDrawerSide.End,
    val title: String = "Properties",
    val subtitle: String = "Configure the current component",
    val showSubtitle: Boolean = true,
    val showFooter: Boolean = true,
    val longContent: Boolean = false,
) {
    fun toCodeSnippet(): String {
        fun literal(value: String) = "\"" + value.replace("\\", "\\\\").replace("\"", "\\\"").replace("$", "\\$").replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t") + "\""
        val parameters = mutableListOf("    state = drawerState", "    onDismissRequest = { scope.launch { drawerState.close() } }")
        if (presentation != FrogDrawerPresentation.Auto) parameters += "    presentation = FrogDrawerPresentation.$presentation"
        if (side != FrogDrawerSide.End) parameters += "    side = FrogDrawerSide.$side"
        parameters += "    title = ${literal(title)}"
        if (showSubtitle) parameters += "    subtitle = ${literal(subtitle)}"
        if (showFooter) parameters += "    footer = {\n        FrogButton(onClick = { scope.launch { drawerState.close() } }) {\n            Text(\"Done\")\n        }\n    }"
        val content = if (longContent) "    repeat(12) { index ->\n        Text(\"Section \${index + 1}\")\n        Text(\"Contextual options and guidance.\")\n    }" else "    Text(\"Contextual options and guidance.\")"
        return "val drawerState = rememberFrogDrawerState()\nval scope = rememberCoroutineScope()\n\nFrogButton(onClick = { scope.launch { drawerState.open() } }) {\n    Text(\"Open drawer\")\n}\n\nFrogDrawer(\n${parameters.joinToString(",\n")}\n) {\n$content\n}"
    }
    companion object {
        val saver = listSaver<DrawerDemoState, Any>(save = { listOf(it.presentation.name, it.side.name, it.title, it.subtitle, it.showSubtitle, it.showFooter, it.longContent) },
            restore = { DrawerDemoState(FrogDrawerPresentation.valueOf(it[0] as String), FrogDrawerSide.valueOf(it[1] as String), it[2] as String, it[3] as String, it[4] as Boolean, it[5] as Boolean, it[6] as Boolean) })
    }
}
