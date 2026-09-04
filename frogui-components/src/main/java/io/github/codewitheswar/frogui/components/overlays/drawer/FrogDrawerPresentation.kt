package io.github.codewitheswar.frogui.components.overlays.drawer

/**
 * Placement within the available native window or bounded overlay host.
 * Both Bottom and Side are modal by default; this enum does not select modal/persistent behavior.
 */
enum class FrogDrawerPresentation {
    /**
     * Uses the local adaptive policy: [Bottom] for Compact and [Side] otherwise.
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
