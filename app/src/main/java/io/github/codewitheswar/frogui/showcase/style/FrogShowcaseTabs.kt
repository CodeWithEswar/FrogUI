package io.github.codewitheswar.frogui.showcase.style

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.IntOffset
import io.github.codewitheswar.frogui.theme.FrogTheme

@Composable
internal fun FrogShowcaseTabs(labels: List<String>, selected: Int, onSelect: (Int) -> Unit, modifier: Modifier = Modifier) {
    val colors = FrogTheme.colors
    BoxWithConstraints(modifier.fillMaxWidth()) {
        val itemWidth = maxOf(maxWidth / labels.size, 96.dp * LocalDensity.current.fontScale.coerceAtMost(1.5f))
        val offset by animateDpAsState(itemWidth * selected, tween(ShowcaseMotion.standard), label = "tab indicator")
        val scroll = rememberScrollState()
        val density = LocalDensity.current
        LaunchedEffect(selected) { scroll.scrollTo(with(density) { (itemWidth * selected).roundToPx() }.coerceAtMost(scroll.maxValue)) }
        Column(Modifier.horizontalScroll(scroll).width(itemWidth * labels.size)) {
            Row(Modifier.fillMaxWidth().height(IntrinsicSize.Min).selectableGroup()) {
                labels.forEachIndexed { index, label ->
                    val source = remember { MutableInteractionSource() }
                    Box(Modifier.width(itemWidth).fillMaxHeight().heightIn(min = 48.dp).showcaseFocus(source)
                        .selectable(index == selected, interactionSource = source, indication = null, role = Role.Tab, onClick = { onSelect(index) }).padding(8.dp), contentAlignment = Alignment.Center) {
                        Text(label, color = if (selected == index) colors.foreground else colors.mutedForeground, style = FrogTheme.typography.bodySmall, textAlign = TextAlign.Center)
                    }
                }
            }
            Box(Modifier.fillMaxWidth().height(2.dp).background(colors.border)) {
                Box(Modifier.offset { IntOffset(offset.roundToPx(), 0) }.width(itemWidth).padding(horizontal = 16.dp).fillMaxHeight().background(colors.foreground))
            }
        }
    }
}
