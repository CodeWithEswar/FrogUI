package io.github.codewitheswar.frogui.showcase.components.iconbutton

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.components.button.*
import io.github.codewitheswar.frogui.showcase.canvas.PreviewBackground
import io.github.codewitheswar.frogui.showcase.colorpicker.*
import io.github.codewitheswar.frogui.showcase.detail.ComponentDetailState
import io.github.codewitheswar.frogui.showcase.icons.FrogIcons
import io.github.codewitheswar.frogui.theme.FrogTheme
import java.util.Locale

@Composable
internal fun IconButtonLivePreview(
    state: IconButtonDemoState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val iconVector = when (state.icon) {
        IconButtonDemoIcon.Search -> FrogIcons.Search
        IconButtonDemoIcon.Close -> FrogIcons.Close
        IconButtonDemoIcon.Settings -> FrogIcons.Settings
        IconButtonDemoIcon.Reset -> FrogIcons.Reset
        IconButtonDemoIcon.Play -> FrogIcons.Play
        IconButtonDemoIcon.Info -> FrogIcons.Info
    }

    val badgeContent: (@Composable () -> Unit)? = when (state.badge) {
        IconButtonDemoBadge.None -> null
        IconButtonDemoBadge.Dot -> {
            {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(FrogTheme.colors.destructive, CircleShape)
                )
            }
        }
        IconButtonDemoBadge.Count -> {
            {
                Box(
                    modifier = Modifier
                        .defaultMinSize(minWidth = 16.dp, minHeight = 16.dp)
                        .background(FrogTheme.colors.destructive, CircleShape)
                        .padding(horizontal = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${state.badgeCount}",
                        style = FrogTheme.typography.bodySmall,
                        color = Color.White
                    )
                }
            }
        }
    }

    FrogIconButton(
        icon = {
            Icon(
                imageVector = iconVector,
                contentDescription = null,
                modifier = Modifier.size(FrogIconButtonDefaults.iconSize(state.size))
            )
        },
        contentDescription = state.contentDescription,
        onClick = onClick,
        modifier = modifier,
        variant = state.variant,
        size = state.size,
        enabled = state.enabled,
        loading = state.loading,
        badge = badgeContent,
        colors = state.resolvedColors()
    )
}

@Composable
internal fun IconButtonInspectorPreview(
    state: IconButtonDemoState,
    ui: ComponentDetailState,
    onClick: () -> Unit,
    editing: IconButtonColorProperty? = null
) {
    FrogTheme(darkTheme = ui.previewDark) {
        val canvas = when (ui.background) {
            PreviewBackground.Light -> Color.White
            PreviewBackground.Dark -> Color(0xFF09090B)
            else -> FrogTheme.colors.background
        }
        val preview = if (editing != null) {
            state.copy(
                enabled = editing !in setOf(
                    IconButtonColorProperty.DisabledContainer,
                    IconButtonColorProperty.DisabledContent,
                    IconButtonColorProperty.DisabledBorder
                ),
                loading = false
            )
        } else state

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(canvas)
                .padding(horizontal = 18.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = if (editing != null && !preview.enabled) "Disabled preview" else "Live preview",
                style = FrogTheme.typography.label,
                color = FrogTheme.colors.mutedForeground
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 60.dp),
                contentAlignment = Alignment.Center
            ) {
                IconButtonLivePreview(preview, onClick, Modifier.testTag("drawer-live-icon-button"))
            }
            if (editing != null) {
                val resolved = preview.resolvedColors()
                val contrast = colorContrast(
                    if (preview.enabled) resolved.contentColor else resolved.disabledContentColor,
                    if (preview.enabled) resolved.containerColor else resolved.disabledContainerColor,
                    canvas
                )
                Text(
                    text = String.format(
                        Locale.ROOT,
                        "Contrast %.2f:1 · %s",
                        contrast,
                        if (contrast >= 4.5) "AA normal text threshold met" else "Low for normal text"
                    ) + if (ui.background == PreviewBackground.Transparent) " (theme canvas)" else "",
                    style = FrogTheme.typography.bodySmall,
                    color = FrogTheme.colors.mutedForeground
                )
            }
        }
    }
}

@Composable
internal fun ColorComparison(label: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        ColorSwatch(color)
        Column {
            Text(label, style = FrogTheme.typography.bodySmall, color = FrogTheme.colors.mutedForeground)
            Text(color.hex(), style = FrogTheme.typography.code, color = FrogTheme.colors.foreground)
        }
    }
}
