package io.github.codewitheswar.frogui.showcase.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.foundation.color.FrogPalette
import io.github.codewitheswar.frogui.foundation.theme.FrogTheme
import io.github.codewitheswar.frogui.registry.FrogComponentCategory
import io.github.codewitheswar.frogui.registry.FrogComponentMetadata
import io.github.codewitheswar.frogui.registry.FrogComponentRegistry
import io.github.codewitheswar.frogui.registry.FrogComponentStatus

/**
 * Filterable, searchable component catalog screen for FrogUI.
 */
@Composable
fun ComponentsScreen(
    onSelectComponent: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = FrogTheme.colors
    val shapes = FrogTheme.shapes
    val spacing = FrogTheme.spacing

    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<FrogComponentCategory?>(null) }

    val filteredComponents = remember(searchQuery, selectedCategory) {
        FrogComponentRegistry.search(searchQuery, selectedCategory)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = spacing.lg),
        verticalArrangement = Arrangement.spacedBy(spacing.md)
    ) {
        // Search Field
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = spacing.md),
            placeholder = {
                Text(
                    text = "Search components (e.g. Button, Dialog)...",
                    style = FrogTheme.typography.bodySmall,
                    color = colors.mutedForeground
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = null,
                    tint = colors.mutedForeground,
                    modifier = Modifier.size(20.dp)
                )
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(
                            imageVector = Icons.Rounded.Clear,
                            contentDescription = "Clear search",
                            tint = colors.mutedForeground,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            },
            singleLine = true,
            textStyle = FrogTheme.typography.body,
            shape = shapes.md,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colors.primary,
                unfocusedBorderColor = colors.border,
                focusedContainerColor = colors.surfaceElevated,
                unfocusedContainerColor = colors.surfaceElevated,
                focusedTextColor = colors.foreground,
                unfocusedTextColor = colors.foreground
            )
        )

        // Category Filter Chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(spacing.xs)
        ) {
            CategoryChip(
                title = "All",
                isSelected = selectedCategory == null,
                onClick = { selectedCategory = null }
            )

            FrogComponentCategory.entries.forEach { cat ->
                CategoryChip(
                    title = cat.displayName,
                    isSelected = selectedCategory == cat,
                    onClick = { selectedCategory = cat }
                )
            }
        }

        // Component List
        if (filteredComponents.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = spacing.x4l),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(spacing.xs)
                ) {
                    Text(
                        text = "No components found",
                        style = FrogTheme.typography.heading,
                        color = colors.foreground
                    )
                    Text(
                        text = "Try adjusting your search query or category filter.",
                        style = FrogTheme.typography.bodySmall,
                        color = colors.mutedForeground
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(spacing.sm)
            ) {
                items(filteredComponents, key = { it.id }) { item ->
                    ComponentListItem(
                        component = item,
                        onClick = { onSelectComponent(item.id) }
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(spacing.xl))
                }
            }
        }
    }
}

@Composable
private fun CategoryChip(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val colors = FrogTheme.colors
    val shapes = FrogTheme.shapes
    val spacing = FrogTheme.spacing

    Box(
        modifier = Modifier
            .clip(shapes.sm)
            .background(if (isSelected) colors.primary else colors.surfaceElevated)
            .border(
                1.dp,
                if (isSelected) colors.primary else colors.border,
                shapes.sm
            )
            .clickable(onClick = onClick)
            .padding(horizontal = spacing.md, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            style = FrogTheme.typography.caption,
            color = if (isSelected) colors.primaryForeground else colors.foreground
        )
    }
}

@Composable
private fun ComponentListItem(
    component: FrogComponentMetadata,
    onClick: () -> Unit
) {
    val colors = FrogTheme.colors
    val shapes = FrogTheme.shapes
    val spacing = FrogTheme.spacing

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shapes.lg)
            .background(colors.surfaceElevated)
            .border(1.dp, colors.border, shapes.lg)
            .clickable(onClick = onClick)
            .padding(spacing.md),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(spacing.md)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(spacing.xs)
            ) {
                Text(
                    text = component.name,
                    style = FrogTheme.typography.heading,
                    color = colors.foreground
                )

                // Status Pill
                StatusPill(status = component.status)
            }

            Text(
                text = component.description,
                style = FrogTheme.typography.bodySmall,
                color = colors.mutedForeground,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}

@Composable
private fun StatusPill(status: FrogComponentStatus) {
    val colors = FrogTheme.colors
    val shapes = FrogTheme.shapes

    val (bg, fg) = when (status) {
        FrogComponentStatus.Stable -> FrogPalette.Success.copy(alpha = 0.15f) to FrogPalette.Success
        FrogComponentStatus.Beta -> colors.muted to colors.foreground
        FrogComponentStatus.Experimental -> FrogPalette.Warning.copy(alpha = 0.15f) to FrogPalette.Warning
        FrogComponentStatus.Deprecated -> FrogPalette.Destructive.copy(alpha = 0.15f) to FrogPalette.Destructive
    }

    Box(
        modifier = Modifier
            .clip(shapes.full)
            .background(bg)
            .padding(horizontal = 8.dp, vertical = 2.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = status.label,
            style = FrogTheme.typography.caption,
            color = fg
        )
    }
}
