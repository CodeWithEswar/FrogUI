package io.github.codewitheswar.frogui.showcase.canvas

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.material.icons.rounded.LightMode
import androidx.compose.material.icons.rounded.RestartAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.foundation.theme.FrogTheme
import io.github.codewitheswar.frogui.foundation.theme.FrogThemeDefaults

enum class PreviewWidthMode(val label: String, val width: Dp?) {
    Fit("Fit", null),
    Compact("360dp", 360.dp),
    Medium("480dp", 480.dp)
}

/**
 * Signature FrogUI Component Preview Canvas.
 * Provides an isolated testing surface with independent light/dark theme switching,
 * responsive width simulation, and reset controls.
 */
@Composable
fun ComponentPreviewCanvas(
    previewDarkTheme: Boolean,
    onTogglePreviewTheme: () -> Unit,
    widthMode: PreviewWidthMode,
    onChangeWidthMode: (PreviewWidthMode) -> Unit,
    onReset: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val outerTheme = FrogTheme.colors
    val canvasShape = FrogTheme.shapes.lg

    // Preview colors independently controlled
    val previewColors = if (previewDarkTheme) {
        FrogThemeDefaults.darkColors()
    } else {
        FrogThemeDefaults.lightColors()
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(canvasShape)
            .background(outerTheme.surfaceElevated)
            .border(1.dp, outerTheme.borderStrong, canvasShape)
    ) {
        // Preview Canvas Toolbar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(outerTheme.subtleSurface)
                .padding(horizontal = FrogTheme.spacing.md, vertical = FrogTheme.spacing.xs),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "PREVIEW CANVAS",
                style = FrogTheme.typography.caption,
                color = outerTheme.mutedForeground
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(FrogTheme.spacing.xs)
            ) {
                // Width Mode Switchers
                PreviewWidthMode.entries.forEach { mode ->
                    val isSelected = mode == widthMode
                    Box(
                        modifier = Modifier
                            .clip(FrogTheme.shapes.xs)
                            .background(if (isSelected) outerTheme.muted else outerTheme.subtleSurface)
                            .border(
                                1.dp,
                                if (isSelected) outerTheme.borderStrong else outerTheme.border,
                                FrogTheme.shapes.xs
                            )
                            .clickable { onChangeWidthMode(mode) }
                            .padding(horizontal = FrogTheme.spacing.sm, vertical = 2.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = mode.label,
                            style = FrogTheme.typography.caption,
                            color = if (isSelected) outerTheme.foreground else outerTheme.mutedForeground
                        )
                    }
                }

                // Independent Theme Toggle
                IconButton(onClick = onTogglePreviewTheme) {
                    Icon(
                        imageVector = if (previewDarkTheme) Icons.Rounded.LightMode else Icons.Rounded.DarkMode,
                        contentDescription = "Toggle preview canvas theme",
                        tint = outerTheme.foreground
                    )
                }

                // Reset Action
                IconButton(onClick = onReset) {
                    Icon(
                        imageVector = Icons.Rounded.RestartAlt,
                        contentDescription = "Reset component properties",
                        tint = outerTheme.mutedForeground
                    )
                }
            }
        }

        // Isolated Component Surface
        FrogTheme(
            darkTheme = previewDarkTheme,
            colors = previewColors
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(previewColors.background)
                    .padding(FrogTheme.spacing.xl),
                contentAlignment = Alignment.Center
            ) {
                val widthModifier = if (widthMode.width != null) {
                    Modifier.width(widthMode.width)
                } else {
                    Modifier.fillMaxWidth()
                }

                Box(
                    modifier = widthModifier
                        .wrapContentHeight()
                        .animateContentSize(),
                    contentAlignment = Alignment.Center
                ) {
                    content()
                }
            }
        }
    }
}
