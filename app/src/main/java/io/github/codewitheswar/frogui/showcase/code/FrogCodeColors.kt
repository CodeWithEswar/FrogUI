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
            colors.surfaceElevated, colors.foreground, Color(0xFFD8B4FE), Color(0xFFA7D8B5), Color(0xFFE8C38E),
            colors.mutedForeground, colors.foreground, Color(0xFFB6CBDD), colors.border,
        ) else FrogCodeColors(
            colors.surfaceElevated, colors.foreground, Color(0xFF6B328F), Color(0xFF25633C), Color(0xFF794C12),
            colors.mutedForeground, colors.foreground, Color(0xFF315572), colors.border,
        )
    }
}
