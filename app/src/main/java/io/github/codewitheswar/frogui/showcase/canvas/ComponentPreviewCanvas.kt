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
import io.github.codewitheswar.frogui.components.button.FrogIconButton
import io.github.codewitheswar.frogui.components.button.FrogIconButtonDefaults
import io.github.codewitheswar.frogui.components.button.FrogIconButtonSize
import io.github.codewitheswar.frogui.components.button.FrogIconButtonVariant
import io.github.codewitheswar.frogui.showcase.icons.FrogIcons
import io.github.codewitheswar.frogui.theme.FrogTheme
import io.github.codewitheswar.frogui.showcase.colorpicker.checkerboard
import io.github.codewitheswar.frogui.showcase.style.LocalFrogMotionEnabled
import io.github.codewitheswar.frogui.foundation.motion.FrogMotion
import androidx.compose.ui.graphics.Color
import io.github.codewitheswar.frogui.showcase.detail.PreviewCapabilities
import io.github.codewitheswar.frogui.showcase.detail.PreviewContentMode
import io.github.codewitheswar.frogui.components.overlays.FrogOverlayHost

internal enum class PreviewBackground { Canvas, Light, Dark, Transparent }
internal enum class PreviewAlignment { Center, Start, End }

enum class PreviewWidthMode(val label: String, val width: Dp?) {
    Fit("Fit", null), Compact("Compact", 360.dp), Medium("Medium", 600.dp), Expanded("Expanded", 840.dp)
}

@Composable
internal fun ComponentPreviewCanvas(previewDarkTheme: Boolean, onTogglePreviewTheme: () -> Unit,
    widthMode: PreviewWidthMode, onChangeWidthMode: (PreviewWidthMode) -> Unit, onReset: () -> Unit,
    modifier: Modifier = Modifier, background: PreviewBackground = PreviewBackground.Canvas,
    alignment: PreviewAlignment = PreviewAlignment.Center, onConfigure: (() -> Unit)? = null,
    capabilities: PreviewCapabilities = PreviewCapabilities(), content: @Composable () -> Unit) {
    val colors = FrogTheme.colors
    var widthsOpen by remember { mutableStateOf(false) }
    Column(modifier.fillMaxWidth().clip(FrogTheme.shapes.md).background(colors.surfaceElevated).border(1.dp, colors.border, FrogTheme.shapes.md)) {
        Row(Modifier.fillMaxWidth().padding(start = 14.dp, end = 4.dp), verticalAlignment = Alignment.CenterVertically) {
            Text("Preview · ${widthMode.label}", Modifier.weight(1f), style = FrogTheme.typography.bodySmall, color = colors.mutedForeground)
            if (capabilities.width) Box {
                FrogIconButton(
                    icon = {
                        Icon(
                            imageVector = if (widthMode == PreviewWidthMode.Medium) FrogIcons.Tablet else FrogIcons.Phone,
                            contentDescription = null,
                            modifier = Modifier.size(FrogIconButtonDefaults.iconSize(FrogIconButtonSize.Small))
                        )
                    },
                    contentDescription = "Preview width: ${widthMode.label}",
                    onClick = { widthsOpen = true },
                    variant = FrogIconButtonVariant.Ghost,
                    size = FrogIconButtonSize.Small
                )
                DropdownMenu(widthsOpen, { widthsOpen = false }, containerColor = colors.surfaceElevated) {
                    PreviewWidthMode.entries.forEach { mode -> DropdownMenuItem(text = { Text(mode.label + (mode.width?.let { " · ${it.value.toInt()} dp" } ?: "")) }, onClick = { onChangeWidthMode(mode); widthsOpen = false },
                        trailingIcon = if (mode == widthMode) ({ Icon(FrogIcons.Check, "Selected") }) else null) }
                }
            }
            if (capabilities.theme) {
                FrogIconButton(
                    icon = {
                        Icon(
                            imageVector = if (previewDarkTheme) FrogIcons.Light else FrogIcons.Dark,
                            contentDescription = null,
                            modifier = Modifier.size(FrogIconButtonDefaults.iconSize(FrogIconButtonSize.Small))
                        )
                    },
                    contentDescription = "Use ${if (previewDarkTheme) "light" else "dark"} preview",
                    onClick = onTogglePreviewTheme,
                    variant = FrogIconButtonVariant.Ghost,
                    size = FrogIconButtonSize.Small
                )
            }
            FrogIconButton(
                icon = {
                    Icon(
                        imageVector = FrogIcons.Reset,
                        contentDescription = null,
                        modifier = Modifier.size(FrogIconButtonDefaults.iconSize(FrogIconButtonSize.Small))
                    )
                },
                contentDescription = "Reset component properties",
                onClick = onReset,
                variant = FrogIconButtonVariant.Ghost,
                size = FrogIconButtonSize.Small
            )
            if (onConfigure != null) {
                FrogIconButton(
                    icon = {
                        Icon(
                            imageVector = FrogIcons.Sliders,
                            contentDescription = null,
                            modifier = Modifier.size(FrogIconButtonDefaults.iconSize(FrogIconButtonSize.Small))
                        )
                    },
                    contentDescription = "Preview settings",
                    onClick = onConfigure,
                    variant = FrogIconButtonVariant.Ghost,
                    size = FrogIconButtonSize.Small
                )
            }
        }
        HorizontalDivider(color = colors.border)
        FrogTheme(darkTheme = previewDarkTheme) {
            val canvas = when (background) { PreviewBackground.Canvas -> FrogTheme.colors.background; PreviewBackground.Light -> Color.White; PreviewBackground.Dark -> Color(0xFF09090B); PreviewBackground.Transparent -> Color.Transparent }
            val placement = when (alignment) { PreviewAlignment.Center -> Alignment.Center; PreviewAlignment.Start -> Alignment.CenterStart; PreviewAlignment.End -> Alignment.CenterEnd }
            BoxWithConstraints(Modifier.fillMaxWidth().heightIn(min = capabilities.minHeight).then(if (background == PreviewBackground.Transparent) Modifier.checkerboard() else Modifier).background(canvas).padding(FrogTheme.spacing.xxxl), contentAlignment = Alignment.Center) {
                val width = widthMode.width?.coerceAtMost(maxWidth) ?: maxWidth
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(FrogTheme.spacing.md)) {
                    Text("${width.value.toInt()} dp · ${FrogTheme.adaptive.windowSizeClass(width)}",
                        Modifier.background(FrogTheme.colors.surface, FrogTheme.shapes.sm).padding(horizontal = FrogTheme.spacing.sm, vertical = FrogTheme.spacing.xxs),
                        style = FrogTheme.typography.caption, color = FrogTheme.colors.mutedForeground)
                    if (capabilities.contentMode == PreviewContentMode.Overlay) FrogOverlayHost(Modifier.width(width).height(capabilities.minHeight)) { content() }
                    else Box(Modifier.width(width), contentAlignment = placement) { content() }
                }
            }
        }
    }
}
