package io.github.codewitheswar.frogui.navigation

/**
 * Top-level and detail destinations within the FrogUI Showcase application.
 */
sealed class FrogUiDestination(val route: String, val title: String) {
    data object Home : FrogUiDestination("home", "Home")
    data object Components : FrogUiDestination("components", "Components")
    data object Foundation : FrogUiDestination("foundation", "Foundation")
    data object About : FrogUiDestination("about", "About")
    data class ComponentDetail(val componentId: String) : FrogUiDestination("component/$componentId", "Component Detail")
}
