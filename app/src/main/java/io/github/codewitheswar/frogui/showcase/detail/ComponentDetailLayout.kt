package io.github.codewitheswar.frogui.showcase.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.*
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.components.button.*
import io.github.codewitheswar.frogui.registry.FrogComponentMetadata
import io.github.codewitheswar.frogui.showcase.canvas.*
import io.github.codewitheswar.frogui.showcase.drawer.FrogDrawer
import io.github.codewitheswar.frogui.showcase.icons.FrogIcons
import io.github.codewitheswar.frogui.showcase.inspector.FrogEnumSelector
import io.github.codewitheswar.frogui.showcase.style.*
import io.github.codewitheswar.frogui.showcase.markdown.*
import io.github.codewitheswar.frogui.theme.FrogTheme
import io.github.codewitheswar.frogui.foundation.adaptive.FrogWindowSizeClass
import kotlinx.coroutines.launch

@Composable
internal fun ComponentDetailHeader(metadata: FrogComponentMetadata) {
    Column(verticalArrangement = Arrangement.spacedBy(FrogTheme.spacing.md)) {
        Text(metadata.displayName, style = FrogTheme.typography.titleLarge, color = FrogTheme.colors.foreground, modifier = Modifier.semantics { heading() })
        Text(metadata.description, style = FrogTheme.typography.bodySmall, color = FrogTheme.colors.mutedForeground)
        FlowRow(horizontalArrangement = Arrangement.spacedBy(FrogTheme.spacing.lg), verticalArrangement = Arrangement.spacedBy(FrogTheme.spacing.xs)) {
            Text(metadata.category.displayName, style = FrogTheme.typography.label, color = FrogTheme.colors.mutedForeground)
            ComponentStatusBadge(metadata.status.label)
            Text("Since ${metadata.since}", style = FrogTheme.typography.label, color = FrogTheme.colors.mutedForeground)
        }
    }
}

@Composable
private fun ComponentStatusBadge(label: String) {
    Text(label, Modifier.background(FrogTheme.colors.muted, FrogTheme.shapes.sm).border(1.dp, FrogTheme.colors.border, FrogTheme.shapes.sm).padding(horizontal = 6.dp, vertical = 2.dp),
        style = FrogTheme.typography.label, color = FrogTheme.colors.foreground)
}

@Composable
internal fun ComponentDetailLayout(definition: ComponentShowcaseDefinition, state: ComponentDetailState, modifier: Modifier = Modifier) {
    val docs = rememberComponentDocs(definition.metadata.id)
    val scroll = rememberScrollState()
    val scope = rememberCoroutineScope()
    val motion = LocalFrogMotionEnabled.current
    var tabsTop by remember { mutableIntStateOf(0) }
    BoxWithConstraints(modifier.fillMaxSize().testTag("component-detail")) {
        val wide = FrogTheme.adaptive.windowSizeClass(maxWidth) != FrogWindowSizeClass.Compact
        val split = wide && LocalDensity.current.fontScale <= 1.3f && definition.capabilities.inspector
        Row(Modifier.fillMaxSize().padding(horizontal = FrogTheme.spacing.xl), horizontalArrangement = Arrangement.spacedBy(FrogTheme.spacing.xl)) {
            Column(Modifier.weight(1f).verticalScroll(scroll).padding(vertical = FrogTheme.spacing.xl), verticalArrangement = Arrangement.spacedBy(FrogTheme.spacing.xl)) {
                ComponentDetailHeader(definition.metadata)
                ComponentPreviewWorkspace(definition, state)
                if (!split) definition.quickControls?.invoke()
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text(definition.previewStatus.orEmpty(), Modifier.weight(1f).semantics { liveRegion = LiveRegionMode.Polite }, style = FrogTheme.typography.bodySmall, color = FrogTheme.colors.mutedForeground)
                    if (!split && definition.capabilities.inspector) FrogButton(onClick = { state.open(ComponentDrawerPage.Inspector) }, variant = FrogButtonVariant.Outline,
                        size = FrogButtonSize.Small, leadingIcon = { Icon(FrogIcons.Sliders, null, Modifier.size(16.dp)) }) { Text("Customize") }
                }
                FrogShowcaseTabs(listOf("Preview", "Code", "API", "Accessibility"), state.tab, { selected ->
                    state.tab = selected
                    scope.launch {
                        withFrameNanos { }
                        if (motion) scroll.animateScrollTo(tabsTop.coerceAtMost(scroll.maxValue)) else scroll.scrollTo(tabsTop.coerceAtMost(scroll.maxValue))
                    }
                }, Modifier.onGloballyPositioned { tabsTop = it.positionInParent().y.toInt() }.testTag("component-detail-tabs"), fit = true)
                when (state.tab) {
                    0 -> {
                        ComponentDocSection("Overview") { Text(definition.metadata.description, style = FrogTheme.typography.body, color = FrogTheme.colors.foreground) }
                        definition.previewContent()
                        ComponentLongFormDocs(docs.withoutSection("Accessibility"))
                    }
                    1 -> ComponentCodeContent(definition.generatedCode, definition.metadata.name, definition.codeNote)
                    2 -> ComponentApiReference(definition.metadata.name, definition.api) { state.open(ComponentDrawerPage.Api(it)) }
                    3 -> ComponentAccessibilityContent(definition.accessibility, docs)
                }
            }
            if (split) {
                VerticalDivider(color = FrogTheme.colors.border)
                Column(Modifier.width(264.dp).fillMaxHeight().verticalScroll(rememberScrollState()).padding(vertical = FrogTheme.spacing.xl).testTag("persistent-inspector")) { definition.inspector() }
            }
        }
        ComponentInspectorHost(definition, state, side = wide)
    }
}

