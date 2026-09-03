package io.github.codewitheswar.frogui.testing

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import io.github.codewitheswar.frogui.theme.FrogTheme

/** Shared Android test fixture. Depend on this module only from test configurations. */
fun ComposeContentTestRule.setFrogContent(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    setContent { FrogTheme(darkTheme = darkTheme, content = content) }
}
