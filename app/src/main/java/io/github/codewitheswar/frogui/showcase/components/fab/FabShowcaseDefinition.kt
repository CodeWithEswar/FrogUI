package io.github.codewitheswar.frogui.showcase.components.fab

import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.components.button.FrogButton
import io.github.codewitheswar.frogui.components.button.FrogButtonSize
import io.github.codewitheswar.frogui.components.fab.FrogFabPresentation
import io.github.codewitheswar.frogui.components.fab.FrogFloatingActionButton
import io.github.codewitheswar.frogui.registry.ComponentExampleMetadata
import io.github.codewitheswar.frogui.registry.FrogComponentMetadata
import io.github.codewitheswar.frogui.showcase.colorpicker.FrogColorPicker
import io.github.codewitheswar.frogui.showcase.colorpicker.FrogColorValue
import io.github.codewitheswar.frogui.showcase.detail.AccessibilityFact
import io.github.codewitheswar.frogui.showcase.detail.ComponentAccessibilityInfo
import io.github.codewitheswar.frogui.showcase.detail.ComponentDocSection
import io.github.codewitheswar.frogui.showcase.detail.ComponentDrawerContent
import io.github.codewitheswar.frogui.showcase.detail.ComponentDrawerPage
import io.github.codewitheswar.frogui.showcase.detail.ComponentDetailState
import io.github.codewitheswar.frogui.showcase.detail.ComponentExampleSection
import io.github.codewitheswar.frogui.showcase.detail.ComponentShowcaseDefinition
import io.github.codewitheswar.frogui.showcase.icons.FrogIcons
import io.github.codewitheswar.frogui.theme.FrogTheme
import io.github.codewitheswar.frogui.theme.FrogThemeDefaults

@Composable
internal fun fabShowcaseDefinition(
    metadata: FrogComponentMetadata,
    ui: ComponentDetailState
): ComponentShowcaseDefinition {
    var state by rememberSaveable(stateSaver = FabDemoState.saver) { mutableStateOf(FabDemoState()) }
    var activations by rememberSaveable { mutableIntStateOf(0) }
    var draftEncoded by rememberSaveable { mutableStateOf<String?>(null) }
    var valid by rememberSaveable { mutableStateOf(true) }
    var revision by rememberSaveable { mutableIntStateOf(0) }

    val customId = (ui.currentPage as? ComponentDrawerPage.Custom)?.id
    val editing = customId?.takeIf { it.startsWith("color:") }
        ?.substringAfter(':')
        ?.let { runCatching { FabColorProperty.valueOf(it) }.getOrNull() }
    val draft = draftEncoded?.let(FrogColorValue::decode)
    val effective = if (editing == null) state else state.withColor(editing, draft)
    val tokens = if (ui.previewDark) FrogThemeDefaults.darkColors() else FrogThemeDefaults.lightColors()

    fun openColor(property: FabColorProperty) {
        draftEncoded = state.colorOverrides[property]?.encode()
        valid = true
        revision++
        ui.push(ComponentDrawerPage.Custom("color:${property.name}"))
    }

    return ComponentShowcaseDefinition(
        metadata = metadata,
        preview = { FabLivePreview(effective, { activations++ }, Modifier.testTag("live-fab")) },
        quickControls = { FabQuickControls(state, { state = it }) },
        inspector = { PropertyInspector(state, { state = it }, onColor = ::openColor, tokenColors = tokens) },
        inspectorPreview = { FabInspectorPreview(state, ui, { activations++ }) },
        previewStatus = "Activations: $activations",
        onReset = { state = FabDemoState(); activations = 0 },
        generatedCode = effective.toCodeSnippet(),
        codeNote = "FrogFloatingActionButton requires contentDescription for accessible naming. When hidden (visible = false), semantics and clicks are suppressed.",
        api = metadata.properties.map(::fabApiProperty),
        accessibility = fabAccessibility,
        previewContent = {
            ComponentDocSection("States") { FabStateGallery() }
            ComponentDocSection("Examples") { FabExampleGallery(metadata.examples) }
        },
        customDrawer = { id ->
            if (editing == null || id != customId) null else ComponentDrawerContent(
                title = editing.label,
                subtitle = "${metadata.displayName} · draft preview",
                preview = { FabInspectorPreview(effective, ui, { activations++ }, editing) },
                actions = {
                    TextButton(onClick = { draftEncoded = null; valid = true; revision++ }) { Text("Reset") }
                    Spacer(Modifier.weight(1f))
                    TextButton(onClick = ui::back) { Text("Cancel") }
                    FrogButton(
                        onClick = { state = state.withColor(editing, draft); ui.back() },
                        enabled = valid,
                        size = FrogButtonSize.Small
                    ) { Text("Apply") }
                },
                content = {
                    Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                        ColorComparison("Current", effective.colorValue(editing).resolve(tokens))
                        ColorComparison("Previous", state.colorValue(editing).resolve(tokens))
                    }
                    key(editing, revision) {
                        FrogColorPicker(
                            effective.colorValue(editing),
                            { draftEncoded = it.encode() },
                            tokenColors = tokens,
                            onValidityChange = { valid = it }
                        )
                    }
                }
            )
        }
    )
}

