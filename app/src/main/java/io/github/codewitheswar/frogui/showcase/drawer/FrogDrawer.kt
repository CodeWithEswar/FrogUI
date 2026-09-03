package io.github.codewitheswar.frogui.showcase.drawer

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.codewitheswar.frogui.components.overlays.drawer.FrogDrawer as CanonicalFrogDrawer

/**
 * Showcase delegation wrapper pointing directly to the canonical library [CanonicalFrogDrawer].
 *
 * This ensures no duplicate drawer implementation exists in the Showcase module while
 * preserving complete binary and source compatibility for Showcase consumers.
 */
@Composable
internal fun FrogDrawer(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    side: Boolean = false,
    onBack: (() -> Unit)? = null,
    preview: (@Composable () -> Unit)? = null,
    actions: (@Composable RowScope.() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    CanonicalFrogDrawer(
        visible = visible,
        onDismissRequest = onDismissRequest,
        title = title,
        modifier = modifier,
        subtitle = subtitle,
        side = side,
        onBack = onBack,
        preview = preview,
        actions = actions,
        content = content
    )
}
