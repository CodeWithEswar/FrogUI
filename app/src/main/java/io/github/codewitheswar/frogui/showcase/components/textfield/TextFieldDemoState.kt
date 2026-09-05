package io.github.codewitheswar.frogui.showcase.components.textfield

import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.mapSaver
import io.github.codewitheswar.frogui.components.textfield.FrogTextFieldVariant

internal data class TextFieldDemoState(
    val value: String = "",
    val label: String = "Email",
    val placeholder: String = "name@example.com",
    val helperText: String = "Use your work email address",
    val errorText: String? = null,
    val variant: FrogTextFieldVariant = FrogTextFieldVariant.Filled,
    val leadingEnabled: Boolean = true,
    val trailingEnabled: Boolean = false,
    val enabled: Boolean = true,
    val readOnly: Boolean = false,
    val singleLine: Boolean = true,
    val maxLines: Int = 1,
) {
    fun toCodeSnippet(): String {
        val params = mutableListOf<String>()
        params.add("value = value")
        params.add("onValueChange = { value = it }")

        if (label.isNotEmpty()) {
            params.add("label = \"$label\"")
        }
        if (placeholder.isNotEmpty()) {
            params.add("placeholder = \"$placeholder\"")
        }
        if (!errorText.isNullOrEmpty()) {
            params.add("errorText = \"$errorText\"")
        } else if (helperText.isNotEmpty()) {
            params.add("helperText = \"$helperText\"")
        }

        if (variant != FrogTextFieldVariant.Filled) {
            params.add("variant = FrogTextFieldVariant.$variant")
        }

        if (leadingEnabled) {
            params.add("leading = {\n        Icon(FrogIcons.Search, contentDescription = null, Modifier.size(20.dp))\n    }")
        }
        if (trailingEnabled) {
            params.add("trailing = {\n        FrogIconButton(\n            icon = { Icon(FrogIcons.Close, contentDescription = null) },\n            contentDescription = \"Clear input\",\n            onClick = { value = \"\" },\n            variant = FrogIconButtonVariant.Ghost,\n            size = FrogIconButtonSize.Small,\n        )\n    }")
        }

        if (!enabled) {
            params.add("enabled = false")
        }
        if (readOnly) {
            params.add("readOnly = true")
        }
        if (!singleLine) {
            params.add("singleLine = false")
            if (maxLines != Int.MAX_VALUE) {
                params.add("maxLines = $maxLines")
            }
        }

        val indent = "    "
        val paramsString = params.joinToString(",\n$indent")

        return """
var value by rememberSaveable { mutableStateOf("$value") }

FrogTextField(
$indent$paramsString,
)
""".trimIndent()
    }

    companion object {
        val Saver: Saver<TextFieldDemoState, Any> = mapSaver(
            save = { state ->
                mapOf(
                    "value" to state.value,
                    "label" to state.label,
                    "placeholder" to state.placeholder,
                    "helperText" to state.helperText,
                    "errorText" to (state.errorText ?: ""),
                    "variant" to state.variant.name,
                    "leadingEnabled" to state.leadingEnabled,
                    "trailingEnabled" to state.trailingEnabled,
                    "enabled" to state.enabled,
                    "readOnly" to state.readOnly,
                    "singleLine" to state.singleLine,
                    "maxLines" to state.maxLines,
                )
            },
            restore = { map ->
                val errorStr = map["errorText"] as? String
                TextFieldDemoState(
                    value = map["value"] as? String ?: "",
                    label = map["label"] as? String ?: "Email",
                    placeholder = map["placeholder"] as? String ?: "name@example.com",
                    helperText = map["helperText"] as? String ?: "Use your work email address",
                    errorText = if (errorStr.isNullOrEmpty()) null else errorStr,
                    variant = runCatching { FrogTextFieldVariant.valueOf(map["variant"] as String) }.getOrDefault(FrogTextFieldVariant.Filled),
                    leadingEnabled = map["leadingEnabled"] as? Boolean ?: true,
                    trailingEnabled = map["trailingEnabled"] as? Boolean ?: false,
                    enabled = map["enabled"] as? Boolean ?: true,
                    readOnly = map["readOnly"] as? Boolean ?: false,
                    singleLine = map["singleLine"] as? Boolean ?: true,
                    maxLines = map["maxLines"] as? Int ?: 1,
                )
            }
        )
    }
}
