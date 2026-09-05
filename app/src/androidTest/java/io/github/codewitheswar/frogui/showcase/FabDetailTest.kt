package io.github.codewitheswar.frogui.showcase

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.*
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.components.fab.FrogFloatingActionButton
import io.github.codewitheswar.frogui.foundation.motion.FrogMotion
import io.github.codewitheswar.frogui.showcase.screens.ComponentDetailScreen
import io.github.codewitheswar.frogui.showcase.style.LocalFrogMotionEnabled
import io.github.codewitheswar.frogui.theme.FrogTheme
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class FabDetailTest {
    @get:Rule
    val compose = createComposeRule()

    private fun content(id: String = "fab") {
        compose.setContent {
            FrogTheme(darkTheme = true, motion = FrogMotion(0, 0, 0)) {
                CompositionLocalProvider(LocalFrogMotionEnabled provides false) {
                    Box(
                        Modifier
                            .width(390.dp)
                            .fillMaxHeight()
                            .background(FrogTheme.colors.background)
                    ) {
                        ComponentDetailScreen(id, onBack = {})
                    }
                }
            }
        }
    }

    private fun tab(label: String) = compose.onNode(
        hasText(label) and SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Tab)
    ).performSemanticsAction(SemanticsActions.OnClick) { it() }

    private fun click(label: String) {
        val node = compose.onNode(hasText(label) and hasClickAction())
        if (generateSequence(node.fetchSemanticsNode().parent) { it.parent }
                .any { it.config.contains(SemanticsActions.ScrollBy) }) {
            node.performScrollTo()
        }
        node.performClick()
    }

    @Test
    fun fabUsesSharedDetailTabsAndInitialLivePreview() {
        content()
        // Standard shared tabs present
        compose.onNodeWithTag("component-detail-tabs").assertExists()
        compose.onNode(hasText("Preview") and SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Tab)).assertExists()
        compose.onNode(hasText("Code") and SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Tab)).assertExists()
        compose.onNode(hasText("API") and SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Tab)).assertExists()
        compose.onNode(hasText("Accessibility") and SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Tab)).assertExists()

        // FAB live preview rendered with proper semantics
        compose.onNode(hasContentDescription("Create item") and SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Button))
            .assertIsDisplayed()
            .performClick()
    }

    @Test
    fun presentationControlsUpdatePreviewAndGeneratedCode() {
        content()

        // Switch to Extended presentation
        click("Extended")

        // Extended FAB should display label text "Create"
        compose.onNodeWithText("Create").assertIsDisplayed()

        // Verify Code tab generates Extended presentation
        tab("Code")
        compose.onNodeWithText("presentation = FrogFabPresentation.Extended", substring = true).assertExists()
        compose.onNodeWithText("label = {", substring = true).assertExists()

        // Switch back to Preview tab
        tab("Preview")

        // Toggle Expanded switch off
        click("Expanded")

        // Label should collapse/hide
        compose.onAllNodesWithText("Create").assertCountEquals(0)

        // Verify Code tab generates expanded = false
        tab("Code")
        compose.onNodeWithText("expanded = false", substring = true).assertExists()
    }

    @Test
    fun visibilityControlHidesFabAndRemovesSemantics() {
        content()

        // Toggle Visible switch off
        click("Visible")

        // Live preview FAB should not exist in semantics tree
        compose.onAllNodes(hasContentDescription("Create item") and SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Button))
            .assertCountEquals(0)

        // Hidden indicator appears
        compose.onNodeWithText("Component hidden (visible = false)").assertIsDisplayed()

        // Code tab reflects visible = false
        tab("Code")
        compose.onNodeWithText("visible = false", substring = true).assertExists()
    }

    @Test
    fun apiTabAndAccessibilityTabProvideFullGuidance() {
        content()

        // API Tab
        tab("API")
        click("presentation")
        compose.onNodeWithText("API property").assertIsDisplayed()
        compose.onNodeWithText("Values").assertExists()
        compose.onNodeWithContentDescription("Close drawer").performClick()

        // Accessibility Tab
        tab("Accessibility")
        compose.onNodeWithText("Accessible action name").performScrollTo().assertIsDisplayed()
        compose.onNodeWithText("Touch target").performScrollTo().assertIsDisplayed()
        compose.onNodeWithText("State-safe motion").performScrollTo().assertIsDisplayed()
    }

    @Test
    fun smallPresentationMaintainsMinimumTouchTarget() {
        content()

        click("Small")

        // Small FAB is displayed with Role.Button
        val fabNode = compose.onNode(hasContentDescription("Create item") and SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Button))
        fabNode.assertIsDisplayed()

        val bounds = fabNode.getUnclippedBoundsInRoot()
        assertTrue("Small FAB height must be at least 48dp", bounds.bottom - bounds.top >= 48.dp)
        assertTrue("Small FAB width must be at least 48dp", bounds.right - bounds.left >= 48.dp)
    }
}
