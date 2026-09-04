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
import io.github.codewitheswar.frogui.foundation.adaptive.FrogWindowSizeClass
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalContentColor
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
import io.github.codewitheswar.frogui.components.overlays.LocalFrogOverlayHost
import io.github.codewitheswar.frogui.components.button.*
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection

/**
 * Shows contextual content in a native modal bottom sheet or side panel.
 *
 * Presents contextual content in an adaptive modal sheet or side panel surface without
 * navigating away from the current screen destination. The caller owns [state] and must close
 * it in [onDismissRequest]; a request alone does not change visibility. Auto uses Bottom for
 * Compact available width; Medium/Expanded use Side through the local
 * [io.github.codewitheswar.frogui.foundation.adaptive.FrogAdaptive] policy.
 * Side is modal too, not a persistent pane.
 *
 * The header and optional preview/footer stay fixed around a scrolling body. The title names
 * the accessibility pane; close and dismiss semantics are supplied. Native windows receive
 * focus at the close action and respect system/IME insets. Within FrogOverlayHost, rendering
 * is bounded/nonmodal: its caller handles Back and focus restoration outside that region.
 * Theme motion controls transitions. This API remains Experimental in the current snapshot.
 *
 * @param state Hoisted requested visibility; create with [rememberFrogDrawerState] for restoration.
 * @param onDismissRequest Called when the user initiates a dismiss gesture, outside tap, or system back.
 * @param modifier Layout modifier applied to the drawer container.
 * @param presentation Presentation mode: [FrogDrawerPresentation.Auto], [FrogDrawerPresentation.Bottom], or [FrogDrawerPresentation.Side].
 * @param side Side placement edge when in side presentation mode: [FrogDrawerSide.End] or [FrogDrawerSide.Start].
 * @param title Optional title text displayed in the drawer header.
 * @param subtitle Optional subtitle text displayed underneath the title.
 * @param navigationIcon Optional leading navigation action; supply its label and 48dp target.
 * @param actions Optional slot rendered at the trailing edge of the header.
 * @param preview Optional slot rendered immediately below the header divider.
 * @param footer Optional sticky footer rendered at the bottom of the drawer, outside the scrollable body.
 * @param colors Resolved colors for container, content, border, handle, and scrim.
 * @param onBackRequest Optional native-window Back callback for nested pages; defaults to [onDismissRequest].
 * @param closeIcon Optional decorative icon inside the standard accessible close button.
 * @param content Scrollable ColumnScope body inheriting [FrogDrawerColors.contentColor].
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
    onBackRequest: (() -> Unit)? = null,
    closeIcon: (@Composable () -> Unit)? = null,
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
        onBackRequest = onBackRequest,
        closeIcon = closeIcon,
        content = content
    )
}

/**
 * Caller-controlled alternative to the state overload of [FrogDrawer]. No state helper is required.
 * Update [visible] in [onDismissRequest] to close; keep it true to leave the drawer open.
 * Presentation, slots, defaults, insets and accessibility follow the state overload's contract.
 *
 * @param visible Requested visibility; rendering may finish a closing transition after it is false.
 * @param onDismissRequest Requests closure without changing [visible] automatically.
 * @param onBackRequest Optional native-window Back override; otherwise requests dismissal.
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
    onBackRequest: (() -> Unit)? = null,
    closeIcon: (@Composable () -> Unit)? = null,
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
        onBackRequest = onBackRequest,
        closeIcon = closeIcon,
        content = content
    )
}

/**
 * Compatibility adapter for older Boolean-side calls. Prefer the presentation-based overload.
 * Unlike the canonical overload, false [side] explicitly means Bottom and [actions] is a footer.
 * Migrate [onBack] to both a navigation control and `onBackRequest`, and move [actions] into a
 * Row in `footer`. This overload remains callable while consumers migrate.
 */
