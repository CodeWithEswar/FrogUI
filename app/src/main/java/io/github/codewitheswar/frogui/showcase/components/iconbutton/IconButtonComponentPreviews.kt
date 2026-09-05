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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.components.button.*
import io.github.codewitheswar.frogui.showcase.FrogComponentPreview
import io.github.codewitheswar.frogui.showcase.icons.FrogIcons
import io.github.codewitheswar.frogui.theme.FrogTheme

@Preview(name = "FrogIconButton · Default", widthDp = 390)
@Composable
private fun FrogIconButtonDefaultPreview() = FrogComponentPreview {
    FrogIconButton(
        icon = { Icon(FrogIcons.Search, null, Modifier.size(FrogIconButtonDefaults.iconSize(FrogIconButtonSize.Medium))) },
        contentDescription = "Search",
        onClick = {}
    )
}

@Preview(name = "FrogIconButton · Variants", widthDp = 390, heightDp = 160)
@Composable
private fun FrogIconButtonVariantsPreview() = FrogComponentPreview {
    Row(
        horizontalArrangement = Arrangement.spacedBy(FrogTheme.spacing.md),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FrogIconButtonVariant.entries.forEach { variant ->
            FrogIconButton(
                icon = { Icon(FrogIcons.Search, null, Modifier.size(FrogIconButtonDefaults.iconSize(FrogIconButtonSize.Medium))) },
                contentDescription = "${variant.name} search",
                onClick = {},
                variant = variant
            )
        }
    }
}

@Preview(name = "FrogIconButton · Sizes", widthDp = 390, heightDp = 160)
@Composable
private fun FrogIconButtonSizesPreview() = FrogComponentPreview {
    Row(
        horizontalArrangement = Arrangement.spacedBy(FrogTheme.spacing.md),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FrogIconButtonSize.entries.forEach { size ->
            FrogIconButton(
                icon = { Icon(FrogIcons.Search, null, Modifier.size(FrogIconButtonDefaults.iconSize(size))) },
                contentDescription = "${size.name} search",
                onClick = {},
                size = size
            )
        }
    }
}

@Preview(name = "FrogIconButton · States and badges", widthDp = 420, heightDp = 160)
@Composable
private fun FrogIconButtonStatesPreview() = FrogComponentPreview {
    Row(
        horizontalArrangement = Arrangement.spacedBy(FrogTheme.spacing.md),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Default
        FrogIconButton(
            icon = { Icon(FrogIcons.Search, null, Modifier.size(FrogIconButtonDefaults.iconSize(FrogIconButtonSize.Medium))) },
            contentDescription = "Search",
            onClick = {}
        )
        // Disabled
        FrogIconButton(
            icon = { Icon(FrogIcons.Search, null, Modifier.size(FrogIconButtonDefaults.iconSize(FrogIconButtonSize.Medium))) },
            contentDescription = "Search disabled",
            onClick = {},
            enabled = false
        )
        // Loading
        FrogIconButton(
            icon = { Icon(FrogIcons.Search, null, Modifier.size(FrogIconButtonDefaults.iconSize(FrogIconButtonSize.Medium))) },
            contentDescription = "Searching",
            onClick = {},
            loading = true
        )
        // Badged Dot
        FrogIconButton(
            icon = { Icon(FrogIcons.Info, null, Modifier.size(FrogIconButtonDefaults.iconSize(FrogIconButtonSize.Medium))) },
            contentDescription = "Info with alert",
            onClick = {},
            variant = FrogIconButtonVariant.Tonal,
            badge = {
                Box(Modifier.size(8.dp).background(FrogTheme.colors.destructive, CircleShape))
            }
        )
        // Badged Count
        FrogIconButton(
            icon = { Icon(FrogIcons.Info, null, Modifier.size(FrogIconButtonDefaults.iconSize(FrogIconButtonSize.Medium))) },
            contentDescription = "Info with 3 notifications",
            onClick = {},
            variant = FrogIconButtonVariant.Tonal,
            badge = {
                Box(
                    modifier = Modifier
                        .defaultMinSize(minWidth = 16.dp, minHeight = 16.dp)
                        .background(FrogTheme.colors.destructive, CircleShape)
                        .padding(horizontal = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("3", style = FrogTheme.typography.bodySmall, color = Color.White)
                }
            }
        )
    }
}

@Preview(name = "FrogIconButton · Dark custom", widthDp = 390)
@Composable
private fun FrogIconButtonDarkCustomPreview() = FrogComponentPreview(darkTheme = true) {
    FrogIconButton(
        icon = { Icon(FrogIcons.Play, null, Modifier.size(FrogIconButtonDefaults.iconSize(FrogIconButtonSize.Medium))) },
        contentDescription = "Play video",
        onClick = {},
        colors = FrogIconButtonDefaults.colors(
            containerColor = Color(0xFF1D4ED8),
            contentColor = Color.White
        )
    )
}
