package io.github.codewitheswar.frogui.showcase.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.components.button.*
import io.github.codewitheswar.frogui.components.overlays.FrogOverlayHost
import io.github.codewitheswar.frogui.components.overlays.drawer.FrogDrawer
import io.github.codewitheswar.frogui.components.overlays.drawer.FrogDrawerPresentation
import io.github.codewitheswar.frogui.showcase.colorpicker.*
import io.github.codewitheswar.frogui.showcase.code.FrogCodeBlock
import io.github.codewitheswar.frogui.showcase.icons.FrogIcons
import io.github.codewitheswar.frogui.showcase.inspector.FrogEnumSelector
import io.github.codewitheswar.frogui.showcase.style.FrogShowcaseTabs
import io.github.codewitheswar.frogui.theme.FrogTheme
import io.github.codewitheswar.frogui.theme.FrogThemeDefaults
import io.github.codewitheswar.frogui.theme.ProvideFrogThemeEnvironment

/** The live token explorer: all values come from the current runtime or canonical defaults. */
@Composable
fun FoundationScreen(modifier: Modifier = Modifier) {
    val pages = listOf("Colors", "Typography", "Spacing", "Shapes", "Elevation", "Motion", "Sizing", "Adaptive")
    var page by rememberSaveable { mutableIntStateOf(0) }
    Column(modifier.fillMaxSize().testTag("foundation-explorer")) {
        FrogShowcaseTabs(pages, page, { page = it }, Modifier.fillMaxWidth())
        key(page) {
            Column(Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(FrogTheme.spacing.xl),
                verticalArrangement = Arrangement.spacedBy(FrogTheme.spacing.xl)) {
                Text(pages[page], style = FrogTheme.typography.titleLarge, modifier = Modifier.semantics { heading() })
                when (page) {
                    0 -> FoundationColors()
                    1 -> FoundationTypography()
                    2 -> FoundationSpacing()
                    3 -> FoundationShapes()
                    4 -> FoundationElevation()
                    5 -> FoundationMotion()
                    6 -> FoundationSizing()
                    7 -> FoundationAdaptive()
                }
            }
        }
    }
}

@Composable
private fun Note(text: String) { Text(text, style = FrogTheme.typography.bodySmall, color = FrogTheme.colors.mutedForeground) }

@Composable
private fun TokenPanel(content: @Composable ColumnScope.() -> Unit) {
    Column(Modifier.fillMaxWidth().background(FrogTheme.colors.surfaceElevated, FrogTheme.shapes.md)
        .border(1.dp, FrogTheme.colors.border, FrogTheme.shapes.md).padding(FrogTheme.spacing.lg),
        verticalArrangement = Arrangement.spacedBy(FrogTheme.spacing.md), content = content)
}

@Composable
private fun FoundationColors() {
    Note("Semantic roles follow the local theme. Borders describe structure; focusRing identifies focus. Custom colors still need contrast review.")
    FrogCodeBlock("FrogTheme(darkTheme = false) {\n    FrogButton(onClick = {}) { Text(\"Save\") }\n}", filename = "Theme usage")
    Note("For custom colors, pass FrogThemeDefaults.lightColors().copy(...). Nested themes inherit other token groups. Sizing, adaptive policy and reduced motion are scoped with ProvideFrogThemeEnvironment.")
    val current = FrogTheme.colors
    val light = FrogThemeDefaults.lightColors()
    val dark = FrogThemeDefaults.darkColors()
    FrogColorToken.entries.forEach { token ->
        TokenPanel {
            Text(token.member, style = FrogTheme.typography.code)
            Note(token.purpose)
            FlowRow(horizontalArrangement = Arrangement.spacedBy(FrogTheme.spacing.lg), verticalArrangement = Arrangement.spacedBy(FrogTheme.spacing.md)) {
                NamedSwatch("Current", token.resolve(current))
                NamedSwatch("Light", token.resolve(light))
                NamedSwatch("Dark", token.resolve(dark))
            }
        }
    }
}

@Composable
private fun NamedSwatch(label: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(FrogTheme.spacing.sm)) {
        ColorSwatch(color)
        Column { Text(label, style = FrogTheme.typography.caption); Note(color.hex()) }
    }
}

