package io.github.codewitheswar.frogui.showcase.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.theme.FrogTheme
import io.github.codewitheswar.frogui.components.button.FrogButton
import io.github.codewitheswar.frogui.showcase.detail.*
import io.github.codewitheswar.frogui.showcase.registry.ShowcaseRegistry

@Composable
internal fun ComponentDetailScreen(componentId: String, onBack: () -> Unit, modifier: Modifier = Modifier) {
    val entry = ShowcaseRegistry.findById(componentId)
    if (entry == null) {
        Column(modifier.padding(20.dp)) {
            Text("Component unavailable", color = FrogTheme.colors.foreground, style = FrogTheme.typography.heading)
            Text("The Showcase metadata or definition for this component could not be loaded.", color = FrogTheme.colors.mutedForeground, style = FrogTheme.typography.body)
            FrogButton(onClick = onBack) { Text("Back to components") }
        }
        return
    }
    androidx.compose.runtime.key(componentId) {
        val state = rememberComponentDetailState()
        ComponentDetailLayout(entry.demo.definition.create(entry.metadata, state), state, modifier)
    }
}
