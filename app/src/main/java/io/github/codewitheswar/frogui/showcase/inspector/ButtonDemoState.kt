package io.github.codewitheswar.frogui.showcase.inspector

import androidx.compose.runtime.Stable
import io.github.codewitheswar.frogui.components.button.FrogButtonSize
import io.github.codewitheswar.frogui.components.button.FrogButtonVariant

/**
 * Isolated demo state for interactive property testing of [FrogButton].
 * The core FrogButton component remains completely decoupled from this showcase model.
 */
@Stable
data class ButtonDemoState(
    val variant: FrogButtonVariant = FrogButtonVariant.Primary,
    val size: FrogButtonSize = FrogButtonSize.Medium,
    val enabled: Boolean = true,
    val loading: Boolean = false,
    val hasLeadingIcon: Boolean = false,
    val hasTrailingIcon: Boolean = false,
    val fullWidth: Boolean = false,
    val buttonText: String = "Continue"
) {
    /**
     * Generates a realistic, conceptually compilable Kotlin usage snippet
     * matching the current state.
     */
    fun toCodeSnippet(): String {
        val params = mutableListOf<String>()
        if (variant != FrogButtonVariant.Primary) {
            params.add("    variant = FrogButtonVariant.$variant")
        }
        if (size != FrogButtonSize.Medium) {
            params.add("    size = FrogButtonSize.$size")
        }
        if (!enabled) {
            params.add("    enabled = false")
        }
        if (loading) {
            params.add("    loading = true")
        }
        if (fullWidth) {
            params.add("    modifier = Modifier.fillMaxWidth()")
        }
        if (hasLeadingIcon) {
            params.add("    leadingIcon = { Icon(Icons.Rounded.PlayArrow, contentDescription = null) }")
        }
        if (hasTrailingIcon) {
            params.add("    trailingIcon = { Icon(Icons.Rounded.ArrowForward, contentDescription = null) }")
        }
        params.add("    onClick = { /* Handle action */ }")

        val paramString = if (params.isNotEmpty()) {
            "\n" + params.joinToString(",\n") + "\n"
        } else ""

        return "FrogButton($paramString) {\n    Text(\"$buttonText\")\n}"
    }

    companion object {
        fun default() = ButtonDemoState()
    }
}
