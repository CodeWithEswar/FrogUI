package io.github.codewitheswar.frogui.components.button

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import io.github.codewitheswar.frogui.components.R
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.theme.FrogTheme

/**
 * Icon-only button component for FrogUI.
 *
 * One labelled action owns the complete minimum 48dp target. Loading and disabled state
 * suppress activation; the caller owns both values. The loading indicator is decorative,
 * and the action label remains available. Restrictive parent constraints can reduce the target.
 * This companion API follows Button's Experimental lifecycle.
 *
 * @param onClick Called for touch, keyboard or accessibility activation while interactive.
 * @param contentDescription Required concise action label. Decorative child icons use null.
 * @param modifier Applied to the outer interaction target.
 * @param variant Semantic emphasis; defaults to Ghost for a quiet icon action.
 * @param size Visible square/icon dimensions; all sizes retain the minimum target.
 * @param enabled False uses disabled colors and prevents activation.
 * @param loading Replaces the icon with progress and exposes Loading without duplicate progress semantics.
 * @param shape Clips the visible surface and defines its border/focus outline.
 * @param colors Immutable enabled, disabled, pressed and focused colors from local theme defaults.
 * @param border Optional visible stroke; by default uses the supplied colors and enabled state.
 * @param interactionSource Remembered stream for press/focus feedback; hoist to observe interactions.
 * @param content Decorative icon content inheriting the resolved content color.
 */
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
