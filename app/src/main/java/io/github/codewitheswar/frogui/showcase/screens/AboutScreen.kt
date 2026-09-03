package io.github.codewitheswar.frogui.showcase.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.foundation.branding.FrogUiLogo
import io.github.codewitheswar.frogui.foundation.branding.FrogUiLogoVariant
import io.github.codewitheswar.frogui.foundation.theme.FrogTheme

/**
 * Restrained developer-tool About screen for FrogUI.
 */
@Composable
fun AboutScreen(
    modifier: Modifier = Modifier
) {
    val colors = FrogTheme.colors
    val shapes = FrogTheme.shapes
    val spacing = FrogTheme.spacing
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(spacing.xl),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(spacing.xl)
    ) {
        Spacer(modifier = Modifier.height(spacing.md))

        FrogUiLogo(
            size = 80.dp,
            variant = FrogUiLogoVariant.Auto,
            contentDescription = "FrogUI"
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "FrogUI",
                style = FrogTheme.typography.titleLarge,
                color = colors.foreground
            )
            Text(
                text = "v1.0.0 • Open Source Android Component Ecosystem",
                style = FrogTheme.typography.caption,
                color = colors.mutedForeground
            )
        }

        Text(
            text = "Beautifully engineered Android components. Open. Customizable. Native.",
            style = FrogTheme.typography.body,
            color = colors.mutedForeground
        )

        // Metadata Spec Cards
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shapes.lg)
                .background(colors.surfaceElevated)
                .border(1.dp, colors.border, shapes.lg)
                .padding(spacing.md),
            verticalArrangement = Arrangement.spacedBy(spacing.sm)
        ) {
            AboutSpecRow("Architecture", "Multi-Module Gradle (:foundation, :components, :registry, :app)")
            AboutSpecRow("Language", "Kotlin 2.2 • Jetpack Compose")
            AboutSpecRow("Platform", "Android SDK 24+ • Edge-to-Edge")
            AboutSpecRow("License", "Apache License 2.0")
            AboutSpecRow("Repository", "github.com/CodeWithEswar/FrogUI")
        }

        Spacer(modifier = Modifier.height(spacing.xl))
    }
}

@Composable
private fun AboutSpecRow(label: String, value: String) {
    val colors = FrogTheme.colors

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, style = FrogTheme.typography.caption, color = colors.mutedForeground)
        Text(text = value, style = FrogTheme.typography.bodySmall, color = colors.foreground)
    }
}
