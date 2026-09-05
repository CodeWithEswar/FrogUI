package io.github.codewitheswar.frogui.components.fab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.theme.FrogTheme

private val PreviewAddIcon: ImageVector by lazy {
    ImageVector.Builder(
        name = "PreviewAdd",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        addPath(
            pathData = PathParser().parsePathString("M12 5V19M5 12H19").toNodes(),
            fill = null,
            stroke = SolidColor(Color.Black),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round
        )
    }.build()
}

@Preview(name = "Regular FAB - Light", group = "FAB")
@Composable
internal fun FrogFab_Regular() {
    FrogTheme {
        Box(Modifier.padding(16.dp)) {
            FrogFloatingActionButton(
                icon = { androidx.compose.material3.Icon(PreviewAddIcon, null) },
                contentDescription = "Add item",
                onClick = {},
                presentation = FrogFabPresentation.Regular
            )
        }
    }
}

@Preview(name = "Small FAB - Light", group = "FAB")
@Composable
internal fun FrogFab_Small() {
    FrogTheme {
        Box(Modifier.padding(16.dp)) {
            FrogFloatingActionButton(
                icon = { androidx.compose.material3.Icon(PreviewAddIcon, null) },
                contentDescription = "Quick action",
                onClick = {},
                presentation = FrogFabPresentation.Small
            )
        }
    }
}

@Preview(name = "Extended FAB - Expanded", group = "FAB")
@Composable
internal fun FrogFab_Extended() {
    FrogTheme {
        Box(Modifier.padding(16.dp)) {
            FrogFloatingActionButton(
                icon = { androidx.compose.material3.Icon(PreviewAddIcon, null) },
                label = { Text("Compose message") },
                contentDescription = "Compose message",
                onClick = {},
                presentation = FrogFabPresentation.Extended,
                expanded = true
            )
        }
    }
}

@Preview(name = "Extended FAB - Collapsed", group = "FAB")
@Composable
internal fun FrogFab_ExtendedCollapsed() {
    FrogTheme {
        Box(Modifier.padding(16.dp)) {
            FrogFloatingActionButton(
                icon = { androidx.compose.material3.Icon(PreviewAddIcon, null) },
                label = { Text("Compose message") },
                contentDescription = "Compose message",
                onClick = {},
                presentation = FrogFabPresentation.Extended,
                expanded = false
            )
        }
    }
}

@Preview(name = "FAB States Matrix", group = "FAB")
@Composable
internal fun FrogFab_States() {
    FrogTheme {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FrogFloatingActionButton(
                icon = { androidx.compose.material3.Icon(PreviewAddIcon, null) },
                contentDescription = "Default active",
                onClick = {}
            )
            FrogFloatingActionButton(
                icon = { androidx.compose.material3.Icon(PreviewAddIcon, null) },
                contentDescription = "Disabled",
                onClick = {},
                enabled = false
            )
        }
    }
}

@Preview(name = "FAB Elevation Scale", group = "FAB")
@Composable
internal fun FrogFab_Elevation() {
    FrogTheme {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FrogFloatingActionButton(
                icon = { androidx.compose.material3.Icon(PreviewAddIcon, null) },
                contentDescription = "Standard elevation",
                onClick = {}
            )
            FrogFloatingActionButton(
                icon = { androidx.compose.material3.Icon(PreviewAddIcon, null) },
                contentDescription = "High elevation",
                onClick = {},
                elevation = FrogFloatingActionButtonDefaults.elevation(default = 8.dp)
            )
        }
    }
}

@Preview(name = "FAB - Dark Theme", group = "FAB")
@Composable
internal fun FrogFab_Dark() {
    FrogTheme(darkTheme = true) {
        Box(Modifier.background(FrogTheme.colors.background).padding(16.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                FrogFloatingActionButton(
                    icon = { androidx.compose.material3.Icon(PreviewAddIcon, null) },
                    contentDescription = "Dark regular",
                    onClick = {}
                )
                FrogFloatingActionButton(
                    icon = { androidx.compose.material3.Icon(PreviewAddIcon, null) },
                    label = { Text("New thread") },
                    contentDescription = "Dark extended",
                    onClick = {},
                    presentation = FrogFabPresentation.Extended
                )
            }
        }
    }
}
