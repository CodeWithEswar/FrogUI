package io.github.codewitheswar.frogui.showcase.components.button

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.components.button.*
import io.github.codewitheswar.frogui.registry.FrogComponentMetadata
import io.github.codewitheswar.frogui.showcase.colorpicker.*
import io.github.codewitheswar.frogui.showcase.detail.*
import io.github.codewitheswar.frogui.theme.FrogThemeDefaults

@Composable
internal fun buttonShowcaseDefinition(metadata: FrogComponentMetadata, ui: ComponentDetailState): ComponentShowcaseDefinition {
    var state by rememberSaveable(stateSaver = ButtonDemoState.saver) { mutableStateOf(ButtonDemoState()) }
    var activations by rememberSaveable { mutableIntStateOf(0) }
    var draftEncoded by rememberSaveable { mutableStateOf<String?>(null) }
    var valid by rememberSaveable { mutableStateOf(true) }
    var revision by rememberSaveable { mutableIntStateOf(0) }
    val customId = (ui.currentPage as? ComponentDrawerPage.Custom)?.id
    val editing = customId?.takeIf { it.startsWith("color:") }?.substringAfter(':')?.let { runCatching { ButtonColorProperty.valueOf(it) }.getOrNull() }
    val draft = draftEncoded?.let(FrogColorValue::decode)
    val effective = if (editing == null) state else state.withColor(editing, draft)
    val tokens = if (ui.previewDark) FrogThemeDefaults.darkColors() else FrogThemeDefaults.lightColors()
    fun openColor(property: ButtonColorProperty) {
        draftEncoded = state.colorOverrides[property]?.encode(); valid = true; revision++
        ui.push(ComponentDrawerPage.Custom("color:${property.name}"))
    }
    return ComponentShowcaseDefinition(
        metadata = metadata,
        preview = { ButtonLivePreview(effective, { activations++ }, Modifier.testTag("live-button")) },
        quickControls = { ButtonQuickControls(state, { state = it }) },
        inspector = { PropertyInspector(state, { state = it }, onColor = ::openColor, tokenColors = tokens) },
        inspectorPreview = { ButtonInspectorPreview(state, ui, { activations++ }) },
        previewStatus = "Activations: $activations",
        onReset = { state = ButtonDemoState(); activations = 0 },
        generatedCode = effective.toCodeSnippet(),
        codeNote = if (state.hasLeadingIcon || state.hasTrailingIcon) "Pass your app's icon vectors to the example's parameters. FrogButton itself has no icon-provider dependency." else null,
        api = metadata.properties.map(::buttonApiProperty),
        accessibility = buttonAccessibility,
        previewContent = {
            ComponentDocSection("States") { ButtonStateGallery() }
            ComponentDocSection("Examples") { ButtonExampleGallery(metadata.examples) }
        },
        customDrawer = { id ->
            if (editing == null || id != customId) null else ComponentDrawerContent(
                title = editing.label, subtitle = "${metadata.displayName} · draft preview",
                preview = { ButtonInspectorPreview(effective, ui, { activations++ }, editing) },
                actions = {
                    TextButton(onClick = { draftEncoded = null; valid = true; revision++ }) { Text("Reset") }
                    Spacer(Modifier.weight(1f))
                    TextButton(onClick = ui::back) { Text("Cancel") }
                    FrogButton(onClick = { state = state.withColor(editing, draft); ui.back() }, enabled = valid, size = FrogButtonSize.Small) { Text("Apply") }
                },
                content = {
                    Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                        ColorComparison("Current", effective.colorValue(editing).resolve(tokens))
                        ColorComparison("Previous", state.colorValue(editing).resolve(tokens))
                    }
                    key(editing, revision) {
                        FrogColorPicker(effective.colorValue(editing), { draftEncoded = it.encode() }, tokenColors = tokens, onValidityChange = { valid = it })
                    }
                },
            )
        },
    )
}

private val buttonAccessibility = ComponentAccessibilityInfo(
    "FrogButton exposes one button action, its label, enabled/loading state, and a minimum touch target.",
    listOf(
        AccessibilityFact("Semantics and state", "One Button role. Disabled and loading states suppress activation; loading keeps the label and measured slots.", "Automated interaction coverage"),
        AccessibilityFact("Touch and focus", "The full target is at least 48dp. Focus uses a semantic ring; decorative icons do not duplicate the label.", "Automated bounds coverage; speech review pending"),
        AccessibilityFact("Text scaling and RTL", "Text may increase surface height. Inspector choices scroll at large scales and leading/trailing positions follow layout direction.", "Responsive render coverage"),
        AccessibilityFact("Contrast and motion", "Check custom foreground/background combinations. Reduced-motion settings disable showcase transitions and press transforms."),
    ),
)
