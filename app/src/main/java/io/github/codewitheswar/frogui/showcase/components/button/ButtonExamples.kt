package io.github.codewitheswar.frogui.showcase.components.button

import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.foundation.layout.size
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.showcase.icons.FrogIcons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.codewitheswar.frogui.components.button.FrogButton
import io.github.codewitheswar.frogui.components.button.FrogButtonVariant

// example:primary:start
@Composable
internal fun ButtonPrimaryExample(modifier: Modifier = Modifier) {
    FrogButton(
        onClick = {},
        modifier = modifier,
        variant = FrogButtonVariant.Primary
    ) {
        Text("Continue")
    }
}
// example:primary:end

// example:secondary:start
@Composable
internal fun ButtonSecondaryExample(modifier: Modifier = Modifier) {
    FrogButton(
        onClick = {},
        modifier = modifier,
        variant = FrogButtonVariant.Secondary
    ) {
        Text("Cancel")
    }
}
// example:secondary:end

// example:outline:start
@Composable
internal fun ButtonOutlineExample(modifier: Modifier = Modifier) {
    FrogButton(
        onClick = {},
        modifier = modifier,
        variant = FrogButtonVariant.Outline
    ) {
        Text("Documentation")
    }
}
// example:outline:end

// example:ghost:start
@Composable
internal fun ButtonGhostExample(modifier: Modifier = Modifier) {
    FrogButton(
        onClick = {},
        modifier = modifier,
        variant = FrogButtonVariant.Ghost
    ) {
        Text("Learn more")
    }
}
// example:ghost:end

// example:destructive:start
@Composable
internal fun ButtonDestructiveExample(modifier: Modifier = Modifier) {
    FrogButton(
        onClick = {},
        modifier = modifier,
        variant = FrogButtonVariant.Destructive
    ) {
        Text("Delete repository")
    }
}
// example:destructive:end

// example:loading:start
@Composable
internal fun ButtonLoadingExample(modifier: Modifier = Modifier) {
    FrogButton(
        onClick = {},
        modifier = modifier,
        variant = FrogButtonVariant.Primary,
        loading = true
    ) {
        Text("Saving...")
    }
}
// example:loading:end

// example:leading:start
@Composable
internal fun ButtonLeadingExample(modifier: Modifier = Modifier) {
    FrogButton(onClick = {}, modifier = modifier, leadingIcon = { Icon(FrogIcons.Play, null, Modifier.size(18.dp)) }) { Text("Run preview") }
}
// example:leading:end

// example:trailing:start
@Composable
internal fun ButtonTrailingExample(modifier: Modifier = Modifier) {
    FrogButton(onClick = {}, modifier = modifier, trailingIcon = { Icon(FrogIcons.Forward, null, Modifier.size(18.dp)) }) { Text("Continue") }
}
// example:trailing:end

// example:disabled:start
@Composable
internal fun ButtonDisabledExample(modifier: Modifier = Modifier) {
    FrogButton(onClick = {}, modifier = modifier, enabled = false) { Text("Unavailable") }
}
// example:disabled:end

// example:fullwidth:start
@Composable
internal fun ButtonFullWidthExample(modifier: Modifier = Modifier) {
    FrogButton(onClick = {}, modifier = modifier, fullWidth = true) { Text("Continue") }
}
// example:fullwidth:end
