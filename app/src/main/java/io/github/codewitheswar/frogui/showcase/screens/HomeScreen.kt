package io.github.codewitheswar.frogui.showcase.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.BuildConfig
import io.github.codewitheswar.frogui.registry.FrogComponentRegistry
import io.github.codewitheswar.frogui.showcase.icons.FrogIcons
import io.github.codewitheswar.frogui.showcase.style.showcaseFocus
import io.github.codewitheswar.frogui.theme.FrogTheme

@Composable
fun HomeScreen(onNavigateToComponents: () -> Unit, onNavigateToFoundation: () -> Unit, onNavigateToButtonDetail: () -> Unit, modifier: Modifier = Modifier) {
    val colors = FrogTheme.colors
    Column(modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp), verticalArrangement = Arrangement.spacedBy(20.dp)) {
        Text("NATIVE COMPONENT WORKSPACE", style = FrogTheme.typography.label, color = colors.mutedForeground)
        Text("Own your UI.\nCompose everything.", style = FrogTheme.typography.display, color = colors.foreground, modifier = Modifier.semantics { heading() })
        Text("Explore the components, inspect their behavior, and take the code into your app.", style = FrogTheme.typography.body, color = colors.mutedForeground)
        Row(Modifier.fillMaxWidth().background(colors.subtleSurface, FrogTheme.shapes.md).border(1.dp, colors.border, FrogTheme.shapes.md).padding(16.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Column(Modifier.weight(1f)) {
                Text(FrogComponentRegistry.allComponents.size.toString(), style = FrogTheme.typography.titleLarge, color = colors.foreground)
                Text("Registered", style = FrogTheme.typography.bodySmall, color = colors.mutedForeground)
            }
            Column(Modifier.weight(1f)) {
                Text(FrogComponentRegistry.allComponents.count { it.status.label == "Stable" }.toString(), style = FrogTheme.typography.titleLarge, color = colors.foreground)
                Text("Stable", style = FrogTheme.typography.bodySmall, color = colors.mutedForeground)
            }
        }
        Text("Explore", style = FrogTheme.typography.heading, color = colors.foreground)
        HomeLink("Button workbench", "Variants, states, code, and documentation.", FrogIcons.Playground, onNavigateToButtonDetail)
        HomeLink("Components", "Browse the native registry.", FrogIcons.Components, onNavigateToComponents)
        HomeLink("Foundation", "Color, type, space, shape, and motion.", FrogIcons.Foundation, onNavigateToFoundation)
        HorizontalDivider(color = colors.border)
        Text("FrogUI ${BuildConfig.VERSION_NAME}", style = FrogTheme.typography.code, color = colors.mutedForeground)
    }
}

@Composable
private fun HomeLink(title: String, description: String, icon: ImageVector, onClick: () -> Unit) {
    val source = remember { MutableInteractionSource() }
    val colors = FrogTheme.colors
    Row(Modifier.fillMaxWidth().showcaseFocus(source).clickable(interactionSource = source, indication = null, role = Role.Button, onClick = onClick).padding(vertical = 12.dp, horizontal = 6.dp),
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp)) {
        Icon(icon, null, Modifier.size(22.dp), tint = colors.foreground)
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(title, style = FrogTheme.typography.subheading, color = colors.foreground)
            Text(description, style = FrogTheme.typography.bodySmall, color = colors.mutedForeground)
        }
        Icon(FrogIcons.Forward, null, Modifier.size(18.dp), tint = colors.mutedForeground)
    }
}
