package io.github.codewitheswar.frogui.components.overlays.drawer

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
import io.github.codewitheswar.frogui.theme.FrogTheme

/**
 * Standard FrogUI Drawer component.
 *
 * Presents contextual content in an adaptive modal sheet or side panel surface without
 * navigating away from the current screen destination.
 *
 * @param state The [FrogDrawerState] controlling the drawer's visibility.
 * @param onDismissRequest Called when the user initiates a dismiss gesture, outside tap, or system back.
 * @param modifier Layout modifier applied to the drawer container.
 * @param presentation Presentation mode: [FrogDrawerPresentation.Auto], [FrogDrawerPresentation.Bottom], or [FrogDrawerPresentation.Side].
 * @param side Side placement edge when in side presentation mode: [FrogDrawerSide.End] or [FrogDrawerSide.Start].
 * @param title Optional title text displayed in the drawer header.
 * @param subtitle Optional subtitle text displayed underneath the title.
 * @param navigationIcon Optional slot rendered at the leading edge of the header (e.g., back action).
 * @param actions Optional slot rendered at the trailing edge of the header.
 * @param preview Optional slot rendered immediately below the header divider.
 * @param footer Optional sticky footer rendered at the bottom of the drawer, outside the scrollable body.
 * @param colors Resolved colors for container, content, border, handle, and scrim.
 * @param content The scrollable content rendered inside the drawer body.
 */
@Composable
fun FrogDrawer(
    state: FrogDrawerState,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    presentation: FrogDrawerPresentation = FrogDrawerPresentation.Auto,
    side: FrogDrawerSide = FrogDrawerSide.End,
    title: String? = null,
    subtitle: String? = null,
    navigationIcon: (@Composable () -> Unit)? = null,
    actions: (@Composable RowScope.() -> Unit)? = null,
    preview: (@Composable () -> Unit)? = null,
    footer: (@Composable () -> Unit)? = null,
    colors: FrogDrawerColors = FrogDrawerDefaults.colors(),
    content: @Composable ColumnScope.() -> Unit
) {
    FrogDrawerInternal(
        visible = state.isOpen,
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        presentation = presentation,
        side = side,
        title = title,
        subtitle = subtitle,
        navigationIcon = navigationIcon,
        actions = actions,
        preview = preview,
        footer = footer,
        colors = colors,
        content = content
    )
}

/**
 * Overload of [FrogDrawer] driven directly by a [visible] Boolean flag.
 */
@Composable
fun FrogDrawer(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    presentation: FrogDrawerPresentation = FrogDrawerPresentation.Auto,
    side: FrogDrawerSide = FrogDrawerSide.End,
    title: String? = null,
    subtitle: String? = null,
    navigationIcon: (@Composable () -> Unit)? = null,
    actions: (@Composable RowScope.() -> Unit)? = null,
    preview: (@Composable () -> Unit)? = null,
    footer: (@Composable () -> Unit)? = null,
    colors: FrogDrawerColors = FrogDrawerDefaults.colors(),
    content: @Composable ColumnScope.() -> Unit
) {
    FrogDrawerInternal(
        visible = visible,
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        presentation = presentation,
        side = side,
        title = title,
        subtitle = subtitle,
        navigationIcon = navigationIcon,
        actions = actions,
        preview = preview,
        footer = footer,
        colors = colors,
        content = content
    )
}

/**
 * Legacy-compatible overload supporting existing Showcase call-sites with `side: Boolean` and `onBack`.
 */
@Composable
fun FrogDrawer(
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
    FrogDrawerInternal(
        visible = visible,
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        presentation = if (side) FrogDrawerPresentation.Side else FrogDrawerPresentation.Bottom,
        side = FrogDrawerSide.End,
        title = title,
        subtitle = subtitle,
        navigationIcon = if (onBack != null) ({
            DrawerBackButton(onClick = onBack)
        }) else null,
        actions = actions,
        preview = preview,
        footer = null,
        colors = FrogDrawerDefaults.colors(),
        content = content
    )
}

