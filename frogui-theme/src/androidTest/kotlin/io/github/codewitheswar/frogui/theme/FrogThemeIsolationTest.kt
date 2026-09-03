package io.github.codewitheswar.frogui.theme

import androidx.compose.ui.test.junit4.createComposeRule
import io.github.codewitheswar.frogui.testing.setFrogContent
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class FrogThemeIsolationTest {
    @get:Rule val compose = createComposeRule()

    @Test fun nestedPreviewThemeDoesNotChangeTheSurroundingTheme() {
        var before = false
        var inside = true
        var after = false
        compose.setFrogContent(darkTheme = true) {
            before = FrogTheme.colors.isDark
            FrogTheme(darkTheme = false) { inside = FrogTheme.colors.isDark }
            after = FrogTheme.colors.isDark
        }
        compose.runOnIdle {
            assertEquals(true, before)
            assertEquals(false, inside)
            assertEquals(true, after)
        }
    }
}
