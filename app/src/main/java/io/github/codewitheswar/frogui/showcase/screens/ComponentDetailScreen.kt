package io.github.codewitheswar.frogui.showcase.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.theme.FrogTheme
import io.github.codewitheswar.frogui.components.button.FrogButton
import io.github.codewitheswar.frogui.showcase.components.button.ButtonScreen
import io.github.codewitheswar.frogui.showcase.registry.ComponentDemo
import io.github.codewitheswar.frogui.showcase.registry.ShowcaseRegistry

@Composable
internal fun ComponentDetailScreen(componentId: String, onBack: () -> Unit, modifier: Modifier = Modifier) {
    val entry = ShowcaseRegistry.findById(componentId)
    if (entry == null) {
        Column(modifier.padding(20.dp)) {
            Text("Component not found", color = FrogTheme.colors.foreground, style = FrogTheme.typography.heading)
            FrogButton(onClick = onBack) { Text("Back to components") }
        }
        return
    }
    when (entry.demo) {
        ComponentDemo.Button -> ButtonScreen(entry.metadata.id, onBack, modifier)
    }
}
