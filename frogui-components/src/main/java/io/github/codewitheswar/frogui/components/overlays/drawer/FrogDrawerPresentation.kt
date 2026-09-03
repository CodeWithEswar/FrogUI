package io.github.codewitheswar.frogui.components.overlays.drawer

/**
 * Presentation mode for [FrogDrawer].
 */
enum class FrogDrawerPresentation {
    /**
     * Dynamically chooses [Bottom] on compact screens (< 620dp) and [Side] on expanded screens (>= 620dp).
     */
    Auto,

    /**
     * Presents as a modal bottom sheet with drag handle and rounded top corners.
     */
    Bottom,

    /**
     * Presents as a contextual side panel docked to the screen edge.
     */
    Side
}

/**
 * Alignment edge for side drawer presentation.
 */
enum class FrogDrawerSide {
    /**
     * Docked to the start (left in LTR, right in RTL) of the screen.
     */
    Start,

    /**
     * Docked to the end (right in LTR, left in RTL) of the screen. Standard for contextual inspectors.
     */
    End
}
