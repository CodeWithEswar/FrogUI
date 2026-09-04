package io.github.codewitheswar.frogui.theme

import androidx.compose.ui.graphics.Color
import io.github.codewitheswar.frogui.foundation.color.FrogColors
import io.github.codewitheswar.frogui.foundation.color.FrogPalette

/**
 * Standard default configurations for FrogUI themes.
 */
object FrogThemeDefaults {

    /**
     * Canonical dark foundation.
     * High-contrast, engineered zinc aesthetic.
     */
    fun darkColors(): FrogColors = FrogColors(
        background = FrogPalette.Zinc950,           // #09090B
        foreground = FrogPalette.Zinc50,            // #FAFAFA
        surface = Color(0xFF0C0C0E),
        surfaceElevated = Color(0xFF111113),
        subtleSurface = FrogPalette.Zinc900,        // #18181B
        muted = FrogPalette.Zinc800,                // #27272A
        mutedForeground = FrogPalette.Zinc400,      // #A1A1AA
        border = Color(0x14FFFFFF),                 // rgba(255, 255, 255, 0.08)
        borderStrong = Color(0x24FFFFFF),           // rgba(255, 255, 255, 0.14)
        primary = FrogPalette.Zinc50,               // #FAFAFA
        primaryForeground = FrogPalette.Zinc950,     // #09090B
        secondary = FrogPalette.Zinc800,            // #27272A
        secondaryForeground = FrogPalette.Zinc50,   // #FAFAFA
        destructive = FrogPalette.DestructiveDark,  // #DC2626
        destructiveForeground = FrogPalette.White,
        focusRing = FrogPalette.Zinc400,
        isDark = true
    )

    /**
     * Canonical light foundation.
     * Crisp, clean monochrome surfaces with subtle borders.
     */
    fun lightColors(): FrogColors = FrogColors(
        background = FrogPalette.White,             // #FFFFFF
        foreground = FrogPalette.Zinc950,           // #09090B
        surface = FrogPalette.Zinc50,               // #FAFAFA
        surfaceElevated = FrogPalette.White,        // #FFFFFF
        subtleSurface = FrogPalette.Zinc100,        // #F4F4F5
        muted = FrogPalette.Zinc200,                // #E4E4E7
        mutedForeground = FrogPalette.Zinc600,      // #52525B; readable on muted surfaces too
        border = Color(0x14000000),                 // rgba(0, 0, 0, 0.08)
        borderStrong = Color(0x24000000),           // rgba(0, 0, 0, 0.14)
        primary = FrogPalette.Zinc950,              // #09090B
        primaryForeground = FrogPalette.White,
        secondary = FrogPalette.Zinc100,            // #F4F4F5
        secondaryForeground = FrogPalette.Zinc900,  // #18181B
        destructive = FrogPalette.DestructiveDark,  // #DC2626; white label contrast 4.83:1
        destructiveForeground = FrogPalette.White,
        focusRing = FrogPalette.Zinc600,
        isDark = false
    )
}
