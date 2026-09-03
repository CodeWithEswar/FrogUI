package io.github.codewitheswar.frogui.showcase.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import io.github.codewitheswar.frogui.components.button.FrogButton
import io.github.codewitheswar.frogui.components.button.FrogButtonSize
import io.github.codewitheswar.frogui.components.button.FrogButtonVariant
import io.github.codewitheswar.frogui.foundation.color.FrogPalette
import io.github.codewitheswar.frogui.foundation.theme.FrogTheme
import io.github.codewitheswar.frogui.registry.FrogComponentMetadata
import io.github.codewitheswar.frogui.registry.FrogComponentRegistry
import io.github.codewitheswar.frogui.showcase.canvas.ComponentPreviewCanvas
import io.github.codewitheswar.frogui.showcase.canvas.PreviewWidthMode
import io.github.codewitheswar.frogui.showcase.code.CodeSnippetViewer
import io.github.codewitheswar.frogui.showcase.inspector.ButtonDemoState
import io.github.codewitheswar.frogui.showcase.inspector.PropertyInspector

/**
 * Signature component workbench screen.
 * Implements adaptive phone (single column) and tablet (pinned inspector side-by-side) layouts.
 */
@Composable
fun ComponentDetailScreen(
    componentId: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val component = remember(componentId) {
        FrogComponentRegistry.findById(componentId) ?: FrogComponentRegistry.Button
    }

    val colors = FrogTheme.colors
    val spacing = FrogTheme.spacing

    var demoState by remember { mutableStateOf(ButtonDemoState.default()) }
    var previewDarkTheme by remember { mutableStateOf(true) }
    var widthMode by remember { mutableStateOf(PreviewWidthMode.Fit) }

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val isTablet = maxWidth >= 840.dp
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = spacing.lg)
        ) {
            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = spacing.md),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(spacing.sm)
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = "Back",
                        tint = colors.foreground
                    )
                }

                Text(
                    text = component.name,
                    style = FrogTheme.typography.title,
                    color = colors.foreground
                )

                // Status pill
                Box(
                    modifier = Modifier
                        .clip(FrogTheme.shapes.full)
                        .background(FrogPalette.Success.copy(alpha = 0.15f))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = component.status.label,
                        style = FrogTheme.typography.caption,
                        color = FrogPalette.Success
                    )
                }
            }

            // Adaptive Content
            if (isTablet) {
                // Tablet Layout: Two-Pane Workbench
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = spacing.lg),
                    horizontalArrangement = Arrangement.spacedBy(spacing.lg)
                ) {
                    // Left Pane: Canvas + Code + Examples + API
                    Column(
                        modifier = Modifier
                            .weight(1.3f)
                            .verticalScroll(scrollState),
                        verticalArrangement = Arrangement.spacedBy(spacing.lg)
                    ) {
                        WorkbenchCanvasSection(
                            component = component,
                            demoState = demoState,
                            previewDarkTheme = previewDarkTheme,
                            onTogglePreviewTheme = { previewDarkTheme = !previewDarkTheme },
                            widthMode = widthMode,
                            onChangeWidthMode = { widthMode = it },
                            onReset = { demoState = ButtonDemoState.default() }
                        )

                        CodeSnippetViewer(code = demoState.toCodeSnippet())

                        ExamplesSection()

                        ApiDocumentationSection(component = component)

                        Spacer(modifier = Modifier.height(spacing.xl))
                    }

                    // Right Pane: Pinned Property Inspector
                    Column(
                        modifier = Modifier
                            .weight(0.9f)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(spacing.md)
                    ) {
                        PropertyInspector(
                            state = demoState,
                            onStateChange = { demoState = it }
                        )
                    }
                }
            } else {
                // Phone Layout: Single Column Stack
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState),
                    verticalArrangement = Arrangement.spacedBy(spacing.lg)
                ) {
                    WorkbenchCanvasSection(
                        component = component,
                        demoState = demoState,
                        previewDarkTheme = previewDarkTheme,
                        onTogglePreviewTheme = { previewDarkTheme = !previewDarkTheme },
                        widthMode = widthMode,
                        onChangeWidthMode = { widthMode = it },
                        onReset = { demoState = ButtonDemoState.default() }
                    )

                    PropertyInspector(
                        state = demoState,
                        onStateChange = { demoState = it }
                    )

                    CodeSnippetViewer(code = demoState.toCodeSnippet())

                    ExamplesSection()

                    ApiDocumentationSection(component = component)

                    Spacer(modifier = Modifier.height(spacing.x4l))
                }
            }
        }
    }
}

