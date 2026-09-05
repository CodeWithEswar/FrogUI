package io.github.codewitheswar.frogui.showcase.components.fab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.components.fab.FrogFloatingActionButton
import io.github.codewitheswar.frogui.components.fab.FrogFloatingActionButtonDefaults
import io.github.codewitheswar.frogui.showcase.canvas.PreviewBackground
import io.github.codewitheswar.frogui.showcase.colorpicker.ColorSwatch
import io.github.codewitheswar.frogui.showcase.colorpicker.colorContrast
import io.github.codewitheswar.frogui.showcase.colorpicker.hex
import io.github.codewitheswar.frogui.showcase.detail.ComponentDetailState
import io.github.codewitheswar.frogui.showcase.icons.FrogIcons
import io.github.codewitheswar.frogui.theme.FrogTheme
import java.util.Locale

@Composable
internal fun FabLivePreview(
    state: FabDemoState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val iconVector = when (state.icon) {
        FabDemoIcon.Add -> FrogIcons.Add
        FabDemoIcon.Search -> FrogIcons.Search
        FabDemoIcon.Settings -> FrogIcons.Settings
        FabDemoIcon.Reset -> FrogIcons.Reset
        FabDemoIcon.Play -> FrogIcons.Play
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FrogFloatingActionButton(
            icon = { Icon(iconVector, null) },
            label = { Text(state.labelText) },
            contentDescription = state.contentDescription,
            onClick = onClick,
            presentation = state.presentation,
            expanded = state.expanded,
            enabled = state.enabled,
            visible = state.visible,
            elevation = FrogFloatingActionButtonDefaults.elevation(default = state.elevationDp.dp),
            colors = state.resolvedColors()
        )

        if (!state.visible) {
            Text(
                text = "FAB hidden (visible = false)",
                style = FrogTheme.typography.bodySmall,
                color = FrogTheme.colors.mutedForeground
            )
        }
    }
}

@Composable
internal fun FabInspectorPreview(
    state: FabDemoState,
    ui: ComponentDetailState,
    onClick: () -> Unit,
    editing: FabColorProperty? = null
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
                    FabColorProperty.DisabledContainer,
                    FabColorProperty.DisabledContent
                ),
                visible = true
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
                    .heightIn(min = 72.dp),
                contentAlignment = Alignment.Center
            ) {
                FabLivePreview(preview, onClick, Modifier.testTag("drawer-live-fab"))
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
