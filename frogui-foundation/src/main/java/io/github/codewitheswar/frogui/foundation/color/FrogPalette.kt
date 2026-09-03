package io.github.codewitheswar.frogui.foundation.color

import androidx.compose.ui.graphics.Color

/**
 * Raw FrogUI design palette tokens.
 * A disciplined, developer-focused monochrome foundation built on curated neutral zinc shades.
 */
object FrogPalette {
    val Black: Color = Color(0xFF000000)
    val White: Color = Color(0xFFFFFFFF)

    // Curated Zinc Scale
    val Zinc950: Color = Color(0xFF09090B)
    val Zinc900: Color = Color(0xFF18181B)
    val Zinc800: Color = Color(0xFF27272A)
    val Zinc700: Color = Color(0xFF3F3F46)
    val Zinc600: Color = Color(0xFF52525B)
    val Zinc500: Color = Color(0xFF71717A)
    val Zinc400: Color = Color(0xFFA1A1AA)
    val Zinc300: Color = Color(0xFFD4D4D8)
    val Zinc200: Color = Color(0xFFE4E4E7)
    val Zinc100: Color = Color(0xFFF4F4F5)
    val Zinc50: Color = Color(0xFFFAFAFA)

    // Semantic accents (functional, high-contrast)
    val Destructive: Color = Color(0xFFEF4444)
    val DestructiveDark: Color = Color(0xFFDC2626)
    val Success: Color = Color(0xFF10B981)
    val Warning: Color = Color(0xFFF59E0B)
}
