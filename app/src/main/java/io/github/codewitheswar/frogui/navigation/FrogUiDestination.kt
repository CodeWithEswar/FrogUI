package io.github.codewitheswar.frogui.navigation

/**
 * Top-level and detail destinations within the FrogUI Showcase application.
 */
sealed class FrogUiDestination(val route: String, val title: String) {
    data object Home : FrogUiDestination("home", "Home")
    data object Components : FrogUiDestination("components", "Components")
    data object Playground : FrogUiDestination("playground", "Playground")
    data object Foundation : FrogUiDestination("foundation", "Foundation")
    data object About : FrogUiDestination("about", "About")
    data object Settings : FrogUiDestination("settings", "Settings")
    data class ComponentDetail(val componentId: String) : FrogUiDestination("components/$componentId", "Component Detail")

    companion object {
        fun fromRoute(route: String): FrogUiDestination = when (route) {
            Home.route -> Home
            Components.route -> Components
            Playground.route -> Playground
            Foundation.route -> Foundation
            Settings.route -> Settings
            About.route -> About
            else -> if (route.startsWith("components/")) ComponentDetail(route.removePrefix("components/")) else Home
        }
    }
}
