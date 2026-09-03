package io.github.codewitheswar.frogui.showcase

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.*
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.test.platform.app.InstrumentationRegistry
import io.github.codewitheswar.frogui.showcase.components.button.ButtonScreen
import io.github.codewitheswar.frogui.showcase.style.LocalFrogMotionEnabled
import io.github.codewitheswar.frogui.theme.FrogTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.io.File

@RunWith(Parameterized::class)
class ButtonDetailWindowTest(private val width: Int, private val height: Int, private val dark: Boolean, private val font: Float) {
    @get:Rule val compose = createComposeRule()
    companion object {
        @JvmStatic @Parameterized.Parameters(name = "detail-{0}x{1}-dark{2}-font{3}")
        fun windows() = listOf(arrayOf<Any>(360, 800, true, 1f), arrayOf<Any>(390, 844, false, 1f),
            arrayOf<Any>(840, 1000, true, 1f), arrayOf<Any>(1000, 800, false, 1f),
            arrayOf<Any>(360, 800, true, 2f), arrayOf<Any>(900, 500, false, 1f))
    }
    private fun capture(tag: String, name: String) {
        val directory = File(InstrumentationRegistry.getInstrumentation().targetContext.getExternalFilesDir(null), "showcase-qa").apply { mkdirs() }
        compose.onNodeWithTag(tag).captureToImage().asAndroidBitmap().let { bitmap -> File(directory, "detail-${width}x$height-dark$dark-font$font-$name.png").outputStream().use { bitmap.compress(Bitmap.CompressFormat.PNG, 100, it) } }
    }
    private fun click(label: String) {
        var matcher = hasText(label) and hasClickAction()
        if (compose.onAllNodesWithTag("drawer-window").fetchSemanticsNodes().isNotEmpty()) matcher = matcher and hasAnyAncestor(hasTestTag("drawer-window"))
        val node = compose.onNode(matcher)
        if (generateSequence(node.fetchSemanticsNode().parent) { it.parent }.any { it.config.contains(SemanticsActions.ScrollBy) }) node.performScrollTo()
        node.performClick()
    }
    @Test fun phoneAndTabletDrawersRemainReadable() {
        compose.setContent {
            DeviceConfigurationOverride(DeviceConfigurationOverride.ForcedSize(DpSize(width.dp, height.dp)) then DeviceConfigurationOverride.FontScale(font)) {
                CompositionLocalProvider(LocalFrogMotionEnabled provides false) {
                    FrogTheme(darkTheme = dark) {
                        Box(Modifier.fillMaxSize().consumeWindowInsets(WindowInsets.safeDrawing).background(FrogTheme.colors.background).testTag("detail-viewport")) { ButtonScreen("button", {}) }
                    }
                }
            }
        }
        if (width >= 620 && font <= 1.3f) compose.onNodeWithTag("persistent-inspector").assertIsDisplayed()
        else compose.onNode(hasText("Customize") and hasClickAction()).assertExists()
        capture("detail-viewport", "preview")
        if (width < 620 || font > 1.3f) { click("Customize"); capture("drawer-window", "inspector") }
        click("Container color")
        compose.onNodeWithTag(if (width >= 620) "drawer-side" else "drawer-bottom").assertIsDisplayed()
        capture("drawer-window", "tokens")
        click("Custom")
        compose.onNodeWithContentDescription("Hue").performScrollTo().assertExists()
        capture("drawer-window", "custom")
        compose.onNodeWithContentDescription("Close drawer").performClick()
        compose.onNodeWithTag("drawer-window").assertDoesNotExist()
    }
}
