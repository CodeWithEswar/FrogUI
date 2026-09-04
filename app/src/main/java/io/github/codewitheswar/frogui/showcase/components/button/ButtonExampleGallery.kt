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
import io.github.codewitheswar.frogui.showcase.detail.ComponentExampleSection
import io.github.codewitheswar.frogui.showcase.icons.FrogIcons
import io.github.codewitheswar.frogui.theme.FrogTheme

@Composable
internal fun ButtonExampleGallery(examples: List<ComponentExampleMetadata>) {
    Column(verticalArrangement = Arrangement.spacedBy(FrogTheme.spacing.xxl)) {
        examples.forEach { example ->
            ComponentExampleSection(example) {
                            when (example.id) {
                                "primary" -> ButtonPrimaryExample(); "secondary" -> ButtonSecondaryExample()
                                "outline" -> ButtonOutlineExample(); "ghost" -> ButtonGhostExample()
                                "destructive" -> ButtonDestructiveExample(); "loading" -> ButtonLoadingExample()
                                "leading" -> ButtonLeadingExample(FrogIcons.Play); "trailing" -> ButtonTrailingExample(FrogIcons.Forward)
                                "disabled" -> ButtonDisabledExample(); "fullwidth" -> ButtonFullWidthExample()
                            }
            }
        }
    }
}

@Composable
internal fun ButtonStateGallery() {
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
