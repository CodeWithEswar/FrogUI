package io.github.codewitheswar.frogui.showcase.components.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.*
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.components.button.*
import io.github.codewitheswar.frogui.foundation.motion.FrogMotion
import io.github.codewitheswar.frogui.registry.FrogComponentRegistry
import io.github.codewitheswar.frogui.showcase.canvas.*
import io.github.codewitheswar.frogui.showcase.code.FrogCodeSnippet
import io.github.codewitheswar.frogui.showcase.colorpicker.*
import io.github.codewitheswar.frogui.showcase.drawer.FrogDrawer
import io.github.codewitheswar.frogui.showcase.icons.FrogIcons
import io.github.codewitheswar.frogui.showcase.inspector.*
import io.github.codewitheswar.frogui.showcase.markdown.*
import io.github.codewitheswar.frogui.showcase.style.*
import io.github.codewitheswar.frogui.theme.FrogTheme
import io.github.codewitheswar.frogui.theme.FrogThemeDefaults
import java.util.Locale

/** One committed model, one draft transaction, and one contextual drawer host. */
@Composable
internal fun ButtonScreen(componentId: String, onBack: () -> Unit, modifier: Modifier = Modifier) {
    val component = FrogComponentRegistry.findById(componentId)
    if (component == null) {
        Column(modifier.padding(20.dp)) { Text("Component not found"); FrogButton(onClick = onBack) { Text("Back to components") } }
        return
    }
    var state by rememberSaveable(stateSaver = ButtonDemoState.saver) { mutableStateOf(ButtonDemoState()) }
    var drawer by rememberSaveable(stateSaver = ButtonDrawerState.saver) { mutableStateOf(ButtonDrawerState()) }
    var draftEncoded by rememberSaveable { mutableStateOf<String?>(null) }
    var draftValid by rememberSaveable { mutableStateOf(true) }
    var editorRevision by rememberSaveable { mutableIntStateOf(0) }
    var tab by rememberSaveable { mutableIntStateOf(0) }
    val appDark = FrogTheme.colors.isDark
    var previewDark by rememberSaveable { mutableStateOf(appDark) }
    var widthMode by rememberSaveable { mutableStateOf(PreviewWidthMode.Fit) }
    var background by rememberSaveable { mutableStateOf(PreviewBackground.Canvas) }
    var alignment by rememberSaveable { mutableStateOf(PreviewAlignment.Center) }
    var activations by rememberSaveable { mutableIntStateOf(0) }
    val colors = FrogTheme.colors
    val previewTokens = if (previewDark) FrogThemeDefaults.darkColors() else FrogThemeDefaults.lightColors()
    val page = drawer.current
    val editing = (page as? ButtonDrawerPage.Color)?.property
    val draft = draftEncoded?.let(FrogColorValue::decode)
    val effective = if (editing != null) state.withColor(editing, draft) else state
    fun openColor(property: ButtonColorProperty) {
        draftEncoded = state.colorOverrides[property]?.encode()
        draftValid = true
        drawer = if (drawer.current == null) drawer.open(ButtonDrawerPage.Color(property)) else drawer.push(ButtonDrawerPage.Color(property))
    }
    val assets = LocalContext.current.assets
    val docs = remember(componentId, assets) {
        MarkdownParser().parse(runCatching { assets.open("components/$componentId.md").bufferedReader().use { it.readText() } }.getOrDefault(""))
    }
    BoxWithConstraints(modifier.fillMaxSize()) {
        val split = maxWidth >= 620.dp && LocalDensity.current.fontScale <= 1.3f
        val sideDrawer = maxWidth >= 620.dp
        Row(Modifier.fillMaxSize().padding(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Column(Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(vertical = 16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(component.displayName, style = FrogTheme.typography.titleLarge, color = colors.foreground, modifier = Modifier.semantics { heading() })
                    Text(component.description, style = FrogTheme.typography.bodySmall, color = colors.mutedForeground)
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        listOf(component.category.name, component.status.name, "Since ${component.since}").forEach { Text(it, style = FrogTheme.typography.label, color = colors.mutedForeground) }
                    }
                }
                ComponentPreviewCanvas(previewDark, { previewDark = !previewDark }, widthMode, { widthMode = it },
                    { state = ButtonDemoState(); activations = 0 }, background = background, alignment = alignment,
                    onConfigure = { drawer = drawer.open(ButtonDrawerPage.Preview) }) {
                    ButtonLivePreview(effective, { activations++ }, Modifier.testTag("live-button"))
                }
                if (!split) ButtonQuickControls(state, { state = it })
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Activations: $activations", Modifier.weight(1f).semantics { liveRegion = LiveRegionMode.Polite }, style = FrogTheme.typography.bodySmall, color = colors.mutedForeground)
                    if (!split) FrogButton(onClick = { drawer = drawer.open(ButtonDrawerPage.Properties) }, variant = FrogButtonVariant.Outline, size = FrogButtonSize.Small,
                        leadingIcon = { Icon(FrogIcons.Sliders, null, Modifier.size(16.dp)) }) { Text("Customize") }
                }
                FrogShowcaseTabs(listOf("Preview", "Code", "API", "Docs", "Accessibility"), tab, { tab = it })
                when (tab) {
                    0 -> {
                        Text("Examples", style = FrogTheme.typography.heading, color = colors.foreground)
                        ButtonExampleGallery(component.examples)
                        ButtonStateGallery()
                    }
                    1 -> {
                        FrogCodeSnippet(effective.toCodeSnippet(), filename = "FrogButton.kt")
                        if (effective.hasLeadingIcon || effective.hasTrailingIcon) Text("FrogIcons belongs to this showcase. Supply your own icons through the component slots.", style = FrogTheme.typography.bodySmall, color = colors.mutedForeground)
                    }
                    2 -> ButtonApiReference(component.properties) { drawer = drawer.open(ButtonDrawerPage.Api(it)) }
                    3 -> FrogMarkdownDocument(docs)
                    4 -> FrogMarkdownDocument(docs.section("Accessibility"))
                }
            }
            if (split) {
                VerticalDivider(color = colors.border)
                Column(Modifier.width(264.dp).fillMaxHeight().verticalScroll(rememberScrollState()).padding(vertical = 16.dp).testTag("persistent-inspector")) {
                    PropertyInspector(state, { state = it }, onColor = ::openColor, tokenColors = previewTokens)
                }
            }
        }
        val title = when (page) {
            ButtonDrawerPage.Properties -> "Customize"
            ButtonDrawerPage.Preview -> "Preview settings"
            is ButtonDrawerPage.Color -> page.property.label
            is ButtonDrawerPage.Api -> "API property"
            null -> "Inspector"
        }
        FrogDrawer(page != null, { drawer = ButtonDrawerState() }, title, subtitle = when (page) {
            is ButtonDrawerPage.Color -> "Button · draft preview"
            is ButtonDrawerPage.Api -> page.property
            else -> "FrogButton"
        }, side = sideDrawer, onBack = if (drawer.pages.size > 1) ({ drawer = drawer.back() }) else null,
            preview = if (page == ButtonDrawerPage.Properties || editing != null) ({
                FrogTheme(darkTheme = previewDark, motion = if (LocalFrogMotionEnabled.current) FrogMotion() else FrogMotion(0, 0, 0)) {
                    val canvas = when (background) { PreviewBackground.Light -> Color.White; PreviewBackground.Dark -> Color(0xFF09090B); else -> FrogTheme.colors.background }
                    val previewState = if (editing != null) effective.copy(enabled = editing !in setOf(ButtonColorProperty.DisabledContainer, ButtonColorProperty.DisabledContent, ButtonColorProperty.DisabledBorder), loading = false) else effective
                    Column(Modifier.fillMaxWidth().background(canvas).padding(horizontal = 18.dp, vertical = 10.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(if (editing != null && !previewState.enabled) "Disabled preview" else "Live preview", style = FrogTheme.typography.label, color = FrogTheme.colors.mutedForeground)
                        Box(Modifier.fillMaxWidth().heightIn(min = 60.dp), contentAlignment = Alignment.Center) {
                            ButtonLivePreview(previewState, { activations++ }, Modifier.testTag("drawer-live-button"))
                        }
                        if (editing != null) {
                            val resolved = previewState.resolvedColors()
                            val contrast = colorContrast(if (previewState.enabled) resolved.contentColor else resolved.disabledContentColor,
                                if (previewState.enabled) resolved.containerColor else resolved.disabledContainerColor, canvas)
                            Text(String.format(Locale.ROOT, "Contrast %.2f:1 · %s", contrast, if (contrast >= 4.5) "AA normal text threshold met" else "Low for normal text") +
                                if (background == PreviewBackground.Transparent) " (theme canvas)" else "",
                                style = FrogTheme.typography.bodySmall, color = FrogTheme.colors.mutedForeground)
                        }
                    }
                }
            }) else null,
            actions = if (editing != null) ({
                TextButton(onClick = { draftEncoded = null; draftValid = true; editorRevision++ }) { Text("Reset") }
                Spacer(Modifier.weight(1f))
                TextButton(onClick = { drawer = drawer.back() }) { Text("Cancel") }
                FrogButton(onClick = { state = state.withColor(editing, draft); drawer = drawer.back() }, enabled = draftValid, size = FrogButtonSize.Small) { Text("Apply") }
            }) else null,
        ) {
            when (page) {
                ButtonDrawerPage.Properties -> PropertyInspector(state, { state = it }, onColor = ::openColor, tokenColors = previewTokens)
                ButtonDrawerPage.Preview -> {
                    FrogEnumSelector("Theme", listOf("Light", "Dark"), if (previewDark) "Dark" else "Light") { previewDark = it == "Dark" }
                    FrogEnumSelector("Width", PreviewWidthMode.entries.map { it.label }, widthMode.label) { label -> widthMode = PreviewWidthMode.entries.first { it.label == label } }
                    FrogEnumSelector("Background", PreviewBackground.entries.map { it.name }, background.name) { background = PreviewBackground.valueOf(it) }
                    FrogEnumSelector("Alignment", PreviewAlignment.entries.map { it.name }, alignment.name) { alignment = PreviewAlignment.valueOf(it) }
                    Text("Preview settings are independent of the app theme and exported button code.", style = FrogTheme.typography.bodySmall, color = colors.mutedForeground)
                    TextButton(onClick = { previewDark = appDark; widthMode = PreviewWidthMode.Fit; background = PreviewBackground.Canvas; alignment = PreviewAlignment.Center }) { Text("Reset preview") }
                }
                is ButtonDrawerPage.Color -> {
                    val value = effective.colorValue(page.property)
                    val previous = state.colorValue(page.property)
                    Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) { Text("Current", color = colors.mutedForeground); ColorSwatch(value.resolve(previewTokens), Modifier.size(40.dp)) }
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) { Text("Previous", color = colors.mutedForeground); ColorSwatch(previous.resolve(previewTokens), Modifier.size(40.dp)) }
                    }
                    key(page.property, editorRevision) { FrogColorPicker(value, { draftEncoded = it.encode() }, tokenColors = previewTokens, onValidityChange = { draftValid = it }) }
                }
                is ButtonDrawerPage.Api -> component.properties.firstOrNull { it.name == page.property }?.let { ButtonApiPropertyContent(buttonApiProperty(it)) }
                null -> Unit
            }
        }
    }
}

@Composable
internal fun ButtonLivePreview(state: ButtonDemoState, onClick: () -> Unit, modifier: Modifier = Modifier) {
    FrogButton(onClick = onClick, variant = state.variant, size = state.size, enabled = state.enabled, loading = state.loading,
        modifier = modifier, fullWidth = state.fullWidth, colors = state.resolvedColors(),
        shape = when (state.shape) { ButtonShape.Default -> FrogButtonDefaults.shape(state.size); ButtonShape.Square -> RectangleShape; ButtonShape.Pill -> RoundedCornerShape(percent = 50) },
        leadingIcon = if (state.hasLeadingIcon) ({ Icon(FrogIcons.Play, null, Modifier.size(state.size.iconSize)) }) else null,
        trailingIcon = if (state.hasTrailingIcon) ({ Icon(FrogIcons.Forward, null, Modifier.size(state.size.iconSize)) }) else null,
    ) { Text(state.buttonText) }
}
