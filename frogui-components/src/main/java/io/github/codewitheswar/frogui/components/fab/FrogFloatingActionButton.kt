package io.github.codewitheswar.frogui.components.fab

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.snap
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.theme.FrogTheme

/**
 * A prominent contextual action button floating above the primary content surface.
 *
 * Emphasizes one key action on the screen (such as Create, Compose, or Scan).
 * Offers three presentation forms: [FrogFabPresentation.Regular], [FrogFabPresentation.Small],
 * and [FrogFabPresentation.Extended]. Guarantees at least a 48dp minimum interactive touch target
 * across all presentations, including [FrogFabPresentation.Small].
 *
 * @param icon Primary visual glyph composable. Should be decorative (`contentDescription = null`)
 *   as the parent button owns the accessible action name.
 * @param contentDescription Mandatory concise action description announced by accessibility services.
 * @param onClick Callback invoked when the button is activated via touch, keyboard, or accessibility action.
 * @param modifier Applied to the outer layout container.
 * @param presentation Dimensional scale and form: [FrogFabPresentation.Regular], [FrogFabPresentation.Small],
 *   or [FrogFabPresentation.Extended].
 * @param label Composable visible text label used when [presentation] is [FrogFabPresentation.Extended].
 * @param expanded When true and [presentation] is [FrogFabPresentation.Extended], displays both [icon]
 *   and [label]. When false, collapses to an icon-only representation. Ignored for [FrogFabPresentation.Regular]
 *   and [FrogFabPresentation.Small].
 * @param enabled Controls interaction state. When false, suppresses clicks and applies disabled styling.
 * @param visible Controls presence. When false, smoothly animates out and is removed from semantics and interaction.
 * @param elevation Elevation tokens controlling shadow and surface separation across interaction states.
 * @param colors Resolved container, content, disabled, and interaction colors.
 * @param shape Corner radius shape applied to the FAB container.
 * @param interactionSource Stream of interaction events driving press, focus, and elevation animations.
 */
@Composable
fun FrogFloatingActionButton(
    icon: @Composable () -> Unit,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    presentation: FrogFabPresentation = FrogFabPresentation.Regular,
    label: (@Composable () -> Unit)? = null,
    expanded: Boolean = true,
    enabled: Boolean = true,
    visible: Boolean = true,
    elevation: FrogFabElevation = FrogFloatingActionButtonDefaults.elevation(),
    colors: FrogFabColors = FrogFloatingActionButtonDefaults.colors(),
    shape: Shape = FrogFloatingActionButtonDefaults.shape(presentation),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val motion = FrogTheme.motion
    val isReduced = motion.isReduced

    val enterTransition = remember(isReduced, motion) {
        if (isReduced) {
            fadeIn(animationSpec = snap())
        } else {
            fadeIn(animationSpec = androidx.compose.animation.core.tween(motion.fastDurationMillis, easing = motion.standardEasing)) +
                scaleIn(initialScale = 0.8f, animationSpec = androidx.compose.animation.core.tween(motion.fastDurationMillis, easing = motion.standardEasing))
        }
    }

    val exitTransition = remember(isReduced, motion) {
        if (isReduced) {
            fadeOut(animationSpec = snap())
        } else {
            fadeOut(animationSpec = androidx.compose.animation.core.tween(motion.fastDurationMillis, easing = motion.standardEasing)) +
                scaleOut(targetScale = 0.8f, animationSpec = androidx.compose.animation.core.tween(motion.fastDurationMillis, easing = motion.standardEasing))
        }
    }

    AnimatedVisibility(
        visible = visible,
        enter = enterTransition,
        exit = exitTransition,
        modifier = modifier
    ) {
        val isInteractive = enabled
        val isPressed by interactionSource.collectIsPressedAsState()
        val isFocused by interactionSource.collectIsFocusedAsState()

        val targetElevation = when {
            !enabled -> elevation.disabled
            isPressed -> elevation.pressed
            isFocused -> elevation.focused
            else -> elevation.default
        }

        val currentElevation by animateDpAsState(
            targetValue = targetElevation,
            animationSpec = if (isReduced) snap() else motion.fastSpec(),
            label = "frog_fab_elevation"
        )

        val targetContainerColor = when {
            !enabled -> colors.disabledContainerColor
            isPressed -> colors.pressedOverlayColor.compositeOver(colors.containerColor)
            else -> colors.containerColor
        }

        val currentContainerColor by animateColorAsState(
            targetValue = targetContainerColor,
            animationSpec = if (isReduced) snap() else motion.fastSpec(),
            label = "frog_fab_container"
        )

        val currentContentColor = if (enabled) colors.contentColor else colors.disabledContentColor

        val scale by animateFloatAsState(
            targetValue = if (isPressed && isInteractive && !isReduced) 0.96f else 1.0f,
            animationSpec = if (isReduced) snap() else motion.fastSpec(),
            label = "frog_fab_scale"
        )

        val iconSize = FrogFloatingActionButtonDefaults.iconSize(presentation)
        val containerHeight = FrogFloatingActionButtonDefaults.containerHeight(presentation)
        val isDark = FrogTheme.colors.isDark

        val semanticsModifier = Modifier.semantics(mergeDescendants = true) {
            this.contentDescription = contentDescription
            role = Role.Button
            if (!isInteractive) {
                disabled()
            }
        }

        Box(
            modifier = Modifier
                .defaultMinSize(
                    minWidth = FrogFloatingActionButtonDefaults.MinTouchTarget,
                    minHeight = FrogFloatingActionButtonDefaults.MinTouchTarget
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
                modifier = Modifier
                    .shadow(
                        elevation = currentElevation,
                        shape = shape,
                        clip = false
                    )
                    .clip(shape)
                    .background(currentContainerColor)
                    .then(
                        if (isFocused && colors.focusRingColor != Color.Transparent) {
                            Modifier.border(2.dp, colors.focusRingColor, shape)
                        } else if (isDark) {
                            Modifier.border(1.dp, FrogTheme.colors.border, shape)
                        } else {
                            Modifier
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                CompositionLocalProvider(
                    LocalContentColor provides currentContentColor,
                    LocalTextStyle provides FrogTheme.typography.label
                ) {
                    when (presentation) {
                        FrogFabPresentation.Regular -> {
                            Box(
                                modifier = Modifier.size(56.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Box(modifier = Modifier.size(iconSize), contentAlignment = Alignment.Center) {
                                    icon()
                                }
                            }
                        }

                        FrogFabPresentation.Small -> {
                            Box(
                                modifier = Modifier.size(40.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Box(modifier = Modifier.size(iconSize), contentAlignment = Alignment.Center) {
                                    icon()
                                }
                            }
                        }

                        FrogFabPresentation.Extended -> {
                            val showLabel = expanded && label != null
                            Row(
                                modifier = Modifier
                                    .height(containerHeight)
                                    .animateContentSize(
                                        animationSpec = if (isReduced) snap() else androidx.compose.animation.core.tween(motion.normalDurationMillis, easing = motion.standardEasing)
                                    )
                                    .padding(
                                        start = 16.dp,
                                        end = if (showLabel) 20.dp else 16.dp
                                    ),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Box(modifier = Modifier.size(iconSize), contentAlignment = Alignment.Center) {
                                    icon()
                                }
                                if (showLabel) {
                                    Spacer(modifier = Modifier.width(FrogFloatingActionButtonDefaults.IconLabelSpacing))
                                    label?.invoke()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