@Composable
private fun WorkbenchCanvasSection(
    component: FrogComponentMetadata,
    demoState: ButtonDemoState,
    previewDarkTheme: Boolean,
    onTogglePreviewTheme: () -> Unit,
    widthMode: PreviewWidthMode,
    onChangeWidthMode: (PreviewWidthMode) -> Unit,
    onReset: () -> Unit
) {
    val colors = FrogTheme.colors

    Column(verticalArrangement = Arrangement.spacedBy(FrogTheme.spacing.xs)) {
        Text(
            text = component.description,
            style = FrogTheme.typography.body,
            color = colors.mutedForeground
        )

        ComponentPreviewCanvas(
            previewDarkTheme = previewDarkTheme,
            onTogglePreviewTheme = onTogglePreviewTheme,
            widthMode = widthMode,
            onChangeWidthMode = onChangeWidthMode,
            onReset = onReset
        ) {
            val buttonModifier = if (demoState.fullWidth) {
                Modifier.fillMaxWidth()
            } else {
                Modifier
            }

            FrogButton(
                onClick = { /* Interactive action */ },
                variant = demoState.variant,
                size = demoState.size,
                enabled = demoState.enabled,
                loading = demoState.loading,
                modifier = buttonModifier,
                leadingIcon = if (demoState.hasLeadingIcon) {
                    { Icon(Icons.Rounded.PlayArrow, contentDescription = null, modifier = Modifier.size(16.dp)) }
                } else null,
                trailingIcon = if (demoState.hasTrailingIcon) {
                    { Icon(Icons.Rounded.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp)) }
                } else null
            ) {
                Text(demoState.buttonText)
            }
        }
    }
}

@Composable
private fun ExamplesSection() {
    val colors = FrogTheme.colors
    val shapes = FrogTheme.shapes
    val spacing = FrogTheme.spacing

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shapes.lg)
            .background(colors.surfaceElevated)
            .border(1.dp, colors.border, shapes.lg)
            .padding(spacing.md),
        verticalArrangement = Arrangement.spacedBy(spacing.md)
    ) {
        Text(
            text = "PRESET EXAMPLES",
            style = FrogTheme.typography.caption,
            color = colors.mutedForeground
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(spacing.sm)
        ) {
            FrogButton(
                variant = FrogButtonVariant.Primary,
                onClick = {},
                modifier = Modifier.weight(1f)
            ) {
                Text("Primary")
            }

            FrogButton(
                variant = FrogButtonVariant.Secondary,
                onClick = {},
                modifier = Modifier.weight(1f)
            ) {
                Text("Secondary")
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(spacing.sm)
        ) {
            FrogButton(
                variant = FrogButtonVariant.Outline,
                onClick = {},
                modifier = Modifier.weight(1f)
            ) {
                Text("Outline")
            }

            FrogButton(
                variant = FrogButtonVariant.Ghost,
                onClick = {},
                modifier = Modifier.weight(1f)
            ) {
                Text("Ghost")
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(spacing.sm)
        ) {
            FrogButton(
                variant = FrogButtonVariant.Destructive,
                onClick = {},
                modifier = Modifier.weight(1f)
            ) {
                Text("Destructive")
            }

            FrogButton(
                variant = FrogButtonVariant.Primary,
                loading = true,
                onClick = {},
                modifier = Modifier.weight(1f)
            ) {
                Text("Loading")
            }
        }
    }
}

@Composable
private fun ApiDocumentationSection(component: FrogComponentMetadata) {
    val colors = FrogTheme.colors
    val shapes = FrogTheme.shapes
    val spacing = FrogTheme.spacing

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shapes.lg)
            .background(colors.surfaceElevated)
            .border(1.dp, colors.border, shapes.lg)
            .padding(spacing.md),
        verticalArrangement = Arrangement.spacedBy(spacing.sm)
    ) {
        Text(
            text = "API REFERENCE",
            style = FrogTheme.typography.caption,
            color = colors.mutedForeground
        )

        component.properties.forEach { prop ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shapes.sm)
                    .background(colors.subtleSurface)
                    .padding(spacing.sm),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = prop.name,
                        style = FrogTheme.typography.code,
                        color = colors.foreground
                    )
                    Text(
                        text = prop.type,
                        style = FrogTheme.typography.caption,
                        color = colors.mutedForeground
                    )
                }
                Text(
                    text = prop.description,
                    style = FrogTheme.typography.bodySmall,
                    color = colors.mutedForeground
                )
            }
        }
    }
}
