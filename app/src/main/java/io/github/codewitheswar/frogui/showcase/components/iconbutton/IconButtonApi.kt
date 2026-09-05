package io.github.codewitheswar.frogui.showcase.components.iconbutton

import io.github.codewitheswar.frogui.registry.ComponentPropertyMetadata
import io.github.codewitheswar.frogui.showcase.detail.*

/** Registry owns names, signatures, defaults and descriptions. Showcase adds teaching material. */
internal fun iconButtonApiProperty(property: ComponentPropertyMetadata): ComponentApiProperty {
    val category = when (property.name) {
        "icon", "contentDescription" -> ApiCategory.Core
        "onClick" -> ApiCategory.Core
        "variant", "size", "colors", "shape" -> ApiCategory.Appearance
        "enabled", "loading" -> ApiCategory.State
        "modifier" -> ApiCategory.Layout
        "badge" -> ApiCategory.Content
        else -> ApiCategory.Advanced
    }

    val guidance = when (property.name) {
        "icon" -> "Visual icon composable slot. The child icon composable should normally use `contentDescription = null` to avoid duplicate screen reader announcements; the parent IconButton supplies the action description."
        "contentDescription" -> "Mandatory accessibility label describing the action (e.g. \"Search\", \"Close\"), not the visual icon appearance. Required for actionable icon controls."
        "onClick" -> "Triggered by touch, Enter/Space, or accessibility action. Disabled and loading buttons suppress activation."
        "variant" -> "- **Filled:** high-emphasis solid container.\n- **Tonal:** medium-emphasis muted surface.\n- **Outline:** medium/low-emphasis with structural border.\n- **Ghost:** lowest visual emphasis for compact toolbars."
        "size" -> "- **Small:** 32dp visual container (16dp icon).\n- **Medium:** 40dp visual container (18dp icon).\n- **Large:** 48dp visual container (20dp icon).\n\nAll sizes maintain a minimum 48dp touch target."
        "enabled" -> "When false, clicks are suppressed, disabled styling is applied, and disabled semantics are exposed."
        "loading" -> "Replaces visible icon with a centered progress indicator while maintaining container size. Suppresses activation."
        "badge" -> "Optional overlay slot positioned at TopEnd without changing touch target bounds or centered icon alignment."
        "colors" -> "FrogUI immutable colors model. Resolved defaults derive from the current variant and FrogTheme."
        "shape" -> "Corner radius shape applied to button container and border. Derives from FrogTheme.shapes."
        "modifier" -> "Applied to the outer touch target container."
        "interactionSource" -> "Hoist a MutableInteractionSource to observe presses and focus states."
        else -> property.description
    }

    val argument = when (property.name) {
        "variant" -> "variant = FrogIconButtonVariant.Tonal"
        "size" -> "size = FrogIconButtonSize.Small"
        "enabled" -> "enabled = false"
        "loading" -> "loading = true"
        "badge" -> "badge = { Box(Modifier.size(8.dp).background(FrogTheme.colors.destructive, CircleShape)) }"
        "colors" -> "colors = FrogIconButtonDefaults.colors(variant = FrogIconButtonVariant.Outline)"
        "shape" -> "shape = CircleShape"
        "modifier" -> "modifier = Modifier.padding(8.dp)"
        "interactionSource" -> "interactionSource = remember { MutableInteractionSource() }"
        else -> null
    }

    val code = "FrogIconButton(\n" +
        "    icon = { Icon(FrogIcons.Search, contentDescription = null) },\n" +
        "    contentDescription = \"Search\",\n" +
        (argument?.let { "    $it,\n" } ?: "") +
        "    onClick = { /* Handle action */ }\n" +
        ")"

    val values = when (property.name) {
        "variant" -> listOf(
            "Filled" to "High-emphasis action with solid container.",
            "Tonal" to "Medium-emphasis muted container.",
            "Outline" to "Medium/low-emphasis with boundary border.",
            "Ghost" to "Lowest emphasis for toolbars and compact surfaces."
        )
        "size" -> listOf(
            "Small" to "Compact toolbar density; 32dp container, 48dp touch target.",
            "Medium" to "General-purpose action; 40dp container, 48dp touch target.",
            "Large" to "High-emphasis action; 48dp container, 48dp touch target."
        )
        else -> emptyList()
    }

    return ComponentApiProperty(property, category, guidance, code, values.map { ComponentApiValue(it.first, it.second) })
}
