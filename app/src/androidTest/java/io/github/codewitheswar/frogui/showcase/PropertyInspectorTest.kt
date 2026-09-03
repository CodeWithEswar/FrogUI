package io.github.codewitheswar.frogui.showcase

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import androidx.test.platform.app.InstrumentationRegistry
import io.github.codewitheswar.frogui.showcase.components.button.ButtonDemoState
import io.github.codewitheswar.frogui.showcase.components.button.PropertyInspector
import io.github.codewitheswar.frogui.testing.setFrogContent
import io.github.codewitheswar.frogui.theme.FrogTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.io.File

class PropertyInspectorTest {
    @get:Rule val compose = createComposeRule()
    private fun choice(label: String) = compose.onNode(hasText(label) and SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.RadioButton))
    private fun capture(name: String) {
        val directory = File(InstrumentationRegistry.getInstrumentation().targetContext.getExternalFilesDir(null), "showcase-qa").apply { mkdirs() }
        compose.onNodeWithTag("properties-review").captureToImage().asAndroidBitmap().let { bitmap ->
            File(directory, "$name.png").outputStream().use { bitmap.compress(Bitmap.CompressFormat.PNG, 100, it) }
        }
    }
    @Test fun selectionKeepsOptionBoundsStableAndExposesCurrentValues() {
        compose.setFrogContent(darkTheme = true) {
            var state by remember { mutableStateOf(ButtonDemoState()) }
            Box(Modifier.width(360.dp).fillMaxHeight().background(FrogTheme.colors.background).testTag("properties-review")) {
                Column(Modifier.verticalScroll(rememberScrollState()).padding(16.dp)) { PropertyInspector(state, { state = it }) }
            }
        }
        choice("Primary").assertIsSelected()
        choice("Medium").assertIsSelected()
        val smallBounds = choice("Small").fetchSemanticsNode().boundsInRoot
        capture("properties-primary-medium")
        choice("Ghost").performScrollTo().assertIsDisplayed()
        val ghostBounds = choice("Ghost").fetchSemanticsNode().boundsInRoot
        choice("Ghost").performClick().assertIsSelected()
        choice("Primary").assertIsNotSelected()
        choice("Small").performClick().assertIsSelected()
        choice("Medium").assertIsNotSelected()
        assertEquals(ghostBounds, choice("Ghost").fetchSemanticsNode().boundsInRoot)
        assertEquals(smallBounds, choice("Small").fetchSemanticsNode().boundsInRoot)
        capture("properties-ghost-small")
    }
}
