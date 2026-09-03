package io.github.codewitheswar.frogui.showcase.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.navigation.ShowcaseAppearance
import io.github.codewitheswar.frogui.components.button.FrogButton
import io.github.codewitheswar.frogui.components.button.FrogButtonVariant
import io.github.codewitheswar.frogui.showcase.style.ShowcaseChoice
import io.github.codewitheswar.frogui.showcase.style.showcaseFocus
import io.github.codewitheswar.frogui.theme.FrogTheme

@Composable
internal fun SettingsScreen(appearance: ShowcaseAppearance, onAppearanceChange: (ShowcaseAppearance) -> Unit,
    reduceMotion: Boolean, onReduceMotionChange: (Boolean) -> Unit, onAbout: () -> Unit) {
    val colors = FrogTheme.colors
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("Appearance", style = FrogTheme.typography.heading, color = colors.foreground)
        Row(Modifier.fillMaxWidth().selectableGroup()) {
            ShowcaseAppearance.entries.forEach { ShowcaseChoice(it.name, it == appearance, { onAppearanceChange(it) }, Modifier.weight(1f)) }
        }
        val source = remember { MutableInteractionSource() }
        Row(Modifier.fillMaxWidth().showcaseFocus(source).toggleable(reduceMotion, interactionSource = source, indication = null, role = Role.Switch, onValueChange = onReduceMotionChange)
            .padding(8.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Column(Modifier.weight(1f)) {
                Text("Reduce motion", style = FrogTheme.typography.subheading, color = colors.foreground)
                Text("Use immediate state changes. Android's animation setting is always respected.", style = FrogTheme.typography.bodySmall, color = colors.mutedForeground)
            }
            Switch(reduceMotion, onCheckedChange = null)
        }
        FrogButton(onClick = onAbout, variant = FrogButtonVariant.Outline) { Text("About FrogUI & licenses") }
    }
}
