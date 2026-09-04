package io.github.codewitheswar.frogui.showcase.components.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.components.button.*
import io.github.codewitheswar.frogui.foundation.motion.FrogMotion
import io.github.codewitheswar.frogui.showcase.canvas.PreviewBackground
import io.github.codewitheswar.frogui.showcase.colorpicker.*
import io.github.codewitheswar.frogui.showcase.detail.ComponentDetailState
import io.github.codewitheswar.frogui.showcase.icons.FrogIcons
import io.github.codewitheswar.frogui.showcase.style.LocalFrogMotionEnabled
import io.github.codewitheswar.frogui.theme.FrogTheme
import java.util.Locale

@Composable
internal fun ButtonLivePreview(state: ButtonDemoState, onClick: () -> Unit, modifier: Modifier = Modifier) {
    FrogButton(onClick = onClick, variant = state.variant, size = state.size, enabled = state.enabled, loading = state.loading,
        modifier = modifier, fullWidth = state.fullWidth, colors = state.resolvedColors(),
        shape = when (state.shape) { ButtonShape.Default -> FrogButtonDefaults.shape(state.size); ButtonShape.Square -> RectangleShape; ButtonShape.Pill -> RoundedCornerShape(percent = 50) },
        leadingIcon = if (state.hasLeadingIcon) ({ Icon(FrogIcons.Play, null, Modifier.size(FrogButtonDefaults.iconSize(state.size))) }) else null,
        trailingIcon = if (state.hasTrailingIcon) ({ Icon(FrogIcons.Forward, null, Modifier.size(FrogButtonDefaults.iconSize(state.size))) }) else null,
    ) { Text(state.buttonText) }
}

@Composable
internal fun ButtonInspectorPreview(state: ButtonDemoState, ui: ComponentDetailState, onClick: () -> Unit, editing: ButtonColorProperty? = null) {
    FrogTheme(darkTheme = ui.previewDark) {
        val canvas = when (ui.background) { PreviewBackground.Light -> Color.White; PreviewBackground.Dark -> Color(0xFF09090B); else -> FrogTheme.colors.background }
        val preview = if (editing != null) state.copy(enabled = editing !in setOf(ButtonColorProperty.DisabledContainer, ButtonColorProperty.DisabledContent, ButtonColorProperty.DisabledBorder), loading = false) else state
        Column(Modifier.fillMaxWidth().background(canvas).padding(horizontal = 18.dp, vertical = 10.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(if (editing != null && !preview.enabled) "Disabled preview" else "Live preview", style = FrogTheme.typography.label, color = FrogTheme.colors.mutedForeground)
            Box(Modifier.fillMaxWidth().heightIn(min = 60.dp), contentAlignment = Alignment.Center) { ButtonLivePreview(preview, onClick, Modifier.testTag("drawer-live-button")) }
            if (editing != null) {
                val resolved = preview.resolvedColors()
                val contrast = colorContrast(if (preview.enabled) resolved.contentColor else resolved.disabledContentColor, if (preview.enabled) resolved.containerColor else resolved.disabledContainerColor, canvas)
                Text(String.format(Locale.ROOT, "Contrast %.2f:1 · %s", contrast, if (contrast >= 4.5) "AA normal text threshold met" else "Low for normal text") + if (ui.background == PreviewBackground.Transparent) " (theme canvas)" else "",
                    style = FrogTheme.typography.bodySmall, color = FrogTheme.colors.mutedForeground)
            }
        }
    }
}

@Composable
internal fun ColorComparison(label: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        ColorSwatch(color)
        Column { Text(label, style = FrogTheme.typography.bodySmall, color = FrogTheme.colors.mutedForeground); Text(color.hex(), style = FrogTheme.typography.code, color = FrogTheme.colors.foreground) }
    }
}
