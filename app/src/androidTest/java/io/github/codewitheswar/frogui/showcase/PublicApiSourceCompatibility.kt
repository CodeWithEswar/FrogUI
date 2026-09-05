@file:Suppress("DEPRECATION") // Deliberate legacy-consumer compilation coverage.

package io.github.codewitheswar.frogui.showcase

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import io.github.codewitheswar.frogui.components.button.*
import io.github.codewitheswar.frogui.components.fab.*
import io.github.codewitheswar.frogui.components.overlays.drawer.*

/** Compiled consumer fixture: named slots, positional prefixes and retained legacy calls. */
@Composable
private fun ExistingConsumerCalls(onClick: () -> Unit, state: FrogDrawerState) {
    FrogButton(onClick, Modifier, FrogButtonVariant.Outline, FrogButtonSize.Small,
        leadingIcon = { Text("+") }, trailingIcon = { Text(">") }, fullWidth = true) { Text("Save") }
    FrogIconButton(onClick, "Add", colors = FrogButtonDefaults.colors(contentColor = Color.Red)) { Text("+") }
    FrogDrawer(state, onClick, title = "Properties", footer = { Text("Footer") }) { Text("Body") }
    FrogDrawer(visible = false, onDismissRequest = onClick, presentation = FrogDrawerPresentation.Auto,
        title = "Boolean owner") { Text("Body") }
    FrogDrawer(false, onClick, "Legacy", side = false, onBack = onClick, actions = { Text("Footer") }) { Text("Body") }
    FrogFloatingActionButton(
        icon = { Text("+") },
        contentDescription = "Create item",
        onClick = onClick,
        presentation = io.github.codewitheswar.frogui.components.fab.FrogFabPresentation.Extended,
        label = { Text("Create") }
    )
    FrogFloatingActionButton(
        icon = { Text("+") },
        contentDescription = "Quick add",
        onClick = onClick,
        presentation = io.github.codewitheswar.frogui.components.fab.FrogFabPresentation.Small
    )
    io.github.codewitheswar.frogui.components.textfield.FrogTextField(
        value = "text",
        onValueChange = {},
        label = "Label",
        placeholder = "Hint",
        helperText = "Helper",
        variant = io.github.codewitheswar.frogui.components.textfield.FrogTextFieldVariant.Filled
    )
    io.github.codewitheswar.frogui.components.textfield.FrogTextField(
        value = "text",
        onValueChange = {},
        variant = io.github.codewitheswar.frogui.components.textfield.FrogTextFieldVariant.Outline,
        errorText = "Error"
    )
    io.github.codewitheswar.frogui.components.textfield.FrogTextField(
        value = "text",
        onValueChange = {},
        variant = io.github.codewitheswar.frogui.components.textfield.FrogTextFieldVariant.Underline,
        readOnly = true
    )
}
