package io.github.codewitheswar.frogui.showcase.components.iconbutton

import androidx.compose.foundation.interaction.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.components.button.*
import io.github.codewitheswar.frogui.registry.ComponentExampleMetadata
import io.github.codewitheswar.frogui.registry.FrogComponentMetadata
import io.github.codewitheswar.frogui.showcase.colorpicker.*
import io.github.codewitheswar.frogui.showcase.detail.*
import io.github.codewitheswar.frogui.showcase.icons.FrogIcons
import io.github.codewitheswar.frogui.theme.FrogTheme
import io.github.codewitheswar.frogui.theme.FrogThemeDefaults

@Composable
internal fun iconButtonShowcaseDefinition(
    metadata: FrogComponentMetadata,
    ui: ComponentDetailState
): ComponentShowcaseDefinition {
    var state by rememberSaveable(stateSaver = IconButtonDemoState.saver) { mutableStateOf(IconButtonDemoState()) }
    var activations by rememberSaveable { mutableIntStateOf(0) }
    var draftEncoded by rememberSaveable { mutableStateOf<String?>(null) }
    var valid by rememberSaveable { mutableStateOf(true) }
    var revision by rememberSaveable { mutableIntStateOf(0) }

    val customId = (ui.currentPage as? ComponentDrawerPage.Custom)?.id
    val editing = customId?.takeIf { it.startsWith("color:") }
        ?.substringAfter(':')
        ?.let { runCatching { IconButtonColorProperty.valueOf(it) }.getOrNull() }
    val draft = draftEncoded?.let(FrogColorValue::decode)
    val effective = if (editing == null) state else state.withColor(editing, draft)
    val tokens = if (ui.previewDark) FrogThemeDefaults.darkColors() else FrogThemeDefaults.lightColors()

    fun openColor(property: IconButtonColorProperty) {
        draftEncoded = state.colorOverrides[property]?.encode()
        valid = true
        revision++
        ui.push(ComponentDrawerPage.Custom("color:${property.name}"))
    }

    return ComponentShowcaseDefinition(
        metadata = metadata,
        preview = { IconButtonLivePreview(effective, { activations++ }, Modifier.testTag("live-icon-button")) },
        quickControls = { IconButtonQuickControls(state, { state = it }) },
        inspector = { PropertyInspector(state, { state = it }, onColor = ::openColor, tokenColors = tokens) },
        inspectorPreview = { IconButtonInspectorPreview(state, ui, { activations++ }) },
        previewStatus = "Activations: $activations",
        onReset = { state = IconButtonDemoState(); activations = 0 },
        generatedCode = effective.toCodeSnippet(),
        codeNote = "FrogIconButton requires contentDescription for accessible actions. The icon slot composable should provide contentDescription = null to avoid duplicate announcements.",
        api = metadata.properties.map(::iconButtonApiProperty),
        accessibility = iconButtonAccessibility,
        previewContent = {
            ComponentDocSection("States") { IconButtonStateGallery() }
            ComponentDocSection("Examples") { IconButtonExampleGallery(metadata.examples) }
        },
        customDrawer = { id ->
            if (editing == null || id != customId) null else ComponentDrawerContent(
                title = editing.label,
                subtitle = "${metadata.displayName} · draft preview",
                preview = { IconButtonInspectorPreview(effective, ui, { activations++ }, editing) },
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
private fun IconButtonExampleGallery(examples: List<ComponentExampleMetadata>) {
    Column(verticalArrangement = Arrangement.spacedBy(FrogTheme.spacing.xxl)) {
        examples.forEach { example ->
            ComponentExampleSection(example) {
                when (example.id) {
                    "basic" -> IconButtonBasicExample()
                    "tonal" -> IconButtonTonalExample()
                    "outline" -> IconButtonOutlineExample()
                    "ghost" -> IconButtonGhostExample()
                    "loading" -> IconButtonLoadingExample()
                    "badge" -> IconButtonBadgeExample()
                    "toolbar" -> IconButtonToolbarExample()
                }
            }
        }
    }
}

@Composable
private fun IconButtonStateGallery() {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        listOf("Default", "Pressed (simulated)", "Focused (simulated)", "Disabled", "Loading").forEach { stateName ->
            val source = remember { MutableInteractionSource() }
            LaunchedEffect(stateName) {
                if (stateName.startsWith("Pressed")) source.emit(PressInteraction.Press(Offset.Zero))
                if (stateName.startsWith("Focused")) source.emit(FocusInteraction.Focus())
            }
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(stateName, style = FrogTheme.typography.label, color = FrogTheme.colors.mutedForeground)
                FrogIconButton(
                    icon = { Icon(FrogIcons.Search, null, Modifier.size(FrogIconButtonDefaults.iconSize(FrogIconButtonSize.Medium))) },
                    contentDescription = "Search",
                    onClick = {},
                    enabled = stateName != "Disabled",
                    loading = stateName == "Loading",
                    interactionSource = source
                )
            }
        }
    }
}

private val iconButtonAccessibility = ComponentAccessibilityInfo(
    summary = "FrogIconButton exposes a Button role, mandatory accessible action label, loading/disabled state, and a minimum 48dp touch target.",
    facts = listOf(
        AccessibilityFact("Role and action description", "Exposes Role.Button. contentDescription is mandatory because icon controls lack visible text. Describe the action (e.g. \"Search\"), not the visual glyph.", "Automated semantics coverage"),
        AccessibilityFact("Icon child semantics", "The inner icon composable should use contentDescription = null to ensure TalkBack announces the button label only once.", "Automated semantic tree verification"),
        AccessibilityFact("Touch target and density", "Small (32dp) and Medium (40dp) visual containers maintain a canonical 48dp minimum touch target for motor accessibility.", "Automated bounds verification"),
        AccessibilityFact("Keyboard and focus", "Focus ring appears on hardware keyboard or D-pad navigation. Enter and Space trigger the action when enabled.", "Keyboard navigation coverage"),
        AccessibilityFact("Badge semantics", "Badges are positioned at TopEnd without changing touch targets. If the badge conveys critical state (e.g. unread count), include it in the contentDescription.", "Layout and RTL alignment coverage")
    )
)
