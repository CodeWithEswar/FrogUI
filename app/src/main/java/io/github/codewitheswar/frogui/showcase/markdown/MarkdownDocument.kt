package io.github.codewitheswar.frogui.showcase.markdown

import io.github.codewitheswar.frogui.showcase.code.CodeLanguage

internal data class MarkdownInline(val text: String, val bold: Boolean = false, val italic: Boolean = false, val code: Boolean = false, val link: String? = null)
internal sealed interface MarkdownBlock {
    data class Heading(val level: Int, val content: List<MarkdownInline>) : MarkdownBlock
    data class Paragraph(val content: List<MarkdownInline>) : MarkdownBlock
    data class Code(val source: String, val language: CodeLanguage) : MarkdownBlock
    data class ListBlock(val items: List<List<MarkdownBlock>>, val start: Int? = null) : MarkdownBlock
    data class Quote(val blocks: List<MarkdownBlock>) : MarkdownBlock
    data class Table(val header: List<List<MarkdownInline>>, val rows: List<List<List<MarkdownInline>>>) : MarkdownBlock
    data object Divider : MarkdownBlock
}
internal data class MarkdownDocument(val blocks: List<MarkdownBlock>, val parseFailed: Boolean = false) {
    fun withoutSection(title: String): MarkdownDocument {
        val start = blocks.indexOfFirst { it is MarkdownBlock.Heading && it.content.joinToString("") { part -> part.text }.equals(title, true) }
        if (start < 0) return this
        val level = (blocks[start] as MarkdownBlock.Heading).level
        val count = blocks.drop(start + 1).takeWhile { it !is MarkdownBlock.Heading || it.level > level }.size + 1
        return copy(blocks = blocks.take(start) + blocks.drop(start + count))
    }
    fun section(title: String): MarkdownDocument {
        val start = blocks.indexOfFirst { it is MarkdownBlock.Heading && it.content.joinToString("") { text -> text.text }.equals(title, true) }
        if (start < 0) return MarkdownDocument(emptyList())
        val level = (blocks[start] as MarkdownBlock.Heading).level
        return MarkdownDocument(blocks.drop(start + 1).takeWhile { it !is MarkdownBlock.Heading || it.level > level })
    }
}
