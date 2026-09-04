package io.github.codewitheswar.frogui.navigation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.showcase.branding.FrogUiLogo
import io.github.codewitheswar.frogui.showcase.icons.FrogIcons
import io.github.codewitheswar.frogui.showcase.style.*
import io.github.codewitheswar.frogui.theme.FrogTheme
import io.github.codewitheswar.frogui.foundation.adaptive.FrogAdaptive
import io.github.codewitheswar.frogui.foundation.adaptive.FrogWindowSizeClass

internal typealias ShowcaseWindow = FrogWindowSizeClass
internal fun showcaseWindow(width: Dp, adaptive: FrogAdaptive = FrogAdaptive()) = adaptive.windowSizeClass(width)
internal data class ShowcaseDestination(val route: String, val label: String, val icon: ImageVector)
internal val showcaseDestinations get() = listOf(
    ShowcaseDestination("home", "Home", FrogIcons.Home),
    ShowcaseDestination("components", "Components", FrogIcons.Components),
    ShowcaseDestination("playground", "Playground", FrogIcons.Playground),
    ShowcaseDestination("foundation", "Foundation", FrogIcons.Foundation),
)

@Composable
internal fun AnimatedNavIcon(icon: ImageVector, selected: Boolean, modifier: Modifier = Modifier) {
    val motion = LocalFrogMotionEnabled.current
    val progress by animateFloatAsState(
        targetValue = if (selected) 1f else 0f,
        animationSpec = tween(durationMillis = if (motion) ShowcaseMotion.fast else 0),
        label = "navigation icon emphasis"
    )
    Icon(
        imageVector = icon,
        contentDescription = null,
        modifier = modifier
            .size(FrogTheme.sizing.iconLarge)
            .graphicsLayer {
                scaleX = if (motion) 1f + 0.06f * progress else 1f
                scaleY = scaleX
                translationY = if (motion) -1f * progress * density else 0f
            },
        tint = if (selected) FrogTheme.colors.foreground else FrogTheme.colors.mutedForeground
    )
}

@Composable
internal fun FrogShowcaseBottomBar(
    destinations: List<ShowcaseDestination>, selectedRoute: String, onDestinationClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = FrogTheme.colors
    val columns = if (LocalDensity.current.fontScale >= 1.5f) 2 else 4
    Column(modifier.fillMaxWidth().background(colors.surface).drawBehind { drawLine(colors.border, androidx.compose.ui.geometry.Offset.Zero, androidx.compose.ui.geometry.Offset(size.width, 0f), 1.dp.toPx()) }
        .navigationBarsPadding().padding(horizontal = 4.dp, vertical = 6.dp).selectableGroup().testTag("bottom-navigation")) {
        destinations.chunked(columns).forEach { row ->
            Row(Modifier.fillMaxWidth()) {
                row.forEach { destination ->
                    NavigationItem(destination, destination.route == selectedRoute, { onDestinationClick(destination.route) }, Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun NavigationItem(
    destination: ShowcaseDestination,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    horizontal: Boolean = false
) {
    val colors = FrogTheme.colors
    val motion = LocalFrogMotionEnabled.current
    val source = remember { MutableInteractionSource() }
    val itemModifier = modifier
        .heightIn(min = 56.dp)
        .showcaseFocus(source)
        .selectable(selected, interactionSource = source, indication = null, role = Role.Tab, onClick = onClick)
        .padding(horizontal = 4.dp, vertical = 8.dp)

    val labelColor by animateColorAsState(
        targetValue = if (selected) colors.foreground else colors.mutedForeground,
        animationSpec = tween(durationMillis = if (motion) ShowcaseMotion.fast else 0),
        label = "nav label color"
    )
    val indicatorAlpha by animateFloatAsState(
        targetValue = if (selected) 1f else 0f,
        animationSpec = tween(durationMillis = if (motion) ShowcaseMotion.fast else 0),
        label = "nav indicator alpha"
    )

    if (horizontal) {
        val indicatorHeight by animateDpAsState(
            targetValue = if (selected) 20.dp else 0.dp,
            animationSpec = tween(durationMillis = if (motion) ShowcaseMotion.fast else 0),
            label = "rail indicator height"
        )
        Row(
            itemModifier
                .background(if (selected) colors.subtleSurface else Color.Transparent)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                Modifier
                    .width(2.dp)
                    .height(indicatorHeight)
                    .graphicsLayer { alpha = indicatorAlpha }
                    .background(colors.foreground, RoundedCornerShape(1.dp))
            )
            AnimatedNavIcon(destination.icon, selected)
            Text(
                text = destination.label,
                color = labelColor,
                style = FrogTheme.typography.bodySmall,
                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
            )
        }
    } else {
        val indicatorWidth by animateDpAsState(
            targetValue = if (selected) 16.dp else 0.dp,
            animationSpec = tween(durationMillis = if (motion) ShowcaseMotion.fast else 0),
            label = "bottom indicator width"
        )
        Column(
            itemModifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            AnimatedNavIcon(destination.icon, selected)
            Text(
                text = destination.label,
                color = labelColor,
                style = FrogTheme.typography.label,
                textAlign = TextAlign.Center,
                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
            )
            Box(
                Modifier
                    .width(indicatorWidth)
                    .height(2.dp)
                    .graphicsLayer { alpha = indicatorAlpha }
                    .background(colors.foreground, RoundedCornerShape(1.dp))
            )
        }
    }
}

@Composable
internal fun FrogNavigationRail(selectedRoute: String, onDestinationClick: (String) -> Unit, modifier: Modifier = Modifier) {
    NavigationSide(selectedRoute, onDestinationClick, false, modifier)
}

@Composable
internal fun FrogNavigationSidebar(selectedRoute: String, onDestinationClick: (String) -> Unit, modifier: Modifier = Modifier) {
    NavigationSide(selectedRoute, onDestinationClick, true, modifier)
}

@Composable
private fun NavigationSide(selectedRoute: String, onDestinationClick: (String) -> Unit, expanded: Boolean, modifier: Modifier) {
    val colors = FrogTheme.colors
    Column(modifier.width(if (expanded) 208.dp else 108.dp).fillMaxHeight().background(colors.surface)
        .drawBehind { drawLine(colors.border, androidx.compose.ui.geometry.Offset(size.width, 0f), androidx.compose.ui.geometry.Offset(size.width, size.height), 1.dp.toPx()) }
        .windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Vertical)).padding(8.dp)
        .testTag(if (expanded) "sidebar-navigation" else "rail-navigation")) {
        Row(Modifier.fillMaxWidth().padding(vertical = 14.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
            FrogUiLogo(size = 28.dp, contentDescription = if (expanded) null else "FrogUI")
            if (expanded) Text("FrogUI", Modifier.padding(start = 10.dp), style = FrogTheme.typography.heading, color = colors.foreground)
        }
        Column(Modifier.weight(1f).verticalScroll(rememberScrollState()).selectableGroup(), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            showcaseDestinations.forEach { destination -> NavigationItem(destination, destination.route == selectedRoute, { onDestinationClick(destination.route) }, Modifier.fillMaxWidth(), expanded) }
        }
        NavigationItem(ShowcaseDestination("settings", "Settings", FrogIcons.Settings), selectedRoute == "settings", { onDestinationClick("settings") }, Modifier.fillMaxWidth(), expanded)
    }
}
