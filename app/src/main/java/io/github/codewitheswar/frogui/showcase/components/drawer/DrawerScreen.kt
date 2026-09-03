package io.github.codewitheswar.frogui.showcase.components.drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.components.button.FrogButton
import io.github.codewitheswar.frogui.components.button.FrogButtonSize
import io.github.codewitheswar.frogui.components.button.FrogButtonVariant
import io.github.codewitheswar.frogui.components.overlays.drawer.FrogDrawer
import io.github.codewitheswar.frogui.components.overlays.drawer.FrogDrawerPresentation
import io.github.codewitheswar.frogui.components.overlays.drawer.FrogDrawerSide
import io.github.codewitheswar.frogui.components.overlays.drawer.rememberFrogDrawerState
import io.github.codewitheswar.frogui.registry.FrogComponentRegistry
import io.github.codewitheswar.frogui.showcase.icons.FrogIcons
import io.github.codewitheswar.frogui.showcase.style.*
import io.github.codewitheswar.frogui.theme.FrogTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun DrawerScreen(
    componentId: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val component = FrogComponentRegistry.findById(componentId)
    val colors = FrogTheme.colors

    if (component == null) {
        Column(modifier.padding(20.dp)) {
            Text("Component not found", color = colors.foreground, style = FrogTheme.typography.heading)
            FrogButton(onClick = onBack) { Text("Back to components") }
        }
        return
    }

    var presentation by rememberSaveable { mutableStateOf(FrogDrawerPresentation.Auto) }
    var side by rememberSaveable { mutableStateOf(FrogDrawerSide.End) }
    var title by rememberSaveable { mutableStateOf("Component Settings") }
    var subtitle by rememberSaveable { mutableStateOf("Customize appearance and behaviors") }
    var showSubtitle by rememberSaveable { mutableStateOf(true) }
    var showFooter by rememberSaveable { mutableStateOf(true) }
    var longContent by rememberSaveable { mutableStateOf(false) }

    val drawerState = rememberFrogDrawerState()
    val scope = rememberCoroutineScope()

    var tab by rememberSaveable { mutableIntStateOf(0) }
    val appDark = FrogTheme.colors.isDark
    var previewDark by rememberSaveable { mutableStateOf(appDark) }

    val clipboard = LocalClipboardManager.current

    BoxWithConstraints(modifier.fillMaxSize()) {
        val isWide = maxWidth >= 620.dp && LocalDensity.current.fontScale <= 1.3f

        Row(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header section
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        component.displayName,
                        style = FrogTheme.typography.titleLarge,
                        color = colors.foreground,
                        modifier = Modifier.semantics { heading() }
                    )
                    Text(
                        component.description,
                        style = FrogTheme.typography.bodySmall,
                        color = colors.mutedForeground
                    )
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        listOf(
                            component.category.name,
                            component.status.name,
                            "Since ${component.since}"
                        ).forEach {
                            Text(it, style = FrogTheme.typography.label, color = colors.mutedForeground)
                        }
                    }
                }

                // Interactive Workbench Card
                Column(
                    Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(colors.surface)
                        .border(1.dp, colors.border, RoundedCornerShape(12.dp))
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Interactive Workbench",
                            style = FrogTheme.typography.heading,
                            color = colors.foreground
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            FrogButton(
                                onClick = { scope.launch { drawerState.open() } },
                                variant = FrogButtonVariant.Primary,
                                size = FrogButtonSize.Small
                            ) {
                                Text("Open Drawer")
                            }
                        }
                    }

                    Text(
                        "Click 'Open Drawer' to test the current configuration in real-time. On compact layouts, it presents as a bottom sheet; on expanded layouts, as a side inspector.",
                        style = FrogTheme.typography.bodySmall,
                        color = colors.mutedForeground
                    )

                    // Quick Configuration Chips
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Presentation Mode", style = FrogTheme.typography.label, color = colors.mutedForeground)
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            FrogDrawerPresentation.entries.forEach { mode ->
                                val selected = presentation == mode
                                Box(
                                    Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(if (selected) colors.primary else colors.surfaceElevated)
                                        .border(1.dp, if (selected) colors.primary else colors.border, RoundedCornerShape(6.dp))
                                        .clickable { presentation = mode }
                                        .padding(horizontal = 10.dp, vertical = 6.dp)
                                ) {
                                    Text(
                                        mode.name,
                                        style = FrogTheme.typography.bodySmall,
                                        color = if (selected) colors.primaryForeground else colors.foreground
                                    )
                                }
                            }
                        }
                    }

                    if (presentation == FrogDrawerPresentation.Side) {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text("Side Placement", style = FrogTheme.typography.label, color = colors.mutedForeground)
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                FrogDrawerSide.entries.forEach { s ->
                                    val selected = side == s
                                    Box(
                                        Modifier
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(if (selected) colors.primary else colors.surfaceElevated)
                                            .border(1.dp, if (selected) colors.primary else colors.border, RoundedCornerShape(6.dp))
                                            .clickable { side = s }
                                            .padding(horizontal = 10.dp, vertical = 6.dp)
                                    ) {
                                        Text(
                                            s.name,
                                            style = FrogTheme.typography.bodySmall,
                                            color = if (selected) colors.primaryForeground else colors.foreground
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Toggles
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier.clickable { showSubtitle = !showSubtitle }
                        ) {
                            Checkbox(checked = showSubtitle, onCheckedChange = { showSubtitle = it })
                            Text("Subtitle", style = FrogTheme.typography.bodySmall, color = colors.foreground)
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier.clickable { showFooter = !showFooter }
                        ) {
                            Checkbox(checked = showFooter, onCheckedChange = { showFooter = it })
                            Text("Action Footer", style = FrogTheme.typography.bodySmall, color = colors.foreground)
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier.clickable { longContent = !longContent }
                        ) {
                            Checkbox(checked = longContent, onCheckedChange = { longContent = it })
                            Text("Long Content", style = FrogTheme.typography.bodySmall, color = colors.foreground)
                        }
                    }
                }

                // Section Tabs
                FrogShowcaseTabs(
                    listOf("Preview", "Code", "API", "Docs", "Accessibility"),
                    tab,
                    { tab = it }
                )

                when (tab) {
                    0 -> {
                        Text("Examples Gallery", style = FrogTheme.typography.heading, color = colors.foreground)
                        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                            DrawerExampleCard("Basic Drawer", "Simple open and close with hoisted state.") {
                                DrawerBasicExample()
                            }
                            DrawerExampleCard("Bottom Presentation", "Compact modal bottom sheet with drag handle.") {
                                DrawerBottomExample()
                            }
                            DrawerExampleCard("Side Inspector", "Contextual end-docked tool panel.") {
                                DrawerSideExample()
                            }
                            DrawerExampleCard("Header Actions", "Header with navigation and secondary reset action.") {
                                DrawerHeaderExample()
                            }
                            DrawerExampleCard("Sticky Footer", "Sticky Cancel and Apply buttons below scroll body.") {
                                DrawerFooterExample()
                            }
                            DrawerExampleCard("Scrollable Content", "Long form content testing scroll containment.") {
                                DrawerScrollExample()
                            }
                        }
                    }
                    1 -> {
                        Text("Generated Usage Code", style = FrogTheme.typography.heading, color = colors.foreground)
                        val generatedCode = buildString {
                            appendLine("val drawerState = rememberFrogDrawerState()")
                            appendLine("val scope = rememberCoroutineScope()")
                            appendLine()
                            appendLine("FrogButton(")
                            appendLine("    onClick = { scope.launch { drawerState.open() } }")
                            appendLine(") {")
                            appendLine("    Text(\"Open Drawer\")")
                            appendLine("}")
                            appendLine()
                            appendLine("FrogDrawer(")
                            appendLine("    state = drawerState,")
                            appendLine("    onDismissRequest = { scope.launch { drawerState.close() } },")
                            if (presentation != FrogDrawerPresentation.Auto) {
                                appendLine("    presentation = FrogDrawerPresentation.$presentation,")
                            }
                            if (presentation == FrogDrawerPresentation.Side && side != FrogDrawerSide.End) {
                                appendLine("    side = FrogDrawerSide.$side,")
                            }
                            appendLine("    title = \"$title\",")
                            if (showSubtitle) {
                                appendLine("    subtitle = \"$subtitle\",")
                            }
                            if (showFooter) {
                                appendLine("    footer = {")
                                appendLine("        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {")
                                appendLine("            FrogButton(onClick = { scope.launch { drawerState.close() } }, modifier = Modifier.weight(1f), variant = FrogButtonVariant.Secondary) { Text(\"Cancel\") }")
                                appendLine("            FrogButton(onClick = { scope.launch { drawerState.close() } }, modifier = Modifier.weight(1f), variant = FrogButtonVariant.Primary) { Text(\"Apply\") }")
                                appendLine("        }")
                                appendLine("    }")
                            }
                            appendLine(") {")
                            appendLine("    Text(\"Drawer content goes here.\")")
                            appendLine("}")
                        }

                        Column(
                            Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(colors.surfaceElevated)
                                .border(1.dp, colors.border, RoundedCornerShape(8.dp))
                                .padding(14.dp)
                        ) {
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Kotlin (Jetpack Compose)", style = FrogTheme.typography.label, color = colors.mutedForeground)
                                FrogButton(
                                    onClick = { clipboard.setText(AnnotatedString(generatedCode)) },
                                    size = FrogButtonSize.Small,
                                    variant = FrogButtonVariant.Ghost
                                ) {
                                    Text("Copy")
                                }
                            }
                            Spacer(Modifier.height(8.dp))
                            Text(generatedCode, style = FrogTheme.typography.bodySmall, color = colors.foreground)
                        }
                    }
                    2 -> {
                        Text("API Reference", style = FrogTheme.typography.heading, color = colors.foreground)
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            component.properties.forEach { prop ->
                                Column(
                                    Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(colors.surfaceElevated)
                                        .border(1.dp, colors.border, RoundedCornerShape(8.dp))
                                        .padding(12.dp),
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Row(
                                        Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(prop.name, style = FrogTheme.typography.heading, color = colors.foreground)
                                        Text(prop.type, style = FrogTheme.typography.label, color = colors.mutedForeground)
                                    }
                                    Text(prop.description, style = FrogTheme.typography.bodySmall, color = colors.mutedForeground)
                                    Text("Default: ${prop.defaultValue}", style = FrogTheme.typography.label, color = colors.foreground)
                                }
                            }
                        }
                    }
                    3 -> {
                        Text("Documentation & Architecture", style = FrogTheme.typography.heading, color = colors.foreground)
                        Text(
                            "FrogDrawer provides contextual overlays for secondary tasks. On compact layouts, it anchors to the bottom edge with a drag handle; on wide screens, it docks to the side edge as a fixed-width inspector. It exposes full focus containment and dismiss semantics.",
                            style = FrogTheme.typography.body,
                            color = colors.foreground
                        )
                    }
                    4 -> {
                        Text("Accessibility & Semantics", style = FrogTheme.typography.heading, color = colors.foreground)
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Text("• Pane Semantics: Declares paneTitle = title for TalkBack navigation.", style = FrogTheme.typography.bodySmall, color = colors.foreground)
                            Text("• Dismiss Action: Provides semantic dismiss action for assistive navigation.", style = FrogTheme.typography.bodySmall, color = colors.foreground)
                            Text("• Focus Restoration: Focus enters the close/first control and restores on dismiss.", style = FrogTheme.typography.bodySmall, color = colors.foreground)
                            Text("• Minimum Touch Targets: 48dp dismiss and navigation touch targets.", style = FrogTheme.typography.bodySmall, color = colors.foreground)
                        }
                    }
                }
            }
        }
    }

    // Live FrogDrawer instance for Workbench
    FrogDrawer(
        state = drawerState,
        onDismissRequest = { scope.launch { drawerState.close() } },
        presentation = presentation,
        side = side,
        title = title,
        subtitle = if (showSubtitle) subtitle else null,
        footer = if (showFooter) ({
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FrogButton(
                    onClick = { scope.launch { drawerState.close() } },
                    modifier = Modifier.weight(1f),
                    variant = FrogButtonVariant.Secondary
                ) {
                    Text("Cancel")
                }
                FrogButton(
                    onClick = { scope.launch { drawerState.close() } },
                    modifier = Modifier.weight(1f),
                    variant = FrogButtonVariant.Primary
                ) {
                    Text("Apply")
                }
            }
        }) else null
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(
                "This is the live FrogDrawer content area. It is independently scrollable and maintains fixed header and footer slots.",
                style = FrogTheme.typography.body,
                color = colors.foreground
            )

            if (longContent) {
                repeat(12) { index ->
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(colors.surfaceElevated)
                            .padding(12.dp)
                    ) {
                        Text("Config Item #${index + 1}", style = FrogTheme.typography.heading, color = colors.foreground)
                        Text("Secondary parameter options and description.", style = FrogTheme.typography.bodySmall, color = colors.mutedForeground)
                    }
                }
            }
        }
    }
}

@Composable
private fun DrawerExampleCard(
    title: String,
    description: String,
    content: @Composable () -> Unit
) {
    val colors = FrogTheme.colors
    Column(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(colors.surface)
            .border(1.dp, colors.border, RoundedCornerShape(10.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(title, style = FrogTheme.typography.heading, color = colors.foreground)
        Text(description, style = FrogTheme.typography.bodySmall, color = colors.mutedForeground)
        content()
    }
}
