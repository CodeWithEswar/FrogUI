package io.github.codewitheswar.frogui.showcase.markdown

import io.github.codewitheswar.frogui.showcase.code.CodeLanguage
import org.commonmark.ext.gfm.tables.*
import org.commonmark.node.*
import org.commonmark.parser.Parser
import java.net.URI

/** Parser ownership is local to its caller. No global mutable AST or HTML rendering engine. */
internal class MarkdownParser {
    private val parser = Parser.builder().extensions(listOf(TablesExtension.create())).build()
    fun parse(source: String): MarkdownDocument = runCatching {
        MarkdownDocument(blocks(parser.parse(source)))
    }.getOrElse { MarkdownDocument(listOf(MarkdownBlock.Paragraph(listOf(MarkdownInline(source)))), parseFailed = true) }

    private fun children(node: Node): List<Node> = buildList {
        var child = node.firstChild
        while (child != null) { add(child); child = child.next }
    }
    private fun blocks(node: Node): List<MarkdownBlock> = children(node).flatMap { block ->
        when (block) {
            is Heading -> listOf(MarkdownBlock.Heading(block.level, inline(block)))
            is Paragraph -> listOf(MarkdownBlock.Paragraph(inline(block)))
            is FencedCodeBlock -> listOf(MarkdownBlock.Code(block.literal.removeSuffix("\n"), CodeLanguage.fromFence(block.info)))
            is IndentedCodeBlock -> listOf(MarkdownBlock.Code(block.literal.removeSuffix("\n"), CodeLanguage.PlainText))
            is BulletList -> listOf(MarkdownBlock.ListBlock(children(block).map(::blocks)))
            is OrderedList -> listOf(MarkdownBlock.ListBlock(children(block).map(::blocks), block.markerStartNumber))
            is BlockQuote -> listOf(MarkdownBlock.Quote(blocks(block)))
            is ThematicBreak -> listOf(MarkdownBlock.Divider)
            is TableBlock -> {
                val rows = children(block).flatMap(::children).map { row -> children(row).map(::inline) }
                listOf(MarkdownBlock.Table(rows.firstOrNull().orEmpty(), rows.drop(1)))
            }
            is HtmlBlock -> listOf(MarkdownBlock.Paragraph(listOf(MarkdownInline(block.literal))))
            else -> blocks(block)
        }
    }
    private fun inline(node: Node, style: MarkdownInline = MarkdownInline("")): List<MarkdownInline> = children(node).flatMap { child ->
        when (child) {
            is Text -> listOf(style.copy(text = child.literal))
            is Code -> listOf(style.copy(text = child.literal, code = true))
            is StrongEmphasis -> inline(child, style.copy(bold = true))
            is Emphasis -> inline(child, style.copy(italic = true))
            is Link -> inline(child, style.copy(link = safeDocumentationUrl(child.destination)))
            is SoftLineBreak -> listOf(style.copy(text = " "))
            is HardLineBreak -> listOf(style.copy(text = "\n"))
            is HtmlInline -> listOf(style.copy(text = child.literal))
            else -> inline(child, style)
        }
    }
}

/** Only ordinary web links are actionable. Raw HTML and unsupported URLs remain text. */
internal fun safeDocumentationUrl(value: String): String? = runCatching {
    val uri = URI(value)
    value.takeIf { uri.scheme?.lowercase() in listOf("https", "http") && !uri.host.isNullOrBlank() && uri.userInfo == null }
}.getOrNull()
