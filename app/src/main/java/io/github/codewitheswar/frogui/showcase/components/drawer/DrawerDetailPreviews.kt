package io.github.codewitheswar.frogui.showcase.components.drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.components.overlays.drawer.FrogDrawer
import io.github.codewitheswar.frogui.components.overlays.drawer.FrogDrawerDefaults
import io.github.codewitheswar.frogui.components.overlays.drawer.FrogDrawerPresentation
import io.github.codewitheswar.frogui.showcase.FrogComponentPreview
import io.github.codewitheswar.frogui.showcase.style.LocalFrogMotionEnabled
import io.github.codewitheswar.frogui.theme.FrogTheme
import io.github.codewitheswar.frogui.showcase.screens.ComponentDetailScreen

@Preview(name = "Drawer Screen Light", widthDp = 390, heightDp = 844)
@Composable
private fun DrawerScreenLightPreview() {
    FrogTheme(darkTheme = false) {
        CompositionLocalProvider(LocalFrogMotionEnabled provides false) {
            Box(Modifier.fillMaxSize().background(FrogTheme.colors.background)) {
                ComponentDetailScreen("drawer", {})
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
                ComponentDetailScreen("drawer", {})
            }
        }
    }
}

@Preview(name = "FrogDrawer · Compact bottom", widthDp = 390, heightDp = 844)
@Composable
private fun DrawerBottomOpenPreview() {
    FrogComponentPreview(darkTheme = true) {
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

@Preview(name = "FrogDrawer · Expanded side", widthDp = 840, heightDp = 600)
@Composable
private fun DrawerSideOpenPreview() {
    FrogComponentPreview(darkTheme = true) {
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

@Preview(name = "FrogDrawer · Custom theme", widthDp = 390, heightDp = 700)
@Composable
private fun DrawerCustomPreview() = FrogComponentPreview {
    FrogDrawer(
        visible = true,
        onDismissRequest = {},
        presentation = FrogDrawerPresentation.Bottom,
        title = "Custom drawer",
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
        colors = FrogDrawerDefaults.colors(
            containerColor = Color(0xFFEFF6FF),
            contentColor = Color(0xFF172554),
            borderColor = Color(0xFF60A5FA),
        ),
    ) { androidx.compose.material3.Text("Consumer colors and shape") }
}
