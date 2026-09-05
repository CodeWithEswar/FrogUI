package io.github.codewitheswar.frogui.components.button

/**
 * Visual semantic variant for [FrogIconButton].
 *
 * Configures the container emphasis, border treatment, and contrast profile.
 */
enum class FrogIconButtonVariant {
    /** High-emphasis action with a solid primary container and high-contrast icon. */
    Filled,

    /** Medium-emphasis action with a subtle tonal surface and readable foreground icon. */
    Tonal,

    /** Medium-to-low emphasis action with a transparent surface and structural border. */
    Outline,

    /** Lowest-emphasis action with a transparent surface, suitable for dense toolbars. */
    Ghost
}
