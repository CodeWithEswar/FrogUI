package io.github.codewitheswar.frogui.showcase.registry

import io.github.codewitheswar.frogui.navigation.FrogUiDestination
import io.github.codewitheswar.frogui.registry.FrogComponentRegistry
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ShowcaseRegistryTest {
    @Test fun everyCatalogEntryHasExactlyOneNativeDestination() {
        val ids = FrogComponentRegistry.allComponents.map { it.id }.sorted()
        assertEquals(ids, ComponentDemo.entries.map { it.componentId }.sorted())
        assertEquals(ids, ShowcaseRegistry.components.map { it.metadata.id }.sorted())
        ShowcaseRegistry.components.forEach { component ->
            assertEquals(component.route, FrogUiDestination.ComponentDetail(component.metadata.id).route)
        }
    }

    @Test fun unknownComponentsDoNotBorrowAnotherPreview() {
        assertNull(ShowcaseRegistry.findById("missing-component"))
    }
}
