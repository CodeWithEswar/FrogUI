package io.github.codewitheswar.frogui.showcase

import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.InputMode
import androidx.compose.ui.input.InputModeManager
import androidx.compose.ui.platform.LocalInputModeManager
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.unit.dp
import androidx.test.platform.app.InstrumentationRegistry
import io.github.codewitheswar.frogui.navigation.*
import io.github.codewitheswar.frogui.showcase.code.FrogCodeBlock
import io.github.codewitheswar.frogui.showcase.icons.FrogIcons
import io.github.codewitheswar.frogui.showcase.markdown.FrogMarkdown
import io.github.codewitheswar.frogui.showcase.style.*
import io.github.codewitheswar.frogui.testing.setFrogContent
import io.github.codewitheswar.frogui.theme.FrogTheme
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalTestApi::class)
class ShowcaseInteractionTest {
    @get:Rule val compose = createComposeRule()
    private fun tab(label: String) = compose.onNode(hasText(label) and SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Tab))

    @Test fun dockSelectionHasSemanticsTouchTargetsAndKeyboardActivation() {
        lateinit var inputMode: InputModeManager
        compose.setFrogContent {
            inputMode = LocalInputModeManager.current
            var route by remember { mutableStateOf("home") }
            CompositionLocalProvider(LocalFrogMotionEnabled provides false) {
                FrogShowcaseBottomBar(showcaseDestinations, route, { route = it })
            }
        }
        tab("Home").assertIsSelected()
        tab("Components").assertIsNotSelected().assertHeightIsAtLeast(48.dp).assertWidthIsAtLeast(48.dp)
        compose.runOnIdle { inputMode.requestInputMode(InputMode.Keyboard) }
        tab("Components").performSemanticsAction(SemanticsActions.RequestFocus)
        tab("Components").assertIsFocused().performKeyInput { keyDown(Key.Enter); keyUp(Key.Enter) }
        tab("Components").assertIsSelected()
        tab("Home").assertIsNotSelected()
    }

    @Test fun toolbarBackIsARealAccessibleAction() {
        var backs = 0
        compose.setFrogContent { FrogShowcaseTopBar("Button", subtitle = "Actions · Experimental", navigationIcon = { ShowcaseBackButton(onClick = { backs++ }) }) }
        compose.onNodeWithContentDescription("Back").assertHasClickAction().assertHeightIsAtLeast(48.dp).performClick()
        assertEquals(1, backs)
    }

    @Test fun copyWritesFullUnwrappedSourceAndReportsSuccess() {
        val code = "val message = \"${"long source ".repeat(12)}\"\n    next()"
        compose.setFrogContent { FrogCodeBlock(code) }
        compose.onNodeWithContentDescription("Copy code").performClick()
        compose.waitUntil { compose.onAllNodesWithText("Copied").fetchSemanticsNodes().isNotEmpty() }
        compose.onNodeWithText("Copied").assertIsDisplayed()
        val clipboard = InstrumentationRegistry.getInstrumentation().targetContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        compose.runOnIdle { assertEquals(code, clipboard.primaryClip?.getItemAt(0)?.text.toString()) }
        compose.onNode(hasScrollAction()).assertExists()
    }

    @Test fun markdownFencesUseCodeActionsAndHeadingsKeepSemantics() {
        compose.setFrogContent { FrogMarkdown("# Usage\n\nUse **FrogTheme** and `FrogButton`.\n\n```kotlin\nval count = 1\n```") }
        compose.onNodeWithText("Usage").assert(SemanticsMatcher.keyIsDefined(SemanticsProperties.Heading))
        compose.onNodeWithContentDescription("Copy code").assertIsDisplayed().performClick()
        compose.onNodeWithText("val count = 1").assertExists()
    }

    @Test fun appearanceSwitchRetainsNavigationAndNestedDetailHidesDock() {
        var appearance by mutableStateOf(ShowcaseAppearance.Light)
        compose.setContent {
            FrogTheme(darkTheme = appearance == ShowcaseAppearance.Dark) {
                CompositionLocalProvider(LocalFrogMotionEnabled provides false) { FrogUiShell(appearance, { appearance = it }, false, {}) }
            }
        }
        tab("Components").performClick()
        compose.onNodeWithContentDescription("Appearance").performClick()
        compose.onNodeWithText("Dark").performClick()
        compose.runOnIdle { assertEquals(ShowcaseAppearance.Dark, appearance) }
        tab("Components").assertIsSelected()
        compose.onNodeWithText("Button", substring = false).performClick()
        compose.onNodeWithTag("bottom-navigation").assertDoesNotExist()
        compose.onNodeWithContentDescription("Back").performClick()
        tab("Components").assertIsSelected()
    }

    @Test fun navigationAndSearchSurviveSavedStateRestoration() {
        val restoration = StateRestorationTester(compose)
        restoration.setContent {
            FrogTheme(darkTheme = false) {
                CompositionLocalProvider(LocalFrogMotionEnabled provides false) { FrogUiShell(ShowcaseAppearance.Light, {}, false, {}) }
            }
        }
        tab("Components").performClick()
        compose.onNode(hasSetTextAction()).performTextInput("Button")
        restoration.emulateSavedInstanceStateRestore()
        tab("Components").assertIsSelected()
        compose.onNode(hasSetTextAction()).assertTextContains("Button")
    }
}
