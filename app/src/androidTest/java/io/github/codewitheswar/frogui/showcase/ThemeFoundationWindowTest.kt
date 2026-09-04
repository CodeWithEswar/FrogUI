package io.github.codewitheswar.frogui.showcase

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.toPixelMap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.test.platform.app.InstrumentationRegistry
import io.github.codewitheswar.frogui.components.button.*
import io.github.codewitheswar.frogui.components.overlays.FrogOverlayHost
import io.github.codewitheswar.frogui.components.overlays.drawer.FrogDrawer
import io.github.codewitheswar.frogui.components.overlays.drawer.FrogDrawerPresentation
import io.github.codewitheswar.frogui.foundation.sizing.FrogSizing
import io.github.codewitheswar.frogui.theme.*
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.io.File

@RunWith(Parameterized::class)
class ThemeFoundationWindowTest(private val width: Int, private val palette: String, private val font: Float) {
    @get:Rule val compose = createComposeRule()
    companion object {
        @JvmStatic @Parameterized.Parameters(name = "{0}dp-{1}-font{2}")
        fun cases() = listOf(arrayOf<Any>(360, "light", 1f), arrayOf<Any>(390, "dark", 1.3f),
            arrayOf<Any>(412, "custom", 1.5f), arrayOf<Any>(600, "light", 1f),
            arrayOf<Any>(840, "dark", 1f), arrayOf<Any>(1000, "custom", 1f))
    }

    @Test fun themedComponentsUseRealConstraintsColorsTargetsAndReducedLoading() {
        val colors = when (palette) {
            "dark" -> FrogThemeDefaults.darkColors()
            "custom" -> FrogThemeDefaults.lightColors().copy(primary = Color(0xFF1D4ED8))
            else -> FrogThemeDefaults.lightColors()
        }
        compose.setContent {
            DeviceConfigurationOverride(DeviceConfigurationOverride.ForcedSize(DpSize(width.dp, 800.dp)) then DeviceConfigurationOverride.FontScale(font)) {
                FrogTheme(colors = colors) {
                    ProvideFrogThemeEnvironment(sizing = FrogSizing(minimumTouchTarget = 56.dp), reduceMotion = true) {
                        Column(Modifier.fillMaxSize().background(FrogTheme.colors.background).testTag("theme-viewport")) {
                            FrogIconButton({}, "Color sample", Modifier.testTag("color-sample"), variant = FrogButtonVariant.Primary,
                                size = FrogButtonSize.Small) { Spacer(Modifier.size(16.dp)) }
                            FrogButton({}, loading = true, modifier = Modifier.testTag("reduced-loading")) { Text("Saving changes") }
                            FrogOverlayHost(Modifier.fillMaxWidth().height(400.dp)) {
                                FrogDrawer(visible = true, onDismissRequest = {}, presentation = FrogDrawerPresentation.Auto, title = "Theme and adaptive") { Text("Readable drawer content") }
                            }
                        }
                    }
                }
            }
        }
        val control = compose.onNodeWithTag("color-sample")
        control.assertWidthIsAtLeast(56.dp).assertHeightIsAtLeast(56.dp)
        val pixels = control.captureToImage().toPixelMap()
        assertEquals(colors.primary.toArgb(), pixels[pixels.width / 2, pixels.height / 2].toArgb())
        compose.onNodeWithTag(if (width < 600) "drawer-bottom" else "drawer-side").assertIsDisplayed()
        compose.onNodeWithText("Readable drawer content").assertIsDisplayed()
        compose.onNodeWithTag("reduced-loading").assertIsNotEnabled().assertTextContains("Saving changes")
        val before = compose.onNodeWithTag("reduced-loading").captureToImage().asAndroidBitmap()
        compose.mainClock.advanceTimeBy(1000)
        val after = compose.onNodeWithTag("reduced-loading").captureToImage().asAndroidBitmap()
        org.junit.Assert.assertTrue("Reduced loading should have no decorative rotation", before.sameAs(after))
        val directory = File(InstrumentationRegistry.getInstrumentation().targetContext.getExternalFilesDir(null), "showcase-qa").apply { mkdirs() }
        compose.onNodeWithTag("theme-viewport").captureToImage().asAndroidBitmap().let { bitmap ->
            File(directory, "theme-$width-$palette-font$font.png").outputStream().use { bitmap.compress(Bitmap.CompressFormat.PNG, 100, it) }
        }
    }
}
