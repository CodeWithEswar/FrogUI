package io.github.codewitheswar.frogui.registry

/**
 * Single source-of-truth registry for all FrogUI components.
 * Powers the showcase catalog, search, interactive inspector, and future CLI tooling.
 */
object FrogComponentRegistry {

    val Button = FrogComponentMetadata(
        id = "button",
        name = "FrogButton",
        description = "A versatile, accessible action button with 5 visual variants, 3 sizes, tactile press physics, loading state, and slot APIs.",
        category = FrogComponentCategory.Actions,
        status = FrogComponentStatus.Stable,
        since = "1.0.0",
        docsPath = "components/button",
        properties = listOf(
            ComponentPropertyMetadata(
                name = "onClick",
                type = "() -> Unit",
                defaultValue = "required",
                description = "Invoked when the button is clicked by the user."
            ),
            ComponentPropertyMetadata(
                name = "variant",
                type = "FrogButtonVariant",
                defaultValue = "FrogButtonVariant.Primary",
                description = "Semantic visual style: Primary, Secondary, Outline, Ghost, or Destructive."
            ),
            ComponentPropertyMetadata(
                name = "size",
                type = "FrogButtonSize",
                defaultValue = "FrogButtonSize.Medium",
                description = "Controls height, padding, icon scale, and typography: Small (32dp), Medium (40dp), Large (48dp)."
            ),
            ComponentPropertyMetadata(
                name = "enabled",
                type = "Boolean",
                defaultValue = "true",
                description = "Controls interaction state. When false, interactions and clicks are suppressed."
            ),
            ComponentPropertyMetadata(
                name = "loading",
                type = "Boolean",
                defaultValue = "false",
                description = "When true, displays an inline circular progress indicator and suppresses click events."
            ),
            ComponentPropertyMetadata(
                name = "leadingIcon",
                type = "(@Composable () -> Unit)?",
                defaultValue = "null",
                description = "Optional composable slot rendered before the button label."
            ),
            ComponentPropertyMetadata(
                name = "trailingIcon",
                type = "(@Composable () -> Unit)?",
                defaultValue = "null",
                description = "Optional composable slot rendered after the button label."
            )
        ),
        examples = listOf(
            ComponentExampleMetadata(
                id = "primary",
                title = "Primary Action",
                description = "High-emphasis action for the main task on the screen.",
                codeSnippet = """FrogButton(
    variant = FrogButtonVariant.Primary,
    onClick = { }
) {
    Text("Continue")
}"""
            ),
            ComponentExampleMetadata(
                id = "secondary",
                title = "Secondary Action",
                description = "Tonal zinc surface for alternative actions.",
                codeSnippet = """FrogButton(
    variant = FrogButtonVariant.Secondary,
    onClick = { }
) {
    Text("Cancel")
}"""
            ),
            ComponentExampleMetadata(
                id = "outline",
                title = "Outlined Action",
                description = "Transparent surface with a defined border.",
                codeSnippet = """FrogButton(
    variant = FrogButtonVariant.Outline,
    onClick = { }
) {
    Text("Documentation")
}"""
            ),
            ComponentExampleMetadata(
                id = "ghost",
                title = "Ghost Action",
                description = "Subtle flat button for inline toolbars and list items.",
                codeSnippet = """FrogButton(
    variant = FrogButtonVariant.Ghost,
    onClick = { }
) {
    Text("Learn more")
}"""
            ),
            ComponentExampleMetadata(
                id = "destructive",
                title = "Destructive Action",
                description = "Communicates irreversible operations.",
                codeSnippet = """FrogButton(
    variant = FrogButtonVariant.Destructive,
    onClick = { }
) {
    Text("Delete repository")
}"""
            ),
            ComponentExampleMetadata(
                id = "loading",
                title = "Loading State",
                description = "Displays inline circular progress and pauses interactions.",
                codeSnippet = """FrogButton(
    variant = FrogButtonVariant.Primary,
    loading = true,
    onClick = { }
) {
    Text("Saving...")
}"""
            )
        )
    )

    val IconButton = FrogComponentMetadata(
        id = "icon-button",
        name = "FrogIconButton",
        description = "Icon-only button enforcing 48dp minimum accessible touch targets and concise semantics.",
        category = FrogComponentCategory.Actions,
        status = FrogComponentStatus.Stable,
        since = "1.0.0",
        docsPath = "components/icon-button"
    )

    val Card = FrogComponentMetadata(
        id = "card",
        name = "FrogCard",
        description = "Tonal container for grouped content with subtle borders and semantic padding.",
        category = FrogComponentCategory.DataDisplay,
        status = FrogComponentStatus.Beta,
        since = "1.0.0",
        docsPath = "components/card"
    )

    val TextField = FrogComponentMetadata(
        id = "text-field",
        name = "FrogTextField",
        description = "Accessible text input with labels, supporting text, error states, and IME management.",
        category = FrogComponentCategory.Inputs,
        status = FrogComponentStatus.Beta,
        since = "1.0.0",
        docsPath = "components/text-field"
    )

    val Badge = FrogComponentMetadata(
        id = "badge",
        name = "FrogBadge",
        description = "Compact pill indicator for counts, status labels, and tags.",
        category = FrogComponentCategory.DataDisplay,
        status = FrogComponentStatus.Beta,
        since = "1.0.0",
        docsPath = "components/badge"
    )

    val Switch = FrogComponentMetadata(
        id = "switch",
        name = "FrogSwitch",
        description = "Tactile toggle control for immediate on/off preferences.",
        category = FrogComponentCategory.Inputs,
        status = FrogComponentStatus.Beta,
        since = "1.0.0",
        docsPath = "components/switch"
    )

    val Dialog = FrogComponentMetadata(
        id = "dialog",
        name = "FrogDialog",
        description = "Focused overlay modal with backdrop dismissal and keyboard handling.",
        category = FrogComponentCategory.Overlays,
        status = FrogComponentStatus.Beta,
        since = "1.0.0",
        docsPath = "components/dialog"
    )

    val Tabs = FrogComponentMetadata(
        id = "tabs",
        name = "FrogTabs",
        description = "Segmented tab control for switching views with animated indicator.",
        category = FrogComponentCategory.Navigation,
        status = FrogComponentStatus.Beta,
        since = "1.0.0",
        docsPath = "components/tabs"
    )

    /** Complete list of all components in the FrogUI ecosystem. */
    val allComponents: List<FrogComponentMetadata> = listOf(
        Button,
        IconButton,
        Card,
        TextField,
        Badge,
        Switch,
        Dialog,
        Tabs
    )

    /**
     * Finds a component metadata by its unique [id].
     */
    fun findById(id: String): FrogComponentMetadata? {
        return allComponents.firstOrNull { it.id.equals(id, ignoreCase = true) }
    }

    /**
     * Filters components by [query] matching name, description, or category.
     */
    fun search(query: String, category: FrogComponentCategory? = null): List<FrogComponentMetadata> {
        val trimmed = query.trim().lowercase()
        return allComponents.filter { item ->
            val matchesCategory = category == null || item.category == category
            val matchesQuery = trimmed.isEmpty() ||
                item.name.lowercase().contains(trimmed) ||
                item.description.lowercase().contains(trimmed) ||
                item.category.displayName.lowercase().contains(trimmed)
            matchesCategory && matchesQuery
        }
    }
}
