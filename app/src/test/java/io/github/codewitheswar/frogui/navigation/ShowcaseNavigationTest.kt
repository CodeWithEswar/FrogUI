package io.github.codewitheswar.frogui.navigation

import androidx.compose.ui.unit.dp
import org.junit.Assert.*
import org.junit.Test

class ShowcaseNavigationTest {
    @Test fun thresholdsDependOnAvailableWindowWidth() {
        assertEquals(ShowcaseWindow.Compact, showcaseWindow(599.dp))
        assertEquals(ShowcaseWindow.Medium, showcaseWindow(600.dp))
        assertEquals(ShowcaseWindow.Medium, showcaseWindow(839.dp))
        assertEquals(ShowcaseWindow.Expanded, showcaseWindow(840.dp))
    }
    @Test fun nestedSettingsPreservesRealBackHistoryWithoutSelfLoops() {
        val detail = navigateShowcase(listOf("home"), "components/button")
        val settings = navigateShowcase(detail, "settings")
        assertEquals(listOf("home", "components/button", "settings"), settings)
        assertEquals(settings, navigateShowcase(settings, "settings"))
        assertEquals(detail, settings.dropLast(1))
        assertEquals(listOf("foundation"), navigateShowcase(settings, "foundation"))
    }
}
