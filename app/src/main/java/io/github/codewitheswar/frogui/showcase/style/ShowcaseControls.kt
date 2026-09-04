package io.github.codewitheswar.frogui.showcase.style

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.theme.FrogTheme
import io.github.codewitheswar.frogui.components.button.*
import io.github.codewitheswar.frogui.showcase.icons.FrogIcons
import androidx.compose.foundation.BorderStroke

@Composable
internal fun ShowcaseBackButton(onClick: () -> Unit, modifier: Modifier = Modifier, label: String = "Back") {
    val colors = FrogTheme.colors
    FrogIconButton(onClick, label, modifier, variant = FrogButtonVariant.Secondary, size = FrogButtonSize.Small,
        colors = FrogButtonDefaults.colors(FrogButtonVariant.Secondary, containerColor = colors.muted, contentColor = colors.foreground),
        border = BorderStroke(1.dp, colors.border)) {
        Icon(FrogIcons.Back, null, Modifier.size(FrogTheme.sizing.iconMedium))
    }
}

/** Shared visible keyboard focus and pressed feedback. Interaction modifiers own semantics. */
@Composable
internal fun Modifier.showcaseFocus(source: MutableInteractionSource): Modifier {
    val focused by source.collectIsFocusedAsState()
    val pressed by source.collectIsPressedAsState()
    val colors = FrogTheme.colors
    val shape = FrogTheme.shapes.sm
    return clip(shape).background(if (pressed) colors.muted else Color.Transparent)
        .border(if (focused) 2.dp else 0.dp, if (focused) colors.focusRing else Color.Transparent, shape)
}

@Composable
internal fun ShowcaseIconButton(icon: ImageVector, label: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val source = remember { MutableInteractionSource() }
    Box(modifier.sizeIn(minWidth = FrogTheme.sizing.minimumTouchTarget, minHeight = FrogTheme.sizing.minimumTouchTarget).showcaseFocus(source)
        .clickable(interactionSource = source, indication = null, role = Role.Button, onClickLabel = label, onClick = onClick),
        contentAlignment = Alignment.Center) {
        Icon(icon, contentDescription = label, tint = FrogTheme.colors.foreground, modifier = Modifier.size(FrogTheme.sizing.iconLarge))
    }
}

@Composable
internal fun ShowcaseChoice(label: String, selected: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val source = remember { MutableInteractionSource() }
    val colors = FrogTheme.colors
    Box(modifier.heightIn(min = FrogTheme.sizing.minimumTouchTarget).showcaseFocus(source)
        .background(if (selected) colors.muted else Color.Transparent)
        .selectable(selected, interactionSource = source, indication = null, role = Role.RadioButton, onClick = onClick)
        .padding(horizontal = 12.dp, vertical = 10.dp), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(3.dp)) {
            Text(label, style = FrogTheme.typography.bodySmall, color = colors.foreground)
            Box(Modifier.width(16.dp).height(2.dp).background(if (selected) colors.foreground else Color.Transparent))
        }
    }
}
