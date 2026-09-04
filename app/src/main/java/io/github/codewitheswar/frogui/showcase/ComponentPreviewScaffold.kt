package io.github.codewitheswar.frogui.showcase

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.codewitheswar.frogui.theme.FrogTheme
import io.github.codewitheswar.frogui.theme.ProvideFrogThemeEnvironment

/** Shared Android Studio preview boundary for theme, canvas, padding, and deterministic motion. */
@Composable
internal fun FrogComponentPreview(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit,
) {
    FrogTheme(darkTheme = darkTheme) {
        ProvideFrogThemeEnvironment(reduceMotion = true) {
            Box(
                Modifier.fillMaxSize().background(FrogTheme.colors.background).padding(FrogTheme.spacing.xl)
            ) { content() }
        }
    }
}
