package io.github.codewitheswar.frogui.showcase.components.iconbutton

import androidx.compose.ui.graphics.Color
import io.github.codewitheswar.frogui.components.button.*
import io.github.codewitheswar.frogui.showcase.colorpicker.*
import org.junit.Assert.*
import org.junit.Test

class IconButtonColorStateTest {
    @Test
    fun defaultColorsMatchExpectedTokens() {
        val state = IconButtonDemoState(variant = FrogIconButtonVariant.Filled)
        assertEquals(FrogColorValue.Token(FrogColorToken.Primary), state.colorValue(IconButtonColorProperty.Container))
        assertEquals(FrogColorValue.Token(FrogColorToken.PrimaryForeground), state.colorValue(IconButtonColorProperty.Content))
        assertEquals(FrogColorValue.Custom(0), state.colorValue(IconButtonColorProperty.Border))
    }

    @Test
    fun colorOverridesApplyCorrectly() {
        val state = IconButtonDemoState(variant = FrogIconButtonVariant.Filled)
            .withColor(IconButtonColorProperty.Container, FrogColorValue.Custom(0xFFFF0000L))
            .withColor(IconButtonColorProperty.Content, FrogColorValue.Token(FrogColorToken.Destructive))

        assertEquals(FrogColorValue.Custom(0xFFFF0000L), state.colorValue(IconButtonColorProperty.Container))
        assertEquals(FrogColorValue.Token(FrogColorToken.Destructive), state.colorValue(IconButtonColorProperty.Content))
    }

    @Test
    fun resetColorsRestoresDefaults() {
        val state = IconButtonDemoState(variant = FrogIconButtonVariant.Filled)
            .withColor(IconButtonColorProperty.Container, FrogColorValue.Custom(0xFFFF0000L))

        assertEquals(FrogColorValue.Custom(0xFFFF0000L), state.colorValue(IconButtonColorProperty.Container))
        val reset = state.resetColors()
        assertEquals(FrogColorValue.Token(FrogColorToken.Primary), reset.colorValue(IconButtonColorProperty.Container))
    }

    @Test
    fun variantChangeClearsOverrides() {
        val state = IconButtonDemoState(variant = FrogIconButtonVariant.Filled)
            .withColor(IconButtonColorProperty.Container, FrogColorValue.Custom(0xFFFF0000L))

        val tonal = state.withVariant(FrogIconButtonVariant.Tonal)
        assertTrue(tonal.colorOverrides.isEmpty())
        assertEquals(FrogColorValue.Token(FrogColorToken.Secondary), tonal.colorValue(IconButtonColorProperty.Container))
    }

    @Test
    fun codeGenerationIncludesColorsWhenOverridden() {
        val state = IconButtonDemoState(variant = FrogIconButtonVariant.Filled)
            .withColor(IconButtonColorProperty.Container, FrogColorValue.Token(FrogColorToken.Destructive))

        val code = state.toCodeSnippet()
        assertTrue(code.contains("colors = FrogIconButtonDefaults.colors("))
        assertTrue(code.contains("containerColor = FrogTheme.colors.destructive"))
    }
}
