package io.github.codewitheswar.frogui.showcase.components.button

import androidx.compose.runtime.saveable.listSaver

import androidx.compose.runtime.Stable
import io.github.codewitheswar.frogui.components.button.FrogButton
import io.github.codewitheswar.frogui.components.button.FrogButtonSize
import io.github.codewitheswar.frogui.components.button.FrogButtonVariant
import io.github.codewitheswar.frogui.showcase.colorpicker.FrogColorValue

internal enum class ButtonShape { Default, Square, Pill }

/**
 * Isolated demo state for interactive property testing of [FrogButton].
 * The core FrogButton component remains completely decoupled from this showcase model.
 */
@Stable
internal data class ButtonDemoState(
    val variant: FrogButtonVariant = FrogButtonVariant.Primary,
    val size: FrogButtonSize = FrogButtonSize.Medium,
    val enabled: Boolean = true,
    val loading: Boolean = false,
    val hasLeadingIcon: Boolean = false,
    val hasTrailingIcon: Boolean = false,
    val fullWidth: Boolean = false,
    val buttonText: String = "Continue",
    val colorOverrides: Map<ButtonColorProperty, FrogColorValue> = emptyMap(),
    val shape: ButtonShape = ButtonShape.Default,
) {
    fun withVariant(value: FrogButtonVariant) = if (value == variant) this else copy(variant = value, colorOverrides = emptyMap())
    fun withColor(property: ButtonColorProperty, value: FrogColorValue?) = copy(colorOverrides = if (value == null) colorOverrides - property else colorOverrides + (property to value))
    fun resetColors() = copy(colorOverrides = emptyMap())
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
            params.add("    fullWidth = true")
        }
        if (shape != ButtonShape.Default) params.add("    shape = ${if (shape == ButtonShape.Square) "RectangleShape" else "RoundedCornerShape(percent = 50)"}")
        if (colorOverrides.isNotEmpty()) {
            val colorParams = mutableListOf<String>()
            if (variant != FrogButtonVariant.Primary) colorParams += "        variant = FrogButtonVariant.$variant"
            ButtonColorProperty.entries.forEach { property -> colorOverrides[property]?.let { colorParams += "        ${property.parameter} = ${it.code()}" } }
            params += "    colors = FrogButtonDefaults.colors(\n${colorParams.joinToString(",\n")}\n    )"
        }
        if (hasLeadingIcon) {
            params.add("    leadingIcon = { Icon(FrogIcons.Play, contentDescription = null) }")
        }
        if (hasTrailingIcon) {
            params.add("    trailingIcon = { Icon(FrogIcons.Forward, contentDescription = null) }")
        }
        params.add("    onClick = { /* Handle action */ }")

        val paramString = if (params.isNotEmpty()) {
            "\n" + params.joinToString(",\n") + "\n"
        } else ""

        val label = buttonText.replace("\\", "\\\\").replace("\"", "\\\"")
            .replace("$", "\\$").replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t")
        return "FrogButton($paramString) {\n    Text(\"$label\")\n}"
    }

    companion object {
        val saver = listSaver<ButtonDemoState, Any>(
            save = { listOf(it.variant.name, it.size.name, it.enabled, it.loading, it.hasLeadingIcon, it.hasTrailingIcon, it.fullWidth, it.buttonText,
                it.colorOverrides.entries.joinToString("|") { (property, value) -> "${property.name}=${value.encode()}" }, it.shape.name) },
            restore = { saved -> ButtonDemoState(FrogButtonVariant.valueOf(saved[0] as String), FrogButtonSize.valueOf(saved[1] as String), saved[2] as Boolean, saved[3] as Boolean, saved[4] as Boolean, saved[5] as Boolean, saved[6] as Boolean, saved[7] as String,
                (saved.getOrNull(8) as? String).orEmpty().split('|').mapNotNull { entry ->
                    val parts = entry.split('=', limit = 2)
                    if (parts.size != 2) null else FrogColorValue.decode(parts[1])?.let { ButtonColorProperty.valueOf(parts[0]) to it }
                }.toMap(), (saved.getOrNull(9) as? String)?.let(ButtonShape::valueOf) ?: ButtonShape.Default) },
        )
        fun default() = ButtonDemoState()
    }
}
