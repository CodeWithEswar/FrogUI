package io.github.codewitheswar.frogui.showcase.components.drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.github.codewitheswar.frogui.components.overlays.drawer.FrogDrawer
import io.github.codewitheswar.frogui.components.overlays.drawer.FrogDrawerPresentation
import io.github.codewitheswar.frogui.showcase.style.LocalFrogMotionEnabled
import io.github.codewitheswar.frogui.theme.FrogTheme

@Preview(name = "Drawer Screen Light", widthDp = 390, heightDp = 844)
@Composable
private fun DrawerScreenLightPreview() {
    FrogTheme(darkTheme = false) {
        CompositionLocalProvider(LocalFrogMotionEnabled provides false) {
            Box(Modifier.fillMaxSize().background(FrogTheme.colors.background)) {
                DrawerScreen("drawer", {})
            }
        }
    }
}

@Preview(name = "Drawer Screen Dark", widthDp = 390, heightDp = 844)
@Composable
private fun DrawerScreenDarkPreview() {
    FrogTheme(darkTheme = true) {
        CompositionLocalProvider(LocalFrogMotionEnabled provides false) {
            Box(Modifier.fillMaxSize().background(FrogTheme.colors.background)) {
                DrawerScreen("drawer", {})
            }
        }
    }
}

@Preview(name = "Drawer Bottom Open Preview", widthDp = 390, heightDp = 844)
@Composable
private fun DrawerBottomOpenPreview() {
    FrogTheme(darkTheme = true) {
        FrogDrawer(
            visible = true,
            onDismissRequest = {},
            presentation = FrogDrawerPresentation.Bottom,
            title = "Preview Bottom Drawer",
            subtitle = "Contextual action preview"
        ) {
            androidx.compose.material3.Text("Drawer content preview")
        }
    }
}

@Preview(name = "Drawer Side Open Preview", widthDp = 840, heightDp = 600)
@Composable
private fun DrawerSideOpenPreview() {
    FrogTheme(darkTheme = true) {
        FrogDrawer(
            visible = true,
            onDismissRequest = {},
            presentation = FrogDrawerPresentation.Side,
            title = "Preview Side Inspector",
            subtitle = "Contextual inspector preview"
        ) {
            androidx.compose.material3.Text("Inspector content preview")
        }
    }
}
