package io.github.codewitheswar.frogui.showcase.registry

import io.github.codewitheswar.frogui.registry.FrogComponentMetadata
import io.github.codewitheswar.frogui.registry.FrogComponentRegistry
import io.github.codewitheswar.frogui.showcase.detail.ComponentShowcaseFactory
import io.github.codewitheswar.frogui.showcase.components.button.buttonShowcaseDefinition
import io.github.codewitheswar.frogui.showcase.components.drawer.drawerShowcaseDefinition

/** Explicit native destinations. No executable factories live in the shared registry. */
internal enum class ComponentDemo(val componentId: String, val definition: ComponentShowcaseFactory) {
    Button("button", ComponentShowcaseFactory { metadata, state -> buttonShowcaseDefinition(metadata, state) }),
    Drawer("drawer", ComponentShowcaseFactory { metadata, state -> drawerShowcaseDefinition(metadata, state) })
}

internal data class ShowcaseComponent(val metadata: FrogComponentMetadata, val demo: ComponentDemo) {
    val route: String get() = metadata.showcaseRoute
}

internal object ShowcaseRegistry {
    val components = FrogComponentRegistry.allComponents.map { metadata ->
        ShowcaseComponent(metadata, ComponentDemo.entries.single { it.componentId == metadata.id })
    }

    fun findById(id: String): ShowcaseComponent? = components.firstOrNull { it.metadata.id == id }
}
