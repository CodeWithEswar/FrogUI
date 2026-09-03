package io.github.codewitheswar.frogui.showcase

import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.espresso.Espresso
import io.github.codewitheswar.frogui.MainActivity
import org.junit.Rule
import org.junit.Test

/** Uses the real edge-to-edge activity and Android IME, without changing system settings. */
class ShowcaseImeTest {
    @get:Rule val compose = createAndroidComposeRule<MainActivity>()
    @Test fun searchRemainsUsableWhileDockYieldsToTheKeyboard() {
        compose.onNode(hasText("Components") and SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Tab)).performClick()
        compose.onNode(hasSetTextAction()).performClick().performTextInput("Button")
        compose.waitUntil(5000) { compose.onAllNodesWithTag("bottom-navigation").fetchSemanticsNodes().isEmpty() }
        compose.onNode(hasSetTextAction()).assertIsDisplayed().assertTextContains("Button")
        Espresso.pressBack()
        compose.waitUntil(5000) { compose.onAllNodesWithTag("bottom-navigation").fetchSemanticsNodes().isNotEmpty() }
        compose.onNode(hasText("Components") and SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Tab)).assertIsSelected()
    }
}
