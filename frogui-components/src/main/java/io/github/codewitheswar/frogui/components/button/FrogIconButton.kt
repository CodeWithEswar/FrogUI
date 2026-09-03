package io.github.codewitheswar.frogui.components.button

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
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.foundation.theme.FrogTheme

/**
 * Icon-only button component for FrogUI.
 *
 * Enforces accessible touch targets (minimum 48dp), concise TalkBack labels,
 * and consistent sizing.
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
    border: BorderStroke? = FrogButtonDefaults.border(variant, enabled && !loading),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit
) {
    val isInteractive = enabled && !loading
    val isPressed by interactionSource.collectIsPressedAsState()
    val isFocused by interactionSource.collectIsFocusedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed && isInteractive) 0.95f else 1.0f,
        animationSpec = FrogTheme.motion.fastSpec(),
        label = "frog_icon_button_scale"
    )

    val currentContainerColor = when {
        !enabled -> colors.disabledContainerColor
        isPressed -> colors.containerColor
        else -> colors.containerColor
    }

    val currentContentColor = if (enabled) colors.contentColor else colors.disabledContentColor

    val semanticsModifier = Modifier.semantics {
        this.contentDescription = contentDescription
        role = Role.Button
        if (loading) {
            stateDescription = "Loading"
        }
        if (!isInteractive) {
            disabled()
        }
    }

    Box(
        modifier = modifier
            .defaultMinSize(minWidth = FrogButtonDefaults.MinTouchTarget, minHeight = FrogButtonDefaults.MinTouchTarget)
            .scale(scale)
            .then(semanticsModifier),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(size.minHeight)
                .clip(shape)
                .background(currentContainerColor)
                .then(
                    if (isFocused && colors.focusRingColor != Color.Transparent) {
                        Modifier.border(2.dp, colors.focusRingColor, shape)
                    } else if (border != null) {
                        Modifier.border(border, shape)
                    } else Modifier
                )
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    enabled = isInteractive,
                    onClick = onClick
                ),
            contentAlignment = Alignment.Center
        ) {
            CompositionLocalProvider(
                LocalContentColor provides currentContentColor
            ) {
                if (loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(size.iconSize),
                        color = currentContentColor,
                        strokeWidth = 2.dp,
                        strokeCap = StrokeCap.Round
                    )
                } else {
                    content()
                }
            }
        }
    }
}
