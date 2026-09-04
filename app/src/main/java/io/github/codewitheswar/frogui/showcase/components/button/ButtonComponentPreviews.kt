package io.github.codewitheswar.frogui.showcase.components.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.components.button.FrogButton
import io.github.codewitheswar.frogui.components.button.FrogButtonDefaults
import io.github.codewitheswar.frogui.components.button.FrogButtonSize
import io.github.codewitheswar.frogui.components.button.FrogButtonVariant
import io.github.codewitheswar.frogui.showcase.FrogComponentPreview
import io.github.codewitheswar.frogui.theme.FrogTheme

@Preview(name = "FrogButton · Default", widthDp = 390)
@Composable
private fun FrogButtonDefaultPreview() = FrogComponentPreview {
    FrogButton(onClick = {}) { Text("Continue") }
}

@Preview(name = "FrogButton · Variants", widthDp = 390, heightDp = 420)
@Composable
private fun FrogButtonVariantsPreview() = FrogComponentPreview {
    Column(verticalArrangement = Arrangement.spacedBy(FrogTheme.spacing.md)) {
        FrogButtonVariant.entries.forEach { variant ->
            FrogButton(onClick = {}, variant = variant, modifier = Modifier.fillMaxWidth()) { Text(variant.name) }
        }
    }
}

@Preview(name = "FrogButton · States and sizes", widthDp = 520, heightDp = 260)
@Composable
private fun FrogButtonStatesPreview() = FrogComponentPreview {
    Column(verticalArrangement = Arrangement.spacedBy(FrogTheme.spacing.lg)) {
        FrogButtonSize.entries.forEach { size ->
            Row(horizontalArrangement = Arrangement.spacedBy(FrogTheme.spacing.md)) {
                FrogButton(onClick = {}, size = size) { Text(size.name) }
                FrogButton(onClick = {}, size = size, enabled = false) { Text("Disabled") }
                FrogButton(onClick = {}, size = size, loading = true) { Text("Loading") }
            }
        }
    }
}

@Preview(name = "FrogButton · Dark custom", widthDp = 390)
@Composable
private fun FrogButtonDarkCustomPreview() = FrogComponentPreview(darkTheme = true) {
    FrogButton(
        onClick = {},
        shape = RoundedCornerShape(18.dp),
        colors = FrogButtonDefaults.colors(containerColor = Color(0xFF1D4ED8), contentColor = Color.White),
    ) { Text("Custom action") }
}
