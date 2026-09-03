package io.github.codewitheswar.frogui.components.button

/**
 * Visual semantic variant for [FrogButton].
 */
enum class FrogButtonVariant {
    /** High-contrast primary action. Solid primary surface with inverse text. */
    Primary,

    /** Tonal secondary action. Subtle neutral surface with primary text. */
    Secondary,

    /** Outlined action with a visible structural border and transparent surface. */
    Outline,

    /** Low-emphasis ghost action without background or border until pressed/hovered. */
    Ghost,

    /** High-priority destructive action communicating permanent or dangerous operations. */
    Destructive
}
