package io.github.codewitheswar.frogui.components.button

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.foundation.theme.FrogTheme

/**
 * Standard FrogUI Button component.
 *
 * Designed with strict monochrome aesthetic, slot-based architecture, tactile pressed motion,
 * accessibility semantics, and platform touch target compliance.
 *
 * @param onClick Action invoked when the button is clicked.
 * @param modifier Modifier applied to the button layout.
 * @param variant Visual semantic style (Primary, Secondary, Outline, Ghost, Destructive).
 * @param size Button dimension scale (Small 32dp, Medium 40dp, Large 48dp).
 * @param enabled Controls whether interaction is enabled.
 * @param loading When true, shows an inline progress indicator and suppresses click events.
 * @param shape Corner radius shape applied to the button background and border.
 * @param colors Color configuration across states.
 * @param border Optional border stroke around the button.
 * @param contentPadding Inner padding between the boundary and content.
 * @param leadingIcon Optional leading slot (icons, badges).
 * @param trailingIcon Optional trailing slot (chevrons, icons).
 * @param content The text or content row of the button.
 */
@Composable
fun FrogButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: FrogButtonVariant = FrogButtonVariant.Primary,
    size: FrogButtonSize = FrogButtonSize.Medium,
    enabled: Boolean = true,
    loading: Boolean = false,
    shape: Shape = FrogButtonDefaults.shape(size),
    colors: FrogButtonColors = FrogButtonDefaults.colors(variant),
    border: BorderStroke? = FrogButtonDefaults.border(variant, enabled && !loading),
    contentPadding: PaddingValues = FrogButtonDefaults.contentPadding(size),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    content: @Composable RowScope.() -> Unit
) {
    val isInteractive = enabled && !loading
    val isPressed by interactionSource.collectIsPressedAsState()
    val isFocused by interactionSource.collectIsFocusedAsState()

    // Tactile press spring animation (120ms)
    val scale by animateFloatAsState(
        targetValue = if (isPressed && isInteractive) 0.97f else 1.0f,
        animationSpec = FrogTheme.motion.fastSpec(),
        label = "frog_button_scale"
    )

    val currentContainerColor = when {
        !enabled -> colors.disabledContainerColor
        isPressed -> colors.containerColor
        else -> colors.containerColor
    }

    val currentContentColor = if (enabled) colors.contentColor else colors.disabledContentColor

    val textStyle = when (size) {
        FrogButtonSize.Small -> FrogTheme.typography.label
        FrogButtonSize.Medium -> FrogTheme.typography.bodySmall
        FrogButtonSize.Large -> FrogTheme.typography.body
    }

    // Compose accessibility semantics
    val semanticsModifier = Modifier.semantics {
        role = Role.Button
        if (loading) {
            stateDescription = "Loading"
        }
        if (!isInteractive) {
            disabled()
        }
    }

    // Outer box ensures platform minimum touch target (48dp)
    Box(
        modifier = modifier
            .defaultMinSize(minHeight = FrogButtonDefaults.MinTouchTarget)
            .scale(scale)
            .then(semanticsModifier),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .defaultMinSize(minHeight = size.minHeight)
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
                    indication = null, // Custom tactile scale and overlay provide crisp feedback
                    enabled = isInteractive,
                    onClick = onClick
                )
                .padding(contentPadding),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CompositionLocalProvider(
                LocalContentColor provides currentContentColor,
                LocalTextStyle provides textStyle
            ) {
                if (loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(size.iconSize),
                        color = currentContentColor,
                        strokeWidth = 2.dp,
                        strokeCap = StrokeCap.Round
                    )
                    Spacer(modifier = Modifier.width(size.iconSpacing))
                } else if (leadingIcon != null) {
                    leadingIcon()
                    Spacer(modifier = Modifier.width(size.iconSpacing))
                }

                content()

                if (!loading && trailingIcon != null) {
                    Spacer(modifier = Modifier.width(size.iconSpacing))
                    trailingIcon()
                }
            }
        }
    }
}
