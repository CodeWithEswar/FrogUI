package io.github.codewitheswar.frogui.showcase.components.fab

import io.github.codewitheswar.frogui.registry.ComponentPropertyMetadata
import io.github.codewitheswar.frogui.showcase.detail.*

/** Registry owns names, signatures, defaults and descriptions. Showcase adds teaching material. */
internal fun fabApiProperty(property: ComponentPropertyMetadata): ComponentApiProperty {
    val category = when (property.name) {
        "icon", "contentDescription", "label" -> ApiCategory.Core
        "onClick" -> ApiCategory.Core
        "presentation", "elevation", "colors", "shape" -> ApiCategory.Appearance
        "expanded", "enabled", "visible" -> ApiCategory.State
        "modifier" -> ApiCategory.Layout
        else -> ApiCategory.Advanced
    }

    val guidance = when (property.name) {
        "icon" -> "Primary visual glyph composable. The child icon composable should use `contentDescription = null`; the parent FAB supplies the accessible name."
        "contentDescription" -> "Mandatory concise accessibility label describing the action (e.g. \"Create document\", \"Scan\"). Required for all presentation forms."
        "onClick" -> "Callback triggered by touch, Enter/Space, or accessibility action. Disabled buttons suppress activation."
        "presentation" -> "- **Regular:** 56dp square visual container (24dp icon) for screen-level actions.\n- **Small:** 40dp square visual container (20dp icon) with 48dp minimum touch target.\n- **Extended:** 48dp height container presenting icon and visible label."
        "label" -> "Visible text label composable presented alongside the icon when presentation is Extended."
        "expanded" -> "Controls label visibility for Extended presentation. When true, shows icon + label. When false, collapses to icon only. Ignored for Regular and Small."
        "enabled" -> "When false, clicks are blocked, disabled styling is applied, and disabled semantics are exposed."
        "visible" -> "When false, smoothly animates out and is completely removed from semantics and interaction. Snaps immediately when reduced motion is preferred."
        "elevation" -> "FrogUI immutable elevation model controlling resting, pressed, and focused surface separation."
        "colors" -> "FrogUI immutable colors model. Defaults resolve from FrogTheme tokens."
        "shape" -> "Corner radius shape applied to the FAB container. Derives from FrogTheme.shapes."
        "modifier" -> "Applied to the outer layout container."
        "interactionSource" -> "Hoist a MutableInteractionSource to observe press and focus interaction events."
        else -> property.description
    }

    val argument = when (property.name) {
        "presentation" -> "presentation = FrogFabPresentation.Extended"
        "label" -> "label = { Text(\"Compose\") }"
        "expanded" -> "expanded = false"
        "enabled" -> "enabled = false"
        "visible" -> "visible = false"
        "elevation" -> "elevation = FrogFloatingActionButtonDefaults.elevation(default = 6.dp)"
        "colors" -> "colors = FrogFloatingActionButtonDefaults.colors()"
        "shape" -> "shape = CircleShape"
        "modifier" -> "modifier = Modifier.padding(16.dp)"
        "interactionSource" -> "interactionSource = remember { MutableInteractionSource() }"
        else -> null
    }

    val code = "FrogFloatingActionButton(\n" +
        "    icon = { Icon(FrogIcons.Add, contentDescription = null) },\n" +
        "    contentDescription = \"Create item\",\n" +
        (argument?.let { "    $it,\n" } ?: "") +
        "    onClick = { /* Handle action */ }\n" +
        ")"

    val values = when (property.name) {
        "presentation" -> listOf(
            "Regular" to "56dp default floating action button.",
            "Small" to "40dp compact action maintaining 48dp touch target.",
            "Extended" to "Action with icon and visible label."
        )
        else -> emptyList()
    }

    return ComponentApiProperty(
        metadata = property,
        category = category,
        guidance = guidance,
        example = code,
        values = values.map { ComponentApiValue(it.first, it.second) }
    )
}
