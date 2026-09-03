package io.github.codewitheswar.frogui.showcase

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.toPixelMap
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.*
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.unit.dp
import androidx.test.espresso.Espresso.pressBack
import androidx.test.platform.app.InstrumentationRegistry
import io.github.codewitheswar.frogui.components.button.FrogButton
import io.github.codewitheswar.frogui.components.button.FrogButtonSize
import io.github.codewitheswar.frogui.showcase.colorpicker.FrogColorValue
import io.github.codewitheswar.frogui.showcase.components.button.*
import io.github.codewitheswar.frogui.showcase.style.LocalFrogMotionEnabled
import io.github.codewitheswar.frogui.testing.setFrogContent
import io.github.codewitheswar.frogui.theme.FrogTheme
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import java.io.File

class ButtonDetailTest {
    @get:Rule val compose = createComposeRule()
    private fun drawerNode(matcher: SemanticsMatcher) = compose.onNode(matcher and hasAnyAncestor(hasTestTag("drawer-window")))
    private fun click(label: String) {
        val matcher = hasText(label) and hasClickAction()
        val node = if (compose.onAllNodesWithTag("drawer-window").fetchSemanticsNodes().isNotEmpty()) drawerNode(matcher) else compose.onNode(matcher)
        if (generateSequence(node.fetchSemanticsNode().parent) { it.parent }.any { it.config.contains(SemanticsActions.ScrollBy) }) node.performScrollTo()
        node.performClick()
    }
    private fun tab(label: String) = compose.onNode(hasText(label) and SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Tab)).performSemanticsAction(SemanticsActions.OnClick) { it() }
    private fun content() { compose.setFrogContent(darkTheme = true) { CompositionLocalProvider(LocalFrogMotionEnabled provides false) { Box(Modifier.width(360.dp).fillMaxHeight().background(FrogTheme.colors.background)) { ButtonScreen("button", {}) } } } }
    private fun color() { click("Customize"); click("Container color"); compose.onNodeWithTag("drawer-bottom").assertIsDisplayed() }
    private fun capture(name: String) {
        val directory = File(InstrumentationRegistry.getInstrumentation().targetContext.getExternalFilesDir(null), "showcase-qa").apply { mkdirs() }
        compose.onNodeWithTag("drawer-window").captureToImage().asAndroidBitmap().let { bitmap -> File(directory, "$name.png").outputStream().use { bitmap.compress(Bitmap.CompressFormat.PNG, 100, it) } }
    }
    @Test fun tokenCancelApplyAndVariantReset() {
        content()
        color()
        click("Surface")
        capture("detail-color-tokens-dark")
        click("Cancel")
        compose.onNodeWithText("Properties").assertExists()
        compose.onNodeWithContentDescription("Close drawer").performClick()
        tab("Code")
        compose.onAllNodesWithText("containerColor = FrogTheme.colors.surface", substring = true).assertCountEquals(0)
        color()
        click("Surface")
        click("Apply")
        compose.onNodeWithContentDescription("Close drawer").performClick()
        compose.onNodeWithText("containerColor = FrogTheme.colors.surface", substring = true).assertExists()
        click("Outline")
        compose.onAllNodesWithText("containerColor = FrogTheme.colors.surface", substring = true).assertCountEquals(0)
    }
    @Test fun hexValidationAlphaAndReset() {
        content(); color(); click("Custom")
        compose.onNodeWithText("Hex color").performScrollTo().performTextReplacement("invalid")
        compose.onNode(hasText("Apply") and hasClickAction()).assertIsNotEnabled()
        compose.onNodeWithText("Hex color").performTextReplacement("#8018181B")
        compose.onNode(hasText("Apply") and hasClickAction()).assertIsEnabled()
        pressBack() // IME only; the draft stays open.
        compose.onNodeWithTag("drawer-bottom").assertIsDisplayed()
        capture("detail-color-custom-dark")
        click("Apply")
        compose.onNodeWithContentDescription("Close drawer").performClick()
        tab("Code")
        compose.onNodeWithText("Color(0x8018181B)", substring = true).assertExists()
        color(); click("Reset")
        compose.onNode(hasText("Apply") and hasClickAction()).assertIsEnabled()
        click("Apply")
        compose.onNodeWithContentDescription("Close drawer").performClick()
        compose.onAllNodesWithText("Color(0x8018181B)", substring = true).assertCountEquals(0)
    }
    @Test fun nestedDrawerRestoresAndBackReturnsToInspector() {
        val restoration = StateRestorationTester(compose)
        restoration.setContent { FrogTheme { CompositionLocalProvider(LocalFrogMotionEnabled provides false) { Box(Modifier.width(360.dp)) { ButtonScreen("button", {}) } } } }
        color(); click("Surface")
        restoration.emulateSavedInstanceStateRestore()
        compose.onNodeWithContentDescription("Back within drawer").assertIsDisplayed().performClick()
        compose.onNodeWithText("Properties").assertExists()
        pressBack()
        compose.onNodeWithTag("drawer-window").assertDoesNotExist()
        compose.onNode(hasText("Customize") and hasClickAction()).assertExists()
    }
    @Test fun apiPropertyOpensGuidanceAndCopiesCode() {
        content(); tab("API"); click("variant")
        compose.onNodeWithText("API property").assertIsDisplayed()
        drawerNode(hasText("FrogButtonVariant")).assertExists()
        capture("detail-api-drawer")
        drawerNode(hasContentDescription("Copy code")).performScrollTo().performClick()
        compose.onNodeWithText("Copied").assertIsDisplayed()
        pressBack()
        compose.onNodeWithTag("drawer-window").assertDoesNotExist()
    }
    @Test fun loadingPreservesBoundsLabelAndDisablesActivation() {
        var loading by mutableStateOf(false)
        var hits = 0
        compose.setFrogContent { FrogButton(onClick = { hits++ }, loading = loading, modifier = Modifier.testTag("button")) { Text("Continue") } }
        val button = compose.onNodeWithTag("button")
        button.assertHeightIsAtLeast(48.dp).performClick()
        val bounds = button.fetchSemanticsNode().boundsInRoot
        compose.runOnIdle { loading = true }
        button.assertIsNotEnabled().assert(SemanticsMatcher.expectValue(SemanticsProperties.StateDescription, "Loading"))
        button.assertTextContains("Continue")
        assertEquals(bounds, button.fetchSemanticsNode().boundsInRoot)
        button.performClick()
        assertEquals(1, hits)
    }
    @Test fun customizationChangesWholeSurfaceAndWidthNotOnlyLabel() {
        var state by mutableStateOf(ButtonDemoState())
        compose.setFrogContent(darkTheme = true) {
            Box(Modifier.width(300.dp).background(Color.Black)) { ButtonLivePreview(state, {}, Modifier.testTag("surface-test")) }
        }
        val button = compose.onNodeWithTag("surface-test")
        val original = button.fetchSemanticsNode().boundsInRoot
        compose.runOnIdle {
            state = state.copy(fullWidth = true, size = FrogButtonSize.Large)
                .withColor(ButtonColorProperty.Container, FrogColorValue.Custom(0xFF2255CC))
                .withColor(ButtonColorProperty.Content, FrogColorValue.Custom(0xFFFFFFFF))
        }
        compose.waitForIdle()
        val resized = button.fetchSemanticsNode().boundsInRoot
        assertTrue(resized.width > original.width * 2)
        assertTrue(resized.height >= original.height)
        val pixels = button.captureToImage().toPixelMap()
        val background = pixels[pixels.width / 8, pixels.height / 2]
        assertEquals(0x22 / 255f, background.red, .02f)
        assertEquals(0x55 / 255f, background.green, .02f)
        assertEquals(0xCC / 255f, background.blue, .02f)
    }
    @Test fun inspectorUpdatesItsLiveButtonSurface() {
        content(); click("Customize")
        val preview = compose.onNodeWithTag("drawer-live-button")
        val original = preview.fetchSemanticsNode().boundsInRoot
        click("Large")
        click("Full width")
        assertTrue(preview.fetchSemanticsNode().boundsInRoot.width > original.width * 2)
        click("Container color"); click("Custom")
        compose.onNodeWithText("Hex color").performScrollTo().performTextReplacement("#2255CC")
        pressBack()
        click("Apply")
        val pixels = preview.captureToImage().toPixelMap()
        assertEquals(0xCC / 255f, pixels[pixels.width / 8, pixels.height / 2].blue, .02f)
        capture("detail-button-drawer-customized")
    }
}
