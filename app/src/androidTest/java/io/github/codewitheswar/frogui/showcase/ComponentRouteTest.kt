package io.github.codewitheswar.frogui.showcase

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.assertIsDisplayed
import io.github.codewitheswar.frogui.showcase.screens.ComponentDetailScreen
import io.github.codewitheswar.frogui.testing.setFrogContent
import org.junit.Rule
import org.junit.Test

class ComponentRouteTest {
    @get:Rule val compose = createComposeRule()

    @Test fun unknownRouteShowsAnExplicitMissingDestination() {
        compose.setFrogContent {
            ComponentDetailScreen("not-registered", onBack = {})
        }
        compose.onNodeWithText("Component not found").assertIsDisplayed()
        compose.onNodeWithText("Continue").assertDoesNotExist()
    }
}
