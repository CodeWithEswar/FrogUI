package io.github.codewitheswar.frogui.showcase

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.toPixelMap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.*
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.components.button.*
import io.github.codewitheswar.frogui.components.overlays.FrogOverlayHost
import io.github.codewitheswar.frogui.components.overlays.drawer.*
import io.github.codewitheswar.frogui.foundation.motion.FrogMotion
import io.github.codewitheswar.frogui.theme.FrogTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

/** Consumer-level behavior; uses only public component/theme contracts. */
class PublicApiContractTest {
    @get:Rule val compose = createComposeRule()

    @Test fun tinyButtonRetainsWholeTargetAndLoadingLabel() {
        var loading by mutableStateOf(false)
        var clicks = 0
        compose.setContent { FrogTheme(motion = FrogMotion(0, 0, 0)) {
            FrogButton({ clicks++ }, Modifier.testTag("button"), size = FrogButtonSize.Small,
                loading = loading, contentPadding = PaddingValues(0.dp)) { Text("I") }
        } }
        val button = compose.onNodeWithTag("button")
        button.assertWidthIsAtLeast(48.dp).assertHeightIsAtLeast(48.dp).performClick()
        compose.runOnIdle { assertEquals(1, clicks); loading = true }
        button.assertIsNotEnabled().assertTextContains("I")
        button.assert(SemanticsMatcher.expectValue(SemanticsProperties.StateDescription, "Loading"))
        compose.onAllNodes(SemanticsMatcher.keyIsDefined(SemanticsProperties.ProgressBarRangeInfo), useUnmergedTree = true).assertCountEquals(0)
    }

    @Test fun iconButtonUsesPressedOverrideAndLoadingHasOneActionLabel() {
        val interactions = MutableInteractionSource()
        var loading by mutableStateOf(false)
        compose.setContent { FrogTheme(motion = FrogMotion(0, 0, 0)) {
            FrogIconButton({}, "Refresh", Modifier.testTag("icon"), size = FrogButtonSize.Small,
                loading = loading, interactionSource = interactions,
                colors = FrogButtonDefaults.colors(containerColor = Color.Red).copy(pressedOverlayColor = Color.Green)) { Spacer(Modifier.size(16.dp)) }
        } }
        compose.runOnIdle { interactions.tryEmit(PressInteraction.Press(Offset.Zero)) }
        val pixels = compose.onNodeWithTag("icon").captureToImage().toPixelMap()
        assertEquals(Color.Green.toArgb(), pixels[pixels.width / 2, pixels.height / 2].toArgb())
        compose.runOnIdle { loading = true }
        compose.onNodeWithContentDescription("Refresh").assertIsNotEnabled()
        compose.onAllNodes(SemanticsMatcher.keyIsDefined(SemanticsProperties.ProgressBarRangeInfo), useUnmergedTree = true).assertCountEquals(0)
    }

    @Test fun drawerRestoresRequestedVisibilityAndDismissalRemainsCallerOwned() {
        val restoration = StateRestorationTester(compose)
        lateinit var state: FrogDrawerState
        var acceptDismissal by mutableStateOf(false)
        restoration.setContent { FrogTheme(motion = FrogMotion(0, 0, 0)) {
            state = rememberFrogDrawerState()
            FrogOverlayHost(Modifier.size(350.dp, 400.dp)) {
                FrogDrawer(
                    state,
                    { if (acceptDismissal) state.snapTo(FrogDrawerValue.Closed) },
                    title = "Saved pane",
                    shape = RoundedCornerShape(24.dp),
                    colors = FrogDrawerDefaults.colors(containerColor = Color.Red, contentColor = Color.White),
                ) { Text("Saved body") }
            }
        } }
        compose.runOnIdle { state.snapTo(FrogDrawerValue.Open) }
        restoration.emulateSavedInstanceStateRestore()
        val pane = compose.onNode(SemanticsMatcher.expectValue(SemanticsProperties.PaneTitle, "Saved pane"))
        pane.performSemanticsAction(SemanticsActions.Dismiss) { it() }
        compose.onNodeWithText("Saved body").assertIsDisplayed()
        compose.runOnIdle { acceptDismissal = true }
        pane.performSemanticsAction(SemanticsActions.Dismiss) { it() }
        compose.onNodeWithText("Saved body").assertDoesNotExist()
    }

    @Test fun canonicalIconButtonExposesRoleAndMandatoryLabel() {
        var clicked = false
        compose.setContent {
            FrogTheme {
                FrogIconButton(
                    icon = { Spacer(Modifier.size(16.dp)) },
                    contentDescription = "Search catalog",
                    onClick = { clicked = true },
                    modifier = Modifier.testTag("canonical-icon-btn")
                )
            }
        }
        val button = compose.onNodeWithTag("canonical-icon-btn")
        button.assert(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Button))
        button.assertContentDescriptionEquals("Search catalog")
        button.assertWidthIsAtLeast(48.dp).assertHeightIsAtLeast(48.dp)
        button.performClick()
        compose.runOnIdle { assertEquals(true, clicked) }
    }

    @Test fun canonicalFabExposesRoleActionDescriptionAndTouchTarget() {
        var clicked = false
        compose.setContent {
            FrogTheme {
                io.github.codewitheswar.frogui.components.fab.FrogFloatingActionButton(
                    icon = { Spacer(Modifier.size(20.dp)) },
                    contentDescription = "New draft",
                    onClick = { clicked = true },
                    presentation = io.github.codewitheswar.frogui.components.fab.FrogFabPresentation.Small,
                    modifier = Modifier.testTag("canonical-fab-btn")
                )
            }
        }
        val fab = compose.onNodeWithTag("canonical-fab-btn")
        fab.assert(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Button))
        fab.assertContentDescriptionEquals("New draft")
        fab.assertWidthIsAtLeast(48.dp).assertHeightIsAtLeast(48.dp)
        fab.performClick()
        compose.runOnIdle { assertEquals(true, clicked) }
    }

    @Test fun canonicalTextFieldExposesErrorSemanticsAndAccessibleLabel() {
        var text by mutableStateOf("")
        compose.setContent {
            FrogTheme {
                io.github.codewitheswar.frogui.components.textfield.FrogTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = "Full Name",
                    errorText = "Name is required",
                    modifier = Modifier.testTag("canonical-text-field")
                )
            }
        }
        val field = compose.onNodeWithTag("canonical-text-field")
        field.assertTextContains("Full Name")
        field.assert(SemanticsMatcher.expectValue(SemanticsProperties.Error, "Name is required"))
        field.assertHeightIsAtLeast(56.dp)
        field.performTextInput("Eswar")
        compose.runOnIdle { assertEquals("Eswar", text) }
    }
}