@Composable
private fun FoundationTypography() {
    Note("System fonts scale with Android text settings. Line heights use sp too; labels can grow instead of being clipped to a fixed text height.")
    val t = FrogTheme.typography
    listOf("display" to t.display, "titleLarge" to t.titleLarge, "title" to t.title,
        "heading" to t.heading, "subheading" to t.subheading, "body" to t.body,
        "bodySmall" to t.bodySmall, "label" to t.label, "caption" to t.caption, "code" to t.code).forEach { (name, style) ->
        TokenPanel {
            Text(if (name == "code") "FrogTheme { AppContent() }" else "Build something useful", style = style)
            Note("$name · ${style.fontSize} / ${style.lineHeight} · weight ${style.fontWeight?.weight ?: 400}")
        }
    }
}

@Composable
private fun FoundationSpacing() {
    Note("One existing xxs–x7l scale for shared rhythm. Component-specific padding stays in component Defaults.")
    val s = FrogTheme.spacing
    listOf("xxs" to s.xxs, "xs" to s.xs, "sm" to s.sm, "md" to s.md, "lg" to s.lg,
        "xl" to s.xl, "xxl" to s.xxl, "xxxl" to s.xxxl, "x4l" to s.x4l, "x5l" to s.x5l,
        "x6l" to s.x6l, "x7l" to s.x7l).forEach { (name, value) ->
        TokenPanel {
            Text("$name · $value", style = FrogTheme.typography.code)
            Box(Modifier.width(value).height(12.dp).background(FrogTheme.colors.primary, FrogTheme.shapes.xs))
        }
    }
}

@Composable
private fun FoundationShapes() {
    Note("Actual local shapes: compact controls, containers and overlays use restrained corners. Full is reserved for circular or pill-shaped content.")
    val s = FrogTheme.shapes
    FlowRow(horizontalArrangement = Arrangement.spacedBy(FrogTheme.spacing.lg), verticalArrangement = Arrangement.spacedBy(FrogTheme.spacing.lg)) {
        listOf("xs" to s.xs, "sm" to s.sm, "md" to s.md, "lg" to s.lg, "xl" to s.xl, "full" to s.full).forEach { (name, shape) ->
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(FrogTheme.spacing.md)) {
                Box(Modifier.size(84.dp).background(FrogTheme.colors.muted, shape).border(1.dp, FrogTheme.colors.borderStrong, shape))
                Text(name, style = FrogTheme.typography.code)
            }
        }
    }
}