@Composable
private fun FrogDrawerInternal(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    presentation: FrogDrawerPresentation = FrogDrawerPresentation.Auto,
    side: FrogDrawerSide = FrogDrawerSide.End,
    title: String? = null,
    subtitle: String? = null,
    navigationIcon: (@Composable () -> Unit)? = null,
    actions: (@Composable RowScope.() -> Unit)? = null,
    preview: (@Composable () -> Unit)? = null,
    footer: (@Composable () -> Unit)? = null,
    colors: FrogDrawerColors = FrogDrawerDefaults.colors(),
    content: @Composable ColumnScope.() -> Unit
) {
    val inspection = LocalInspectionMode.current
    val transition = remember { MutableTransitionState(inspection && visible) }
    transition.targetState = visible
    if (!transition.currentState && !transition.targetState) return

    val duration = FrogDrawerDefaults.AnimationDurationMs
    val entryFocus = remember { FocusRequester() }

    val windowContent: @Composable () -> Unit = {
        BoxWithConstraints(
            Modifier
                .fillMaxSize()
                .testTag("drawer-window")
        ) {
            val isSidePresentation = when (presentation) {
                FrogDrawerPresentation.Auto -> maxWidth >= 620.dp
                FrogDrawerPresentation.Side -> true
                FrogDrawerPresentation.Bottom -> false
            }

            // Scrim overlay
            Box(
                Modifier
                    .fillMaxSize()
                    .background(colors.scrimColor)
                    .clickable(
                        remember { MutableInteractionSource() },
                        indication = null,
                        onClick = onDismissRequest
                    )
                    .clearAndSetSemantics {}
            )

            // Surface positioning
            val contentAlignment = if (isSidePresentation) {
                if (side == FrogDrawerSide.Start) Alignment.CenterStart else Alignment.CenterEnd
            } else {
                Alignment.BottomCenter
            }

            BoxWithConstraints(
                Modifier
                    .fillMaxSize()
                    .safeDrawingPadding()
                    .imePadding(),
                contentAlignment = contentAlignment
            ) {
                val drawerMaxHeight = maxHeight * 0.9f

                AnimatedVisibility(
                    transition,
                    enter = fadeIn(tween(duration)) + if (isSidePresentation) {
                        slideInHorizontally(tween(duration)) { if (side == FrogDrawerSide.Start) -it / 3 else it / 3 }
                    } else {
                        slideInVertically(tween(duration)) { it }
                    },
                    exit = fadeOut(tween(duration)) + if (isSidePresentation) {
                        slideOutHorizontally(tween(duration)) { if (side == FrogDrawerSide.Start) -it / 3 else it / 3 }
                    } else {
                        slideOutVertically(tween(duration)) { it }
                    }
                ) {
                    val shape = if (isSidePresentation) {
                        FrogDrawerDefaults.sideShape(side)
                    } else {
                        FrogDrawerDefaults.bottomShape
                    }

                    val layoutModifier = if (isSidePresentation) {
                        Modifier
                            .widthIn(max = FrogDrawerDefaults.SideWidth)
                            .fillMaxWidth()
                            .fillMaxHeight()
                    } else {
                        Modifier
                            .widthIn(max = FrogDrawerDefaults.BottomMaxWidth)
                            .fillMaxWidth()
                            .heightIn(max = drawerMaxHeight)
                    }

                    Column(
                        modifier
                            .then(layoutModifier)
                            .clip(shape)
                            .background(colors.containerColor)
                            .border(1.dp, colors.borderColor, shape)
                            .semantics {
                                paneTitle = title ?: "Drawer"
                                dismiss {
                                    onDismissRequest()
                                    true
                                }
                            }
                            .testTag(if (isSidePresentation) "drawer-side" else "drawer-bottom")
                    ) {
                        // Bottom drawer drag handle
                        if (!isSidePresentation) {
                            var drag by remember { mutableFloatStateOf(0f) }
                            val threshold = with(LocalDensity.current) { FrogDrawerDefaults.DragDismissThreshold.toPx() }
                            Box(
                                Modifier
                                    .fillMaxWidth()
                                    .height(24.dp)
                                    .draggable(
                                        rememberDraggableState { drag += it },
                                        Orientation.Vertical,
                                        onDragStarted = { drag = 0f },
                                        onDragStopped = { if (drag > threshold) onDismissRequest() }
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Box(
                                    Modifier
                                        .size(32.dp, 3.dp)
                                        .clip(RoundedCornerShape(2.dp))
                                        .background(colors.handleColor)
                                )
                            }
                        }

                        // Header
                        if (title != null || subtitle != null || navigationIcon != null || actions != null) {
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = 12.dp,
                                        end = 6.dp,
                                        top = if (isSidePresentation) 12.dp else 0.dp,
                                        bottom = 8.dp
                                    ),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                navigationIcon?.invoke()

                                Column(
                                    Modifier
                                        .weight(1f)
                                        .padding(start = if (navigationIcon != null) 6.dp else 4.dp),
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    if (title != null) {
                                        Text(
                                            title,
                                            style = FrogTheme.typography.heading,
                                            color = colors.contentColor,
                                            modifier = Modifier.semantics { heading() }
                                        )
                                    }
                                    if (subtitle != null) {
                                        Text(
                                            subtitle,
                                            style = FrogTheme.typography.bodySmall,
                                            color = FrogTheme.colors.mutedForeground
                                        )
                                    }
                                }

                                actions?.invoke(this)

                                // Standard Accessible Close button
                                DrawerCloseButton(
                                    onClick = onDismissRequest,
                                    modifier = Modifier.focusRequester(entryFocus)
                                )
                            }
                            HorizontalDivider(color = FrogTheme.colors.border)
                        }

                        // Optional Preview slot
                        if (preview != null) {
                            preview()
                            HorizontalDivider(color = FrogTheme.colors.border)
                        }

                        // Scrollable content body
                        val bodyScroll = remember(title) { ScrollState(0) }
                        Column(
                            Modifier
                                .weight(1f, fill = false)
                                .verticalScroll(bodyScroll)
                                .padding(18.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            content = content
                        )

                        // Sticky Footer
                        if (footer != null) {
                            HorizontalDivider(color = FrogTheme.colors.border)
                            Box(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 18.dp, vertical = 10.dp)
                            ) {
                                footer()
                            }
                        }
                    }

                    LaunchedEffect(Unit) {
                        if (!inspection) entryFocus.requestFocus()
                    }
                }
            }
        }
    }

    if (inspection) {
        windowContent()
    } else {
        Dialog(
            onDismissRequest = onDismissRequest,
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                dismissOnClickOutside = false,
                decorFitsSystemWindows = false
            ),
            content = windowContent
        )
    }
}

