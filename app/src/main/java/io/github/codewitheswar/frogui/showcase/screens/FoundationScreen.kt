package io.github.codewitheswar.frogui.showcase.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.foundation.color.FrogPalette
import io.github.codewitheswar.frogui.foundation.theme.FrogTheme

/**
 * Interactive visual token explorer for FrogUI design foundation.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FoundationScreen(
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
        verticalArrangement = Arrangement.spacedBy(spacing.xl)
    ) {
        // Section 1: Colors
        SectionHeader(
            title = "COLOR SYSTEM",
            subtitle = "Strict monochrome Zinc palette with high contrast and subtle tonal borders."
        )

        // Zinc Scale Swatches
        Text(text = "Zinc Palette", style = FrogTheme.typography.heading, color = colors.foreground)
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(spacing.xs),
            verticalArrangement = Arrangement.spacedBy(spacing.xs)
        ) {
            ColorSwatch("Zinc 950", "#09090B", FrogPalette.Zinc950)
            ColorSwatch("Zinc 900", "#18181B", FrogPalette.Zinc900)
            ColorSwatch("Zinc 800", "#27272A", FrogPalette.Zinc800)
            ColorSwatch("Zinc 700", "#3F3F46", FrogPalette.Zinc700)
            ColorSwatch("Zinc 500", "#71717A", FrogPalette.Zinc500)
            ColorSwatch("Zinc 400", "#A1A1AA", FrogPalette.Zinc400)
            ColorSwatch("Zinc 200", "#E4E4E7", FrogPalette.Zinc200)
            ColorSwatch("Zinc 50", "#FAFAFA", FrogPalette.Zinc50)
        }

        // Section 2: Typography
        SectionHeader(
            title = "TYPOGRAPHY",
            subtitle = "Predictable scale engineered for developer tooling and accessibility font scaling."
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shapes.lg)
                .background(colors.surfaceElevated)
                .border(1.dp, colors.border, shapes.lg)
                .padding(spacing.md),
            verticalArrangement = Arrangement.spacedBy(spacing.sm)
        ) {
            TypographyRow("display", "32sp", "Display Title", FrogTheme.typography.display)
            TypographyRow("titleLarge", "24sp", "Title Large", FrogTheme.typography.titleLarge)
            TypographyRow("title", "20sp", "Section Title", FrogTheme.typography.title)
            TypographyRow("heading", "18sp", "Heading Style", FrogTheme.typography.heading)
            TypographyRow("body", "15sp", "Body content text", FrogTheme.typography.body)
            TypographyRow("bodySmall", "13sp", "Body small content", FrogTheme.typography.bodySmall)
            TypographyRow("label", "12sp", "Button Label", FrogTheme.typography.label)
            TypographyRow("code", "13sp", "val frog = FrogTheme()", FrogTheme.typography.code)
        }

        // Section 3: Spacing
        SectionHeader(
            title = "SPACING SCALE",
            subtitle = "Consistent, intentional spatial rhythm: 2, 4, 6, 8, 12, 16, 20, 24, 32, 40, 48, 64 dp."
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shapes.lg)
                .background(colors.surfaceElevated)
                .border(1.dp, colors.border, shapes.lg)
                .padding(spacing.md),
            verticalArrangement = Arrangement.spacedBy(spacing.sm)
        ) {
            SpacingRow("xxs", spacing.xxs)
            SpacingRow("xs", spacing.xs)
            SpacingRow("sm", spacing.sm)
            SpacingRow("md", spacing.md)
            SpacingRow("lg", spacing.lg)
            SpacingRow("xl", spacing.xl)
            SpacingRow("xxl", spacing.xxl)
            SpacingRow("xxxl", spacing.xxxl)
            SpacingRow("x4l", spacing.x4l)
        }

        // Section 4: Shapes
        SectionHeader(
            title = "SHAPES & RADII",
            subtitle = "Structural geometry: xs (4dp), sm (6dp), md (10dp), lg (14dp), xl (18dp), full (100%)."
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(spacing.sm)
        ) {
            ShapeCard("xs", "4dp", shapes.xs)
            ShapeCard("sm", "6dp", shapes.sm)
            ShapeCard("md", "10dp", shapes.md)
            ShapeCard("lg", "14dp", shapes.lg)
            ShapeCard("xl", "18dp", shapes.xl)
            ShapeCard("full", "Circle", shapes.full)
        }

        // Section 5: Motion
        SectionHeader(
            title = "MOTION & FEEDBACK",
            subtitle = "Restrained timing curves: fast (120ms), normal (200ms), large (280ms)."
        )

        InteractiveMotionDemo()

        Spacer(modifier = Modifier.height(spacing.xl))
    }
}

@Composable
private fun SectionHeader(title: String, subtitle: String) {
    val colors = FrogTheme.colors
    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Text(text = title, style = FrogTheme.typography.caption, color = colors.mutedForeground)
        Text(text = subtitle, style = FrogTheme.typography.bodySmall, color = colors.mutedForeground)
    }
}

@Composable
private fun ColorSwatch(name: String, hex: String, color: Color) {
    val clipboard = LocalClipboardManager.current
    val shapes = FrogTheme.shapes
    val colors = FrogTheme.colors

    Column(
        modifier = Modifier
            .width(100.dp)
            .clip(shapes.md)
            .background(colors.surfaceElevated)
            .border(1.dp, colors.border, shapes.md)
            .clickable { clipboard.setText(AnnotatedString(name)) }
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clip(shapes.sm)
                .background(color)
                .border(1.dp, Color(0x20FFFFFF), shapes.sm)
        )
        Text(text = name, style = FrogTheme.typography.caption, color = colors.foreground)
        Text(text = hex, style = FrogTheme.typography.caption, color = colors.mutedForeground)
    }
}

@Composable
private fun TypographyRow(
    name: String,
    size: String,
    sample: String,
    style: androidx.compose.ui.text.TextStyle
) {
    val colors = FrogTheme.colors
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = sample, style = style, color = colors.foreground)
            Text(text = "$name • $size", style = FrogTheme.typography.caption, color = colors.mutedForeground)
        }
    }
}

@Composable
private fun SpacingRow(name: String, dp: Dp) {
    val colors = FrogTheme.colors
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(FrogTheme.spacing.md)
    ) {
        Text(
            text = "$name (${dp.value.toInt()}dp)",
            style = FrogTheme.typography.caption,
            color = colors.mutedForeground,
            modifier = Modifier.width(80.dp)
        )
        Box(
            modifier = Modifier
                .height(12.dp)
                .width(dp * 3)
                .clip(FrogTheme.shapes.xs)
                .background(colors.primary)
        )
    }
}

@Composable
private fun ShapeCard(name: String, value: String, shape: androidx.compose.ui.graphics.Shape) {
    val colors = FrogTheme.colors

    Column(
        modifier = Modifier
            .size(80.dp)
            .clip(FrogTheme.shapes.md)
            .background(colors.surfaceElevated)
            .border(1.dp, colors.border, FrogTheme.shapes.md)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(shape)
                .background(colors.muted)
                .border(1.dp, colors.borderStrong, shape)
        )
        Text(text = name, style = FrogTheme.typography.caption, color = colors.foreground)
        Text(text = value, style = FrogTheme.typography.caption, color = colors.mutedForeground)
    }
}

@Composable
private fun InteractiveMotionDemo() {
    val colors = FrogTheme.colors
    val shapes = FrogTheme.shapes
    var toggled by remember { mutableStateOf(false) }

    val fastScale by animateFloatAsState(
        targetValue = if (toggled) 1.15f else 1.0f,
        animationSpec = FrogTheme.motion.fastSpec(),
        label = "fast_motion"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shapes.lg)
            .background(colors.surfaceElevated)
            .border(1.dp, colors.border, shapes.lg)
            .clickable { toggled = !toggled }
            .padding(FrogTheme.spacing.md),
        verticalArrangement = Arrangement.spacedBy(FrogTheme.spacing.sm)
    ) {
        Text(
            text = "Tap to test tactile spring transition",
            style = FrogTheme.typography.bodySmall,
            color = colors.foreground
        )

        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(shapes.md)
                .background(colors.primary)
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${(fastScale * 100).toInt()}%",
                style = FrogTheme.typography.caption,
                color = colors.primaryForeground
            )
        }
    }
}
