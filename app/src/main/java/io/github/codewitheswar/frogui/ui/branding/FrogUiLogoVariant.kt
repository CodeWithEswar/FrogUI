package io.github.codewitheswar.frogui.ui.branding

/**
 * Visual variants for the official [FrogUiLogo] brand component.
 */
enum class FrogUiLogoVariant {
    /**
     * Automatically selects [Dark] when in dark theme (white mark on dark zinc container),
     * or [Light] when in light theme (dark zinc mark on white container).
     */
    Auto,

    /**
     * Dark zinc container (#09090B) with white frog mark. Canonical brand badge.
     */
    Dark,

    /**
     * Inverted light container (#FFFFFF) with dark zinc frog mark.
     */
    Light,

    /**
     * Standalone geometric frog mark without outer container badge.
     * Tinted with the current [androidx.compose.material3.LocalContentColor] or provided color.
     */
    Monochrome
}
