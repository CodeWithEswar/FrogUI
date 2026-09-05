package io.github.codewitheswar.frogui.components.fab

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class FrogFloatingActionButtonTest {

    @Test
    fun defaultDimensionsMatchSpecifications() {
        assertEquals(56.dp, FrogFloatingActionButtonDefaults.containerHeight(FrogFabPresentation.Regular))
        assertEquals(40.dp, FrogFloatingActionButtonDefaults.containerHeight(FrogFabPresentation.Small))
        assertEquals(48.dp, FrogFloatingActionButtonDefaults.containerHeight(FrogFabPresentation.Extended))

        assertEquals(24.dp, FrogFloatingActionButtonDefaults.iconSize(FrogFabPresentation.Regular))
        assertEquals(20.dp, FrogFloatingActionButtonDefaults.iconSize(FrogFabPresentation.Small))
        assertEquals(20.dp, FrogFloatingActionButtonDefaults.iconSize(FrogFabPresentation.Extended))

        assertEquals(48.dp, FrogFloatingActionButtonDefaults.MinTouchTarget)
        assertTrue(FrogFloatingActionButtonDefaults.MinTouchTarget >= 48.dp)
        assertEquals(10.dp, FrogFloatingActionButtonDefaults.IconLabelSpacing)
    }

    @Test
    fun elevationModelDefaults() {
        val customElevation = FrogFabElevation(
            default = 4.dp,
            pressed = 8.dp,
            focused = 4.dp,
            disabled = 0.dp
        )
        assertEquals(4.dp, customElevation.default)
        assertEquals(8.dp, customElevation.pressed)
        assertEquals(4.dp, customElevation.focused)
        assertEquals(0.dp, customElevation.disabled)
    }

    @Test
    fun colorsModelIntegrity() {
        val colors = FrogFabColors(
            containerColor = Color.Black,
            contentColor = Color.White,
            disabledContainerColor = Color.DarkGray,
            disabledContentColor = Color.Gray,
            pressedOverlayColor = Color.LightGray,
            focusRingColor = Color.Cyan
        )
        assertEquals(Color.Black, colors.containerColor)
        assertEquals(Color.White, colors.contentColor)
        assertEquals(Color.DarkGray, colors.disabledContainerColor)
        assertEquals(Color.Gray, colors.disabledContentColor)
        assertEquals(Color.LightGray, colors.pressedOverlayColor)
        assertEquals(Color.Cyan, colors.focusRingColor)
    }

    @Test
    fun presentationsCoverAllForms() {
        val forms = FrogFabPresentation.entries
        assertEquals(3, forms.size)
        assertTrue(forms.contains(FrogFabPresentation.Regular))
        assertTrue(forms.contains(FrogFabPresentation.Small))
        assertTrue(forms.contains(FrogFabPresentation.Extended))
    }
}
