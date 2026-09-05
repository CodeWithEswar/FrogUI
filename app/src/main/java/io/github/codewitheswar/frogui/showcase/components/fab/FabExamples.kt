package io.github.codewitheswar.frogui.showcase.components.fab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.components.button.FrogButton
import io.github.codewitheswar.frogui.components.button.FrogButtonSize
import io.github.codewitheswar.frogui.components.button.FrogButtonVariant
import io.github.codewitheswar.frogui.components.fab.FrogFabPresentation
import io.github.codewitheswar.frogui.components.fab.FrogFloatingActionButton
import io.github.codewitheswar.frogui.showcase.icons.FrogIcons
import io.github.codewitheswar.frogui.theme.FrogTheme

@Composable
internal fun FabRegularExample() {
    // example:regular:start
    FrogFloatingActionButton(
        icon = { Icon(FrogIcons.Add, null) },
        contentDescription = "Create new item",
        onClick = { /* Handle action */ },
        presentation = FrogFabPresentation.Regular
    )
    // example:regular:end
}

@Composable
internal fun FabSmallExample() {
    // example:small:start
    FrogFloatingActionButton(
        icon = { Icon(FrogIcons.Search, null) },
        contentDescription = "Quick search",
        onClick = { /* Handle action */ },
        presentation = FrogFabPresentation.Small
    )
    // example:small:end
}

@Composable
internal fun FabExtendedExample() {
    // example:extended:start
    FrogFloatingActionButton(
        icon = { Icon(FrogIcons.Add, null) },
        label = { Text("Compose message") },
        contentDescription = "Compose message",
        onClick = { /* Handle action */ },
        presentation = FrogFabPresentation.Extended
    )
    // example:extended:end
}

@Composable
internal fun FabCollapsingExample() {
    // example:collapsing:start
    var isExpanded by remember { mutableStateOf(true) }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        FrogFloatingActionButton(
            icon = { Icon(FrogIcons.Add, null) },
            label = { Text("New task") },
            contentDescription = "New task",
            onClick = { /* Handle action */ },
            presentation = FrogFabPresentation.Extended,
            expanded = isExpanded
        )
        FrogButton(
            onClick = { isExpanded = !isExpanded },
            variant = FrogButtonVariant.Outline,
            size = FrogButtonSize.Small
        ) {
            Text(if (isExpanded) "Collapse FAB" else "Expand FAB")
        }
    }
    // example:collapsing:end
}

@Composable
internal fun FabScrollAwareExample() {
    // example:scroll-aware:start
    val listState = rememberLazyListState()
    val isScrollingUp by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex == 0 ||
                listState.firstVisibleItemScrollOffset == 0
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .background(FrogTheme.colors.surface)
            .padding(8.dp)
    ) {
        LazyColumn(state = listState, modifier = Modifier.fillMaxWidth()) {
            items((1..15).toList()) { index ->
                Text(
                    text = "Activity feed item #$index",
                    style = FrogTheme.typography.bodySmall,
                    color = FrogTheme.colors.foreground,
                    modifier = Modifier.padding(vertical = 6.dp)
                )
            }
        }
        FrogFloatingActionButton(
            icon = { Icon(FrogIcons.Add, null) },
            label = { Text("Add update") },
            contentDescription = "Add update",
            onClick = { },
            presentation = FrogFabPresentation.Extended,
            expanded = isScrollingUp,
            modifier = Modifier.align(Alignment.BottomEnd).padding(8.dp)
        )
    }
    // example:scroll-aware:end
}

@Composable
internal fun FabInsetAwareExample() {
    // example:inset-aware:start
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .background(FrogTheme.colors.surface)
            .padding(12.dp)
    ) {
        Text(
            text = "Screen content area",
            style = FrogTheme.typography.body,
            color = FrogTheme.colors.mutedForeground
        )
        // Positioned at bottom-end above navigation bar/insets
        FrogFloatingActionButton(
            icon = { Icon(FrogIcons.Add, null) },
            contentDescription = "Floating creation action",
            onClick = { },
            presentation = FrogFabPresentation.Regular,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 8.dp, end = 8.dp)
        )
    }
    // example:inset-aware:end
}
