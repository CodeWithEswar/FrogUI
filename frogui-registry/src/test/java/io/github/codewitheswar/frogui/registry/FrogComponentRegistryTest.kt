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
        assertEquals(FrogComponentStatus.Stable, button.status)
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
    fun testMachineReadableRegistryFiles() {
        val candidates = listOf(
            java.io.File("../registry/components/button.json"),
            java.io.File("registry/components/button.json"),
            java.io.File("../../registry/components/button.json")
        )
        val buttonFile = candidates.firstOrNull { it.exists() }
        assertNotNull("registry/components/button.json must exist in the repository", buttonFile)

        val jsonContent = buttonFile!!.readText()
        assertTrue("button.json must declare id: button", jsonContent.contains("\"id\": \"button\""))
        assertTrue("button.json must declare name: FrogButton", jsonContent.contains("\"name\": \"FrogButton\""))
        assertTrue("button.json must declare category: actions", jsonContent.contains("\"category\": \"actions\""))
        assertTrue("button.json must declare status: stable", jsonContent.contains("\"status\": \"stable\""))
    }
}
