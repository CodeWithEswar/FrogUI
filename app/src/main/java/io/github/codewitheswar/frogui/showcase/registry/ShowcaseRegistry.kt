package io.github.codewitheswar.frogui.showcase.registry

import io.github.codewitheswar.frogui.registry.FrogComponentMetadata
import io.github.codewitheswar.frogui.registry.FrogComponentRegistry

/** Explicit native destinations. No executable factories live in the shared registry. */
internal enum class ComponentDemo(val componentId: String) {
    Button("button")
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
