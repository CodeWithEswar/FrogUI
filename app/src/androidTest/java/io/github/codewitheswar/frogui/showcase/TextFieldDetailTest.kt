package io.github.codewitheswar.frogui.showcase

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertExists
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performSemanticsAction
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.components.textfield.FrogTextField
import io.github.codewitheswar.frogui.foundation.motion.FrogMotion
import io.github.codewitheswar.frogui.showcase.screens.ComponentDetailScreen
import io.github.codewitheswar.frogui.showcase.style.LocalFrogMotionEnabled
import io.github.codewitheswar.frogui.theme.FrogTheme
import org.junit.Rule
import org.junit.Test

class TextFieldDetailTest {
    @get:Rule
    val compose = createComposeRule()

    private fun content(id: String = "text-field") {
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
    fun textFieldUsesSharedDetailTabsAndInitialLivePreview() {
        content()
        // Standard shared tabs present
        compose.onNodeWithTag("component-detail-tabs").assertExists()
        compose.onNode(hasText("Preview") and SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Tab)).assertExists()
        compose.onNode(hasText("Code") and SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Tab)).assertExists()
        compose.onNode(hasText("API") and SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Tab)).assertExists()
        compose.onNode(hasText("Accessibility") and SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Tab)).assertExists()

        // FrogTextField initial live preview rendered with label and value
        compose.onNodeWithTag("showcase-textfield-preview").assertIsDisplayed()
        compose.onNodeWithText("Email").assertIsDisplayed()
        compose.onNodeWithText("alex@example.com").assertIsDisplayed()
    }

    @Test
    fun liveTypingUpdatesPreviewAndValue() {
        content()

        val inputNode = compose.onNodeWithTag("showcase-textfield-preview")
        inputNode.assertIsDisplayed()
        inputNode.performTextClearance()
        inputNode.performTextInput("jordan@acme.corp")

        compose.onNodeWithText("jordan@acme.corp").assertIsDisplayed()

        // Verify Code tab reflects updated text value in remember declaration
        tab("Code")
        compose.onNodeWithText("jordan@acme.corp", substring = true).assertExists()
    }

    @Test
    fun variantSelectionUpdatesGeneratedCode() {
        content()

        // Switch to Outline variant
        click("Outline")

        // Verify Code tab generates Outline variant
        tab("Code")
        compose.onNodeWithText("variant = FrogTextFieldVariant.Outline", substring = true).assertExists()

        // Switch to Underline variant
        tab("Preview")
        click("Underline")

        tab("Code")
        compose.onNodeWithText("variant = FrogTextFieldVariant.Underline", substring = true).assertExists()
    }

    @Test
    fun errorValidationShowsErrorTextAndSemantics() {
        content()

        // Click Invalid email preset
        click("Invalid email")

        // Error message appears
        compose.onNodeWithText("Please enter a valid email address").assertIsDisplayed()

        // Error semantics attached to preview node
        compose.onNodeWithTag("showcase-textfield-preview")
            .assert(SemanticsMatcher.expectValue(SemanticsProperties.Error, "Please enter a valid email address"))

        // Verify Code tab generates errorText
        tab("Code")
        compose.onNodeWithText("errorText = \"Please enter a valid email address\"", substring = true).assertExists()
    }

    @Test
    fun readOnlyAndDisabledStatesReflectedInPreviewAndCode() {
        content()

        // Toggle Read only switch
        click("Read only")

        tab("Code")
        compose.onNodeWithText("readOnly = true", substring = true).assertExists()

        tab("Preview")
        click("Read only") // toggle off

        // Toggle Enabled switch off
        click("Enabled")
        compose.onNodeWithTag("showcase-textfield-preview").assertIsNotEnabled()

        tab("Code")
        compose.onNodeWithText("enabled = false", substring = true).assertExists()
    }

    @Test
    fun apiTabAndAccessibilityTabProvideFullGuidance() {
        content()

        // API Tab
        tab("API")
        click("variant")
        compose.onNodeWithText("API property").assertIsDisplayed()
        compose.onNodeWithText("FrogTextFieldVariant").assertExists()
        compose.onNodeWithContentDescription("Close drawer").performClick()

        // Accessibility Tab
        tab("Accessibility")
        compose.onNodeWithText("Persistent label association").performScrollTo().assertIsDisplayed()
        compose.onNodeWithText("Semantic error announcement").performScrollTo().assertIsDisplayed()
        compose.onNodeWithText("Disabled vs read-only distinction").performScrollTo().assertIsDisplayed()
    }
}
