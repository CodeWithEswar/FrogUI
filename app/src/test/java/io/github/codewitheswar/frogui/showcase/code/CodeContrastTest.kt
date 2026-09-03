package io.github.codewitheswar.frogui.showcase.code

import androidx.compose.ui.graphics.luminance
import io.github.codewitheswar.frogui.theme.FrogThemeDefaults
import org.junit.Assert.assertTrue
import org.junit.Test

class CodeContrastTest {
    @Test fun textTokensMeetNormalTextContrastInBothThemes() {
        listOf(FrogThemeDefaults.lightColors(), FrogThemeDefaults.darkColors()).forEach { theme ->
            val palette = FrogCodeColors.from(theme)
            TokenKind.entries.forEach { token ->
                val fg = palette.color(token).luminance()
                val bg = palette.background.luminance()
                val contrast = (maxOf(fg, bg) + .05f) / (minOf(fg, bg) + .05f)
                assertTrue("$token dark=${theme.isDark} contrast=$contrast", contrast >= 4.5f)
            }
        }
    }
}