/**
 * Accessible Drawer Close Icon Button.
 */
@Composable
private fun DrawerCloseButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(40.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .semantics {
                role = Role.Button
                contentDescription = "Close drawer"
            },
        contentAlignment = Alignment.Center
    ) {
        // Crisp 14dp vector close crosshair
        androidx.compose.foundation.Canvas(modifier = Modifier.size(14.dp)) {
            val strokeWidth = 2.dp.toPx()
            val color = Color(0xFFA1A1AA)
            drawLine(color, start = androidx.compose.ui.geometry.Offset(0f, 0f), end = androidx.compose.ui.geometry.Offset(size.width, size.height), strokeWidth = strokeWidth)
            drawLine(color, start = androidx.compose.ui.geometry.Offset(size.width, 0f), end = androidx.compose.ui.geometry.Offset(0f, size.height), strokeWidth = strokeWidth)
        }
    }
}

/**
 * Accessible Drawer Back Button.
 */
@Composable
private fun DrawerBackButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(40.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .semantics {
                role = Role.Button
                contentDescription = "Back within drawer"
            },
        contentAlignment = Alignment.Center
    ) {
        androidx.compose.foundation.Canvas(modifier = Modifier.size(16.dp)) {
            val strokeWidth = 2.dp.toPx()
            val color = Color(0xFFA1A1AA)
            val w = size.width
            val h = size.height
            // Left arrow
            drawLine(color, start = androidx.compose.ui.geometry.Offset(w * 0.8f, h * 0.5f), end = androidx.compose.ui.geometry.Offset(w * 0.2f, h * 0.5f), strokeWidth = strokeWidth)
            drawLine(color, start = androidx.compose.ui.geometry.Offset(w * 0.5f, h * 0.2f), end = androidx.compose.ui.geometry.Offset(w * 0.2f, h * 0.5f), strokeWidth = strokeWidth)
            drawLine(color, start = androidx.compose.ui.geometry.Offset(w * 0.5f, h * 0.8f), end = androidx.compose.ui.geometry.Offset(w * 0.2f, h * 0.5f), strokeWidth = strokeWidth)
        }
    }
}
