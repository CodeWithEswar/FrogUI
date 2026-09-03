package io.github.codewitheswar.frogui.registry

/**
 * Functional category for FrogUI components.
 */
enum class FrogComponentCategory(val displayName: String) {
    Actions("Actions"),
    Inputs("Inputs"),
    DataDisplay("Data Display"),
    Feedback("Feedback"),
    Navigation("Navigation"),
    Overlays("Overlays"),
    Layout("Layout")
}

/**
 * Production maturity status of a FrogUI component.
 */
enum class FrogComponentStatus(val label: String) {
    Stable("Stable"),
    Beta("Beta"),
    Experimental("Experimental"),
    Deprecated("Deprecated")
}
