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
import io.github.codewitheswar.frogui.foundation.motion.FrogMotion
import io.github.codewitheswar.frogui.showcase.screens.ComponentDetailScreen
import io.github.codewitheswar.frogui.showcase.style.LocalFrogMotionEnabled
import io.github.codewitheswar.frogui.theme.FrogTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.io.File

@RunWith(Parameterized::class)
class SharedDetailWindowTest(private val id: String, private val width: Int, private val height: Int, private val dark: Boolean, private val font: Float) {
    @get:Rule val compose = createComposeRule()
    companion object {
        @JvmStatic @Parameterized.Parameters(name = "{0}-{1}x{2}-dark{3}-font{4}")
        fun windows() = listOf(
            arrayOf<Any>("drawer", 360, 800, true, 1f), arrayOf<Any>("drawer", 390, 844, false, 1f), arrayOf<Any>("drawer", 412, 915, true, 1f),
            arrayOf<Any>("drawer", 600, 900, false, 1f), arrayOf<Any>("drawer", 840, 1000, true, 1f), arrayOf<Any>("drawer", 1000, 800, false, 1f),
            arrayOf<Any>("drawer", 900, 500, true, 1f), arrayOf<Any>("drawer", 390, 844, true, 1.3f), arrayOf<Any>("drawer", 390, 844, false, 1.5f),
            arrayOf<Any>("button", 390, 844, false, 1.3f), arrayOf<Any>("button", 1000, 800, false, 1f),
        )
    }
    private fun capture(name: String) {
        val directory = File(InstrumentationRegistry.getInstrumentation().targetContext.getExternalFilesDir(null), "showcase-qa").apply { mkdirs() }
        compose.onNodeWithTag("shared-viewport").captureToImage().asAndroidBitmap().let { bitmap -> File(directory, "shared-$id-${width}x$height-dark$dark-font$font-$name.png").outputStream().use { bitmap.compress(Bitmap.CompressFormat.PNG, 100, it) } }
    }
    private fun tab(label: String) = compose.onNode(hasText(label) and SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Tab)).performSemanticsAction(SemanticsActions.OnClick) { it() }
    @Test fun sharedLayoutSupportsWindowThemeAndFontVariations() {
        compose.setContent {
            DeviceConfigurationOverride(DeviceConfigurationOverride.ForcedSize(DpSize(width.dp, height.dp)) then DeviceConfigurationOverride.FontScale(font)) {
                FrogTheme(darkTheme = dark, motion = FrogMotion(0, 0, 0)) { CompositionLocalProvider(LocalFrogMotionEnabled provides false) {
                    Box(Modifier.fillMaxSize().consumeWindowInsets(WindowInsets.safeDrawing).background(FrogTheme.colors.background).testTag("shared-viewport")) { ComponentDetailScreen(id, {}) }
                } }
            }
        }
        if (width >= 600 && font <= 1.3f) compose.onNodeWithTag("persistent-inspector").assertIsDisplayed()
        capture("preview")
        compose.onNodeWithContentDescription(if (dark) "Use light preview" else "Use dark preview").performScrollTo().performClick()
        if (id == "drawer") {
            compose.onNodeWithTag("open-drawer-preview").performScrollTo().performClick()
            compose.onNodeWithText("Contextual options and guidance.").assertExists()
            capture("overlay-opposite-theme")
            compose.onNodeWithContentDescription("Close drawer").performScrollTo().performClick()
        } else capture("opposite-theme")
        tab("Code")
        compose.onNodeWithContentDescription("Copy code").performScrollTo().assertIsDisplayed()
        capture("code")
        tab("API")
        compose.onNodeWithTag(if (width - 32 - (if (width >= 600 && font <= 1.3f) 281 else 0) >= 680) "api-table" else "api-stacked").assertExists()
        capture("api")
        tab("Accessibility")
        compose.onNodeWithText(if (id == "drawer") "Pane and dismissal" else "Semantics and state").performScrollTo().assertIsDisplayed()
        capture("accessibility")
    }
}
