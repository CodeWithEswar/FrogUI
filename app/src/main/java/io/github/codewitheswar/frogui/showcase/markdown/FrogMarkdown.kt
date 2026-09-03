package io.github.codewitheswar.frogui.showcase.markdown

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.showcase.code.FrogCodeSnippet
import io.github.codewitheswar.frogui.theme.FrogTheme

@Composable
internal fun FrogMarkdown(markdown: String, modifier: Modifier = Modifier) {
    val document = remember(markdown) { MarkdownParser().parse(markdown) }
    FrogMarkdownDocument(document, modifier)
}

@Composable
internal fun FrogMarkdownDocument(document: MarkdownDocument, modifier: Modifier = Modifier) {
    if (document.blocks.isEmpty()) {
        Column(modifier, verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text("Documentation unavailable", color = FrogTheme.colors.foreground, style = FrogTheme.typography.subheading)
            Text("This component does not have documentation yet.", color = FrogTheme.colors.mutedForeground, style = FrogTheme.typography.body)
        }
    } else Column(modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        document.blocks.forEach { MarkdownBlockView(it) }
    }
}

@Composable
private fun MarkdownBlockView(block: MarkdownBlock) {
    when (block) {
        is MarkdownBlock.Heading -> FrogMarkdownHeading(block)
        is MarkdownBlock.Paragraph -> FrogMarkdownParagraph(block.content)
        is MarkdownBlock.Code -> FrogCodeSnippet(block.source, language = block.language)
        is MarkdownBlock.ListBlock -> FrogMarkdownList(block)
        is MarkdownBlock.Quote -> FrogMarkdownQuote(block)
        MarkdownBlock.Divider -> HorizontalDivider(color = FrogTheme.colors.border, modifier = Modifier.padding(vertical = 4.dp))
        is MarkdownBlock.Table -> MarkdownTable(block)
    }
}

@Composable
internal fun FrogMarkdownHeading(heading: MarkdownBlock.Heading) {
    val style = when (heading.level) { 1 -> FrogTheme.typography.titleLarge; 2 -> FrogTheme.typography.heading; else -> FrogTheme.typography.subheading }
    MarkdownText(heading.content, Modifier.padding(top = 8.dp).semantics { heading() }, style)
}

@Composable
internal fun FrogMarkdownParagraph(content: List<MarkdownInline>) = MarkdownText(content, style = FrogTheme.typography.body)

@Composable
internal fun FrogMarkdownList(list: MarkdownBlock.ListBlock) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        list.items.forEachIndexed { index, blocks ->
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                if (list.start != null) Text("${list.start + index}.", Modifier.widthIn(min = 20.dp), style = FrogTheme.typography.body, color = FrogTheme.colors.mutedForeground)
                else Box(Modifier.padding(top = 9.dp, start = 7.dp, end = 7.dp).size(4.dp).background(FrogTheme.colors.mutedForeground, FrogTheme.shapes.full))
                Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) { blocks.forEach { MarkdownBlockView(it) } }
            }
        }
    }
}

@Composable
internal fun FrogMarkdownQuote(quote: MarkdownBlock.Quote) {
    val colors = FrogTheme.colors
    Column(Modifier.fillMaxWidth().background(colors.subtleSurface).drawBehind { drawLine(colors.borderStrong, Offset.Zero, Offset(0f, size.height), 3.dp.toPx()) }
        .padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) { quote.blocks.forEach { MarkdownBlockView(it) } }
}

@Composable
private fun MarkdownTable(table: MarkdownBlock.Table) {
    Column(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState())) {
        (listOf(table.header) + table.rows).forEachIndexed { index, cells ->
            Row(Modifier.background(if (index == 0) FrogTheme.colors.muted else FrogTheme.colors.background)) {
                cells.forEach { cell -> Box(Modifier.width(180.dp).padding(12.dp)) { MarkdownText(cell, style = FrogTheme.typography.bodySmall) } }
            }
            HorizontalDivider(color = FrogTheme.colors.border)
        }
    }
}

@Composable
private fun MarkdownText(inlines: List<MarkdownInline>, modifier: Modifier = Modifier, style: TextStyle) {
    val colors = FrogTheme.colors
    val uriHandler = LocalUriHandler.current
    val annotated = remember(inlines, colors, uriHandler) {
        buildAnnotatedString {
            inlines.forEach { part ->
                val span = SpanStyle(
                    fontWeight = if (part.bold) FontWeight.SemiBold else null,
                    fontStyle = if (part.italic) FontStyle.Italic else null,
                    fontFamily = if (part.code) FontFamily.Monospace else null,
                    background = if (part.code) colors.muted else androidx.compose.ui.graphics.Color.Unspecified,
                )
                withStyle(span) {
                    val url = part.link
                    if (url == null) append(part.text)
                    else withLink(LinkAnnotation.Url(url, TextLinkStyles(
                        style = SpanStyle(color = colors.foreground, textDecoration = TextDecoration.Underline),
                        focusedStyle = SpanStyle(background = colors.muted, fontWeight = FontWeight.Bold),
                    )) { runCatching { uriHandler.openUri(url) } }) { append(part.text) }
                }
            }
        }
    }
    SelectionContainer { Text(annotated, modifier, color = colors.foreground, style = style) }
}
