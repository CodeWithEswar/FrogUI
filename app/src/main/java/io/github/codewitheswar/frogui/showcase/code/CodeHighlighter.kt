package io.github.codewitheswar.frogui.showcase.code

import dev.snipme.highlights.Highlights
import dev.snipme.highlights.model.PhraseLocation
import dev.snipme.highlights.model.SyntaxLanguage

internal enum class CodeLanguage(val label: String) {
    Kotlin("Kotlin"), KotlinDsl("Gradle Kotlin DSL"), Json("JSON"), Xml("XML"), Markdown("Markdown"), Shell("Shell"), PlainText("Text");
    companion object {
        fun fromFence(info: String) = when (info.trim().substringBefore(' ').lowercase()) {
            "kotlin", "kt" -> Kotlin; "kts", "gradle", "kotlin-dsl" -> KotlinDsl
            "json" -> Json; "xml", "html" -> Xml; "md", "markdown" -> Markdown
            "sh", "bash", "shell", "console" -> Shell; else -> PlainText
        }
    }
}
internal enum class TokenKind { Keyword, String, Number, Comment, Annotation, Type, Function, Plain }
internal data class CodeToken(val start: Int, val end: Int, val kind: TokenKind)
internal fun interface CodeHighlighter { fun highlight(code: String, language: CodeLanguage): List<CodeToken> }

/** Pure token API isolates the native engine from Compose and semantic color choices. */
internal class NativeCodeHighlighter : CodeHighlighter {
    override fun highlight(code: String, language: CodeLanguage): List<CodeToken> {
        if (language == CodeLanguage.PlainText || code.length > 100_000) return emptyList()
        if (language !in listOf(CodeLanguage.Kotlin, CodeLanguage.KotlinDsl, CodeLanguage.Shell)) return DocumentCodeLexer.highlight(code, language)
        val syntax = if (language == CodeLanguage.Shell) SyntaxLanguage.SHELL else SyntaxLanguage.KOTLIN
        val structure = Highlights.Builder().code(code).language(syntax).build().getCodeStructure()
        return buildList {
            fun append(locations: Set<PhraseLocation>, kind: TokenKind) {
                locations.filter { it.start >= 0 && it.end <= code.length && it.end > it.start }
                    .forEach { add(CodeToken(it.start, it.end, kind)) }
            }
            append(structure.keywords, TokenKind.Keyword)
            append(structure.strings, TokenKind.String)
            append(structure.literals, TokenKind.Number)
            append(structure.annotations, TokenKind.Annotation)
            append(structure.comments, TokenKind.Comment)
            append(structure.multilineComments, TokenKind.Comment)
        }
    }
}
