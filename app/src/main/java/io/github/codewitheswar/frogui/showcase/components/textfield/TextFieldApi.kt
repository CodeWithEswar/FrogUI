package io.github.codewitheswar.frogui.showcase.components.textfield

import io.github.codewitheswar.frogui.registry.ComponentPropertyMetadata
import io.github.codewitheswar.frogui.showcase.detail.ApiCategory
import io.github.codewitheswar.frogui.showcase.detail.ComponentApiProperty
import io.github.codewitheswar.frogui.showcase.detail.ComponentApiValue

internal fun textFieldApiProperty(property: ComponentPropertyMetadata): ComponentApiProperty {
    val category = when (property.name) {
        "value", "onValueChange" -> ApiCategory.Core
        "label", "placeholder", "helperText", "errorText" -> ApiCategory.Content
        "leading", "trailing" -> ApiCategory.Content
        "variant", "shape", "colors" -> ApiCategory.Appearance
        "enabled", "readOnly" -> ApiCategory.State
        "singleLine", "maxLines", "keyboardOptions", "keyboardActions", "visualTransformation" -> ApiCategory.Behavior
        "modifier" -> ApiCategory.Layout
        "interactionSource" -> ApiCategory.Advanced
        else -> ApiCategory.Advanced
    }

    val guidance = when (property.name) {
        "value" -> "The current input text string. Owned and hoisted by the caller; FrogTextField does not manage internal state."
        "onValueChange" -> "Callback invoked whenever the user modifies text. The consumer decides validation and formatting policy before updating value."
        "label" -> "The primary persistent identifier for the field. Does not disappear when the user begins typing, maintaining accessibility and orientation."
        "placeholder" -> "Supplemental example hint shown only when value is empty. Never use placeholder as the sole identifier for a form field."
        "helperText" -> "Non-error supporting guidance rendered below the field. Automatically replaced when errorText is present."
        "errorText" -> "Validation error message. When non-null, activates error container styling and exposes semantic error information to assistive technology."
        "variant" -> "Visual presentation style: Filled (surface container), Outline (surrounding border), or Underline (minimal baseline indicator)."
        "leading" -> "Composable slot rendered at the start of the field (e.g. search icon, country flag, or currency symbol). Decorative icons should use contentDescription = null."
        "trailing" -> "Composable slot rendered at the end of the field (e.g. clear button, dropdown trigger, or status icon). Interactive actions should use FrogIconButton."
        "enabled" -> "When false, suppresses all user interaction, disables the input, and renders in muted disabled tones."
        "readOnly" -> "When true, prevents text modification while preserving text readability, focusability, and clipboard selection."
        "singleLine" -> "When true, forces text into a single horizontally scrolling line and configures appropriate IME behavior."
        "maxLines" -> "Limits the maximum visual lines rendered. When singleLine is true, effectively 1."
        "keyboardOptions" -> "Software keyboard configuration specifying keyboard type, auto-correction, capitalization, and IME action."
        "keyboardActions" -> "Callbacks triggered when IME actions (Done, Next, Search, Go) are executed by the user."
        "visualTransformation" -> "Transforms the displayed text (e.g. password masking or phone number formatting) without altering the underlying string value."
        "colors" -> "Immutable color mapping for container, borders, text, label, placeholder, helper, error, and icons across all states."
        "shape" -> "Corner geometry applied to the container and border."
        "modifier" -> "Layout modifier applied to the outer field container."
        "interactionSource" -> "Hoisted interaction source used to observe focus and pressed events."
        else -> property.description
    }

    val argument = when (property.name) {
        "label" -> "label = \"Email\""
        "placeholder" -> "placeholder = \"name@example.com\""
        "helperText" -> "helperText = \"We will send your login link here\""
        "errorText" -> "errorText = \"Enter a valid email address\""
        "variant" -> "variant = FrogTextFieldVariant.Outline"
        "readOnly" -> "readOnly = true"
        "enabled" -> "enabled = false"
        "singleLine" -> "singleLine = true"
        "maxLines" -> "maxLines = 4"
        "leading" -> "leading = { Icon(FrogIcons.Search, null) }"
        "trailing" -> "trailing = { FrogIconButton(icon = { Icon(FrogIcons.Close, null) }, contentDescription = \"Clear\", onClick = {}) }"
        else -> null
    }

    val code = "var text by rememberSaveable { mutableStateOf(\"\") }\n\n" +
        "FrogTextField(\n" +
        "    value = text,\n" +
        "    onValueChange = { text = it },\n" +
        (argument?.let { "    $it,\n" } ?: "") +
        ")"

    val values = when (property.name) {
        "variant" -> listOf(
            "Filled" to "Filled surface container with subtle background and accent indicator.",
            "Outline" to "Clear bounded container with a surrounding border outline.",
            "Underline" to "Minimal form surface with an active bottom underline indicator."
        )
        else -> emptyList()
    }

    return ComponentApiProperty(
        metadata = property,
        category = category,
        guidance = guidance,
        example = code,
        values = values.map { ComponentApiValue(it.first, it.second) }
    )
}
