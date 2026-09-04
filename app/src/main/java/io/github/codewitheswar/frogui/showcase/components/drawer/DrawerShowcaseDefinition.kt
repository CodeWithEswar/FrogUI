package io.github.codewitheswar.frogui.showcase.components.drawer

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.components.button.FrogButton
import io.github.codewitheswar.frogui.components.overlays.drawer.*
import io.github.codewitheswar.frogui.registry.FrogComponentMetadata
import io.github.codewitheswar.frogui.showcase.detail.*
import io.github.codewitheswar.frogui.showcase.colorpicker.*
import io.github.codewitheswar.frogui.showcase.icons.FrogIcons
import io.github.codewitheswar.frogui.showcase.inspector.*
import io.github.codewitheswar.frogui.theme.FrogTheme
import kotlinx.coroutines.launch

@Composable
internal fun drawerShowcaseDefinition(metadata: FrogComponentMetadata, ui: ComponentDetailState): ComponentShowcaseDefinition {
    var state by rememberSaveable(stateSaver = DrawerDemoState.saver) { mutableStateOf(DrawerDemoState()) }
    val overlay = rememberFrogDrawerState()
    val scope = rememberCoroutineScope()
    var draftEncoded by rememberSaveable { mutableStateOf<String?>(null) }
    var valid by rememberSaveable { mutableStateOf(true) }
    var revision by rememberSaveable { mutableIntStateOf(0) }
    val customId = (ui.currentPage as? ComponentDrawerPage.Custom)?.id
    val editing = customId?.takeIf { it.startsWith("drawer-color:") }?.substringAfter(':')
        ?.let { runCatching { DrawerColorProperty.valueOf(it) }.getOrNull() }
    val draft = draftEncoded?.let(FrogColorValue::decode)
    val effective = if (editing == null) state else state.withColor(editing, draft)
    fun openColor(property: DrawerColorProperty) {
        draftEncoded = state.colorOverrides[property]?.encode()
        valid = true
        revision++
        ui.push(ComponentDrawerPage.Custom("drawer-color:${property.name}"))
    }
    BackHandler(overlay.isOpen && ui.currentPage == null) { scope.launch { overlay.close() } }
    return ComponentShowcaseDefinition(
        metadata = metadata,
        preview = { DrawerPreview(effective, overlay) },
        inspector = { DrawerInspector(state, { state = it }, ::openColor) },
        quickControls = { FrogEnumSelector("Presentation", FrogDrawerPresentation.entries.map { it.name }, state.presentation.name) { state = state.copy(presentation = FrogDrawerPresentation.valueOf(it)) } },
        onReset = { state = DrawerDemoState(); overlay.snapTo(FrogDrawerValue.Closed) },
        generatedCode = effective.toCodeSnippet(),
        api = metadata.properties.map(::drawerApiProperty),
        accessibility = drawerAccessibility,
        previewStatus = if (overlay.isOpen) "Drawer open · ${state.presentation.name}" else "Drawer closed · ${state.presentation.name}",
        capabilities = PreviewCapabilities(alignment = false, contentMode = PreviewContentMode.Overlay, minHeight = 360.dp),
        previewContent = {
            ComponentDocSection("Presentations", "Auto resolves from the preview's available width. Bottom and Side make the placement explicit.") {
                Text("The main preview is a bounded workspace. The examples below open native modal windows. Both use the same public FrogDrawer implementation.", style = FrogTheme.typography.bodySmall, color = FrogTheme.colors.foreground)
            }
            ComponentDocSection("Examples") {
                metadata.examples.forEach { example -> ComponentExampleSection(example) {
                    when (example.id) { "basic" -> DrawerBasicExample(); "bottom" -> DrawerBottomExample(); "side" -> DrawerSideExample(); "header" -> DrawerHeaderExample(); "footer" -> DrawerFooterExample(); "scroll" -> DrawerScrollExample() }
                } }
            }
        },
        customDrawer = { id ->
            if (editing == null || id != customId) null else ComponentDrawerContent(
                title = editing.label,
                subtitle = "Drawer · draft preview",
                preview = {
                    Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                        DrawerColorComparison("Draft", effective.colorValue(editing))
                        DrawerColorComparison("Previous", state.colorValue(editing))
                    }
                },
                actions = {
                    TextButton(onClick = { draftEncoded = null; valid = true; revision++ }) { Text("Reset") }
                    Spacer(Modifier.weight(1f))
                    TextButton(onClick = ui::back) { Text("Cancel") }
                    FrogButton(onClick = { state = state.withColor(editing, draft); ui.back() }, enabled = valid, size = io.github.codewitheswar.frogui.components.button.FrogButtonSize.Small) { Text("Apply") }
                },
                content = {
                    key(editing, revision) {
                        FrogColorPicker(effective.colorValue(editing), { draftEncoded = it.encode() }, onValidityChange = { valid = it })
                    }
                },
            )
        },
    )
}

