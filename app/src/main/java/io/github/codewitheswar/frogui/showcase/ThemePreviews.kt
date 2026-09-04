package io.github.codewitheswar.frogui.showcase

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.codewitheswar.frogui.components.button.*
import io.github.codewitheswar.frogui.theme.*

/** Compiled consumer example shared by the docs and native Foundation explorer. */
@Composable
internal fun ThemeOverrideExample() {
    val typography = FrogTheme.typography.copy(body = FrogTheme.typography.body.copy(fontSize = 16.sp, lineHeight = 24.sp))
    val shapes = FrogTheme.shapes.copy(md = RoundedCornerShape(12.dp))
    FrogTheme(colors = FrogTheme.colors, typography = typography, shapes = shapes) {
        ProvideFrogThemeEnvironment(sizing = FrogTheme.sizing.copy(minimumTouchTarget = 56.dp), reduceMotion = true) {
            FrogButton(onClick = {}, loading = true) { Text("Saving changes") }
        }
    }
}

@Composable
private fun ThemeSample(dark: Boolean = false, custom: Boolean = false, reduced: Boolean = false) {
    val colors = if (dark) FrogThemeDefaults.darkColors() else FrogThemeDefaults.lightColors()
    FrogTheme(colors = if (custom) colors.copy(primary = Color(0xFF1D4ED8), primaryForeground = Color.White) else colors) {
        ProvideFrogThemeEnvironment(reduceMotion = reduced) {
            Column(Modifier.fillMaxWidth().background(FrogTheme.colors.background).padding(FrogTheme.spacing.xl),
                verticalArrangement = Arrangement.spacedBy(FrogTheme.spacing.lg)) {
                Text("Your changes are ready", style = FrogTheme.typography.heading)
                FrogButton({}) { Text("Save changes") }
                FrogButton({}, variant = FrogButtonVariant.Destructive) { Text("Delete draft") }
                FrogButton({}, loading = true) { Text("Saving changes") }
                FrogTheme(darkTheme = !dark) {
                    Column(Modifier.fillMaxWidth().background(FrogTheme.colors.background).padding(FrogTheme.spacing.lg)) {
                        Text("Isolated preview", style = FrogTheme.typography.body)
                        ThemeOverrideExample()
                    }
                }
            }
        }
    }
}

@Preview(name = "Theme light", widthDp = 390) @Composable private fun ThemeLight() = ThemeSample()
@Preview(name = "Theme dark", widthDp = 390) @Composable private fun ThemeDark() = ThemeSample(dark = true)
@Preview(name = "Theme custom colors", widthDp = 390) @Composable private fun ThemeCustom() = ThemeSample(custom = true)
@Preview(name = "Theme large text", widthDp = 390, fontScale = 1.5f) @Composable private fun ThemeLargeText() = ThemeSample()
@Preview(name = "Theme reduced motion", widthDp = 390) @Composable private fun ThemeReducedMotion() = ThemeSample(reduced = true)