@Composable
internal fun ComponentPreviewWorkspace(definition: ComponentShowcaseDefinition, state: ComponentDetailState) {
    ComponentPreviewCanvas(state.previewDark, { state.previewDark = !state.previewDark }, state.width, { state.width = it }, definition.onReset,
        background = state.background, alignment = state.alignment, onConfigure = { state.open(ComponentDrawerPage.Preview) }, capabilities = definition.capabilities, content = definition.preview)
}

@Composable
internal fun ComponentInspectorHost(definition: ComponentShowcaseDefinition, state: ComponentDetailState, side: Boolean) {
    val page = state.currentPage
    val custom = (page as? ComponentDrawerPage.Custom)?.let { definition.customDrawer?.invoke(it.id) }
    val title = custom?.title ?: when (page) { ComponentDrawerPage.Inspector -> "Customize"; ComponentDrawerPage.Preview -> "Preview settings"; is ComponentDrawerPage.Api -> "API property"; else -> "Inspector" }
    FrogDrawer(page != null, state::dismiss, title, subtitle = custom?.subtitle ?: (page as? ComponentDrawerPage.Api)?.name ?: definition.metadata.name,
        side = side, onBack = if (state.pages.size > 1) state::back else null,
        preview = custom?.preview ?: if (page == ComponentDrawerPage.Inspector) definition.inspectorPreview else null, actions = custom?.actions) {
        when (page) {
            ComponentDrawerPage.Inspector -> definition.inspector()
            ComponentDrawerPage.Preview -> ComponentPreviewSettings(definition.capabilities, state)
            is ComponentDrawerPage.Api -> definition.api.firstOrNull { it.metadata.name == page.name }?.let { ComponentApiPropertyDetail(it) }
            is ComponentDrawerPage.Custom -> custom?.content?.invoke(this)
            null -> Unit
        }
    }
}

@Composable
private fun ComponentPreviewSettings(capabilities: PreviewCapabilities, state: ComponentDetailState) {
    if (capabilities.theme) FrogEnumSelector("Theme", listOf("Light", "Dark"), if (state.previewDark) "Dark" else "Light") { state.previewDark = it == "Dark" }
    if (capabilities.width) FrogEnumSelector("Width", PreviewWidthMode.entries.map { it.label }, state.width.label) { label -> state.width = PreviewWidthMode.entries.first { it.label == label } }
    if (capabilities.background) FrogEnumSelector("Background", PreviewBackground.entries.map { it.name }, state.background.name) { state.background = PreviewBackground.valueOf(it) }
    if (capabilities.alignment) FrogEnumSelector("Alignment", PreviewAlignment.entries.map { it.name }, state.alignment.name) { state.alignment = PreviewAlignment.valueOf(it) }
    Text("These preview settings are independent of exported component code.", style = FrogTheme.typography.bodySmall, color = FrogTheme.colors.mutedForeground)
    val appDark = FrogTheme.colors.isDark
    TextButton(onClick = { state.previewDark = appDark; state.width = PreviewWidthMode.Fit; state.background = PreviewBackground.Canvas; state.alignment = PreviewAlignment.Center }) { Text("Reset preview") }
}
