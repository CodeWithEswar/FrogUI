package io.github.codewitheswar.frogui.showcase.components.textfield

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.components.button.FrogIconButton
import io.github.codewitheswar.frogui.components.button.FrogIconButtonDefaults
import io.github.codewitheswar.frogui.components.button.FrogIconButtonSize
import io.github.codewitheswar.frogui.components.button.FrogIconButtonVariant
import io.github.codewitheswar.frogui.components.textfield.FrogTextField
import io.github.codewitheswar.frogui.showcase.icons.FrogIcons

import androidx.compose.ui.platform.testTag

@Composable
internal fun TextFieldLivePreview(
    state: TextFieldDemoState,
    onChange: (TextFieldDemoState) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        FrogTextField(
            value = state.value,
            onValueChange = { onChange(state.copy(value = it)) },
            modifier = Modifier.fillMaxWidth().testTag("showcase-textfield-preview"),
            label = state.label.ifEmpty { null },
            placeholder = state.placeholder.ifEmpty { null },
            helperText = state.helperText.ifEmpty { null },
            errorText = state.errorText?.ifEmpty { null },
            variant = state.variant,
            leading = if (state.leadingEnabled) {
                {
                    Icon(
                        imageVector = FrogIcons.Search,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                    )
                }
            } else null,
            trailing = if (state.trailingEnabled && state.value.isNotEmpty()) {
                {
                    FrogIconButton(
                        icon = {
                            Icon(
                                imageVector = FrogIcons.Close,
                                contentDescription = null,
                                modifier = Modifier.size(FrogIconButtonDefaults.iconSize(FrogIconButtonSize.Small)),
                            )
                        },
                        contentDescription = "Clear input",
                        onClick = { onChange(state.copy(value = "")) },
                        variant = FrogIconButtonVariant.Ghost,
                        size = FrogIconButtonSize.Small,
                    )
                }
            } else null,
            enabled = state.enabled,
            readOnly = state.readOnly,
            singleLine = state.singleLine,
            maxLines = state.maxLines,
        )
    }
}

@Composable
internal fun TextFieldInspectorPreview(
    state: TextFieldDemoState,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
        contentAlignment = Alignment.Center,
    ) {
        FrogTextField(
            value = state.value,
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            label = state.label.ifEmpty { null },
            placeholder = state.placeholder.ifEmpty { null },
            helperText = state.helperText.ifEmpty { null },
            errorText = state.errorText?.ifEmpty { null },
            variant = state.variant,
            leading = if (state.leadingEnabled) {
                {
                    Icon(
                        imageVector = FrogIcons.Search,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                    )
                }
            } else null,
            trailing = if (state.trailingEnabled && state.value.isNotEmpty()) {
                {
                    Icon(
                        imageVector = FrogIcons.Close,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                    )
                }
            } else null,
            enabled = state.enabled,
            readOnly = state.readOnly,
            singleLine = state.singleLine,
            maxLines = state.maxLines,
        )
    }
}
