package io.github.codewitheswar.frogui.showcase.drawer

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import io.github.codewitheswar.frogui.showcase.icons.FrogIcons
import io.github.codewitheswar.frogui.showcase.style.LocalFrogMotionEnabled
import io.github.codewitheswar.frogui.showcase.style.ShowcaseIconButton
import io.github.codewitheswar.frogui.showcase.style.ShowcaseBackButton
import io.github.codewitheswar.frogui.theme.FrogTheme

/** One native modal window, with contextual pages supplied by its owner. */
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
    content: @Composable ColumnScope.() -> Unit,
) {
    val inspection = LocalInspectionMode.current
    val transition = remember { MutableTransitionState(inspection && visible) }
    transition.targetState = visible
    if (!transition.currentState && !transition.targetState) return
    val colors = FrogTheme.colors
    val duration = if (LocalFrogMotionEnabled.current) 220 else 0
    val entryFocus = remember { FocusRequester() }
    val windowContent: @Composable () -> Unit = {
        BackHandler { (onBack ?: onDismissRequest)() }
        BoxWithConstraints(Modifier.fillMaxSize().testTag("drawer-window")) {
            Box(Modifier.fillMaxSize().background(Color.Black.copy(alpha = .48f))
                .clickable(remember { MutableInteractionSource() }, indication = null, onClick = onDismissRequest)
                .clearAndSetSemantics {})
            BoxWithConstraints(Modifier.fillMaxSize().safeDrawingPadding().imePadding(), contentAlignment = if (side) Alignment.CenterEnd else Alignment.BottomCenter) {
                val drawerMaxHeight = maxHeight * .9f
                AnimatedVisibility(transition,
                    enter = fadeIn(tween(duration)) + if (side) slideInHorizontally(tween(duration)) { it / 3 } else slideInVertically(tween(duration)) { it },
                    exit = fadeOut(tween(duration)) + if (side) slideOutHorizontally(tween(duration)) { it / 3 } else slideOutVertically(tween(duration)) { it },
                ) {
                    val shape = if (side) RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp) else RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp)
                    Column(modifier.then(if (side) Modifier.widthIn(max = 400.dp).fillMaxWidth().fillMaxHeight() else Modifier.widthIn(max = 600.dp).fillMaxWidth().heightIn(max = drawerMaxHeight))
                        .clip(shape).background(colors.surfaceElevated).border(1.dp, colors.borderStrong, shape)
                        .semantics { paneTitle = title; dismiss { onDismissRequest(); true } }
                        .testTag(if (side) "drawer-side" else "drawer-bottom")) {
                        if (!side) {
                            var drag by remember { mutableFloatStateOf(0f) }
                            val threshold = with(LocalDensity.current) { 64.dp.toPx() }
                            Box(Modifier.fillMaxWidth().height(24.dp).draggable(rememberDraggableState { drag += it }, Orientation.Vertical,
                                onDragStarted = { drag = 0f }, onDragStopped = { if (drag > threshold) onDismissRequest() }), contentAlignment = Alignment.Center) {
                                Box(Modifier.size(32.dp, 3.dp).clip(RoundedCornerShape(2.dp)).background(colors.borderStrong))
                            }
                        }
                        Row(Modifier.fillMaxWidth().padding(start = 12.dp, end = 6.dp, top = if (side) 12.dp else 0.dp, bottom = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                            if (onBack != null) ShowcaseBackButton("Back within drawer", onBack)
                            Column(Modifier.weight(1f).padding(start = 6.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text(title, style = FrogTheme.typography.heading, color = colors.foreground, modifier = Modifier.semantics { heading() })
                                subtitle?.let { Text(it, style = FrogTheme.typography.bodySmall, color = colors.mutedForeground) }
                            }
                            ShowcaseIconButton(FrogIcons.Close, "Close drawer", onDismissRequest, Modifier.focusRequester(entryFocus))
                        }
                        HorizontalDivider(color = colors.border)
                        if (preview != null) {
                            preview()
                            HorizontalDivider(color = colors.border)
                        }
                        val bodyScroll = remember(title) { ScrollState(0) }
                        Column(Modifier.weight(1f, fill = false).verticalScroll(bodyScroll).padding(18.dp), verticalArrangement = Arrangement.spacedBy(16.dp), content = content)
                        if (actions != null) {
                            HorizontalDivider(color = colors.border)
                            Row(Modifier.fillMaxWidth().padding(horizontal = 18.dp, vertical = 10.dp), horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically, content = actions)
                        }
                    }
                    LaunchedEffect(Unit) { if (!inspection) entryFocus.requestFocus() }
                }
            }
        }
    }
    if (inspection) windowContent() else Dialog(onDismissRequest = { (onBack ?: onDismissRequest)() }, properties = DialogProperties(
        usePlatformDefaultWidth = false, dismissOnClickOutside = false, decorFitsSystemWindows = false,
    ), content = windowContent)
}
