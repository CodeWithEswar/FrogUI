package io.github.codewitheswar.frogui.showcase.code

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import io.github.codewitheswar.frogui.foundation.color.FrogColors

@Immutable
internal data class FrogCodeColors(val background: Color, val foreground: Color, val keyword: Color,
    val string: Color, val number: Color, val comment: Color, val function: Color, val type: Color, val border: Color) {
    fun color(kind: TokenKind) = when (kind) {
        TokenKind.Keyword, TokenKind.Annotation -> keyword
        TokenKind.String -> string
        TokenKind.Number -> number
        TokenKind.Comment -> comment
        TokenKind.Function -> function
        TokenKind.Type -> type
        TokenKind.Plain -> foreground
    }
    companion object {
        fun from(colors: FrogColors) = if (colors.isDark) FrogCodeColors(
            Color(0xFF101012), Color(0xFFE4E4E7), Color(0xFFD8B4FE), Color(0xFFA7D8B5), Color(0xFFE8C38E),
            Color(0xFFA1A1AA), Color(0xFFE4E4E7), Color(0xFFB6CBDD), colors.border,
        ) else FrogCodeColors(
            Color(0xFFF7F7F8), Color(0xFF27272A), Color(0xFF6B328F), Color(0xFF25633C), Color(0xFF794C12),
            Color(0xFF62626B), Color(0xFF27272A), Color(0xFF315572), colors.border,
        )
    }
}
