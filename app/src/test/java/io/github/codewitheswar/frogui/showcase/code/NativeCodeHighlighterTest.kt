package io.github.codewitheswar.frogui.showcase.code

import org.junit.Assert.*
import org.junit.Test

class NativeCodeHighlighterTest {
    private val engine = NativeCodeHighlighter()
    @Test fun kotlinKeywordsStringsNumbersAndCommentsUseSourceOffsets() {
        val code = "val name = \"Frog\"\n// val is a comment\nval count = 42"
        val tokens = engine.highlight(code, CodeLanguage.Kotlin)
        fun has(kind: TokenKind, text: String) = tokens.any { it.kind == kind && code.substring(it.start, it.end).contains(text) }
        assertTrue(has(TokenKind.Keyword, "val"))
        assertTrue(has(TokenKind.String, "Frog"))
        assertTrue(has(TokenKind.Number, "42"))
        assertTrue(has(TokenKind.Comment, "val is a comment"))
        assertTrue(tokens.all { it.start >= 0 && it.end <= code.length && it.start < it.end })
        assertEquals(tokens, engine.highlight(code, CodeLanguage.KotlinDsl))
    }
    @Test fun documentScannerHonorsEscapesAndUnclosedXmlComments() {
        val json = "{\"key\": \"a\\\"b\", \"enabled\": true, \"size\": -1.5e+2}"
        val jsonTokens = engine.highlight(json, CodeLanguage.Json)
        assertTrue(jsonTokens.any { it.kind == TokenKind.String && json.substring(it.start, it.end) == "\"a\\\"b\"" })
        assertTrue(jsonTokens.any { it.kind == TokenKind.Number && json.substring(it.start, it.end) == "-1.5e+2" })
        val xml = "<node value=\"hi\"/><!-- never closed"
        val xmlTokens = engine.highlight(xml, CodeLanguage.Xml)
        assertEquals(xml.length, xmlTokens.last().end)
        assertEquals(TokenKind.Comment, xmlTokens.last().kind)
    }
    @Test fun shellMarkdownAndPlainTextHaveExplicitLanguageBehavior() {
        assertTrue(engine.highlight("# comment\necho \"Frog\"", CodeLanguage.Shell).any { it.kind == TokenKind.Comment })
        assertTrue(engine.highlight("# Heading\nUse `FrogTheme`", CodeLanguage.Markdown).any { it.kind == TokenKind.String })
        assertTrue(engine.highlight("val a = 1", CodeLanguage.PlainText).isEmpty())
        assertEquals(CodeLanguage.KotlinDsl, CodeLanguage.fromFence("kts title=build.gradle.kts"))
        assertEquals(CodeLanguage.PlainText, CodeLanguage.fromFence("unknown"))
    }
    @Test fun largeInputsSafelyRetainPlainSource() {
        assertTrue(engine.highlight("x".repeat(100_001), CodeLanguage.Kotlin).isEmpty())
    }
}
