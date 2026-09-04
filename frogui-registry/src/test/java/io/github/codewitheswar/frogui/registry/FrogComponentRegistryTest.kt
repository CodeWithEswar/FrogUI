package io.github.codewitheswar.frogui.registry

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class FrogComponentRegistryTest {

    @Test
    fun testUniqueComponentIds() {
        val all = FrogComponentRegistry.allComponents
        val ids = all.map { it.id }
        assertEquals("Component IDs must be unique", ids.size, ids.distinct().size)
    }

    @Test
    fun testButtonMetadataCompleteness() {
        val button = FrogComponentRegistry.Button
        assertEquals("FrogButton", button.name)
        assertEquals(FrogComponentCategory.Actions, button.category)
        assertEquals(FrogComponentStatus.Experimental, button.status)
        assertFalse(button.properties.isEmpty())
        assertFalse(button.examples.isEmpty())
    }

    @Test
    fun testRegistrySearch() {
        val buttonResults = FrogComponentRegistry.search("button")
        assertTrue("Search for 'button' should return at least FrogButton", buttonResults.any { it.id == "button" })

        val actionsResults = FrogComponentRegistry.search("", category = FrogComponentCategory.Actions)
        assertTrue("Category filter for Actions should return actions", actionsResults.all { it.category == FrogComponentCategory.Actions })
    }

    @Test
    fun testFindById() {
        val button = FrogComponentRegistry.findById("button")
        assertNotNull(button)
        assertEquals("FrogButton", button?.name)
    }

    @Test
    fun testCatalogDoesNotAdvertiseRoadmapPlaceholders() {
        assertEquals(listOf("button", "drawer"), FrogComponentRegistry.allComponents.map { it.id })
        assertTrue(FrogComponentRegistry.search("card").isEmpty())
        assertTrue(FrogComponentRegistry.findById("not-a-component") == null)
    }

    @Test
    fun testGeneratedPropertiesAndExamples() {
        val button = FrogComponentRegistry.Button
        assertTrue(button.properties.any { it.name == "modifier" })
        assertTrue(button.properties.any { it.name == "colors" })
        assertTrue(button.properties.any { it.name == "content" })
        assertTrue(button.examples.first().codeSnippet.contains("Text(\"Continue\")"))
        assertTrue(button.examples.first().codeSnippet.contains('\n'))
    }
}
