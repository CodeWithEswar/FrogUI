package io.github.codewitheswar.frogui.showcase.drawer

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.components.overlays.drawer.FrogDrawerPresentation
import io.github.codewitheswar.frogui.showcase.style.ShowcaseBackButton
import io.github.codewitheswar.frogui.showcase.icons.FrogIcons
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
        presentation = if (side) FrogDrawerPresentation.Side else FrogDrawerPresentation.Bottom,
        navigationIcon = if (onBack != null) ({ ShowcaseBackButton(onClick = onBack, label = "Back within drawer") }) else null,
        onBackRequest = onBack,
        closeIcon = { Icon(FrogIcons.Close, null, Modifier.size(18.dp)) },
        preview = preview,
        footer = actions?.let { slot -> ({ Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp), content = slot) }) },
        content = content
    )
}
