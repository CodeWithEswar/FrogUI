package io.github.codewitheswar.frogui.showcase.components.button

import androidx.compose.foundation.interaction.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.components.button.FrogButton
import io.github.codewitheswar.frogui.registry.ComponentExampleMetadata
import io.github.codewitheswar.frogui.showcase.code.FrogCodeSnippet
import io.github.codewitheswar.frogui.theme.FrogTheme

@Composable
internal fun ButtonExampleGallery(examples: List<ComponentExampleMetadata>) {
    BoxWithConstraints(Modifier.fillMaxWidth()) {
        val columns = if (maxWidth >= 640.dp) 2 else 1
        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            examples.chunked(columns).forEach { row ->
                Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                    row.forEach { example ->
                        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Text(example.title, style = FrogTheme.typography.subheading, color = FrogTheme.colors.foreground)
                            Text(example.description, style = FrogTheme.typography.bodySmall, color = FrogTheme.colors.mutedForeground)
                            when (example.id) {
                                "primary" -> ButtonPrimaryExample(); "secondary" -> ButtonSecondaryExample()
                                "outline" -> ButtonOutlineExample(); "ghost" -> ButtonGhostExample()
                                "destructive" -> ButtonDestructiveExample(); "loading" -> ButtonLoadingExample()
                                "leading" -> ButtonLeadingExample(); "trailing" -> ButtonTrailingExample()
                                "disabled" -> ButtonDisabledExample(); "fullwidth" -> ButtonFullWidthExample()
                            }
                            FrogCodeSnippet(example.codeSnippet)
                            HorizontalDivider(color = FrogTheme.colors.border)
                        }
                    }
                    if (row.size < columns) Spacer(Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
internal fun ButtonStateGallery() {
    Text("State comparison", style = FrogTheme.typography.heading, color = FrogTheme.colors.foreground)
    FlowRow(horizontalArrangement = Arrangement.spacedBy(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        listOf("Default", "Pressed (simulated)", "Focused (simulated)", "Disabled", "Loading").forEach { state ->
            val source = remember { MutableInteractionSource() }
            LaunchedEffect(state) {
                if (state.startsWith("Pressed")) source.emit(PressInteraction.Press(Offset.Zero))
                if (state.startsWith("Focused")) source.emit(FocusInteraction.Focus())
            }
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(state, style = FrogTheme.typography.label, color = FrogTheme.colors.mutedForeground)
                FrogButton(onClick = {}, enabled = state != "Disabled", loading = state == "Loading", interactionSource = source) { Text("Continue") }
            }
        }
    }
}
