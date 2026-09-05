package io.github.codewitheswar.frogui.components.fab

/**
 * Visual presentation form of a [FrogFloatingActionButton].
 *
 * Controls the dimensional scale, label affordance, and layout geometry.
 */
enum class FrogFabPresentation {
    /**
     * Canonical 56dp floating action button for screen-level primary actions.
     * Contains only an icon and relies on [FrogFloatingActionButton]'s mandatory accessible name.
     */
    Regular,

    /**
     * Compact 40dp visual container for space-constrained or secondary floating actions.
     * Preserves a guaranteed 48dp minimum interactive touch target.
     */
    Small,

    /**
     * Prominent floating action button presenting both an icon and a visible text label.
     * Supports dynamic expansion and collapsing through the `expanded` parameter.
     */
    Extended
}
