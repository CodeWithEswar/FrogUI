package io.github.codewitheswar.frogui.components.button

import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.registry.FrogComponentRegistry
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class FrogButtonTest {

    @Test
    fun registryCapabilitiesMatchPublicEnums() {
        assertEquals(FrogButtonVariant.entries.map { it.name }, FrogComponentRegistry.Button.variants)
        assertEquals(FrogButtonSize.entries.map { it.name }, FrogComponentRegistry.Button.sizes)
    }

    @Test
    fun testButtonSizes() {
        assertEquals(32.dp, FrogButtonSize.Small.minHeight)
        assertEquals(40.dp, FrogButtonSize.Medium.minHeight)
        assertEquals(48.dp, FrogButtonSize.Large.minHeight)

        assertEquals(12.dp, FrogButtonSize.Small.horizontalPadding)
        assertEquals(16.dp, FrogButtonSize.Medium.horizontalPadding)
        assertEquals(20.dp, FrogButtonSize.Large.horizontalPadding)
    }

    @Test
    fun testButtonVariants() {
        val variants = FrogButtonVariant.entries
        assertEquals(5, variants.size)
        assertNotNull(FrogButtonVariant.valueOf("Primary"))
        assertNotNull(FrogButtonVariant.valueOf("Secondary"))
        assertNotNull(FrogButtonVariant.valueOf("Outline"))
        assertNotNull(FrogButtonVariant.valueOf("Ghost"))
        assertNotNull(FrogButtonVariant.valueOf("Destructive"))
    }

    @Test
    fun testMinTouchTarget() {
        assertEquals(48.dp, FrogButtonDefaults.MinTouchTarget)
    }
}
