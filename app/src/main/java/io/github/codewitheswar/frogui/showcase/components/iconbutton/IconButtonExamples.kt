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
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.components.button.*
import io.github.codewitheswar.frogui.showcase.icons.FrogIcons
import io.github.codewitheswar.frogui.theme.FrogTheme

// example:basic:start
@Composable
internal fun IconButtonBasicExample(modifier: Modifier = Modifier) {
    FrogIconButton(
        icon = {
            Icon(
                imageVector = FrogIcons.Search,
                contentDescription = null,
                modifier = Modifier.size(FrogIconButtonDefaults.iconSize(FrogIconButtonSize.Medium))
            )
        },
        contentDescription = "Search",
        onClick = {},
        modifier = modifier,
        variant = FrogIconButtonVariant.Filled
    )
}
// example:basic:end

// example:tonal:start
@Composable
internal fun IconButtonTonalExample(modifier: Modifier = Modifier) {
    FrogIconButton(
        icon = {
            Icon(
                imageVector = FrogIcons.Settings,
                contentDescription = null,
                modifier = Modifier.size(FrogIconButtonDefaults.iconSize(FrogIconButtonSize.Medium))
            )
        },
        contentDescription = "Settings",
        onClick = {},
        modifier = modifier,
        variant = FrogIconButtonVariant.Tonal
    )
}
// example:tonal:end

// example:outline:start
@Composable
internal fun IconButtonOutlineExample(modifier: Modifier = Modifier) {
    FrogIconButton(
        icon = {
            Icon(
                imageVector = FrogIcons.Close,
                contentDescription = null,
                modifier = Modifier.size(FrogIconButtonDefaults.iconSize(FrogIconButtonSize.Medium))
            )
        },
        contentDescription = "Close",
        onClick = {},
        modifier = modifier,
        variant = FrogIconButtonVariant.Outline
    )
}
// example:outline:end

// example:ghost:start
@Composable
internal fun IconButtonGhostExample(modifier: Modifier = Modifier) {
    FrogIconButton(
        icon = {
            Icon(
                imageVector = FrogIcons.Reset,
                contentDescription = null,
                modifier = Modifier.size(FrogIconButtonDefaults.iconSize(FrogIconButtonSize.Medium))
            )
        },
        contentDescription = "Reset preferences",
        onClick = {},
        modifier = modifier,
        variant = FrogIconButtonVariant.Ghost
    )
}
// example:ghost:end

// example:loading:start
@Composable
internal fun IconButtonLoadingExample(modifier: Modifier = Modifier) {
    FrogIconButton(
        icon = {
            Icon(
                imageVector = FrogIcons.Reset,
                contentDescription = null,
                modifier = Modifier.size(FrogIconButtonDefaults.iconSize(FrogIconButtonSize.Medium))
            )
        },
        contentDescription = "Refreshing content",
        onClick = {},
        modifier = modifier,
        variant = FrogIconButtonVariant.Filled,
        loading = true
    )
}
// example:loading:end

// example:badge:start
@Composable
internal fun IconButtonBadgeExample(modifier: Modifier = Modifier) {
    FrogIconButton(
        icon = {
            Icon(
                imageVector = FrogIcons.Info,
                contentDescription = null,
                modifier = Modifier.size(FrogIconButtonDefaults.iconSize(FrogIconButtonSize.Medium))
            )
        },
        contentDescription = "Notifications, 3 unread",
        onClick = {},
        modifier = modifier,
        variant = FrogIconButtonVariant.Tonal,
        badge = {
            Box(
                modifier = Modifier
                    .defaultMinSize(minWidth = 16.dp, minHeight = 16.dp)
                    .background(FrogTheme.colors.destructive, CircleShape)
                    .padding(horizontal = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "3",
                    style = FrogTheme.typography.bodySmall,
                    color = Color.White
                )
            }
        }
    )
}
// example:badge:end

// example:toolbar:start
@Composable
internal fun IconButtonToolbarExample(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(FrogTheme.colors.surfaceElevated, FrogTheme.shapes.md)
            .padding(horizontal = 8.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FrogIconButton(
            icon = { Icon(FrogIcons.Back, null, Modifier.size(FrogIconButtonDefaults.iconSize(FrogIconButtonSize.Small))) },
            contentDescription = "Navigate back",
            onClick = {},
            variant = FrogIconButtonVariant.Ghost,
            size = FrogIconButtonSize.Small
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = "Editor Toolbar",
            style = FrogTheme.typography.heading,
            color = FrogTheme.colors.foreground,
            modifier = Modifier.weight(1f)
        )
        FrogIconButton(
            icon = { Icon(FrogIcons.Search, null, Modifier.size(FrogIconButtonDefaults.iconSize(FrogIconButtonSize.Small))) },
            contentDescription = "Search document",
            onClick = {},
            variant = FrogIconButtonVariant.Ghost,
            size = FrogIconButtonSize.Small
        )
        Spacer(Modifier.width(4.dp))
        FrogIconButton(
            icon = { Icon(FrogIcons.Sliders, null, Modifier.size(FrogIconButtonDefaults.iconSize(FrogIconButtonSize.Small))) },
            contentDescription = "More options",
            onClick = {},
            variant = FrogIconButtonVariant.Ghost,
            size = FrogIconButtonSize.Small
        )
    }
}
// example:toolbar:end
