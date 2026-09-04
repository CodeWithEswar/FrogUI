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
internal fun FrogShowcaseTabs(labels: List<String>, selected: Int, onSelect: (Int) -> Unit, modifier: Modifier = Modifier, fit: Boolean = false) {
    val colors = FrogTheme.colors
    BoxWithConstraints(modifier.fillMaxWidth()) {
        val itemWidth = maxOf(maxWidth / labels.size, 96.dp * LocalDensity.current.fontScale.coerceAtMost(1.5f))
        val weights = labels.map { it.length.coerceAtLeast(4) + 3 }
        val widths = if (fit && maxWidth >= 320.dp && LocalDensity.current.fontScale <= 1.3f) weights.map { maxWidth * it / weights.sum() } else labels.map { itemWidth }
        val before = widths.take(selected).fold(0.dp) { sum, width -> sum + width }
        val totalWidth = widths.fold(0.dp) { sum, width -> sum + width }
        val offset by animateDpAsState(before, tween(ShowcaseMotion.standard), label = "tab indicator")
        val indicatorWidth by animateDpAsState(widths[selected], tween(ShowcaseMotion.standard), label = "tab width")
        val scroll = rememberScrollState()
        val density = LocalDensity.current
        LaunchedEffect(selected) { scroll.scrollTo(with(density) { before.roundToPx() }.coerceAtMost(scroll.maxValue)) }
        Column(Modifier.horizontalScroll(scroll).width(totalWidth)) {
            Row(Modifier.fillMaxWidth().height(IntrinsicSize.Min).selectableGroup()) {
                labels.forEachIndexed { index, label ->
                    val source = remember { MutableInteractionSource() }
                    Box(Modifier.width(widths[index]).fillMaxHeight().heightIn(min = FrogTheme.sizing.minimumTouchTarget).showcaseFocus(source)
                        .selectable(index == selected, interactionSource = source, indication = null, role = Role.Tab, onClick = { onSelect(index) }).padding(8.dp), contentAlignment = Alignment.Center) {
                        Text(label, color = if (selected == index) colors.foreground else colors.mutedForeground, style = FrogTheme.typography.bodySmall, textAlign = TextAlign.Center)
                    }
                }
            }
            Box(Modifier.fillMaxWidth().height(2.dp).background(colors.border)) {
                Box(Modifier.offset { IntOffset(offset.roundToPx(), 0) }.width(indicatorWidth).padding(horizontal = 16.dp).fillMaxHeight().background(colors.foreground))
            }
        }
    }
}
