package io.github.codewitheswar.frogui.showcase.components.button

import androidx.compose.ui.graphics.Color
import io.github.codewitheswar.frogui.components.button.*
import io.github.codewitheswar.frogui.showcase.colorpicker.*
import org.junit.Assert.*
import org.junit.Test

class ButtonColorStateTest {
    @Test fun hexAcceptsRgbAndArgbWithoutLosingAlpha() {
        assertEquals(0xFF18181BL, parseHexColor("#18181B")!!.argb)
        assertEquals(0xFF18181BL, parseHexColor("18181b")!!.argb)
        assertEquals(0x8018181BL, parseHexColor("#8018181B")!!.argb)
        assertEquals(0L, parseHexColor("#00000000")!!.argb)
        listOf("#123", "#GG0011", "#1234567", "##123456", "FFFFFFFFF", "").forEach { assertNull(parseHexColor(it)) }
    }
    @Test fun tokenAndLiteralRoundTripAndGenerateDistinctCode() {
        val token = FrogColorValue.Token(FrogColorToken.Surface)
        val custom = FrogColorValue.Custom(0x8018181B)
        assertEquals(token, FrogColorValue.decode(token.encode()))
        assertEquals(custom, FrogColorValue.decode(custom.encode()))
        val code = ButtonDemoState(variant = FrogButtonVariant.Outline).withColor(ButtonColorProperty.Container, token)
            .withColor(ButtonColorProperty.Border, custom).toCodeSnippet()
        assertTrue(code.contains("containerColor = FrogTheme.colors.surface"))
        assertTrue(code.contains("borderColor = Color(0x8018181B)"))
        assertEquals(2, Regex("variant = FrogButtonVariant.Outline").findAll(code).count())
    }
    @Test fun variantAndColorResetLeaveUnrelatedControlsUntouched() {
        val state = ButtonDemoState(size = FrogButtonSize.Large, loading = true, fullWidth = true)
            .withColor(ButtonColorProperty.Container, FrogColorValue.Custom(0xFF123456))
        val reset = state.withVariant(FrogButtonVariant.Outline)
        assertTrue(reset.colorOverrides.isEmpty())
        assertEquals(FrogButtonSize.Large, reset.size)
        assertTrue(reset.loading && reset.fullWidth)
        assertEquals(FrogColorValue.Custom(0), reset.colorValue(ButtonColorProperty.Container))
        assertEquals(state.copy(colorOverrides = emptyMap()), state.resetColors())
        assertEquals(state, state.withVariant(state.variant))
    }
    @Test fun draftIsSeparateUntilExplicitlyCommitted() {
        val committed = ButtonDemoState()
        val draft = committed.withColor(ButtonColorProperty.Container, FrogColorValue.Token(FrogColorToken.Surface))
        assertTrue(committed.colorOverrides.isEmpty())
        assertFalse(draft.colorOverrides.isEmpty())
        assertEquals(committed, draft.withColor(ButtonColorProperty.Container, null))
    }
    @Test fun contrastCompositesTransparencyAndHasKnownEndpoints() {
        assertEquals(21.0, colorContrast(Color.Black, Color.White, Color.White), .0001)
        assertEquals(1.0, colorContrast(Color.Transparent, Color.White, Color.White), .0001)
        assertEquals(21.0, colorContrast(Color.White, Color.Transparent, Color.Black), .0001)
        val half = colorContrast(Color.Black.copy(alpha = .5f), Color.Transparent, Color.White)
        assertTrue(half > 3.9 && half < 4.1)
    }
    @Test fun drawerBackReturnsToParentWithoutStackingWindows() {
        val root = ButtonDrawerState().open(ButtonDrawerPage.Properties)
        val color = root.push(ButtonDrawerPage.Color(ButtonColorProperty.Container))
        assertEquals(root, color.back())
        assertEquals(2, color.pages.size)
        assertNull(root.back().current)
    }
}
