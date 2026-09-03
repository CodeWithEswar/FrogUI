package io.github.codewitheswar.frogui.showcase.components.drawer

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.components.button.FrogButton
import io.github.codewitheswar.frogui.components.button.FrogButtonVariant
import io.github.codewitheswar.frogui.components.overlays.drawer.FrogDrawer
import io.github.codewitheswar.frogui.components.overlays.drawer.FrogDrawerPresentation
import io.github.codewitheswar.frogui.components.overlays.drawer.FrogDrawerSide
import io.github.codewitheswar.frogui.components.overlays.drawer.rememberFrogDrawerState
import io.github.codewitheswar.frogui.theme.FrogTheme
import kotlinx.coroutines.launch

// example:basic:start
@Composable
internal fun DrawerBasicExample(modifier: Modifier = Modifier) {
    val drawerState = rememberFrogDrawerState()
    val scope = rememberCoroutineScope()

    FrogButton(
        onClick = { scope.launch { drawerState.open() } },
        modifier = modifier
    ) {
        Text("Open Drawer")
    }

    FrogDrawer(
        state = drawerState,
        onDismissRequest = { scope.launch { drawerState.close() } },
        title = "Settings"
    ) {
        Text(
            "Configure application preferences and appearance settings.",
            style = FrogTheme.typography.body,
            color = FrogTheme.colors.foreground
        )
    }
}
// example:basic:end

// example:bottom:start
@Composable
internal fun DrawerBottomExample(modifier: Modifier = Modifier) {
    val drawerState = rememberFrogDrawerState()
    val scope = rememberCoroutineScope()

    FrogButton(
        onClick = { scope.launch { drawerState.open() } },
        modifier = modifier
    ) {
        Text("Open Bottom Sheet")
    }

    FrogDrawer(
        state = drawerState,
        onDismissRequest = { scope.launch { drawerState.close() } },
        presentation = FrogDrawerPresentation.Bottom,
        title = "Share Options",
        subtitle = "Select destination"
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text("Copy direct link", style = FrogTheme.typography.body, color = FrogTheme.colors.foreground)
            Text("Export as JSON", style = FrogTheme.typography.body, color = FrogTheme.colors.foreground)
            Text("Send to device", style = FrogTheme.typography.body, color = FrogTheme.colors.foreground)
        }
    }
}
// example:bottom:end

// example:side:start
@Composable
internal fun DrawerSideExample(modifier: Modifier = Modifier) {
    val drawerState = rememberFrogDrawerState()
    val scope = rememberCoroutineScope()

    FrogButton(
        onClick = { scope.launch { drawerState.open() } },
        modifier = modifier
    ) {
        Text("Open Side Inspector")
    }

    FrogDrawer(
        state = drawerState,
        onDismissRequest = { scope.launch { drawerState.close() } },
        presentation = FrogDrawerPresentation.Side,
        side = FrogDrawerSide.End,
        title = "Component Properties",
        subtitle = "Interactive configuration"
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text("Variant: Primary", style = FrogTheme.typography.body, color = FrogTheme.colors.foreground)
            Text("Size: Medium (40dp)", style = FrogTheme.typography.body, color = FrogTheme.colors.foreground)
            Text("Touch target: 48dp minimum", style = FrogTheme.typography.bodySmall, color = FrogTheme.colors.mutedForeground)
        }
    }
}
// example:side:end

// example:header:start
@Composable
internal fun DrawerHeaderExample(modifier: Modifier = Modifier) {
    val drawerState = rememberFrogDrawerState()
    val scope = rememberCoroutineScope()

    FrogButton(
        onClick = { scope.launch { drawerState.open() } },
        modifier = modifier
    ) {
        Text("Drawer With Custom Header")
    }

    FrogDrawer(
        state = drawerState,
        onDismissRequest = { scope.launch { drawerState.close() } },
        title = "Filter Components",
        subtitle = "3 filters applied",
        actions = {
            FrogButton(
                onClick = {},
                variant = FrogButtonVariant.Ghost
            ) {
                Text("Reset")
            }
        }
    ) {
        Text(
            "Filter items by category, capability, or status.",
            style = FrogTheme.typography.body,
            color = FrogTheme.colors.foreground
        )
    }
}
// example:header:end

// example:footer:start
@Composable
internal fun DrawerFooterExample(modifier: Modifier = Modifier) {
    val drawerState = rememberFrogDrawerState()
    val scope = rememberCoroutineScope()

    FrogButton(
        onClick = { scope.launch { drawerState.open() } },
        modifier = modifier
    ) {
        Text("Drawer With Action Footer")
    }

    FrogDrawer(
        state = drawerState,
        onDismissRequest = { scope.launch { drawerState.close() } },
        title = "Confirm Changes",
        footer = {
            Row(
                modifier = Modifier.fillMaxWidth(),
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
        }
    ) {
        Text(
            "Review your configuration before saving changes to the project.",
            style = FrogTheme.typography.body,
            color = FrogTheme.colors.foreground
        )
    }
}
// example:footer:end

// example:scroll:start
@Composable
internal fun DrawerScrollExample(modifier: Modifier = Modifier) {
    val drawerState = rememberFrogDrawerState()
    val scope = rememberCoroutineScope()

    FrogButton(
        onClick = { scope.launch { drawerState.open() } },
        modifier = modifier
    ) {
        Text("Scrollable Content Drawer")
    }

    FrogDrawer(
        state = drawerState,
        onDismissRequest = { scope.launch { drawerState.close() } },
        title = "Changelog & Release Notes",
        subtitle = "Recent library updates"
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            repeat(15) { index ->
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        "Release v0.1.${index + 1}",
                        style = FrogTheme.typography.heading,
                        color = FrogTheme.colors.foreground
                    )
                    Text(
                        "Detailed release notes explaining architectural refinements, accessibility audits, and Compose compatibility improvements.",
                        style = FrogTheme.typography.bodySmall,
                        color = FrogTheme.colors.mutedForeground
                    )
                }
            }
        }
    }
}
// example:scroll:end
