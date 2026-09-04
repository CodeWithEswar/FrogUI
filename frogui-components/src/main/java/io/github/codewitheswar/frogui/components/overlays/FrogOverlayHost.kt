package io.github.codewitheswar.frogui.components.overlays

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds

internal val LocalFrogOverlayHost = staticCompositionLocalOf { false }

/**
 * A bounded overlay environment for previews or embedded workspaces.
 * Supported overlays use this host's constraints instead of a platform modal window.
 * The caller supplies finite width/height and owns focus outside this embedded region.
 * FrogDrawer currently supports this environment. It does not trap application focus or install
 * a Back handler; the embedding caller handles dismissal and trigger focus restoration.
 * This is an Experimental companion API, independent of Showcase or registry models.
 *
 * @param modifier Supplies the host's finite bounds; content and scrims are clipped to them.
 * @param content Workspace and overlay content sharing those bounds.
 */
@Composable
fun FrogOverlayHost(modifier: Modifier = Modifier, content: @Composable BoxScope.() -> Unit) {
    CompositionLocalProvider(LocalFrogOverlayHost provides true) { Box(modifier.clipToBounds(), content = content) }
}
