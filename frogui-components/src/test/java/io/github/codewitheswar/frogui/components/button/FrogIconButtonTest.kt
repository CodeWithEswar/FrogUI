package io.github.codewitheswar.frogui.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.registry.FrogComponentRegistry
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test

class FrogIconButtonTest {

    @Test
    fun suppliedColorsOwnEnabledAndDisabledBorders() {
        val colors = FrogIconButtonColors(
            containerColor = Color.Black,
            contentColor = Color.White,
            disabledContainerColor = Color.Gray,
            disabledContentColor = Color.White,
            borderColor = Color.Red,
            disabledBorderColor = Color.Blue
        )
        assertEquals(BorderStroke(1.dp, Color.Red), FrogIconButtonDefaults.border(colors, true))
        assertEquals(BorderStroke(1.dp, Color.Blue), FrogIconButtonDefaults.border(colors, false))
        assertNull(FrogIconButtonDefaults.border(colors.copy(disabledBorderColor = Color.Transparent), false))
    }

    @Test
    fun testIconButtonSizes() {
        assertEquals(32.dp, FrogIconButtonSize.Small.containerSize)
        assertEquals(16.dp, FrogIconButtonSize.Small.iconSize)
        assertEquals(48.dp, FrogIconButtonSize.Small.minTouchTarget)

        assertEquals(40.dp, FrogIconButtonSize.Medium.containerSize)
        assertEquals(18.dp, FrogIconButtonSize.Medium.iconSize)
        assertEquals(48.dp, FrogIconButtonSize.Medium.minTouchTarget)

        assertEquals(48.dp, FrogIconButtonSize.Large.containerSize)
        assertEquals(20.dp, FrogIconButtonSize.Large.iconSize)
        assertEquals(48.dp, FrogIconButtonSize.Large.minTouchTarget)
    }

    @Test
    fun testIconButtonVariants() {
        val variants = FrogIconButtonVariant.entries
        assertEquals(4, variants.size)
        assertNotNull(FrogIconButtonVariant.valueOf("Filled"))
        assertNotNull(FrogIconButtonVariant.valueOf("Tonal"))
        assertNotNull(FrogIconButtonVariant.valueOf("Outline"))
        assertNotNull(FrogIconButtonVariant.valueOf("Ghost"))
    }

    @Test
    fun testMinTouchTarget() {
        assertEquals(48.dp, FrogIconButtonDefaults.MinTouchTarget)
        assertEquals(1.dp, FrogIconButtonDefaults.BorderWidth)
    }

    @Test
    fun registryCapabilitiesMatchPublicEnums() {
        val iconButton = FrogComponentRegistry.findById("icon-button")
        if (iconButton != null) {
            assertEquals(FrogIconButtonVariant.entries.map { it.name }, iconButton.variants)
            assertEquals(FrogIconButtonSize.entries.map { it.name }, iconButton.sizes)
        }
    }
}