@Composable
private fun FabExampleGallery(examples: List<ComponentExampleMetadata>) {
    Column(verticalArrangement = Arrangement.spacedBy(FrogTheme.spacing.xxl)) {
        examples.forEach { example ->
            ComponentExampleSection(example) {
                when (example.id) {
                    "regular" -> FabRegularExample()
                    "small" -> FabSmallExample()
                    "extended" -> FabExtendedExample()
                    "collapsing" -> FabCollapsingExample()
                    "scroll-aware" -> FabScrollAwareExample()
                    "inset-aware" -> FabInsetAwareExample()
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun FabStateGallery() {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        listOf("Regular", "Small", "Extended", "Disabled", "Pressed (simulated)", "Focused (simulated)").forEach { stateName ->
            val source = remember { MutableInteractionSource() }
            LaunchedEffect(stateName) {
                if (stateName.startsWith("Pressed")) source.emit(PressInteraction.Press(Offset.Zero))
                if (stateName.startsWith("Focused")) source.emit(FocusInteraction.Focus())
            }
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(stateName, style = FrogTheme.typography.label, color = FrogTheme.colors.mutedForeground)
                FrogFloatingActionButton(
                    icon = { Icon(FrogIcons.Add, null) },
                    label = if (stateName == "Extended") ({ Text("Compose") }) else null,
                    contentDescription = "Action",
                    onClick = {},
                    presentation = when (stateName) {
                        "Small" -> FrogFabPresentation.Small
                        "Extended" -> FrogFabPresentation.Extended
                        else -> FrogFabPresentation.Regular
                    },
                    enabled = stateName != "Disabled",
                    interactionSource = source
                )
            }
        }
    }
}

private val fabAccessibility = ComponentAccessibilityInfo(
    summary = "FrogFloatingActionButton exposes Role.Button, a mandatory accessible name, complete semantic removal when hidden, and a 48dp minimum touch target.",
    facts = listOf(
        AccessibilityFact("Role and action description", "Exposes Role.Button. contentDescription is mandatory for all presentations. Describe the action (e.g. \"Create item\"), not the visual glyph.", "Automated semantics coverage"),
        AccessibilityFact("Hidden state semantics", "When visible = false, the FAB is completely removed from the semantics tree and cannot be focused or clicked.", "Automated bounds & semantics coverage"),
        AccessibilityFact("Touch target and density", "Small FAB (40dp visual container) maintains the 48dp minimum interactive touch target for motor accessibility.", "Automated touch bounds verification"),
        AccessibilityFact("Reduced-motion support", "Respects user preference by snapping layout and visibility instantly without spatial or scale transitions.", "Motion policy coverage"),
        AccessibilityFact("Focus and keyboard", "Focus ring highlights keyboard and D-pad navigation. Enter and Space trigger activation when enabled.", "Keyboard navigation coverage")
    )
)
