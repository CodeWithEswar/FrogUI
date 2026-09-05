package io.github.codewitheswar.frogui.showcase.components.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.components.textfield.FrogTextField
import io.github.codewitheswar.frogui.components.textfield.FrogTextFieldVariant
import io.github.codewitheswar.frogui.theme.FrogTheme

@Preview(name = "Showcase Text Field Gallery", group = "TextField")
@Composable
fun TextFieldComponentPreviews() {
    FrogTheme {
        Column(
            modifier = Modifier
                .background(FrogTheme.colors.background)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Variants", style = FrogTheme.typography.heading, color = FrogTheme.colors.foreground)

            FrogTextField(
                value = "alex@example.com",
                onValueChange = {},
                label = "Filled variant",
                variant = FrogTextFieldVariant.Filled,
                helperText = "With helper message",
                modifier = Modifier.fillMaxWidth(),
            )

            FrogTextField(
                value = "alex@example.com",
                onValueChange = {},
                label = "Outline variant",
                variant = FrogTextFieldVariant.Outline,
                modifier = Modifier.fillMaxWidth(),
            )

            FrogTextField(
                value = "alex@example.com",
                onValueChange = {},
                label = "Underline variant",
                variant = FrogTextFieldVariant.Underline,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
