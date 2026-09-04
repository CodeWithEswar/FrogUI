package io.github.codewitheswar.frogui.showcase.components.drawer

import io.github.codewitheswar.frogui.registry.ComponentPropertyMetadata
import io.github.codewitheswar.frogui.showcase.detail.*

internal fun drawerApiProperty(property: ComponentPropertyMetadata): ComponentApiProperty {
    val category = when (property.name) {
        "state", "onDismissRequest" -> ApiCategory.Core
        "presentation", "side", "modifier" -> ApiCategory.Layout
        "title", "subtitle", "navigationIcon", "actions", "preview", "footer", "content", "closeIcon" -> ApiCategory.Content
        "colors" -> ApiCategory.Style
        else -> ApiCategory.Behavior
    }
    val values = when (property.name) {
        "presentation" -> listOf(ComponentApiValue("Auto", "FrogTheme.adaptive: Bottom for Compact; Side for Medium/Expanded using actual host constraints."), ComponentApiValue("Bottom", "Bottom edge with a downward drag handle."), ComponentApiValue("Side", "Docked to the selected logical edge, within available width."))
        "side" -> listOf(ComponentApiValue("Start", "Leading edge; left in LTR and right in RTL."), ComponentApiValue("End", "Trailing edge; right in LTR and left in RTL."))
        else -> emptyList()
    }
    val guidance = when (property.name) {
        "state" -> "Hoist rememberFrogDrawerState so an external action can open or close the panel. The state saves its open/closed value across configuration changes. A separate Boolean overload is available for state owners that already track visibility."
        "onDismissRequest" -> "The callback requests a state change; it does not mutate caller state automatically. Close the state here. Cancel and Apply belong to the caller's transaction model."
        "presentation" -> "Use Auto for adaptive behavior. In a FrogOverlayHost the bounded preview width determines placement; otherwise the native modal window width is used. A fixed width preset is clamped to the available space."
        "side" -> "Start and End are logical layout edges. They mirror in RTL, including the transition direction. Bottom presentation ignores this property."
        "navigationIcon" -> "Supply a composable navigation control for nested contextual pages. Keep at least a 48dp touch target and a meaningful accessible label."
        "actions" -> "Header actions are separate from the sticky footer. Keep them brief so the title and close control remain readable on compact screens."
        "footer" -> "Footer content remains outside the scrolling body. Use this slot for action buttons. Let actions wrap or grow when text scales; avoid an oversized fixed footer."
        "preview" -> "A fixed region below the header, useful for contextual feedback. Keep it compact enough to leave room for the scrollable body and keyboard."
        "colors" -> "Defaults follow FrogTheme. Override selected fields through FrogDrawerDefaults.colors rather than copying an unrelated theme."
        "onBackRequest" -> "Optionally handle system Back separately from Close and outside dismissal, such as returning to a parent inspector. Without an override, Back uses onDismissRequest."
        "closeIcon" -> "Optional decorative glyph slot inside the standard accessible FrogIconButton close action. The surrounding button owns its label and focus behavior."
        "title" -> "The title is a heading and names the accessibility pane. Supply concise contextual text. A title-free drawer still retains its close action."
        "subtitle" -> "Optional supporting text underneath the title. Prefer one concise explanation; larger text is allowed to wrap."
        "content" -> "Content receives a ColumnScope and scrolls within the available panel height. Header, preview, and footer stay fixed. Avoid placing another unbounded vertical scrolling region here."
        "modifier" -> "Applied to the panel surface. Use the preview host's constraints to demonstrate adaptive sizing instead of scaling pixels."
        else -> property.description
    }
    val state = when (property.name) {
        "presentation" -> DrawerDemoState(presentation = io.github.codewitheswar.frogui.components.overlays.drawer.FrogDrawerPresentation.Side)
        "side" -> DrawerDemoState(presentation = io.github.codewitheswar.frogui.components.overlays.drawer.FrogDrawerPresentation.Side, side = io.github.codewitheswar.frogui.components.overlays.drawer.FrogDrawerSide.Start)
        "content" -> DrawerDemoState(longContent = true)
        else -> DrawerDemoState()
    }
    return ComponentApiProperty(property, category, guidance, state.toCodeSnippet(), values)
}