@Composable
private fun FoundationElevation() {
    Note("Depth uses tonal separation and borders as well as shadow. The same four local elevation values are shown in both palettes.")
    listOf(false, true).forEach { dark ->
        FrogTheme(darkTheme = dark) {
            TokenPanel {
                Text(if (dark) "Dark" else "Light", color = FrogTheme.colors.foreground, style = FrogTheme.typography.heading)
                val e = FrogTheme.elevation
                FlowRow(horizontalArrangement = Arrangement.spacedBy(FrogTheme.spacing.lg), verticalArrangement = Arrangement.spacedBy(FrogTheme.spacing.lg)) {
                    listOf("none" to e.none, "low" to e.low, "medium" to e.medium, "high" to e.high).forEach { (name, elevation) ->
                        Column(Modifier.width(100.dp).heightIn(min = 80.dp).shadow(elevation, FrogTheme.shapes.md)
                            .background(FrogTheme.colors.surface, FrogTheme.shapes.md).border(1.dp, FrogTheme.colors.border, FrogTheme.shapes.md)
                            .padding(FrogTheme.spacing.md), verticalArrangement = Arrangement.Center) {
                            Text(name, style = FrogTheme.typography.code)
                            Note("$elevation")
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FoundationMotion() {
    var target by rememberSaveable { mutableStateOf(false) }
    var reduce by rememberSaveable { mutableStateOf(false) }
    Note("One transition per activation. Reduced motion removes animated movement and freezes decorative loading while preserving action state.")
    FrogButton(onClick = { target = !target }, size = FrogButtonSize.Small) { Text("Run transition") }
    FrogButton(onClick = { reduce = !reduce }, variant = FrogButtonVariant.Outline, size = FrogButtonSize.Small) {
        Text("Reduce motion: ${if (reduce) "on" else "off"}")
    }
    ProvideFrogThemeEnvironment(reduceMotion = reduce || FrogTheme.reduceMotion) {
        val m = FrogTheme.motion
        Note(if (FrogTheme.reduceMotion) "Effective preference: reduced" else "Effective preference: full motion")
        listOf("fast" to m.fastDurationMillis, "normal" to m.normalDurationMillis, "large" to m.largeDurationMillis).forEach { (name, duration) ->
            val amount by animateFloatAsState(if (target) 1f else .2f, tween(duration, easing = m.standardEasing), label = "$name sample")
            TokenPanel {
                Text("$name · $duration ms", style = FrogTheme.typography.code)
                Box(Modifier.fillMaxWidth().height(12.dp).background(FrogTheme.colors.muted, FrogTheme.shapes.full)) {
                    Box(Modifier.fillMaxWidth(amount).fillMaxHeight().background(FrogTheme.colors.primary, FrogTheme.shapes.full))
                }
            }
        }
        FrogButton(onClick = {}, loading = true, size = FrogButtonSize.Small) { Text("Saving changes") }
    }
}

@Composable
private fun FoundationSizing() {
    val s = FrogTheme.sizing
    Note("Visual size and interaction size are separate. The outline below shows the reserved target; the compact button sits inside it.")
    FlowRow(horizontalArrangement = Arrangement.spacedBy(FrogTheme.spacing.xl), verticalArrangement = Arrangement.spacedBy(FrogTheme.spacing.lg)) {
        listOf("iconSmall" to s.iconSmall, "iconMedium" to s.iconMedium, "iconLarge" to s.iconLarge).forEach { (name, size) ->
            Column(verticalArrangement = Arrangement.spacedBy(FrogTheme.spacing.sm)) {
                Icon(FrogIcons.Check, null, Modifier.size(size))
                Note("$name · $size")
            }
        }
    }
    FrogButtonSize.entries.forEach { size ->
        TokenPanel {
            Note("$size · visual minimum ${FrogButtonDefaults.controlHeight(size)}")
            FrogButton(onClick = {}, size = size, variant = FrogButtonVariant.Secondary) { Text("Save changes") }
        }
    }
    Box(Modifier.border(1.dp, FrogTheme.colors.focusRing, FrogTheme.shapes.sm)) {
        FrogIconButton({}, "Sizing sample", size = FrogButtonSize.Small, variant = FrogButtonVariant.Secondary) {
            Icon(FrogIcons.Check, null, Modifier.size(s.iconSmall))
        }
    }
    Note("minimumTouchTarget · ${s.minimumTouchTarget} in both dimensions. Text scaling can increase a control's height.")
}

@Composable
private fun FoundationAdaptive() {
    val policy = FrogTheme.adaptive
    Note("Classify available Compose width. Buttons keep their size; navigation, inspectors and Auto Drawer change composition.")
    TokenPanel {
        Note("Compact: below ${policy.mediumMinWidth} · bottom navigation and stacked content.")
        Note("Medium: ${policy.mediumMinWidth} to below ${policy.expandedMinWidth} · rail and contextual side panels.")
        Note("Expanded: ${policy.expandedMinWidth} and above · sidebar and multiple panes when content space permits.")
    }
    var requested by rememberSaveable { mutableStateOf("Compact") }
    var open by rememberSaveable { mutableStateOf(true) }
    FrogEnumSelector("Requested preview", listOf("Compact", "Medium", "Expanded"), requested) { requested = it }
    val requestedWidth = when (requested) { "Medium" -> policy.mediumMinWidth; "Expanded" -> policy.expandedMinWidth; else -> 360.dp }
    BoxWithConstraints(Modifier.fillMaxWidth()) {
        val width = requestedWidth.coerceAtMost(maxWidth)
        Column(verticalArrangement = Arrangement.spacedBy(FrogTheme.spacing.md)) {
            Note("Actual host: ${width.value.toInt()} dp · ${policy.windowSizeClass(width)}. Requests are clamped to available space.")
            FrogButton({ open = true }, size = FrogButtonSize.Small, enabled = !open) { Text("Open Auto Drawer") }
            FrogOverlayHost(Modifier.width(width).height(300.dp).background(FrogTheme.colors.surface)
                .border(1.dp, FrogTheme.colors.border, FrogTheme.shapes.md)) {
                FrogDrawer(visible = open, onDismissRequest = { open = false }, presentation = FrogDrawerPresentation.Auto, title = "Adaptive preview") {
                    Text("This is the real Auto Drawer, resolved against this bounded host.", style = FrogTheme.typography.body)
                }
            }
        }
    }
}
