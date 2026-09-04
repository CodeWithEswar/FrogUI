package io.github.codewitheswar.frogui.showcase.style

import androidx.compose.runtime.*
import io.github.codewitheswar.frogui.theme.FrogTheme
import io.github.codewitheswar.frogui.theme.ProvideFrogThemeEnvironment

internal val LocalFrogMotionEnabled = staticCompositionLocalOf { true }

/** App owns its preference; FrogTheme owns system preference and component behavior. */
@Composable
internal fun ProvideShowcaseMotion(reduceMotion: Boolean, content: @Composable () -> Unit) {
    ProvideFrogThemeEnvironment(reduceMotion = reduceMotion) {
        CompositionLocalProvider(LocalFrogMotionEnabled provides !FrogTheme.reduceMotion, content = content)
    }
}

internal object ShowcaseMotion {
    val fast: Int @Composable get() = FrogTheme.motion.fastDurationMillis
    val standard: Int @Composable get() = FrogTheme.motion.normalDurationMillis
}
