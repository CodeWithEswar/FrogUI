package io.github.codewitheswar.frogui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.theme.FrogTheme

@Composable
internal fun FrogShowcaseTopBar(title: String, modifier: Modifier = Modifier, subtitle: String? = null,
    navigationIcon: (@Composable () -> Unit)? = null, actions: @Composable RowScope.() -> Unit = {}) {
    val colors = FrogTheme.colors
    Column(modifier.fillMaxWidth().background(colors.background)) {
        Row(Modifier.fillMaxWidth().heightIn(min = 60.dp).padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically) {
            navigationIcon?.invoke()
            Column(Modifier.weight(1f).padding(horizontal = 8.dp, vertical = 4.dp)) {
                Text(title, style = FrogTheme.typography.heading, color = colors.foreground,
                    maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.semantics { heading() })
                subtitle?.let { Text(it, style = FrogTheme.typography.bodySmall, color = colors.mutedForeground, maxLines = 1, overflow = TextOverflow.Ellipsis) }
            }
            actions()
        }
        HorizontalDivider(color = colors.border)
    }
}
