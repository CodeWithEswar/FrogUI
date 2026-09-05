package io.github.codewitheswar.frogui.showcase.components.fab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.components.fab.FrogFabPresentation
import io.github.codewitheswar.frogui.components.fab.FrogFloatingActionButton
import io.github.codewitheswar.frogui.showcase.icons.FrogIcons
import io.github.codewitheswar.frogui.theme.FrogTheme

@Preview(name = "Showcase FAB Gallery", group = "FAB")
@Composable
fun FabComponentPreviews() {
    FrogTheme {
        Column(
            modifier = Modifier
                .background(FrogTheme.colors.background)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Presentations", style = FrogTheme.typography.heading, color = FrogTheme.colors.foreground)
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                FrogFloatingActionButton(
                    icon = { Icon(FrogIcons.Add, null) },
                    contentDescription = "Regular action",
                    onClick = {},
                    presentation = FrogFabPresentation.Regular
                )
                FrogFloatingActionButton(
                    icon = { Icon(FrogIcons.Search, null) },
                    contentDescription = "Small action",
                    onClick = {},
                    presentation = FrogFabPresentation.Small
                )
                FrogFloatingActionButton(
                    icon = { Icon(FrogIcons.Add, null) },
                    label = { Text("Extended") },
                    contentDescription = "Extended action",
                    onClick = {},
                    presentation = FrogFabPresentation.Extended
                )
            }
        }
    }
}
