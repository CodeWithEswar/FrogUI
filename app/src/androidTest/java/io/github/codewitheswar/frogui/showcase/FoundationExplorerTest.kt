package io.github.codewitheswar.frogui.showcase

import android.graphics.Bitmap
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.test.platform.app.InstrumentationRegistry
import io.github.codewitheswar.frogui.showcase.screens.FoundationScreen
import io.github.codewitheswar.frogui.theme.FrogTheme
import io.github.codewitheswar.frogui.theme.ProvideFrogThemeEnvironment
import org.junit.Rule
import org.junit.Test
import java.io.File

class FoundationExplorerTest {
    @get:Rule val compose = createComposeRule()
    private fun tab(label: String) = compose.onNode(hasText(label) and SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Tab))
        .performScrollTo().performClick()
    private fun capture(name: String) {
        val directory = File(InstrumentationRegistry.getInstrumentation().targetContext.getExternalFilesDir(null), "showcase-qa").apply { mkdirs() }
        compose.onNodeWithTag("foundation-viewport").captureToImage().asAndroidBitmap().let { bitmap ->
            File(directory, "foundation-$name.png").outputStream().use { bitmap.compress(Bitmap.CompressFormat.PNG, 100, it) }
        }
    }

    @Test fun allFoundationPagesAreReachableWithLargeTextAndThemeChanges() {
        var dark by mutableStateOf(false)
        compose.setContent {
            DeviceConfigurationOverride(DeviceConfigurationOverride.ForcedSize(DpSize(390.dp, 844.dp)) then DeviceConfigurationOverride.FontScale(1.5f)) {
                FrogTheme(darkTheme = dark) { ProvideFrogThemeEnvironment(reduceMotion = true) {
                    Box(Modifier.fillMaxSize().testTag("foundation-viewport")) { FoundationScreen() }
                } }
            }
        }
        compose.onNodeWithText("background", substring = false).performScrollTo().assertIsDisplayed()
        capture("colors-light-large-text")
        compose.runOnIdle { dark = true }
        capture("colors-dark-large-text")
        tab("Typography")
        compose.onNodeWithText("code ·", substring = true).performScrollTo().assertIsDisplayed()
        tab("Spacing")
        compose.onNodeWithText("x7l ·", substring = true).performScrollTo().assertIsDisplayed()
        tab("Shapes")
        compose.onNodeWithText("full", substring = false).performScrollTo().assertIsDisplayed()
        tab("Elevation")
        compose.onNodeWithText("Dark", substring = false).performScrollTo().assertIsDisplayed()
        tab("Motion")
        compose.onNodeWithText("Run transition").performScrollTo().performClick()
        tab("Sizing")
        compose.onNodeWithText("minimumTouchTarget ·", substring = true).performScrollTo().assertIsDisplayed()
        tab("Adaptive")
        compose.onNodeWithText("Actual host:", substring = true).performScrollTo().assertIsDisplayed()
        capture("adaptive-large-text")
    }
}
