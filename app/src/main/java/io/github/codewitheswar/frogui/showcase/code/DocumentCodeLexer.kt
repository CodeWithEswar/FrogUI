package io.github.codewitheswar.frogui.showcase.code

/** Bounded character scanner for document languages absent from Highlights 1.1.
 * It colors lexical categories only; it is not a validator or a compiler.
 */
internal object DocumentCodeLexer : CodeHighlighter {
    override fun highlight(code: String, language: CodeLanguage): List<CodeToken> = buildList {
        var i = 0
        var inTag = false
        fun emit(start: Int, kind: TokenKind) { if (i > start) add(CodeToken(start, i, kind)) }
        fun consumeQuoted(quote: Char) {
            i++
            while (i < code.length) {
                if (code[i] == '\\') { i = (i + 2).coerceAtMost(code.length); continue }
                if (code[i++] == quote) break
            }
        }
        while (i < code.length) {
            val start = i
            val ch = code[i]
            when {
                language == CodeLanguage.Xml && code.startsWith("<!--", i) -> {
                    val end = code.indexOf("-->", i + 4)
                    i = if (end < 0) code.length else end + 3
                    emit(start, TokenKind.Comment)
                }
                language == CodeLanguage.Xml && ch == '<' -> {
                    inTag = true; i++
                    if (i < code.length && code[i] in "/?!") i++
                    while (i < code.length && (code[i].isLetterOrDigit() || code[i] in "_:-")) i++
                    emit(start, TokenKind.Keyword)
                }
                language == CodeLanguage.Xml && ch == '>' -> { inTag = false; i++ }
                (language == CodeLanguage.Json || (language == CodeLanguage.Xml && inTag)) && ch in "\"'" -> {
                    consumeQuoted(ch)
                    var after = i
                    while (after < code.length && code[after].isWhitespace()) after++
                    emit(start, if (language == CodeLanguage.Json && after < code.length && code[after] == ':') TokenKind.Keyword else TokenKind.String)
                }
                language == CodeLanguage.Json && (ch.isDigit() || ch == '-') -> {
                    i++
                    while (i < code.length && (code[i].isDigit() || code[i] in ".eE+-")) i++
                    emit(start, TokenKind.Number)
                }
                language == CodeLanguage.Json && ch.isLetter() -> {
                    while (i < code.length && code[i].isLetter()) i++
                    if (code.substring(start, i) in listOf("true", "false", "null")) emit(start, TokenKind.Keyword)
                }
                language == CodeLanguage.Markdown && ch == '`' -> {
                    var count = 0
                    while (i < code.length && code[i] == '`') { count++; i++ }
                    val delimiter = "`".repeat(count)
                    val end = code.indexOf(delimiter, i)
                    i = if (end < 0) code.length else end + count
                    emit(start, TokenKind.String)
                }
                language == CodeLanguage.Markdown && (i == 0 || code[i - 1] == '\n') && ch == '#' -> {
                    while (i < code.length && code[i] != '\n') i++
                    emit(start, TokenKind.Keyword)
                }
                language == CodeLanguage.Markdown && ch in "*_[]()>" -> { i++; emit(start, TokenKind.Keyword) }
                else -> i++
            }
        }
    }
}
