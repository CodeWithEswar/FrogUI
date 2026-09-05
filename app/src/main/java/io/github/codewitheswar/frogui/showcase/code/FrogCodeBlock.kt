package io.github.codewitheswar.frogui.showcase.code

import android.content.ClipData
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.codewitheswar.frogui.components.button.FrogIconButton
import io.github.codewitheswar.frogui.components.button.FrogIconButtonDefaults
import io.github.codewitheswar.frogui.components.button.FrogIconButtonSize
import io.github.codewitheswar.frogui.components.button.FrogIconButtonVariant
import io.github.codewitheswar.frogui.showcase.icons.FrogIcons
import io.github.codewitheswar.frogui.theme.FrogTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal enum class CodePresentation { Block, Compact, Expandable }

@Composable
internal fun FrogCodeBlock(code: String, modifier: Modifier = Modifier, language: CodeLanguage = CodeLanguage.Kotlin,
    filename: String? = null, lineNumbers: Boolean = true, presentation: CodePresentation = CodePresentation.Block) {
    val colors = FrogCodeColors.from(FrogTheme.colors)
    val shape = FrogTheme.shapes.md
    var expanded by rememberSaveable(code) { mutableStateOf(false) }
    val lines = remember(code) { code.split('\n') }
    val expandable = presentation == CodePresentation.Expandable && lines.size > 14
    val visibleCode = if (expandable && !expanded) lines.take(12).joinToString("\n") else code
    val tokens by produceState(emptyList<CodeToken>(), visibleCode, language) {
        value = emptyList()
        value = withContext(Dispatchers.Default) { runCatching { NativeCodeHighlighter().highlight(visibleCode, language) }.getOrDefault(emptyList()) }
    }
    val annotated = remember(visibleCode, tokens, colors) {
        buildAnnotatedString {
            append(visibleCode)
            tokens.filter { it.start >= 0 && it.end <= visibleCode.length }.forEach {
                addStyle(SpanStyle(color = colors.color(it.kind)), it.start, it.end)
            }
        }
    }
    Column(modifier.fillMaxWidth().clip(shape).background(colors.background).border(1.dp, colors.border, shape)) {
        FrogCodeToolbar(code, filename ?: language.label, expandable, expanded, { expanded = !expanded })
        HorizontalDivider(color = colors.border)
        Row(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()).padding(if (presentation == CodePresentation.Compact) 10.dp else 14.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            val textStyle = FrogTheme.typography.code
            if (lineNumbers && presentation != CodePresentation.Compact) DisableSelection {
                Text((1..visibleCode.split('\n').size).joinToString("\n"), color = colors.comment, style = textStyle, softWrap = false,
                    modifier = Modifier.clearAndSetSemantics {})
            }
            SelectionContainer { Text(annotated, color = colors.foreground, style = textStyle, softWrap = false) }
        }
        if (expandable && !expanded) Text("${lines.size - 12} more lines", Modifier.padding(start = 14.dp, bottom = 12.dp), style = FrogTheme.typography.bodySmall, color = colors.comment)
    }
}

@Composable
internal fun FrogCodeToolbar(code: String, title: String, expandable: Boolean, expanded: Boolean, onExpand: () -> Unit, modifier: Modifier = Modifier) {
    val clipboard = LocalClipboard.current
    val scope = rememberCoroutineScope()
    var copyCount by remember(code) { mutableIntStateOf(0) }
    var copied by remember(code) { mutableStateOf(false) }
    LaunchedEffect(copyCount) { if (copyCount > 0) { copied = true; delay(1800); copied = false } }
    Row(modifier.fillMaxWidth().heightIn(min = FrogTheme.sizing.minimumTouchTarget).padding(start = 14.dp, end = 4.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(title, Modifier.weight(1f), style = FrogTheme.typography.bodySmall, color = FrogTheme.colors.mutedForeground, maxLines = 1, overflow = TextOverflow.Ellipsis)
        if (copied) Text("Copied", Modifier.semantics { liveRegion = LiveRegionMode.Polite }, style = FrogTheme.typography.bodySmall, color = FrogTheme.colors.foreground)
        if (expandable) {
            FrogIconButton(
                icon = {
                    Icon(
                        imageVector = if (expanded) FrogIcons.Collapse else FrogIcons.Expand,
                        contentDescription = null,
                        modifier = Modifier.size(FrogIconButtonDefaults.iconSize(FrogIconButtonSize.Small))
                    )
                },
                contentDescription = if (expanded) "Collapse code" else "Expand code",
                onClick = onExpand,
                variant = FrogIconButtonVariant.Ghost,
                size = FrogIconButtonSize.Small
            )
        }
        FrogIconButton(
            icon = {
                Icon(
                    imageVector = if (copied) FrogIcons.Check else FrogIcons.Copy,
                    contentDescription = null,
                    modifier = Modifier.size(FrogIconButtonDefaults.iconSize(FrogIconButtonSize.Small))
                )
            },
            contentDescription = if (copied) "Copied code" else "Copy code",
            onClick = {
                scope.launch { clipboard.setClipEntry(ClipEntry(ClipData.newPlainText(title, code))); copyCount++ }
            },
            variant = FrogIconButtonVariant.Ghost,
            size = FrogIconButtonSize.Small
        )
    }
}

@Composable
internal fun FrogInlineCode(code: String, modifier: Modifier = Modifier) {
    SelectionContainer { Text(code, modifier.clip(FrogTheme.shapes.xs).background(FrogTheme.colors.muted).padding(horizontal = 5.dp, vertical = 2.dp), style = FrogTheme.typography.code, color = FrogTheme.colors.foreground) }
}

@Composable
internal fun FrogCodeSnippet(code: String, modifier: Modifier = Modifier, language: CodeLanguage = CodeLanguage.Kotlin, filename: String? = null) =
    FrogCodeBlock(code, modifier, language, filename, presentation = CodePresentation.Expandable)
