package io.github.codewitheswar.frogui.showcase

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.test.platform.app.InstrumentationRegistry
import io.github.codewitheswar.frogui.navigation.*
import io.github.codewitheswar.frogui.registry.FrogComponentRegistry
import io.github.codewitheswar.frogui.showcase.markdown.FrogApiTable
import io.github.codewitheswar.frogui.showcase.style.LocalFrogMotionEnabled
import io.github.codewitheswar.frogui.theme.FrogTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.io.File

/** Native render captures are review artifacts, not pixel golden comparisons. */
@RunWith(Parameterized::class)
class ShowcaseWindowTest(private val width: Int, private val height: Int, private val fontScale: Float, private val dark: Boolean, private val rtl: Boolean) {
    @get:Rule val compose = createComposeRule()
    companion object {
        @JvmStatic @Parameterized.Parameters(name = "{0}x{1}-font{2}-dark{3}-rtl{4}")
        fun windows() = listOf(
            arrayOf<Any>(360, 800, 1f, true, false), arrayOf<Any>(390, 844, 1f, false, false), arrayOf<Any>(412, 915, 1f, true, false),
            arrayOf<Any>(600, 900, 1f, false, false), arrayOf<Any>(840, 1050, 1f, true, false), arrayOf<Any>(1000, 800, 1f, true, false),
            arrayOf<Any>(900, 500, 1f, false, false), arrayOf<Any>(360, 800, 2f, true, false), arrayOf<Any>(840, 1050, 1.6f, false, false),
            arrayOf<Any>(390, 844, 1f, true, true),
        )
    }
    private fun tab(label: String) = compose.onNode(hasText(label) and SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Tab))
    private fun capture(name: String) {
        val directory = File(InstrumentationRegistry.getInstrumentation().targetContext.getExternalFilesDir(null), "showcase-qa").apply { mkdirs() }
        val file = File(directory, "${width}x$height-font$fontScale-dark$dark-rtl$rtl-$name.png")
        compose.waitUntil(5000) { compose.onAllNodesWithTag("viewport", useUnmergedTree = true).fetchSemanticsNodes().isNotEmpty() }
        compose.onNodeWithTag("viewport", useUnmergedTree = true).captureToImage().asAndroidBitmap().let { bitmap -> file.outputStream().use { bitmap.compress(Bitmap.CompressFormat.PNG, 100, it) } }
    }
    @Test fun adaptiveShellAndDetailRenderAcrossWindows() {
        compose.setContent {
            DeviceConfigurationOverride(DeviceConfigurationOverride.ForcedSize(DpSize(width.dp, height.dp)) then DeviceConfigurationOverride.FontScale(fontScale)) {
                CompositionLocalProvider(LocalFrogMotionEnabled provides false, LocalLayoutDirection provides if (rtl) LayoutDirection.Rtl else LayoutDirection.Ltr) {
                    FrogTheme(darkTheme = dark) {
                        // Physical device insets do not scale with ForcedSize. Verify content
                        // layouts here; the real activity covers platform insets separately.
                        Box(Modifier.fillMaxSize().consumeWindowInsets(WindowInsets.safeDrawing).background(FrogTheme.colors.background).testTag("viewport")) {
                            FrogUiShell(if (dark) ShowcaseAppearance.Dark else ShowcaseAppearance.Light, {}, false, {})
                        }
                    }
                }
            }
        }
        val nav = when { width < 600 -> "bottom-navigation"; width < 840 -> "rail-navigation"; else -> "sidebar-navigation" }
        compose.onNodeWithTag(nav).assertIsDisplayed()
        tab("Home").assertIsSelected()
        capture("home")
        tab("Components").performClick()
        compose.onNodeWithText("Button", substring = false).performClick()
        compose.onNodeWithContentDescription("Back").assertIsDisplayed()
        if (width < 600) compose.onNodeWithTag("bottom-navigation").assertDoesNotExist()
        capture("preview")
        tab("Code").performSemanticsAction(SemanticsActions.OnClick) { it() }
        compose.onAllNodesWithContentDescription("Copy code").onFirst().performScrollTo().assertIsDisplayed()
        capture("code")
        tab("API").performSemanticsAction(SemanticsActions.OnClick) { it() }
        val detailWidth = width - (if (width >= 840) 208 else if (width >= 600) 108 else 0)
        val inspectorWidth = if (detailWidth >= 600 && fontScale <= 1.3f) 281 else 0
        val apiTag = if (detailWidth - 32 - inspectorWidth >= 680) "api-table" else "api-stacked"
        compose.onNodeWithTag(apiTag).assertExists()
        capture("api")
        tab("Accessibility").performSemanticsAction(SemanticsActions.OnClick) { it() }
        compose.onNodeWithText("Semantics and state").performScrollTo().assertIsDisplayed()
        capture("accessibility")
    }
}