@Deprecated("Use the presentation-based FrogDrawer overload. Map side to Bottom/Side, actions to footer, and onBack to navigationIcon/onBackRequest. See api-design.md migration guidance.")
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
        actions = null,
        preview = preview,
        footer = actions?.let { footerActions -> ({ Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp), content = footerActions) }) },
        colors = FrogDrawerDefaults.colors(),
        onBackRequest = onBack,
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
    onBackRequest: (() -> Unit)? = null,
    closeIcon: (@Composable () -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    val inspection = LocalInspectionMode.current
    val transition = remember { MutableTransitionState(inspection && visible) }
    transition.targetState = visible
    if (!transition.currentState && !transition.targetState) return

    val motion = FrogTheme.motion
    val duration = motion.normalDurationMillis
    val spacing = FrogTheme.spacing
    val embedded = LocalFrogOverlayHost.current
    val rtl = LocalLayoutDirection.current == LayoutDirection.Rtl
    val fromLeft = (side == FrogDrawerSide.Start) != rtl
    val entryFocus = remember { FocusRequester() }

    val windowContent: @Composable () -> Unit = {
        BoxWithConstraints(
            Modifier
                .fillMaxSize()
                .testTag("drawer-window")
        ) {
            val isSidePresentation = when (presentation) {
                FrogDrawerPresentation.Auto -> FrogTheme.adaptive.windowSizeClass(maxWidth) != FrogWindowSizeClass.Compact
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
                    .then(if (embedded) Modifier else Modifier.safeDrawingPadding().imePadding()),
                contentAlignment = contentAlignment
            ) {
                val drawerMaxHeight = maxHeight * 0.9f

                AnimatedVisibility(
                    transition,
                    enter = fadeIn(tween(duration, easing = motion.enterEasing)) + if (isSidePresentation) {
                        slideInHorizontally(tween(duration, easing = motion.enterEasing)) { if (fromLeft) -it / 3 else it / 3 }
                    } else {
                        slideInVertically(tween(duration, easing = motion.enterEasing)) { it }
                    },
                    exit = fadeOut(tween(duration, easing = motion.exitEasing)) + if (isSidePresentation) {
                        slideOutHorizontally(tween(duration, easing = motion.exitEasing)) { if (fromLeft) -it / 3 else it / 3 }
                    } else {
                        slideOutVertically(tween(duration, easing = motion.exitEasing)) { it }
                    }
                ) {
                    val shape = FrogDrawerDefaults.shape(
                        if (isSidePresentation) FrogDrawerPresentation.Side else FrogDrawerPresentation.Bottom, side)

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
                                    .height(FrogDrawerDefaults.HandleAreaHeight)
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
                                        .size(FrogDrawerDefaults.HandleWidth, FrogDrawerDefaults.HandleHeight)
                                        .clip(FrogTheme.shapes.full)
                                        .background(colors.handleColor)
                                )
                            }
                        }

                        // Header
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = spacing.lg,
                                        end = spacing.sm,
                                        top = if (isSidePresentation) spacing.lg else 0.dp,
                                        bottom = spacing.md
                                    ),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                navigationIcon?.invoke()

                                Column(
                                    Modifier
                                        .weight(1f)
                                        .padding(start = if (navigationIcon != null) spacing.sm else spacing.xs),
                                    verticalArrangement = Arrangement.spacedBy(spacing.xs)
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
                                    modifier = Modifier.focusRequester(entryFocus),
                                    icon = closeIcon,
                                )
                            }
                            HorizontalDivider(color = FrogTheme.colors.border)

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
                                .padding(FrogDrawerDefaults.ContentInset),
                            verticalArrangement = Arrangement.spacedBy(spacing.xl),
                            content = content
                        )

                        // Sticky Footer
                        if (footer != null) {
                            HorizontalDivider(color = FrogTheme.colors.border)
                            Box(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = FrogDrawerDefaults.ContentInset, vertical = FrogDrawerDefaults.FooterVerticalInset)
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

    val themedContent: @Composable () -> Unit = {
        CompositionLocalProvider(LocalContentColor provides colors.contentColor) { windowContent() }
    }
    if (inspection || embedded) {
        themedContent()
    } else {
        Dialog(
            onDismissRequest = onBackRequest ?: onDismissRequest,
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                dismissOnClickOutside = false,
                decorFitsSystemWindows = false
            ),
            content = themedContent
        )
    }
}

/**
 * Accessible Drawer Close Icon Button.
 */
@Composable
private fun DrawerCloseButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit)? = null,
) {
    val glyphColor = FrogTheme.colors.foreground
    FrogIconButton(onClick, "Close drawer", modifier, size = FrogButtonSize.Small) {
        if (icon != null) { icon(); return@FrogIconButton }
        // Crisp 14dp vector close crosshair
        androidx.compose.foundation.Canvas(modifier = Modifier.size(14.dp)) {
            val strokeWidth = 2.dp.toPx()
            val color = glyphColor
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
    val glyphColor = FrogTheme.colors.foreground
    FrogIconButton(onClick, "Back within drawer", modifier, variant = FrogButtonVariant.Secondary, size = FrogButtonSize.Small) {
        androidx.compose.foundation.Canvas(modifier = Modifier.size(16.dp)) {
            val strokeWidth = 2.dp.toPx()
            val color = glyphColor
            val w = size.width
            val h = size.height
            // Left arrow
            drawLine(color, start = androidx.compose.ui.geometry.Offset(w * 0.8f, h * 0.5f), end = androidx.compose.ui.geometry.Offset(w * 0.2f, h * 0.5f), strokeWidth = strokeWidth)
            drawLine(color, start = androidx.compose.ui.geometry.Offset(w * 0.5f, h * 0.2f), end = androidx.compose.ui.geometry.Offset(w * 0.2f, h * 0.5f), strokeWidth = strokeWidth)
            drawLine(color, start = androidx.compose.ui.geometry.Offset(w * 0.5f, h * 0.8f), end = androidx.compose.ui.geometry.Offset(w * 0.2f, h * 0.5f), strokeWidth = strokeWidth)
        }
    }
}
