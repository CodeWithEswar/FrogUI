package io.github.codewitheswar.frogui.showcase

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.semantics.*
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.unit.dp
import androidx.test.espresso.Espresso.pressBack
import io.github.codewitheswar.frogui.navigation.*
import io.github.codewitheswar.frogui.showcase.screens.ComponentDetailScreen
import io.github.codewitheswar.frogui.showcase.style.LocalFrogMotionEnabled
import io.github.codewitheswar.frogui.theme.FrogTheme
import io.github.codewitheswar.frogui.foundation.motion.FrogMotion
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertTrue

class SharedComponentDetailTest {
    @get:Rule val compose = createComposeRule()
    private fun content(id: String = "drawer") { compose.setContent { FrogTheme(darkTheme = true, motion = FrogMotion(0, 0, 0)) { CompositionLocalProvider(LocalFrogMotionEnabled provides false) { Box(Modifier.width(390.dp).fillMaxHeight().background(FrogTheme.colors.background)) { ComponentDetailScreen(id, {}) } } } } }
    private fun tab(label: String) = compose.onNode(hasText(label) and SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Tab)).performSemanticsAction(SemanticsActions.OnClick) { it() }
    private fun click(label: String) {
        val node = compose.onNode(hasText(label) and hasClickAction())
        if (generateSequence(node.fetchSemanticsNode().parent) { it.parent }.any { it.config.contains(SemanticsActions.ScrollBy) }) node.performScrollTo()
        node.performClick()
    }
    @Test fun drawerUsesSharedTabsCodeApiAndAccessibility() {
        content()
        compose.onNodeWithTag("component-detail-tabs").assertExists()
        compose.onAllNodes(hasText("Docs") and SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Tab)).assertCountEquals(0)
        click("Side")
        tab("Code")
        compose.onNodeWithText("presentation = FrogDrawerPresentation.Side", substring = true).assertExists()
        compose.onNodeWithContentDescription("Copy code").performScrollTo().performClick()
        compose.onNodeWithText("Copied").assertIsDisplayed()
        tab("API"); click("presentation")
        compose.onNodeWithText("API property").assertIsDisplayed()
        compose.onNodeWithText("Values").assertExists()
        compose.onNodeWithContentDescription("Close drawer").assertHeightIsAtLeast(48.dp).performClick()
        tab("Accessibility")
        compose.onNodeWithText("Pane and dismissal").performScrollTo().assertIsDisplayed()
    }
    @Test fun boundedOverlayUsesActualPresentationAndCloses() {
        content()
        click("Side")
        compose.onNodeWithTag("open-drawer-preview").performScrollTo().performClick()
        compose.onNodeWithTag("drawer-side").assertExists()
        val bounds = compose.onNodeWithTag("drawer-side").getUnclippedBoundsInRoot()
        assertTrue(bounds.right - bounds.left <= 342.dp)
        compose.onNodeWithContentDescription("Close drawer").performScrollTo().performClick()
        compose.onNodeWithTag("drawer-side").assertDoesNotExist()
        click("Bottom")
        compose.onNodeWithTag("open-drawer-preview").performScrollTo().performClick()
        compose.onNodeWithTag("drawer-bottom").assertExists()
        pressBack()
        compose.onNodeWithTag("drawer-bottom").assertDoesNotExist()
    }
    @Test fun inspectorControlsPersistAcrossTabsAndRestore() {
        val restoration = StateRestorationTester(compose)
        restoration.setContent { FrogTheme(darkTheme = true, motion = FrogMotion(0, 0, 0)) { Box(Modifier.width(390.dp).fillMaxHeight()) { ComponentDetailScreen("drawer", {}) } } }
        click("Customize")
        compose.onNodeWithText("Drawer title").performScrollTo().performClick().performTextReplacement("Saved drawer title")
        androidx.test.espresso.Espresso.closeSoftKeyboard()
        click("Long content")
        compose.onNodeWithContentDescription("Close drawer").performClick()
        tab("Code")
        compose.onNodeWithContentDescription("Expand code").performScrollTo().performClick()
        compose.onNodeWithText("title = \"Saved drawer title\"", substring = true).assertExists()
        compose.onNodeWithText("repeat(12)", substring = true).assertExists()
        restoration.emulateSavedInstanceStateRestore()
        compose.onNodeWithText("title = \"Saved drawer title\"", substring = true).assertExists()
        compose.onNodeWithContentDescription("Reset component properties").performScrollTo().performClick()
        compose.onAllNodesWithText("title = \"Saved drawer title\"", substring = true).assertCountEquals(0)
    }
    @Test fun missingDefinitionUsesSharedErrorAndCatalogLinksUseSharedRoutes() {
        content("not-registered")
        compose.onNodeWithText("Component unavailable").assertIsDisplayed()
        compose.onNode(hasText("Back to components") and hasClickAction()).assertExists()
    }
    @Test fun incomingComponentLinkOpensTheSameDetailAndHidesDock() {
        compose.setContent { FrogTheme { FrogUiShell(ShowcaseAppearance.Dark, {}, false, {}, incomingLink = ComponentDeepLink("drawer", 1)) } }
        compose.onNodeWithTag("component-detail").assertExists()
        compose.onNodeWithTag("open-drawer-preview").assertExists()
        compose.onNodeWithTag("bottom-navigation").assertDoesNotExist()
        compose.onNodeWithContentDescription("Back").performClick()
        compose.onNodeWithTag("bottom-navigation").assertIsDisplayed()
    }
}
