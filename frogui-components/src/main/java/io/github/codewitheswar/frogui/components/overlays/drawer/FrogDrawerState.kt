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
 * Hoist this helper when several actions open or close the same drawer. For a simple Boolean
 * owner, use the visible overload instead. [open], [close] and [snapTo] update requested
 * visibility immediately; they do not await or describe the rendered transition's progress.
 * Call mutations from the UI owner. Use [rememberFrogDrawerState] to save across recreation.
 *
 * @param initialValue The initial value of the state.
 */
@Stable
class FrogDrawerState(
    initialValue: FrogDrawerValue = FrogDrawerValue.Closed
) {
    /**
     * Last requested visibility. It changes immediately, independently of the exit animation.
     */
    var currentValue: FrogDrawerValue by mutableStateOf(initialValue)
        internal set

    /**
     * Requested destination; currently updated together with [currentValue].
     */
    var targetValue: FrogDrawerValue by mutableStateOf(initialValue)
        internal set

    /**
     * Whether Open is requested. This is not a measurement of on-screen animation progress.
     */
    val isOpen: Boolean
        get() = currentValue == FrogDrawerValue.Open || targetValue == FrogDrawerValue.Open

    /**
     * Whether Closed is requested; a closing visual transition may still be running.
     */
    val isClosed: Boolean
        get() = !isOpen

    /**
     * Requests Open and returns immediately. Suspension is retained for API compatibility;
     * it does not await the opening transition.
     */
    suspend fun open() {
        targetValue = FrogDrawerValue.Open
        currentValue = FrogDrawerValue.Open
    }

    /**
     * Requests Closed and returns immediately; it does not await the closing transition.
     */
    suspend fun close() {
        targetValue = FrogDrawerValue.Closed
        currentValue = FrogDrawerValue.Closed
    }

    /**
     * Immediately changes requested visibility. It does not bypass the renderer's motion;
     * supply zero-duration theme motion when transitions must be disabled.
     */
    fun snapTo(targetValue: FrogDrawerValue) {
        this.targetValue = targetValue
        this.currentValue = targetValue
    }

    companion object {
        /**
         * Saves requested visibility only; callbacks, content and animation frames are not saved.
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
