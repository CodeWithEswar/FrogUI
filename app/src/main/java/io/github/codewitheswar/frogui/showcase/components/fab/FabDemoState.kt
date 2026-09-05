package io.github.codewitheswar.frogui.showcase.components.fab

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.components.fab.*
import io.github.codewitheswar.frogui.showcase.colorpicker.FrogColorToken
import io.github.codewitheswar.frogui.showcase.colorpicker.FrogColorValue
import io.github.codewitheswar.frogui.theme.FrogTheme

internal enum class FabDemoIcon(val label: String, val actionDescription: String) {
    Add("Add", "Create item"),
    Search("Search", "Search items"),
    Settings("Settings", "Settings"),
    Reset("Reset", "Refresh"),
    Play("Play", "Start operation")
}

internal enum class FabColorProperty(val label: String, val parameter: String) {
    Container("Container color", "containerColor"),
    Content("Content color", "contentColor"),
    DisabledContainer("Disabled container", "disabledContainerColor"),
    DisabledContent("Disabled content", "disabledContentColor")
}

internal fun defaultFabColor(property: FabColorProperty): FrogColorValue {
    fun token(value: FrogColorToken, alpha: Float? = null) = FrogColorValue.Token(value, alpha)
    return when (property) {
        FabColorProperty.Container -> token(FrogColorToken.Primary)
        FabColorProperty.Content -> token(FrogColorToken.PrimaryForeground)
        FabColorProperty.DisabledContainer -> token(FrogColorToken.Primary, 0.25f)
        FabColorProperty.DisabledContent -> token(FrogColorToken.PrimaryForeground, 0.45f)
    }
}

/**
 * Isolated demo state for interactive testing of [FrogFloatingActionButton].
 */
@Stable
internal data class FabDemoState(
    val presentation: FrogFabPresentation = FrogFabPresentation.Regular,
    val expanded: Boolean = true,
    val visible: Boolean = true,
    val enabled: Boolean = true,
    val elevationDp: Int = 3,
    val icon: FabDemoIcon = FabDemoIcon.Add,
    val labelText: String = "Create",
    val colorOverrides: Map<FabColorProperty, FrogColorValue> = emptyMap()
) {
    val contentDescription: String get() = icon.actionDescription

    fun withPresentation(value: FrogFabPresentation) =
        if (value == presentation) this else copy(presentation = value)

    fun withColor(property: FabColorProperty, value: FrogColorValue?) =
        copy(colorOverrides = if (value == null) colorOverrides - property else colorOverrides + (property to value))

    fun resetColors() = copy(colorOverrides = emptyMap())

    fun colorValue(property: FabColorProperty): FrogColorValue =
        colorOverrides[property] ?: defaultFabColor(property)

    @Composable
    fun resolvedColors(): FrogFabColors {
        val colors = FrogTheme.colors
        return FrogFloatingActionButtonDefaults.colors(
            containerColor = colorValue(FabColorProperty.Container).resolve(colors),
            contentColor = colorValue(FabColorProperty.Content).resolve(colors),
            disabledContainerColor = colorValue(FabColorProperty.DisabledContainer).resolve(colors),
            disabledContentColor = colorValue(FabColorProperty.DisabledContent).resolve(colors)
        )
    }

    fun toCodeSnippet(): String {
        val params = mutableListOf<String>()
        params.add("    icon = {\n        Icon(\n            imageVector = FrogIcons.${icon.name},\n            contentDescription = null\n        )\n    }")
        if (presentation == FrogFabPresentation.Extended) {
            params.add("    label = { Text(\"$labelText\") }")
        }
        params.add("    contentDescription = \"${icon.actionDescription}\"")
        params.add("    onClick = { /* Handle action */ }")

        if (presentation != FrogFabPresentation.Regular) {
            params.add("    presentation = FrogFabPresentation.$presentation")
        }
        if (presentation == FrogFabPresentation.Extended && !expanded) {
            params.add("    expanded = false")
        }
        if (!enabled) {
            params.add("    enabled = false")
        }
        if (!visible) {
            params.add("    visible = false")
        }
        if (elevationDp != 3) {
            params.add("    elevation = FrogFloatingActionButtonDefaults.elevation(default = ${elevationDp}.dp)")
        }

        if (colorOverrides.isNotEmpty()) {
            val colorParams = mutableListOf<String>()
            FabColorProperty.entries.forEach { property ->
                colorOverrides[property]?.let { colorParams += "        ${property.parameter} = ${it.code()}" }
            }
            params += "    colors = FrogFloatingActionButtonDefaults.colors(\n${colorParams.joinToString(",\n")}\n    )"
        }

        return "FrogFloatingActionButton(\n${params.joinToString(",\n")}\n)"
    }

    companion object {
        val saver = listSaver<FabDemoState, Any>(
            save = {
                listOf(
                    it.presentation.name,
                    it.expanded,
                    it.visible,
                    it.enabled,
                    it.elevationDp,
                    it.icon.name,
                    it.labelText,
                    it.colorOverrides.entries.joinToString("|") { (property, value) -> "${property.name}=${value.encode()}" }
                )
            },
            restore = { saved ->
                FabDemoState(
                    presentation = FrogFabPresentation.valueOf(saved[0] as String),
                    expanded = saved[1] as Boolean,
                    visible = saved[2] as Boolean,
                    enabled = saved[3] as Boolean,
                    elevationDp = saved[4] as Int,
                    icon = FabDemoIcon.valueOf(saved[5] as String),
                    labelText = saved[6] as String,
                    colorOverrides = (saved.getOrNull(7) as? String).orEmpty().split('|').mapNotNull { entry ->
                        val parts = entry.split('=', limit = 2)
                        if (parts.size != 2) null else FrogColorValue.decode(parts[1])?.let { FabColorProperty.valueOf(parts[0]) to it }
                    }.toMap()
                )
            }
        )
    }
}
