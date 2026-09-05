package io.github.codewitheswar.frogui.showcase.components.textfield

import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.components.textfield.FrogTextField
import io.github.codewitheswar.frogui.components.textfield.FrogTextFieldVariant
import io.github.codewitheswar.frogui.registry.ComponentExampleMetadata
import io.github.codewitheswar.frogui.registry.FrogComponentMetadata
import io.github.codewitheswar.frogui.showcase.detail.AccessibilityFact
import io.github.codewitheswar.frogui.showcase.detail.ComponentAccessibilityInfo
import io.github.codewitheswar.frogui.showcase.detail.ComponentDetailState
import io.github.codewitheswar.frogui.showcase.detail.ComponentDocSection
import io.github.codewitheswar.frogui.showcase.detail.ComponentExampleSection
import io.github.codewitheswar.frogui.showcase.detail.ComponentShowcaseDefinition
import io.github.codewitheswar.frogui.theme.FrogTheme

@Composable
internal fun textFieldShowcaseDefinition(
    metadata: FrogComponentMetadata,
    ui: ComponentDetailState
): ComponentShowcaseDefinition {
    var state by rememberSaveable(stateSaver = TextFieldDemoState.Saver) { mutableStateOf(TextFieldDemoState()) }

    return ComponentShowcaseDefinition(
        metadata = metadata,
        preview = { TextFieldLivePreview(state, { state = it }, Modifier.testTag("live-textfield")) },
        quickControls = {
            // Quick variant switch in preview bar
            FrogTheme {
                Column {
                    Text(
                        text = "Variant: ${state.variant.name}",
                        style = FrogTheme.typography.bodySmall,
                        color = FrogTheme.colors.mutedForeground,
                    )
                }
            }
        },
        inspector = { PropertyInspector(state, { state = it }) },
        inspectorPreview = { TextFieldInspectorPreview(state) },
        previewStatus = "Characters: ${state.value.length}",
        onReset = { state = TextFieldDemoState() },
        generatedCode = state.toCodeSnippet(),
        codeNote = "FrogTextField preserves state hoisting. The application owns value and validation state; FrogTextField handles presentation and accessibility.",
        api = metadata.properties.map(::textFieldApiProperty),
        accessibility = textFieldAccessibility,
        previewContent = {
            ComponentDocSection("States") { TextFieldStateGallery() }
            ComponentDocSection("Examples") { TextFieldExampleGallery(metadata.examples) }
        },
    )
}

@Composable
private fun TextFieldExampleGallery(examples: List<ComponentExampleMetadata>) {
    Column(verticalArrangement = Arrangement.spacedBy(FrogTheme.spacing.xxl)) {
        examples.forEach { example ->
            ComponentExampleSection(example) {
                when (example.id) {
                    "basic" -> TextFieldBasicExample()
                    "placeholder-helper" -> TextFieldPlaceholderHelperExample()
                    "validation-error" -> TextFieldValidationErrorExample()
                    "leading-trailing" -> TextFieldLeadingTrailingExample()
                    "read-only" -> TextFieldReadOnlyExample()
                    "multiline" -> TextFieldMultilineExample()
                    "form-flow" -> TextFieldFormFlowExample()
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun TextFieldStateGallery() {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        listOf("Filled", "Outline", "Underline", "Error State", "Read-Only", "Disabled").forEach { stateName ->
            val source = remember { MutableInteractionSource() }
            LaunchedEffect(stateName) {
                if (stateName == "Focused (simulated)") source.emit(FocusInteraction.Focus())
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(stateName, style = FrogTheme.typography.label, color = FrogTheme.colors.mutedForeground)
                FrogTextField(
                    value = when (stateName) {
                        "Error State" -> "invalid-entry"
                        "Read-Only" -> "read_only_token"
                        "Disabled" -> "disabled_entry"
                        else -> "Input sample"
                    },
                    onValueChange = {},
                    label = stateName,
                    variant = when (stateName) {
                        "Outline" -> FrogTextFieldVariant.Outline
                        "Underline" -> FrogTextFieldVariant.Underline
                        else -> FrogTextFieldVariant.Filled
                    },
                    errorText = if (stateName == "Error State") "Invalid entry format" else null,
                    helperText = if (stateName != "Error State") "Supporting information" else null,
                    enabled = stateName != "Disabled",
                    readOnly = stateName == "Read-Only",
                    interactionSource = source,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

private val textFieldAccessibility = ComponentAccessibilityInfo(
    summary = "FrogTextField provides mandatory label association, semantic error exposure, distinct read-only and disabled handling, and accessible touch target heights.",
    facts = listOf(
        AccessibilityFact(
            title = "Label association",
            description = "The label is persistent and never disappears while typing, ensuring the field's identity remains clear to screen reader users.",
            verification = "Automated semantics & hierarchy verification"
        ),
        AccessibilityFact(
            title = "Semantic error reporting",
            description = "When errorText is present, it replaces helperText and is semantically linked to the input, preventing reliance on color alone.",
            verification = "Automated error semantics coverage"
        ),
        AccessibilityFact(
            title = "Disabled vs read-only distinction",
            description = "Disabled fields suppress interaction entirely. Read-only fields prevent edits while allowing full focus, readability, and text copying.",
            verification = "Automated state & interaction coverage"
        ),
        AccessibilityFact(
            title = "Touch and container target",
            description = "All variants provide at least 48dp container height (56dp standard for Filled/Outline), ensuring reliable motor accessibility.",
            verification = "Automated touch bounds verification"
        ),
        AccessibilityFact(
            title = "IME and keyboard navigation",
            description = "Supports physical keyboard input, IME actions (Next, Done, Search), and standard software keyboard configurations.",
            verification = "IME & keyboard action integration"
        )
    )
)
