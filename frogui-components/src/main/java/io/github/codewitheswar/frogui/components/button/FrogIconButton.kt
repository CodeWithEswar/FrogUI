package io.github.codewitheswar.frogui.components.button

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.components.R
import io.github.codewitheswar.frogui.theme.FrogTheme

/**
 * A compact actionable control whose visible content is primarily an icon.
 *
 * One labelled action owns the complete minimum 48dp target. Loading and disabled state
 * suppress activation; the caller owns both values. [contentDescription] provides the
 * mandatory accessible action name and must describe the action rather than the visual glyph.
 *
 * @param icon The primary visual icon content to display.
 * @param contentDescription Mandatory concise action description announced by accessibility services.
 * @param onClick Callback invoked when the icon button is activated.
 * @param modifier Applied to the outer minimum touch-target container.
 * @param variant Visual semantic emphasis: [FrogIconButtonVariant.Filled], [FrogIconButtonVariant.Tonal], [FrogIconButtonVariant.Outline], or [FrogIconButtonVariant.Ghost].
 * @param size Dimensional scale: [FrogIconButtonSize.Small], [FrogIconButtonSize.Medium], or [FrogIconButtonSize.Large].
 * @param enabled When false, suppresses interaction and applies disabled styling.
 * @param loading Replaces icon with circular progress indicator and announces Loading state.
 * @param badge Optional overlay slot positioned at TopEnd for notification dots or count badges.
 * @param colors Resolved background, content, border, and interaction colors.
 * @param shape Corner radius shape applied to button container and border.
 * @param interactionSource Interaction stream driving press animations and focus indicators.
 */
@Composable
fun FrogIconButton(
    icon: @Composable () -> Unit,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: FrogIconButtonVariant = FrogIconButtonVariant.Filled,
    size: FrogIconButtonSize = FrogIconButtonSize.Medium,
    enabled: Boolean = true,
    loading: Boolean = false,
    badge: (@Composable () -> Unit)? = null,
    colors: FrogIconButtonColors = FrogIconButtonDefaults.colors(variant),
    shape: Shape = FrogIconButtonDefaults.shape(size),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val isInteractive = enabled && !loading
    val isPressed by interactionSource.collectIsPressedAsState()
    val isFocused by interactionSource.collectIsFocusedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed && isInteractive && FrogTheme.motion.fastDurationMillis > 0) 0.95f else 1.0f,
        animationSpec = FrogTheme.motion.fastSpec(),
        label = "frog_icon_button_scale"
    )

    val currentContainerColor by animateColorAsState(
        targetValue = when {
            !enabled -> colors.disabledContainerColor
            isPressed -> colors.pressedOverlayColor.compositeOver(colors.containerColor)
            else -> colors.containerColor
        },
        animationSpec = FrogTheme.motion.fastSpec(),
        label = "frog_icon_button_container"
    )

    val currentContentColor = if (enabled) colors.contentColor else colors.disabledContentColor
    val border = FrogIconButtonDefaults.border(colors, enabled)
    val loadingDescription = stringResource(R.string.frogui_loading)
    val containerSize = FrogIconButtonDefaults.containerSize(size)
    val iconSize = FrogIconButtonDefaults.iconSize(size)

    val semanticsModifier = Modifier.semantics(mergeDescendants = true) {
        this.contentDescription = contentDescription
        role = Role.Button
        if (loading) {
            stateDescription = loadingDescription
        }
        if (!isInteractive) {
            disabled()
        }
    }

    Box(
        modifier = modifier
            .defaultMinSize(
                minWidth = FrogTheme.sizing.minimumTouchTarget,
                minHeight = FrogTheme.sizing.minimumTouchTarget
            )
            .scale(scale)
            .then(semanticsModifier)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = isInteractive,
                role = Role.Button,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier.size(containerSize),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape)
                    .background(currentContainerColor)
                    .then(
                        if (isFocused && colors.focusRingColor != Color.Transparent) {
                            Modifier.border(2.dp, colors.focusRingColor, shape)
                        } else if (border != null) {
                            Modifier.border(border, shape)
                        } else Modifier
                    ),
                contentAlignment = Alignment.Center
            ) {
                CompositionLocalProvider(
                    LocalContentColor provides currentContentColor
                ) {
                    if (loading) {
                        FrogButtonProgress(currentContentColor, Modifier.size(iconSize))
                    } else {
                        icon()
                    }
                }
            }

            if (badge != null) {
                Box(
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    badge()
                }
            }
        }
    }
}

/**
 * Legacy overload for [FrogIconButton].
 * Prefer the canonical overload using [FrogIconButtonVariant] and [FrogIconButtonSize].
 */
@Deprecated(
    message = "Use canonical FrogIconButton with icon slot and FrogIconButtonVariant/FrogIconButtonSize",
    level = DeprecationLevel.WARNING
)
@Composable
fun FrogIconButton(
    onClick: () -> Unit,
    contentDescription: String,
    modifier: Modifier = Modifier,
    variant: FrogButtonVariant = FrogButtonVariant.Ghost,
    size: FrogButtonSize = FrogButtonSize.Medium,
    enabled: Boolean = true,
    loading: Boolean = false,
    shape: Shape = FrogButtonDefaults.shape(size),
    colors: FrogButtonColors = FrogButtonDefaults.colors(variant),
    border: BorderStroke? = FrogButtonDefaults.border(colors, enabled),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit
) {
    val isInteractive = enabled && !loading
    val isPressed by interactionSource.collectIsPressedAsState()
    val isFocused by interactionSource.collectIsFocusedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed && isInteractive && FrogTheme.motion.fastDurationMillis > 0) 0.95f else 1.0f,
        animationSpec = FrogTheme.motion.fastSpec(),
        label = "frog_icon_button_scale"
    )

    val currentContainerColor by animateColorAsState(when {
        !enabled -> colors.disabledContainerColor
        isPressed -> colors.pressedOverlayColor.compositeOver(colors.containerColor)
        else -> colors.containerColor
    }, animationSpec = FrogTheme.motion.fastSpec(), label = "frog_icon_button_container")

    val currentContentColor = if (enabled) colors.contentColor else colors.disabledContentColor

    val loadingDescription = stringResource(R.string.frogui_loading)
    val semanticsModifier = Modifier.semantics(mergeDescendants = true) {
        this.contentDescription = contentDescription
        role = Role.Button
        if (loading) {
            stateDescription = loadingDescription
        }
        if (!isInteractive) {
            disabled()
        }
    }

    Box(
        modifier = modifier
            .defaultMinSize(minWidth = FrogTheme.sizing.minimumTouchTarget, minHeight = FrogTheme.sizing.minimumTouchTarget)
            .scale(scale)
            .then(semanticsModifier)
            .clickable(interactionSource, indication = null, enabled = isInteractive, role = Role.Button, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(FrogButtonDefaults.controlHeight(size))
                .clip(shape)
                .background(currentContainerColor)
                .then(
                    if (isFocused && colors.focusRingColor != Color.Transparent) {
                        Modifier.border(2.dp, colors.focusRingColor, shape)
                    } else if (border != null) {
                        Modifier.border(border, shape)
                    } else Modifier
                ),
            contentAlignment = Alignment.Center
        ) {
            CompositionLocalProvider(
                LocalContentColor provides currentContentColor
            ) {
                if (loading) {
                    FrogButtonProgress(currentContentColor, Modifier.size(FrogButtonDefaults.iconSize(size)))
                } else {
                    content()
                }
            }
        }
    }
}
