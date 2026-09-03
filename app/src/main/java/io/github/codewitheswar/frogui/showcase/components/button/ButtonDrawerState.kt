package io.github.codewitheswar.frogui.showcase.components.button

import androidx.compose.runtime.saveable.listSaver

internal sealed interface ButtonDrawerPage {
    data object Properties : ButtonDrawerPage
    data object Preview : ButtonDrawerPage
    data class Color(val property: ButtonColorProperty) : ButtonDrawerPage
    data class Api(val property: String) : ButtonDrawerPage
}

/** One stack means a nested picker replaces its parent panel instead of adding a modal. */
internal data class ButtonDrawerState(val pages: List<ButtonDrawerPage> = emptyList()) {
    val current get() = pages.lastOrNull()
    fun open(page: ButtonDrawerPage) = ButtonDrawerState(listOf(page))
    fun push(page: ButtonDrawerPage) = if (current == page) this else copy(pages = pages + page)
    fun back() = copy(pages = pages.dropLast(1))
    companion object {
        val saver = listSaver<ButtonDrawerState, String>(save = { state -> state.pages.map { page -> when (page) {
            ButtonDrawerPage.Properties -> "properties"
            ButtonDrawerPage.Preview -> "preview"
            is ButtonDrawerPage.Color -> "color:${page.property.name}"
            is ButtonDrawerPage.Api -> "api:${page.property}"
        } } }, restore = { routes -> ButtonDrawerState(routes.mapNotNull { route -> when {
            route == "properties" -> ButtonDrawerPage.Properties
            route == "preview" -> ButtonDrawerPage.Preview
            route.startsWith("color:") -> runCatching { ButtonDrawerPage.Color(ButtonColorProperty.valueOf(route.substringAfter(':'))) }.getOrNull()
            route.startsWith("api:") -> ButtonDrawerPage.Api(route.substringAfter(':'))
            else -> null
        } }) })
    }
}
