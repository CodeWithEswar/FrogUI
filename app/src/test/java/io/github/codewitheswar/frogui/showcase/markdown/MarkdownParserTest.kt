package io.github.codewitheswar.frogui.showcase.markdown

import io.github.codewitheswar.frogui.showcase.code.CodeLanguage
import org.junit.Assert.*
import org.junit.Test

class MarkdownParserTest {
    @Test fun parsesNativeDocumentElementsAndFenceLanguage() {
        val source = """
            # Heading
            A **strong** and *soft* paragraph with `code`.

            ## Accessibility
            - First
              - Nested
            - Second

            3. Third
            4. Fourth

            > A quote

            ---

            ```kotlin
            val count = 1
            ```

            | Property | Default |
            | --- | --- |
            | size | Medium |
        """.trimIndent()
        val result = MarkdownParser().parse(source)
        assertFalse(result.parseFailed)
        val paragraph = result.blocks.filterIsInstance<MarkdownBlock.Paragraph>().first()
        assertTrue(paragraph.content.any { it.bold && it.text == "strong" })
        assertTrue(paragraph.content.any { it.italic && it.text == "soft" })
        assertTrue(paragraph.content.any { it.code && it.text == "code" })
        assertEquals(3, result.blocks.filterIsInstance<MarkdownBlock.ListBlock>().last().start)
        assertEquals(CodeLanguage.Kotlin, result.blocks.filterIsInstance<MarkdownBlock.Code>().single().language)
        assertEquals(2, result.blocks.filterIsInstance<MarkdownBlock.Table>().single().header.size)
        assertTrue(result.blocks.any { it is MarkdownBlock.Quote })
        assertTrue(result.blocks.contains(MarkdownBlock.Divider))
        assertTrue(result.section("Accessibility").blocks.isNotEmpty())
    }
    @Test fun unsafeLinksAndHtmlRemainInertReadableText() {
        assertNull(safeDocumentationUrl("javascript:alert(1)"))
        assertNull(safeDocumentationUrl("intent://host/#Intent;end"))
        assertNull(safeDocumentationUrl("https://user:pass@example.com"))
        assertEquals("https://example.com/docs", safeDocumentationUrl("https://example.com/docs"))
        val result = MarkdownParser().parse("[safe](https://example.com) [unsafe](data:text/plain,hi)\n\n<script>alert(1)</script>")
        val links = result.blocks.filterIsInstance<MarkdownBlock.Paragraph>().first().content
        assertEquals("https://example.com", links.first { it.text == "safe" }.link)
        assertNull(links.first { it.text == "unsafe" }.link)
        assertTrue(result.blocks.filterIsInstance<MarkdownBlock.Paragraph>().last().content.single().text.contains("<script>"))
    }
    @Test fun unknownFenceAndMissingSectionsAreContained() {
        val document = MarkdownParser().parse("```unknown\nraw\n```")
        assertEquals(CodeLanguage.PlainText, (document.blocks.single() as MarkdownBlock.Code).language)
        assertTrue(document.section("Missing").blocks.isEmpty())
        assertTrue(MarkdownParser().parse("").blocks.isEmpty())
    }
}
