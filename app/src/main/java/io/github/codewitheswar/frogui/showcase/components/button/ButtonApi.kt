package io.github.codewitheswar.frogui.showcase.components.button

import io.github.codewitheswar.frogui.registry.ComponentPropertyMetadata
import io.github.codewitheswar.frogui.showcase.detail.*

/** Registry owns names, signatures, defaults and descriptions. Showcase adds teaching material. */
internal fun buttonApiProperty(property: ComponentPropertyMetadata): ComponentApiProperty {
    val category = when (property.name) {
        "onClick" -> ApiCategory.Core
        "variant", "size", "colors", "shape", "border" -> ApiCategory.Appearance
        "enabled", "loading" -> ApiCategory.State
        "modifier", "fullWidth", "contentPadding" -> ApiCategory.Layout
        "leadingIcon", "trailingIcon", "content" -> ApiCategory.Content
        else -> ApiCategory.Advanced
    }
    val guidance = when (property.name) {
        "onClick" -> "Triggered by touch, Enter/Space, or an accessibility activation. Keep business state in the caller. Disabled and loading buttons suppress activation."
        "variant" -> "- **Primary:** main action in a region.\n- **Secondary:** supporting action.\n- **Outline:** lower emphasis with a visible boundary.\n- **Ghost:** quiet toolbar or inline action.\n- **Destructive:** an action with destructive consequences.\n\nChanging the showcase variant restores that variant's default colors."
        "size" -> "- **Small:** dense interfaces and toolbars; 32dp minimum surface.\n- **Medium:** general actions; 40dp minimum surface.\n- **Large:** prominent actions; 48dp minimum surface.\n\nAll sizes retain a minimum 48dp touch target. Text and padding can make the surface taller at larger font scales."
        "enabled" -> "When false, activation is suppressed, disabled colors are used, and accessibility exposes a disabled button. Explain why an action is unavailable nearby when that helps the user."
        "loading" -> "A centered progress indicator replaces the visible label and slots while their measured layout stays in place. The action label remains available to accessibility, the state becomes Loading, and repeated activation is suppressed. Loading uses enabled colors unless enabled is also false."
        "fullWidth" -> "Fills the available horizontal width of the button surface and touch target. A parent must provide bounded width. Use Modifier for advanced sizing and positioning."
        "colors" -> "Defaults resolve from the current variant and FrogTheme. Override only the fields you need with FrogButtonDefaults.colors. Token values adapt to theme changes; custom Color literals remain fixed. Include the same variant when resolving overridden defaults."
        "shape" -> "Small uses FrogTheme.shapes.sm; Medium and Large use shapes.md. A custom Shape is an escape hatch for a deliberate design requirement. Prefer semantic defaults for a consistent interface."
        "border" -> "The default stroke uses the supplied colors, including disabledBorderColor when enabled is false. Pass null to remove it or a BorderStroke for explicit width and color. Keyboard focus draws a separate focus ring."
        "modifier" -> "Applied to the outer touch target. Use padding, semantics, test tags, and width constraints here. Prefer fullWidth when both the button surface and target should expand."
        "contentPadding" -> "Defaults come from the chosen size. Overrides may increase the measured surface; preserve sufficient room for the label and minimum touch target."
        "leadingIcon", "trailingIcon" -> "A composable slot, with no dependency on an icon vendor. Use FrogButtonDefaults.iconSize(size) for theme-aware icon dimensions. Decorative icons use a null contentDescription so they do not duplicate the action label. The slot remains measured during loading."
        "interactionSource" -> "Hoist a remembered MutableInteractionSource to observe presses and focus. The component uses it for pressed feedback and its focus ring. The state gallery uses synthetic interactions only for comparison and labels them as simulated."
        "content" -> "Required RowScope content, normally Text with a clear action label. It inherits the button's content color and typography. Avoid placing independently clickable controls inside a button."
        else -> property.description
    }
    val argument = when (property.name) {
        "variant" -> "variant = FrogButtonVariant.Outline"
        "size" -> "size = FrogButtonSize.Large"
        "enabled" -> "enabled = false"
        "loading" -> "loading = true"
        "fullWidth" -> "fullWidth = true"
        "colors" -> "colors = FrogButtonDefaults.colors(\n        containerColor = FrogTheme.colors.surfaceElevated,\n        contentColor = FrogTheme.colors.foreground\n    )"
        "shape" -> "shape = RoundedCornerShape(percent = 50)"
        "border" -> "border = BorderStroke(1.dp, FrogTheme.colors.borderStrong)"
        "contentPadding" -> "contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp)"
        "modifier" -> "modifier = Modifier.padding(16.dp)"
        "interactionSource" -> "interactionSource = remember { MutableInteractionSource() }"
        else -> null
    }
    val code = when (property.name) {
        "leadingIcon" -> ButtonDemoState(hasLeadingIcon = true).toCodeSnippet()
        "trailingIcon" -> ButtonDemoState(hasTrailingIcon = true).toCodeSnippet()
        else -> "FrogButton(\n" + (argument?.let { "    $it,\n" } ?: "") + "    onClick = { /* Handle action */ }\n) {\n    Text(\"Continue\")\n}"
    }
    val values = when (property.name) {
        "variant" -> listOf("Primary" to "Main action in a region.", "Secondary" to "Supporting action.", "Outline" to "Lower emphasis with a visible boundary.", "Ghost" to "Quiet toolbar or inline action.", "Destructive" to "Destructive consequences.")
        "size" -> listOf("Small" to "Dense controls; minimum 32dp surface.", "Medium" to "General actions; minimum 40dp surface.", "Large" to "Prominent actions; minimum 48dp surface.")
        else -> emptyList()
    }
    return ComponentApiProperty(property, category, guidance, code, values.map { ComponentApiValue(it.first, it.second) })
}
