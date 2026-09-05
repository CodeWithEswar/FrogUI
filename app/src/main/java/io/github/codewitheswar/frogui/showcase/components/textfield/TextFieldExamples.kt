package io.github.codewitheswar.frogui.showcase.components.textfield

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.components.button.FrogIconButton
import io.github.codewitheswar.frogui.components.button.FrogIconButtonDefaults
import io.github.codewitheswar.frogui.components.button.FrogIconButtonSize
import io.github.codewitheswar.frogui.components.button.FrogIconButtonVariant
import io.github.codewitheswar.frogui.components.textfield.FrogTextField
import io.github.codewitheswar.frogui.components.textfield.FrogTextFieldVariant
import io.github.codewitheswar.frogui.showcase.icons.FrogIcons

@Composable
internal fun TextFieldBasicExample() {
    // example:basic:start
    var name by rememberSaveable { mutableStateOf("") }

    FrogTextField(
        value = name,
        onValueChange = { name = it },
        label = "Full name",
        modifier = Modifier.fillMaxWidth(),
    )
    // example:basic:end
}

@Composable
internal fun TextFieldPlaceholderHelperExample() {
    // example:placeholder-helper:start
    var username by rememberSaveable { mutableStateOf("") }

    FrogTextField(
        value = username,
        onValueChange = { username = it },
        label = "Username",
        placeholder = "e.g. alex_dev",
        helperText = "Only lowercase letters, numbers, and underscores",
        modifier = Modifier.fillMaxWidth(),
    )
    // example:placeholder-helper:end
}

@Composable
internal fun TextFieldValidationErrorExample() {
    // example:validation-error:start
    var email by rememberSaveable { mutableStateOf("invalid-email") }
    val isValid = email.contains("@") && email.contains(".")

    FrogTextField(
        value = email,
        onValueChange = { email = it },
        label = "Email address",
        errorText = if (!isValid) "Enter a valid email address" else null,
        helperText = "We will send your login link here",
        modifier = Modifier.fillMaxWidth(),
    )
    // example:validation-error:end
}

@Composable
internal fun TextFieldLeadingTrailingExample() {
    // example:leading-trailing:start
    var query by rememberSaveable { mutableStateOf("Design tokens") }

    FrogTextField(
        value = query,
        onValueChange = { query = it },
        label = "Search components",
        leading = {
            Icon(
                imageVector = FrogIcons.Search,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
            )
        },
        trailing = if (query.isNotEmpty()) {
            {
                FrogIconButton(
                    icon = {
                        Icon(
                            imageVector = FrogIcons.Close,
                            contentDescription = null,
                            modifier = Modifier.size(FrogIconButtonDefaults.iconSize(FrogIconButtonSize.Small)),
                        )
                    },
                    contentDescription = "Clear search",
                    onClick = { query = "" },
                    variant = FrogIconButtonVariant.Ghost,
                    size = FrogIconButtonSize.Small,
                )
            }
        } else null,
        modifier = Modifier.fillMaxWidth(),
    )
    // example:leading-trailing:end
}

@Composable
internal fun TextFieldReadOnlyExample() {
    // example:read-only:start
    FrogTextField(
        value = "org_2026_production",
        onValueChange = {},
        label = "Organization identifier",
        readOnly = true,
        helperText = "Generated during account setup; cannot be modified",
        variant = FrogTextFieldVariant.Outline,
        modifier = Modifier.fillMaxWidth(),
    )
    // example:read-only:end
}

@Composable
internal fun TextFieldMultilineExample() {
    // example:multiline:start
    var notes by rememberSaveable {
        mutableStateOf("Release notes draft:\n- Added FrogTextField\n- Verified accessibility")
    }

    FrogTextField(
        value = notes,
        onValueChange = { notes = it },
        label = "Release notes",
        singleLine = false,
        maxLines = 4,
        modifier = Modifier.fillMaxWidth(),
    )
    // example:multiline:end
}

@Composable
internal fun TextFieldFormFlowExample() {
    // example:form-flow:start
    val focusManager = LocalFocusManager.current
    var firstName by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        FrogTextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = "First name",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            modifier = Modifier.fillMaxWidth(),
        )

        FrogTextField(
            value = email,
            onValueChange = { email = it },
            label = "Email",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            ),
            modifier = Modifier.fillMaxWidth(),
        )
    }
    // example:form-flow:end
}
