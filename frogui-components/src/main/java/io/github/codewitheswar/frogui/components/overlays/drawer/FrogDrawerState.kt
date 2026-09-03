package io.github.codewitheswar.frogui.components.overlays.drawer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

/**
 * State of a [FrogDrawer] composable.
 *
 * Controls visibility and provides imperative suspend [open] and [close] operations.
 *
 * @param initialValue The initial value of the state.
 */
@Stable
class FrogDrawerState(
    initialValue: FrogDrawerValue = FrogDrawerValue.Closed
) {
    /**
     * The current value of the drawer state.
     */
    var currentValue: FrogDrawerValue by mutableStateOf(initialValue)
        internal set

    /**
     * The target value of the drawer state.
     */
    var targetValue: FrogDrawerValue by mutableStateOf(initialValue)
        internal set

    /**
     * Whether the drawer is currently open or opening.
     */
    val isOpen: Boolean
        get() = currentValue == FrogDrawerValue.Open || targetValue == FrogDrawerValue.Open

    /**
     * Whether the drawer is currently closed and not opening.
     */
    val isClosed: Boolean
        get() = !isOpen

    /**
     * Request opening the drawer.
     */
    suspend fun open() {
        targetValue = FrogDrawerValue.Open
        currentValue = FrogDrawerValue.Open
    }

    /**
     * Request closing the drawer.
     */
    suspend fun close() {
        targetValue = FrogDrawerValue.Closed
        currentValue = FrogDrawerValue.Closed
    }

    /**
     * Instantly updates the drawer state without animation.
     */
    fun snapTo(targetValue: FrogDrawerValue) {
        this.targetValue = targetValue
        this.currentValue = targetValue
    }

    companion object {
        /**
         * The default [Saver] implementation for [FrogDrawerState].
         */
        val Saver: Saver<FrogDrawerState, FrogDrawerValue> = Saver(
            save = { it.currentValue },
            restore = { FrogDrawerState(initialValue = it) }
        )
    }
}

/**
 * Create and remember a [FrogDrawerState] that survives configuration changes.
 *
 * @param initialValue The initial value of the drawer state.
 */
@Composable
fun rememberFrogDrawerState(
    initialValue: FrogDrawerValue = FrogDrawerValue.Closed
): FrogDrawerState {
    return rememberSaveable(saver = FrogDrawerState.Saver) {
        FrogDrawerState(initialValue = initialValue)
    }
}
