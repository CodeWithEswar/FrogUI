package io.github.codewitheswar.frogui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import org.junit.Assert.*
import org.junit.Test

class FrogThemeContrastTest {
    private fun contrast(a: Color, b: Color): Double {
        val first = a.luminance().toDouble()
        val second = b.luminance().toDouble()
        return (maxOf(first, second) + .05) / (minOf(first, second) + .05)
    }

    @Test fun normalTextPairsMeetTheirThresholdInBothCanonicalPalettes() {
        listOf(FrogThemeDefaults.lightColors(), FrogThemeDefaults.darkColors()).forEach { colors ->
            val pairs = listOf("body" to (colors.foreground to colors.background),
                "primary" to (colors.primaryForeground to colors.primary),
                "secondary" to (colors.secondaryForeground to colors.secondary),
                "destructive" to (colors.destructiveForeground to colors.destructive),
                "muted surface" to (colors.mutedForeground to colors.surface),
                "muted elevated" to (colors.mutedForeground to colors.surfaceElevated),
                "muted fill" to (colors.mutedForeground to colors.muted))
            pairs.forEach { (name, pair) ->
                val ratio = contrast(pair.first, pair.second)
                assertTrue("$name dark=${colors.isDark}: $ratio", ratio >= 4.5)
            }
        }
    }

    @Test fun focusIndicatorContrastsWithAdjacentNeutralSurfaces() {
        listOf(FrogThemeDefaults.lightColors(), FrogThemeDefaults.darkColors()).forEach { colors ->
            listOf(colors.background, colors.surface, colors.surfaceElevated, colors.muted).forEach { surface ->
                assertTrue("Focus dark=${colors.isDark}", contrast(colors.focusRing, surface) >= 3.0)
            }
        }
    }

    @Test fun customPaletteFlowsThroughMaterialContainerErrorAndInverseRoles() {
        val original = FrogThemeDefaults.lightColors()
        val custom = original.copy(primary = Color.Blue, destructive = Color.Red, surfaceElevated = Color.Green)
        val material = custom.toMaterialColors()
        assertEquals(custom.primary, material.primary)
        assertEquals(custom.secondary, material.primaryContainer)
        assertEquals(custom.destructive, material.errorContainer)
        assertEquals(custom.destructiveForeground, material.onErrorContainer)
        assertEquals(custom.surfaceElevated, material.surfaceContainerHigh)
        assertEquals(custom.foreground, material.inverseSurface)
        assertEquals(custom.primary, material.primaryFixed)
        assertEquals(custom.primaryForeground, material.onPrimaryFixedVariant)
        assertEquals(Color.Transparent, material.surfaceTint)
        assertNotEquals(original.primary, custom.primary)
    }
}
