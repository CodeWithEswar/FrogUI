package io.github.codewitheswar.frogui.navigation

import java.net.URI

internal data class ComponentDeepLink(val componentId: String, val sequence: Long)

/** A single route grammar for cold/warm links; unknown valid IDs use the shared error state. */
internal fun componentIdFromDeepLink(value: String?): String? = runCatching {
    val uri = URI(value ?: return null)
    if (uri.scheme != "frogui" || uri.host != "components" || uri.userInfo != null || uri.port != -1 || uri.query != null || uri.fragment != null) return null
    uri.path.removePrefix("/").takeIf { it.matches(Regex("[a-z][a-z0-9-]*")) }
}.getOrNull()
