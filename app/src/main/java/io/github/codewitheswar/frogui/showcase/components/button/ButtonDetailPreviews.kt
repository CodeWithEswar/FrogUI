package io.github.codewitheswar.frogui.showcase.components.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.registry.FrogComponentRegistry
import io.github.codewitheswar.frogui.showcase.colorpicker.*
import io.github.codewitheswar.frogui.components.overlays.drawer.FrogDrawer
import io.github.codewitheswar.frogui.showcase.style.LocalFrogMotionEnabled
import io.github.codewitheswar.frogui.theme.FrogTheme

@Preview(name = "Button detail dark", widthDp = 390, heightDp = 844)
@Composable private fun DetailDark() = DetailPreview(true)
@Preview(name = "Button detail light", widthDp = 390, heightDp = 844)
@Composable private fun DetailLight() = DetailPreview(false)
@Preview(name = "Button detail tablet", widthDp = 1000, heightDp = 800)
@Composable private fun DetailTablet() = DetailPreview(true)
@Composable private fun DetailPreview(dark: Boolean) {
    FrogTheme(darkTheme = dark) { CompositionLocalProvider(LocalFrogMotionEnabled provides false) { Box(Modifier.fillMaxSize().background(FrogTheme.colors.background)) { ButtonScreen("button", {}) } } }
}
@Preview(name = "FrogDrawer phone", widthDp = 390, heightDp = 844)
@Composable private fun DrawerPhone() = DrawerPreview(false)
@Preview(name = "FrogDrawer tablet", widthDp = 1000, heightDp = 800)
@Composable private fun DrawerTablet() = DrawerPreview(true)
@Composable private fun DrawerPreview(side: Boolean) {
    FrogTheme(darkTheme = true) { FrogDrawer(true, {}, "Customize", subtitle = "FrogButton", side = side) { PropertyInspector(ButtonDemoState(), {}, onColor = {}) } }
}
@Preview(name = "Color picker tokens", widthDp = 390, heightDp = 844)
@Composable private fun TokenPicker() = PickerPreview(FrogColorValue.Token(FrogColorToken.Primary))
@Preview(name = "Color picker custom", widthDp = 390, heightDp = 844)
@Composable private fun CustomPicker() = PickerPreview(FrogColorValue.Custom(0xFF6750A4))
@Composable private fun PickerPreview(initial: FrogColorValue) {
    FrogTheme(darkTheme = true) {
        var value by remember { mutableStateOf(initial) }
        Column(Modifier.background(FrogTheme.colors.background).verticalScroll(rememberScrollState()).padding(18.dp)) { FrogColorPicker(value, { value = it }) }
    }
}
@Preview(name = "Button API compact", widthDp = 360, heightDp = 800)
@Composable private fun ApiCompact() = ApiPreview()
@Preview(name = "Button API expanded", widthDp = 840, heightDp = 800)
@Composable private fun ApiExpanded() = ApiPreview()
@Composable private fun ApiPreview() {
    FrogTheme(darkTheme = false) { Column(Modifier.verticalScroll(rememberScrollState()).padding(16.dp)) { ButtonApiReference(FrogComponentRegistry.Button.properties) {} } }
}
