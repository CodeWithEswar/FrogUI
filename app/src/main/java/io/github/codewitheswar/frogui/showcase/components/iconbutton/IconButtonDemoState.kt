package io.github.codewitheswar.frogui.showcase.components.iconbutton

import androidx.compose.runtime.Stable
import androidx.compose.runtime.saveable.listSaver
import io.github.codewitheswar.frogui.components.button.FrogIconButtonSize
import io.github.codewitheswar.frogui.components.button.FrogIconButtonVariant
import io.github.codewitheswar.frogui.showcase.colorpicker.FrogColorValue

internal enum class IconButtonDemoIcon(val label: String, val actionDescription: String) {
    Search("Search", "Search"),
    Close("Close", "Close"),
    Settings("Settings", "Settings"),
    Reset("Reset", "Refresh"),
    Play("Play", "Play video"),
    Info("Info", "Information")
}

internal enum class IconButtonDemoBadge(val label: String) {
    None("None"),
    Dot("Dot"),
    Count("Count")
}

/**
 * Isolated demo state for interactive property testing of [FrogIconButton].
 * The core FrogIconButton component remains completely decoupled from this showcase model.
 */
@Stable
internal data class IconButtonDemoState(
    val variant: FrogIconButtonVariant = FrogIconButtonVariant.Filled,
    val size: FrogIconButtonSize = FrogIconButtonSize.Medium,
    val enabled: Boolean = true,
    val loading: Boolean = false,
    val icon: IconButtonDemoIcon = IconButtonDemoIcon.Search,
    val badge: IconButtonDemoBadge = IconButtonDemoBadge.None,
    val badgeCount: Int = 3,
    val colorOverrides: Map<IconButtonColorProperty, FrogColorValue> = emptyMap()
) {
    val contentDescription: String get() = icon.actionDescription

    fun withVariant(value: FrogIconButtonVariant) =
        if (value == variant) this else copy(variant = value, colorOverrides = emptyMap())

    fun withColor(property: IconButtonColorProperty, value: FrogColorValue?) =
        copy(colorOverrides = if (value == null) colorOverrides - property else colorOverrides + (property to value))

    fun resetColors() = copy(colorOverrides = emptyMap())

    /**
     * Generates a realistic, conceptually compilable Kotlin usage snippet
     * matching the current state, omitting default values.
     */
    fun toCodeSnippet(): String {
        val params = mutableListOf<String>()
        params.add("    icon = {\n        Icon(\n            imageVector = FrogIcons.${icon.name},\n            contentDescription = null,\n            modifier = Modifier.size(FrogIconButtonDefaults.iconSize(FrogIconButtonSize.$size))\n        )\n    }")
        params.add("    contentDescription = \"${icon.actionDescription}\"")
        params.add("    onClick = { /* Handle action */ }")

        if (variant != FrogIconButtonVariant.Filled) {
            params.add("    variant = FrogIconButtonVariant.$variant")
        }
        if (size != FrogIconButtonSize.Medium) {
            params.add("    size = FrogIconButtonSize.$size")
        }
        if (!enabled) {
            params.add("    enabled = false")
        }
        if (loading) {
            params.add("    loading = true")
        }
        when (badge) {
            IconButtonDemoBadge.Dot -> {
                params.add("    badge = {\n        Box(\n            modifier = Modifier\n                .size(8.dp)\n                .background(FrogTheme.colors.destructive, CircleShape)\n        )\n    }")
            }
            IconButtonDemoBadge.Count -> {
                params.add("    badge = {\n        Box(\n            modifier = Modifier\n                .defaultMinSize(minWidth = 16.dp, minHeight = 16.dp)\n                .background(FrogTheme.colors.destructive, CircleShape)\n                .padding(horizontal = 4.dp),\n            contentAlignment = Alignment.Center\n        ) {\n            Text(\"$badgeCount\", style = FrogTheme.typography.bodySmall, color = Color.White)\n        }\n    }")
            }
            IconButtonDemoBadge.None -> { /* Omit */ }
        }

        if (colorOverrides.isNotEmpty()) {
            val colorParams = mutableListOf<String>()
            if (variant != FrogIconButtonVariant.Filled) colorParams += "        variant = FrogIconButtonVariant.$variant"
            IconButtonColorProperty.entries.forEach { property ->
                colorOverrides[property]?.let { colorParams += "        ${property.parameter} = ${it.code()}" }
            }
            params += "    colors = FrogIconButtonDefaults.colors(\n${colorParams.joinToString(",\n")}\n    )"
        }

        return "FrogIconButton(\n${params.joinToString(",\n")}\n)"
    }

    companion object {
        val saver = listSaver<IconButtonDemoState, Any>(
            save = {
                listOf(
                    it.variant.name,
                    it.size.name,
                    it.enabled,
                    it.loading,
                    it.icon.name,
                    it.badge.name,
                    it.badgeCount,
                    it.colorOverrides.entries.joinToString("|") { (property, value) -> "${property.name}=${value.encode()}" }
                )
            },
            restore = { saved ->
                IconButtonDemoState(
                    variant = FrogIconButtonVariant.valueOf(saved[0] as String),
                    size = FrogIconButtonSize.valueOf(saved[1] as String),
                    enabled = saved[2] as Boolean,
                    loading = saved[3] as Boolean,
                    icon = IconButtonDemoIcon.valueOf(saved[4] as String),
                    badge = IconButtonDemoBadge.valueOf(saved[5] as String),
                    badgeCount = saved[6] as Int,
                    colorOverrides = (saved.getOrNull(7) as? String).orEmpty().split('|').mapNotNull { entry ->
                        val parts = entry.split('=', limit = 2)
                        if (parts.size != 2) null else FrogColorValue.decode(parts[1])?.let { IconButtonColorProperty.valueOf(parts[0]) to it }
                    }.toMap()
                )
            }
        )

        fun default() = IconButtonDemoState()
    }
}