@Composable
private fun DrawerInspector(state: DrawerDemoState, onChange: (DrawerDemoState) -> Unit, onColor: (DrawerColorProperty) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(FrogTheme.spacing.xxl)) {
        Text("Properties", style = FrogTheme.typography.heading, color = FrogTheme.colors.foreground)
        FrogInspectorSection("Presentation") {
            FrogEnumSelector("Presentation", FrogDrawerPresentation.entries.map { it.name }, state.presentation.name) { onChange(state.copy(presentation = FrogDrawerPresentation.valueOf(it))) }
            if (state.presentation != FrogDrawerPresentation.Bottom) FrogEnumSelector("Side", FrogDrawerSide.entries.map { it.name }, state.side.name) { onChange(state.copy(side = FrogDrawerSide.valueOf(it))) }
        }
        FrogInspectorSection("Appearance") {
            FrogEnumSelector("Shape", DrawerShapePreset.entries.map { it.label }, state.shape.label) { label ->
                onChange(state.copy(shape = DrawerShapePreset.entries.first { it.label == label }))
            }
            DrawerColorProperty.entries.forEach { property ->
                val value = state.colorValue(property)
                FrogInspectorRow(property.label, value.readableValue(FrogTheme.colors), { onColor(property) },
                    leading = { ColorSwatch(value.resolve(FrogTheme.colors)) })
            }
            TextButton(onClick = { onChange(state.resetColors()) }, enabled = state.colorOverrides.isNotEmpty()) {
                Icon(FrogIcons.Reset, null, Modifier.size(16.dp)); Spacer(Modifier.width(6.dp)); Text("Reset colors")
            }
        }
        FrogInspectorSection("Content") {
            FrogInspectorText("Drawer title", state.title) { onChange(state.copy(title = it)) }
            FrogBooleanSelector("Show subtitle", state.showSubtitle) { onChange(state.copy(showSubtitle = it)) }
            if (state.showSubtitle) FrogInspectorText("Drawer subtitle", state.subtitle) { onChange(state.copy(subtitle = it)) }
        }
        FrogInspectorSection("Demo content") {
            FrogBooleanSelector("Footer actions", state.showFooter) { onChange(state.copy(showFooter = it)) }
            FrogBooleanSelector("Long content", state.longContent) { onChange(state.copy(longContent = it)) }
            Text("These switches configure footer and content slots; they are not Drawer props. Close, Back, outside tap, and the bottom handle dismiss the preview.", style = FrogTheme.typography.bodySmall, color = FrogTheme.colors.mutedForeground)
        }
    }
}

@Composable
private fun DrawerPreview(state: DrawerDemoState, overlay: FrogDrawerState) {
    val scope = rememberCoroutineScope()
    val trigger = remember { FocusRequester() }
    var wasOpen by remember { mutableStateOf(false) }
    LaunchedEffect(overlay.isOpen) {
        if (overlay.isOpen) wasOpen = true else if (wasOpen) { trigger.requestFocus(); wasOpen = false }
    }
    Box(Modifier.fillMaxSize().testTag("overlay-preview"), contentAlignment = Alignment.Center) {
        FrogButton(onClick = { scope.launch { overlay.open() } }, modifier = Modifier.focusRequester(trigger).testTag("open-drawer-preview")) { Text("Open drawer") }
        FrogDrawer(state = overlay, onDismissRequest = { scope.launch { overlay.close() } }, presentation = state.presentation, side = state.side,
            title = state.title, subtitle = state.subtitle.takeIf { state.showSubtitle },
            shape = state.shape.shape, colors = state.resolvedColors(),
            closeIcon = { Icon(FrogIcons.Close, null, Modifier.size(18.dp)) },
            footer = if (state.showFooter) ({ FrogButton(onClick = { scope.launch { overlay.close() } }) { Text("Done") } }) else null) {
            if (state.longContent) repeat(12) { index ->
                Text("Section ${index + 1}", style = FrogTheme.typography.subheading, color = FrogTheme.colors.foreground)
                Text("Contextual options and guidance.", style = FrogTheme.typography.bodySmall, color = FrogTheme.colors.mutedForeground)
            } else Text("Contextual options and guidance.", style = FrogTheme.typography.bodySmall, color = FrogTheme.colors.foreground)
        }
    }
}

@Composable
private fun DrawerColorComparison(label: String, value: FrogColorValue) {
    val resolved = value.resolve(FrogTheme.colors)
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        ColorSwatch(resolved)
        Column {
            Text(label, style = FrogTheme.typography.bodySmall, color = FrogTheme.colors.mutedForeground)
            Text(value.readableValue(FrogTheme.colors), style = FrogTheme.typography.code, color = FrogTheme.colors.foreground)
        }
    }
}

private val drawerAccessibility = ComponentAccessibilityInfo(
    "FrogDrawer provides pane semantics, dismissal actions, focus entry, and adaptive placement. Modal and embedded usage have different focus boundaries.",
    listOf(
        AccessibilityFact("Pane and dismissal", "The title names the pane. Close, outside tap, Back and the bottom handle request dismissal; the owner updates state."),
        AccessibilityFact("Keyboard and focus", "Native modal windows contain focus. Embedded previews return focus to their Open drawer control and do not claim to trap the whole application."),
        AccessibilityFact("Touch and IME", "Close and navigation buttons retain 48dp targets. Modal surfaces account for system/keyboard insets and provide a scrolling body."),
        AccessibilityFact("RTL and motion", "Start/End placement follows layout direction. Zero-duration theme motion disables transitions; large text can increase header/footer height."),
        AccessibilityFact("Manual review", "Human TalkBack speech/traversal, physical keyboard/tablet and hinge-aware placement remain release checks.", "Manual review pending"),
    ),
)
