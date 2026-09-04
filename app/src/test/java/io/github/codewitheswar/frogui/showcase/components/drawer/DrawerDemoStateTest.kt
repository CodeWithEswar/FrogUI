package io.github.codewitheswar.frogui.showcase.components.drawer

import io.github.codewitheswar.frogui.components.overlays.drawer.*
import org.junit.Assert.*
import org.junit.Test

class DrawerDemoStateTest {
    @Test fun codeUsesPublicApiAndOnlySelectedOptions() {
        val defaults = DrawerDemoState().toCodeSnippet()
        assertTrue(defaults.contains("rememberFrogDrawerState()"))
        assertFalse(defaults.contains("presentation ="))
        assertFalse(defaults.contains("FrogIcons"))
        assertFalse(defaults.contains("DrawerDemoState"))
        val code = DrawerDemoState(presentation = FrogDrawerPresentation.Side, side = FrogDrawerSide.Start, showSubtitle = false, showFooter = false, longContent = true).toCodeSnippet()
        assertTrue(code.contains("presentation = FrogDrawerPresentation.Side"))
        assertTrue(code.contains("side = FrogDrawerSide.Start"))
        assertTrue(code.contains("repeat(12)"))
        assertFalse(code.contains("subtitle ="))
        assertFalse(code.contains("footer ="))
    }
    @Test fun titleAndSubtitleRemainSafeKotlinLiterals() {
        val code = DrawerDemoState(title = "\"draft\"\n\$value\\path").toCodeSnippet()
        assertTrue(code.contains("title = \"\\\"draft\\\"\\n\\\$value\\\\path\""))
    }
}
