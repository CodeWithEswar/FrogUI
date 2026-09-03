package io.github.codewitheswar.frogui.components.overlays.drawer

import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.registry.FrogComponentRegistry
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class FrogDrawerTest {

    @Test
    fun testDrawerStateTransitions() = runBlocking {
        val state = FrogDrawerState(FrogDrawerValue.Closed)
        assertFalse(state.isOpen)
        assertTrue(state.isClosed)
        assertEquals(FrogDrawerValue.Closed, state.currentValue)

        state.open()
        assertTrue(state.isOpen)
        assertFalse(state.isClosed)
        assertEquals(FrogDrawerValue.Open, state.currentValue)

        state.close()
        assertFalse(state.isOpen)
        assertTrue(state.isClosed)
        assertEquals(FrogDrawerValue.Closed, state.currentValue)

        state.snapTo(FrogDrawerValue.Open)
        assertTrue(state.isOpen)
        assertEquals(FrogDrawerValue.Open, state.currentValue)
    }

    @Test
    fun testDrawerDefaults() {
        assertEquals(400.dp, FrogDrawerDefaults.SideWidth)
        assertEquals(600.dp, FrogDrawerDefaults.BottomMaxWidth)
        assertEquals(64.dp, FrogDrawerDefaults.DragDismissThreshold)
        assertEquals(220, FrogDrawerDefaults.AnimationDurationMs)
    }

    @Test
    fun testDrawerPresentations() {
        val presentations = FrogDrawerPresentation.entries
        assertEquals(3, presentations.size)
        assertNotNull(FrogDrawerPresentation.valueOf("Auto"))
        assertNotNull(FrogDrawerPresentation.valueOf("Bottom"))
        assertNotNull(FrogDrawerPresentation.valueOf("Side"))
    }

    @Test
    fun testDrawerSides() {
        val sides = FrogDrawerSide.entries
        assertEquals(2, sides.size)
        assertNotNull(FrogDrawerSide.valueOf("Start"))
        assertNotNull(FrogDrawerSide.valueOf("End"))
    }

    @Test
    fun testRegistryMetadata() {
        val drawer = FrogComponentRegistry.findById("drawer")
        assertNotNull("Drawer should be registered in FrogComponentRegistry", drawer)
        assertEquals("FrogDrawer", drawer?.name)
        assertEquals("Drawer", drawer?.displayName)
        assertEquals(io.github.codewitheswar.frogui.registry.FrogComponentCategory.Overlays, drawer?.category)
        assertEquals(listOf("Auto", "Bottom", "Side"), drawer?.variants)
    }
}
