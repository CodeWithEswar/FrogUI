package io.github.codewitheswar.frogui.showcase.canvas

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.showcase.icons.FrogIcons
import io.github.codewitheswar.frogui.showcase.style.ShowcaseIconButton
import io.github.codewitheswar.frogui.theme.FrogTheme
import io.github.codewitheswar.frogui.showcase.colorpicker.checkerboard
import io.github.codewitheswar.frogui.showcase.style.LocalFrogMotionEnabled
import io.github.codewitheswar.frogui.foundation.motion.FrogMotion
import androidx.compose.ui.graphics.Color

internal enum class PreviewBackground { Canvas, Light, Dark, Transparent }
internal enum class PreviewAlignment { Center, Start, End }

enum class PreviewWidthMode(val label: String, val width: Dp?) {
    Fit("Fit", null), Compact("360 dp", 360.dp), Medium("480 dp", 480.dp)
}

@Composable
internal fun ComponentPreviewCanvas(previewDarkTheme: Boolean, onTogglePreviewTheme: () -> Unit,
    widthMode: PreviewWidthMode, onChangeWidthMode: (PreviewWidthMode) -> Unit, onReset: () -> Unit,
    modifier: Modifier = Modifier, background: PreviewBackground = PreviewBackground.Canvas,
    alignment: PreviewAlignment = PreviewAlignment.Center, onConfigure: (() -> Unit)? = null, content: @Composable () -> Unit) {
    val colors = FrogTheme.colors
    var widthsOpen by remember { mutableStateOf(false) }
    Column(modifier.fillMaxWidth().clip(FrogTheme.shapes.md).background(colors.surfaceElevated).border(1.dp, colors.border, FrogTheme.shapes.md)) {
        Row(Modifier.fillMaxWidth().padding(start = 14.dp, end = 4.dp), verticalAlignment = Alignment.CenterVertically) {
            Text("Preview · ${widthMode.label}", Modifier.weight(1f), style = FrogTheme.typography.bodySmall, color = colors.mutedForeground)
            Box {
                ShowcaseIconButton(if (widthMode == PreviewWidthMode.Medium) FrogIcons.Tablet else FrogIcons.Phone, "Preview width: ${widthMode.label}", { widthsOpen = true })
                DropdownMenu(widthsOpen, { widthsOpen = false }, containerColor = colors.surfaceElevated) {
                    PreviewWidthMode.entries.forEach { mode -> DropdownMenuItem(text = { Text(mode.label) }, onClick = { onChangeWidthMode(mode); widthsOpen = false },
                        trailingIcon = if (mode == widthMode) ({ Icon(FrogIcons.Check, "Selected") }) else null) }
                }
            }
            ShowcaseIconButton(if (previewDarkTheme) FrogIcons.Light else FrogIcons.Dark, "Use ${if (previewDarkTheme) "light" else "dark"} preview", onTogglePreviewTheme)
            ShowcaseIconButton(FrogIcons.Reset, "Reset component properties", onReset)
            if (onConfigure != null) ShowcaseIconButton(FrogIcons.Sliders, "Preview settings", onConfigure)
        }
        HorizontalDivider(color = colors.border)
        FrogTheme(darkTheme = previewDarkTheme, motion = if (LocalFrogMotionEnabled.current) FrogMotion() else FrogMotion(0, 0, 0)) {
            val canvas = when (background) { PreviewBackground.Canvas -> FrogTheme.colors.background; PreviewBackground.Light -> Color.White; PreviewBackground.Dark -> Color(0xFF09090B); PreviewBackground.Transparent -> Color.Transparent }
            val placement = when (alignment) { PreviewAlignment.Center -> Alignment.Center; PreviewAlignment.Start -> Alignment.CenterStart; PreviewAlignment.End -> Alignment.CenterEnd }
            BoxWithConstraints(Modifier.fillMaxWidth().heightIn(min = 192.dp).then(if (background == PreviewBackground.Transparent) Modifier.checkerboard() else Modifier).background(canvas).padding(24.dp), contentAlignment = Alignment.Center) {
                val width = widthMode.width?.coerceAtMost(maxWidth) ?: maxWidth
                Box(Modifier.width(width), contentAlignment = placement) { content() }
            }
        }
    }
}
