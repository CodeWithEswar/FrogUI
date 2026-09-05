package io.github.codewitheswar.frogui.components.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.theme.FrogTheme

private val PreviewMailIcon: ImageVector by lazy {
    ImageVector.Builder(
        name = "PreviewMail",
        defaultWidth = 20.dp,
        defaultHeight = 20.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
    ).apply {
        addPath(
            pathData = PathParser().parsePathString(
                "M4 4h16c1.1 0 2 .9 2 2v12c0 1.1-.9 2-2 2H4c-1.1 0-2-.9-2-2V6c0-1.1.9-2 2-2z M22 6l-10 7L2 6"
            ).toNodes(),
            fill = null,
            stroke = SolidColor(Color.Black),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
        )
    }.build()
}

@Preview(name = "Text Field - Default", group = "TextField")
@Composable
internal fun FrogTextField_Default() {
    FrogTheme {
        Box(Modifier.padding(16.dp)) {
            var text by remember { mutableStateOf("") }
            FrogTextField(
                value = text,
                onValueChange = { text = it },
                label = "Full name",
                placeholder = "Enter your name",
                helperText = "As shown on your ID",
            )
        }
    }
}

@Preview(name = "Text Field - Variants", group = "TextField")
@Composable
internal fun FrogTextField_Variants() {
    FrogTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            FrogTextField(
                value = "John Doe",
                onValueChange = {},
                label = "Filled",
                variant = FrogTextFieldVariant.Filled,
                helperText = "Filled container",
            )
            FrogTextField(
                value = "John Doe",
                onValueChange = {},
                label = "Outline",
                variant = FrogTextFieldVariant.Outline,
                helperText = "Outlined border",
            )
            FrogTextField(
                value = "John Doe",
                onValueChange = {},
                label = "Underline",
                variant = FrogTextFieldVariant.Underline,
                helperText = "Underlined baseline",
            )
        }
    }
}

@Preview(name = "Text Field - States", group = "TextField")
@Composable
internal fun FrogTextField_States() {
    FrogTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            FrogTextField(
                value = "",
                onValueChange = {},
                label = "Default",
                placeholder = "Enter text...",
                variant = FrogTextFieldVariant.Outline,
            )
            FrogTextField(
                value = "invalid-email",
                onValueChange = {},
                label = "Error State",
                errorText = "Enter a valid email address",
                variant = FrogTextFieldVariant.Outline,
            )
            FrogTextField(
                value = "read-only-content@example.com",
                onValueChange = {},
                label = "Read Only",
                readOnly = true,
                variant = FrogTextFieldVariant.Outline,
                helperText = "This value cannot be edited",
            )
            FrogTextField(
                value = "disabled@example.com",
                onValueChange = {},
                label = "Disabled",
                enabled = false,
                variant = FrogTextFieldVariant.Outline,
            )
        }
    }
}

@Preview(name = "Text Field - Slots", group = "TextField")
@Composable
internal fun FrogTextField_Slots() {
    FrogTheme {
        Box(Modifier.padding(16.dp)) {
            var text by remember { mutableStateOf("team@example.com") }
            FrogTextField(
                value = text,
                onValueChange = { text = it },
                label = "Email address",
                leading = {
                    Icon(PreviewMailIcon, null, Modifier.size(20.dp))
                },
                trailing = {
                    Text("@", style = FrogTheme.typography.code)
                },
                helperText = "We will never share your email",
            )
        }
    }
}

@Preview(name = "Text Field - Multiline", group = "TextField")
@Composable
internal fun FrogTextField_Multiline() {
    FrogTheme {
        Box(Modifier.padding(16.dp)) {
            var text by remember {
                mutableStateOf("Here is a longer multi-line note spanning several lines of text.")
            }
            FrogTextField(
                value = text,
                onValueChange = { text = it },
                label = "Project description",
                singleLine = false,
                maxLines = 4,
                variant = FrogTextFieldVariant.Outline,
            )
        }
    }
}

@Preview(name = "Text Field - Dark Theme", group = "TextField")
@Composable
internal fun FrogTextField_Dark() {
    FrogTheme(darkTheme = true) {
        Box(
            Modifier
                .background(FrogTheme.colors.background)
                .padding(16.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                FrogTextField(
                    value = "Dark filled",
                    onValueChange = {},
                    label = "Filled",
                    variant = FrogTextFieldVariant.Filled,
                )
                FrogTextField(
                    value = "Dark outline",
                    onValueChange = {},
                    label = "Outline",
                    variant = FrogTextFieldVariant.Outline,
                )
                FrogTextField(
                    value = "Dark underline",
                    onValueChange = {},
                    label = "Underline",
                    variant = FrogTextFieldVariant.Underline,
                )
            }
        }
    }
}
