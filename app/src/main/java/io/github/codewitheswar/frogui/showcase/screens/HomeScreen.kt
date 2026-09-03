package io.github.codewitheswar.frogui.showcase.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material.icons.rounded.Palette
import androidx.compose.material.icons.rounded.TouchApp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.foundation.branding.FrogUiLogo
import io.github.codewitheswar.frogui.foundation.branding.FrogUiLogoVariant
import io.github.codewitheswar.frogui.foundation.theme.FrogTheme
import io.github.codewitheswar.frogui.registry.FrogComponentRegistry

/**
 * Developer-tool landing experience for the FrogUI Showcase application.
 */
@Composable
fun HomeScreen(
    onNavigateToComponents: () -> Unit,
    onNavigateToFoundation: () -> Unit,
    onNavigateToButtonDetail: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = FrogTheme.colors
    val shapes = FrogTheme.shapes
    val spacing = FrogTheme.spacing
    val scrollState = rememberScrollState()

    val totalComponents = FrogComponentRegistry.allComponents.size
    val stableComponents = FrogComponentRegistry.allComponents.count { it.status.label == "Stable" }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(spacing.xl),
        verticalArrangement = Arrangement.spacedBy(spacing.xl)
    ) {
        // Hero Card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shapes.xl)
                .background(colors.surfaceElevated)
                .border(1.dp, colors.borderStrong, shapes.xl)
                .padding(spacing.xl)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(spacing.md)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(spacing.md)
                ) {
                    FrogUiLogo(
                        size = 56.dp,
                        variant = FrogUiLogoVariant.Auto,
                        contentDescription = "FrogUI"
                    )

                    Column {
                        Text(
                            text = "FrogUI",
                            style = FrogTheme.typography.titleLarge,
                            color = colors.foreground
                        )
                        Text(
                            text = "v1.0.0 • Open • Native",
                            style = FrogTheme.typography.caption,
                            color = colors.mutedForeground
                        )
                    }
                }

                Text(
                    text = "Composable components for modern Android.",
                    style = FrogTheme.typography.heading,
                    color = colors.foreground
                )

                Text(
                    text = "A restrained, developer-controlled Android UI ecosystem built with Kotlin, Jetpack Compose, and a disciplined monochrome Zinc foundation.",
                    style = FrogTheme.typography.body,
                    color = colors.mutedForeground
                )
            }
        }

        // Live Ecosystem Statistics
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(spacing.md)
        ) {
            MetricCard(
                title = "TOTAL REGISTERED",
                value = "$totalComponents",
                subtitle = "Active in catalog",
                modifier = Modifier.weight(1f)
            )
            MetricCard(
                title = "PRODUCTION READY",
                value = "$stableComponents",
                subtitle = "Stable components",
                modifier = Modifier.weight(1f)
            )
        }

        // Quick Navigation Section
        Text(
            text = "EXPLORE ECOSYSTEM",
            style = FrogTheme.typography.caption,
            color = colors.mutedForeground
        )

        QuickActionCard(
            title = "FrogButton Reference Milestone",
            description = "Interactive testing workbench with 5 variants, 3 sizes, live inspector, and dynamic snippet generation.",
            icon = Icons.Rounded.TouchApp,
            onClick = onNavigateToButtonDetail
        )

        QuickActionCard(
            title = "Component Catalog",
            description = "Browse actions, inputs, data display, overlays, and navigation primitives.",
            icon = Icons.Rounded.Category,
            onClick = onNavigateToComponents
        )

        QuickActionCard(
            title = "Design System Foundation",
            description = "Inspect official Zinc color palette, typography scale, spacing tokens, and motion curves.",
            icon = Icons.Rounded.Palette,
            onClick = onNavigateToFoundation
        )

        // Core Philosophy Strip
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shapes.lg)
                .background(colors.surface)
                .border(1.dp, colors.border, shapes.lg)
                .padding(spacing.lg),
            verticalArrangement = Arrangement.spacedBy(spacing.xs)
        ) {
            Text(
                text = "PHILOSOPHY",
                style = FrogTheme.typography.caption,
                color = colors.mutedForeground
            )
            Text(
                text = "Own Your UI. Compose Everything.",
                style = FrogTheme.typography.heading,
                color = colors.foreground
            )
            Text(
                text = "FrogUI provides sensible defaults and slot-based APIs without vendor lock-in. Developers maintain full ownership over their application UI.",
                style = FrogTheme.typography.bodySmall,
                color = colors.mutedForeground
            )
        }

        Spacer(modifier = Modifier.height(spacing.xl))
    }
}

@Composable
private fun MetricCard(
    title: String,
    value: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    val colors = FrogTheme.colors
    val shapes = FrogTheme.shapes
    val spacing = FrogTheme.spacing

    Column(
        modifier = modifier
            .clip(shapes.md)
            .background(colors.surfaceElevated)
            .border(1.dp, colors.border, shapes.md)
            .padding(spacing.md),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(text = title, style = FrogTheme.typography.caption, color = colors.mutedForeground)
        Text(text = value, style = FrogTheme.typography.titleLarge, color = colors.foreground)
        Text(text = subtitle, style = FrogTheme.typography.caption, color = colors.mutedForeground)
    }
}

@Composable
private fun QuickActionCard(
    title: String,
    description: String,
    icon: ImageVector,
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
            .padding(spacing.lg),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(spacing.md)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(shapes.sm)
                .background(colors.subtleSurface),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = colors.foreground,
                modifier = Modifier.size(20.dp)
            )
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = FrogTheme.typography.heading,
                color = colors.foreground
            )
            Text(
                text = description,
                style = FrogTheme.typography.bodySmall,
                color = colors.mutedForeground
            )
        }

        Icon(
            imageVector = Icons.Rounded.ArrowForward,
            contentDescription = null,
            tint = colors.mutedForeground,
            modifier = Modifier.size(20.dp)
        )
    }
}
