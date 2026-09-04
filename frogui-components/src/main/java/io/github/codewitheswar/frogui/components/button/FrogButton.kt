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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.stringResource
import io.github.codewitheswar.frogui.components.R
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.theme.FrogTheme

/**
 * Performs a caller-owned action using FrogUI semantic variants and composable row content.
 *
 * The common case only needs [onClick] and [content]. Loading keeps the measured label/slots
 * and accessible action label while showing a centered spinner and suppressing activation.
 * Disabled buttons also suppress activation. The whole minimum 48dp target owns one button
 * action; restrictive parent constraints or consumer semantics can override these defaults.
 * Place in FrogTheme. This component remains Experimental in the current development snapshot.
 *
 * @param onClick Action invoked when the button is clicked.
 * @param modifier Modifier applied to the button layout.
 * @param variant Visual semantic style (Primary, Secondary, Outline, Ghost, Destructive).
 * @param size Button dimension scale (Small 32dp, Medium 40dp, Large 48dp).
 * @param enabled Controls whether interaction is enabled.
 * @param loading Shows centered progress with a Loading state description; the caller ends loading.
 * @param shape Corner radius shape applied to the button background and border.
 * @param colors Color configuration across states.
 * @param border Optional border stroke around the button.
 * @param contentPadding Inner padding between the boundary and content.
 * @param interactionSource Remembered stream used for press/focus feedback; hoist to observe it.
 * @param leadingIcon Optional decorative content before the label; use null icon descriptions.
 * @param trailingIcon Optional decorative content after the label; no icon-library dependency.
 * @param fullWidth Fills both the visible surface and outer target within bounded parent width.
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
    border: BorderStroke? = FrogButtonDefaults.border(colors, enabled),
    contentPadding: PaddingValues = FrogButtonDefaults.contentPadding(size),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    fullWidth: Boolean = false,
    content: @Composable RowScope.() -> Unit
) {
    val isInteractive = enabled && !loading
    val isPressed by interactionSource.collectIsPressedAsState()
    val isFocused by interactionSource.collectIsFocusedAsState()

    // Local fast feedback; reduced motion retains the color/state change without scale.
    val scale by animateFloatAsState(
        targetValue = if (isPressed && isInteractive && FrogTheme.motion.fastDurationMillis > 0) 0.97f else 1.0f,
        animationSpec = FrogTheme.motion.fastSpec(),
        label = "frog_button_scale"
    )

    val currentContainerColor by animateColorAsState(when {
        !enabled -> colors.disabledContainerColor
        isPressed -> colors.pressedOverlayColor.compositeOver(colors.containerColor)
        else -> colors.containerColor
    }, animationSpec = FrogTheme.motion.fastSpec(), label = "frog_button_container")

    val currentContentColor = if (enabled) colors.contentColor else colors.disabledContentColor

    val textStyle = when (size) {
        FrogButtonSize.Small -> FrogTheme.typography.label
        FrogButtonSize.Medium -> FrogTheme.typography.bodySmall
        FrogButtonSize.Large -> FrogTheme.typography.body
    }

    // Compose accessibility semantics
    val loadingDescription = stringResource(R.string.frogui_loading)
    val semanticsModifier = Modifier.semantics(mergeDescendants = true) {
        role = Role.Button
        if (loading) {
            stateDescription = loadingDescription
        }
        if (!isInteractive) {
            disabled()
        }
    }

    // Outer box ensures platform minimum touch target (48dp)
    Box(
        modifier = modifier
            .then(if (fullWidth) Modifier.fillMaxWidth() else Modifier)
            .defaultMinSize(minWidth = FrogTheme.sizing.minimumTouchTarget, minHeight = FrogTheme.sizing.minimumTouchTarget)
            .scale(scale)
            .then(semanticsModifier)
            .clickable(interactionSource, indication = null, enabled = isInteractive, role = Role.Button, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .then(if (fullWidth) Modifier.fillMaxWidth() else Modifier)
                .defaultMinSize(minHeight = FrogButtonDefaults.controlHeight(size))
                .clip(shape)
                .background(currentContainerColor)
                .then(
                    if (isFocused && colors.focusRingColor != Color.Transparent) {
                        Modifier.border(2.dp, colors.focusRingColor, shape)
                    } else if (border != null) {
                        Modifier.border(border, shape)
                    } else Modifier
                )
                .padding(contentPadding),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CompositionLocalProvider(
                LocalContentColor provides currentContentColor,
                LocalTextStyle provides textStyle
            ) {
                // Keep the exact label and slot layout while showing a centered spinner.
                // Alpha preserves the accessible action label without announcing spinner ticks.
                Box(contentAlignment = Alignment.Center) {
                    Row(Modifier.alpha(if (loading) 0f else 1f), verticalAlignment = Alignment.CenterVertically) {
                        if (leadingIcon != null) { leadingIcon(); Spacer(Modifier.width(size.iconSpacing)) }
                        content()
                        if (trailingIcon != null) { Spacer(Modifier.width(size.iconSpacing)); trailingIcon() }
                    }
                    if (loading) FrogButtonProgress(currentContentColor, Modifier.size(FrogButtonDefaults.iconSize(size)))
                }
            }
        }
    }
}
