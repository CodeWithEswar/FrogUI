package io.github.codewitheswar.frogui.showcase.branding

/**
 * Visual appearance variant for the FrogUI brand mark.
 */
internal enum class FrogUiLogoVariant {
    /** Adapts dynamically to the active FrogTheme (Dark on dark theme, Light on light theme). */
    Auto,

    /** Canonical dark badge: near-black container (#09090B) with white geometric frog mark. */
    Dark,

    /** Inverted light badge: white container with dark frog mark for light cards/surfaces. */
    Light,

    /** Single-tone tintable mark adapting to context or custom tint parameter. */
    Monochrome
}
